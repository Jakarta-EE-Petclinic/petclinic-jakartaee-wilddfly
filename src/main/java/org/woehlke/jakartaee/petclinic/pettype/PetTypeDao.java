package org.woehlke.jakartaee.petclinic.pettype;

import org.woehlke.jakartaee.petclinic.application.framework.crud.CrudDao;
import org.woehlke.jakartaee.petclinic.application.framework.entity.SearchableEntity;

/**
 * Created with IntelliJ IDEA.
 * User: Fert
 * Date: 06.01.14
 * Time: 11:51
 * To change this template use File | Settings | File Templates.
 */
public interface PetTypeDao extends CrudDao<PetType>, SearchableEntity<PetType> {

    long serialVersionUID = -8106442452154966621L;

    PetType findByName(String name);
}
