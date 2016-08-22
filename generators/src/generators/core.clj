(ns generators.core
  (require [generators generate-choices generate-lists generate-supplier-logic generate-threading]))

(defn generate [& n]
  (spit "../src/main/java/stonehorse/candy/Lists.java" (with-out-str (generators.generate-lists/generate 20 "stonehorse.candy")))
  (spit "../src/main/java/stonehorse/candy/Choices.java" (with-out-str (generators.generate-choices/generate 10 "stonehorse.candy")))
  (spit "../src/main/java/stonehorse/candy/SupplierLogic.java" (with-out-str (generators.generate-supplier-logic/generate 10 "stonehorse.candy")))
  (spit "../src/main/java/stonehorse/candy/Threading.java" (with-out-str (generators.generate-threading/generate 10 "stonehorse.candy"))))
