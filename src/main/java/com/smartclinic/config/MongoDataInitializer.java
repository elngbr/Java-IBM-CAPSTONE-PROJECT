package com.smartclinic.config;

import com.smartclinic.model.Prescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * MongoDB Data Initializer
 * Populates MongoDB with sample prescription documents
 */
@Component
@ConditionalOnBean(MongoTemplate.class)
public class MongoDataInitializer implements CommandLineRunner {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void run(String... args) throws Exception {
        // Check if data already exists
        long count = mongoTemplate.count(new Query(), Prescription.class);
        if (count > 0) {
            System.out.println("MongoDB already contains " + count + " prescriptions. Skipping initialization.");
            return;
        }

        System.out.println("Initializing MongoDB with sample prescription data...");
        
        // Create sample prescriptions
        List<Prescription> prescriptions = createSamplePrescriptions();
        
        // Insert prescriptions
        mongoTemplate.insertAll(prescriptions);
        
        System.out.println("Successfully inserted " + prescriptions.size() + " sample prescriptions into MongoDB.");
    }

    private List<Prescription> createSamplePrescriptions() {
        return Arrays.asList(
            createPrescription1(),
            createPrescription2(),
            createPrescription3(),
            createPrescription4()
        );
    }

    private Prescription createPrescription1() {
        // Patient: John Smith, Doctor: Dr. Sarah Johnson
        Prescription prescription = new Prescription();
        prescription.setAppointmentId(1L);
        prescription.setPatientId(1L);
        prescription.setDoctorId(1L);
        prescription.setPrescriptionDate(LocalDateTime.of(2025, 10, 15, 10, 45));

        // Patient Info
        Prescription.PatientInfo patientInfo = new Prescription.PatientInfo();
        patientInfo.setName("John Smith");
        patientInfo.setAge(45);
        patientInfo.setWeight(180.0);
        patientInfo.setAllergies(Arrays.asList("Penicillin", "Shellfish"));
        prescription.setPatientInfo(patientInfo);

        // Doctor Info
        Prescription.DoctorInfo doctorInfo = new Prescription.DoctorInfo();
        doctorInfo.setName("Dr. Sarah Johnson");
        doctorInfo.setLicenseNumber("MD123456");
        doctorInfo.setSpecialization("Internal Medicine");
        prescription.setDoctorInfo(doctorInfo);

        // Diagnosis
        Prescription.Diagnosis diagnosis = new Prescription.Diagnosis();
        diagnosis.setPrimary("Hypertension");
        diagnosis.setSecondary(Arrays.asList("Type 2 Diabetes", "Obesity"));
        diagnosis.setIcdCodes(Arrays.asList("I10", "E11.9", "E66.9"));
        prescription.setDiagnosis(diagnosis);

        // Medications
        Prescription.Medication medication1 = new Prescription.Medication();
        medication1.setMedicationName("Lisinopril");
        medication1.setGenericName("Lisinopril");
        medication1.setDosage("10mg");
        medication1.setFrequency("Once daily");
        medication1.setDuration("30 days");
        medication1.setQuantity(30);
        medication1.setRefills(2);
        medication1.setInstructions("Take with or without food. Monitor blood pressure regularly.");
        medication1.setSideEffects(Arrays.asList("Dizziness", "Dry cough"));
        medication1.setInteractions(Arrays.asList("NSAIDs", "Potassium supplements"));

        Prescription.Medication medication2 = new Prescription.Medication();
        medication2.setMedicationName("Metformin");
        medication2.setGenericName("Metformin HCl");
        medication2.setDosage("500mg");
        medication2.setFrequency("Twice daily with meals");
        medication2.setDuration("90 days");
        medication2.setQuantity(180);
        medication2.setRefills(3);
        medication2.setInstructions("Take with food to reduce stomach upset. Monitor blood glucose levels.");
        medication2.setSideEffects(Arrays.asList("Nausea", "Diarrhea", "Metallic taste"));
        medication2.setInteractions(Arrays.asList("Alcohol", "Contrast dyes"));

        prescription.setMedications(Arrays.asList(medication1, medication2));

        // Additional Instructions
        prescription.setAdditionalInstructions(Arrays.asList(
            "Follow up in 4 weeks for blood pressure check",
            "Continue low-sodium diet",
            "Exercise 30 minutes daily",
            "Monitor weight weekly"
        ));

        // Pharmacy Info
        Prescription.PharmacyInfo pharmacyInfo = new Prescription.PharmacyInfo();
        pharmacyInfo.setName("Central Pharmacy");
        pharmacyInfo.setAddress("123 Main St, New York, NY 10001");
        pharmacyInfo.setPhone("(555) 123-4567");
        pharmacyInfo.setFaxSent(true);
        pharmacyInfo.setSentAt(LocalDateTime.of(2025, 10, 15, 10, 50));
        prescription.setPharmacyInfo(pharmacyInfo);

        // Digital Signature
        Prescription.DigitalSignature signature = new Prescription.DigitalSignature();
        signature.setSigned(true);
        signature.setSignedAt(LocalDateTime.of(2025, 10, 15, 10, 47));
        signature.setSignatureHash("sha256:a1b2c3d4e5f6789012345678901234567890abcdef");
        prescription.setDigitalSignature(signature);

        prescription.setStatus(Prescription.PrescriptionStatus.ACTIVE);
        return prescription;
    }

