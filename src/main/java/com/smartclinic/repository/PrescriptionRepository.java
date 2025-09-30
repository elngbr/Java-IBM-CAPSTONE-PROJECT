package com.smartclinic.repository;

import com.smartclinic.model.Prescription;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Prescription entity operations.
 * Provides CRUD operations and custom queries for prescription management in MongoDB.
 */
@Repository
public interface PrescriptionRepository extends MongoRepository<Prescription, String> {
    
    /**
     * Find prescriptions by patient name.
     * Used for patient history and prescription lookup.
     */
    List<Prescription> findByPatientName(String patientName);
    
    /**
     * Find prescriptions by patient name (case-insensitive).
     * Supports flexible patient name search.
     */
    @Query("{'patientName': {$regex: ?0, $options: 'i'}}")
    List<Prescription> findByPatientNameIgnoreCase(String patientName);
    
    /**
     * Find prescription by appointment ID.
     * Links prescriptions to specific appointments.
     */
    Optional<Prescription> findByAppointmentId(Long appointmentId);
    
    /**
     * Find prescriptions by medication name.
     * Used for medication usage analysis and inventory management.
     */
    @Query("{'medication': {$regex: ?0, $options: 'i'}}")
    List<Prescription> findByMedicationContainingIgnoreCase(String medication);
    
    /**
     * Find prescriptions by dosage.
     * Used for dosage analysis and medication management.
     */
    List<Prescription> findByDosage(String dosage);
    
    /**
     * Find all prescriptions ordered by patient name.
     * Used for prescription management and reporting.
     */
    @Query(value = "{}", sort = "{'patientName': 1}")
    List<Prescription> findAllOrderByPatientName();
    
    /**
     * Find prescriptions containing specific text in doctor notes.
     * Used for prescription search and medical history analysis.
     */
    @Query("{'doctorNotes': {$regex: ?0, $options: 'i'}}")
    List<Prescription> findByDoctorNotesContaining(String searchText);
    
    /**
     * Count prescriptions by medication.
     * Used for medication usage statistics and reporting.
     */
    @Query(value = "{}", fields = "{'medication': 1}")
    List<Prescription> findAllMedications();
    
    /**
     * Find recent prescriptions by limiting results.
     * Used for dashboard and recent activity views.
     */
    @Query(value = "{}", sort = "{'_id': -1}")
    List<Prescription> findRecentPrescriptions();
    
    /**
     * Check if prescription exists for appointment.
     * Prevents duplicate prescriptions for the same appointment.
     */
    boolean existsByAppointmentId(Long appointmentId);
    
    /**
     * Count total prescriptions.
     * Used for dashboard statistics and reporting.
     */
    @Query(value = "{}", count = true)
    Long countTotalPrescriptions();
    
    /**
     * Find prescriptions by multiple appointment IDs.
     * Used for batch prescription lookup.
     */
    List<Prescription> findByAppointmentIdIn(List<Long> appointmentIds);
}
