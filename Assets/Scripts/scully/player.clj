(ns scully.player
  (:use arcadia.core)
  (:import [UnityEngine GameObject Transform])) 

(defcomponent Player [^float fireRate 
                      ^float fireFrame
                      ^GameObject bullet]
  (Start [this] (set! fireFrame 0.))
  (Update [this] 
    (let [fire-frame (if (= fireFrame 0.)
                         0.
                         (- fireFrame 1.))
          new-pos (.. (object-named "Main Camera") camera (ScreenToWorldPoint Input/mousePosition))
          new-vec (Vector3. (.x new-pos) (.y new-pos) 0.)
          clicked (Input/GetMouseButton 0)
          fire-bullet (and clicked (= 0 fire-frame))
          bullet-location (if fire-bullet (.. this transform position))]
       (do 
         (set! (.. this transform position) 
                   new-vec)
         (set! fireFrame fire-frame)
         (if fire-bullet
             (do 
               (instantiate bullet bullet-location)
               (set! fireFrame fireRate)))))))