    private Prescription createPrescription2() {
        // Patient: Lisa Garcia (Prenatal), Doctor: Dr. Emily Williams
        Prescription prescription = new Prescription();
        prescription.setAppointmentId(4L);
        prescription.setPatientId(4L);
        prescription.setDoctorId(3L);
        prescription.setPrescriptionDate(LocalDateTime.of(2025, 10, 18, 11, 30));

        // Patient Info
        Prescription.PatientInfo patientInfo = new Prescription.PatientInfo();
        patientInfo.setName("Lisa Garcia");
        patientInfo.setAge(37);
        patientInfo.setWeight(145.0);
        patientInfo.setAllergies(Arrays.asList());
        prescription.setPatientInfo(patientInfo);

        // Doctor Info
        Prescription.DoctorInfo doctorInfo = new Prescription.DoctorInfo();
        doctorInfo.setName("Dr. Emily Williams");
        doctorInfo.setLicenseNumber("MD345678");
        doctorInfo.setSpecialization("Pediatrics");
        prescription.setDoctorInfo(doctorInfo);

        // Diagnosis
        Prescription.Diagnosis diagnosis = new Prescription.Diagnosis();
        diagnosis.setPrimary("Pregnancy - Second Trimester");
        diagnosis.setSecondary(Arrays.asList("Prenatal Vitamins Needed"));
        diagnosis.setIcdCodes(Arrays.asList("Z34.90"));
        prescription.setDiagnosis(diagnosis);

        // Medications
        Prescription.Medication medication1 = new Prescription.Medication();
        medication1.setMedicationName("Prenatal Multivitamin");
        medication1.setGenericName("Prenatal Vitamin");
        medication1.setDosage("1 tablet");
        medication1.setFrequency("Once daily");
        medication1.setDuration("Until delivery");
        medication1.setQuantity(90);
        medication1.setRefills(3);
        medication1.setInstructions("Take with food to reduce nausea. Contains folic acid and iron.");
        medication1.setSideEffects(Arrays.asList("Mild nausea", "Constipation"));
        medication1.setInteractions(Arrays.asList("Calcium supplements - separate by 2 hours"));

        prescription.setMedications(Arrays.asList(medication1));

        // Additional Instructions
        prescription.setAdditionalInstructions(Arrays.asList(
            "Continue regular prenatal appointments",
            "Maintain healthy diet rich in calcium and protein",
            "Take prenatal vitamin with food to reduce nausea",
            "Next appointment in 4 weeks"
        ));

        // Pharmacy Info
        Prescription.PharmacyInfo pharmacyInfo = new Prescription.PharmacyInfo();
        pharmacyInfo.setName("Family Health Pharmacy");
        pharmacyInfo.setAddress("456 Oak Ave, Brooklyn, NY 11201");
        pharmacyInfo.setPhone("(555) 234-5678");
        pharmacyInfo.setFaxSent(true);
        pharmacyInfo.setSentAt(LocalDateTime.of(2025, 10, 18, 11, 35));
        prescription.setPharmacyInfo(pharmacyInfo);

        // Digital Signature
        Prescription.DigitalSignature signature = new Prescription.DigitalSignature();
        signature.setSigned(true);
        signature.setSignedAt(LocalDateTime.of(2025, 10, 18, 11, 32));
        signature.setSignatureHash("sha256:b2c3d4e5f6789012345678901234567890abcdef1");
        prescription.setDigitalSignature(signature);

        prescription.setStatus(Prescription.PrescriptionStatus.ACTIVE);
        return prescription;
    }

