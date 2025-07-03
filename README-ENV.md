# Environment Variables Setup

This application uses environment variables for configuration. You need to create a `.env` file in the root directory of the backend project.

## Setup Instructions

1. Copy `.env.example` to `.env`:
   ```bash
   cp .env.example .env
   ```

2. Update the values in `.env` with your actual configuration:
   - Set your database credentials
   - Generate a secure JWT secret key (minimum 256 bits)
   - Adjust other settings as needed

## Required Environment Variables

| Variable | Description | Example |
|----------|-------------|---------|
| `DB_URL` | Database connection URL | `jdbc:postgresql://localhost:5432/ecommerce` |
| `DB_USERNAME` | Database username | `postgres` |
| `DB_PASSWORD` | Database password | `your_secure_password` |
| `JWT_SECRET` | JWT signing secret (min 256 bits) | `your_very_long_random_secret_key` |
| `JWT_EXPIRATION_MS` | JWT token expiration in milliseconds | `3000000` |
| `JWT_EXPIRATION_MS_EXTENDED` | Extended JWT expiration for "remember me" | `2592000000` |
| `JWT_COOKIE_NAME` | Name of the JWT cookie | `ecom-jwt` |
| `PROJECT_IMAGE_PATH` | Path for storing uploaded images | `images/` |

## Security Notes

- **NEVER** commit your `.env` file to version control
- Generate a strong, random JWT secret key
- Use different secrets for development and production
- Consider using environment variables directly in production instead of `.env` files

## Generating a Secure JWT Secret

You can generate a secure JWT secret using:

```bash
# Using openssl
openssl rand -base64 64

# Using Node.js
node -e "console.log(require('crypto').randomBytes(64).toString('base64'))"

# Using Python
python -c "import secrets; print(secrets.token_urlsafe(64))"
```
