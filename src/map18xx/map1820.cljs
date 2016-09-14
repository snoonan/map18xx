(ns map18xx.map1820)

(defn color-offboard
  [coords colors values]
  (loop [coords coords
         values values
         colors colors
         xd {}]
    (if (empty? values)
      xd
      (recur (rest coords) (rest values) (rest colors) (assoc xd [(first coords) (second coords) (first colors)] (first values))))))

(defn color-offboard-line
  [values]
  (color-offboard ["f2" "e5" "d8" "c11" "b14"] ["yellow" "green" "brown" "lightgrey"] values))

(defn color-offboard-box
  [values]
  (merge
    (color-offboard ["f6" "e9" "d12"] ["brown" "lightgrey"] (drop 2 values))
    (color-offboard ["e5" "d8" "c11"] ["yellow" "green"] (take 2 values))))

(def ob1244 (color-offboard-box ["10" "20" "40" "20"]))
(def ob2235 (color-offboard-box ["20" "20" "30" "50"]))
(def ob2332 (color-offboard-box ["20" "30" "30" "20"]))
(def ob2334 (color-offboard-box ["20" "30" "30" "40"]))
(def ob2342 (color-offboard-box ["20" "30" "40" "20"]))
(def ob2344 (color-offboard-box ["20" "30" "40" "40"]))
(def ob2345 (color-offboard-box ["20" "30" "40" "50"]))
(def ob2433 (color-offboard-box ["20" "40" "30" "30"]))
(def ob2533 (color-offboard-box ["20" "50" "30" "30"]))
(def ob3223 (color-offboard-box ["30" "20" "20" "30"]))
(def ob3345 (color-offboard-box ["30" "30" "40" "50"]))
(def ob3432 (color-offboard-box ["30" "40" "30" "20"]))
(def ob3434 (color-offboard-box ["30" "40" "30" "40"]))
(def ob3456 (color-offboard-box ["30" "40" "50" "60"]))
(def ob4681 (color-offboard-box ["40" "60" "80" "100"]))

