package repository;

import model.File;


public class FileRepository extends BaseRepository<Long, File> {

    public FileRepository() {
        super(File.class);
    }

}
