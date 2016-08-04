(ns map18xx.upgrade )

(def upgrade-simple {
                  [0 1] ["t7" 0]
                  [1 2] ["t7" 1]
                  [2 3] ["t7" 2]
                  [3 4] ["t7" 3]
                  [4 5] ["t7" 4]
                  [0 5] ["t7" 5]
                  [0 2] ["t8" 0]
                  [1 3] ["t8" 1]
                  [2 4] ["t8" 2]
                  [3 5] ["t8" 3]
                  [0 4] ["t8" 4]
                  [1 5] ["t8" 5]
                  [0 3] ["t9" 0]
                  [1 4] ["t9" 1]
                  [2 5] ["t9" 2]
                  [0 1 0 2] ["t29" 0]
                  [0 1 0 3] ["t27" 0]
                  [0 1 0 4] ["t30" 0]
                  [0 1 0 5] ["t624" 0]
                  [0 1 1 2] ["t624" 1]
                  [0 1 1 3] ["t31" 1]
                  [0 1 1 4] ["t26" 1]
                  [0 1 1 5] ["t28" 5]
                  [0 1 2 3] ["t625" 0]
                  [0 1 2 4] ["t22" 2]
                  [0 1 2 5] ["t18" 5]
                  [0 1 3 4] ["t626" 0]
                  [0 1 3 5] ["t21" 3]
                  [0 1 4 5] ["t625" 4]
                  [0 2 0 1] ["t29" 0]
                  [0 2 0 3] ["t24" 0]
                  [0 2 0 4] ["t25" 0]
                  [0 2 0 5] ["t31" 0]
                  [0 2 1 2] ["t28" 0]
                  [0 2 1 3] ["t16" 0]
                  [0 2 1 4] ["t19" 1]
                  [0 2 1 5] ["t16" 5]
                  [0 2 2 3] ["t30" 2]
                  [0 2 2 4] ["t25" 2]
                  [0 2 2 5] ["t23" 2]
                  [0 2 3 4] ["t21" 0]
                  [0 2 3 5] ["t17" 0]
                  [0 2 4 5] ["t22" 0]
                  [0 3 0 1] ["t27" 0]
                  [0 3 0 2] ["t24" 0]
                  [0 3 0 4] ["t23" 0]
                  [0 3 0 5] ["t26" 0]
                  [0 3 1 2] ["t18" 0]
                  [0 3 1 3] ["t23" 3]
                  [0 3 1 4] ["t20" 0]
                  [0 3 1 5] ["t19" 0]
                  [0 3 2 3] ["t26" 3]
                  [0 3 2 4] ["t19" 3]
                  [0 3 2 5] ["t20" 5]
                  [0 3 3 4] ["t27" 3]
                  [0 3 3 5] ["t24" 3]
                  [0 3 4 5] ["t18" 3]
                  [0 2 1 3 0 1] ["t70" 0]
                  [0 2 1 3 0 3] ["t43" 0]
                  [0 2 1 3 1 2] ["t43" 0]
                  [0 2 1 3 2 3] ["t70" 0]
                  [0 2 3 5 0 3] ["t47" 5]
                  [0 2 3 5 0 5] ["t628" 0]
                  [0 2 3 5 2 3] ["t628" 0]
                  [0 2 3 5 2 5] ["t47" 5]
                  [0 3 1 2 0 1] ["t627" 0]
                  [0 3 1 2 0 2] ["t43" 0]
                  [0 3 1 2 1 3] ["t43" 0]
                  [0 3 1 2 2 3] ["t627" 0]
                  [0 3 1 5 0 1] ["t46" 0]
                  [0 3 1 5 0 5] ["t45" 0]
                  [0 3 1 5 1 3] ["t45" 0]
                  [0 3 1 5 3 5] ["t46" 0]
                  [0 3 1 4 0 1] ["t44" 0]
                  [0 3 1 4 0 4] ["t47" 0]
                  [0 3 1 4 1 3] ["t47" 0]
                  [0 3 1 4 3 4] ["t44" 0]
                  [0 2 3 4 0 3] ["t46" 3]
                  [0 2 3 4 0 4] ["t629" 0]
                  [0 2 3 4 2 3] ["t629" 0]
                  [0 2 3 4 2 4] ["t46" 3]
                  [0 2 4 5 0 4] ["t45" 5]
                  [0 2 4 5 0 5] ["t629" 2]
                  [0 2 4 5 2 4] ["t629" 2]
                  [0 2 4 5 2 5] ["t45" 5]
                  [0 3 0 4 3 4] ["t41" 0]
                  [0 3 0 4 1 3] ["t47" 0]
                  [0 3 0 4 1 4] ["t47" 0]
                  [0 3 0 4 2 3] ["t45" 2]
                  [0 3 0 4 2 4] ["t45" 2]
                  [0 3 0 4 3 5] ["t43" 3]
                  [0 3 0 4 4 5] ["t43" 3]
                  [0 2 0 3 2 3] ["t42" 0]
                  [0 2 0 3 1 2] ["t43" 0]
                  [0 2 0 3 1 3] ["t43" 0]
                  [0 2 0 3 2 4] ["t46" 3]
                  [0 2 0 3 3 4] ["t46" 3]
                  [0 2 0 3 2 5] ["t47" 5]
                  [0 2 0 3 3 5] ["t47" 5]
                  [0 2 0 4 2 4] ["t40" 0]
                  [0 2 0 4 1 2] ["t46" 1]
                  [0 2 0 4 1 4] ["t46" 1]
                  [0 2 0 4 2 3] ["t629" 0]
                  [0 2 0 4 3 4] ["t629" 0]
                  [0 2 0 4 2 5] ["t45" 5]
                  [0 2 0 4 4 5] ["t45" 5]
                  [0 3 0 5 3 5] ["t42" 3]
                  [0 3 0 5 1 3] ["t45" 0]
                  [0 3 0 5 1 5] ["t45" 0]
                  [0 3 0 5 2 3] ["t44" 5]
                  [0 3 0 5 2 5] ["t44" 5]
                  [0 3 0 5 3 4] ["t627" 3]
                  [0 3 0 5 4 5] ["t627" 3]
                  [0 1 0 3 1 3] ["t41" 3]
                  [0 1 0 3 1 2] ["t627" 0]
                  [0 1 0 3 2 3] ["t627" 0]
                  [0 1 0 3 1 4] ["t44" 3]
                  [0 1 0 3 3 4] ["t44" 3]
                  [0 1 0 3 1 5] ["t46" 0]
                  [0 1 0 3 3 5] ["t46" 0]
                  [0 2 1 2 0 1] ["t39" 0]
                  [0 2 1 2 0 3] ["t43" 0]
                  [0 2 1 2 1 3] ["t43" 0]
                  [0 2 1 2 0 4] ["t45" 3]
                  [0 2 1 2 1 4] ["t45" 3]
                  [0 2 1 2 0 5] ["t628" 0]
                  [0 2 1 2 1 5] ["t628" 0]
                  [0 1 0 2 1 2] ["t39" 0]
                  [0 1 0 2 1 3] ["t70" 0]
                  [0 1 0 2 2 3] ["t70" 0]
                  [0 1 0 2 1 4] ["t45" 1]
                  [0 1 0 2 2 4] ["t45" 1]
                  [0 1 0 2 1 5] ["t43" 5]
                  [0 1 0 2 2 5] ["t43" 5]
                  [0 1 0 4 1 4] ["t41" 3]
                  [0 1 0 4 1 2] ["t629" 4]
                  [0 1 0 4 2 4] ["t629" 4]
                  [0 1 0 4 1 3] ["t628" 1]
                  [0 1 0 4 3 4] ["t628" 1]
                  [0 1 0 4 1 5] ["t70" 4]
                  [0 1 0 4 4 5] ["t70" 4]
                  [0 2 0 5 2 5] ["t41" 5]
                  [0 2 0 5 1 2] ["t70" 5]
                  [0 2 0 5 1 5] ["t70" 2]
                  [0 2 0 5 2 3] ["t628" 0]
                  [0 2 0 5 3 5] ["t628" 0]
                  [0 2 0 5 2 4] ["t629" 2]
                  [0 2 0 5 4 5] ["t629" 2]
                  [0 1 0 5 1 5] ["t39" 5]
                  [0 1 0 5 1 2] ["t627" 5]
                  [0 1 0 5 2 5] ["t627" 5]
                  [0 1 0 5 1 3] ["t629" 3]
                  [0 1 0 5 3 5] ["t629" 3]
                  [0 1 0 5 1 4] ["t627" 4]
                  [0 1 0 5 4 5] ["t627" 4]
                  [0 1 2 3 0 2] ["t70" 0]
                  [0 1 2 3 0 3] ["t627" 0]
                  [0 1 2 3 1 2] ["t627" 0]
                  [0 1 2 3 1 3] ["t70" 0]
                  [0 1 3 4 0 3] ["t44" 0]
                  [0 1 3 4 0 4] ["t47" 0]
                  [0 1 3 4 1 3] ["t47" 0]
                  [0 1 3 4 1 4] ["t44" 0]
                  })

