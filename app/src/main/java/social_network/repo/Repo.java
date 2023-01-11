package social_network.repo;

import java.util.Collection;

import social_network.domain.Entity;

/**
 * Interface for a repository.
 */
public interface Repo<ID, E extends Entity<ID>> {
    /**
     * Adds an entity to the repository.
     * 
     * @param entity the entity to be added
     * @throws IllegalArgumentException if the entity already exists
     */
    public abstract void add(E entity);

    /**
     * Deletes an entity from the repository.
     * 
     * @param id the id of the entity to be deleted
     * @throws IllegalArgumentException if the entity does not exist
     */
    public abstract void delete(ID id);

    /**
     * Deletes all entities from the repository.
     */
    public abstract void deleteAll();

    /**
     * Updates an entity from the repository.
     * 
     * @param entity the entity to be updated
     * @throws IllegalArgumentException if the entity does not exist
     */
    public abstract void update(E entity);

    /**
     * Finds an entity by id.
     * 
     * @param id the id of the entity to be found
     * @return the entity with the given id
     */
    public abstract E findOne(ID id);

    /**
     * Getter for all entities.
     * 
     * @return all entities
     */
    public abstract Collection<E> findAll();

}
