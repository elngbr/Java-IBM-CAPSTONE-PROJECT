# Smart Clinic Management System - User Stories

## User Story Template
**Title:**
_As a [user role], I want [feature/goal], so that [reason]._

**Acceptance Criteria:**
1. [Criteria 1]
2. [Criteria 2]
3. [Criteria 3]

**Priority:** [High/Medium/Low]
**Story Points:** [Estimated Effort in Points]
**Notes:**
- [Additional information or edge cases]

---

## Admin User Stories

### US-001: Admin Login
**Title:**
_As an admin, I want to log into the portal with my username and password, so that I can manage the platform securely._

**Acceptance Criteria:**
1. Admin can enter username and password on a login form
2. System validates credentials against the admin database
3. Upon successful login, admin is redirected to the admin dashboard
4. Failed login attempts display appropriate error messages
5. Session is created and maintained for the admin user

**Priority:** High
**Story Points:** 3
**Notes:**
- Implement secure password hashing
- Consider account lockout after multiple failed attempts
- Session timeout after inactivity

### US-002: Admin Logout
**Title:**
_As an admin, I want to log out of the portal, so that I can protect system access when I'm done._

**Acceptance Criteria:**
1. Admin can click a logout button from any admin page
2. System terminates the admin session immediately
3. Admin is redirected to the login page
4. All admin-specific data is cleared from the browser session
5. Attempting to access admin pages after logout redirects to login

**Priority:** High
**Story Points:** 2
**Notes:**
- Clear all session data and cookies
- Implement automatic logout on browser close

### US-003: Add Doctor to Portal
**Title:**
_As an admin, I want to add doctors to the portal, so that patients can book appointments with qualified medical professionals._

**Acceptance Criteria:**
1. Admin can access a "Add Doctor" form from the admin dashboard
2. Form includes fields for doctor's name, email, specialization, contact info, and credentials
3. System validates all required fields before submission
4. Doctor profile is created in the database with a unique ID
5. New doctor receives login credentials via email
6. Doctor appears in the system's doctor list immediately

**Priority:** High
**Story Points:** 5
**Notes:**
- Email validation and uniqueness check required
- Generate secure temporary password for new doctors
- Include doctor photo upload functionality

### US-004: Delete Doctor Profile
**Title:**
_As an admin, I want to delete a doctor's profile from the portal, so that I can remove doctors who are no longer available._

**Acceptance Criteria:**
1. Admin can view a list of all doctors with delete options
2. System prompts for confirmation before deletion
3. Deletion removes doctor from database and all associated data
4. Existing appointments with the doctor are handled appropriately (cancelled/reassigned)
5. Patients are notified if their appointments are affected
6. Action is logged for audit purposes

**Priority:** Medium
**Story Points:** 4
**Notes:**
- Implement soft delete to maintain data integrity
- Handle cascading effects on appointments and patient records
- Provide data export option before deletion

### US-005: View Appointment Statistics
**Title:**
_As an admin, I want to run a stored procedure in MySQL CLI to get the number of appointments per month, so that I can track usage statistics and make informed decisions._

**Acceptance Criteria:**
1. Admin can access a reports section in the admin dashboard
2. System provides a button to generate monthly appointment statistics
3. Report displays number of appointments per month for the current year
4. Data is retrieved using a MySQL stored procedure
5. Results are displayed in a clear, readable format (table/chart)
6. Admin can export the report data

**Priority:** Medium
**Story Points:** 3
**Notes:**
- Create stored procedure for efficient data retrieval
- Consider adding filters for date ranges
- Include additional metrics like doctor utilization

### US-006: Manage System Settings
**Title:**
_As an admin, I want to configure system settings, so that I can customize the portal's behavior and appearance._

**Acceptance Criteria:**
1. Admin can access a system settings page
2. Settings include appointment duration, working hours, and clinic information
3. Changes are saved immediately and applied system-wide
4. Settings validation prevents invalid configurations
5. Changes are logged with timestamp and admin user

**Priority:** Low
**Story Points:** 4
**Notes:**
- Include backup and restore functionality for settings
- Validate business rules (e.g., working hours must be logical)

---

## Patient User Stories

### US-007: View Doctors Without Login
**Title:**
_As a patient, I want to view a list of doctors without logging in, so that I can explore options before registering._

