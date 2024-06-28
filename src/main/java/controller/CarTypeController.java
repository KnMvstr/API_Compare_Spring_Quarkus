package controller;

import dto.CarTypeDTO;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.CarTypeService;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/api/v2/cartypes")
public class CarTypeController {
    @Inject
    CarTypeService carTypeService;

    @GET
    public Response getAllCartypes() {
        return Response.ok(carTypeService.getAllCarTypes()).build();
    }

    @GET
    @Path("/{id}")
    public Response getCartypeById(@PathParam("id") Long id) {
        CarTypeDTO carType = carTypeService.getCarTypeById(id);
        return Response.ok(carType).build();
    }

    @POST
    @Path("/create")
    public Response createCartype(@Valid CarTypeDTO carType) {
        CarTypeDTO newCartype = carTypeService.persist(carType, null);
        return Response.status(Response.Status.CREATED).entity(newCartype).build();
    }

    @PUT
    @Path("/edit/{id}")
    public Response updateCartype(@PathParam("id") Long id, @Valid CarTypeDTO carType) {
        CarTypeDTO updatedCartype = carTypeService.persist(carType, id);
        return Response.status(Response.Status.ACCEPTED).entity(updatedCartype).build();
    }

    @DELETE
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") Long id) {
        carTypeService.delete(id);
        return Response.status(Response.Status.ACCEPTED).build();
    }
}
