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
            ]})