**Acceptance Criteria:**
1. Patient can access a public "Find Doctors" page without authentication
2. Page displays all available doctors with basic information (name, specialization, photo)
3. Doctor profiles show availability status but not specific time slots
4. Page includes search and filter functionality by specialization
5. Patient can view doctor details but cannot book appointments without login

**Priority:** High
**Story Points:** 3
**Notes:**
- Public page should be optimized for search engines
- Include doctor ratings/reviews if available
- Responsive design for mobile access

### US-008: Patient Registration
**Title:**
_As a patient, I want to sign up using my email and password, so that I can book appointments and manage my healthcare._

**Acceptance Criteria:**
1. Patient can access a registration form from the homepage
2. Form requires email, password, full name, phone number, and basic medical info
3. System validates email uniqueness and password strength
4. Account verification email is sent upon successful registration
5. Patient can log in immediately after email verification
6. Welcome email with portal usage instructions is sent

**Priority:** High
**Story Points:** 4
**Notes:**
- Implement GDPR-compliant data handling
- Include terms of service and privacy policy acceptance
- Password requirements: minimum 8 characters, mixed case, numbers

### US-009: Patient Login
**Title:**
_As a patient, I want to log into the portal, so that I can manage my bookings and access my medical information._

**Acceptance Criteria:**
1. Patient can enter email and password on the login form
2. System authenticates credentials against patient database
3. Successful login redirects to patient dashboard
4. Failed attempts show clear error messages
5. "Forgot Password" functionality is available
6. Session management keeps patient logged in appropriately

**Priority:** High
**Story Points:** 3
**Notes:**
- Implement secure session management
- Include "Remember Me" functionality
- Password reset via email verification

### US-010: Patient Logout
**Title:**
_As a patient, I want to log out of the portal, so that I can secure my account and personal medical information._

**Acceptance Criteria:**
1. Patient can easily find and click logout from any page
2. System immediately terminates the patient session
3. Patient is redirected to the homepage
4. All personal data is cleared from browser session
5. Subsequent page access requires re-authentication

**Priority:** High
**Story Points:** 2
**Notes:**
- Automatic logout after extended inactivity
- Clear session storage and cookies completely

### US-011: Book Appointment
**Title:**
_As a patient, I want to log in and book an hour-long appointment, so that I can consult with a doctor about my health concerns._

**Acceptance Criteria:**
1. Logged-in patient can access appointment booking from dashboard
2. Patient can search and select from available doctors by specialization
3. Calendar interface shows available time slots for selected doctor
4. System prevents booking of unavailable or past time slots
5. Appointment confirmation includes date, time, doctor, and location details
6. Both patient and doctor receive confirmation notifications

**Priority:** High
**Story Points:** 5
**Notes:**
- Default appointment duration is 1 hour
- Include appointment type selection (consultation, follow-up, etc.)
- Payment integration if required

### US-012: View Upcoming Appointments
**Title:**
_As a patient, I want to view my upcoming appointments, so that I can prepare accordingly and manage my schedule._

**Acceptance Criteria:**
1. Patient dashboard displays a list of upcoming appointments
2. Each appointment shows date, time, doctor name, and appointment type
3. Appointments are sorted chronologically (nearest first)
4. Patient can view appointment details including location and instructions
5. Option to cancel or reschedule appointments (within policy limits)
6. Past appointments are accessible in a separate section

**Priority:** Medium
**Story Points:** 3
**Notes:**
- Include appointment reminders via email/SMS
- Show cancellation/rescheduling policies
- Calendar integration options

### US-013: Update Patient Profile
**Title:**
_As a patient, I want to update my profile information, so that doctors have current and accurate information about me._

**Acceptance Criteria:**
1. Patient can access profile editing from account settings
2. Editable fields include contact info, emergency contacts, and medical history
3. Changes are validated and saved immediately
4. Sensitive medical information requires additional confirmation
5. Profile update history is maintained for audit purposes

**Priority:** Low
**Story Points:** 3
**Notes:**
- Include profile photo upload functionality
- Medical history section should be comprehensive but user-friendly

---

## Doctor User Stories

### US-014: Doctor Login
**Title:**
_As a doctor, I want to log into the portal, so that I can manage my appointments and access patient information._

