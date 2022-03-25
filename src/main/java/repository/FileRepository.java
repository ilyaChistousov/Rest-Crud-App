package repository;

import lombok.Cleanup;
import model.File;
import org.hibernate.Session;


public class FileRepository extends BaseRepository<Long, File> {

    public FileRepository() {
        super(File.class);
    }

//    @Override
//    public void update(File entity) {
//        @Cleanup Session session = getSessionFactory().openSession();
//        session.beginTransaction();
//        var query = session.createQuery("update files f set pathFile = :pathFile where f.id = :id");
//        query.setParameter("pathFile", entity.getPathFile());
//        query.setParameter("id", entity.getId());
//        query.executeUpdate();
//        session.flush();
//        session.getTransaction().commit();
//    }
}
