//
//  LoginVC.swift

import RxCocoa
import RxSwift
import SwiftValidator
import Swinject
import UIKit

protocol LoginVCProtocol: AnyObject {
    var onBack: (() -> Void)? { get set }
    var onLogin: (() -> Void)? { get set }
    var onSignUp: (() -> Void)? { get set }
    var onForgotPassword: (() -> Void)? {get set}
}

class LoginVC: BaseViewController, LoginVCProtocol, AuthStoryboardLodable {
    

    // MARK: - IBOutlets
    @IBOutlet weak var loginBtn: UIButton!
    @IBOutlet var txtFieldEmail: UITextField!
    @IBOutlet var txtFieldEmailErrorLabel: UILabel!
    @IBOutlet var txtFieldPassword: UITextField!
    @IBOutlet var txtFieldPasswordErrorLabel: UILabel!
    @IBOutlet weak var imageViewBackground: UIImageView!
    @IBOutlet weak var formContainer: UIStackView!
    
    
    // Validator
    private let validator = Validator()

    // View Model
    var loginViewModel: LogInViewModel!

    // MARK: - LoginVCProtocol

    var onBack: (() -> Void)?
    var onLogin: (() -> Void)?
    var onSignUp: (() -> Void)?
    var onForgotPassword: (() -> Void)?

    // MARK: - IBActions
    @IBAction func onForgotPassword(_ sender: Any) {
        self.onForgotPassword?()
    }
    
    @IBAction func actionLogin(_: Any) {
        self.validate()
    }

    @IBAction func actionSignUP(_: Any) {
        self.onSignUp?()
    }
    
    // MARK: - Overrides

    override func viewDidLoad() {
        super.viewDidLoad()
        setUpValidator()
        setUI()
        bindViewModel()
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        navigationController?.setNavigationBarHidden(true, animated: false)
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        resetErrors()
        txtFieldEmail.text = ""
        txtFieldPassword.text = ""
    }
    
    override func didSelectCustomBackAction() {
        onBack?()
    }

    // MARK: - Private Methods

    private func login() {
        loginViewModel.login()
    }

    private func setUI() {
        // Configure Login Button
        loginBtn.layer.cornerRadius = 12
        loginBtn.layer.borderWidth = 3
        loginBtn.layer.borderColor = UIColor(named: "colorPrimaryDark")?.cgColor
        
        // Add Blur layer
        formContainer.addBlurToView(alpha: 0.3)
        formContainer.layoutMargins = UIEdgeInsets(top: 10, left: 12, bottom: 10, right: 12)
        formContainer.isLayoutMarginsRelativeArrangement = true
    }


    private func bindViewModel() {
        
        configureTwoBinding(textField: txtFieldPassword, to: loginViewModel.password)
        configureTwoBinding(textField: txtFieldEmail, to: loginViewModel.email)

    
        loginViewModel
            .onShowAlert
            .observeOn(MainScheduler.instance)
            .subscribe(onNext: {
                AppHUD.shared.showErrorMessage($0.message ?? "", title: $0.title ?? "")
            })
            .disposed(by: disposeBag)

        loginViewModel
            .onShowingLoading
            .observeOn(MainScheduler.instance)
            .subscribe(onNext: {[weak self] in
                self?.setLoadingHud(visible: $0)
            })
            .disposed(by: disposeBag)

        loginViewModel
            .onSuccess
            .observeOn(MainScheduler.instance)
            .subscribe(onNext: { [weak self] isSuccess in
                guard let self = self else {
                    return
                }
                self.navigationController?.setNavigationBarHidden(false, animated: false)
                self.onLogin?()
            })
            .disposed(by: disposeBag)
    }
}

// MARK: ValidationDelegate Methods

extension LoginVC: ValidationDelegate {
    
    

    // ValidationDelegate methods
    func validationSuccessful() {
        resetErrors()
        login()
    }

    func validationFailed(_ errors: [(Validatable, ValidationError)]) {
        
        for (field, error) in errors {
            
            if let field = field as? UITextField {
                field.layer.borderColor = UIColor.red.cgColor
                field.layer.borderWidth = 1.0
            }
            error.errorLabel?.text = error.errorMessage
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
        
        validator.registerField(txtFieldPassword,
            errorLabel: txtFieldPasswordErrorLabel,
            rules: [RequiredRule(), MinLengthRule(length: 5)])
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
