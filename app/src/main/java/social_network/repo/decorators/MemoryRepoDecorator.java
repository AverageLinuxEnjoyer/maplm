package social_network.repo.decorators;

import java.util.Collection;

import social_network.domain.Entity;
import social_network.repo.Repo;

public class MemoryRepoDecorator<ID, E extends Entity<ID>> extends AbstractRepoDecorator<ID, E> {
    public MemoryRepoDecorator(Repo<ID, E> repo) {
        super(repo);
    }

    @Override
    public void add(E entity) {
        if (findOne(entity.getId()) != null) {
            throw new IllegalArgumentException("Entity already exists!");
        }
        super.add(entity);
    }

    @Override
    public void update(E entity) {
        if (findOne(entity.getId()) == null) {
            throw new IllegalArgumentException("Entity does not exist!");
        }
        super.update(entity);
    }

    @Override
    public void delete(ID id) {
        if (findOne(id) == null) {
            throw new IllegalArgumentException("Entity does not exist!");
        }
        super.delete(id);
    }

    @Override
    public E findOne(ID id) {
        return super.findOne(id);
    }

    @Override
    public Collection<E> findAll() {
        return super.findAll();
    }
}
