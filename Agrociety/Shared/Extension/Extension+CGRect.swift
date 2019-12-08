//
//  Extension+CGRect.swift
//  Agrociety
//
//  Created by Sergio Sánchez Sánchez on 06/12/2019.
//  Copyright © 2019 sadman samee. All rights reserved.
//

import Foundation
import UIKit

extension CGRect {
    
    func increaseRect(byPercentage percentage: CGFloat) -> CGRect {
        let startWidth = self.width
        let startHeight = self.height
        let adjustmentWidth = (startWidth * percentage) / 2.0
        let adjustmentHeight = (startHeight * percentage) / 2.0
        return self.insetBy(dx: -adjustmentWidth, dy: -adjustmentHeight)
    }
    
    
    func decreaseRect(widthPercentage wp: CGFloat, heightPercentage hp: CGFloat) -> CGRect {
        let startWidth = self.width
        let startHeight = self.height
        let adjustmentWidth = (startWidth * wp) / 2.0
        let adjustmentHeight = (startHeight * hp) / 2.0
        return self.insetBy(dx: adjustmentWidth, dy: adjustmentHeight)
    }
    
}
