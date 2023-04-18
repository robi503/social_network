package socialnetwork.repo;

import socialnetwork.domain.Entity;
import socialnetwork.validation.Validator;
import socialnetwork.validation.exceptions.ValidationException;

import java.util.HashMap;
import java.util.Map;

public class InMemoryRepo<ID, E extends Entity<ID>> implements Repository<ID, E>{
    private final Validator<E> validator;
    Map<ID,E> entities;

    public InMemoryRepo(Validator<E> validator) {
        this.validator = validator;
        entities=new HashMap<>();
    }
    @Override
    public E findOne(ID id){
        if (id==null)
            throw new IllegalArgumentException("id must be not null");
        return entities.get(id);
    }
    @Override
    public Iterable<E> findAll() {
        return entities.values();
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
        if (id==null)
            throw new IllegalArgumentException("ID must be not null");
        return entities.remove(id);
    }
    @Override
    public E update(E entity) {
        if(entity == null)
            throw new IllegalArgumentException("entity must be not null!");
        try{
            validator.validate(entity);
        }
        catch(ValidationException v){
            System.out.println(v.toString());
        }
        entities.put(entity.getId(),entity);
        if(entities.get(entity.getId()) != null) {
            entities.put(entity.getId(),entity);
            return null;
        }
        return entity;
    }
}
