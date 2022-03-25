package repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.emptyMap;

public interface Repository <K, E> {

    void save (E entity);

    Optional<E> getById(K id, Map<String, Object> properties);

    default Optional<E> getById(K id) {
        return getById(id, emptyMap());
    }

    List<E> getAll(Object property);

    default List<E> getAll() {
        return getAll(new Object());
    }

    void update(E entity);

    void delete(E entity);

}
