(ns generators.common)
(defn increasing-args [prefix from to]
  (apply str (interpose "," (map #(str prefix %) (range from to)))))

(defn nline
  ([]
   (with-out-str (newline)))
  ([n]
   (apply str (repeatedly n nline))))
