# GithubRepoSearch
---


#  **GitHub Repository Search API (Spring Boot)**


##  Overview


This project is a Spring Boot REST API that integrates with the GitHub Search API to fetch repositories based on user queries, store them in a PostgreSQL database, and provide endpoints to retrieve and filter the stored repositories.




---


## Features


* Search repositories from GitHub API

* Store search results in PostgreSQL

* Fetch stored repositories from database

* Filter by:


  * Programming language

  * Minimum stars

* Sort by:


  * Stars

  * Forks

  * Last updated date

* RESTful API design

* Exception handling

* Uses Spring Data JPA


---


##  Technologies Used


* Java 21

* Spring Boot

* Spring Data JPA

* PostgreSQL

* Hibernate

* Maven

* RestTemplate

* Postman (for testing)


---


## Project Structure


```

GithubRepoSearch

│

├── controller      → REST endpoints

├── service         → Business logic

├── repository      → Database access layer

├── entity          → JPA entities

├── dto             → Data Transfer Objects

├── exception       → Global exception handling

└── resources

    └── application.properties

```


---


##  Prerequisites


Make sure you have:


* Java 21+

* Maven

* PostgreSQL

* Git

* Postman (optional)


---


##  Database Configuration


Create a PostgreSQL database:


```sql

CREATE DATABASE github_search_db;

```


Update `application.properties`:


```properties

spring.datasource.url=jdbc:postgresql://localhost:5432/github_search_db

spring.datasource.username=postgresql

spring.datasource.password=your_password


spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

```


---


##  How to Run the Application


### 1. Clone the Repository


```bash

git clone https://github.com/Chakradhar-Malage/GithubRepoSearch.git

cd GithubRepoSearch

```


### 2. Build the Project


```bash

mvn clean install

```


### 3. Run the Application


```bash

mvn spring-boot:run

```


OR


Run from IDE using:


```

GithubRepoSearchApplication.java

```


---


##  API Endpoints


Base URL:


```

http://localhost:8089/api/github

```


---


### 1. Search GitHub & Save to DB


**POST** `/search`


#### URL


```

POST /api/github/search

```


#### Request Body


```json

{

  "query": "spring boot",

  "language": "Java",

  "sort": "stars"

}

```


#### Response


```json

{

  "message": "Success",

  "repositories": [ ... ]

}

```


---


### 2. Get Stored Repositories


**GET** `/repositories`


#### URL


```

GET /api/github/repositories

```


#### Optional Query Parameters


| Parameter | Description             |

| --------- | ----------------------- |

| language  | Filter by language      |

| minStars  | Minimum stars           |

| sort      | stars / forks / updated |


---


#### Examples


Get all repositories:


```

GET /api/github/repositories

```


Filter by language:


```

GET /api/github/repositories?language=Java

```


Filter by stars:


```

GET /api/github/repositories?minStars=1000

```


Sort by forks:


```

GET /api/github/repositories?sort=forks

```


Combined filters:


```

GET /api/github/repositories?language=Java&minStars=500&sort=stars

```


---


##  Pagination Note


The GitHub Search API returns 30 results per request by default.

Currently, this project fetches the first page only.



---


## Testing


You can test the APIs using:


* Postman

* Curl

* Browser 


Example:


```bash

curl http://localhost:8089/api/github/repositories

```


---


## Error Handling


* Invalid requests return proper HTTP status codes

* Global exception handler is implemented

* Database and API errors are handled gracefully


---


## ���‍��� Author


**Chakradhar**

---



