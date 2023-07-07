### The Spring REST API “Phone contacts”
This hone contacts application allows adding/editing and deleting contacts data. Single contact is represented by the following data:
1. Contact name
2. Contact emails. One contact may have multiple emails
3. Contact phone number. One contact may have multiple phone numbers
# User should have a possibility to:
1. Register in the app, login and password should be provided during registration
2. Login to the app
3. Add new contact
4. Edit existing contact
5. Delete existing contact
6. Get list of existing contacts

From additional task in this application was:
- Added swagger documentation to the project-
- Added ability to upload image for each contact when create/update contact

Requirements:

- Use any relational database to store contacts data. Take into account that schema should be designed in accordance with 1st and 2nd normal forms
- Use Spring Boot, Spring Web, Spring Security and Spring Data + Hibernate
- Should give access only to authorized users, so each user should have his own list of phone contacts (Use Spring Security)
- Be a RESTful webservice from a client perspective
- When contact is added or edited, emails and phone numbers should be validated so thatit is not possible to add phone number like “+38-asdas” or email like “aa@”. Also every phone number and email should be unique per contact. So it should not be possible to add already existing email, the same for phone numbers
Contact names should be unique
- Have unit and integration tests

Additional work as bonus which was done:
- Add swagger documentation to the project
- Add ability to upload image for each contact when create/update contact

Use any relational database to store contacts data. Take into account that schema should be designed in accordance with 1st and 2nd normal forms
- Use Spring Boot, Spring Web, Spring Security and Spring Data + Hibernate
- Should give access only to authorized users, so each user should have his own list of phone contacts (Use Spring Security)
- Be a RESTful webservice from a client perspective

## Getting Started
1. Clone the repository: 
  https://github.com/Joroboro253/Test_Task_Phone_Contacts.git
2. Navigate to the project directory: 
  cd Test_Task_Phone_Contacts
3. Configure the database connection in the `application.properties` file.
4. 4. Build the project and package it into a JAR file: 
  mvn clean install
5. Run the application:
java -jar target/Test_Task_Phone_Contacts.jar
6. Open a web browser and go to [http://localhost:8080] to access the application.
7. For more detailed API documentation, please refer to the link:
   http://localhost:8080/swagger-ui/index.html#/search-controller/search

## License
The Spring PetClinic sample application is released under version 2.0 of the Apache License.

