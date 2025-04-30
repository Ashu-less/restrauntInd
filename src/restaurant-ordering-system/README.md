# Restaurant Ordering System

## Overview
The Restaurant Ordering System is a Java-based application designed to facilitate the ordering process in a restaurant. It provides a user-friendly interface for customers to browse the menu, select items, and view their cart before checking out.

## Project Structure
The project is organized into the following directories and files:

```
restaurant-ordering-system
├── src
│   ├── App.java
│   ├── panels
│   │   ├── AppetizersPanel.java
│   │   ├── EntreesPanel.java
│   │   ├── DessertsPanel.java
│   │   ├── ViewCartPanel.java
│   │   └── CheckoutPanel.java
│   ├── models
│   │   ├── MenuItem.java
│   │   └── CartItem.java
│   ├── utils
│       └── Constants.java
├── assets
│   └── images
│       ├── appetizer1.jpg
│       ├── entree1.jpg
│       ├── dessert1.jpg
│       └── ...
├── README.md
└── .gitignore
```

## Features
- **Menu Panels**: Displays appetizers, entrees, and desserts with options to add or remove quantities.
- **View Cart**: Shows current selections with the ability to enter a tip and view totals.
- **Checkout**: Finalizes the order and displays the total cost.

## Setup Instructions
1. Clone the repository to your local machine.
2. Navigate to the project directory.
3. Compile the Java files using a Java compiler.
4. Run the `App.java` file to start the application.

## Usage
- Select items from the Appetizers, Entrees, or Desserts panels.
- Adjust quantities using the add and remove buttons.
- View your selections in the cart and enter a tip percentage.
- Proceed to checkout to view the final total.

## Contributing
Contributions are welcome! Please submit a pull request or open an issue for any enhancements or bug fixes.

## License
This project is licensed under the MIT License.