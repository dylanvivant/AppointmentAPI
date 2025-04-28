package fr.univlr.info.AppointmentAPIV1.store;

import fr.univlr.info.AppointmentAPIV1.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
}
