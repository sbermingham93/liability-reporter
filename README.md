# Liability Reporter

## Description

Simple flexible Command Line App used to report on betting liability.
The service can read in csv and json files containing betting details 
from the local filesystem, and produces the Liability Reports written either 
to the console or csv files.

## Run The App Locally

1. Install JDK 1.8 or greater and configure JAVA_HOME (skip if this is already done). This can be tested by opening a terminal window and entering "java -version".
2. Clone the Repository onto your local machine or download the zip file with the contents of the repository and unzip.
3. Open a terminal window and change directory to the top level of the application (you should see the "gradle" and "src" folders if you are in the right place).
4. Enter "./gradlew build" from the terminal window. (This should compile, test and generate the executable .jar file)
5. Enter "java -jar build/libs/betting-report-generation-service-0.1-all.jar"

If successful, you should be prompted from the terminal to enter your desired input and output options.

## Run the Unit Tests Locally (Skip to step 4 if you have already run the app locally!)

1. Install JDK 1.8 or greater and configure JAVA_HOME (skip if this is already done). This can be tested by opening a terminal window and entering "java -version".
2. Clone the Repository onto your local machine or download the zip file with the contents of the repository and unzip.
3. Open a terminal window and change directory to the top level of the application (inside the "betting-report-generation-service" folder, you should see the "gradle" and "src" folders if you are in the right place).
4. Enter "./gradlew test" from the terminal window.


## Roadmap
Take in the user input for input,output file location - pass into the file loader and output
Create a service factory which will provide instances of service classes (or dependency injection framework...)
Add comments to the services
Add in some error handling, try/catch in sections