(def track-simple {
                "t500" []
                "t7"   [0 1]
                "t8"   [0 2]
                "t9"   [0 3]
                "t16"  [0 2 1 3]
                "t17"  [0 2 3 5]
                "t18"  [0 3 1 2]
                "t19"  [0 3 1 5]
                "t20"  [0 3 1 4]
                "t21"  [0 2 3 4]
                "t22"  [0 2 4 5]
                "t23"  [0 3 0 4]
                "t24"  [0 2 0 3]
                "t25"  [0 2 0 4]
                "t26"  [0 3 0 5]
                "t27"  [0 1 0 3]
                "t28"  [0 2 1 2]
                "t29"  [0 1 0 2]
                "t30"  [0 1 0 4]
                "t31"  [0 2 0 5]
                "t624" [0 1 0 5]
                "t625" [0 1 2 3]
                "t626" [0 1 3 4]
                "t39"  [0 1 0 2 1 2]
                "t40"  [0 2 2 4 0 4]
                "t41"  [0 3 0 4 3 4]
                "t42"  [0 2 0 3 2 3]
                "t43"  [0 2 0 3 1 3 1 2]
                "t44"  [0 3 1 4 0 1 3 4]
                "t45"  [0 3 1 5 1 3 0 5]
                "t46"  [0 3 1 5 0 1 3 5]
                "t47"  [0 3 1 4 1 3 0 4]
                "t70"  [0 2 1 3 0 1 2 3]
                "t627"  [0 3 0 1 1 2 2 3]
                "t628"  [0 2 2 3 3 5 0 5]
                "t629"  [0 2 2 3 3 4 0 4]
                })

