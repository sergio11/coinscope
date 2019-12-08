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
    
    
    @IBOutlet var txtFieldEmail: UITextField!
    @IBOutlet var formContainerStackView: UIStackView!
    @IBOutlet weak var sendEmailBtn: UIButton!
    
    
    // View Model
    var forgotPasswordViewModel: ForgotPasswordViewModel!
    
    // Validator
    private let validator = Validator()
    
    private var disposeBag = DisposeBag()

    override func viewDidLoad() {
        super.viewDidLoad()
        setUpValidator()
        bindViewModel()
        setupUI()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationController?.setNavigationBarHidden(false, animated: true)
    }
    
    override func didSelectCustomBackAction() {
        print("didSelectCustomBackAction")
        self.onBack?()
    }
    
    @IBAction func onTappedSendEmail(_ sender: Any) {
        self.validator.validate(self)
    }
    
    
    // Forgot Password View Controller Protocol
    var onBack: (() -> Void)?
    
    var onSend: (() -> Void)?
    
    
    // Private Methods
    
    
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
            print("Show HUD")
            AppHUD.shared.showHUD()
        } else {
            print("Hide HUD")
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
