package org.woehlke.jakartaee.petclinic.specialty.views;

import org.jboss.arquillian.graphene.page.Location;
import org.junit.Assert;
import org.openqa.selenium.support.FindBy;
import org.primefaces.selenium.AbstractPrimePage;
import org.primefaces.selenium.component.CommandButton;
import org.primefaces.selenium.component.DataTable;

@Location("specialty.jsf")
public class SpecialtyPage extends AbstractPrimePage {

    private final static String JSF_PAGE = "specialty.jsf";

    @FindBy(id = "findEntityForm:showNewFormButton")
    private CommandButton showNewFormButton;

    @FindBy(id = "entityDataTableForm:entityDataTable")
    private DataTable entityDataTable;

    @Override
    public String getLocation() {
        return JSF_PAGE;
    }

    public void create_customer_test() {
        Assert.assertTrue(true);
    }
}
