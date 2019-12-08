//
//  ForgotPasswordVC.swift
//  Agrociety
//
//  Created by Sergio Sánchez Sánchez on 08/12/2019.
//  Copyright © 2019 sadman samee. All rights reserved.
//

import Foundation
import RxSwift
import SwiftValidator
import RxCocoa

// View Controller Protocol
protocol ForgotPasswordVCProtocol: AnyObject {
    var onBack: (() -> Void)? { get set }
    var onSend: (() -> Void)? { get set }
}

class ForgotPasswordVC: BaseViewController, ForgotPasswordVCProtocol, AuthStoryboardLodable {
    
    
    @IBOutlet var txtFieldEmail: UITextField!
    
    
    // View Model
    var forgotPasswordViewModel: ForgotPasswordViewModel!
    
    // Validator
    private let validator = Validator()
    
    private var disposeBag = DisposeBag()

    override func viewDidLoad() {
        super.viewDidLoad()
        setUpValidator()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        navigationController?.setNavigationBarHidden(true, animated: false)
    }
    
    override func didSelectCustomBackAction() {
        onBack?()
    }
    
    
    // Forgot Password View Controller Protocol
    var onBack: (() -> Void)?
    
    var onSend: (() -> Void)?
    
    
    // Private Methods
    
    private func bindViewModel() {
        
        configureTwoBinding(textField: txtFieldEmail, to: forgotPasswordViewModel.email)
        
        
        forgotPasswordViewModel
            .onShowAlert
            .map {
                AppHUD.shared.showErrorMessage($0.message ?? "", title: $0.title ?? "")
            }
            .subscribe()
            .disposed(by: disposeBag)

        forgotPasswordViewModel
            .onShowingLoading
            .map { [weak self] in
                self?.setLoadingHud(visible: $0)
            }
            .subscribe()
            .disposed(by: disposeBag)
        
    }
    
    
    private func configureTwoBinding(textField: UITextField, to behaviorRelay: BehaviorRelay<String>) {
        behaviorRelay.asObservable()
            .bind(to: textField.rx.text)
            .disposed(by: disposeBag)
        textField.rx.text.orEmpty
            .bind(to: behaviorRelay)
            .disposed(by: disposeBag)
    }
    
    private func sendEmail() {
        forgotPasswordViewModel.send()
    }
    
    private func setLoadingHud(visible: Bool) {
        if visible {
            AppHUD.shared.showHUD()
        } else {
            AppHUD.shared.hideHUD()
        }
    }
}


// MARK: ValidationDelegate Methods

extension ForgotPasswordVC: ValidationDelegate {

    // ValidationDelegate methods
    func validationSuccessful() {
        sendEmail()
    }

    func validationFailed(_ errors: [(Validatable, ValidationError)]) {
        for (field, error) in errors {
            if let field = field as? UITextField {
                field.layer.borderColor = UIColor.red.cgColor
                field.layer.borderWidth = 1.0
            }
            error.errorLabel?.text = error.errorMessage // works if you added labels
            error.errorLabel?.isHidden = false
        }
    }
    
    // Private method
    private func setUpValidator() {
        validator.registerField(txtFieldEmail, rules: [RequiredRule(), EmailRule(), MinLengthRule(length: 5)])
    }
}