(def upgrade-city {
                  [0 1] ["t5" 0]
                  [1 2] ["t5" 1]
                  [2 3] ["t5" 2]
                  [3 4] ["t5" 3]
                  [4 5] ["t5" 4]
                  [0 5] ["t5" 5]
                  [0 2] ["t6" 0]
                  [1 3] ["t6" 1]
                  [2 4] ["t6" 2]
                  [3 5] ["t6" 3]
                  [0 4] ["t6" 4]
                  [1 5] ["t6" 5]
                  [0 3] ["t57" 0]
                  [1 4] ["t57" 1]
                  [2 5] ["t57" 2]
                  [0 1 2 3] ["t15" 0]
                  [0 1 2 4] ["t38" 1]
                  [0 1 2 5] ["t15" 5]
                  [0 1 3 4] ["t14" 0]
                  [0 1 3 5] ["t38" 0]
                  [0 1 4 5] ["t15" 2]
                  [0 2 1 3] ["t15" 0]
                  [0 2 1 4] ["t38" 1]
                  [0 2 1 5] ["t15" 5]
                  [0 2 3 4] ["t38" 3]
                  [0 2 3 5] ["t14" 5]
                  [0 2 4 5] ["t38" 5]
                  [0 3 1 2] ["t15" 0]
                  [0 3 1 4] ["t14" 0]
                  [0 3 1 5] ["t38" 0]
                  [0 3 2 4] ["t38" 3]
                  [0 3 2 5] ["t14" 5]
                  [0 3 4 5] ["t15" 3]
                  [0 1 2 3 4] ["t51" 0]
                  [1 2 3 4 5] ["t51" 1]
                  [0 2 3 4 5] ["t51" 2]
                  [0 1 3 4 5] ["t51" 3]
                  [0 1 2 4 5] ["t51" 4]
                  [0 1 2 3 5] ["t51" 5]
                  })


