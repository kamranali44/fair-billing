# Fair Billing Application

## Description

This java application takes a log file of user sessions in the format of `timestamp, userId, action`

An example of log file

```
14:02:58 ALICE99 Start
14:03:02 CHARLIE Start
14:03:33 ALICE99 Start
14:03:35 ALICE99 End
14:03:37 CHARLIE End
```

Based on the input, it prints a report containg the name, sessions and minimum possible duration in the format shown below:
```
ALICE99 4 240
CHARLIE 3 37
```

## Assumptions
1. The log file is from a single day, no overlapping of days
2. Data is assumed to be in sequential order of time
3. Invalid entries are ignored without throwing exceptions
4. Users can have multiple sessions at a time
5. If there is an End without a matching Start, the earliest time in the record will be assumed the Start time for calculation
6. If there is a Start without a matching End, the latest time of the file would be used for calculation

## Running the application

### Requirements
- Maven
- Java OpenJDK 11
- Docker


### Command line
Run the application by running the follwing command, replacing `<full-log-file-path>` with the actual path
```
java src/main/java/com/bt/fairbilling/MainRunner.java <full-log-file-path>
```

### Using Docker

#### Build jar with maven
Firstly build the java jar file with Maven by running the following command
```
mvn clean install
```

#### Copy log files to relevant directory
Ensure the log files you are planning to use are copied in the `src/main/resources/` folder

#### Build image
Now build the docker image by running the following command
```
docker build -t fair-billing .
```

#### Run docker image
Ensure you update the following command with the relevant log file you want to use by replacing `<file-name>` with your specific name.
```
docker run fairbilling /logs/<file-name>
```

### Run tests
You can run maven tests by using the mvn test command shown below
```
mvn test
```

You can also create a test report (created in target/surefire-reports/surefire-report.html ) by using the following command
```
mvn clean test surefire-report:report
```