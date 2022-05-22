package com.doczilla.students.validation;

import org.springframework.validation.Errors;

public class StudentValidationErrorBuilder {

    public static StudentValidationError fromBindingErrors(Errors errors) {
        StudentValidationError error = new StudentValidationError("Validation failed. " + errors.getErrorCount() +
                " error(s)");
        errors.getAllErrors().forEach(objectError -> error.addValidationError(objectError.getDefaultMessage()));
        return error;
    }

}
