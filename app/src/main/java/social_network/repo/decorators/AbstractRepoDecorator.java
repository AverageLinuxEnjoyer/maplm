package social_network.repo.decorators;

import java.util.Collection;

import social_network.domain.Entity;
import social_network.repo.Repo;

public abstract class AbstractRepoDecorator<ID, E extends Entity<ID>> implements Repo<ID, E> {
    private Repo<ID, E> repo;

    public AbstractRepoDecorator(Repo<ID, E> repo) {
        this.repo = repo;
    }

    @Override
    public void add(E entity) {
        repo.add(entity);
    }

    @Override
    public void delete(ID id) {
        repo.delete(id);
    }

    @Override
    public void deleteAll() {
        repo.deleteAll();
    }

    @Override
    public Collection<E> findAll() {
        return repo.findAll();
    }

    @Override
    public E findOne(ID id) {
        return repo.findOne(id);
    }

    @Override
    public void update(E entity) {
        repo.update(entity);
    }

}
