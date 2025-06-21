package com.quizgame.validation;

import com.quizgame.dto.UserRegistrationDto;
import com.quizgame.dto.ChangePasswordRequestDto; // Yeni import
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        String password = null;
        String confirmPassword = null;

        if (obj instanceof UserRegistrationDto) {
            UserRegistrationDto user = (UserRegistrationDto) obj;
            password = user.getPassword();
            confirmPassword = user.getConfirmPassword();
            // Validasyon hatasını doğru alana bağlamak için
            if (password != null && !password.equals(confirmPassword)) {
                 context.disableDefaultConstraintViolation();
                 context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                        .addPropertyNode("confirmPassword")
                        .addConstraintViolation();
            }
        } else if (obj instanceof ChangePasswordRequestDto) { // Yeni kontrol
            ChangePasswordRequestDto changePasswordRequest = (ChangePasswordRequestDto) obj;
            password = changePasswordRequest.getNewPassword();
            confirmPassword = changePasswordRequest.getConfirmNewPassword();
            // Validasyon hatasını doğru alana bağlamak için
            if (password != null && !password.equals(confirmPassword)) {
                 context.disableDefaultConstraintViolation();
                 context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                        .addPropertyNode("confirmNewPassword") // Hatayı bu alana bağla
                        .addConstraintViolation();
            }
        }

        // Eğer ikisi de null ise veya eşleşiyorsa true döner
        return password != null && password.equals(confirmPassword);
    }
}