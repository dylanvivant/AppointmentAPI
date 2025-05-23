package fr.univlr.info.AppointmentAPIV1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.univlr.info.AppointmentAPIV1.controller.AppointmentDateConstraint;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Entity
@AppointmentDateConstraint
public class Appointment {
    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Doctor doctorObj;

    private String doctor;
    private Date startDate, endDate;
    private String patient;

    // Constructeurs existants
    public Appointment() {
    }

    public Appointment(String doctor, Date start, Date end, String patient) {
        this.doctor = doctor;
        this.startDate = start;
        this.endDate = end;
        this.patient = patient;
    }

    // Getters & Setters existants
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date start) {
        this.startDate = start;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date end) {
        this.endDate = end;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    // Nouveaux getters & setters pour doctorObj
    public Doctor getDoctorObj() {
        return doctorObj;
    }

    public void setDoctorObj(Doctor doctorObj) {
        this.doctorObj = doctorObj;
    }

    @Override
    public String toString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        return "Appointment{" +
                "id=" + id +
                ", doctor='" + doctor + '\'' +
                ", startDate=" + df.format(startDate) +
                ", endDate=" + df.format(endDate) +
                ", patient='" + patient + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return id.equals(that.id) && Objects.equals(doctor, that.doctor) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(patient, that.patient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, doctor, startDate, endDate, patient);
    }
}