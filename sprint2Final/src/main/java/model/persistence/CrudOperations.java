package model.persistence;

import java.util.List;
import java.util.Optional;

public interface CrudOperations <T, Id>{
    T save(T entity);

    Optional<T> findById(Id id);

    List<T> findAll(); //delete

    Optional<T> update(T entity, Id id);

//    void delete(Id id);
}