(def track-city {
                "t503" []
                "t5"  [0 1]
                "t6"  [0 2]
                "t57" [0 3]
                "t14" [0 1 3 4]
                "t15" [0 1 2 3]
                "t38" [0 1 3 5]
                "t51" [0 1 4 3 5]})

(comment 179 -183 are guesses, they may be shuffeled.)
(def upgrade-bcity {
                  [0 1] ["t148" 0]
                  [1 2] ["t148" 1]
                  [2 3] ["t148" 2]
                  [3 4] ["t148" 3]
                  [4 5] ["t148" 4]
                  [0 5] ["t148" 5]
                  [0 2] ["t149" 0]
                  [1 3] ["t149" 1]
                  [2 4] ["t149" 2]
                  [3 5] ["t149" 3]
                  [0 4] ["t149" 4]
                  [1 5] ["t149" 5]
                  [0 3] ["t150" 0]
                  [1 4] ["t150" 1]
                  [2 5] ["t150" 2]
                  [0 1 2] ["t179" 0]
                  [0 1 3] ["t180" 0]
                  [0 1 4] ["t181" 0]
                  [0 1 5] ["t179" 5]
                  [0 2 1] ["t179" 0]
                  [0 2 3] ["t181" 2]
                  [0 2 4] ["t182" 0]
                  [0 2 5] ["t180" 5]
                  [0 3 1] ["t180" 0]
                  [0 3 2] ["t181" 2]
                  [0 3 4] ["t180" 3]
                  [0 3 5] ["t181" 5]
                  [0 1 2 3] ["t199" 0]
                  [0 1 2 4] ["t200" 1]
                  [0 1 2 5] ["t199" 5]
                  [0 1 3 4] ["t198" 0]
                  [0 1 4 3] ["t198" 0]  ;; Gack! three leg to 4 has fun here. Rest are just fine order wise.
                  [0 1 3 5] ["t200" 0]
                  [0 1 4 5] ["t199" 2]
                  [0 2 1 3] ["t199" 0]
                  [0 2 1 4] ["t200" 1]
                  [0 2 1 5] ["t199" 5]
                  [0 2 3 4] ["t200" 3]
                  [0 2 3 5] ["t198" 5]
                  [0 2 4 5] ["t200" 5]
                  [0 3 1 2] ["t199" 0]
                  [0 3 1 4] ["t198" 0]
                  [0 3 1 5] ["t200" 0]
                  [0 3 2 4] ["t200" 3]
                  [0 3 2 5] ["t198" 5]
                  [0 3 4 5] ["t199" 3]
                  [0 1 3 4 2] ["t290" 5]
                  [0 1 3 4 5] ["t290" 2]
                  [0 1 2 3 4] ["t290" 5]
                  [0 1 2 3 5] ["t290" 4]
                  [0 1 3 5 2] ["t290" 4]
                  [0 1 3 5 4] ["t290" 2]
                  })


(def track-bcity {
                "tbig" []
                "t148" [0 1]
                "t149" [0 2]
                "t150" [0 3]
                "t179" [0 1 2]
                "t180" [0 1 3]
                "t181" [0 1 4]
                "t182" [0 2 4]
                "t198" [0 1 3 4]
                "t199" [0 1 2 3]
                "t200" [0 1 3 5]})