    private Prescription createPrescription3() {
        // Patient: James Taylor (Pediatric Asthma), Doctor: Dr. Emily Williams
        Prescription prescription = new Prescription();
        prescription.setAppointmentId(5L);
        prescription.setPatientId(5L);
        prescription.setDoctorId(3L);
        prescription.setPrescriptionDate(LocalDateTime.of(2025, 10, 19, 15, 20));

        // Patient Info
        Prescription.PatientInfo patientInfo = new Prescription.PatientInfo();
        patientInfo.setName("James Taylor");
        patientInfo.setAge(14);
        patientInfo.setWeight(110.0);
        patientInfo.setAllergies(Arrays.asList("Peanuts", "Tree Nuts"));
        prescription.setPatientInfo(patientInfo);

        // Doctor Info
        Prescription.DoctorInfo doctorInfo = new Prescription.DoctorInfo();
        doctorInfo.setName("Dr. Emily Williams");
        doctorInfo.setLicenseNumber("MD345678");
        doctorInfo.setSpecialization("Pediatrics");
        prescription.setDoctorInfo(doctorInfo);

        // Diagnosis
        Prescription.Diagnosis diagnosis = new Prescription.Diagnosis();
        diagnosis.setPrimary("Asthma");
        diagnosis.setSecondary(Arrays.asList("Allergic Rhinitis"));
        diagnosis.setIcdCodes(Arrays.asList("J45.9", "J30.9"));
        prescription.setDiagnosis(diagnosis);

        // Medications
        Prescription.Medication medication1 = new Prescription.Medication();
        medication1.setMedicationName("Albuterol Inhaler");
        medication1.setGenericName("Albuterol Sulfate HFA");
        medication1.setDosage("90mcg per puff");
        medication1.setFrequency("2 puffs every 4-6 hours as needed");
        medication1.setDuration("30 days");
        medication1.setQuantity(1);
        medication1.setRefills(2);
        medication1.setInstructions("Shake well before use. Rinse mouth after use. Use spacer if available.");
        medication1.setSideEffects(Arrays.asList("Tremor", "Rapid heartbeat", "Nervousness"));
        medication1.setInteractions(Arrays.asList("Beta-blockers"));

        Prescription.Medication medication2 = new Prescription.Medication();
        medication2.setMedicationName("Fluticasone Inhaler");
        medication2.setGenericName("Fluticasone Propionate HFA");
        medication2.setDosage("44mcg per puff");
        medication2.setFrequency("2 puffs twice daily");
        medication2.setDuration("30 days");
        medication2.setQuantity(1);
        medication2.setRefills(2);
        medication2.setInstructions("Use daily for prevention. Rinse mouth after use to prevent thrush.");
        medication2.setSideEffects(Arrays.asList("Hoarse voice", "Thrush", "Sore throat"));
        medication2.setInteractions(Arrays.asList("CYP3A4 inhibitors"));

        prescription.setMedications(Arrays.asList(medication1, medication2));

        // Additional Instructions
        prescription.setAdditionalInstructions(Arrays.asList(
            "Use peak flow meter daily and record readings",
            "Avoid known allergens (peanuts, tree nuts)",
            "Keep rescue inhaler available at all times",
            "Follow asthma action plan",
            "Return if symptoms worsen or peak flow drops below 80% of personal best"
        ));

        // Pharmacy Info
        Prescription.PharmacyInfo pharmacyInfo = new Prescription.PharmacyInfo();
        pharmacyInfo.setName("Pediatric Specialty Pharmacy");
        pharmacyInfo.setAddress("789 Pine St, Queens, NY 11375");
        pharmacyInfo.setPhone("(555) 345-6789");
        pharmacyInfo.setFaxSent(true);
        pharmacyInfo.setSentAt(LocalDateTime.of(2025, 10, 19, 15, 25));
        prescription.setPharmacyInfo(pharmacyInfo);

        // Digital Signature
        Prescription.DigitalSignature signature = new Prescription.DigitalSignature();
        signature.setSigned(true);
        signature.setSignedAt(LocalDateTime.of(2025, 10, 19, 15, 22));
        signature.setSignatureHash("sha256:c3d4e5f6789012345678901234567890abcdef12");
        prescription.setDigitalSignature(signature);

        prescription.setStatus(Prescription.PrescriptionStatus.ACTIVE);
        return prescription;
    }

