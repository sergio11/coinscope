//
//  BaseViewController.swift

import Reachability
import UIKit

class BaseViewController: UIViewController, CoordinatorNavigationControllerDelegate {
    var isConnectedToInternet: Bool = false

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

    // MARK: - Private methods

    private func setupNavigationController() {
        if let navigationController = self.navigationController as? CoordinatorNavigationController {
            navigationController.swipeBackDelegate = self
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
