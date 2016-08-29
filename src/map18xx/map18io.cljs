(ns map18xx.map18io)

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
  (color-offboard ["f2" "e5" "d8" "c11"] ["yellow" "green" "brown"] values))

(def app-state {
    :rotate 30
    :scale 50
    :tiles [
            { :pos "a5"  :tile "t7"   :orient 2 :color "red" :overlay-punch {"g5" "whiteanchor" "d14" "pinkanchor"} :label (color-offboard-line ["20" "40" "50"])}
            { :pos "a7"  :tile "t7"   :orient 2 :color "grey"}
            { :pos "a21" :tile "t506" :orient 2 :color "red" :overlay-punch {"g5" "pinkanchor" "d14" "whiteanchor"} :label (merge (color-offboard-line ["20" "40" "60"]) {["f8" "e11"] "East"})}
            { :pos "b2"  :tile "t500" :orient 0 }
            { :pos "b4"  :tile "t500" :orient 0 }
            { :pos "b6"  :tile "t500" :orient 0 }
            { :pos "b8"  :tile "ttoledo" :orient 0 :overlay-punch {"e5" "pinkanchor"}}
            { :pos "b18" :tile "t501" :orient 0 :overlay-punch {"e5" "pinkanchor"}}
            { :pos "b20" :tile "t503" :orient 0 :overlay-punch {"e5" "pinkanchor"}}
            { :pos "b22" :tile "t39"  :orient 3 :color "grey"}
            { :pos "c1"  :tile "t507" :orient 0 :label (merge (color-offboard-line ["20" "50" "60"]) { ["d4" "c7"] "West"}) :color "red" }
            { :pos "c3"  :tile "t500" :orient 0 }
            { :pos "c5"  :tile "t500" :orient 0 }
            { :pos "c7"  :tile "t500" :orient 0 }
            { :pos "c9"  :tile "t500" :orient 0 }
            { :pos "c11" :tile "tsandusky" :orient 0 :overlay-punch {"c7" "pinkanchor"}}
            { :pos "c13" :tile "t500" :orient 0 }
            { :pos "c15" :tile "t501" :orient 0 :overlay-punch {"g11" "pinkanchor"}}
            { :pos "c17" :tile "t6Y"  :orient 4 :overlay-punch {"g11" "pinkanchor"}}
            { :pos "c19" :tile "t500" :orient 0 }
            { :pos "c21" :tile "t500" :orient 0 }
            { :pos "d2"  :tile "t500" :orient 0 }
            { :pos "d4"  :tile "t501" :orient 0 }
            { :pos "d6"  :tile "t503" :orient 0 }
            { :pos "d8"  :tile "t500" :orient 0 }
            { :pos "d10" :tile "t500" :orient 0 }
            { :pos "d12" :tile "t501" :orient 0 }
            { :pos "d14" :tile "t500" :orient 0 }
            { :pos "d16" :tile "thudson" :orient 0 }
            { :pos "d18" :tile "t503" :orient 0 :overlay {"g9" "twater2"} }
            { :pos "d20" :tile "t503" :orient 0 :overlay {"g9" "twater2"} }
            { :pos "e3"  :tile "t500" :orient 0 }
            { :pos "e5"  :tile "t500" :orient 0 }
            { :pos "e7"  :tile "t500" :orient 0 }
            { :pos "e9"  :tile "t501" :orient 0 }
            { :pos "e11" :tile "t500" :orient 0 }
            { :pos "e13" :tile "t500" :orient 0 }
            { :pos "e15" :tile "t500" :orient 0 }
            { :pos "e17" :tile "t503" :orient 0 }
            { :pos "e19" :tile "t501" :orient 0 }
            { :pos "e21" :tile "t500" :orient 0 }
            { :pos "f2"  :tile "t12"  :orient 0 :color "grey" :label {["e3" "d6"] "West"}}
            { :pos "f4"  :tile "t501" :orient 0 }
            { :pos "f6"  :tile "t500" :orient 0 }
            { :pos "f8"  :tile "t500" :orient 0 }
            { :pos "f10" :tile "t501" :orient 0 }
            { :pos "f12" :tile "tmans" :orient 0 }
            { :pos "f14" :tile "t500" :orient 0 }
            { :pos "f16" :tile "t500" :orient 0 }
            { :pos "f18" :tile "t500" :orient 0 }
            { :pos "f20" :tile "t503" :orient 0 :overlay {"g9" "tmountain2"} }
            { :pos "g3"  :tile "t500" :orient 0 }
            { :pos "g5"  :tile "t503" :orient 0 }
            { :pos "g7"  :tile "t500" :orient 0 }
            { :pos "g9"  :tile "t500" :orient 0 }
            { :pos "g11" :tile "t500" :orient 0 }
            { :pos "g13" :tile "t500" :orient 0 }
            { :pos "g15" :tile "t501" :orient 0 }
            { :pos "g17" :tile "t500" :orient 0 }
            { :pos "g19" :tile "t500" :orient 0 :overlay {"e9" "tmountain2"}}
            { :pos "g21" :tile "t507" :orient 3 :color "red" :overlay-punch {"f12" "greenanchor"} :label (merge (color-offboard-line ["30" "40" "50"]) {["c11" "b14"] "East"})}
            { :pos "h2"  :tile "t501" :orient 0 }
            { :pos "h4"  :tile "t500" :orient 0 }
            { :pos "h6"  :tile "t501" :orient 0 }
            { :pos "h8"  :tile "t500" :orient 0 }
            { :pos "h10" :tile "t6Y"  :orient 1 }
            { :pos "h12" :tile "t500" :orient 0 }
            { :pos "h14" :tile "t500" :orient 0 }
            { :pos "h16" :tile "t503" :orient 0 }
            { :pos "h18" :tile "t500" :orient 0 }
            { :pos "h20" :tile "t500" :orient 0 :overlay {"e9" "tmountain2"}}
            { :pos "i1"  :tile "t39"  :orient 0 :color "grey"}
            { :pos "i3"  :tile "t500" :orient 0 }
            { :pos "i5"  :tile "t503" :orient 0 :overlay {"g9" "twater2"}}
            { :pos "i7"  :tile "t503" :orient 0 :overlay {"g9" "twater2"}}
            { :pos "i9"  :tile "t500" :orient 0 }
            { :pos "i11" :tile "t500" :orient 0 :overlay {"e9" "twater2"}}
            { :pos "i13" :tile "t500" :orient 0 }
            { :pos "i15" :tile "t500" :orient 0 }
            { :pos "i17" :tile "t500" :orient 0 }
            { :pos "i19" :tile "t500" :orient 0 :overlay {"e9" "twater2"}}
            { :pos "j2"  :tile "t501" :orient 0 }
            { :pos "j4"  :tile "tfranklin" :orient 0 }
            { :pos "j6"  :tile "tmorrow" :orient 0 }
            { :pos "j8"  :tile "t500" :orient 0 }
            { :pos "j10" :tile "t503" :orient 0 }
            { :pos "j12" :tile "t500" :orient 0 }
            { :pos "j14" :tile "t501" :orient 0 }
            { :pos "j16" :tile "t500" :orient 0 }
            { :pos "j18" :tile "t503" :orient 0 :overlay {"g9" "tmountain2"} :overlay-punch {"e5" "greenanchor"}}
            { :pos "k1"  :tile "t505" :orient 1 :color "red" :overlay-punch {"g7" "greenanchor"} :label (merge (color-offboard-line ["30" "50" "60"]) {["g11" "f14"] "West"})}
            { :pos "k3"  :tile "tcincin" :orient 0 :overlay-punch {"h6" "greenanchor"}}
            { :pos "k5"  :tile "t500" :orient 0 }
            { :pos "k7"  :tile "t500" :orient 0 }
            { :pos "k9"  :tile "t500" :orient 0 }
            { :pos "k11" :tile "t500" :orient 0 :overlay {"d9" "twater2"}}
            { :pos "k13" :tile "t500" :orient 0 }
            { :pos "k15" :tile "t500" :orient 0 }
            { :pos "k17" :tile "t5TP" :orient 0 :overlay-punch {"g7" "greenanchor" "g11" "whiteanchor"}}
            { :pos "k19" :tile "t506" :orient 4 :color "red" :overlay-punch {"g9" "greenanchor"} :label (color-offboard-line ["30" "40" "50"])}
            { :pos "l4"  :tile "t500" :orient 0 }
            { :pos "l6"  :tile "t500" :orient 0 }
            { :pos "l8"  :tile "t500" :orient 0 }
            { :pos "l10" :tile "t15"  :orient 4 :color "grey" :overlay-punch {"h10" "greenanchor" "g13" "whiteanchor"}}
            { :pos "l12" :tile "t500" :orient 0 }
            { :pos "l14" :tile "t501" :orient 0 :overlay-punch {"g11" "greenanchor"}}
            ]
  :companies [
              {:name "red" :token "red"}
              {:name "green" :token "green"}
              {:name "blue" :token "blue"}
              {:name "yellow" :token "yellow"}
              {:name "magenta" :token "magenta"}
              ]})

