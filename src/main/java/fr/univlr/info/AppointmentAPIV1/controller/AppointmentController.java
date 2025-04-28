package fr.univlr.info.AppointmentAPIV1.controller;

import fr.univlr.info.AppointmentAPIV1.model.Appointment;
import fr.univlr.info.AppointmentAPIV1.store.AppointmentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class AppointmentController {
    private final AppointmentRepository apptRepository;

    public AppointmentController(AppointmentRepository apptRepository) {
        this.apptRepository = apptRepository;
    }

    // Récupère tous les rendez-vous
    @GetMapping("/appointments")
    ResponseEntity<Collection<Appointment>> all() {
        List<Appointment> appts = apptRepository.findAll();
        return new ResponseEntity<>(appts, HttpStatus.OK);
    }

    // Ajoute un nouveau rendez-vous
    @PostMapping("/appointments")
    ResponseEntity<Appointment> newAppointment(@Valid @RequestBody Appointment appt) {
        // Sauvegarde le rendez-vous dans la base de données
        Appointment savedAppt = apptRepository.save(appt);

        // Crée l'URI du rendez-vous créé en utilisant ServletUriComponentsBuilder
        // Cette méthode crée une URI absolue basée sur la requête actuelle
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri() // Utilise l'URI de la requête actuelle
                .path("/{id}")          // Ajoute le chemin avec l'ID
                .buildAndExpand(savedAppt.getId()) // Remplace {id} par l'ID réel
                .toUri();              // Convertit en URI

        // Retourne une réponse avec le statut CREATED et l'emplacement du rendez-vous créé
        return ResponseEntity.created(location).body(savedAppt);
    }

    // Récupère un rendez-vous spécifique par son ID
    @GetMapping("/appointments/{id}")
    ResponseEntity<Appointment> one(@PathVariable Long id) {
        // Recherche le rendez-vous dans la base de données
        Optional<Appointment> appt = apptRepository.findById(id);

        // Si le rendez-vous existe, le retourne avec un statut OK
        // Sinon, lance une exception AppointmentNotFoundException
        return appt.map(appointment -> ResponseEntity.ok().body(appointment))
                .orElseThrow(() -> new AppointmentNotFoundException(id));
    }

    // Met à jour un rendez-vous existant
    @PutMapping("/appointments/{id}")
    ResponseEntity<Appointment> replaceAppointment(@PathVariable Long id, @Valid @RequestBody Appointment newAppt) {
        // Recherche le rendez-vous dans la base de données
        return apptRepository.findById(id)
                .map(appointment -> {
                    // Copie les propriétés du nouveau rendez-vous vers l'ancien, en ignorant l'ID
                    appointment.setDoctor(newAppt.getDoctor());
                    appointment.setStartDate(newAppt.getStartDate());
                    appointment.setEndDate(newAppt.getEndDate());
                    appointment.setPatient(newAppt.getPatient());

                    // Sauvegarde le rendez-vous mis à jour
                    Appointment updatedAppt = apptRepository.save(appointment);
                    return ResponseEntity.ok().body(updatedAppt);
                })
                .orElseThrow(() -> new AppointmentNotFoundException(id));
    }

    // Supprime un rendez-vous
    @DeleteMapping("/appointments/{id}")
    ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        // Vérifie si le rendez-vous existe
        if (!apptRepository.existsById(id)) {
            throw new AppointmentNotFoundException(id);
        }

        // Supprime le rendez-vous
        apptRepository.deleteById(id);

        // Retourne une réponse avec le statut OK
        return ResponseEntity.ok().build();
    }

    // Supprime tous les rendez-vous
    @DeleteMapping("/appointments")
    ResponseEntity<Void> deleteAll() {
        // Supprime tous les rendez-vous
        apptRepository.deleteAll();

        // Retourne une réponse avec le statut OK
        return ResponseEntity.ok().build();
    }
}