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
    var onBack: (() -> Void)? { get set}
    var onSend: (() -> Void)? { get set }
}

class ForgotPasswordVC: BaseViewController, ForgotPasswordVCProtocol, AuthStoryboardLodable {
    
    
    @IBOutlet weak var txtFieldEmail: UITextField!
    @IBOutlet weak var txtFieldEmailErrorLabel: UILabel!
    @IBOutlet weak var formContainerStackView: UIStackView!
    @IBOutlet weak var sendEmailBtn: UIButton!
    
    
    // View Model
    var forgotPasswordViewModel: ForgotPasswordViewModel!
    
    // Validator
    private let validator = Validator()
    

    override func viewDidLoad() {
        super.viewDidLoad()
        setUpValidator()
        bindViewModel()
        setupUI()
    }
  
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        resetErrors()
        txtFieldEmail.text = ""
    }
    
    
    @IBAction func onTappedSendEmail(_ sender: Any) {
        self.validate()
    }
    
    @IBAction func actionBackToLogin(_ sender: Any) {
        self.onBack?()
    }
    
    
    // Forgot Password View Controller Protocol
    var onBack: (() -> Void)?
    
    var onSend: (() -> Void)?
    
    
    // MARK: - Private Methods
    
    private func setupUI() {
        
        self.formContainerStackView.addBlurToView(alpha: 0.3)
        self.formContainerStackView.layoutMargins = UIEdgeInsets(top: 10, left: 12, bottom: 10, right: 12)
        self.formContainerStackView.isLayoutMarginsRelativeArrangement = true
        
        self.sendEmailBtn.layer.cornerRadius = 12
        self.sendEmailBtn.layer.borderWidth = 3
        self.sendEmailBtn.layer.borderColor = UIColor(named: "colorPrimaryDark")?.cgColor
    }
    
    private func bindViewModel() {
        
        configureTwoBinding(textField: txtFieldEmail, to: forgotPasswordViewModel.email)
        
        forgotPasswordViewModel
            .onShowAlert
            .observeOn(MainScheduler.instance)
            .subscribe(onNext: {
                AppHUD.shared.showErrorMessage($0.message ?? "", title: $0.title ?? "")
            })
            .disposed(by: disposeBag)

        forgotPasswordViewModel
            .onShowingLoading
            .observeOn(MainScheduler.instance)
            .subscribe(onNext: { [weak self] in
                self?.setLoadingHud(visible: $0)
            })
            .disposed(by: disposeBag)
        
    }
    
    private func sendEmail() {
        forgotPasswordViewModel.send()
    }
    
}


// MARK: ValidationDelegate Methods

extension ForgotPasswordVC: ValidationDelegate {

    // ValidationDelegate methods
    func validationSuccessful() {
        resetErrors()
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
    
    private func validate() {
        resetErrors()
        self.validator.validate(self)
    }
    
    private func setUpValidator() {
        
        validator.registerField(txtFieldEmail,
            errorLabel: txtFieldEmailErrorLabel,
            rules: [RequiredRule(), EmailRule(), MinLengthRule(length: 5)])
    }
    
    
    private func resetErrors () {
        for validation in self.validator.validations.enumerated() {
            let validationRule = validation.element.value
            // Reset Error Label
            validationRule.errorLabel?.isHidden = false
            validationRule.errorLabel?.text = ""
            
            if let field = validationRule.field as? UITextField {
                field.layer.borderColor = UIColor.white.cgColor
            }
        }
    }
}
