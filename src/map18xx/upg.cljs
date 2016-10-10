(ns map18xx.upg
  (:require [clojure.string :as string]
            [map18xx.tileset :as tileset]))

(def track-list (atom {}))

(defn tilelist [new-tiles]
  (reduce (fn [a [k v]]
            (assoc a k (if (contains? v "from")
                         (merge (tileset/track-list (v "from")) v)
                         (if (contains? tileset/track-list k)
                           (merge (tileset/track-list k) v)
                           v))))
           {} new-tiles))

(defn add-tiles [new-tiles]
  (do
    (swap! track-list merge (tilelist new-tiles))
    (reduce (fn [a [k v]]
              (if (contains? v "#.") (conj a {:tile k :remaining (v "#.")}) a))
            [] @track-list)))

; From SO
(defn substring? [sub st]
  "Find if sub is in the string st."
 (not= (.indexOf st sub) -1))

(defn sort-by-edge
  "Sort numbers first in accending order, then letters in accending order."
  [a b]
  (if (integer? a)
    (if (integer? b) (< a b) true)
    (if (integer? b) false (< a b))))

(defn rotate-set
  "Rotate all numbers in a list by a given amount, letters are unchanged."
  [by ends]
  (sort sort-by-edge (map #(if (integer? %) (mod (+ by (int %)) 6) %) ends)))

(defn rotate-edges
  "Apply a rotation to all end carrying values in a track description."
  [by edges]
  (reduce (fn [a [k v]] (assoc a k (if (substring? k ".cd")
                                       (sort-by (juxt first second) (map (partial rotate-set by) v))
                                       v)))
          {} edges))

(defn map-with-keys [ks base] (reduce #(assoc %1 %2 (base %2)) {} ks))
(defn map-by-type [ks base] (reduce #(update %1 (first %2) conj (base %2)) {} ks))

(defn get-edges
  [m]
  (let [track-keys (filter #(substring? (first %) ".cd") (keys m))
        merged (map-by-type track-keys m)]
     (if (contains? merged ".")
       (if (vector? (first (first (get merged "."))))
         (update merged "." first)
         merged)
       merged)))

(defn in-list [x y] (filter #(some (partial = %) y) x))
(defn in-lists [x ys] (some #(= x (in-list x %)) ys))

; all-in-lists returns a list of true/false for each xs if a complete match was in any ys.
(defn all-in-lists [xs ys] (and (not (nil? ys)) (every? #(in-lists % ys) xs)))

(defn merged-edges
  [from-map to-map]
  (let [
        new-merge-keys (filter #(substring? (first %) "m") (keys to-map))
        org-merge-keys (first (vals (select-keys to-map new-merge-keys)))
        ]
    (if (empty? new-merge-keys)
      (get-edges from-map)
      (reduce
                   (fn
                     [a [k v]]
                     (update a "c" into
                                  (map #(filter
                                    (fn [x] (not-any? (partial = x) org-merge-keys))
                                    %) v)))
                   {"c" []} (get-edges from-map)))))

(defn upgrade-from?
  "Remove entries from to-map that are in from-map, nil if something is in from-map but not to-map Return remaining items"
  [from-map from-orient to-map to-orient]
  (let [
        track-map (rotate-edges to-orient (get-edges to-map))
        from-map (rotate-edges from-orient (merged-edges from-map to-map))
        filtered (map (fn [[k v]] (all-in-lists (get from-map k) (get track-map k))) from-map)
        missed (some false? filtered)
        ]
    (not (or (some nil? filtered) missed))))

; Cacheable and expensive but not a hot-spot
(defn valid-upgrade?
  [from-map from-orient to-map to-orient]
  (if (and (= (to-map "p.") (inc (from-map "p.")))
           (= (first (to-map "t.")) (first (from-map "t.")))
           (upgrade-from? from-map from-orient to-map to-orient)) true false))

(defn valid-tile?
  [from-map from-orient to-map]
  (filter #(valid-upgrade? from-map from-orient to-map %) (range 6)))

(defn up-list
  [from orient]
  (let [from-map (@track-list from)]
    (filter #(not (empty? (second %)))
            (map #(vector (first %) (valid-tile? from-map orient (second %))) @track-list))))

; cachable, but not a hotspot.
(defn all-up [from] (filter #(not (empty? (second %))) (map #(list % (up-list from %)) (range 6))))

(defn filter-as-any
  [edges edge-lists]
  (some identity (map (fn [[k v]] (in-lists edges v))
                      edge-lists)))

(defn upgrade-options
  [from orient edges]
  (let [choices (up-list from orient)
        valid (map (fn [[k v]]
                       (reduce (fn [[k xs] v] [k (conj xs v)]) [k []]
                               (filter
                                 #(filter-as-any edges (rotate-edges % (get-edges (@track-list k))))
                                 v)))
                   choices)]
    (filter #(not (empty? (second %))) valid)))

(defn unique-path?
  [tile orient edges]
  (let [options (upgrade-options tile orient edges)
        counts (apply merge (map (fn [[k v]] {k (count v)}) options))
        popular (not= 1 (count options))
        rotated (map (fn [v]
                       (rotate-edges v (get-edges (@track-list (first (first options))))))
                     (second (first options)))
        same (apply = rotated) ]
    (if (and (not popular) same) [(first (first options)) (first (second (first options)))] nil)))

(defn upgrade
  [tile orient new-tile new-orient]
  (let [new-spec (@track-list new-tile)
        org-spec (@track-list tile)
        new-merge-keys (filter #(substring? (first %) "m") (keys new-spec))
        org-merge-keys (first (vals (select-keys new-spec new-merge-keys)))
        new-city-keys (filter #(substring? (first %) "c") (keys new-spec))
        org-city-keys (filter #(substring? (first %) ".c") (keys org-spec))   ; track can merge into cities.
        new-rotated  (reduce #(assoc %1 (subs %2 1)
                                     (rotate-set new-orient (new-spec %2)))
                             {} new-city-keys)
        org-rotated  (reduce #(assoc %1 (subs %2 1)
                                     (rotate-set orient
                                                 (filter (fn [x] (not-any? (partial = x) org-merge-keys))
                                                         (org-spec %2))))
                             {} org-city-keys)
        merge-keys (merge (select-keys new-spec new-merge-keys)
                          (reduce (fn [a [k v]]
                                    (merge a {k
                                            (some #(if (in-lists v [(second %)])
                                                     (first %))
                                                  new-rotated)}))
                                  {} org-rotated))
        ]
    [merge-keys {:orient new-orient :tile new-tile}]))

(defn tile-sort
  [xs]
  (sort (fn [a b]
          (let [a-info (@track-list (:tile a))
                b-info (@track-list (:tile b))
                ]
            (reduce #(if (= 0 %1) (%2) %1) 0
                  [#(- (get a-info "p.") (get b-info "p." ))
                   #(compare (get-in a-info ["t." 0]) (get-in b-info ["t." 0]))
                   #(compare (:tile a) (:tile b))])))
          xs))