    private Prescription createPrescription4() {
        // Patient: Susan Lee (Migraine), Doctor: Dr. Sarah Johnson
        Prescription prescription = new Prescription();
        prescription.setAppointmentId(8L);
        prescription.setPatientId(6L);
        prescription.setDoctorId(1L);
        prescription.setPrescriptionDate(LocalDateTime.of(2025, 10, 25, 16, 15));

        // Patient Info
        Prescription.PatientInfo patientInfo = new Prescription.PatientInfo();
        patientInfo.setName("Susan Lee");
        patientInfo.setAge(50);
        patientInfo.setWeight(135.0);
        patientInfo.setAllergies(Arrays.asList("Sulfa drugs"));
        prescription.setPatientInfo(patientInfo);

        // Doctor Info
        Prescription.DoctorInfo doctorInfo = new Prescription.DoctorInfo();
        doctorInfo.setName("Dr. Sarah Johnson");
        doctorInfo.setLicenseNumber("MD123456");
        doctorInfo.setSpecialization("Internal Medicine");
        prescription.setDoctorInfo(doctorInfo);

        // Diagnosis
        Prescription.Diagnosis diagnosis = new Prescription.Diagnosis();
        diagnosis.setPrimary("Migraine with Aura");
        diagnosis.setSecondary(Arrays.asList("Tension Headache"));
        diagnosis.setIcdCodes(Arrays.asList("G43.109", "G44.209"));
        prescription.setDiagnosis(diagnosis);

        // Medications
        Prescription.Medication medication1 = new Prescription.Medication();
        medication1.setMedicationName("Sumatriptan");
        medication1.setGenericName("Sumatriptan Succinate");
        medication1.setDosage("50mg");
        medication1.setFrequency("As needed for migraine");
        medication1.setDuration("30 days");
        medication1.setQuantity(9);
        medication1.setRefills(1);
        medication1.setInstructions("Take at onset of migraine. Do not exceed 2 doses in 24 hours. Wait 2 hours between doses.");
        medication1.setSideEffects(Arrays.asList("Chest pressure", "Dizziness", "Drowsiness"));
        medication1.setInteractions(Arrays.asList("MAO inhibitors", "Ergot alkaloids", "SSRIs"));

        Prescription.Medication medication2 = new Prescription.Medication();
        medication2.setMedicationName("Propranolol");
        medication2.setGenericName("Propranolol HCl");
        medication2.setDosage("40mg");
        medication2.setFrequency("Twice daily");
        medication2.setDuration("90 days");
        medication2.setQuantity(180);
        medication2.setRefills(2);
        medication2.setInstructions("Take for migraine prevention. Do not stop suddenly. Monitor blood pressure.");
        medication2.setSideEffects(Arrays.asList("Fatigue", "Dizziness", "Cold hands/feet"));
        medication2.setInteractions(Arrays.asList("Insulin", "Calcium channel blockers"));

        prescription.setMedications(Arrays.asList(medication1, medication2));

        // Additional Instructions
        prescription.setAdditionalInstructions(Arrays.asList(
            "Keep migraine diary to identify triggers",
            "Maintain regular sleep schedule",
            "Stay hydrated",
            "Avoid known migraine triggers (stress, certain foods)",
            "Follow up in 6 weeks to assess effectiveness"
        ));

        // Pharmacy Info
        Prescription.PharmacyInfo pharmacyInfo = new Prescription.PharmacyInfo();
        pharmacyInfo.setName("Island Pharmacy");
        pharmacyInfo.setAddress("987 Cedar Ave, Staten Island, NY 10301");
        pharmacyInfo.setPhone("(555) 456-7890");
        pharmacyInfo.setFaxSent(true);
        pharmacyInfo.setSentAt(LocalDateTime.of(2025, 10, 25, 16, 20));
        prescription.setPharmacyInfo(pharmacyInfo);

        // Digital Signature
        Prescription.DigitalSignature signature = new Prescription.DigitalSignature();
        signature.setSigned(true);
        signature.setSignedAt(LocalDateTime.of(2025, 10, 25, 16, 17));
        signature.setSignatureHash("sha256:d4e5f6789012345678901234567890abcdef123");
        prescription.setDigitalSignature(signature);

        prescription.setStatus(Prescription.PrescriptionStatus.ACTIVE);
        return prescription;
    }
}
