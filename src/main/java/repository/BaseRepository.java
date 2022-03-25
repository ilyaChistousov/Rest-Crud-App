package repository;

import lombok.Cleanup;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import model.BaseEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.graph.GraphSemantic;
import util.ConnectionUtil;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public abstract class BaseRepository<K extends Serializable, E extends BaseEntity <K>> implements Repository<K, E> {

    private final SessionFactory sessionFactory = ConnectionUtil.getSessionFactory();
    private final Class<E> clazz;

    @Override
    public void save(E entity) {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
    }

    @Override
    public Optional<E> getById(K id, Map<String, Object> properties) {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        var maybeEntity = Optional.ofNullable(session.find(clazz, id, properties));
        session.getTransaction().commit();
        return maybeEntity;
    }

    @Override
    public List<E> getAll(Object property) {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        var criteriaQuery = session.getCriteriaBuilder().createQuery(clazz);
        criteriaQuery.from(clazz);
        var entityList = session.createQuery(criteriaQuery)
                .setHint(GraphSemantic.LOAD.getJpaHintName(), property).list();
        session.getTransaction().commit();
        return entityList;
    }

    @Override
    public void update(E entity) {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
    }

    @Override
    public void delete(E entity) {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(entity);
        session.getTransaction().commit();
    }
}
