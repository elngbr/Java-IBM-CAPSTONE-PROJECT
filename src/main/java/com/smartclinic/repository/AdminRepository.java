package com.smartclinic.repository;

import com.smartclinic.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Admin entity operations.
 * Provides CRUD operations and custom queries for admin management.
 */
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    
    /**
     * Find admin by email address.
     * Used for authentication and unique email validation.
     */
    Optional<Admin> findByEmail(String email);
    
    /**
     * Find admin by username.
     * Alternative login method for admin users.
     */
    Optional<Admin> findByUsername(String username);
    
    /**
     * Find admins by role.
     * Used for role-based access control and admin management.
     */
    List<Admin> findByRole(String role);
    
    /**
     * Find admins by name (case-insensitive search).
     * Used for admin search and management functionality.
     */
    @Query("SELECT a FROM Admin a WHERE LOWER(CONCAT(a.firstName, ' ', a.lastName)) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Admin> findByNameContainingIgnoreCase(@Param("name") String name);
    
    /**
     * Check if email already exists (excluding current admin).
     * Used for email uniqueness validation during updates.
     */
    @Query("SELECT COUNT(a) > 0 FROM Admin a WHERE a.email = :email AND a.adminId != :adminId")
    boolean existsByEmailAndIdNot(@Param("email") String email, @Param("adminId") Long adminId);
    
    /**
     * Check if username already exists (excluding current admin).
     * Used for username uniqueness validation during updates.
     */
    @Query("SELECT COUNT(a) > 0 FROM Admin a WHERE a.username = :username AND a.adminId != :adminId")
    boolean existsByUsernameAndIdNot(@Param("username") String username, @Param("adminId") Long adminId);
    
    /**
     * Find all available roles.
     * Used for role dropdown in admin management interface.
     */
    @Query("SELECT DISTINCT a.role FROM Admin a ORDER BY a.role")
    List<String> findAllRoles();
    
    /**
     * Count total admins.
     * Used for dashboard statistics and reporting.
     */
    @Query("SELECT COUNT(a) FROM Admin a")
    Long countTotalAdmins();
    
    /**
     * Count admins by role.
     * Used for role distribution analysis and reporting.
     */
    @Query("SELECT a.role, COUNT(a) FROM Admin a GROUP BY a.role")
    List<Object[]> countAdminsByRole();
    
    /**
     * Find super admins.
     * Used for system administration and high-level access control.
     */
    @Query("SELECT a FROM Admin a WHERE a.role = 'SUPER_ADMIN' ORDER BY a.firstName, a.lastName")
    List<Admin> findSuperAdmins();
}
