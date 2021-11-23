package ro.ubbcluj.map.pb3.repository.memory;

import ro.ubbcluj.map.pb3.domain.Entity;
import ro.ubbcluj.map.pb3.domain.Utilizator;
import ro.ubbcluj.map.pb3.domain.validators.Validator;
import ro.ubbcluj.map.pb3.repository.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @param <ID> id
 * @param <E> entity
 */
public class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID,E> {

    private Validator<E> validator;
    private Map<ID,E> entities;

    /**
     *
     * @param validator validator
     */
    public InMemoryRepository(Validator<E> validator) {
        this.validator = validator;
        entities=new HashMap<ID,E>();
    }

    @Override
    public E findOne(ID id){
        if (id==null)
            throw new IllegalArgumentException("id must be not null");
        return entities.get(id);
    }

    @Override
    public List<E> findAll() {
        return entities.values().stream().toList();
    }

    @Override
    public E save(E entity) {
        if (entity==null)
            throw new IllegalArgumentException("entity must be not null");
        validator.validate(entity);
        if(entities.get(entity.getId()) != null) {
            return entity;
        }
        else entities.put(entity.getId(),entity);
        return null;
    }

    @Override
    public E delete(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null!");
        }

        if (entities.get(id) != null) {
            E entity = entities.get(id);
            entities.remove(id);
            return entity;
        }
        return null;
    }

    @Override
    public E update(E entity) {

        if(entity == null)
            throw new IllegalArgumentException("entity must not be null!");
        validator.validate(entity);

//        entities.put(entity.getId(),entity);

        if(entities.get(entity.getId()) != null) {
            entities.put(entity.getId(),entity);
            return null;
        }
        return entity;

    }



}
