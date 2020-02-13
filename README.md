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

