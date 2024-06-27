package controller;

import dto.ColorDTO;
import entity.Color;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.ColorService;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/api/v2/colors")
public class ColorController {
    @Inject
    ColorService colorService;

    @GET
    public Response getAllColors() {
        return Response.ok(colorService.getAllColors()).build();
    }

    @GET
    @Path("/{id}")
    public Response getColorById(@PathParam("id") Long id) {
        Color color = colorService.getColorById(id);
        return Response.ok(color).build();
    }

    @POST
    @Path("/create")
    public Response createColor(@Valid ColorDTO color) {
        Color newColor = colorService.persist(color, null);
        return Response.status(Response.Status.CREATED).entity(newColor).build();
    }

    @PUT
    @Path("/edit/{id}")
    public Response updateColor(@PathParam("id") Long id, @Valid ColorDTO color) {
        Color updatedColor = colorService.persist(color, id);
        return Response.status(Response.Status.ACCEPTED).entity(updatedColor).build();
    }

    @DELETE
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") Long id) {
        colorService.delete(id);
        return Response.status(Response.Status.ACCEPTED).build();
    }
}
