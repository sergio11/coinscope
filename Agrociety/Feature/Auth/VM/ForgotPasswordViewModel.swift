//
//  ForgotPasswordViewModel.swift
//  Agrociety

import Foundation
import RxRelay
import RxSwift
import SwiftyJSON


final class ForgotPasswordViewModel {
    
    private let isLoading = BehaviorRelay(value: false)
    private let alertMessage = PublishSubject<AlertMessage>()
    private let isSuccess = PublishSubject<JSON>()
    
    private let disposeBag = DisposeBag()
    
    var email = BehaviorRelay<String>(value: "")
    
    var onShowingLoading: Observable<Bool> {
        return isLoading.asObservable().distinctUntilChanged()
    }

    var onShowAlert: Observable<AlertMessage> {
        return alertMessage.asObservable()
    }

    var onSuccess: Observable<JSON> {
        return isSuccess.asObservable()
    }
    
    
    func send() {
        
        isLoading.accept(true)
        
        print("Send Email")
        
        DispatchQueue.global().asyncAfter(deadline: DispatchTime.now() + 6) {
            
            print("Response from server")
            self.isLoading.accept(false)
            self.alertMessage.onNext(AlertMessage(title: "Error", message: "The server does not respond"))
            
        }
    }
    
    
    // Private Methods
    private var isEmailValid: Observable<Bool> {
        return email.asObservable().map { $0.count >= 6 && $0.isvalidEmail }
    }
    
}
