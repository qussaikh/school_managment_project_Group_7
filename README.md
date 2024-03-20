 # Spring boot school management project Group 7

You can log only in as an admin from this Account, 
because no one can create an account as an admin, 
but the admin can convert the USER to an ADMIN.  
 Admin login: 
 {
    "email":  "admin@mail.com",
    "password": "54321"
}
- Domain : school-mangement.eu-north-1.elasticbeanstalk.com 

Example to log in 
* URL Params
  POST http://school-mangement.eu-north-1.elasticbeanstalk.com:8080/api/v1/auth/authenticate
* Headers
  Content-Type: application/json
* Data Params
 {
  "email":  "admin@mail.com",
  "password": "54321"
  }
-------------------------------------------------------------
  
## Api controller

### (Public Access)

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

### Get Student details
* URL Params
  GET http://localhost:8080/student/getStudent/{stuId}
* Headers
  Content-Type: application/json
* Data Params


### Get all Students
* URL Params
  GET http://localhost:8080/student/getStudents
* Headers
  Content-Type: application/json
* Data Params

### Get course by id
* URL Params
  GET http://localhost:8080/course/getCourse/{courseId}
* Headers
  Content-Type: application/json
* Data Params


### Get all Courses
* URL Params
  GET http://localhost:8080/course/getCourses
* Headers
  Content-Type: application/json
* Data Params

-------------------------------------------------------------
### (ADMIN Access)

### Delete User 
* URL Params
  DELETE http://localhost:8080/api/v1/users/delete/{userEmail}
* Headers
  Content-Type: application/json
* Authorization: Bearer {{auth-token}}
* Data Params
    none


### Convert the USER to ADMIN 
* URL Params
  POST http://localhost:8080/api/v1/users/promoteToAdmin
* Headers
  Content-Type: application/json
* Authorization: Bearer {{auth-token}}
* Data Params

{
"email":  "qussai@mail.com"
}


### Update student
* URL Params
  PUT http://localhost:8080/student/update/{stuId}
* Headers
  Content-Type: application/json
* * Authorization: Bearer {{auth-token}}
* Data Params

{
"name": "vikrams",
"age": 11,
"dept": "javas"
}


### Delete Student
* URL Params
  DELETE http://localhost:8080/student/delete/{stuId}
* Headers
  Content-Type: application/json
* Authorization: Bearer {{auth-token}}
* Data Params


### Add course
* URL Params
  POST http://localhost:8080/course/save
* Headers
  Content-Type: application/json
* Authorization: Bearer {{auth-token}}
* Data Params

{
"title": "spring security ",
"abbreviation": "RJS",
"modules": 11,
"fee": 3000
}

  
### Update course
* URL Params
  PUT http://localhost:8080/course/update/{courseId}
* Headers
  Content-Type: application/json
* Authorization: Bearer {{auth-token}}
* Data Params

{  "title": "spring security ",
"abbreviation": "SSV",
"modules": 11,
"fee": 500.0
}

### Delete course
* URL Params
  DELETE http://localhost:8080/course/delete/{courseId}
* Headers
  Content-Type: application/json
* Authorization: Bearer {{auth-token}}
* Data Params


-------------------------------------------------------------
### (USER Access)

### Update User 
* URL Params
  PUT http://localhost:8080/api/v1/users/update/{userEmail}
* Headers
  Content-Type: application/json
* Authorization: Bearer {{auth-token}}
* Data Params

{
"firstname": "QUSSAI",
"lastname": "KHALIL",
"email":  "qussai@mail.com",
"password": "54321"
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

### Add student
* URL Params
  POST http://localhost:8080/student/save
* Headers
  Content-Type: application/json
* Authorization: Bearer {{auth-token}}
* Data Params

{
"name": "Mousa",
"age": 33,
"dept": "java spring JPA dev "
}

### Assign course to student
* URL Params
  PATCH http://localhost:8080/student/{stuId}/course/{courseId}
* Headers
  Content-Type: application/json
* Authorization: Bearer {{auth-token}}
* Data Params
  none













  
