package fr.univlr.info.AppointmentAPIV1.controller;

import fr.univlr.info.AppointmentAPIV1.model.Appointment;
import fr.univlr.info.AppointmentAPIV1.model.Doctor;
import fr.univlr.info.AppointmentAPIV1.store.AppointmentRepository;
import fr.univlr.info.AppointmentAPIV1.store.DoctorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class DoctorController {
    private final DoctorRepository doctorRepository;

    public DoctorController(DoctorRepository doctorRepository, AppointmentRepository appointmentRepository) {
        this.doctorRepository = doctorRepository;
    }

    // Récupère tous les médecins
    @GetMapping("/doctors")
    ResponseEntity<Collection<Doctor>> all() {
        List<Doctor> doctors = doctorRepository.findAll();
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    // Récupère un médecin par son nom
    @GetMapping("/doctors/{name}")
    ResponseEntity<Doctor> one(@PathVariable String name) {
        Doctor doctor = doctorRepository.findByName(name);
        if (doctor == null) {
            throw new DoctorNotFoundException(name);
        }
        return ResponseEntity.ok(doctor);
    }

    // Supprime un médecin par son nom
    @DeleteMapping("/doctors/{name}")
    ResponseEntity<Void> deleteDoctor(@PathVariable String name) {
        Doctor doctor = doctorRepository.findByName(name);
        if (doctor == null) {
            throw new DoctorNotFoundException(name);
        }

        // Vérifie si le médecin a des rendez-vous
        if (!doctor.getAppointments().isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        doctorRepository.delete(doctor);
        return ResponseEntity.ok().build();
    }

    // Récupère tous les rendez-vous d'un médecin
    @GetMapping("/doctors/{name}/appointments")
    ResponseEntity<Collection<Appointment>> getAppointmentsForDoctor(@PathVariable String name) {
        Doctor doctor = doctorRepository.findByName(name);
        if (doctor == null) {
            throw new DoctorNotFoundException(name);
        }
        return ResponseEntity.ok(doctor.getAppointments());
    }
}