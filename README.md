# luxoft-movie
Your task is to implement RESTful facade backend API with two mock up services:
1. Movie details service (movie: id, title, description)
2. Movie comments service (comment: movieId, username, message)  Movie details service and Movie comments service are simple standalone services providing mock up data. You can use any mock server library.  Requirements for the facade:
3. Endpoint for providing combined movie details and movie comments by movie id (in JSON)
4. Dummy endpoint for creating movie details. No communication with Movie details service. Endpoint should be authorized with ROLE_ADMIN
5. Dummy endpoint for creating Comment. No communication with Comments service. Endpoint should be authorized with ROLE_USER
6. Basic authentication
7. No user registration required. You can store mocked user data in memory.
8. Calls to the backend services should be implemented asynchronously
9. Movie details service responses should be cached with Least Frequently Used strategy
10. Comments service responses should also be cached but only as fallback. When the backend service is down, comments should be taken from cache.
1. Implemented with JAVA 8
2. Should start as a normal java application with embedded servlet container  Things that will be evaluated:
3. Implementation
4. REST principles, proper status codes, headers, error responses
5. Tests
6. Clean code
7. Commits
 
 You can use any open source library or framework you wish.  Please create an open repository on github and send us the link with your final implementation.   GOOD LUCK!
