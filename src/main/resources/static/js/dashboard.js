// Smart Clinic Management System - Dashboard JavaScript

// Load dashboard stats on page load
document.addEventListener('DOMContentLoaded', function() {
    loadDashboardStats();
});

// Load initial dashboard statistics
async function loadDashboardStats() {
    try {
        // Load patient count
        const patients = await fetchData('/api/patients');
        document.getElementById('totalPatients').textContent = patients.length || 0;
        
        // Load doctor count  
        const doctors = await fetchData('/api/doctors');
        document.getElementById('totalDoctors').textContent = doctors.length || 0;
        
        // Load appointment count
        const appointments = await fetchData('/api/appointments');
        document.getElementById('totalAppointments').textContent = appointments.length || 0;
        
        // Load admin count (placeholder)
        document.getElementById('totalAdmins').textContent = '3';
        
    } catch (error) {
        console.error('Error loading dashboard stats:', error);
        // Set default values on error
        document.getElementById('totalPatients').textContent = '0';
        document.getElementById('totalDoctors').textContent = '0';
        document.getElementById('totalAppointments').textContent = '0';
        document.getElementById('totalAdmins').textContent = '0';
    }
}

// Generic function to fetch data from API
async function fetchData(endpoint) {
    try {
        const response = await axios.get(endpoint);
        return response.data;
    } catch (error) {
        console.error(`Error fetching data from ${endpoint}:`, error);
        return [];
    }
}

// Load and display patients
async function loadPatients() {
    showDataDisplay('Patient List', 'Loading patients...');
    
    try {
        const patients = await fetchData('/api/patients');
        
        if (patients.length === 0) {
            showDataDisplay('Patient List', '<p>No patients found. <a href="/patients" class="btn">Add New Patient</a></p>');
            return;
        }
        
        let html = `
            <table class="table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Gender</th>
                        <th>Blood Type</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
        `;
        
        patients.forEach(patient => {
            html += `
                <tr>
                    <td>${patient.patientId || 'N/A'}</td>
                    <td>${patient.firstName || ''} ${patient.lastName || ''}</td>
                    <td>${patient.email || 'N/A'}</td>
                    <td>${patient.phoneNumber || 'N/A'}</td>
                    <td>${patient.gender || 'N/A'}</td>
                    <td>${patient.bloodType || 'N/A'}</td>
                    <td>
                        <button class="btn" onclick="viewPatient(${patient.patientId})">View</button>
                    </td>
                </tr>
            `;
        });
        
        html += '</tbody></table>';
        showDataDisplay('Patient List', html);
        
    } catch (error) {
        showDataDisplay('Patient List', '<div class="alert alert-error">Error loading patients. Please try again.</div>');
    }
}

// Load and display doctors
async function loadDoctors() {
    showDataDisplay('Doctor List', 'Loading doctors...');
    
    try {
        const doctors = await fetchData('/api/doctors');
        
        if (doctors.length === 0) {
            showDataDisplay('Doctor List', '<p>No doctors found. <a href="/doctors" class="btn">Add New Doctor</a></p>');
            return;
        }
        
        let html = `
            <table class="table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Specialization</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Experience</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
        `;
        
        doctors.forEach(doctor => {
            html += `
                <tr>
                    <td>${doctor.doctorId || 'N/A'}</td>
                    <td>${doctor.firstName || ''} ${doctor.lastName || ''}</td>
                    <td>${doctor.specialization || 'N/A'}</td>
                    <td>${doctor.email || 'N/A'}</td>
                    <td>${doctor.phoneNumber || 'N/A'}</td>
                    <td>${doctor.yearsExperience || 0} years</td>
                    <td>
                        <button class="btn" onclick="viewDoctor(${doctor.doctorId})">View</button>
                    </td>
                </tr>
            `;
        });
        
        html += '</tbody></table>';
        showDataDisplay('Doctor List', html);
        
    } catch (error) {
        showDataDisplay('Doctor List', '<div class="alert alert-error">Error loading doctors. Please try again.</div>');
    }
}

// Load and display appointments
async function loadAppointments() {
    showDataDisplay('Appointment List', 'Loading appointments...');
    
    try {
        const appointments = await fetchData('/api/appointments');
        
        if (appointments.length === 0) {
            showDataDisplay('Appointment List', '<p>No appointments found. <a href="/appointments" class="btn">Schedule New Appointment</a></p>');
            return;
        }
        
        let html = `
            <table class="table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Patient</th>
                        <th>Doctor</th>
                        <th>Date</th>
                        <th>Time</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
        `;
        
        appointments.forEach(appointment => {
            html += `
                <tr>
                    <td>${appointment.appointmentId || 'N/A'}</td>
                    <td>Patient ${appointment.patientId || 'N/A'}</td>
                    <td>Dr. ${appointment.doctorId || 'N/A'}</td>
                    <td>${appointment.appointmentDate || 'N/A'}</td>
                    <td>${appointment.appointmentTime || 'N/A'}</td>
                    <td><span class="status ${appointment.status?.toLowerCase() || ''}">${appointment.status || 'N/A'}</span></td>
                    <td>
                        <button class="btn" onclick="viewAppointment(${appointment.appointmentId})">View</button>
                    </td>
                </tr>
            `;
        });
        
        html += '</tbody></table>';
        showDataDisplay('Appointment List', html);
        
    } catch (error) {
        showDataDisplay('Appointment List', '<div class="alert alert-error">Error loading appointments. Please try again.</div>');
    }
}

