# Ramly-burgers
 PROJECT -BITP3123 DISTRIBUTED APPLICATION DEVELOPMENT


## Overview
The Ramly Burger System consists of two Java Swing applications designed to streamline the processes of order management and inventory tracking. This README provides details about each application, their architecture, endpoints, and database schema.

## Applications

### 1. Cashier App
The Cashier App is designed for order management. It allows the cashier to:
- Key in customer orders.
- Proceed to payment.
- Display a receipt after payment.

### 2. Inventory App
The Inventory App is designed for managing inventory. It allows the user to:
- Display data fetched from a RESTful API.
- Update the quantity, price, and status of inventory items.
- Save updates to the SQL database upon clicking the update button.

## Architecture
![Architecure_Ramly_ Burger_System](https://github.com/AQILAHZAIDI01/Ramly-burgers/assets/74953405/e29ec934-b2d5-4e18-a646-2e385c41770f)



### Cashier App Architecture
The architecture of the Cashier App follows a typical three-layer structure:
1. **Presentation Layer**: Java Swing GUI for user interaction.
2. **Business Logic Layer**: Java classes handling the application logic.
3. **Data Access Layer**: PHP scripts interacting with the MySQL database.


### Inventory App Architecture
The architecture of the Inventory App also follows a three-layer structure:
1. **Presentation Layer**: Java Swing GUI for user interaction.
2. **Business Logic Layer**: Java classes handling the application logic.
3. **Data Access Layer**: PHP scripts interacting with the MySQL database.


## Middleware Endpoints
The middleware consists of PHP scripts that provide RESTful endpoints for interacting with the MySQL database.

### URL Endpoints
- **API Endpoint**: `http://localhost/rbsystem/api.php`
- **Database Endpoint**: `http://localhost/rbsystem/database.php`

### Functions/Features of the Middleware
- **API Endpoint (`api.php`)**:
  - Fetch inventory data
  - Handle CRUD operations for inventory items
- **Database Endpoint (`database.php`)**:
  - Connect to the MySQL database
  - Execute SQL queries for the application

## Database Schema
The database used in this project is MySQL. Below are the details of the tables involved:

### Tables
- **orders**
  - `order_id`: INT, Primary Key
  - `order_date`: TIMESTAMP
  - `price`: DECIMAL(5, 2)

-**order_details**
 - `o_details_id` : INT, Primary Key
 - `order_id` : INT, Foreign Key
 - `product_id` : INT
- `quantity_o` : INT
- `subtotal` : DECIMAL(5,2)
 
- **products**
  - `product_id`: INT, Primary Key
  - `product_name`: VARCHAR(255)
  - `quantity`: INT
  - `price`: DECIMAL(5, 2)
  - `status`: VARCHAR(255)
 
    ## PRESENTATION LINKS
    https://youtu.be/juCCvjZtrN8