(def upgrade-dits {
                  [0 1] ["t3" 0]
                  [1 2] ["t3" 1]
                  [2 3] ["t3" 2]
                  [3 4] ["t3" 3]
                  [4 5] ["t3" 4]
                  [0 5] ["t3" 5]
                  [0 2] ["t58" 0]
                  [1 3] ["t58" 1]
                  [2 4] ["t58" 2]
                  [3 5] ["t58" 3]
                  [0 4] ["t58" 4]
                  [1 5] ["t58" 5]
                  [0 3] ["t4" 0]
                  [1 4] ["t4" 1]
                  [2 5] ["t4" 2]
                  [0 1 0 2] ["t143" 0]
                  [0 1 0 3] ["t142" 0]
                  [0 1 0 4] ["t141" 0]
                  [0 1 0 5] ["t143" 5]
                  [0 1 1 2] ["t143" 0]
                  [0 1 1 3] ["t142" 0]
                  [0 1 1 4] ["t141" 0]
                  [0 1 1 5] ["t143" 5]
                  [0 2 0 1] ["t143" 0]
                  [0 2 0 3] ["t141" 2]
                  [0 2 0 4] ["t203" 0]
                  [0 2 0 5] ["t142" 5]
                  [0 2 1 2] ["t143" 0]
                  [0 2 2 3] ["t141" 2]
                  [0 2 2 4] ["t203" 0]
                  [0 2 2 5] ["t142" 5]
                  [0 3 0 1] ["t142" 0]
                  [0 3 0 2] ["t141" 2]
                  [0 3 0 4] ["t142" 3]
                  [0 3 0 5] ["t141" 5]
                  [0 3 1 3] ["t142" 0]
                  [0 3 2 3] ["t141" 2]
                  [0 3 3 4] ["t142" 3]
                  [0 3 3 5] ["t141" 5]
                  })


(def track-dits {
                "t501" []
                "t3"   [0 1]
                "t58"  [0 2]
                "t4"   [0 3]
                "t143" [0 1 0 2]
                "t142" [0 1 0 3]
                "t141" [0 1 0 4]
                "t203" [0 2 0 4]
                })

(def upgrade-oo {
                  [0 1] ["t803" 0]
                  [1 2] ["t803" 1]
                  [2 3] ["t803" 2]
                  [3 4] ["t803" 3]
                  [4 5] ["t803" 4]
                  [0 5] ["t803" 5]
                  [0 2] ["t248" 0]
                  [1 3] ["t248" 1]
                  [2 4] ["t248" 2]
                  [3 5] ["t248" 3]
                  [0 4] ["t248" 4]
                  [1 5] ["t248" 5]
                  [0 3] ["t802" 0]
                  [1 4] ["t802" 1]
                  [2 5] ["t802" 2]
                  [0 1 2 3] ["t68" 0]
                  [0 1 2 4] ["t67" 2]
                  [0 1 2 5] ["t54" 5]
                  [0 1 3 4] ["t212" 0]
                  [0 1 3 5] ["t66" 3]
                  [0 1 4 5] ["t68" 4]
                  [0 2 1 3] ["t35" 0]
                  [0 2 1 4] ["t64" 1]
                  [0 2 1 5] ["t35" 5]
                  [0 2 3 4] ["t66" 0]
                  [0 2 3 5] ["t36" 0]
                  [0 2 4 5] ["t67" 0]
                  [0 3 1 2] ["t54" 0]
                  [0 3 1 4] ["t65" 0]
                  [0 3 1 5] ["t64" 0]
                  [0 3 2 4] ["t64" 3]
                  [0 3 2 5] ["t65" 5]
                  [0 3 4 5] ["t54" 3]
                  [0 1 2 3 2 4] ["t339" 1]
                  [0 1 2 3 0 4] ["t339" 1]
                  [0 2 4 5 1 5] ["t339" 3]
                  [0 2 4 5 2 3] ["t339" 3]
                  [0 3 1 2 1 5] ["t339" 2]
                  [0 3 1 2 0 4] ["t339" 2]
                  [0 3 1 2 3 4] ["t339" 2]
                  [0 3 1 2 2 4] ["t339" 0]
                  [0 3 1 2 0 5] ["t339" 0]
                  [0 3 1 2 3 5] ["t339" 0]
                  [0 2 1 3 0 5] ["t339" 4]
                  [0 2 1 3 3 4] ["t339" 4]
                  [0 3 1 5 4 5] ["t339" 3]
                  [0 3 1 5 0 2] ["t339" 3]
                  [0 3 1 5 2 3] ["t339" 3]
                  [0 3 1 5 1 2] ["t339" 2]
                  [0 3 1 5 0 4] ["t339" 2]
                  [0 3 1 5 3 4] ["t339" 2]
                  [0 3 1 4 0 5] ["t339" 0]
                  [0 3 1 4 3 5] ["t339" 0]
                  [0 3 1 4 1 2] ["t339" 0]
                  [0 3 1 4 2 4] ["t339" 0]
                  [0 3 1 4 0 2] ["t339" 3]
                  [0 3 1 4 2 3] ["t339" 3]
                  [0 3 1 4 1 5] ["t339" 3]
                  [0 3 1 4 4 5] ["t339" 3]
                  [0 2 3 4 0 5] ["t339" 4]
                  [0 2 3 4 1 3] ["t339" 4]
                  [0 3 4 5 1 5] ["t339" 3]
                  [0 3 4 5 0 2] ["t339" 3]
                  [0 3 4 5 2 3] ["t339" 3]
                  [0 3 4 5 1 2] ["t339" 4]
                  [0 3 4 5 0 4] ["t339" 3]
                  [0 3 4 5 3 4] ["t339" 3]
                  [1 2 2 4 3 5 0 5 0] ["t804" 0]
                  [1 2 2 4 3 5 0 5 1] ["t804" 0]
                  [1 2 2 4 3 5 0 5 2] ["t804" 0]
                  [1 2 2 4 3 5 0 5 3] ["t804" 0]
                  [1 2 2 4 3 5 0 5 4] ["t804" 0]
                  [1 2 2 4 3 5 0 5 5] ["t804" 0]
                 })

