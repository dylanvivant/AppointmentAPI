package fr.univlr.info.AppointmentAPIV1.controller;

public class AppointmentConflictException extends RuntimeException {
    public AppointmentConflictException(String doctor, String startDate, String endDate) {
        super("Appointment conflict for doctor " + doctor +
                " between " + startDate + " and " + endDate);
    }
}