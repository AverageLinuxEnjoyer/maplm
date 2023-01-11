package social_network.repo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import social_network.domain.Entity;
import social_network.domain.exceptions.ValidationException;
import social_network.domain.validators.Validator;

public class RepoMemory<ID, E extends Entity<ID>> implements Repo<ID, E> {
    private Map<ID, E> entities;

    public RepoMemory() {
        entities = new HashMap<>();
    }

    @Override
    public void add(E entity) {
        entities.put(entity.getId(), entity);
    }

    @Override
    public void delete(ID id) {
        entities.remove(id);
    }

    @Override
    public void deleteAll() {
        entities.clear();
    }

    @Override
    public void update(E entity) {
        entities.put(entity.getId(), entity);
    }

    @Override
    public E findOne(ID id) {
        return entities.get(id);
    }

    @Override
    public Collection<E> findAll() {
        return entities.values();
    }
}
