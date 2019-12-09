//
//  AuthCoordinator.swift

//

import Swinject
import UIKit

enum AuthChildCoordinator {
    case about
}

final class AuthCoordinator: Coordinator, CoordinatorFinishOutput {
    // MARK: - CoordinatorFinishOutput

    var finishFlow: (() -> Void)?

    // MARK: - Vars & Lets

    let navigationController: UINavigationController
    let container: Container
    private var childCoordinators = [AuthChildCoordinator: Coordinator]()

    // MARK: - Coordinator

    func start() {
        showLoginVC()
    }

    // MARK: - Init

    init(container: Container, navigationController: UINavigationController) {
        self.container = container
        self.navigationController = navigationController
    }

    // MARK: - Private methods

    // Configure and present Login View Controller
    private func showLoginVC() {
        
        let vc = container.resolveViewController(LoginVC.self)
        
        vc.onBack = { [unowned self] in
            self.navigationController.popVC()
        }
        vc.onLogin = {
            self.finishFlow?()
        }
        vc.onSignUp = {
            self.showSignUpVC()
        }
        vc.onForgotPassword = {
            self.showForgotPasswordVC()
        }

        navigationController.pushVC(vc)
    }
    
    // Configure and push Forgot Password View Controller
    private func showForgotPasswordVC() {
        
        let vc = container.resolveViewController(ForgotPasswordVC.self)
        
        vc.onBack = {[unowned self] in
           self.navigationController.popVC()
        }
        
        navigationController.pushVC(vc)
    }

    // Configure and present Signup View Controller
    private func showSignUpVC() {
        let vc = container.resolveViewController(SignUpVC.self) 
        vc.onBack = { [unowned self] in
            self.navigationController.popVC()
        }
        vc.onSignUp = {
            self.navigationController.popVC()
        }
        vc.onSignIn = {
            self.navigationController.popVC()
        }
        navigationController.pushVC(vc)
    }
}
