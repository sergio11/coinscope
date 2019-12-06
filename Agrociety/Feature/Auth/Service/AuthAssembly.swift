//
//  AuthAssembly.swift

import Foundation
import Moya
import Swinject

final class AuthAssembly: Assembly {
    func assemble(container: Container) {
        // let userService = UserService()

        container.register(UserService.self, factory: { _ in
            UserService()
        }).inObjectScope(ObjectScope.container)

        container.register(MoyaProvider<AuthService>.self, factory: { _ in
            MoyaProvider<AuthService>(plugins: [NetworkLoggerPlugin(verbose: true, responseDataFormatter: JSONResponseDataFormatter)])
        }).inObjectScope(ObjectScope.container)

        container.register(SignUpViewModel.self, factory: { container in
            SignUpViewModel(service: container.resolve(MoyaProvider<AuthService>.self)!)
        }).inObjectScope(ObjectScope.container)

        container.register(LogInViewModel.self, factory: { container in
            LogInViewModel(service: container.resolve(MoyaProvider<AuthService>.self)!, userService: container.resolve(UserService.self)!)
        }).inObjectScope(ObjectScope.container)

        // view controllers
        container.storyboardInitCompleted(LoginVC.self) { r, c in
            c.loginViewModel = r.resolve(LogInViewModel.self)
        }
        container.storyboardInitCompleted(SignUpVC.self) { r, c in
            c.signUpViewModel = r.resolve(SignUpViewModel.self)
        }
    }
}
