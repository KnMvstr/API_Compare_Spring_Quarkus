package resource;

import dto.CarTypeDTO;
import entity.CarType;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/api/v2/cartypes")
public class CarTypeResource  {

    @GET
    @Path("/all")
    public Response getAllCartypes() {
        List<CarTypeDTO> cartypes = CarType.getAllCarTypes();
        return Response.ok(cartypes).build();
    }

    @GET
    @Path("/{id}")
    public Response getCarTypeById(@PathParam("id") Long id) {
        try{
            CarTypeDTO cartype = CarType.getCarTypeById(id);
            return Response.ok(cartype).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Transactional
    @Path("/create")
    public Response createCartype(@Valid CarTypeDTO carTypeDTO) {
        try{
            CarType carType = new CarType();
            carType.setName(carTypeDTO.getName());
            carType.persist();
            return Response.status((Response.Status.CREATED)).entity(carType).build();
        } catch (PersistenceException pe) {
            Throwable cause = pe.getCause();
            if (cause instanceof ConstraintViolationException) {
                // Handle specific constraint violations (e.g., duplicate keys)
                return Response.status(Response.Status.CONFLICT).entity("A cartype with the same ID already exists.").build();
            }
            // Generic error handling for other types of persistence exceptions
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating cartype: " + cause.getMessage()).build();
        } catch (Exception e) {
            // Handle general exceptions
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating cartype: " + e.getMessage()).build();
        }
    }

    @PUT
    @Transactional
    @Path("/edit/{id}")
    public Response updateCartype(@PathParam("id") Long id, @Valid CarTypeDTO carTypeDTO) {
        try {
            CarType carType = CarType.findById(id);
            if (carType == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("CarType not found").build();
            }
            carType.updateFromDTO(carTypeDTO);
            carType.persist();
            return Response.ok(CarType.toDTO(carType)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error updating brand: " + e.getMessage()).build();
        }
    }
}