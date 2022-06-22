package org.woehlke.jakartaee.petclinic.owner.views;

import org.woehlke.jakartaee.petclinic.visit.Visit;

import java.io.Serializable;

/**
 *
 */
public interface OwnersPetVisitView extends Serializable {

    long serialVersionUID = 2400107254778567823L;

    String editOwnerPetVisitNewForm();

    String saveOwnerPetVisitNew();

    String cancelOwnerPetVisitNew();

    Visit getVisit();

    void setVisit(Visit visit);
}
