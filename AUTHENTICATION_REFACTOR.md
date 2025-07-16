# Authentication Refactor: Cookie-based to Header-based JWT

This document outlines the changes made to convert the ecommerce application from cookie-based JWT authentication to header-based JWT authentication.

## Overview

The application previously used HTTP-only cookies to store and transmit JWT tokens. This has been refactored to use Authorization headers with Bearer tokens for improved flexibility and modern authentication practices.

## Backend Changes

### 1. JwtUtils.java
- **Removed**: Cookie-related methods (`generateJwtCookie`, `getCleanJwtCookie`, `getJwtFromCookies`)
- **Added**: `getJwtFromAuthorizationHeader()` method to extract JWT from `Authorization: Bearer <token>` header
- **Modified**: Token generation methods now return string tokens instead of cookie objects
- **Removed**: Cookie-related imports and dependencies

### 2. AuthTokenFilter.java
- **Modified**: `parseJwt()` method now calls `getJwtFromAuthorizationHeader()` instead of `getJwtFromCookies()`
- **Updated**: Debug logging to reflect header-based extraction

### 3. AuthController.java
- **Modified**: `/signin` endpoint now returns JWT token in response body via `UserInfoResponse` object
- **Removed**: Cookie setting logic using `ResponseCookie` and `HttpHeaders.SET_COOKIE`
- **Modified**: `/signout` endpoint no longer clears cookies, just clears security context
- **Updated**: JWT token generation calls to use new header-based methods

### 4. Application Properties
- **Removed**: Cookie-related configurations:
  - `spring.ecom.app.jwtCookieName`
  - `spring.ecom.app.jwtCookieSecure`

### 5. CORS Configuration
- **Modified**: WebSecurityConfig and CorsConfig to:
  - Set `allowCredentials` to `false` (no longer needed for cookies)
  - Remove `Set-Cookie` from exposed headers
  - Keep `Authorization` in exposed headers for frontend access

## Frontend Changes

### 1. api.js (Axios Configuration)
- **Removed**: `withCredentials: true` setting
- **Added**: Request interceptor to automatically add `Authorization: Bearer <token>` header
- **Modified**: Reads JWT token from localStorage user object
- **Updated**: Error handling for 401 responses to clear localStorage

### 2. authService.js
- **Modified**: `login()` method now stores JWT token from response body in localStorage
- **Updated**: `getCurrentUser()` method validates token presence before API calls
- **Simplified**: Removed cookie-related logic and error handling
- **Updated**: All authentication methods to work with header-based tokens

### 3. authSlice.js (Redux)
- **Updated**: Comments to reflect header-based authentication
- **Simplified**: `getCurrentUser` thunk to rely on authService error handling
- **Modified**: Authentication flow descriptions in comments

## How the New Authentication Works

### Login Flow
1. User submits credentials to `/api/auth/signin`
2. Backend validates credentials and generates JWT token
3. Backend returns user info + JWT token in response body
4. Frontend stores user info + JWT token in localStorage
5. Subsequent requests automatically include `Authorization: Bearer <token>` header

### Protected Route Access
1. Frontend axios interceptor reads JWT token from localStorage
2. Adds `Authorization: Bearer <token>` header to all requests
3. Backend AuthTokenFilter extracts token from header
4. Backend validates token and sets authentication context

### Logout Flow
1. Frontend calls `/api/auth/signout`
2. Backend clears security context
3. Frontend removes user data (including JWT) from localStorage

## Testing the Changes

### Prerequisites
1. Ensure backend is running on the configured port (default: 8080)
2. Ensure frontend is running and can communicate with backend
3. Database should be accessible and properly configured

### Test Cases

#### 1. User Registration
- Navigate to registration page
- Submit valid user data
- Verify successful registration message

#### 2. User Login
- Navigate to login page
- Submit valid credentials
- Verify JWT token is stored in localStorage under 'user' key
- Verify user is redirected to appropriate page
- Check browser network tab to confirm Authorization header in subsequent requests

#### 3. Protected Routes
- Access any protected route (e.g., cart, profile, checkout)
- Verify automatic authentication via Authorization header
- Confirm user can access protected content

#### 4. Token Validation
- Check that `/api/auth/user` endpoint works with header-based token
- Verify user information is correctly retrieved and displayed

#### 5. Logout
- Click logout button
- Verify user data is cleared from localStorage
- Verify user is redirected to public pages
- Confirm subsequent requests don't include Authorization header

#### 6. Token Expiration
- Wait for token to expire (or manually modify token in localStorage)
- Attempt to access protected route
- Verify user is redirected to login page
- Confirm localStorage is cleared on 401 responses

## Debugging

### Backend Debugging
- Check application logs for JWT validation messages
- Verify Authorization header is being received in AuthTokenFilter
- Monitor security context creation for authenticated users

### Frontend Debugging
- Check localStorage for 'user' object containing JWT token
- Monitor network requests in browser dev tools for Authorization headers
- Check console for authentication-related error messages

## Benefits of Header-based Authentication

1. **Flexibility**: Easier to use in mobile apps and API clients
2. **Security**: No CSRF vulnerabilities (tokens not in cookies)
3. **Debugging**: Easier to inspect and test with tools like Postman
4. **Standards**: Follows OAuth 2.0 and modern authentication patterns
5. **Scalability**: Works better with microservices and distributed systems

## Potential Considerations

1. **XSS Vulnerability**: JWT tokens in localStorage are vulnerable to XSS attacks
   - Mitigation: Implement proper Content Security Policy (CSP)
   - Regular security audits and input sanitization

2. **Token Storage**: Consider using secure storage mechanisms for sensitive environments
3. **Token Refresh**: Consider implementing refresh token mechanism for longer sessions 