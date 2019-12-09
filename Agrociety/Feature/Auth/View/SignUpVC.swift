//
//  SignUpVC.swift


import RxCocoa
import RxSwift
import SwiftValidator
import Swinject
import UIKit

protocol SignUpVCProtocol: AnyObject {
    var onBack: (() -> Void)? { get set }
    var onSignUp: (() -> Void)? { get set }
    var onSignIn: (() -> Void)? { get set }
}

class SignUpVC: BaseViewController, SignUpVCProtocol, AuthStoryboardLodable {
    
    
    // MARK: - IBOutlets
    @IBOutlet var txtFieldFullName: UITextField!
    @IBOutlet var txtFieldFullNameErrorLabel: UILabel!
    @IBOutlet var txtFieldEmailAddress: UITextField!
    @IBOutlet var txtFieldEmailAddressErrorLabel: UILabel!
    @IBOutlet var txtFieldPassWord: UITextField!
    @IBOutlet var txtFieldPassWordErrorLabel: UILabel!
    @IBOutlet var txtFieldRepeatPassWord: UITextField!
    @IBOutlet var txtFieldRepeatPassWordErrorLabel: UILabel!
    @IBOutlet var btnSignUp: UIButton!
    @IBOutlet var formContainerStackView: UIStackView!

    // Form Validator
    private let validator = Validator()

    // SignUp View Model
    var signUpViewModel: SignUpViewModel!


    // MARK: - SignUpVCProtocol

    var onBack: (() -> Void)?
    var onSignUp: (() -> Void)?
    var onSignIn: (() -> Void)?
    
    // MARK: IBActions
    
    @IBAction func actionLogin(_: Any) {
        onSignIn?()
    }
    
    @IBAction func actionSignup(_: Any) {
        self.validate()
    }

    // MARK: - Overrides

    override func didSelectCustomBackAction() {
        onBack?()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setUpValidator()
        bindViewModel()
        setupUI()
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        resetErrors()
        txtFieldFullName.text = ""
        txtFieldEmailAddress.text = ""
        txtFieldPassWord.text = ""
        txtFieldRepeatPassWord.text = ""
    }

    
    // MARK: Private Methods
    
    private func setupUI() {
        self.formContainerStackView.addBlurToView(alpha: 0.3)
        self.formContainerStackView.layoutMargins = UIEdgeInsets(top: 10, left: 12, bottom: 10, right: 12)
        self.formContainerStackView.isLayoutMarginsRelativeArrangement = true
        
        self.btnSignUp.layer.cornerRadius = 12
        self.btnSignUp.layer.borderWidth = 3
        self.btnSignUp.layer.borderColor = UIColor(named: "colorPrimaryDark")?.cgColor
    }

    private func signUP() {
        signUpViewModel.signUp()
    }

    private func bindViewModel() {
       
        configureTwoBinding(textField: txtFieldPassWord, to: signUpViewModel.password)
        configureTwoBinding(textField: txtFieldEmailAddress, to: signUpViewModel.email)
        configureTwoBinding(textField: txtFieldFullName, to: signUpViewModel.fullName)


        signUpViewModel
            .onShowAlert
            .observeOn(MainScheduler.instance)
            .subscribe(onNext: {
                AppHUD.shared.showErrorMessage($0.message ?? "", title: $0.title ?? "")
            })
            .disposed(by: disposeBag)

        signUpViewModel
            .onShowingLoading
            .observeOn(MainScheduler.instance)
            .subscribe(onNext: { [weak self] in
                self?.setLoadingHud(visible: $0)
            })
            .disposed(by: disposeBag)

        signUpViewModel
            .onSuccess
            .observeOn(MainScheduler.instance)
            .subscribe(onNext: { [weak self] _ in
                self?.onSignIn?()
            })
            .disposed(by: disposeBag)
    }
}

// MARK: ValidationDelegate Methods

extension SignUpVC: ValidationDelegate {
    


    // ValidationDelegate methods
    
    
    func validationSuccessful() {
        signUP()
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
    
        validator.registerField(txtFieldFullName,
            errorLabel: txtFieldFullNameErrorLabel,
            rules: [RequiredRule(), MinLengthRule(length: 5)])
        
        validator.registerField(txtFieldEmailAddress,
            errorLabel: txtFieldEmailAddressErrorLabel,
            rules: [RequiredRule(), EmailRule(), MinLengthRule(length: 5)])
        
        validator.registerField(txtFieldPassWord,
            errorLabel: txtFieldPassWordErrorLabel,
            rules: [RequiredRule(), MinLengthRule(length: 5)])
        
        validator.registerField(txtFieldRepeatPassWord,
            errorLabel: txtFieldRepeatPassWordErrorLabel,
            rules: [ConfirmationRule(confirmField: txtFieldPassWord)])
    }
    
    private func validate() {
        resetErrors()
        self.validator.validate(self)
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
