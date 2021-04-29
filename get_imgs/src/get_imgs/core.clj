(ns get-imgs.core
  (:require [net.cgrand.enlive-html :as html]
           [clj-http.client :as client] ))

;;;; 文字列のURL
(def ^:dynamic *flp-base* "https://www.feynmanlectures.caltech.edu/")
;; (def *flp-vol1* "https://www.feynmanlectures.caltech.edu/I_toc.html")
(def fp (str *flp-base* "I_91.html"))
(def fo (str *flp-base* "I_92.html"))
(def v1_lecs (map #(str *flp-base* "I_" % ".html") (concat (map #(str "0" %) (range 1 10)) (map str (range 10 53)))))

;;;; URL
(defn to-url [str]
  (java.net.URL. str))

;; (def *flp-vol1-url* (to-url *flp-vol1*))
(def fp-url (to-url fp))
(def fo-url (to-url fo))
(def v1_lecs_url (map to-url v1_lecs))

(defn fetch-url [url]
  (html/html-resource url))

;;;; (def top-page (fetch-url *flp-vol1-url*))
;; (def fp-page (fetch-url fp-url))
;; (def fo-page (fetch-url fo-url))
;;;; (remove #(= % "") (map #(get-in % [:attrs :src]) (html/select fp-page [:img])))

(defn image-urls [page]
  (remove #(= % "")
          (map #(get-in % [:attrs :src]) (html/select page [:img]))))
(defn lec-images [page]
  (remove nil? (remove #(= % "")
          (map #(get-in % [:attrs :data-src]) (html/select page [:img])))))

(defn save-img [url-str]
  (let [file-name (str "IMEGES/" (last (clojure.string/split url-str #"/")))
        url (str *flp-base* url-str)
        content (:body (client/get url {:as :byte-array}))]
    (with-open [w (clojure.java.io/make-output-stream url-str {})]
      (.write w content))))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