(def transform-track {} )
(def track-list {
  ;track-simple
                "t500" {"#." 0}
                "t7"   {"#." 5}
                "t8"   {"#." 5}
                "t9"   {"#." 5}
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
                "t503" {"#." 5}
                "t5"   {"#." 5}
                "t6"   {"#." 5}
                "t57"  {"#." 5}
                "t14"  {"#." 5}
                "t15"  {"#." 5}
                "t38"  {"#." 5}
                "t51"  {"#." 5}
;track-dits
                "t501" {"#." 5}
                "t3"   {"#." 5}
                "t58"  {"#." 5}
                "t4"   {"#." 5}
                "t143" {"#." 5}
                "t142" {"#." 5}
                "t141" {"#." 5}
                "t203" {"#." 5}

; map and special tiles
                "t12"  {}
                "t505" {}
                "t506" {}
                "t507" {}
                 "ttoledo"   {"p." 1  "t." ["TP" "c11"] "cf12" [2 5] "cg7" [3 5] "vf12" 20 "vg7" 20}
                 "thudson"   {"p." 3  "cf12" [2 "e9"] "vf12" 20 ".e9" [[0 "e9"] [1 "e9"] [3 "e9"] [4 "e9"]]}
                 "tsandusky" {"p." 2  "cg11" [2 3] "cd12" [1] "cf6" [4] "vg11" 20 "vd12" 20 "vf6" 20}
                 "tfranklin" {"p." 3  "cd6" [5 "e9"] "vd6" 10 ".e9" [[0 "e9"] [2 "e9"] [3 "e9"]]}
                 "tmorrow"   {"p." -2 "cg9" [3 "e9"] "vg9" 10 ".e9" [[0 "e9"] [1 "e9"] [2 "e9"] [5 "e9"]]}
                 "tcincin"   {"p." 1  "t." ["C" "h10"] "cd5" [4 5] "cc8" [0 4] "cd12" [1 4] "cf12" [2 4] "vd5" 20 "vc8" 20 "vd12" 20 "vf12" 20}
                 "tcincinG"  {"p." 2  "t." ["C" "h10"] "cd5" [4 5] "cc8" [0 4] "cd12" [1 4] "cf12" [2 4] "vd5" 30 "vc8" 30 "vd12" 30 "vf12" 30}
                 "tmans"     {"p." -2 "cf6" [3 4 5] "cd12" [0 1 2] "vf6" 30 "vd12" 20}
                 "t6Y"       {"p." 1  "ce9" [0 2] "re9" ["d10"] "t." ["Y" "h12"]}
                 "t5TP"      {"p." 2  "ce9" [0 1] "re9" ["c11"] "t." ["TP" "f6"]}})
