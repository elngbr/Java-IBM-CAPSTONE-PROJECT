package com.smartclinic.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Admin entity representing system administrators
 * Manages admin authentication and system-level operations
 */
@Entity
@Table(name = "admins")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    @JsonProperty("adminId")
    private Long adminId;

    @Column(name = "username", unique = true, nullable = false, length = 100)
    @NotNull(message = "Username cannot be null")
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 100, message = "Username must be between 3 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can only contain letters, numbers, and underscores")
    @JsonProperty("username")
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be a valid email address")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    @JsonProperty("email")
    private String email;

    @Column(name = "password_hash", nullable = false)
    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passwordHash;

    @Column(name = "first_name", nullable = false, length = 100)
    @NotNull(message = "First name cannot be null")
    @NotBlank(message = "First name cannot be blank")
    @Size(max = 100, message = "First name must not exceed 100 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "First name can only contain letters and spaces")
    @JsonProperty("firstName")
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    @NotNull(message = "Last name cannot be null")
    @NotBlank(message = "Last name cannot be blank")
    @Size(max = 100, message = "Last name must not exceed 100 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Last name can only contain letters and spaces")
    @JsonProperty("lastName")
    private String lastName;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Role cannot be null")
    @JsonProperty("role")
    private AdminRole role = AdminRole.ADMIN;

    @Column(name = "permissions", columnDefinition = "JSON")
    @JsonProperty("permissions")
    private String permissions;

    @Column(name = "last_login")
    @JsonProperty("lastLogin")
    private LocalDateTime lastLogin;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;

    @Column(name = "is_active", nullable = false)
    @JsonProperty("isActive")
    private Boolean isActive = true;

    // Enum for Admin Roles
    public enum AdminRole {
        SUPER_ADMIN("Super Admin"),
        ADMIN("Admin"),
        SUPPORT("Support");

        private final String displayName;

        AdminRole(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    // Default constructor
    public Admin() {
    }

    // Constructor with required fields
    public Admin(String username, String email, String passwordHash, String firstName, String lastName) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Getters and Setters
    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public AdminRole getRole() {
        return role;
    }

    public void setRole(AdminRole role) {
        this.role = role;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    // Helper methods
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public boolean isActive() {
        return Boolean.TRUE.equals(isActive);
    }

    public boolean isSuperAdmin() {
        return AdminRole.SUPER_ADMIN.equals(role);
    }

    public void updateLastLogin() {
        this.lastLogin = LocalDateTime.now();
    }

    // equals, hashCode, and toString methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return Objects.equals(adminId, admin.adminId) &&
                Objects.equals(username, admin.username) &&
                Objects.equals(email, admin.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adminId, username, email);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role=" + role +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                '}';
    }
}
