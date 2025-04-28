package co.spa.projectmanagement.integrators.validators;

import co.spa.projectmanagement.integrators.dto.IntegratorUpdateDTO;
import jakarta.validation.ConstraintValidator;



public class IntegratorPartialUpdateValidator implements ConstraintValidator<ValidIntegratorUpdate, IntegratorUpdateDTO> {
    @Override
    public void initialize(ValidIntegratorUpdate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(IntegratorUpdateDTO integratorUpdateDTO, jakarta.validation.ConstraintValidatorContext constraintValidatorContext) {
        boolean isValid = true;

        if (integratorUpdateDTO.getName() != null && (integratorUpdateDTO.getName().length() < 2 || integratorUpdateDTO.getName().length() > 100)) {
            isValid = false;
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Name must be between 2 and 100 characters")
                    .addPropertyNode("name")
                    .addConstraintViolation();
        }

        if (integratorUpdateDTO.getEmail() != null && !integratorUpdateDTO.getEmail().matches("[A-Za-z0-9+_.-]+@(.+)$")) {
            isValid = false;
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Invalid email format")
                    .addPropertyNode("email")
                    .addConstraintViolation();
        }

        if (integratorUpdateDTO.getPhone() != null && (integratorUpdateDTO.getPhone().length() < 10 || integratorUpdateDTO.getPhone().length() > 15)) {
            isValid = false;
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Phone number must be between 10 and 15 characters")
                    .addPropertyNode("phone")
                    .addConstraintViolation();
        }

        return isValid;
    }


}