**Acceptance Criteria:**
1. Doctor can log in using credentials provided by admin
2. Authentication validates against doctor database
3. Successful login redirects to doctor dashboard
4. Dashboard shows today's appointments and pending tasks
5. Failed login attempts are tracked and reported
6. Session security appropriate for medical data access

**Priority:** High
**Story Points:** 3
**Notes:**
- Enhanced security for medical data compliance
- Two-factor authentication consideration for future
- Audit trail for all login activities

### US-015: Doctor Logout
**Title:**
_As a doctor, I want to log out of the portal, so that I can protect patient data and maintain confidentiality._

**Acceptance Criteria:**
1. Doctor can securely log out from any page in the system
2. All patient data is immediately cleared from browser session
3. System logs the logout time for audit purposes
4. Doctor is redirected to a generic login page
5. Auto-logout occurs after inactivity period

**Priority:** High
**Story Points:** 2
**Notes:**
- Shorter inactivity timeout due to sensitive medical data
- Clear all cached patient information
- HIPAA compliance considerations

### US-016: View Appointment Calendar
**Title:**
_As a doctor, I want to view my appointment calendar, so that I can stay organized and manage my time effectively._

**Acceptance Criteria:**
1. Doctor dashboard includes a comprehensive calendar view
2. Calendar displays daily, weekly, and monthly views
3. Each appointment shows patient name, time, and appointment type
4. Color coding for different appointment types or priorities
5. Calendar is updated in real-time as appointments are booked/cancelled
6. Doctor can click on appointments for detailed information

**Priority:** High
**Story Points:** 4
**Notes:**
- Mobile-responsive calendar interface
- Integration with external calendar systems (Google, Outlook)
- Print functionality for offline reference

### US-017: Manage Availability
**Title:**
_As a doctor, I want to mark my unavailability, so that patients can only book appointments during my available slots._

**Acceptance Criteria:**
1. Doctor can access availability management from dashboard
2. Interface allows blocking specific time slots or entire days
3. Unavailable slots are immediately hidden from patient booking system
4. Doctor can set recurring unavailability patterns (e.g., every Wednesday afternoon)
5. System prevents overbooking during available hours
6. Changes to availability notify affected patients if necessary

**Priority:** High
**Story Points:** 4
**Notes:**
- Include reason codes for unavailability (vacation, conference, emergency)
- Bulk availability updates for multiple days
- Integration with hospital/clinic scheduling systems

### US-018: Update Doctor Profile
**Title:**
_As a doctor, I want to update my profile with specialization and contact information, so that patients have up-to-date and accurate information._

**Acceptance Criteria:**
1. Doctor can edit profile information from account settings
2. Updatable fields include specializations, bio, contact info, and credentials
3. Profile changes are reflected immediately in patient-facing doctor listings
4. Professional credentials require admin verification before display
5. Profile photos can be uploaded and updated
6. Changes are logged for audit purposes

**Priority:** Medium
**Story Points:** 3
**Notes:**
- Include medical license number and verification
- Professional biography with formatting options
- Languages spoken by the doctor

### US-019: View Patient Details
**Title:**
_As a doctor, I want to view patient details for upcoming appointments, so that I can be prepared and provide quality care._

**Acceptance Criteria:**
1. Doctor can access patient information from appointment details
2. Patient profile includes medical history, current medications, and allergies
3. Previous appointment notes and diagnoses are accessible
4. Contact information and emergency contacts are displayed
5. Information is presented in a clear, medical-professional format
6. Access to patient data is logged for compliance

**Priority:** High
**Story Points:** 4
**Notes:**
- HIPAA-compliant data access and display
- Quick access to critical patient information (allergies, chronic conditions)
- Integration with electronic health records (EHR) systems

### US-020: Add Appointment Notes
**Title:**
_As a doctor, I want to add notes and prescriptions after appointments, so that I can maintain proper medical records and continuity of care._

**Acceptance Criteria:**
1. Doctor can access note-taking interface during or after appointments
2. Notes section includes diagnosis, treatment plan, and follow-up instructions
3. Prescription module allows adding medications with dosage and duration
4. All notes are timestamped and attributed to the doctor
5. Notes are accessible for future appointments with the same patient
6. Prescriptions integrate with pharmacy systems if available

**Priority:** Medium
**Story Points:** 5
**Notes:**
- Rich text formatting for medical notes
- Template options for common diagnoses
- Digital signature for prescriptions where legally required

---

© IBM Corporation. All rights reserved.
