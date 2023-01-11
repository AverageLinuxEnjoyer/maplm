package social_network.repo.decorators;

import social_network.domain.Entity;
import social_network.domain.exceptions.ValidationException;
import social_network.domain.validators.Validator;
import social_network.repo.Repo;

public class ValidatorRepoDecorator<ID, E extends Entity<ID>> extends AbstractRepoDecorator<ID, E> {
    private Validator<E> validator;

    public ValidatorRepoDecorator(Repo<ID, E> repo, Validator<E> validator) {
        super(repo);
        this.validator = validator;
    }

    @Override
    public void add(E entity) {
        try {
            validator.validate(entity);
        } catch (ValidationException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        super.add(entity);
    }

    @Override
    public void update(E entity) {
        try {
            validator.validate(entity);
        } catch (ValidationException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        super.update(entity);
    }
}
