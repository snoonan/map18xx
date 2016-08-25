(ns map18xx.map1820)

(def app-state {
    :rotate 30
    :scale 50
    :tiles [
            { :pos "a7"  :tile "g7"   :orient 2 }
            { :pos "a9"  :tile "g7"   :orient 2 }
            { :pos "a11" :tile "g7"   :orient 2 }
            { :pos "a13" :tile "t506" :orient 2 :label [[0 -10] "30/30/40/50"] :color "red" }
            { :pos "a15" :tile "t506" :orient 2 :label [[0 -10] "30/30/40/50"] :color "red" }
            { :pos "b4"  :tile "t505" :orient 1 :label [[0 0] "30/40/30/20"] :color "blue" }
            { :pos "b6"  :tile "t503" :orient 0 :overlay [["twater"]] }
            { :pos "b8"  :tile "t500" :orient 0 :overlay [["tmountain"]] }
            { :pos "b10" :tile "t500" :orient 0 :overlay [["tmountain"]] }
            { :pos "b12" :tile "t500" :orient 0 :overlay [["tmountain"]] }
            { :pos "b14" :tile "t500" :orient 0 }
            { :pos "b16" :tile "t500" :orient 0 }
            { :pos "c3"  :tile "t506" :orient 1 :label [[0 -10] "Liverpool+20"] :color "blue" }
            { :pos "c5"  :tile "tbig" :orient 0 :overlay [["twater"] ["ttributary" 5]] }
            { :pos "c7"  :tile "t504" :orient 0 :overlay [["tmountain"]] }
            { :pos "c9"  :tile "t500" :orient 0 :overlay [["tmountain"]] }
            { :pos "c11" :tile "t504" :orient 0 :overlay [["tmountain"]] }
            { :pos "c13" :tile "t500" :orient 0 :overlay [["twater"]] }
            { :pos "c15" :tile "t500" :orient 0 :overlay [["twater"] ["ttributary" 4]] }
            { :pos "c17" :tile "t505" :orient 4 :label [[0 -10] "20/30/40/20"] :color "blue" }
            { :pos "d2"  :tile "t506" :orient 1 :label [[0 -10] "30/40/30/40"] :color "red" }
            { :pos "d4"  :tile "t504" :orient 0 :overlay [["twater"]] }
            { :pos "d6"  :tile "t519" :orient 0 :overlay [["tmountain"]] }
            { :pos "d8"  :tile "t500" :orient 0 :overlay [["tmountain"]] }
            { :pos "d10" :tile "t504" :orient 0 :overlay [["tmountain"]] }
            { :pos "d12" :tile "t500" :orient 0 :overlay [["twater"]] }
            { :pos "d14" :tile "t500" :orient 0 :overlay [["twater"]] }
            { :pos "d16" :tile "t500" :orient 0 :overlay [["twater"]] }
            { :pos "d18" :tile "t505" :orient 4 :label [[0 -10] "20/20/30/50"] :color "blue" }
            { :pos "e1"  :tile "t506" :orient 1 :label [[0 -10] "30/40/30/40"] :color "red" }
            { :pos "e3"  :tile "t500" :orient 0 }
            { :pos "e5"  :tile "t500" :orient 0 }
            { :pos "e7"  :tile "t500" :orient 0 :overlay [["tmountain"]] }
            { :pos "e9"  :tile "t500" :orient 0 :overlay [["tmountain"]] }
            { :pos "e11" :tile "t500" :orient 0 }
            { :pos "e13" :tile "t500" :orient 0 }
            { :pos "e15" :tile "t500" :orient 0 }
            { :pos "e17" :tile "t500" :orient 0 }
            { :pos "f2"  :tile "t500" :orient 0 }
            { :pos "f4"  :tile "t500" :orient 0 }
            { :pos "f6"  :tile "t500" :orient 0 }
            { :pos "f8"  :tile "t500" :orient 0 :overlay [["twater"]] }
            { :pos "f10" :tile "t500" :orient 0 :overlay [["twater"]] }
            { :pos "f12" :tile "t500" :orient 0 :overlay [["twater"]] }
            { :pos "f14" :tile "t500" :orient 0 :overlay [["twater"]] }
            { :pos "f16" :tile "t500" :orient 0 }
            { :pos "g1"  :tile "g624" :orient 1 }
            { :pos "g3"  :tile "t500" :orient 0 :overlay [["twater"]] }
            { :pos "g5"  :tile "t500" :orient 0 }
            { :pos "g7"  :tile "tbig" :orient 0 }
            { :pos "g9"  :tile "t500" :orient 0 }
            { :pos "g11" :tile "t500" :orient 0 }
            { :pos "g13" :tile "t500" :orient 0 }
            { :pos "g15" :tile "t500" :orient 0 :overlay [["twater"]] }
            { :pos "g17" :tile "t505" :orient 2 :label [[0 -10] "30/20/20/30"] :color "blue" }
            { :pos "g19" :tile "t500" :orient 0 }
            { :pos "h2"  :tile "t500" :orient 0 }
            { :pos "h4"  :tile "t500" :orient 0 :overlay [["twater"]] }
            { :pos "h6"  :tile "t500" :orient 0 }
            { :pos "h8"  :tile "t500" :orient 0 }
            { :pos "h10" :tile "t500" :orient 0 }
            { :pos "h12" :tile "t500" :orient 0 }
            { :pos "h14" :tile "t500" :orient 0 :overlay [["twater"]] }
            { :pos "h16" :tile "t500" :orient 0 :overlay [["twater"]] }
            { :pos "h18" :tile "t500" :orient 0 }
            { :pos "h20" :tile "t500" :orient 0 }
            { :pos "h22" :tile "t500" :orient 0 }
            { :pos "i1"  :tile "g7"   :orient 0 }
            { :pos "i3"  :tile "t500" :orient 0 }
            { :pos "i5"  :tile "t500" :orient 0 :overlay [["twater"]] }
            { :pos "i7"  :tile "t500" :orient 0 }
            { :pos "i9"  :tile "t500" :orient 0 }
            { :pos "i11" :tile "t500" :orient 0 }
            { :pos "i13" :tile "t500" :orient 0 }
            { :pos "i15" :tile "t500" :orient 0 :overlay [["twater"]] }
            { :pos "i17" :tile "t500" :orient 0 }
            { :pos "i19" :tile "t500" :orient 0 :overlay [["twater"]] }
            { :pos "i21" :tile "t500" :orient 0 :overlay [["twater"]] }
            { :pos "i23" :tile "t505" :orient 4 :label [[0 -10] "20/50/30/30"] :color "blue" }
            { :pos "j2"  :tile "t507" :orient 0 :label [[0 -10] "30/30/40/50"] :color "red" }
            { :pos "j4"  :tile "t500" :orient 0 }
            { :pos "j6"  :tile "t500" :orient 0 }
            { :pos "j8"  :tile "t500" :orient 0 }
            { :pos "j10" :tile "t500" :orient 0 }
            { :pos "j12" :tile "t500" :orient 0 }
            { :pos "j14" :tile "t500" :orient 0 :overlay [["twater"]] }
            { :pos "j16" :tile "t500" :orient 0 }
            { :pos "j18" :tile "t500" :orient 0 }
            { :pos "j20" :tile "t500" :orient 0 }
            { :pos "j22" :tile "t500" :orient 0 }
            { :pos "k1"  :tile "t505" :orient 1 :label [[0 -10] "20/30/40/40"] :color "blue" }
            { :pos "k3"  :tile "t500" :orient 0 :overlay [["twater"]] }
            { :pos "k5"  :tile "t500" :orient 0 :overlay [["twater"]] }
            { :pos "k7"  :tile "t500" :orient 0 :overlay [["twater"]] }
            { :pos "k9"  :tile "t500" :orient 0 }
            { :pos "k11" :tile "t500" :orient 0 :overlay [["twater"]] }
            { :pos "k13" :tile "t500" :orient 0 }
            { :pos "k15" :tile "t500" :orient 0 }
            { :pos "k17" :tile "t500" :orient 0 }
            { :pos "k19" :tile "t500" :orient 0 }
            { :pos "k21" :tile "t505" :orient 4 :label [[0 -10] "10/20/40/40"] :color "blue" }
            { :pos "l2"  :tile "t500" :orient 0 }
            { :pos "l4"  :tile "t500" :orient 0 }
            { :pos "l6"  :tile "t500" :orient 0 }
            { :pos "l8"  :tile "t500" :orient 0 :overlay [["twater"]] }
            { :pos "l10" :tile "t500" :orient 0 :overlay [["twater"]] }
            { :pos "l12" :tile "t500" :orient 0 }
            { :pos "l14" :tile "t500" :orient 0 }
            { :pos "l16" :tile "t500" :orient 0 }
            { :pos "l18" :tile "t500" :orient 0 :overlay [["twater"] ["ttributary" 2]] }
            { :pos "l20" :tile "t505" :orient 4 :label [[0 -10] "30/40/50/60"] :color "blue" }
            { :pos "m1"  :tile "t507" :orient 0 :label [[0 -10] "30/30/40/50"] :color "red" }
            { :pos "m3"  :tile "t500" :orient 0 :overlay [["twater"]] }
            { :pos "m5"  :tile "t500" :orient 0 }
            { :pos "m7"  :tile "t500" :orient 0 }
            { :pos "m9"  :tile "t500" :orient 0 :overlay [["twater"]] }
            { :pos "m11" :tile "t500" :orient 0 :overlay [["twater"]] }
            { :pos "m13" :tile "g809" :orient 0 }
            { :pos "m15" :tile "gchat" :orient 0 }
            { :pos "m17" :tile "ghoriz" :orient 0 }
            { :pos "m19" :tile "t505" :orient 4 :label [[0 -10] "40/60/80/100"] :color "blue" }
            { :pos "n2"  :tile "t500" :orient 0 }
            { :pos "n4"  :tile "t500" :orient 0 :overlay [["twater"]] }
            { :pos "n6"  :tile "t500" :orient 0 :overlay [["twater"]] }
            { :pos "n8"  :tile "t500" :orient 0 :overlay [["twater"] ["ttributary" 0]] }
            { :pos "n10" :tile "t500" :orient 0 }
            { :pos "n12" :tile "t500" :orient 0 }
            { :pos "n14" :tile "t500" :orient 0 }
            { :pos "n16" :tile "t500" :orient 0 }
            { :pos "n18" :tile "t503" :orient 0 }
            { :pos "n20" :tile "t505" :orient 4 :label [[0 -10] "20/40/30/30"] :color "blue" }
            { :pos "o1"  :tile "g7"   :orient 0 }
            { :pos "o3"  :tile "t500" :orient 0 }
            { :pos "o5"  :tile "t505" :orient 0 :label [[0 -10] "30/40/50/60"] :color "blue" }
            { :pos "o9"  :tile "t505" :orient 5 :label [[0 -10] "20/30/30/20"] :color "blue" }
            { :pos "o11" :tile "t500" :orient 0 }
            { :pos "o13" :tile "t500" :orient 0 }
            { :pos "o15" :tile "t500" :orient 0 }
            { :pos "o17" :tile "tbig" :orient 0 :overlay [["twater"]] }
            { :pos "o19" :tile "t505" :orient 4 :label [[0 -10] "20/30/40/50"] :color "blue" }
            { :pos "p2"  :tile "t505" :orient 0 :label [[0 -10] "20/30/40/20"] :color "blue" }
            { :pos "p4"  :tile "t505" :orient 5 :label [[0 -10] "20/30/30/20"] :color "blue" }
            { :pos "p12" :tile "t505" :orient 0 :label [[0 -10] "20/30/40/20"] :color "blue" }
            { :pos "p16" :tile "t505" :orient 0 :label [[0 -10] "20/30/30/40"] :color "blue" }
            ]
  :companies [
              {:name "red" :token "red"}
              {:name "green" :token "green"}
              {:name "blue" :token "blue"}
              {:name "yellow" :token "yellow"}
              {:name "magenta" :token "magenta"}
              ]})

