package org.woehlke.jakartaee.petclinic.specialty.views;

import jakarta.inject.Inject;
import lombok.extern.java.Log;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.page.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.primefaces.selenium.AbstractPrimePageTest;
import org.woehlke.jakartaee.petclinic.application.TestDeploymentBuilder;

import java.net.URL;

@Log
@RunWith(Arquillian.class)
public class SpecialtyPageIT extends AbstractPrimePageTest {

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return TestDeploymentBuilder.createDeployment();
    }

    @Drone
    private WebDriver browser;

    @ArquillianResource
    private URL deploymentUrl;

    @Inject
    private SpecialtyPage specialtyPage;

    @Test
    @InSequence(1)
    @RunAsClient
    public void create_customer_test(){
        log.info("FUCK YOU!");
        Assert.assertNotNull(browser);
        Assert.assertNotNull(deploymentUrl);
        Assert.assertNotNull(specialtyPage);
        //browser.get(deploymentUrl.toExternalForm() + specialtyPage.getLocation());
        //specialtyPage.create_customer_test();
    }

}
