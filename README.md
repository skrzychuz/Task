## About
Simple REST service which will return details of given Github repository. Details include:
* full name of repository
* description of repository
* git clone url
* number of stargazers
* date of creation (ISO format)

The API of the service looks as follows:
``
GET /repositories/{owner}/{repository-name}
``
```
{
"fullName": "...",
"description": "...",
"cloneUrl": "...",
"stars": 0,
"createdAt": "..."
}
```
## Deploying
This is a Spring Boot Application:
- generate an application JAR file which contains Embedded Tomcat and run as a Java application.
- start as Unix/Linux services by using `init.d`
- create a traditional WAR file and deploy to a Tomcat.


