(ns map18xx.tileset)

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

(def track-list {
  ;track-simple
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

;track-city
                "t503" { "p." 0 "ce9" []}
                "t5"   { "p." 1 "ce9" [0 1] "ae9" ["c11"] "ve9" 20}
                "t6"   { "p." 1 "ce9" [0 2] "ae9" ["d10"] "ve9" 20}
                "t57"  { "p." 1 "ce9" [0 3] "ve9" 20}
                "t14"  { "p." 2 "ce9" [0 1 3 4] "re9" ["d9" "f9"] "ve9" 30}
                "t15"  { "p." 2 "ce9" [0 1 2 3] "re9" ["d9" "f9"] "ve9" 30}
                "t38"  { "p." 2 "ce9" [0 1 3 5] "re9" ["d9" "f9"] "ve9" 30}
                "t51"  { "p." 3 "ce9" [0 1 2 3 4] "re9" ["d9" "f9"] "ve9" 40}

                "t12"  { "p." 2 "ce9" [0 1 2] "ve9" 30}
;B cities
                "tbig" { "t." ["B" "f6"] "p." 0 "ce9" [] }
                "t148" { "t." ["B" "f6"] "p." 1 "ce9" [0 1] "ae9" ["c11"] "ve9" 30}
                "t149" { "t." ["B" "f6"] "p." 1 "ce9" [0 2] "ae9" ["d10"] "ve9" 30}
                "t150" { "t." ["B" "f6"] "p." 1 "ce9" [0 3] "ve9" 30}
                "t179" { "t." ["B" "f6"] "p." 2 "ce9" [0 1 2] "ve9" 40}
                "t180" { "t." ["B" "f6"] "p." 2 "ce9" [0 1 3] "ve9" 40}
                "t181" { "t." ["B" "f6"] "p." 2 "ce9" [0 1 4] "ve9" 40}
                "t182" { "t." ["B" "f6"] "p." 2 "ce9" [0 2 4] "ve9" 40}
                "t198" { "t." ["B" "f6"] "p." 3 "ce9" [0 1 3 4] "re9" ["d9" "f9"] "ve9" 60}
                "t199" { "t." ["B" "f6"] "p." 3 "ce9" [0 1 2 3] "re9" ["d9" "f9"] "ve9" 60}
                "t200" { "t." ["B" "f6"] "p." 3 "ce9" [0 1 3 5] "re9" ["d9" "f9"] "ve9" 60}
                "t290" { "t." ["B" "f6"] "p." 4 "ce9" [0 1 2 3 4] "re9" ["d9" "f9"] "ve9" 70}
;track-dits
                "t501" { "p." 0 "de9" []}
                "t3"   { "p." 1 "de9" [0 1] "ae9" ["c11"] "ve9" 10}
                "t58"  { "p." 1 "de9" [0 2] "ae9" ["d10"] "ve9" 10}
                "t4"   { "p." 1 "de9" [0 3] "ve9" 10}
                "t3g"  { "p." 2 "de9" [0 1] "ae9" ["c13"] "ve9" 20}
                "t58g" { "p." 2 "de9" [0 2] "ae9" ["d10"] "ve9" 20}
                "t4g"  { "p." 2 "de9" [0 3] "ve9" 20}
                "t143" { "p." 3 "de9" [0 1 2] "ve9" 30}
                "t142" { "p." 3 "de9" [0 1 3] "ve9" 30}
                "t141" { "p." 3 "de9" [0 1 4] "ve9" 30}
                "t203" { "p." 3 "de9" [0 2 4] "ve9" 30}

;track-oo
               "t504" { "t." ["OO" "c7"] "p." 0 "cc11" [] "cg7" []}
               "t803" { "t." ["OO" "c7"] "p." 1 "cc11" [0 1] "cg7" [] "vc11" 30}
               "t248" { "t." ["OO" "c7"] "p." 1 "cc11" [0 2] "cg7" [] "rc11" ["b9"] "vc11" 30}
               "t802" { "t." ["OO" "c7"] "p." 1 "cc11" [0 3] "cg7" [] "rc11" ["b9"] "rg7" ["e5"] "vc11" 30}
               "t35"  { "t." ["OO" "c7"] "p." 2 "cc11" [0 2] "cg7" [1 3] "rc11" ["b9"] "rg7" ["h9"] "vc11" 40 "vg7" 40}
               "t36"  { "t." ["OO" "c7"] "p." 2 "cc11" [0 2] "cg7" [3 5] "rc11" ["b9"] "rg7" ["h9"] "vc11" 40 "vg7" 40}
               "t54"  { "t." ["OO" "c7"] "p." 2 "cc11" [0 3] "cg7" [1 2] "rc11" ["b9"] "ag7" ["e13"] "vc11" 40 "vg7" 40}
               "t64"  { "t." ["OO" "c7"] "p." 2 "cc11" [0 3] "cg7" [1 5] "rc11" ["h9"] "ag7" ["d6"] "vc11" 40 "vg7" 40}
               "t65"  { "t." ["OO" "c7"] "p." 2 "cc11" [0 3] "cg7" [1 4] "rc11" ["b9"] "ag7" ["f6"] "vc11" 40 "vg7" 40}
               "t66"  { "t." ["OO" "c7"] "p." 2 "cc11" [0 2] "cg7" [3 4] "rc11" ["b9"] "vg11" 40 "vg7" 40}
               "t67"  { "t." ["OO" "c7"] "p." 2 "cc11" [0 2] "cg7" [4 5] "rc11" ["f12"] "rg7" ["e5"] "vc11" 40 "vg7" 40}
               "t68"  { "t." ["OO" "c7"] "p." 2 "cc11" [0 1] "cg7" [2 3] "rg7" ["g11"] "vc11" 40 "vg7" 40}
               "t212" { "t." ["OO" "c7"] "p." 2 "cc11" [0 1] "cg7" [3 4] "vc11" 40 "vg7" 40}
               "t339" { "t." ["OO" "c7"] "p." 3 "cc11" [0 1 3] "cg7" [2 4 5] "ag7" ["e5"] "vc11" 50 "vg7" 50}
               "t804" { "t." ["OO" "c7"] "p." 4 "cc11" [0 1 3 "e5"] "cg7" [2 4 5] "ag7" ["e5"] "vc11" 60 "vg7" 60}

;track-background


               "t505" {"p." -6 ".e9" [[0 "c9"]]}
               "t506" {"p." -6 ".e9" [[0 "c9"] [1 "d12"]]}
               "t507" {"p." -6 ".e9" [[0 "c9"] [1 "d12"] [2 "f12"]]}
               })
