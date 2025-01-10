# Programming-Contest-Management-API

A RESTful API service for managing competitive programming users, their scores, and badges.

## API Documentation
https://documenter.getpostman.com/view/32910654/2sAYQWHsUV

## Features

- Complete CRUD operations for user management
- Automated badge assignment based on user scores
- Score-based user sorting with O(nlogn) complexity
- Comprehensive input validation and error handling
- JUnit test coverage

## User Data Structure

```json
{
  "userId": "string",
  "username": "string",
  "score": number,
  "badges": ["string"]
}
```

### Badge System

Badges are automatically awarded based on the following score criteria:
- **Code Ninja**: 1 <= Score < 30
- **Code Champ**: 30 <= Score < 60
- **Code Master**: 60 <= Score <= 100

## API Endpoints

### Get All Users
```
GET /users
```
Returns a list of all registered users, sorted by score.

### Get Specific User
```
GET /users/{userId}
```
Returns details for a specific user by their ID.

### Register New User
```
POST /users
```
Creates a new user registration. Required fields:
- userId (unique)
- username

Initial score is set to 0 and badges array is empty.

### Update User Score
```
PUT /users/{userId}
```
Updates a user's score. Only score field can be modified.

### Delete User
```
DELETE /users/{userId}
```
Removes a user from the system.

## Validation Rules

1. User Registration:
   - User ID must be unique
   - Username is required
   - Initial score is set to 0
   - Initial badges array is empty

2. Score Updates:
   - Score must be between 0 and 100
   - Only score field can be modified via PUT requests

3. Badge Rules:
   - Maximum of three unique badges per user
   - Badges are automatically assigned based on score thresholds
   - Duplicate badges are not allowed

## Error Handling

The API returns appropriate HTTP status codes:
- 200: Successful operation
- 201: Resource created successfully
- 400: Bad request (invalid input)
- 409: Conflict
- 404: Resource not found
- 500: Internal server error

## Testing

JUnit tests are included to verify:
- CRUD operations
- Badge assignment logic
- Score validation
- Error handling
- Sorting functionality

## Technical Requirements

1. Score-based sorting must maintain O(nlogn) time complexity
2. All endpoints must include proper input validation
3. Error responses must include appropriate HTTP status codes and error messages

## Getting Started

1. Clone the repository
2. Install dependencies
3. Configure your environment variables
4. Run the application
5. Access the API at `http://localhost:8080`