(def app-state {
                :rotate 30
                :scale 50
                :tiles [
                        { :pos "a7"  :tile "t7"   :orient 2 :color "grey"}
                        { :pos "a9"  :tile "t7"   :orient 2 :color "grey"}
                        { :pos "a11" :tile "t7"   :orient 2 :color "grey"}
                        { :pos "a13" :tile "t506" :orient 2 :label ob3345 :color "red" }
                        { :pos "a15" :tile "t506" :orient 2 :label ob3345 :color "red" }
                        { :pos "b4"  :tile "t505" :orient 1 :label ob3432 :color "blue" }
                        { :pos "b6"  :tile "t503" :orient 0 :overlay {"g11" "twater2"} }
                        { :pos "b8"  :tile "t500" :orient 0 :overlay {"g11" "tmountain2"} }
                        { :pos "b10" :tile "t500" :orient 0 :overlay {"g11" "tmountain2"} }
                        { :pos "b12" :tile "t500" :orient 0 :overlay {"g11" "tmountain2"} }
                        { :pos "b14" :tile "t500" :orient 0 }
                        { :pos "b16" :tile "t500" :orient 0 }
                        { :pos "c3"  :tile "t506" :orient 1
                          :label {["f2" "b14" "white"] "Liverpool+20"} :color "blue" }
                        { :pos "c5"  :tile "tbig" :orient 0 :overlay {"g11" "twater"}
                                                  :overlay-punch {"i9" "ttributary0r" }}
                        { :pos "c7"  :tile "t504" :orient 0 :overlay {"g11" "tmountain2"} }
                        { :pos "c9"  :tile "t500" :orient 0 :overlay {"g11" "tmountain2"} }
                        { :pos "c11" :tile "t504" :orient 0 :overlay {"g11" "tmountain2"} }
                        { :pos "c13" :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
                        { :pos "c15" :tile "t503" :orient 0 :overlay {"g11" "twater"}
                                                  :overlay-punch {"g15" "ttributary2r" }}
                        { :pos "c17" :tile "t505" :orient 4 :label ob2342 :color "blue" }
                        { :pos "d2"  :tile "t506" :orient 1 :label ob3434 :color "red" }
                        { :pos "d4"  :tile "t504" :orient 0 :overlay {"g11" "twater2"} }
                        { :pos "d6"  :tile "t519" :orient 0 :overlay {"h9" "tmountain2"} }
                        { :pos "d8"  :tile "t500" :orient 0 :overlay {"g11" "tmountain2"} }
                        { :pos "d10" :tile "t504" :orient 0 :overlay {"g11" "tmountain2"} }
                        { :pos "d12" :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
                        { :pos "d14" :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
                        { :pos "d16" :tile "tbig" :orient 0 :overlay {"g11" "twater2"} }
                        { :pos "d18" :tile "t505" :orient 4 :label ob2235 :color "blue" }
                        { :pos "e1"  :tile "t506" :orient 1 :label ob3434 :color "red" }
                        { :pos "e3"  :tile "t500" :orient 0 }
                        { :pos "e5"  :tile "t500" :orient 0 }
                        { :pos "e7"  :tile "t500" :orient 0 :overlay {"g11" "tmountain2"} }
                        { :pos "e9"  :tile "t500" :orient 0 :overlay {"g11" "tmountain2"} }
                        { :pos "e11" :tile "t500" :orient 0 }
                        { :pos "e13" :tile "t500" :orient 0 }
                        { :pos "e15" :tile "t500" :orient 0 }
                        { :pos "e17" :tile "t500" :orient 0 }
                        { :pos "f2"  :tile "t500" :orient 0 }
                        { :pos "f4"  :tile "t500" :orient 0 }
                        { :pos "f6"  :tile "t500" :orient 0 }
                        { :pos "f8"  :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
                        { :pos "f10" :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
                        { :pos "f12" :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
                        { :pos "f14" :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
                        { :pos "f16" :tile "t500" :orient 0 }
                        { :pos "g1"  :tile "t624" :orient 1 :color "grey" }
                        { :pos "g3"  :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
                        { :pos "g5"  :tile "t500" :orient 0 }
                        { :pos "g7"  :tile "tbig" :orient 0 }
                        { :pos "g9"  :tile "t500" :orient 0 }
                        { :pos "g11" :tile "t500" :orient 0 }
                        { :pos "g13" :tile "t500" :orient 0 }
                        { :pos "g15" :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
                        { :pos "g17" :tile "t505" :orient 2 :label ob3223 :color "blue" }
                        { :pos "g19" :tile "t500" :orient 0 }
                        { :pos "h2"  :tile "t500" :orient 0 }
                        { :pos "h4"  :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
                        { :pos "h6"  :tile "t500" :orient 0 }
                        { :pos "h8"  :tile "t500" :orient 0 }
                        { :pos "h10" :tile "t500" :orient 0 }
                        { :pos "h12" :tile "t500" :orient 0 }
                        { :pos "h14" :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
                        { :pos "h16" :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
                        { :pos "h18" :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
                        { :pos "h20" :tile "t500" :orient 0 }
                        { :pos "h22" :tile "t500" :orient 0 }
                        { :pos "i1"  :tile "t7"   :orient 0 :color "grey"}
                        { :pos "i3"  :tile "t500" :orient 0 }
                        { :pos "i5"  :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
                        { :pos "i7"  :tile "t500" :orient 0 }
                        { :pos "i9"  :tile "t500" :orient 0 }
                        { :pos "i11" :tile "t500" :orient 0 }
                        { :pos "i13" :tile "t500" :orient 0 }
                        { :pos "i15" :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
                        { :pos "i17" :tile "t500" :orient 0 }
                        { :pos "i19" :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
                        { :pos "i21" :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
                        { :pos "i23" :tile "t505" :orient 4 :label ob2533 :color "blue" }
                        { :pos "j2"  :tile "t507" :orient 0 :label ob3345 :color "red" }
                        { :pos "j4"  :tile "t500" :orient 0 }
                        { :pos "j6"  :tile "t500" :orient 0 }
                        { :pos "j8"  :tile "t500" :orient 0 }
                        { :pos "j10" :tile "t500" :orient 0 }
                        { :pos "j12" :tile "t500" :orient 0 }
                        { :pos "j14" :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
                        { :pos "j16" :tile "t500" :orient 0 }
                        { :pos "j18" :tile "t500" :orient 0 }
                        { :pos "j20" :tile "t500" :orient 0 }
                        { :pos "j22" :tile "t500" :orient 0 }
                        { :pos "k1"  :tile "t505" :orient 1 :label ob2344 :color "blue" }
                        { :pos "k3"  :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
                        { :pos "k5"  :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
                        { :pos "k7"  :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
                        { :pos "k9"  :tile "t500" :orient 0 }
                        { :pos "k11" :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
                        { :pos "k13" :tile "t500" :orient 0 }
                        { :pos "k15" :tile "t500" :orient 0 }
                        { :pos "k17" :tile "t500" :orient 0 }
                        { :pos "k19" :tile "t500" :orient 0 }
                        { :pos "k21" :tile "t505" :orient 4 :label ob1244 :color "blue" }
                        { :pos "l2"  :tile "t500" :orient 0 }
                        { :pos "l4"  :tile "t500" :orient 0 }
                        { :pos "l6"  :tile "t500" :orient 0 }
                        { :pos "l8"  :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
                        { :pos "l10" :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
                        { :pos "l12" :tile "t500" :orient 0 }
                        { :pos "l14" :tile "t500" :orient 0 }
                        { :pos "l16" :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
                        { :pos "l18" :tile "t500" :orient 0 :overlay {"g11" "twater"}
                                                  :overlay-punch {"a9" "ttributary0r"} }
                        { :pos "l20" :tile "t505" :orient 4 :label ob3456 :color "blue" }
                        { :pos "m1"  :tile "t507" :orient 0 :label ob3345 :color "red" }
                        { :pos "m3"  :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
                        { :pos "m5"  :tile "t500" :orient 0 }
                        { :pos "m7"  :tile "t500" :orient 0 }
                        { :pos "m9"  :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
                        { :pos "m11" :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
                        { :pos "m13" :tile "g809" :orient 0 }
                        { :pos "m15" :tile "gchat" :orient 0 }
                        { :pos "m17" :tile "ghoriz" :orient 0 }
                        { :pos "m19" :tile "t505" :orient 4 :label ob4681 :color "blue" }
                        { :pos "n2"  :tile "t500" :orient 0 }
                        { :pos "n4"  :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
                        { :pos "n6"  :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
                        { :pos "n8"  :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
                        { :pos "n10" :tile "t500" :orient 0 }
                        { :pos "n12" :tile "t500" :orient 0 }
                        { :pos "n14" :tile "t500" :orient 0 }
                        { :pos "n16" :tile "t500" :orient 0 }
                        { :pos "n18" :tile "t503" :orient 0 }
                        { :pos "n20" :tile "t505" :orient 4 :label ob2433 :color "blue" }
                        { :pos "o1"  :tile "t7"   :orient 0 :color "grey"}
                        { :pos "o3"  :tile "t500" :orient 0 }
                        { :pos "o5"  :tile "t505" :orient 0 :label ob3456 :color "blue" }
                        { :pos "o9"  :tile "t505" :orient 5 :label ob2332 :color "blue" }
                        { :pos "o11" :tile "t500" :orient 0 }
                        { :pos "o13" :tile "t500" :orient 0 }
                        { :pos "o15" :tile "t500" :orient 0 }
                        { :pos "o17" :tile "tbig" :orient 0 :overlay {"g11" "twater2"} }
                        { :pos "o19" :tile "t505" :orient 4 :label ob2345 :color "blue" }
                        { :pos "p2"  :tile "t505" :orient 0 :label ob2342 :color "blue" }
                        { :pos "p4"  :tile "t505" :orient 5 :label ob2332 :color "blue" }
                        { :pos "p12" :tile "t505" :orient 0 :label ob2342 :color "blue" }
                        { :pos "p16" :tile "t505" :orient 0 :label ob2334 :color "blue" }
                        ]
                        :companies [
                                    {:name "S&LR" :token "S_LR"}
                                    {:name "B&DR" :token "B_DR"}
                                    {:name "blue" :token "blue"}
                                    {:name "yellow" :token "yellow"}
                                    {:name "magenta" :token "magenta"}]
                        :transform-track {"t500" {"t503" ["e7"  "target"]
                                                  "tbig" ["d10" "btarget"]
                                                  "t501" ["f10" "dit" "black"]}}
            })


(def track-list {
  ;track-simple
                "t500" {}
                "t7"   {"#." -1}
                "t8"   {"#." -1}
                "t9"   {"#." -1}
                "t29"  {"#." 5}
                "t27"  {"#." 5}
                "t30"  {"#." 5}
                "t624" {"#." 5}
                "t31"  {"#." 5}
                "t26"  {"#." 5}
                "t28"  {"#." 5}
                "t625" {"#." 5}
                "t22"  {"#." 5}
                "t18"  {"#." 5}
                "t626" {"#." 5}
                "t21"  {"#." 5}
                "t24"  {"#." 5}
                "t25"  {"#." 5}
                "t16"  {"#." 5}
                "t19"  {"#." 5}
                "t23"  {"#." 5}
                "t17"  {"#." 5}
                "t20"  {"#." 5}
                "t39"  {"#." 5}
                "t40"  {"#." 5}
                "t42"  {"#." 5}
                "t41"  {"#." 5}
                "t43"  {"#." 5}
                "t44"  {"#." 5}
                "t45"  {"#." 5}
                "t46"  {"#." 5}
                "t47"  {"#." 5}
                "t70"  {"#." 5}
                "t627" {"#." 5}
                "t628" {"#." 5}
                "t629" {"#." 5}
;track-city
                "t503" {}
                "t5"   {"#." 10}
                "t6"   {"#." 10}
                "t57"  {"#." 10}
                "t14"  {"#." 8}
                "t15"  {"#." 8}
                "t38"  {"#." 8}
                "t51"  {"#." 12}
;track-dits
                "t501" {}
                "t3"   {"#." 5}
                "t58"  {"#." 15}
                "t4"   {"#." 15}
                "t3g"  {"from" "t3"  "p." 2 "#." 5 "ve9" [20 "e9"]}
                "t58g" {"from" "t58" "p." 2 "#." 7 "ve9" [20 "e7"]}
                "t4g"  {"from" "t4"  "p." 2 "#." 7 "ve9" [20 "d12"]}
                "t143" {"p." 3 "#." 5}
                "t142" {"p." 3 "#." 5}
                "t141" {"p." 3 "#." 5}
                "t203" {"p." 3 "#." 5}
;B cities
                "tbig" {}
                "t148" {"#." 7}
                "t149" {"#." 7}
                "t150" {"#." 7}
                "t179" {"#." 7}
                "t180" {"#." 7}
                "t181" {"#." 7}
                "t182" {"#." 7}
                "t198" {"#." 7}
                "t199" {"#." 7}
                "t200" {"#." 7}
                "t290" {"#." 5}
;track-oo
               "t504" {}
               "t803" {"#." 2}
               "t248" {"#." 2}
               "t802" {"#." 2}
               "t35"  {"#." 1}
               "t36"  {"#." 1}
               "t54"  {"#." 1}
               "t64"  {"#." 1}
               "t65"  {"#." 1}
               "t66"  {"#." 1}
               "t67"  {"#." 1}
               "t68"  {"#." 1}
               "t212" {"#." 1}
               "t339" {"#." 3}
               "t804" {"#." 2}
; manchester
               "t519" { "t." ["M" "h6"] "p." 0 "cc9" [] "cf12" [] "cf6" []}
               "t394" { "t." ["M" "h6"] "p." 1 "cc9" [0 3] "cf6" [1 4] "cf12" [2 5] "vc9" [40 "e15"] "#." 1}
               "t815" { "t." ["M" "h6"] "p." 2 "cc9" [0 1 3] "cf6" [1 4 5] "cf12" [2 3 5] "vc9" [40 "e15"] "#." 1}
               "t375" { "t." ["M" "h6"] "p." 3 "me9" ["c9" "f6" "f12"] "re9" ["f6" "d8" "f10" "d12"] "ce9" [0 1 2 3 4 5] "vc9" [80 "h12"] "#." 1}
               "tM04" { "t." ["M" "h6"] "p." 4 "re9" ["f6" "d8" "f10" "d12"] "ce9" [0 1 2 3 4 5] "vc9" [80 "h12"] "#." 1}
; london docks
               "ghoriz" {"p." -2 ".A" [[0 5] [2 3] [1 4]]}
               "gchat" {"p." -2 ".A" [[1 4]] "cg11" [1 2 3]}
;london
               "g809" {"p." -2 "cb10" [0 1] "cc5" [1 5] "cf6" [1 4] "ch9" [1 3] "cf14" [1 2]}
;map tiles
               "t505" {}
               "t506" {}
               "t507" {} })
