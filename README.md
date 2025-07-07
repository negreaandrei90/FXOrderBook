# FX OrderBook

FX OrderBook is a Java CLI application that accepts commands from stdin and depending on the business logic, will print the corresponding results.  
It communicates with the order-service.jar using the HTTP protocol.

---

## Table of Contents

- [Features & How to Run](#features--how-to-run)
- [Technology & Tools](#technology--tools)
- [Running Tests](#running-tests)
- [Contact](#contact)

---

## Features & How to Run

The following commands are available within the application:

| Command |                          Arguments                           | Description                                                                                             |
|:--------|:------------------------------------------------------------:|:--------------------------------------------------------------------------------------------------------|
| new     | 'buy/sell' 'investment_ccy' 'counter_ccy' 'limit' 'validity' | Creates new order in the order book and displays its ID                                                 |
| cancel  |                             'id'                             | Cancels an existing order with the given ID                                                             |
| rates   |                                                              | Displays an overview of the current fx rates                                                            |
| orders  |                                                              | Displays currently existing fx orders (ordered by investment_ccy, counter_ccy, distance to market rate) |
| summary |                                                              | Displays overview of fx orders (grouped and ordered by investment_ccy, counter_ccy                      |
| help    |                                                              | Provides list of all commands and how to use them                                                       |
| exit    |                                                              | Terminates the program                                                                                  |

By making use of Command Prompt, make sure to start the server by running "java -jar order-service.jar" while in the files directory.  
Next, do the same thing in a different Command Prompt tab and run "java -jar FX_OrderBook.jar".  
The application should display a welcome message and will now accept commands.

---

## Technology & Tools

- IDE: IntelliJ IDEA 2025.1.2 (Community Edition)
- JDK: Amazon Corretto 21.0.7
- Build Tool: Maven v3.9.9
- Libraries:
  - lombok v1.18.38
  - fasterxml v2.19.1
  - log4j v2.25.0
  - junit v5.13.1

**Important (if running the project in Intellij):** In order to allow lombok to work properly, when running the project for the first time, you will be asked to enable annotation-processing. If not, head to:  
Project -> Settings -> Build, Execution, Deployment -> Compiler -> Annotation Processors -> Enable annotation processing

---

## Running Tests

The unit tests have been developed to verify the validators which have the assignment to verify the integrity and correctness of the data passed to the commands.  
ex. data type, format, values as well as loading of resources

Before running the tests, make sure the order-service server is running (on port 8888).  
In order to run the tests, head to the Maven tool window (situated on the right side of the IDE), expand: FX_OrderBook -> Lifecycle -> test  
A logger has been setup to track all HTTP communication between the CLI application and the service, and stores everything inside app.log, located in the logs directory.  
If running the 'FXOrderBook.jar' file, in the current directory a 'logs' directory will be created and inside it will be an 'app.log' file.

---

## Contact

**Negrea Gheorghe-Andrei**  
[negreaandrei90@proton.me](mailto:negreaandrei90@proton.me)  
[Linkedin](https://www.linkedin.com/in/andrei-negrea/)