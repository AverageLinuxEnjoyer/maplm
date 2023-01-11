package social_network.domain.validators;

import social_network.domain.Friendship;
import social_network.domain.User;
import social_network.domain.exceptions.ValidationException;
import social_network.repo.Repo;

/**
 * Class for validating a friendship.
 */
public class FriendshipValidator implements Validator<Friendship> {
    Repo<Long, User> users;

    public FriendshipValidator(Repo<Long, User> users) {
        this.users = users;
    }

    @Override
    public void validate(Friendship entity) throws ValidationException {
        if (entity == null) {
            throw new ValidationException("Friendship must not be null");
        }
        if (entity.getId1() == null || entity.getId2() == null) {
            throw new ValidationException("Friendship ids cannot be null!");
        }
        if (entity.getId1().equals(entity.getId2())) {
            throw new ValidationException("Friendship ids cannot be the same!");
        }
        if (users.findOne(entity.getId1()) == null || users.findOne(entity.getId2()) == null) {
            throw new ValidationException("Friendship ids must be valid!");
        }
    }
}
