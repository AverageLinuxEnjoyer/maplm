package social_network.domain.validators;

import social_network.domain.exceptions.ValidationException;

/**
 * Interface for validating an entity.
 */
public interface Validator<T> {
    /**
     * Validates an entity.
     * 
     * @param entity the entity to be validated
     * @throws ValidationException if the entity is not valid
     */
    void validate(T entity) throws ValidationException;
}
