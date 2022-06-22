package org.woehlke.jakartaee.petclinic.pet.impl;

import jakarta.ejb.EJB;
import jakarta.ejb.PostActivate;
import jakarta.ejb.PrePassivate;
import jakarta.ejb.Stateless;
import lombok.extern.java.Log;
import org.woehlke.jakartaee.petclinic.pet.PetDao;
import org.woehlke.jakartaee.petclinic.pet.Pet;
import org.woehlke.jakartaee.petclinic.pet.PetService;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import java.util.List;


/**
 *
 */
@Log
@Stateless
public class PetServiceImpl implements PetService {

    private static final long serialVersionUID = -2093524918552358722L;

    @EJB
    private PetDao petDao;

    @Override
    public Pet addNew(Pet pet) {
        log.info("addNew Pet: " + pet.toString());
        return this.petDao.addNew(pet);
    }

    @Override
    public List<Pet> getAll() {
        return this.petDao.getAll();
    }

    @Override
    public Pet findById(long petId) {
        return this.petDao.findById(petId);
    }

    @Override
    public Pet update(Pet pet) {
        log.info("update Pet: " + pet.toString());
        return this.petDao.update(pet);
    }

    @Override
    public void delete(long id) {
        log.info("delete Pet: " + id);
        this.petDao.delete(id);
    }


    @PostConstruct
    public void postConstruct() {
        log.info("postConstruct");
    }

    @PreDestroy
    public void preDestroy() {
        log.info("preDestroy");
    }

    @PrePassivate
    public void prePassivate() {
        log.info("prePassivate");
    }

    @PostActivate
    public void postActivate() {
        log.info("postActivate");
    }
}
