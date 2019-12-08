//
//  ForgotPasswordViewModel.swift
//  Agrociety
//
//  Created by Sergio Sánchez Sánchez on 08/12/2019.
//  Copyright © 2019 sadman samee. All rights reserved.
//

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
        return isLoading.asObservable()
            .distinctUntilChanged()
    }

    var onShowAlert: Observable<AlertMessage> {
        return alertMessage.asObservable()
    }

    var onSuccess: Observable<JSON> {
        return isSuccess.asObservable()
    }
    
    
    func send() {
        
        isLoading.accept(true)
        
        
        DispatchQueue.global().asyncAfter(deadline: DispatchTime.now() + 5) {
            
            self.isLoading.accept(false)
            self.alertMessage.onNext(AlertMessage(title: "Error", message: "Email couldn`t send"))
            
        }
        
        
    }
    
    
    // Private Methods
    private var isEmailValid: Observable<Bool> {
        return email.asObservable().map { $0.count >= 6 && $0.isvalidEmail }
    }
    
}
