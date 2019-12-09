//
//  LogInViewModel.swift

import Moya
import RxRelay
import RxSwift
import SwiftyJSON

final class LogInViewModel {
   

    init(service: MoyaProvider<AuthService>, userService: UserService) {
        authProvider = service
        self.userService = userService
    }

    
    private var authProvider: MoyaProvider<AuthService>
    private var userService: UserService
    private let disposeBag = DisposeBag()
    
    private let isLoading = BehaviorRelay(value: false)
    private let alertMessage = PublishSubject<AlertMessage>()
    private let isSuccess = PublishSubject<JSON>()

    var email = BehaviorRelay<String>(value: "")
    var password = BehaviorRelay<String>(value: "")

    var onShowingLoading: Observable<Bool> {
        return isLoading.asObservable()
            .distinctUntilChanged()
    }

    var onShowAlert: Observable<AlertMessage> {
        return alertMessage.asObservable()
    }

    var onSuccess: Observable<JSON> {
        return isSuccess.asObservable()
    }


    func login() {
        
        isLoading.accept(true)
        
        DispatchQueue.global().asyncAfter(deadline: DispatchTime.now() + 6) {
            
            print("Response from server")
            self.isLoading.accept(false)
            self.alertMessage.onNext(AlertMessage(title: "Error", message: "The server does not respond"))
            
        }
        
    }
}
