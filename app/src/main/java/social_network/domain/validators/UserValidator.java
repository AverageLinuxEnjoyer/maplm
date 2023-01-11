package social_network.domain.validators;

import social_network.domain.User;
import social_network.domain.exceptions.ValidationException;

/**
 * Class for validating a user.
 */
public class UserValidator implements Validator<User> {
    @Override
    public void validate(User entity) throws ValidationException {
        if (entity == null) {
            throw new ValidationException("Entity must not be null");
        }
        if (entity.getFirstName() == null || entity.getFirstName().equals("")) {
            throw new ValidationException("First name must not be null or empty");
        }
        if (entity.getLastName() == null || entity.getLastName().equals("")) {
            throw new ValidationException("Last name must not be null or empty");
        }
        if (entity.getEmail() == null || entity.getEmail().equals("")) {
            throw new ValidationException("Email must not be null or empty");
        }
    }
}
