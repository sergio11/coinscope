//
//  Extension+UIView.swift
//  Agrociety
//
//  Created by Sergio Sánchez Sánchez on 06/12/2019.
//  Copyright © 2019 sadman samee. All rights reserved.
//

import Foundation
import UIKit


extension UIView {
    
    
    /**
        Add Blur To View with specified alpha value
     */
    func addBlurToView(alpha a: Float = 0.8, reducePercentageOfWidth wp: CGFloat = 0.0, reducePercentageOfHeight hp: CGFloat = 0.0) {
        let blurEffect = UIBlurEffect(style: .light)
        let blurredEffectView = UIVisualEffectView(effect: blurEffect)
        
        let adjustmentBounds = wp > 0.0 || hp > 0.0 ? self.bounds.decreaseRect(widthPercentage: wp, heightPercentage: hp) : self.bounds
        
        blurredEffectView.frame = adjustmentBounds
        blurredEffectView.alpha = alpha
        blurredEffectView.autoresizingMask = [.flexibleWidth, .flexibleHeight]
        self.insertSubview(blurredEffectView, at: 0)
    }
    
    /**
     Remove Blur From View
     */
    func removeBlurFromView() {
        for subView in self.subviews {
            if subView is UIVisualEffectView {
                subView.removeFromSuperview()
            }
        }
    }

}
