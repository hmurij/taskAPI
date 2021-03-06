# taskAPI
### REST API for Tasks Management System.

The API caller is able to use these operations on API:

- Save task          - POST   - /api/tasks
- Update task        - PUT    - /api/tasks
- Delete task by id  - DELETE - /api/tasks/{id}
- Get all tasks      - GET    - /api/tasks
- Get task by id     - GET    - /api/tasks/{id}

Link to [Postman test data samples](https://www.postman.com/avionics-physicist-21440496/workspace/new-personal-workspace/collection/18662089-647b4c1d-650b-4ca4-ba48-15fb11d0f7c1)

## Installation

Download loggingapi folder or ```git pull https://github.com/hmurij/taskAPI.git``` 
Import existing Maven project and run /info-manager/src/main/java/com/cognizant/TasksManagementSystem.java or run jar file with java -jar taskApi.jar

Database available at http://localhost:8080/h2-console/ JDBC URL: jdbc:h2:mem:tasks

Swagger available at http://localhost:8080/swagger-ui.html

