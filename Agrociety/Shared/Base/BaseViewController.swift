//
//  BaseViewController.swift

import Reachability
import UIKit
import RxRelay
import RxSwift

class BaseViewController: UIViewController, CoordinatorNavigationControllerDelegate {
    
    
    var isConnectedToInternet: Bool = false
    var disposeBag = DisposeBag()

    override func viewDidLoad() {
        super.viewDidLoad()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }

    // MARK: - Controller lifecycle

    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        setupNavigationController()
    }
    
    // MARK: - Protected Methods
    
    func configureTwoBinding(textField: UITextField, to behaviorRelay: BehaviorRelay<String>) {
        
        behaviorRelay.asObservable()
            .bind(to: textField.rx.text)
            .disposed(by: disposeBag)
        
        textField.rx.text.orEmpty
            .bind(to: behaviorRelay)
            .disposed(by: disposeBag)
    }
    
    func setLoadingHud(visible: Bool) {
        if visible {
            AppHUD.shared.showHUD()
        } else {
            AppHUD.shared.hideHUD()
        }
    }

    // MARK: - Private methods

    private func setupNavigationController() {
        if let navigationController = self.navigationController as? CoordinatorNavigationController {
            print("Coordinator Navigation Controller configured")
            navigationController.swipeBackDelegate = self
        } else {
            print("Missing Coordinator Navigation Controller")
        }
    }

    // MARK: - SwipeBackNavigationControllerDelegate

    internal func transitionBackFinished() {}

    internal func didSelectCustomBackAction() {}

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        ReachabilityManager.shared.addListener(listener: self)
    }

    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        ReachabilityManager.shared.removeListener(listener: self)
    }
}

extension BaseViewController: NetworkStatusListener {
    func networkStatusDidChange(status: Reachability.Connection) {
        switch status {
            case .none:
                printToConsole(message: "ViewController: Network became unreachable")
            case .wifi:
                printToConsole(message: "ViewController: Network reachable through WiFi")
            case .cellular:
                printToConsole(message: "ViewController: Network reachable through Cellular Data")
            case .unavailable:
                printToConsole(message: "ViewController: UnAvaliable")
        }
    }
}
