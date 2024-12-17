**API Endpoints**
1. User Registration
Endpoint: POST /api/register

```
curl --location 'http://localhost:8080/api/register' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "mahesh",
    "password": "password123",
    "email": "mahesh@gmail.com"
}'
```
2. User Login (Generate API Key)
Endpoint: POST /api/login

```
curl --location 'http://localhost:8080/api/login' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'username=mahesh' \
--data-urlencode 'password=password123'
```

3. Submit Feedback
Endpoint: POST /api/feedback

```
curl --location 'http://localhost:8080/api/feedback' \
--header 'Content-Type: application/json' \
--header 'x-api-key: a44af377-5af4-4c5e-9943-9099f5b5c42c' \
--data 'This is my feedback content.'
```

4. List All Feedback for the User
Endpoint: GET /api/feedback

```
curl --location 'http://localhost:8080/api/feedback' \
--header 'x-api-key: a44af377-5af4-4c5e-9943-9099f5b5c42c'
```
5. Update Feedback
Endpoint: PUT /api/feedback/{feedbackId}

```
curl --location --request PUT 'http://localhost:8080/api/feedback/100000' \
--header 'Content-Type: application/json' \
--header 'x-api-key: a44af377-5af4-4c5e-9943-9099f5b5c42c' \
--data '"Updated feedback content Again"'
```

6. Delete Feedback
Endpoint: DELETE /api/feedback

```
curl --location --request DELETE 'http://localhost:8080/api/feedback' \
--header 'x-api-key: a44af377-5af4-4c5e-9943-9099f5b5c42c' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'feedbackId=100000'
```

7. Logout
Endpoint: POST /api/logout

```
curl --location 'http://localhost:8080/api/logout' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'username=mahesh'
```
