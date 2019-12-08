//
//  Extension+ViewController.swift

import UIKit

extension UINavigationController {
    
    open func pushVC(_ vc: UIViewController) {
        self.pushViewController(vc, animated: true)
    }

    open func popVC() {
        _ = self.popViewController(animated: true)
    }

    open func popToRootVC() {
        _ = self.popToRootViewController(animated: true)
    }

    open func presentVC(_ vc: UIViewController) {
        present(vc, animated: true, completion: nil)
    }
}
