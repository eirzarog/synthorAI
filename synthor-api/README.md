The user operations includes:

Key Components:

User Entity - Complete user model with all necessary fields, relationships, and helper methods
Repository Layer - Both standard JPA methods and custom query implementations
Service Layer - Comprehensive business logic for all user operations
Controller Layer - RESTful endpoints with proper security annotations
DTOs - Request/response objects for clean API contracts
Mappers - MapStruct mapping between entities and DTOs
Criteria - For flexible user searching and filtering
Custom Exceptions - Specific exceptions for user-related errors
Validators - Custom validation annotations and logic

Key Features:

CRUD Operations - Create, read, update, delete users
Profile Management - Update profile info, avatar upload/removal
Account Management - Activate, deactivate, lock/unlock accounts
Email Verification - Complete email verification workflow
Password Management - Reset, change passwords with security
Search & Filtering - Advanced user search with criteria
Security - Method-level security with role-based access
Audit Trail - Inherits from AuditableEntity for tracking changes
Soft Delete - Users can be soft deleted instead of hard deleted

The code follows Spring Boot best practices with proper validation, security, logging, 
and error handling. All operations are properly secured with role-based access 
control and include comprehensive logging for monitoring and debugging.