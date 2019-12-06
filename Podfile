# Uncomment the next line to define a global platform for your project
platform :ios, '10.0'

target 'Agrociety' do
  # Comment the next line if you're not using Swift and don't want to use dynamic frameworks
  use_frameworks!


  pod 'IQKeyboardManager'
  pod 'Kingfisher', '~> 5.0'
  pod 'Moya', '~> 13.0'
  pod 'SwiftyJSON'
  pod 'ReachabilitySwift'
  pod 'SwiftMessages'
  pod 'SwiftValidator' , :git => 'https://github.com/Sadmansamee/SwiftValidator.git', :branch => 'master'
  pod 'RxSwift', '~> 5'
  pod 'RxCocoa', '~> 5'
  pod 'Swinject' , '~> 2.7.1'
  pod 'SwinjectAutoregistration'
  pod 'SwinjectStoryboard', '~> 2.2.0'
  pod 'SwiftKeychainWrapper'

  #to check memory leak
  post_install do |installer|
    installer.pods_project.targets.each do |target|
      if target.name == ‘RxSwift’
        target.build_configurations.each do |config|
          if config.name == ‘Debug’
            config.build_settings[‘OTHER_SWIFT_FLAGS’] ||= [‘-D’, ‘TRACE_RESOURCES’]
          end
        end
      end
    end
  end
  
  target 'AgrocietyTests' do
    inherit! :search_paths
    # Pods for testing
    pod 'RxBlocking', '~> 5'
    pod 'RxTest', '~> 5'
    pod 'Quick'
    pod 'Nimble'
    pod 'Swinject'
    pod 'SwinjectStoryboard'
    pod 'SwiftyJSON'

  end

  target 'AgrocietyUITests' do
    inherit! :search_paths
    # Pods for testing
  end

end
