# 🍔 Self-Ordering Kiosk for a Burger Restaurant (KPH)

## 📘 General Description

This project simulates the operation of a self-ordering kiosk in a burger restaurant. The system guides the user through different screens to select a language, choose individual products or meal combos, review the order, and complete the payment using a simulated credit card banking system.

The system is designed following an object-oriented architecture, with a clear separation of responsibilities between classes, making it easier to maintain, understand, and extend.

> **WARNING:** Make sure your development environment is properly configured and that the translation and product files (such as `catalog.xml` and the language files) are correctly set up before running the program.

---

## 🧩 System Architecture

### Main Classes

- `BurgerKioskApplication`: Main class that launches the application.
- `KioskManager`: Controls the main program flow.
- `Context`: Groups the required resources (translator, menu, order, etc.).
- `SimpleKiosk`: Intermediate layer between the simulator and the application logic.
- `TranslatorManager` / `Translator`: Handles translations and language management.
- `MenuCard`: Reads and structures the restaurant menu.
- `Order`: Represents the customer's order.

### User Interface (`KioskScreen` and derived classes)

- `WellcomeScreen`: Welcome screen.
- `IdiomScreen`: Language selection screen.
- `OrderScreen`: Main order management screen.
- `SectionScreen`, `ProductScreen`, `MenuScreen`: Navigation through menu sections and products.
- `CarouselScreen`: Base class for screens displaying many items.
- `PurcheaseScreen`: Order confirmation and payment screen.

---

## 🔁 User Flow

1. Welcome screen.
2. Language selection.
3. Start the order.
4. Select a product category or meal combo.
5. Browse products using a carousel interface.
6. Review the order.
7. Payment screen (with simulated card payment).
8. Receipt printing and order storage.

---

## 🌐 Internationalization

The system allows users to change the language at any time. Text strings are automatically translated using a dictionary-based system managed by `TranslatorManager`. A translation file containing the required key phrases is needed for each supported language.

---

## 💾 Persistence

- Orders are stored on disk for later processing.
- The order number is saved and updated between executions.
- The system records each order in a file together with the selected products.

---

## 🧪 Simulators Used

- `BurgerSelfOrderKiosk`: Simulated graphical interface of the real kiosk.
- `UrjcBankServer`: Simulation of a credit card payment system.

---

## 👨‍💻 Authors

- **Víctor Hugo Oliveira Petroceli**
- **Rubén Ruiz Martín**
- **Ariel Rodríguez Lozano**
