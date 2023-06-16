# Book Api StudyCase

## Installation
To run this project on your local machine, follow these steps:

1. Clone the repo: `git clone https://github.com/yahyakara/bookApiStudyCase`


## Parameters
* -Dcucumber.filter.tags :Filter the test based on tags : @regression @smoke
* 
### To run test on the local
```sh
mvn clean test -Dcucumber.filter.tags="@regression"
```



### To run test on the docker
## Parameters
* TAG :Filter the test based on tags : @regression @smoke

```sh
cd /bookApiStudyCase
ENV=uat TAG=@regression docker-compose up --build
```



## Reports
### Local (Allure should be installed)
cd /bookApiStudyCase/target
```allure serve```

### Docker
* http://localhost:5050/allure-docker-service/projects/default/reports/latest/index.html?redirect=false

## Dependencies
* RestAssured
* Cucumber
* TestNg
* Allure report
* WireMock
* Jackson
* Lombok

Scenarios 
1. [x]  User can get book information with get book service
2. [x]  Scenario: User can't add book similar book to list
3. [x]  Add a book to the list with invalid payload
4. [x]  User should not miss the book title or author
5. [x]  Add a book to the list
