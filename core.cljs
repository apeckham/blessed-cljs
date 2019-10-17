(require '["blessed" :as blessed])

(def screen (.. blessed (screen #js {:smartCSR true})))

(set! (.. screen -title) "Hey world")

(def box
  (.. blessed
      (box (clj->js {:border {:type "line"}
                     :content "Hello world"
                     :height "50%"
                     :left "center"
                     :style {:bg "magenta" :border {:fg "#f0f0f0"} :fg "white" :hover {:bg "green"}}
                     :top "center"
                     :width "50%"}))))

(.. screen (append box))

(..
 box
 (key
  "enter"
  (fn [_ _] (.. box (setContent "wow")) (.. box (setLine 1 "bar")) (.. box (insertLine 1 "foo")) (.. screen render))))

(def x (atom 0))

(js/setInterval (fn [] (swap! x inc) (.. box (setContent (str @x))) (.. screen render)) 1000)

(.. screen (key #js ["escape" "q" "C-c"] (fn [_ _] (js/process.exit 0))))

(.. box focus)

(.. screen render)