; {[opcode & rest] :as key] ends <key> <ends> ...}
; opcode:
;   .   - track, ignore rest draw from (first end) to (second end)        checked for atleast
;   c   - city, draw to rest (one char only)  (map #(draw rest->%) end)   checked for atleast
;   d   - dit, draw to rest (one char only) (map #(draw rest->%) end)     checked for atleast
;   a   - actuall location of city/dit, render paths to here rest is at ends  render only.
;   m   - merge or rename, rest now part of ends                          processed to annotate atleast
;   t   - type marker, checked to ensure sameness                         checked for atleast
;   r   - render, draw rest markers (cities) at ends (with rects behind to show same place)     render only, atleast ignores
;   l   - label, render ends to rest as text                              render only, atleast ignores
;   v   - value, value of rest is end                                     render only, atleast ignores
;   p   - phase, value is ends                                            tile compare checks, 0 - ground 1 - yellow ... -2 grey -4 red  (skip two so no upgrade path happens by accident)

(def track-simple {
                "t500" { "t." "t" "p." 0 ".A" []}
                "t7"   { "t." "t" "p." 1 ".A" [[0 1]]}
                "t8"   { "t." "t" "p." 1 ".A" [[0 2]]}
                "t9"   { "t." "t" "p." 1 ".A" [[0 3]]}
                "t29"  { "t." "t" "p." 2 ".A" [[0 1] [0 2]]}
                "t27"  { "t." "t" "p." 2 ".A" [[0 1] [0 3]]}
                "t30"  { "t." "t" "p." 2 ".A" [[0 1] [0 4]]}
                "t624" { "t." "t" "p." 2 ".A" [[0 1] [0 5]]}
                "t31"  { "t." "t" "p." 2 ".A" [[0 1] [1 3]]}
                "t26"  { "t." "t" "p." 2 ".A" [[0 1] [1 4]]}
                "t28"  { "t." "t" "p." 2 ".A" [[0 1] [1 5]]}
                "t625" { "t." "t" "p." 2 ".A" [[0 1] [2 3]]}
                "t22"  { "t." "t" "p." 2 ".A" [[0 1] [2 4]]}
                "t18"  { "t." "t" "p." 2 ".A" [[0 1] [2 5]]}
                "t626" { "t." "t" "p." 2 ".A" [[0 1] [3 4]]}
                "t21"  { "t." "t" "p." 2 ".A" [[0 1] [3 5]]}
                "t24"  { "t." "t" "p." 2 ".A" [[0 2] [0 3]]}
                "t25"  { "t." "t" "p." 2 ".A" [[0 2] [0 4]]}
                "t16"  { "t." "t" "p." 2 ".A" [[0 2] [1 3]]}
                "t19"  { "t." "t" "p." 2 ".A" [[0 2] [1 4]]}
                "t23"  { "t." "t" "p." 2 ".A" [[0 2] [2 5]]}
                "t17"  { "t." "t" "p." 2 ".A" [[0 2] [3 5]]}
                "t20"  { "t." "t" "p." 2 ".A" [[0 3] [1 4]]}
                "t39"  { "t." "t" "p." 3 ".A" [[0 1] [0 2] [1 2]]}
                "t40"  { "t." "t" "p." 3 ".A" [[0 2] [0 4] [2 4]]}
                "t42"  { "t." "t" "p." 3 ".A" [[0 2] [0 3] [2 3]]}
                "t41"  { "t." "t" "p." 3 ".A" [[0 3] [0 4] [3 4]]}
                "t43"  { "t." "t" "p." 3 ".A" [[0 2] [0 3] [1 2] [1 3]]}
                "t44"  { "t." "t" "p." 3 ".A" [[0 1] [0 3] [1 4] [3 4]]}
                "t45"  { "t." "t" "p." 3 ".A" [[0 3] [0 5] [1 3] [1 5]]}
                "t46"  { "t." "t" "p." 3 ".A" [[0 1] [0 3] [1 5] [3 5]]}
                "t47"  { "t." "t" "p." 3 ".A" [[0 3] [0 4] [1 3] [1 4]]}
                "t70"  { "t." "t" "p." 3 ".A" [[0 1] [0 2] [1 3] [2 3]]}
                "t627" { "t." "t" "p." 3 ".A" [[0 1] [0 3] [1 2] [2 3]]}
                "t628" { "t." "t" "p." 3 ".A" [[0 2] [0 5] [2 3] [3 5]]}
                "t629" { "t." "t" "p." 3 ".A" [[0 2] [0 4] [2 3] [3 4]]}
                })

(def track-city {
                "t503" { "t." "c" "p." 0 "cA" []}
                "t5"   { "t." "c" "p." 1 "cA" [0 1] "aA" "Z" "vA" 20}
                "t6"   { "t." "c" "p." 1 "cA" [0 2] "aA" "D" "vA" 20}
                "t57"  { "t." "c" "p." 1 "cA" [0 3] "vA" 20}
                "t14"  { "t." "c" "p." 2 "cA" [0 1 3 4] "rA" "BH" "vA" 30}
                "t15"  { "t." "c" "p." 2 "cA" [0 1 2 3] "rA" "BH" "vA" 30}
                "t38"  { "t." "c" "p." 2 "cA" [0 1 3 5] "rA" "BH" "vA" 30}
                "t51"  { "t." "c" "p." 3 "cA" [0 1 2 3 4] "rA" "BH" "vA" 40}})

(def track-bcity {
                "tbig" { "p." 0 "cA" [] "lW" "B" "t." "B"}
                "t148" { "p." 1 "cA" [0 1] "aA" "Z" "lW" "B" "t." "B" "vA" 30}
                "t149" { "p." 1 "cA" [0 2] "aA" "D" "lW" "B" "t." "B" "vA" 30}
                "t150" { "p." 1 "cA" [0 3] "lW" "B" "t." "B" "vA" 30}
                "t179" { "p." 2 "cA" [0 1 2] "lW" "B" "t." "B" "vA" 40}
                "t180" { "p." 2 "cA" [0 1 3] "lW" "B" "t." "B" "vA" 40}
                "t181" { "p." 2 "cA" [0 1 4] "lW" "B" "t." "B" "vA" 40}
                "t182" { "p." 2 "cA" [0 2 4] "lW" "B" "t." "B" "vA" 40}
                "t198" { "p." 3 "cA" [0 1 3 4] "rA" "BH" "lW" "B" "t." "B" "vA" 60}
                "t199" { "p." 3 "cA" [0 1 2 3] "rA" "BH" "lW" "B" "t." "B" "vA" 60}
                "t200" { "p." 3 "cA" [0 1 3 5] "rA" "BH" "lW" "B" "t." "B" "vA" 60}
                "t290" { "p." 4 "cA" [0 1 2 3 4] "rA" "BH" "lW" "B" "t." "B" "vA" 70}})

(def track-dits {
                "t501" { "p." 0 "dA" []}
                "t3"   { "p." 1 "dA" [0 1] "aA" "Z" "vA" 10}
                "t58"  { "p." 1 "dA" [0 2] "aA" "D" "vA" 10}
                "t4"   { "p." 1 "dA" [0 3] "vA" 10}
                "t3g"  { "p." 2 "dA" [0 1] "aA" "Z" "vA" 20}
                "t58g" { "p." 2 "dA" [0 2] "aA" "D" "vA" 20}
                "t4g"  { "p." 2 "dA" [0 3] "vA" 20}
                "t143" { "p." 3 "dA" [0 1 2] "vA" 30}
                "t142" { "p." 3 "dA" [0 1 3] "vA" 30}
                "t141" { "p." 3 "dA" [0 1 4] "vA" 30}
                "t203" { "p." 3 "dA" [0 2 4] "vA" 30}})

(def track-oo {
               "t504" { "t." "OO" "p." 0 "cZ" [] "cU" []}
               "t803" { "t." "OO" "p." 1 "cZ" [0 1] "cU" [] "vZ" 30}
               "t248" { "t." "OO" "p." 1 "cZ" [0 2] "cU" [] "rZ" "N" "vZ" 30}
               "t802" { "t." "OO" "p." 1 "cZ" [0 3] "cU" [] "rZ" "N" "rU" "W" "vZ" 30}
               "t35"  { "t." "OO" "p." 2 "cZ" [0 2] "cU" [1 3] "rZ" "N" "rU" "T" "vZ" 40 "vU" 40}
               "t36"  { "t." "OO" "p." 2 "cZ" [0 2] "cU" [3 5] "rZ" "N" "rU" "T" "vZ" 40 "vU" 40}
               "t54"  { "t." "OO" "p." 2 "cZ" [0 3] "cU" [1 2] "rZ" "N" "aU" "Q" "vZ" 40 "vU" 40}
               "t64"  { "t." "OO" "p." 2 "cZ" [0 3] "cU" [1 5] "rZ" "T" "aU" "X" "vZ" 30 "vU" 40}
               "t65"  { "t." "OO" "p." 2 "cZ" [0 3] "cU" [1 4] "rZ" "N" "aU" "V" "vZ" 40 "vU" 40}
               "t66"  { "t." "OO" "p." 2 "cZ" [0 2] "cU" [3 4] "rZ" "N" "vZ" 40 "vU" 40}
               "t67"  { "t." "OO" "p." 2 "cZ" [0 2] "cU" [4 5] "rZ" "R" "rU" "W" "vZ" 40 "vU" 40}
               "t68"  { "t." "OO" "p." 2 "cZ" [0 1] "cU" [2 3] "rU" "S" "vZ" 40 "vU" 40}
               "t212" { "t." "OO" "p." 2 "cZ" [0 1] "cU" [3 4] "vZ" 40 "vU" 40}
               "t339" { "t." "OO" "p." 3 "cZ" [0 1 3] "cU" [2 4 5] "aU" "W" "vZ" 50 "vU" 50}
               "t804" { "t." "OO" "p." 4 "cZ" [0 1 3 "W"] "cU" [2 4 5] "aU" "W" "vZ" 60 "vU" 60}
               })


(def track-man {"t519" { "t." "M" "p." 0 "cN" [] "cR" [] "cV" []}
                "t394" { "t." "M" "p." 1 "cN" [0 3] "cV" [1 4] "cR" [2 5]}
                "t815" { "t." "M" "p." 2 "cN" [0 1 3] "cV" [1 4 5] "cR" [2 3 5]}
                "t375" { "t." "M" "p." 3 "mNRV" "A" "rA" "CGIM" "cA" [0 1 2 3 4 5]} })

(def track-background {"t505" {"p." -6 ".A" [[0 "N"]]}
                       "t506" {"p." -6 ".A" [[0 "N"] [1 "P"]]}
                       "t507" {"p." -6 ".A" [[0 "N"] [1 "P"] [2 "R"]]}
                       "g7"   {"p." -2 ".A" [[0 1]]}
                       "g624" {"p." -2 ".A" [[0 1] [0 5]]}
                       "ghoriz" {"p." -2 ".A" [[0 5] [2 3] [1 4]]}
                       "gchat" {"p." -2 ".A" [[1 4]] "cR" [1 2 3]}
                       "g809" {"p." -2 "cN" [0 1] "cX" [1 5] "cV" [1 4] "cT" [1 3] "cR" [1 2]}

                       })
(def track-list (merge track-background track-simple track-city track-bcity track-dits track-oo track-man))
