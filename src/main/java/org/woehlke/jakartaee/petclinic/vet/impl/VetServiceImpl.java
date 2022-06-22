package org.woehlke.jakartaee.petclinic.vet.impl;

import lombok.extern.java.Log;
import org.woehlke.jakartaee.petclinic.specialty.SpecialtyDao;
import org.woehlke.jakartaee.petclinic.vet.VetDao;
import org.woehlke.jakartaee.petclinic.vet.Vet;
import org.woehlke.jakartaee.petclinic.vet.VetService;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.EJB;
import jakarta.ejb.PostActivate;
import jakarta.ejb.PrePassivate;
import jakarta.ejb.Stateless;

import java.util.List;
import java.util.UUID;


/**
 *
 */
@Log
@Stateless
public class VetServiceImpl implements VetService {

    private static final long serialVersionUID = 2698313227542867286L;

    @EJB
    private VetDao vetDao;

    @EJB
    private SpecialtyDao specialtyDao;

    @Override
    public List<Vet> getAll() {
        return vetDao.getAll();
    }

    @Override
    public Vet findById(long id) {
        return this.vetDao.findById(id);
    }

    @Override
    public void delete(long id) {
        log.info("update Vet: " + id);
        this.vetDao.delete(id);
    }

    @Override
    public Vet addNew(Vet vet) {
        vet.setUuid(UUID.randomUUID());
        log.info("try to addNew: " + vet.toString());
        return this.vetDao.addNew(vet);
    }

    @Override
    public Vet update(Vet vet) {
        log.info("update Vet: " + vet.toString());
        return this.vetDao.update(vet);
    }

    @Override
    public List<Vet> search(String searchterm) {
        log.info("search: " + searchterm);
        return this.vetDao.search(searchterm);
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
