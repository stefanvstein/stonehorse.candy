(ns generators.generate-lists
  (:require [generators.common :refer [nline]]))

(defn create-lists [n-args]
  (println "
  public static <T> List<T> asList(")
  (print (apply
            str
            (interpose
             (str "," (nline))
             (map #(str "    T t" %) (range 0 n-args)))))
  (println "){
    List<T> l = arrayList(")
  (println (apply str (interpose
                       (str "," (nline))
                       (map #(str "      t" %) (range 0 n-args)))))
  (println "    );
    return Collections.unmodifiableList(l);
  }"))

(defn create-arraylists [n-args]
    (println "
  public static <T> List<T> arrayList(")
  (print (apply
            str
            (interpose
             (str "," (nline))
             (map #(str "    T t" %) (range 0 n-args)))))
  (println (str "){
    List<T> l = new ArrayList<>(" n-args ");"))
  
  (println (apply str  (interpose (nline) (map #(str "    l.add(t" % ");" ) (range 0 n-args)))))
  (println "    return l;
  }"))

(defn generate [n package]
  (println (str
"package " package ";
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
public class Lists{
    private Lists(){}
    public static <T> List<T> asList(){
        return Collections.emptyList();
    }
    public static <T> List<T> arrayList(){
        return new ArrayList<>();
    }
"))
  (dotimes [i n]
    (create-lists (inc i))
    (create-arraylists (inc i)))
  (println "}"))