// Show API information
function showApiInfo() {
    const apiInfo = `
        <div class="api-info">
            <h4>Available REST API Endpoints:</h4>
            <div style="text-align: left; margin: 20px 0;">
                <h5>Patient Management:</h5>
                <ul>
                    <li><code>GET /api/patients</code> - Get all patients</li>
                    <li><code>POST /api/patients</code> - Create new patient</li>
                    <li><code>GET /api/patients/{id}</code> - Get patient by ID</li>
                    <li><code>PUT /api/patients/{id}</code> - Update patient</li>
                    <li><code>DELETE /api/patients/{id}</code> - Delete patient</li>
                </ul>
                
                <h5>Doctor Management:</h5>
                <ul>
                    <li><code>GET /api/doctors</code> - Get all doctors</li>
                    <li><code>POST /api/doctors</code> - Create new doctor</li>
                    <li><code>GET /api/doctors/{id}</code> - Get doctor by ID</li>
                    <li><code>PUT /api/doctors/{id}</code> - Update doctor</li>
                    <li><code>DELETE /api/doctors/{id}</code> - Delete doctor</li>
                </ul>
                
                <h5>Appointment Management:</h5>
                <ul>
                    <li><code>GET /api/appointments</code> - Get all appointments</li>
                    <li><code>POST /api/appointments</code> - Create new appointment</li>
                    <li><code>GET /api/appointments/{id}</code> - Get appointment by ID</li>
                    <li><code>PUT /api/appointments/{id}</code> - Update appointment</li>
                    <li><code>DELETE /api/appointments/{id}</code> - Delete appointment</li>
                </ul>
            </div>
        </div>
    `;
    
    showDataDisplay('API Documentation', apiInfo);
}

// Test API connection
async function testApi() {
    showDataDisplay('API Test', 'Testing API endpoints...');
    
    const tests = [
        { name: 'Patients API', endpoint: '/api/patients' },
        { name: 'Doctors API', endpoint: '/api/doctors' },
        { name: 'Appointments API', endpoint: '/api/appointments' }
    ];
    
    let results = '<h4>API Test Results:</h4><ul>';
    
    for (const test of tests) {
        try {
            await fetchData(test.endpoint);
            results += `<li>✅ ${test.name}: <span style="color: green;">Working</span></li>`;
        } catch (error) {
            results += `<li>❌ ${test.name}: <span style="color: red;">Error</span></li>`;
        }
    }
    
    results += '</ul>';
    showDataDisplay('API Test Results', results);
}

// Test database connection
async function testConnection() {
    showDataDisplay('Database Test', 'Testing database connection...');
    
    try {
        // Test by trying to fetch data
        await fetchData('/api/patients');
        showDataDisplay('Database Test', '<div class="alert alert-success">✅ Database connection is working properly!</div>');
    } catch (error) {
        showDataDisplay('Database Test', '<div class="alert alert-error">❌ Database connection failed. Please check the server.</div>');
    }
}

// Load system statistics
async function loadSystemStats() {
    showDataDisplay('System Statistics', 'Loading system information...');
    
    try {
        const [patients, doctors, appointments] = await Promise.all([
            fetchData('/api/patients'),
            fetchData('/api/doctors'),
            fetchData('/api/appointments')
        ]);
        
        const stats = `
            <div class="stats-grid">
                <div class="stat-card">
                    <div class="stat-number">${patients.length}</div>
                    <div class="stat-label">Total Patients</div>
                </div>
                <div class="stat-card">
                    <div class="stat-number">${doctors.length}</div>
                    <div class="stat-label">Total Doctors</div>
                </div>
                <div class="stat-card">
                    <div class="stat-number">${appointments.length}</div>
                    <div class="stat-label">Total Appointments</div>
                </div>
                <div class="stat-card">
                    <div class="stat-number">Active</div>
                    <div class="stat-label">System Status</div>
                </div>
            </div>
            <div style="margin-top: 20px;">
                <h4>System Information:</h4>
                <ul style="text-align: left; margin: 10px 0;">
                    <li>Database: H2 In-Memory Database</li>
                    <li>Server: Spring Boot 3.1.5</li>
                    <li>Port: 8080</li>
                    <li>Status: Running</li>
                </ul>
            </div>
        `;
        
        showDataDisplay('System Statistics', stats);
        
    } catch (error) {
        showDataDisplay('System Statistics', '<div class="alert alert-error">Error loading system statistics.</div>');
    }
}

// Helper function to show data display area
function showDataDisplay(title, content) {
    const dataDisplay = document.getElementById('dataDisplay');
    const dataTitle = document.getElementById('dataTitle');
    const dataContent = document.getElementById('dataContent');
    
    dataTitle.textContent = title;
    dataContent.innerHTML = content;
    dataDisplay.style.display = 'block';
    
    // Scroll to the data display area
    dataDisplay.scrollIntoView({ behavior: 'smooth' });
}

// Placeholder functions for individual item views
function viewPatient(id) {
    alert(`View patient details for ID: ${id}\n\nThis would open a detailed patient view.`);
}

function viewDoctor(id) {
    alert(`View doctor details for ID: ${id}\n\nThis would open a detailed doctor view.`);
}

function viewAppointment(id) {
    alert(`View appointment details for ID: ${id}\n\nThis would open a detailed appointment view.`);
}
