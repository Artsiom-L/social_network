package edu.lysianok.social.validators;

import edu.lysianok.social.dto.RegistrationDto;
import edu.lysianok.social.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RegistrationValidator implements Validator {
    private UserRepository userRepository;
    public static final String STRING_SIZE = "Please enter at least {min} and at most {max} characters.";
    public static final String REQUIRED_VALUE = "Admissible value from {min} to {max}.";


    public RegistrationValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return RegistrationDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        RegistrationDto registrationDto = RegistrationDto.class.cast(o);
        if (userRepository.findByUsername(registrationDto.getUsername()) != null) {
            errors.rejectValue("username", "duplicate.userForm.username");
        }
        if (!registrationDto.getPassword().equals(registrationDto.getPasswordConfirm())) {
            errors.rejectValue("passwordConfirm", "diff.userForm.passwordConfirm");
        }
        Pattern pattern = Pattern.compile("^\\w*[<>!=)({}\\]\\[]+\\w*$");
        Matcher matcher = pattern.matcher(registrationDto.getUsername());
        if (matcher.matches()) {
            errors.rejectValue("username", "illegal.characters");
        }
    }
}