(def track-oo {
               "t504" []
               "t803" [0 1]
               "t248" [0 2]
               "t802" [0 3]
               "t35" [0 2 1 3]
               "t36" [0 2 3 5]
               "t54" [0 3 1 2]
               "t64" [0 3 1 5]
               "t65" [0 3 1 4]
               "t66" [0 2 3 4]
               "t67" [0 2 4 5]
               "t68" [0 1 2 3]
               "t212" [0 1 3 4]
               "t339" [1 2 2 4 3 5 0 5]
               })


(def upgrade-man {
                  [0 3] ["t394" 0]
                  [1 4] ["t394" 0]
                  [2 5] ["t394" 0]
                  [0 3 0 1] ["t815" 0]
                  [0 3 1 2] ["t815" 1]
                  [0 3 2 3] ["t815" 0]
                  [0 3 3 4] ["t815" 1]
                  [0 3 4 5] ["t815" 0]
                  [0 3 0 5] ["t815" 1]
                  [0 3 1 4 0] ["t375" 0]
                  [0 3 1 4 1] ["t375" 0]
                  [0 3 1 4 2] ["t375" 0]
                  [0 3 1 4 3] ["t375" 0]
                  [0 3 1 4 4] ["t375" 0]
                  [0 3 1 4 5] ["t375" 0]
                  [0 3 1 4 2 5 0] ["tM04" 0]
                  [0 3 1 4 2 5 1] ["tM04" 0]
                  [0 3 1 4 2 5 2] ["tM04" 0]
                  [0 3 1 4 2 5 3] ["tM04" 0]
                  [0 3 1 4 2 5 4] ["tM04" 0]
                  [0 3 1 4 2 5 5] ["tM04" 0]
               })

(def track-man { "t519" [] "t394" [0 3] "t815" [0 3 1 4] "t375" [0 3 1 4 2 5] })

(def upgrademaps [
                 [track-simple upgrade-simple]
                 [track-city upgrade-city]
                 [track-bcity upgrade-bcity]
                 [track-dits upgrade-dits]
                 [track-oo upgrade-oo]
                 [track-man upgrade-man]
                 ])
