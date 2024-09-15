package controller;

import dto.EngineDTO;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.EngineService;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/api/v2/engines")
public class EngineController {
    @Inject
    EngineService engineService;

    @GET
    public Response getAllEngines() {
        return Response.ok(engineService.getAllEngines()).build();
    }

    @GET
    @Path("/{id}")
    public Response getEngineById(@PathParam("id") Long id) {
        EngineDTO engine = engineService.getEngineById(id);
        return Response.ok(engine).build();
    }

    @POST
    @Path("/create")
    public Response createEngine(@Valid EngineDTO engine) {
        EngineDTO newEngine = engineService.persist(engine, null);
        return Response.status(Response.Status.CREATED).entity(newEngine).build();
    }

    @PUT
    @Path("/edit/{id}")
    public Response updateEngine(@PathParam("id") Long id, @Valid EngineDTO engine) {
        EngineDTO updatedEngine = engineService.persist(engine, id);
        return Response.status(Response.Status.ACCEPTED).entity(updatedEngine).build();
    }

    @DELETE
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") Long id) {
        engineService.delete(id);
        return Response.status(Response.Status.ACCEPTED).build();
    }
}
