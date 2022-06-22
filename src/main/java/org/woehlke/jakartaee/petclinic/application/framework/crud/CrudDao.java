package org.woehlke.jakartaee.petclinic.application.framework.crud;

import org.woehlke.jakartaee.petclinic.application.framework.entity.EntityBase;

import java.io.Serializable;
import java.util.List;

/**
 * @param <T>
 */
public interface CrudDao<T extends EntityBase> extends Serializable {

    long serialVersionUID = 5140497751059102450L;

    /**
     * see src/main/resources/META-INF/persistence.xml
     */
    String PERSISTENCE_UNIT_NAME = "petclinicJakartaee";

    List<T> getAll();

    T findById(long id);

    T addNew(T entity);

    T update(T entity);

    void delete(long id);

}
