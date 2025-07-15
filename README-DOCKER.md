# Docker Deployment Guide

This guide explains how to deploy the Ecommerce Backend application using Docker.

## Prerequisites

- Docker installed on your system
- Docker Compose installed
- At least 2GB of free disk space
- Ports 8080 and 5432 available

## Quick Start

### 1. Using Docker Compose (Recommended)

```bash
# Clone the repository and navigate to the project directory
cd ecommerce-backend

# Build and start all services
docker-compose up --build

# Or run in detached mode
docker-compose up --build -d
```

The application will be available at `http://localhost:8080`

### 2. Using Docker only

```bash
# Build the image
docker build -t ecommerce-backend .

# Run with external PostgreSQL
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://your-db-host:5432/ecommerce \
  -e SPRING_DATASOURCE_USERNAME=your-username \
  -e SPRING_DATASOURCE_PASSWORD=your-password \
  ecommerce-backend
```

## Configuration

### Environment Variables

The application supports the following environment variables:

| Variable | Description | Default |
|----------|-------------|---------|
| `SPRING_DATASOURCE_URL` | Database URL | `jdbc:postgresql://postgres:5432/ecommerce` |
| `SPRING_DATASOURCE_USERNAME` | Database username | `postgres` |
| `SPRING_DATASOURCE_PASSWORD` | Database password | `postgres123` |
| `SPRING_APP_JWTSECRET` | JWT secret key | (default provided) |
| `SERVER_PORT` | Application port | `8080` |
| `PROJECT_IMAGE` | Image storage path | `images/` |

### Profiles

To use Docker-specific configuration:

```bash
docker run -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=docker \
  ecommerce-backend
```

## Development Setup

### 1. Start only the database

```bash
docker-compose up postgres
```

### 2. Run the application locally

```bash
# Use Docker database with local application
mvn spring-boot:run -Dspring-boot.run.profiles=docker
```

## Production Deployment

### 1. Environment-specific configuration

Create `.env` file:

```env
POSTGRES_PASSWORD=your-secure-password
SPRING_APP_JWTSECRET=your-jwt-secret-key
SPRING_DATASOURCE_PASSWORD=your-secure-password
```

### 2. Use with docker-compose

```bash
docker-compose --env-file .env up -d
```

### 3. External Database

For production, consider using an external managed database:

```bash
docker run -d \
  --name ecommerce-backend \
  -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://your-rds-endpoint:5432/ecommerce \
  -e SPRING_DATASOURCE_USERNAME=your-username \
  -e SPRING_DATASOURCE_PASSWORD=your-password \
  -v /path/to/images:/app/images \
  ecommerce-backend
```

## Useful Commands

### View logs
```bash
# All services
docker-compose logs -f

# Specific service
docker-compose logs -f ecommerce-backend
```

### Stop services
```bash
docker-compose down
```

### Stop and remove volumes
```bash
docker-compose down -v
```

### Rebuild images
```bash
docker-compose build --no-cache
```

### Access database
```bash
docker-compose exec postgres psql -U postgres -d ecommerce
```

## Health Checks

The application includes health checks:

- Application: `http://localhost:8080/actuator/health`
- Database connectivity is verified automatically

## Troubleshooting

### Common Issues

1. **Port already in use**
   ```bash
   # Change ports in docker-compose.yml
   ports:
     - "8081:8080"  # Use different host port
   ```

2. **Database connection issues**
   ```bash
   # Check if PostgreSQL is ready
   docker-compose logs postgres
   
   # Restart services
   docker-compose restart
   ```

3. **Image storage issues**
   ```bash
   # Ensure proper permissions
   mkdir -p images
   chmod 755 images
   ```

4. **Memory issues**
   ```bash
   # Increase memory limit
   docker run --memory=1g ecommerce-backend
   ```

## Security Considerations

- Change default passwords in production
- Use environment variables for sensitive data
- Consider using Docker secrets for production
- Regularly update base images
- Use non-root user (already configured)

## Monitoring

Add monitoring services to docker-compose.yml:

```yaml
  prometheus:
    image: prom/prometheus
    ports:
      - "9090:9090"
    # Add configuration as needed
```

## Backup

### Database backup
```bash
docker-compose exec postgres pg_dump -U postgres ecommerce > backup.sql
```

### Restore database
```bash
docker-compose exec -T postgres psql -U postgres ecommerce < backup.sql
``` 