# Smart Clinic Management System

A comprehensive clinic management system built with Spring Boot, featuring a hybrid database architecture using both MySQL and MongoDB.

## Features

- **Hybrid Database Architecture**: MySQL for relational data, MongoDB for document storage
- **Comprehensive Models**: Admin, Doctor, Patient, Appointment, and Prescription entities
- **Validation**: Extensive field validation using Jakarta Bean Validation
- **Security**: Role-based access control and secure password handling
- **Sample Data**: Pre-populated with realistic test data for development

## Architecture

This application follows a three-tier architecture:
- **Presentation Layer**: Thymeleaf templates and REST APIs
- **Business Layer**: Spring Boot services with business logic
- **Data Layer**: JPA repositories (MySQL) and MongoDB repositories

## Database Schema

### MySQL Tables
- `admins` - System administrators with role-based permissions
- `doctors` - Medical professionals with credentials and schedules  
- `patients` - Patient demographics and medical information
- `appointments` - Appointment scheduling and management
- `doctor_availability` - Doctor schedule exceptions

### MongoDB Collections
- `prescriptions` - Flexible prescription documents with nested medication data

## Quick Start

1. **Prerequisites**
   - Java 17+
   - Maven 3.6+
   - MySQL 8.0+
   - MongoDB 4.4+

2. **Database Setup**
   ```bash
   # Create MySQL database
   mysql -u root -p -e "CREATE DATABASE cms;"
   
   # Start MongoDB (if not running)
   mongod
   ```

3. **Run Application**
   ```bash
   mvn spring-boot:run
   ```

4. **Load Sample Data**
   - MySQL sample data: Execute `src/main/resources/db/sample_data.sql`
   - MongoDB sample data: Automatically loaded on application startup

## Documentation

- [Database Setup Guide](DATABASE_SETUP.md) - Detailed database configuration and sample data
- [Models Documentation](MODELS_README.md) - Comprehensive model architecture guide
- [Architecture Design](schema-architecture.md) - System architecture overview
- [User Stories](user_stories.md) - Complete user requirements
- [Database Schema](schema-design.md) - Database design decisions

## Sample Data

The application includes comprehensive sample data:
- **5 Doctors** across different specializations
- **6 Patients** with diverse medical histories  
- **8 Appointments** with various statuses and types
- **4 Prescriptions** with complex medication details
- **3 Admin Users** with different permission levels

## Technology Stack

- **Framework**: Spring Boot 3.1.5
- **Database**: MySQL 8.0, MongoDB 4.4+
- **ORM**: Spring Data JPA, Spring Data MongoDB
- **Validation**: Jakarta Bean Validation
- **Security**: Spring Security (configured)
- **Build Tool**: Maven
- **Java Version**: 17

## Development

### Running Tests
```bash
mvn test
```

### Building for Production
```bash
mvn clean package
```

### API Endpoints
Once running, the application exposes REST APIs at:
- `http://localhost:8080/api/doctors`
- `http://localhost:8080/api/patients`  
- `http://localhost:8080/api/appointments`
- `http://localhost:8080/api/prescriptions`

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is part of the IBM Java Developer Capstone course.