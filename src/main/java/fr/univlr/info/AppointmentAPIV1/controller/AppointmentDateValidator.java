package fr.univlr.info.AppointmentAPIV1.controller;

import fr.univlr.info.AppointmentAPIV1.model.Appointment;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

public class AppointmentDateValidator
        implements ConstraintValidator<AppointmentDateConstraint, Appointment> {
    @Override
    public void initialize(AppointmentDateConstraint dateCst) {
        // Pas besoin d'initialisation spécifique
    }

    @Override
    public boolean isValid(Appointment app, ConstraintValidatorContext ctxt) {
        // Vérifie que les dates de début et de fin ne sont pas nulles
        if (app.getStartDate() == null || app.getEndDate() == null) {
            return false;
        }

        // Vérifie que la date de début est avant la date de fin
        return app.getStartDate().before(app.getEndDate());
    }
}