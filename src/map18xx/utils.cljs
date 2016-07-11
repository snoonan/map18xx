(ns map18xx.utils)

(defn pos-to-rc
 "Translate L## into (x y)"
 [pos]
 (if-let [[row & col] pos]
 [(- (.charCodeAt row 0) 97) (reduce #(+ (* %1 10) (int %2)) col) ]
 [-2 -2]))

(defn find-direction
  "Find the edge number in the target hex when coming from the 'from' hex"
 [from to rotate]
 (let [p1 (pos-to-rc from)
       p2 (pos-to-rc to)
       dr (- (p2 0) (p1 0))
       dc (- (p2 1) (p1 1))
       ]
    (cond
      (and (= rotate  0) (= dr -2) (= dc  0)) 3
      (and (= rotate  0) (= dr -1) (= dc  1)) 4
      (and (= rotate  0) (= dr  1) (= dc  1)) 5
      (and (= rotate  0) (= dr  2) (= dc  0)) 0
      (and (= rotate  0) (= dr  1) (= dc -1)) 1
      (and (= rotate  0) (= dr -1) (= dc -1)) 2
      (and (= rotate 30) (= dr -1) (= dc  1)) 3
      (and (= rotate 30) (= dr  0) (= dc  2)) 4
      (and (= rotate 30) (= dr  1) (= dc  1)) 5
      (and (= rotate 30) (= dr  1) (= dc -1)) 0
      (and (= rotate 30) (= dr  0) (= dc -2)) 1
      (and (= rotate 30) (= dr -1) (= dc -1)) 2
    :else nil
    )))
