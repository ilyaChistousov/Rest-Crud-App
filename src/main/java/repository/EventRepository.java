package repository;

import lombok.Cleanup;
import model.Event;
import org.hibernate.graph.GraphSemantic;

import java.util.List;

public class EventRepository extends BaseRepository <Long, Event> {

    public EventRepository() {
        super(Event.class);
    }

    public List<Event> getByUserId(Long userId) {
        @Cleanup var session = getSessionFactory().openSession();
        session.beginTransaction();
        var query = session.createQuery("select e from events e where e.user.id = :id", Event.class);
        query.setParameter("id", userId);
        var events = query.getResultList();
        session.getTransaction().commit();
        return events;
    }
}
