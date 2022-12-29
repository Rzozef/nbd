package org.pl.repositories;

import org.pl.exceptions.RepositoryException;
import org.pl.model.EntityInterface;

import java.util.ArrayList;
import java.util.UUID;

public class Repository<T extends EntityInterface> {
    protected ArrayList<T> elements;

    public ArrayList<T> getElements() {
        return elements;
    }

    public Repository(ArrayList<T> elements) {
        this.elements = elements;
    }

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
            if (element.getId() == id && !element.isArchive()) {
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
            if (element.getId() == id) {
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
            for (EntityInterface entityInterface : elements) {
                if (!entityInterface.isArchive())
                    output++;
            }
        } else {
//            for (int i = 0; i < elements.size(); i++) {
//                if (get(i).isArchive())
//                    output++;
//            }
            for (EntityInterface entityInterface : elements) {
                if (entityInterface.isArchive()) {
                    output++;
                }
            }
        }
        return output;
    }

    public boolean isArchive(UUID id) throws RepositoryException {
        for (T element : elements) {
            if (element.getId() == id) {
                return element.isArchive();
            }
        }
        throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
    }

    public void unarchive(UUID id) throws RepositoryException {
        for (T element : elements) {
            if (element.getId() == id) {
                element.setArchive(false);
                return;
            }
        }
        throw new RepositoryException(RepositoryException.REPOSITORY_ARCHIVE_EXCEPTION);
    }
}
