**Overview**
RecipeApp is a Spring Boot-based backend service that loads recipes data from an external API (https://dummyjson.com/recipes) into an in-memory H2 database and exposes RESTful API endpoints for searching and retrieving recipes.

**Features**
Loads recipe data from a 3rd party API into an in-memory H2 database.
Provides a search API with free text search for recipes based on name and cuisine.
Exposes an endpoint to fetch a recipe by its ID.

**Getting Started**
1. Prerequisites
    a. Java 17+ installed
    b. Maven for building the project
    c. Spring Boot 3.x
2. Clone the Repository
   in bash
   a. git clone https://github.com/your-repository/recipeapp.git
   b. cd recipeapp
3. Build and Run the Application
   Using Maven:
   a. mvn clean install
   b. mvn spring-boot:run
4. Configuration
   The external API URL (https://dummyjson.com/recipes) is configurable in application.properties or application.yml. The default property is:
   third.party.api.recipes.url=https://dummyjson.com/recipes

**Database**
The application uses an H2 in-memory database. The data is loaded on application startup and is cleared when the application is stopped.

To view the H2 console:
Open your browser and go to http://localhost:8080/h2-console.
Login with:
JDBC URL: jdbc:h2:mem:recipe
Username: sa
Password: password

**API Endpoints**
1. search endpoint
   http://localhost:8080/api/recipes/search?query=pizza
2. get recipe by ID
   http://localhost:8080/api/recipes/{id}
