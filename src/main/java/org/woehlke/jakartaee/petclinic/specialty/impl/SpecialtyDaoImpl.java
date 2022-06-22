package org.woehlke.jakartaee.petclinic.specialty.impl;

import jakarta.ejb.PostActivate;
import jakarta.ejb.PrePassivate;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.java.Log;
import org.woehlke.jakartaee.petclinic.specialty.SpecialtyDao;
import org.woehlke.jakartaee.petclinic.specialty.Specialty;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: tw
 * Date: 04.01.14
 * Time: 12:03
 * To change this template use File | Settings | File Templates.
 */
@Log
@Stateless
public class SpecialtyDaoImpl implements SpecialtyDao {

    private static final long serialVersionUID = 1355422039564914705L;

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    @Override
    public List<Specialty> getAll() {
        String qlString = "select s from Specialty s order by s.name";
        TypedQuery<Specialty> q = entityManager.createQuery(qlString, Specialty.class);
        return q.getResultList();
    }

    @Override
    public Specialty findById(long id) {
        Specialty specialty = entityManager.find(Specialty.class, id);
        return specialty;
    }

    @Override
    public Specialty findSpecialtyByName(String name) {
        String ql = "select  s from Specialty s where s.name=:name";
        TypedQuery<Specialty> query = entityManager.createQuery(ql, Specialty.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public void delete(long id) {
        Specialty specialty = entityManager.find(Specialty.class, id);
        entityManager.remove(specialty);
    }

    @Override
    public Specialty addNew(Specialty specialty) {
        specialty.setUuid(UUID.randomUUID());
        log.info("addNewSpecialty: " + specialty.toString());
        entityManager.persist(specialty);
        log.info("persisted:       " + specialty.toString());
        return specialty;
    }

    @Override
    public Specialty update(Specialty specialty) {
        log.info("update: " + specialty.toString());
        specialty = entityManager.merge(specialty);
        log.info("merged: " + specialty.toString());
        return specialty;
    }

    @Override
    public List<Specialty> search(String searchterm) {
        log.info("search Specialty for: " + searchterm);
    /*
    FullTextEntityManager fullTextEntityManager =
        org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);
    QueryBuilder qb = fullTextEntityManager.getSearchFactory()
        .buildQueryBuilder().forEntity(Specialty.class).get();
    org.apache.lucene.search.Query query = qb
        .keyword()
        .onFields("name")
        .matching(searchterm)
        .createQuery();
    // wrap Lucene query in a javax.persistence.Query
    javax.persistence.Query persistenceQuery =
        fullTextEntityManager.createFullTextQuery(query, Specialty.class);
    // execute search
    @SuppressWarnings("unchecked")
    List<Specialty> result = persistenceQuery.getResultList();
    log.info("found: " + result.size());
    for (Specialty o : result) {
      log.info("found: " + o.getName());
    }
    return result;
    */
        return new ArrayList<Specialty>();
    }

    @Override
    public void resetSearchIndex() {
        log.info("resetSearchIndex Specialty ");
    /*
    FullTextEntityManager fullTextEntityManager =
        org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);
    String qlString = "select o from Specialty o";
    TypedQuery<Specialty> findAllActionItems = fullTextEntityManager.createQuery(qlString, Specialty.class);
    for (Specialty petType : findAllActionItems.getResultList()) {
      fullTextEntityManager.index(petType);
    }
    fullTextEntityManager.flushToIndexes();
    */
        //fullTextEntityManager.clear();
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
