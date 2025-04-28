package co.spa.projectmanagement.integrators.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IntegratorPartialUpdateValidator.class) // Aquí debe ser solo la clase del validador, no el array
public @interface ValidIntegratorUpdate {
    String message() default "Invalid integrator data";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
