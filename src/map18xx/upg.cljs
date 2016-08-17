(ns map18xx.upg
  (:require [clojure.string :as string]
            [map18xx.map1820 :as board]))

; From SO
(defn- substring? [sub st]
  "Find if sub is in the string st."
 (not= (.indexOf st sub) -1))

(defn- digit? [c] (and (>= 0 (compare \0 c))
                      (>= 0 (compare c \9))))

(defn- sort-by-edge
  "Sort numbers first in accending order, then letters in accending order."
  [a b]
  (if (integer? a)
    (if (integer? b) (< a b) true)
    (if (integer? b) false (< a b))))

(defn- rotate-set
  "Rotate all numbers in a list by a given amount, letters are unchanged."
  [by ends]
  (sort sort-by-edge (map #(if (integer? %) (mod (+ by (int %)) 6) %) ends)))

(defn- rotate-edges
  "Apply a rotation to all end carrying values in a track description."
  [by edges]
  (reduce (fn [a [k v]] (assoc a k (if (substring? k ".cd")
                                       (sort-by (juxt first second) (map (partial rotate-set by) v))
                                       v)))
          {} edges))

(defn- map-with-keys [ks base] (reduce #(assoc %1 %2 (base %2)) {} ks))
(defn- map-by-type [ks base] (reduce #(update %1 (first %2) conj (base %2)) {} ks))

(defn- get-edges
  [m]
  (let [track-keys (filter #(substring? (first %) ".cd") (keys m))
        merged (map-by-type track-keys m)]
        ;merged (map-with-keys track-keys m)]
     (if (contains? merged ".") (update merged "." first) merged)))

(defn- in-list [x y] (filter #(some (partial = %) y) x))
(defn- in-lists [x ys] (some #(= x (in-list x %)) ys))

; all-in-lists returns a list of true/false for each xs if a complete match was in any ys.
(defn- all-in-lists [xs ys] (every? #(in-lists % ys) xs))

(defn- upgrade-from?
  "Remove entries from up-to that are in up-from, nil if something is in up-from but not up-to. Return remaining items"
  [up-to up-from orient]
  (let [
        ;_ (prn )
        ;_ (prn "up-to" up-to)
        ;_ (prn "up-from" up-from)
        ;_ (prn "orient" orient)
        track-map (rotate-edges orient (get-edges up-to))
        ;_ (prn "track-map" track-map)
        from-map (get-edges up-from)
        ;_ (prn "from-map" from-map)
        ; If all lists in from-map have supersets in track-map then true
        filtered (map (fn [[k v]] (all-in-lists (get from-map k) (get track-map k))) from-map)
        ;_ (prn "filtered" filtered)
        missed (some false? filtered)
        ;_ (prn "missed" missed)
        ;_ (prn "mismatch" (or (some nil? filtered) missed))
        ]
    (not (or (some nil? filtered) missed))))

;pruned (select-keys filtered (for [[k v] filtered :when (not (empty? v))] k))

; Cacheable and expensive but not a hot-spot
(defn- valid-upgrades
  [from orient]

  (reduce (fn [a [k v]] (if (and (= (v "p.") (inc (from "p.")))
                                 (= (v "t.") (from "t."))
                                 (upgrade-from? v from orient))
                            (conj a k) a))
          [] board/track-list))

(defn- up-list [from orient] (valid-upgrades (board/track-list from) orient))
; cachable, but not a hotspot.
(defn- all-up [from] (filter #(not (empty? (second %))) (map #(list % (up-list from %)) (range 6))))

(defn- filter-as-any
  [edges edge-lists]
  (some identity (map (fn [[k v]] (in-lists edges v))
                      edge-lists)))


(defn- has-path
  [tile orient edges]
  (let [tile-options (all-up tile)]
    (filter #(not (empty? (second %)))
      (for [[rotation choices] tile-options]
        (let [edges (rotate-set (- 6 (mod (+ orient rotation) 6)) edges) ]
          [(mod (+ orient rotation) 6) (filter #(filter-as-any edges (get-edges (board/track-list %))) choices)])))))

(defn unique-path?
  [tile orient edges]
  (let [options (has-path tile orient edges)
        counts (apply merge (map (fn [[k v]] {k (count v)}) options))
        popular (some #(< 1 (second %)) counts)
        rotated (map (fn [[k v]] (rotate-edges k (get-edges (board/track-list (first v))))) options)
        same (apply = rotated) ]
    (if (and (not popular) same) [(first (first options)) (first (second (first options)))] nil)))

