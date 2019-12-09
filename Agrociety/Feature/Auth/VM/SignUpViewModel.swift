//
//  SignUpViewModel.swift

import Moya
import RxRelay
import RxSwift
import SwiftyJSON

final class SignUpViewModel {
    
    private var authProvider: MoyaProvider<AuthService>
    private let disposeBag = DisposeBag()

    init(service: MoyaProvider<AuthService>) {
        authProvider = service
    
    }

    private let isLoading = BehaviorRelay(value: false)
    private let alertMessage = PublishSubject<AlertMessage>()
    private let isSuccess = PublishSubject<JSON>()

    var email = BehaviorRelay<String>(value: "")
    var password = BehaviorRelay<String>(value: "")
    var fullName = BehaviorRelay<String>(value: "")


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
    

    func signUp() {
        
        isLoading.accept(true)

        DispatchQueue.global().asyncAfter(deadline: DispatchTime.now() + 6) {
            
            print("Response from server")
            self.isLoading.accept(false)
            self.alertMessage.onNext(AlertMessage(title: "Error", message: "The server does not respond"))
            
        }
        
    }
}
