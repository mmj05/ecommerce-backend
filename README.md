# E-Commerce Backend API

A comprehensive **Spring Boot REST API** for a full-featured e-commerce platform built with **Java 17** and **PostgreSQL**. This backend provides secure, scalable endpoints for managing products, orders, users, and payments with role-based access control.

## 🌐 Live Demo

**Frontend Application**: [https://flipdot.onrender.com/](https://flipdot.onrender.com/)

Experience the full e-commerce platform in action! The live demo showcases all the features including user registration, product browsing, shopping cart, and order management.

> **Note**: This README covers the backend API. The live demo shows the complete frontend application that consumes this API.

## 🚀 Features

### Authentication & Authorization
- **JWT-based Authentication** with header-based token transmission
- **Role-based Access Control** (USER, ADMIN, SELLER)
- **Secure Password Encryption** using BCrypt
- **Token Validation** and automatic authentication filtering

### Product Management
- **CRUD Operations** for products and categories
- **Image Upload** and file management
- **Pagination & Sorting** for product listings
- **Category-based Organization** with nested relationships

### Shopping & Orders
- **Shopping Cart Management** with persistent storage
- **Complete Order Workflow** from cart to completion
- **Payment Integration** with multiple payment methods
- **Order History** and tracking for users and sellers

### User Management
- **User Profiles** with customizable information
- **Address Management** for shipping and billing
- **Password Change** functionality
- **Account Settings** and preferences

### Admin & Seller Features
- **Admin Dashboard** for platform management
- **Seller Portal** with order management
- **Statistics & Analytics** for sellers
- **Category and Product Administration**

## 🛠 Tech Stack

- **Framework**: Spring Boot 3.3.6
- **Security**: Spring Security + JWT
- **Database**: PostgreSQL with JPA/Hibernate
- **Build Tool**: Maven
- **Authentication**: JSON Web Tokens (JWT)
- **Password Encryption**: BCrypt
- **Object Mapping**: ModelMapper
- **Code Generation**: Lombok
- **Containerization**: Docker & Docker Compose

## 📋 Prerequisites

- **Java 17** or higher
- **Maven 3.6+**
- **PostgreSQL 12+**
- **Docker** (optional, for containerized deployment)

## 🚀 Quick Start

### Using Docker Compose (Recommended)

```bash
# Clone the repository
git clone <repository-url>
cd ecommerce-backend

# Start all services (backend + database)
docker-compose up --build

# Access the API at http://localhost:8080
```

### Manual Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd ecommerce-backend
   ```

2. **Setup Database**
   ```bash
   # Create PostgreSQL database
   createdb ecommerce
   ```

3. **Configure Environment**
   ```bash
   # Copy environment template
   cp .env.example .env
   
   # Edit .env with your database credentials and JWT secret
   ```

4. **Run the application**
   ```bash
   # Using Maven wrapper
   ./mvnw spring-boot:run
   
   # Or using Maven directly
   mvn spring-boot:run
   ```

## ⚙️ Configuration

### Environment Variables

Create a `.env` file in the root directory with the following variables:

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


## 📱 API Endpoints

### Authentication
- `POST /api/auth/signup` - User registration
- `POST /api/auth/signin` - User login
- `POST /api/auth/signout` - User logout
- `GET /api/auth/user` - Get current user info

### Public Endpoints
- `GET /api/public/products` - Get all products (with pagination)
- `GET /api/public/products/{id}` - Get product details
- `GET /api/public/categories` - Get all categories

### Protected Endpoints

#### Products (Admin/Seller)
- `POST /api/admin/categories/{categoryId}/products` - Add product
- `PUT /api/admin/products/{productId}` - Update product
- `DELETE /api/admin/products/{productId}` - Delete product

#### Categories (Admin)
- `POST /api/public/categories` - Create category
- `PUT /api/admin/categories/{categoryId}` - Update category
- `DELETE /api/admin/categories/{categoryId}` - Delete category

#### Shopping Cart
- `POST /api/carts/products/{productId}/quantity/{quantity}` - Add to cart
- `GET /api/carts` - Get all carts
- `GET /api/carts/users/cart` - Get user's cart
- `PUT /api/carts/products/{productId}/quantity/{quantity}` - Update cart item
- `DELETE /api/carts/{cartId}/product/{productId}` - Remove from cart

#### Orders
- `POST /api/order/users/payments/{paymentMethod}` - Place order
- `GET /api/orders` - Get order history

#### User Management
- `GET /api/addresses` - Get user addresses
- `POST /api/addresses` - Add new address
- `PUT /api/addresses/{addressId}` - Update address
- `DELETE /api/addresses/{addressId}` - Delete address

#### Seller Dashboard
- `GET /api/seller/orders` - Get seller orders
- `GET /api/seller/stats` - Get seller statistics

## 🐳 Docker Deployment

### Development
```bash
# Start only database
docker-compose up postgres

# Run application locally against Docker database
./mvnw spring-boot:run -Dspring-boot.run.profiles=docker
```

### Production
```bash
# Create production environment file
cp .env.example .env

# Update .env with production values
# Start all services
docker-compose --env-file .env up -d
```


## 🔒 Security Features

- **JWT Token Authentication** with header-based transmission
- **Role-based Authorization** (USER, ADMIN, SELLER)
- **CORS Configuration** for cross-origin requests
- **Password Encryption** using BCrypt
- **Request Validation** and input sanitization
- **Security Headers** and CSRF protection

## 📂 Project Structure

```
src/main/java/com/ecommerce/ecom/
├── config/           # Configuration classes
├── controller/       # REST controllers
├── exceptions/       # Custom exceptions and handlers
├── model/           # JPA entities
├── payload/         # DTOs and request/response objects
├── repositories/    # JPA repositories
├── security/        # Security configuration and JWT
├── service/         # Business logic layer
└── util/            # Utility classes
```

## 🧪 Testing

```bash
# Run all tests
./mvnw test

# Run with coverage
./mvnw test jacoco:report
```

## 📊 Database Schema

The application uses the following main entities:
- **User** - User accounts with roles
- **Product** - Product catalog
- **Category** - Product categories
- **Cart/CartItem** - Shopping cart functionality
- **Order/OrderItem** - Order management
- **Address** - User addresses
- **Payment** - Payment information

## 🚀 Deployment

### Local Development
1. Ensure PostgreSQL is running
2. Configure environment variables
3. Run `./mvnw spring-boot:run`

### Production
1. Build Docker image: `docker build -t ecommerce-backend .`
2. Deploy with external database
3. Configure environment variables for production
4. Set up monitoring and logging

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/new-feature`)
3. Commit your changes (`git commit -am 'Add new feature'`)
4. Push to the branch (`git push origin feature/new-feature`)
5. Create a Pull Request


## 📚 Additional Documentation

- [Environment Setup Guide](README-ENV.md)
- [Docker Deployment Guide](README-DOCKER.md)
- [Authentication Refactor Notes](AUTHENTICATION_REFACTOR.md)
- [Spring Boot Reference Guide](HELP.md)

## 🐛 Troubleshooting

### Common Issues

1. **Database Connection Issues**
   - Verify PostgreSQL is running
   - Check database credentials in `.env`
   - Ensure database exists

2. **JWT Token Issues**
   - Verify JWT_SECRET is properly set
   - Check token expiration settings
   - Ensure proper Authorization header format

3. **File Upload Issues**
   - Check PROJECT_IMAGE_PATH configuration
   - Verify directory permissions
   - Ensure adequate disk space


---

**Built with ❤️ using Spring Boot for modern e-commerce solutions**