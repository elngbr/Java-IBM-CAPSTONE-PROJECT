package com.smartclinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Web Controller for serving HTML pages
 * Handles the main web interface for the Smart Clinic Management System
 */
@Controller
@RequestMapping("/")
public class WebController {

    /**
     * Home page - Dashboard
     */
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Smart Clinic Management System");
        return "index";
    }

    /**
     * Patients management page
     */
    @GetMapping("/patients")
    public String patients(Model model) {
        model.addAttribute("title", "Patient Management");
        return "patients";
    }

    /**
     * Doctors management page
     */
    @GetMapping("/doctors")
    public String doctors(Model model) {
        model.addAttribute("title", "Doctor Management");
        return "doctors";
    }

    /**
     * Appointments management page
     */
    @GetMapping("/appointments")
    public String appointments(Model model) {
        model.addAttribute("title", "Appointment Management");
        return "appointments";
    }

    /**
     * Admin management page
     */
    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("title", "Administration");
        return "admin";
    }
}
