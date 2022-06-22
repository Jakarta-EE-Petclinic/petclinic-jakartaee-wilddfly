package org.woehlke.jakartaee.petclinic.specialty.views;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.ejb.EJBTransactionRolledbackException;
import lombok.extern.java.Log;
import org.woehlke.jakartaee.petclinic.application.config.messages.MessageProvider;
import org.woehlke.jakartaee.petclinic.application.views.FrontendMessagesView;
import org.woehlke.jakartaee.petclinic.application.views.LanguageView;
import org.woehlke.jakartaee.petclinic.specialty.Specialty;
import org.woehlke.jakartaee.petclinic.specialty.SpecialtyService;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: tw
 * Date: 04.01.14
 * Time: 12:00
 * To change this template use File | Settings | File Templates.
 */
@Log
@Named("specialtyView")
@SessionScoped
public class SpecialtyViewImpl implements SpecialtyView {

    private static final long serialVersionUID = 9080853875975855082L;

    private final static String JSF_PAGE = "specialty.jsf";

    private MessageProvider provider;

    private Specialty entity;
    private Specialty selected;
    private List<Specialty> list;

    private String searchterm;

    @EJB
    private SpecialtyService entityService;

    @Inject
    private LanguageView languageView;

    @Inject
    private FrontendMessagesView frontendMessagesView;

    @Inject
    private SpecialtyViewFlow specialtyViewFlow;

    @Override
    @PostConstruct
    public void init() {
        log.info("postConstruct");
        this.specialtyViewFlow.setFlowStateList();
        this.provider = new MessageProvider();
    }

    @Override
    public boolean reloadEntityFromSelected() {
        log.info("reloadEntityFromSelected");
        if (this.selected != null) {
            this.selected = entityService.findById(this.selected.getId());
            this.entity = this.selected;
            return true;
        } else {
            String summaryKey = "org.woehlke.jakartaee.petclinic.specialty.list.choose.summary";
            String summary = this.provider.getBundle().getString(summaryKey);
            String detailKey = "org.woehlke.jakartaee.petclinic.specialty.list.choose.detail";
            String detail = this.provider.getBundle().getString(detailKey);
            frontendMessagesView.addWarnMessage(summary, detail);
            return false;
        }
    }

    @Override
    public void loadList() {
        this.list = entityService.getAll();
    }

    @Override
    public void saveNewEntity() {
        log.info("saveNewEntity");
        try {
            this.entity = entityService.addNew(this.entity);
            this.selected = this.entity;
            this.specialtyViewFlow.setFlowStateList();
            String summaryKey = "org.woehlke.jakartaee.petclinic.specialty.search.done";
            String summary = this.provider.getBundle().getString(summaryKey);
            frontendMessagesView.addInfoMessage(summary, this.entity.getPrimaryKey());
        } catch (EJBException e) {
            this.specialtyViewFlow.setFlowStateNew();
            frontendMessagesView.addWarnMessage(e.getLocalizedMessage(), this.entity);
        }
    }

    @Override
    public void saveEditedEntity() {
        log.info("saveEditedEntity");
        try {
            this.entity = this.entityService.update(this.entity);
            this.selected = this.entity;
            this.specialtyViewFlow.setFlowStateList();
            String summaryKey = "org.woehlke.jakartaee.petclinic.specialty.edit.done";
            String summary = this.provider.getBundle().getString(summaryKey);
            frontendMessagesView.addInfoMessage(summary, this.entity);
        } catch (EJBException e) {
            this.specialtyViewFlow.setFlowStateEdit();
            frontendMessagesView.addWarnMessage(e.getLocalizedMessage(), this.entity);
        }
    }

    @Override
    public void deleteSelectedEntity() {
        log.info("deleteSelectedEntity");
        try {
            if (this.selected != null) {
                String details = this.selected.getPrimaryKey();
                entityService.delete(this.selected.getId());
                if (this.selected.compareTo(this.entity) == 0) {
                    this.entity = null;
                }
                this.selected = null;
                this.specialtyViewFlow.setFlowStateList();
                String summaryKey = "org.woehlke.jakartaee.petclinic.specialty.delete.done";
                String summary = this.provider.getBundle().getString(summaryKey);
                frontendMessagesView.addInfoMessage(summary, details);
            }
        } catch (EJBTransactionRolledbackException e) {
            this.specialtyViewFlow.setFlowStateDelete();
            String summaryKey = "org.woehlke.jakartaee.petclinic.specialty.delete.denied";
            String summary = this.provider.getBundle().getString(summaryKey);
            frontendMessagesView.addWarnMessage(summary, this.selected);
        } catch (EJBException e) {
            this.specialtyViewFlow.setFlowStateDelete();
            frontendMessagesView.addErrorMessage(e.getLocalizedMessage(), this.selected);
        }
    }

