package social_network.repo.decorators;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

import social_network.domain.Entity;
import social_network.repo.Repo;

public class AutoIncrementRepoDecorator<ID, E extends Entity<ID>> extends AbstractRepoDecorator<ID, E> {
    ID nextId = null;
    UnaryOperator<ID> increment = null;

    public AutoIncrementRepoDecorator(Repo<ID, E> repo, ID startId, UnaryOperator<ID> next) {
        super(repo);
        this.nextId = repo.findAll().stream().map(Entity::getId).reduce((a, b) -> b).orElse(startId);

        this.increment = next;

        this.nextId = increment.apply(nextId);
    }

    @Override
    public void add(E entity) {
        entity.setId(nextId);
        nextId = increment.apply(nextId);
        super.add(entity);
    }
}
