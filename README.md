 Spring boot school managment project Group 7

## Api controller

### Register Admin
Creates a new Admin and returns the new object.
* URL Params
  POST http://localhost:8080/api/v1/auth/adminRegister
* Headers

Content-Type: application/json
* Data Params

_{
"firstname": "Qussai",
"lastname": "Khalil",
"email":  "qussai@mail.com",
"password": "password"
}_

### Register User
Creates a new User and returns the new object.
* URL Params
  POST http://localhost:8080/api/v1/auth/userRegister
* Headers
  Content-Type: application/json
* Data Params

{
"firstname": "Qussai",
"lastname": "Khalil",
"email":  "qussai@mail.com",
"password": "password"
}

### Change the password
* URL Params
  PATCH http://localhost:8080/api/v1/users
* Headers
  Content-Type: application/json
  Authorization: Bearer {{auth-token}}
* Data Params

{
"currentPassword": "password",
"newPassword": "newPassword",
"confirmationPassword":  "newPassword"
}

### Login again and update the token
* URL Params
  POST http://localhost:8080/api/v1/auth/authenticate
* Headers
  Content-Type: application/json
* Data Params

{
"email":  "qussai@mail.com",
"password": "newPassword"
}


### Add student
* URL Params
  POST http://localhost:8080/student/save
* Headers
  Content-Type: application/json
* Data Params

{
"name": "Mousa",
"age": 33,
"dept": "java spring JPA dev "
}

### Add Course
* URL Params
  POST http://localhost:8080/course/save
* Headers
  Content-Type: application/json
* Data Params

{
"title": "spring security ",
"abbreviation": "RJS",
"modules": 11,
"fee": 3000
}

### assign course to student
* URL Params
  PUT http://localhost:8080/student/{stuId}/course/{courseId}
* Headers
  Content-Type: application/json
* Data Params
  none 


### get Student details
* URL Params
  GET http://localhost:8080/student/getStudent/{stuId}
* Headers
  Content-Type: application/json
* Data Params

### get all Students
* URL Params
  GET http://localhost:8080/student/getStudents
* Headers
  Content-Type: application/json
* Data Params

### Delete Student 
* URL Params
  GET http://localhost:8080/student/delete/{stuId}
* Headers
  Content-Type: application/json
* Data Params

### get Course by id
* URL Params
  GET http://localhost:8080/course/getCourse/{courseId}
* Headers
  Content-Type: application/json
* Data Params

### get all Courses
* URL Params
  GET http://localhost:8080/course/getCourses
* Headers
  Content-Type: application/json
* Data Params

### Delete course
* URL Params
  GET http://localhost:8080/course/delete/{courseId}
* Headers
  Content-Type: application/json
* Data Params