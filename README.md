# CoffeeShop ☕️

The project simulates an simple coffee shop. Customer places the order in two ways: OrderToGo, OrderWebUI using postman
and provided UI respectively. Administrator of the coffee shop can create, update, delete coffees. Barista prepare an order 
if he is available to do so and have enough coffee in his espresso machine. 
Detailed description of the task is provided in the file "Backend Assignment - Coffee Shop"

## Table of Contents
- [Adjustments](#adjustments)
- [Requirements](#requirements)
- [Branches](#branches)
- [Installation](#installation)
- [Technologies](#technologies)

## Adjustments
  Task describes the relationship between orders and coffees as 1:1. To make it more realistic in the project is used m:n relationship
  which is more suitable for real-world situations.

## Requirements
1. Docker Desktop installed and running
2. Git
  
## Branches 
1. OmegaInterviews - branch setted up with env files for easier testing and overview of the project
2. main - serve as a starting point for future local development without env files
3. dev - copy of the main branch without env files

## Installation

1. **Clone the repository:**

   ```bash
   git clone https://github.com/pancho1995/coffeeShop.git
   ```

2. **Navigate to coffeeShop directory and open terminal**
  
   ```bash
   cd coffeeShop
   ```

3. **Checkout to OmegaInterview branch**
  
   ```bash
   git checkout OmegaInterview
   ```
   
4. **Build and host docker image**

   Build the docker image with the command:
    ```bash
    docker-compose build --no-cache
    ```
   Host docker image:
    ```bash
     docker-compose up -d
    ```
    This will setup application running.
   
5.  **URLs to visit**

   - http://localhost:3000/ - Shows the menu and entry point of the appication.
   - http://localhost:8000/ - Backend side of the application.
   - http://localhost:8000/laravel-websockets - Dashboard for tracking Events and Channels for websockets (click Connect button to see realtime).

## Technologies
- **NodeJS**: v14.18.1
- **React**: v18.2.0
- **PHP**: v8.0.11
- **Laravel**: v9.52.16
- **MySQL**: v8.3
- **Docker**: v25.0.2
  
