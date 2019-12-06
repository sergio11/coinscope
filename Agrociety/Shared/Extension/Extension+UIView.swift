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
    func addBlurToView(alpha a: Float = 0.8, byPercentage percentage: CGFloat = 0.2) {
        let blurEffect = UIBlurEffect(style: .light)
        let blurredEffectView = UIVisualEffectView(effect: blurEffect)
        blurredEffectView.frame = self.bounds.decreaseRect(byPercentage: percentage)
        blurredEffectView.alpha = alpha
        blurredEffectView.autoresizingMask = [.flexibleWidth, .flexibleHeight]
        self.addSubview(blurredEffectView)
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
