package org.woehlke.jakartaee.petclinic.application;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import java.io.File;

public class TestDeploymentBuilder {

    private static final String WEBAPP_SRC = "src/main/webapp";

    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "petclinic.war")
                .addPackages(true, "org.woehlke.jakartaee.petclinic")
                .addAsDirectories("css","img")
                .addAsWebResource(new File(WEBAPP_SRC, "help.xhtml"))
                .addAsWebResource(new File(WEBAPP_SRC, "home.xhtml"))
                .addAsWebResource(new File(WEBAPP_SRC, "info.xhtml"))
                .addAsWebResource(new File(WEBAPP_SRC, "owner.xhtml"))
                .addAsWebResource(new File(WEBAPP_SRC, "pageTemplate.xhtml"))
                .addAsWebResource(new File(WEBAPP_SRC, "petType.xhtml"))
                .addAsWebResource(new File(WEBAPP_SRC, "specialty.xhtml"))
                .addAsWebResource(new File(WEBAPP_SRC, "vetinarian.xhtml"))
                .addAsWebInfResource(new File( "beans.xml"))
                .addAsWebInfResource(new File("faces-config-xml"));
    }
}
