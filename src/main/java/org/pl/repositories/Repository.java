package org.pl.repositories;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Entity;

import java.util.ArrayList;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Repository<T extends Entity> {
    protected ArrayList<T> elements;

    public void add(T element) throws RepositoryException {
        if (element == null) {
            throw new RepositoryException(RepositoryException.REPOSITORY_ADD_INVALID_EXCEPTION);
        }
        for (T t : elements) {
            if (t == element) {
                t.setArchive(false);
                return;
            }
        }
        elements.add(element);
    }

    public void archivise(UUID id) throws RepositoryException {
        for (T element : elements) {
            if (element.getID() == id && !element.isArchive()) {
                element.setArchive(true);
                return;
            }
        }
        throw new RepositoryException(RepositoryException.REPOSITORY_ARCHIVE_EXCEPTION);
    }

    public T get(UUID id) throws RepositoryException {
        if (elements.isEmpty()) {
            throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
        }
        for (T element : elements) {
            if (element.getID() == id) {
                return element;
            }
        }
        throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
    }
    
    public int getSize(boolean present) throws RepositoryException {
        int output = 0;
        if (present) {
//            for (int i = 0; i < elements.size(); i++) {
//                if (!get(i).isArchive())
//                    output++;
//            }
            for (Entity entity : elements) {
                if (!entity.isArchive())
                    output++;
            }
        } else {
//            for (int i = 0; i < elements.size(); i++) {
//                if (get(i).isArchive())
//                    output++;
//            }
            for (Entity entity : elements) {
                if (entity.isArchive()) {
                    output++;
                }
            }
        }
        return output;
    }

    public boolean isArchive(UUID id) throws RepositoryException {
        for (T element : elements) {
            if (element.getID() == id) {
                return element.isArchive();
            }
        }
        throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
    }

    public void unarchive(UUID id) throws RepositoryException {
        for (T element : elements) {
            if (element.getID() == id) {
                element.setArchive(false);
                return;
            }
        }
        throw new RepositoryException(RepositoryException.REPOSITORY_ARCHIVE_EXCEPTION);
    }
}
