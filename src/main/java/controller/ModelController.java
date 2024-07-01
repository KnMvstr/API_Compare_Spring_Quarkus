package controller;


import dto.ModelDTO;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.ModelService;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/api/v2/models")
public class ModelController {

    @Inject
    ModelService modelService;

    @GET
    public Response getAllModels() {
        return Response.ok(modelService.getAllModels()).build();
    }

    @GET
    @Path("/{id}")
    public Response getModelById(@PathParam("id") Long id) {
        ModelDTO model = modelService.getModelById(id);
        return Response.ok(model).build();
    }

    @POST
    @Path("/create")
    public Response createModel(@Valid ModelDTO model) {
        ModelDTO newModel = modelService.persist(model, null);
        return Response.status(Response.Status.CREATED).entity(newModel).build();
    }

    @PUT
    @Path("/edit/{id}")
    public Response updateModel(@PathParam("id") Long id, @Valid ModelDTO model) {
        ModelDTO updatedModel = modelService.persist(model, id);
        return Response.status(Response.Status.ACCEPTED).entity(updatedModel).build();
    }

    @DELETE
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") Long id) {
        modelService.delete(id);
        return Response.status(Response.Status.ACCEPTED).build();
    }
}