    @Override
    public void newEntity() {
        log.info("newEntity");
        String name = "add new name";
        this.entity = new Specialty();
    }

    @Override
    public String showEditForm() {
        log.info("showEditForm");
        if (this.reloadEntityFromSelected()) {
            this.specialtyViewFlow.setFlowStateEdit();
        } else {
            this.specialtyViewFlow.setFlowStateList();
        }
        return JSF_PAGE;
    }

    @Override
    public String showNewForm() {
        log.info("showNewForm");
        this.newEntity();
        this.specialtyViewFlow.setFlowStateNew();
        return JSF_PAGE;
    }

    @Override
    public String saveNew() {
        log.info("saveNew");
        this.saveNewEntity();
        this.specialtyViewFlow.setFlowStateList();
        return JSF_PAGE;
    }

    @Override
    public String saveEdited() {
        log.info("saveEdited");
        this.saveEditedEntity();
        this.specialtyViewFlow.setFlowStateList();
        return JSF_PAGE;
    }

    @Override
    public String cancelEdited() {
        log.info("cancelEdited");
        this.specialtyViewFlow.setFlowStateList();
        return JSF_PAGE;
    }

    @Override
    public String cancelNew() {
        log.info("cancelNew");
        this.specialtyViewFlow.setFlowStateList();
        return JSF_PAGE;
    }

    @Override
    public String showDeleteForm() {
        log.info("showDeleteForm");
        if (this.reloadEntityFromSelected()) {
            this.specialtyViewFlow.setFlowStateDelete();
        } else {
            this.specialtyViewFlow.setFlowStateList();
        }
        return JSF_PAGE;
    }

    @Override
    public String performDelete() {
        log.info("performDelete");
        deleteSelectedEntity();
        this.specialtyViewFlow.setFlowStateList();
        return JSF_PAGE;
    }

    @Override
    public String cancelDelete() {
        log.info("cancelDelete");
        this.specialtyViewFlow.setFlowStateList();
        return JSF_PAGE;
    }

    @Override
    public String search() {
        log.info("search");
        this.specialtyViewFlow.setFlowStateSearchResult();
        return JSF_PAGE;
    }


    @Override
    public void performSearch() {
        log.info("performSearch");
        String summaryKey = "org.woehlke.jakartaee.petclinic.specialty.search.done";
        String summary = this.provider.getBundle().getString(summaryKey);
        if (searchterm == null || searchterm.isEmpty()) {
            this.specialtyViewFlow.setFlowStateList();
            String missingKey = "org.woehlke.jakartaee.petclinic.list.searchterm.missing";
            String detail = this.provider.getBundle().getString(missingKey);
            frontendMessagesView.addInfoMessage(summary, detail);
        } else {
            try {
                this.specialtyViewFlow.setFlowStateSearchResult();
                this.list = entityService.search(searchterm);
                String foundKey = "org.woehlke.jakartaee.petclinic.list.searchterm.found";
                String resultsKey = "org.woehlke.jakartaee.petclinic.list.searchterm.results";
                String found = this.provider.getBundle().getString(foundKey);
                String results = this.provider.getBundle().getString(resultsKey);
                String detail = found + " " + this.list.size() + " " + results + " " + searchterm;
                frontendMessagesView.addInfoMessage(summary, detail);
            } catch (Exception e) {
                this.specialtyViewFlow.setFlowStateList();
                frontendMessagesView.addWarnMessage(e.getLocalizedMessage(), searchterm);
            }
        }
    }

    @Override
    public LanguageView getLanguageView() {
        return languageView;
    }

    @Override
    public void setLanguageView(LanguageView languageView) {
        this.languageView = languageView;
    }

    public FrontendMessagesView getFrontendMessagesView() {
        return frontendMessagesView;
    }

    public void setFrontendMessagesView(FrontendMessagesView frontendMessagesView) {
        this.frontendMessagesView = frontendMessagesView;
    }

    @Override
    public String getSearchterm() {
        return searchterm;
    }

    @Override
    public void setSearchterm(String searchterm) {
        this.searchterm = searchterm;
    }

    @Override
    public Specialty getEntity() {
        return entity;
    }

    @Override
    public void setEntity(Specialty entity) {
        this.entity = entity;
    }

    @Override
    public Specialty getSelected() {
        return selected;
    }

    @Override
    public void setSelected(Specialty selected) {
        this.selected = selected;
    }

    @Override
    public ResourceBundle getMsg() {
        return this.provider.getBundle();
    }

    public void setMsg(ResourceBundle msg) {
    }

    @Override
    public List<Specialty> getList() {
        if (this.specialtyViewFlow.isFlowStateSearchResult()) {
            performSearch();
        } else {
            loadList();
        }
        return this.list;
    }

    @Override
    public void setList(List<Specialty> list) {
        this.list = list;
    }

    @Override
    @PreDestroy
    public void preDestroy() {
        log.info("preDestroy");
    }

}
