# Secure Notes API - Curl Commands

A Spring Boot REST API for managing secure notes with user authentication.

## Endpoints

### User Registration

#### Register a new user
```bash
curl -X POST http://localhost:8081/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "testpass"
  }'
```

**Response:** Returns the created user object with encoded password and USER role.

---

### Note Management

All note endpoints require authentication. You'll need to obtain a JWT token or use basic authentication.

#### Create a new note
```bash
curl -X POST http://localhost:8081/notes \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "title": "My First Note",
    "content": "This is the content of my note"
  }'
```

**Response:** HTTP 200 OK on successful creation.

#### Get all notes for the authenticated user
```bash
curl -X GET http://localhost:8081/notes \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

**Response:** Returns a JSON array of notes belonging to the authenticated user.

#### Delete a note by ID
```bash
curl -X DELETE "http://localhost:8081/notes?id=1" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

**Response:** HTTP 200 OK on successful deletion.

---

## Testing with Basic Authentication

If your application is configured to use basic authentication instead of JWT:

```bash
# For any authenticated endpoint, use:
curl -X GET http://localhost:8081/notes \
  -u username:password
```

---

## Example Workflow

1. **Register a user:**
```bash
curl -X POST http://localhost:8081/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john",
    "password": "mypassword123"
  }'
```

2. **Create a note:**
```bash
curl -X POST http://localhost:8081/notes \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "title": "Shopping List",
    "content": "Milk, Eggs, Bread"
  }'
```

3. **Get all notes:**
```bash
curl -X GET http://localhost:8081/notes \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

4. **Delete a note:**
```bash
curl -X DELETE "http://localhost:8081/notes?id=1" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

---

## Error Handling

- **400 Bad Request:** Invalid input data (e.g., null note or missing title)
- **401 Unauthorized:** Invalid or missing authentication
- **404 Not Found:** Resource not found
- **500 Internal Server Error:** Server-side errors

---

## Running the Application

1. Start MySQL database
2. Update database credentials in `src/main/resources/application.properties`
3. Run the application:
```bash
mvn spring-boot:run
```
4. The API will be available at `http://localhost:8081`

---

## Testing

Run the test suite:
```bash
mvn test
```

The application includes comprehensive unit tests for all endpoints.
