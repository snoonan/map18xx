(ns map18xx.map1820)

(def app-state {
    :rotate 30
    :scale 50
    :tiles [
            { :pos "a7"  :tile "t7"   :orient 2 :color "grey"}
            { :pos "a9"  :tile "t7"   :orient 2 :color "grey"}
            { :pos "a11" :tile "t7"   :orient 2 :color "grey"}
            { :pos "a13" :tile "t506" :orient 2 :label {"d8" "30/30/40/50"} :color "red" }
            { :pos "a15" :tile "t506" :orient 2 :label {"d8" "30/30/40/50"} :color "red" }
            { :pos "b4"  :tile "t505" :orient 1 :label {"d8" "30/40/30/20"} :color "blue" }
            { :pos "b6"  :tile "t503" :orient 0 :overlay {"g11" "twater2"} }
            { :pos "b8"  :tile "t500" :orient 0 :overlay {"g11" "tmountain2"} }
            { :pos "b10" :tile "t500" :orient 0 :overlay {"g11" "tmountain2"} }
            { :pos "b12" :tile "t500" :orient 0 :overlay {"g11" "tmountain2"} }
            { :pos "b14" :tile "t500" :orient 0 }
            { :pos "b16" :tile "t500" :orient 0 }
            { :pos "c3"  :tile "t506" :orient 1 :label {"d8" "Liverpool+20"} :color "blue" }
            { :pos "c5"  :tile "tbig" :orient 0 :overlay {"g11" "twater"} :overlay-punch {"i9" "ttributary0r" }}
            { :pos "c7"  :tile "t504" :orient 0 :overlay {"g11" "tmountain2"} }
            { :pos "c9"  :tile "t500" :orient 0 :overlay {"g11" "tmountain2"} }
            { :pos "c11" :tile "t504" :orient 0 :overlay {"g11" "tmountain2"} }
            { :pos "c13" :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
            { :pos "c15" :tile "t503" :orient 0 :overlay {"g11" "twater"} :overlay-punch {"g15" "ttributary2r" }}
            { :pos "c17" :tile "t505" :orient 4 :label {"d8" "20/30/40/20"} :color "blue" }
            { :pos "d2"  :tile "t506" :orient 1 :label {"d8" "30/40/30/40"} :color "red" }
            { :pos "d4"  :tile "t504" :orient 0 :overlay {"g11" "twater2"} }
            { :pos "d6"  :tile "t519" :orient 0 :overlay {"h9" "tmountain2"} }
            { :pos "d8"  :tile "t500" :orient 0 :overlay {"g11" "tmountain2"} }
            { :pos "d10" :tile "t504" :orient 0 :overlay {"g11" "tmountain2"} }
            { :pos "d12" :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
            { :pos "d14" :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
            { :pos "d16" :tile "tbig" :orient 0 :overlay {"g11" "twater2"} }
            { :pos "d18" :tile "t505" :orient 4 :label {"d8" "20/20/30/50"} :color "blue" }
            { :pos "e1"  :tile "t506" :orient 1 :label {"d8" "30/40/30/40"} :color "red" }
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
            { :pos "g17" :tile "t505" :orient 2 :label {"d8" "30/20/20/30"} :color "blue" }
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
            { :pos "i23" :tile "t505" :orient 4 :label {"d8" "20/50/30/30"} :color "blue" }
            { :pos "j2"  :tile "t507" :orient 0 :label {"d8" "30/30/40/50"} :color "red" }
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
            { :pos "k1"  :tile "t505" :orient 1 :label {"d8" "20/30/40/40"} :color "blue" }
            { :pos "k3"  :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
            { :pos "k5"  :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
            { :pos "k7"  :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
            { :pos "k9"  :tile "t500" :orient 0 }
            { :pos "k11" :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
            { :pos "k13" :tile "t500" :orient 0 }
            { :pos "k15" :tile "t500" :orient 0 }
            { :pos "k17" :tile "t500" :orient 0 }
            { :pos "k19" :tile "t500" :orient 0 }
            { :pos "k21" :tile "t505" :orient 4 :label {"d8" "10/20/40/40"} :color "blue" }
            { :pos "l2"  :tile "t500" :orient 0 }
            { :pos "l4"  :tile "t500" :orient 0 }
            { :pos "l6"  :tile "t500" :orient 0 }
            { :pos "l8"  :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
            { :pos "l10" :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
            { :pos "l12" :tile "t500" :orient 0 }
            { :pos "l14" :tile "t500" :orient 0 }
            { :pos "l16" :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
            { :pos "l18" :tile "t500" :orient 0 :overlay {"g11" "twater"} :overlay-punch {"a9" "ttributary0r"} }
            { :pos "l20" :tile "t505" :orient 4 :label {"d8" "30/40/50/60"} :color "blue" }
            { :pos "m1"  :tile "t507" :orient 0 :label {"d8" "30/30/40/50"} :color "red" }
            { :pos "m3"  :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
            { :pos "m5"  :tile "t500" :orient 0 }
            { :pos "m7"  :tile "t500" :orient 0 }
            { :pos "m9"  :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
            { :pos "m11" :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
            { :pos "m13" :tile "g809" :orient 0 }
            { :pos "m15" :tile "gchat" :orient 0 }
            { :pos "m17" :tile "ghoriz" :orient 0 }
            { :pos "m19" :tile "t505" :orient 4 :label {"d8" "40/60/80/100"} :color "blue" }
            { :pos "n2"  :tile "t500" :orient 0 }
            { :pos "n4"  :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
            { :pos "n6"  :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
            { :pos "n8"  :tile "t500" :orient 0 :overlay {"g11" "twater2"} }
            { :pos "n10" :tile "t500" :orient 0 }
            { :pos "n12" :tile "t500" :orient 0 }
            { :pos "n14" :tile "t500" :orient 0 }
            { :pos "n16" :tile "t500" :orient 0 }
            { :pos "n18" :tile "t503" :orient 0 }
            { :pos "n20" :tile "t505" :orient 4 :label {"d8" "20/40/30/30"} :color "blue" }
            { :pos "o1"  :tile "t7"   :orient 0 :color "grey"}
            { :pos "o3"  :tile "t500" :orient 0 }
            { :pos "o5"  :tile "t505" :orient 0 :label {"d8" "30/40/50/60"} :color "blue" }
            { :pos "o9"  :tile "t505" :orient 5 :label {"d8" "20/30/30/20"} :color "blue" }
            { :pos "o11" :tile "t500" :orient 0 }
            { :pos "o13" :tile "t500" :orient 0 }
            { :pos "o15" :tile "t500" :orient 0 }
            { :pos "o17" :tile "tbig" :orient 0 :overlay {"g11" "twater2"} }
            { :pos "o19" :tile "t505" :orient 4 :label {"d8" "20/30/40/50"} :color "blue" }
            { :pos "p2"  :tile "t505" :orient 0 :label {"d8" "20/30/40/20"} :color "blue" }
            { :pos "p4"  :tile "t505" :orient 5 :label {"d8" "20/30/30/20"} :color "blue" }
            { :pos "p12" :tile "t505" :orient 0 :label {"d8" "20/30/40/20"} :color "blue" }
            { :pos "p16" :tile "t505" :orient 0 :label {"d8" "20/30/30/40"} :color "blue" }
            ]
  :companies [
              {:name "red" :token "red"}
              {:name "green" :token "green"}
              {:name "blue" :token "blue"}
              {:name "yellow" :token "yellow"}
              {:name "magenta" :token "magenta"}
              ]})


(def track-list {
                 ; manchester
                "t519" { "t." ["M" "e15"] "p." 0 "cc9" [] "cf12" [] "cf6" []}
                "t394" { "t." ["M" "e15"] "p." 1 "cc9" [0 3] "cf6" [1 4] "cf12" [2 5]}
                "t815" { "t." ["M" "e15"] "p." 2 "cc9" [0 1 3] "cf6" [1 4 5] "cf12" [2 3 5]}
                "t375" { "t." ["M" "e15"] "p." 3 "me9" ["c9" "f6" "f12"] "re9" ["f6" "d8" "f10" "d12"] "ce9" [0 1 2 3 4 5]}
                ; london docks
                "ghoriz" {"p." -2 ".A" [[0 5] [2 3] [1 4]]}
                "gchat" {"p." -2 ".A" [[1 4]] "cg11" [1 2 3]}
                ;london
                "g809" {"p." -2 "cb10" [0 1] "cc5" [1 5] "cf6" [1 4] "ch9" [1 3] "cf14" [1 2]} })
