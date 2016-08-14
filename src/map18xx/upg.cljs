; Need to compile description into a upgrade list, then use THAT at runtime to do upgrades
; upgrade list contains:
; "t803" {"t68" {} "t68" {"o." 2 "mU" "Z" "mZ" "U"} "t212" {} ...}
; upg takes list, attempts to apply transforms sequentially til one works, then applys the transform to the original tile metadata and replaces the type
; most of the code here goes to compiler except rotate related items.

(ns map18xx.upg
  (:require [clojure.string :as string]))

; From SO
(defn substring? [sub st]
  "Find if sub is in the string st."
 (not= (.indexOf st sub) -1))

; {[opcode & rest] :as key] ends <key> <ends> ...}
; opcode:
;   .   - track, ignore rest draw from (first end) to (second end)        checked for atleast
;   c   - city, draw to rest (one char only)  (map #(draw rest->%) end)   checked for atleast
;   d   - dit, draw to rest (one char only) (map #(draw rest->%) end)     checked for atleast
;   m   - merge or rename, rest now part of ends                          processed to annotate atleast
;   t   - type marker, checked to ensure sameness                         checked for atleast
;   r   - render, draw rest markers (cities) at ends (with rects behind to show same place)     render only, atleast ignores
;   l   - label, render ends to rest as text                              render only, atleast ignores
;   v   - value, value of rest is end                                     render only, atleast ignores
;   p   - phase, value is rest                                            tile compare checks, 0 - ground 1 - yellow ... -2 grey -4 red  (skip two so no upgrade path happens by accident)

(def track-simple {
                "t500" { "p." 0 ".A" []}
                "t7"   { "p." 1 ".A" [[0 1]]}
                "t8"   { "p." 1 ".A" [[0 2]]}
                "t9"   { "p." 1 ".A" [[0 3]]}
                "t29"  { "p." 2 ".A" [[0 1] [0 2]]}
                "t27"  { "p." 2 ".A" [[0 1] [0 3]]}
                "t30"  { "p." 2 ".A" [[0 1] [0 4]]}
                "t624" { "p." 2 ".A" [[0 1] [0 5]]}
                "t31"  { "p." 2 ".A" [[0 1] [1 3]]}
                "t26"  { "p." 2 ".A" [[0 1] [1 4]]}
                "t28"  { "p." 2 ".A" [[0 1] [1 5]]}
                "t625" { "p." 2 ".A" [[0 1] [2 3]]}
                "t22"  { "p." 2 ".A" [[0 1] [2 4]]}
                "t18"  { "p." 2 ".A" [[0 1] [2 5]]}
                "t626" { "p." 2 ".A" [[0 1] [3 4]]}
                "t21"  { "p." 2 ".A" [[0 1] [3 5]]}
                "t24"  { "p." 2 ".A" [[0 2] [0 3]]}
                "t25"  { "p." 2 ".A" [[0 2] [0 4]]}
                "t16"  { "p." 2 ".A" [[0 2] [1 3]]}
                "t19"  { "p." 2 ".A" [[0 2] [1 4]]}
                "t23"  { "p." 2 ".A" [[0 2] [2 5]]}
                "t17"  { "p." 2 ".A" [[0 2] [3 5]]}
                "t20"  { "p." 2 ".A" [[0 3] [1 4]]}
                "t39"  { "p." 3 ".A" [[0 1] [0 2] [1 2]]}
                "t40"  { "p." 3 ".A" [[0 2] [0 4] [2 4]]}
                "t42"  { "p." 3 ".A" [[0 2] [0 3] [2 3]]}
                "t41"  { "p." 3 ".A" [[0 3] [0 4] [3 4]]}
                "t43"  { "p." 3 ".A" [[0 2] [0 3] [1 2] [1 3]]}
                "t44"  { "p." 3 ".A" [[0 1] [0 3] [1 4] [3 4]]}
                "t45"  { "p." 3 ".A" [[0 3] [0 5] [1 3] [1 5]]}
                "t46"  { "p." 3 ".A" [[0 1] [0 3] [1 5] [3 5]]}
                "t47"  { "p." 3 ".A" [[0 3] [0 4] [1 3] [1 4]]}
                "t70"  { "p." 3 ".A" [[0 1] [0 2] [1 3] [2 3]]}
                "t627" { "p." 3 ".A" [[0 1] [0 3] [1 2] [2 3]]}
                "t628" { "p." 3 ".A" [[0 2] [0 5] [2 3] [3 5]]}
                "t629" { "p." 3 ".A" [[0 2] [0 4] [2 3] [3 4]]}
                })

(def track-city {
                "t503" { "p." 0 "cA" []}
                "t5"   { "p." 1 "cA" [0 1] "vA" 20}
                "t6"   { "p." 1 "cA" [0 2] "vA" 20}
                "t57"  { "p." 1 "cA" [0 3] "vA" 20}
                "t14"  { "p." 2 "cA" [0 1 3 4] "rA" "BH" "vA" 30}
                "t15"  { "p." 2 "cA" [0 1 2 3] "rA" "BH" "vA" 30}
                "t38"  { "p." 2 "cA" [0 1 3 5] "rA" "BH" "vA" 30}
                "t51"  { "p." 3 "cA" [0 1 2 3 4] "rA" "BH" "vA" 40}})

(def track-bcity {
                "tbig" { "p." 0 "cA" [] "t." "B"}
                "t148" { "p." 1 "cA" [0 1] "t." "B" "vA" 30}
                "t149" { "p." 1 "cA" [0 2] "t." "B" "vA" 30}
                "t150" { "p." 1 "cA" [0 3] "t." "B" "vA" 30}
                "t179" { "p." 2 "cA" [0 1 2] "t." "B" "vA" 40}
                "t180" { "p." 2 "cA" [0 1 3] "t." "B" "vA" 40}
                "t181" { "p." 2 "cA" [0 1 4] "t." "B" "vA" 40}
                "t182" { "p." 2 "cA" [0 2 4] "t." "B" "vA" 40}
                "t198" { "p." 3 "cA" [0 1 3 4] "rA" "BH" "t." "B" "vA" 60}
                "t199" { "p." 3 "cA" [0 1 2 3] "rA" "BH" "t." "B" "vA" 60}
                "t200" { "p." 3 "cA" [0 1 3 5] "rA" "BH" "t." "B" "vA" 60}
                "t290" { "p." 4 "cA" [0 1 2 3 4] "rA" "BH" "t." "B" "vA" 70}})

(def track-dits {
                "t501" { "p." 0 "dA" []}
                "t3"   { "p." 1 "dA" [0 1] "vA" 10}
                "t58"  { "p." 1 "dA" [0 2] "vA" 10}
                "t4"   { "p." 1 "dA" [0 3] "vA" 10}
                "t3g"  { "p." 2 "dA" [0 1] "vA" 20}
                "t58g" { "p." 2 "dA" [0 2] "vA" 20}
                "t4g"  { "p." 2 "dA" [0 3] "vA" 20}
                "t143" { "p." 3 "dA" [0 1 2] "vA" 30}
                "t142" { "p." 3 "dA" [0 1 3] "vA" 30}
                "t141" { "p." 3 "dA" [0 1 4] "vA" 30}
                "t203" { "p." 3 "dA" [0 2 4] "vA" 30}})

(def track-oo {
               "t504" { "p." 0 "cZ" [] "cU" []}
               "t803" { "p." 1 "cZ" [0 1] "cU" [] "vZ" 30}
               "t248" { "p." 1 "cZ" [0 2] "cU" [] "vZ" 30 "rZ" "P"}
               "t802" { "p." 1 "cZ" [0 3] "cU" [] "vZ" 30}
               "t35"  { "p." 2 "cZ" [0 2] "cU" [1 3] "vZ" 40 "vU" 40}
               "t36"  { "p." 2 "cZ" [0 2] "cU" [3 5] "vZ" 40 "vU" 40}
               "t54"  { "p." 2 "cZ" [0 3] "cU" [1 2] "vZ" 40 "vU" 40}
               "t64"  { "p." 2 "cZ" [0 3] "cU" [1 5] "vZ" 30 "vU" 40}
               "t65"  { "p." 2 "cZ" [0 3] "cU" [1 4] "vZ" 40 "vU" 40}
               "t66"  { "p." 2 "cZ" [0 2] "cU" [3 4] "vZ" 40 "vU" 40}
               "t67"  { "p." 2 "cZ" [0 2] "cU" [4 5] "vZ" 40 "vU" 40}
               "t68"  { "p." 2 "cZ" [0 1] "cU" [2 3] "vZ" 40 "vU" 40}
               "t212" { "p." 2 "cZ" [0 1] "cU" [3 4] "vZ" 40 "vU" 40}
               "t339" { "p." 3 "cZ" [0 1 3] "cU" [2 4 5] "vZ" 50 "vU" 50}
               "tooe" { "p." 4 "cZ" [0 1 3 "U"] "cU" [2 4 5] "vZ" 60 "vU" 60}
               })


(def track-man {"t519" { "t." "M" "p." 0 "cN" [] "cR" [] "cV" []}
                "t394" { "t." "M" "p." 1 "cN" [0 3] "cR" [1 4] "cV" [2 5]}
                "t815" { "t." "M" "p." 2 "cN" [0 1 3] "cR" [1 4 5] "cV" [2 3 5]}
                "t375" { "t." "M" "p." 3 "mNRV" "A" "rA" "CGIM" "cA" [0 1 2 3 4 5]} })

(def track-list (merge track-simple track-city track-bcity track-dits track-oo track-man))

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

;(defn- rotate-edges
;  "Apply a rotation to all end carrying values in a track description."
;  [by edges]
;  (let [
;  (reduce #(apply assoc %1 (if (substring? (first (first %2)) ".cd") (rotate-set by %2) %2)) {} edges))

(defn- rotate-edges
  "Apply a rotation to all end carrying values in a track description."
  [by edges]
  (reduce #(apply assoc %1 (if (substring? (first (first %2)) "cd")
                             [(first %2) (rotate-set by (second %2))]
                             (if (= (first (first %2)) ".")
                               [(first %2) (map (fn [inner] (rotate-set by inner)) (second %2))]
                               %2)))
          {} edges))

(defn- map-with-keys [ks base] (reduce #(assoc %1 %2 (base %2)) {} ks))

(defn- get-edges
  [m]
  (let [track-keys (filter #(substring? (first %) ".cd") (keys m)) ]
        (map-with-keys track-keys m)))

(defn- upgrade-from?
  "Remove entries from up-to that are in up-from, nil if something is in up-from but not up-to. Return remaining items"
  [up-to up-from orient]
  (let [
        rename-keys (filter #(= (first %) "m") (keys up-to))
        expand-map (fn [from to] (reduce #(assoc %1 (str %2) to) {} (rest from)))
        rename-map (reduce #(merge %1 (expand-map (first %2) (second %2))) {} (map-with-keys rename-keys up-to))
        track-map (rotate-edges orient (get-edges up-to))
        from-map (get-edges up-from)
        rename-filter (fn [a [k v]] (assoc a (str (first k) (rename-map (second k) (second k))) v))
        rename-track (reduce rename-filter {} from-map)
        ; filter up-from items that exist in up-to, if resulting list is the same as up-from then the whole set is present
        ; so filter out the items from up-to, else keep base.
        filtered (reduce (fn [a [k v]] (assoc a k (= (filter #(some (partial = %) (track-map k))
                                                              v)
                                                         v)
                                                   ))
                                         track-map rename-track)
        missed (some #(nil? (second %)) filtered)
        extra (not (= (keys filtered) (keys track-map)))
        _ (prn )
        _ (prn "up-to" up-to)
        _ (prn "up-from" up-from)
        _ (prn "orient" orient)
        _ (prn "rename-keys" rename-keys)
        _ (prn "rename-map" rename-map)
        _ (prn "track-map" track-map)
        _ (prn "from-map" from-map)
        _ (prn "rename-track" rename-track)
        _ (prn "filtered" filtered)
        _ (prn "missed" missed)
        _ (prn "extra" extra)
        _ (prn "mismatch" (or (not (every? #(second %) filtered)) missed extra) )
        ]
    (if (or (not (every? #(second %) filtered)) missed extra) nil (keys filtered))))

;pruned (select-keys filtered (for [[k v] filtered :when (not (empty? v))] k))

(defn valid-upgrades
  [from orient]

  (reduce (fn [a [k v]] (if (and (= (v "p.") (inc (from "p.")))
                                 (= (v "t.") (from "t."))
                                 (upgrade-from? v from orient))
                            (conj a k) a))
          [] track-list))

(defn has-path
  [tile orient edges]
  (let [ track-map (rotate-edges orient (track-list tile))   ]
    (loop [dests (keys (get-edges track-map))]
          (let [ dest (first dests)
           tile-edges (track-map dest)
          ]
      (if (nil? dest)
        nil
;  filtered (reduce (fn [a [k v]] (assoc a k (if (= (filter #(some (partial = %) (track-map k)) v ) v)
;                                             (filter #(not (some (partial = %) v)) (track-map k))
;                                             (track-map k))))
  ;(if (substring? (first (first %2)) "cd")
  ;                           [(first %2) (rotate-set by (second %2))]
  ;                           [(first %2) (map (fn [inner] (rotate-set by inner)) (second %2))]
        (if (empty? (reduce  #(if (= (first %1) %2) (rest %1) %1) edges tile-edges))
          true
          (recur (rest dests))))))))

(defn find-tile
  [from edges orient]
  (let [
        options (filterv #(has-path % orient edges) (valid-upgrades (track-list from) orient))
        ]
    (if (= 1 (count options)) [(first options) orient] nil)))


(defn upg
  [[tile orient] edges]
  (let [[track-rank & _track-con] (get track-list tile)
        track-con (first _track-con)
        sorted (sort (map #(mod (- % orient) 6) edges))
        ]
      ))

(defn up-list [from orient] (valid-upgrades (track-list from) orient))
(defn all-up [from] [from (map #(list % (up-list from %)) (range 6))])
