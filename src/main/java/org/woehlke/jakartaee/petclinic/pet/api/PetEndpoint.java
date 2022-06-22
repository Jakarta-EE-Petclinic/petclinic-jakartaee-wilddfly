package org.woehlke.jakartaee.petclinic.pet.api;


import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.extern.java.Log;
import org.woehlke.jakartaee.petclinic.pet.Pet;
import org.woehlke.jakartaee.petclinic.pet.PetService;

import java.io.Serializable;
import java.io.StringWriter;

/**
 *
 */
@Log
@Path("/pet")
@ApplicationScoped
public class PetEndpoint implements Serializable {

    private static final long serialVersionUID = 6505290301528514574L;

    @EJB
    private PetService petService;

    @Inject
    private PetEndpointUtil petEndpointUtil;

    private String dtoJsonFactory(PetDto dto) throws JsonbException {
        Jsonb jsonb = JsonbBuilder.create();
        return jsonb.toJson(dto);
    }

    private String dtoListJsonFactory(PetListDto listDto) throws JsonbException {
        Jsonb jsonb = JsonbBuilder.create();
        return jsonb.toJson(listDto);
    }

    private String dtoXmlFactory(PetDto dto) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(PetDto.class);
        Marshaller m = jc.createMarshaller();
        StringWriter stringWriter = new StringWriter();
        m.marshal(dto, stringWriter);
        return stringWriter.toString();
    }

    private String dtoListXmlFactory(PetListDto listDto) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(PetListDto.class);
        Marshaller m = jc.createMarshaller();
        StringWriter stringWriter = new StringWriter();
        m.marshal(listDto, stringWriter);
        return stringWriter.toString();
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public String getList() {
        log.info("getList");
        return this.dtoListJsonFactory(petEndpointUtil.dtoListFactory(petService.getAll()));
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getEntity(@PathParam("id") Long id) {
        log.info("getEntity");
        return this.dtoJsonFactory(petEndpointUtil.dtoFactory(petService.findById(id)));
    }

    @GET
    @Path("/xml/list")
    @Produces(MediaType.APPLICATION_XML)
    public String getListAsXml() throws JAXBException {
        log.info("getListAsXml");
        return this.dtoListXmlFactory(petEndpointUtil.dtoListFactory(petService.getAll()));
    }

    @GET
    @Path("/xml/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public String getEntityAsXml(@PathParam("id") Long id) throws JAXBException {
        log.info("getEntityAsXml");
        return this.dtoXmlFactory(petEndpointUtil.dtoFactory(petService.findById(id)));
    }

}
