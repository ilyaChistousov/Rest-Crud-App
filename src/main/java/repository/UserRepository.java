package repository;

import model.User;

import javax.persistence.EntityGraph;

public class UserRepository extends BaseRepository<Long, User> {

    public final EntityGraph<?> USER_EVENTS_GRAPH = getEntityGraph();

    public UserRepository() {
        super(User.class);
    }

    private EntityGraph<?> getEntityGraph() {
        var entityGraph = getSessionFactory().openSession().createEntityGraph(User.class);
        entityGraph.addAttributeNodes("events");
        return entityGraph;
    }
}
