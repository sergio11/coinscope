# 💰 **CoinScope**  

**CoinScope** is a **native Android application** designed to monitor cryptocurrency prices and trends. Leveraging modern technologies, clean architecture principles, and best practices, it ensures a seamless and efficient experience for cryptocurrency enthusiasts.  


## 🌟 **Features**  

- 🚀 **Real-time cryptocurrency tracking**: Stay updated with the latest prices and market trends.  
- 📊 **Interactive charts**: Analyze market movements with intuitive and dynamic visuals.  
- 🛠️ **Modern UI**: Built with **Jetpack Compose** for a responsive, sleek, and modern interface.  
- 🔄 **MVI (Model-View-Intent) architecture**: Ensures predictable state management and testable UI logic.  
- 🗂️ **Modular architecture**: Highly maintainable, scalable, and resilient to changes.


## 🛠 **Development Approach**  

CoinScope follows industry best practices, including:  

### **Architecture Principles**  

1. **SOLID Principles**:  
   - Ensures maintainable, scalable, and resilient code.  
   - Encourages single-responsibility design for better separation of concerns.  

2. **Clean Architecture**:  
   - **Use Cases**: Serve as the backbone of the business logic, isolating the `ViewModel` from any changes in business logic.  
   - **Layered Separation**:  
     - **UI Layer**: Manages user interactions and displays information.  
     - **Domain Layer**: Contains business logic and use cases.  
     - **Data Layer**: Manages data sources and abstracts data retrieval (via repositories).  

### **Modularization**  

CoinScope is structured into separate modules, ensuring each component is self-contained and reusable:  
- **`ui`**: Handles the user interface and presentation logic.  
- **`domain`**: Contains business logic, models, and use cases.  
- **`data`**: Implements data sources and repositories, providing abstractions for data access.  
- **`utils`**: Offers reusable utility functions and helpers.  
- **`app`**: Ties all the modules together into a cohesive application.  

### **Dependency Injection**  

- Utilizes **Dagger Hilt** as the Inversion of Control (IoC) container for managing dependencies efficiently across all modules.  


## 🚀 **Technologies Used**  

- **Jetpack Compose**: For building modern, declarative, and responsive UIs.  
- **Ktor**: A flexible and asynchronous HTTP client for seamless data fetching.  
- **Dagger Hilt**: Simplifies dependency injection across the app.  
- **Kotlin Coroutines & Flows**: Provides a robust and reactive way to handle asynchronous data streams.  
- **MVI Architecture**: Implements a unidirectional data flow for predictable and testable state management.  
