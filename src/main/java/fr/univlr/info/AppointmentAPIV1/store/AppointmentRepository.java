package fr.univlr.info.AppointmentAPIV1.store;

import fr.univlr.info.AppointmentAPIV1.model.Appointment;
import fr.univlr.info.AppointmentAPIV1.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    // Trouve les rendez-vous qui se chevauchent pour un médecin
    @Query("SELECT a FROM Appointment a WHERE a.doctor = :doctor AND " +
            "((a.startDate <= :endDate AND a.endDate >= :startDate))")
    List<Appointment> findOverlappingAppointments(
            @Param("doctor") String doctor,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);
}