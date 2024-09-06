package resource;

import dto.EngineDTO;
import entity.Engine;
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
@Path("/api/v2/engines")
public class EngineResource {

    @GET
    @Path("/all")
    public Response getAllEngines() {
        List<EngineDTO> engines = Engine.getAllEngines();
        return Response.ok(engines).build();
    }

    @GET
    @Path("/{id}")
    public Response getEngineById(@PathParam("id") Long id) {
        try {
        EngineDTO engine = Engine.getEngineById(id);
        return Response.ok(engine).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @POST
    @Transactional
    @Path("/create")
    public Response createEngine(@Valid EngineDTO engineDTO) {
        try {
            Engine engine = new Engine();
            engine.setName(engineDTO.getName());
            engine.persist();
            return Response.status(Response.Status.CREATED).entity(Engine.toDTO(engine)).build();
        } catch (PersistenceException pe) {
            Throwable cause = pe.getCause();
            if (cause instanceof ConstraintViolationException) {
                // Handle specific constraint violations (e.g., duplicate keys)
                return Response.status(Response.Status.CONFLICT).entity("A engine with the same ID already exists.").build();
            }
            // Generic error handling for other types of persistence exceptions
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating engine: " + cause.getMessage()).build();
        } catch (Exception e) {
            // Handle general exceptions
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating engine: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/edit/{id}")
    @Transactional
    public Response updateEngine(@PathParam("id") Long id, @Valid EngineDTO engineDTO) {
        try {
            Engine engine = Engine.findById(id);
            if (engine == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Engine not found").build();
            }
            engine.updateFromDTO(engineDTO);
            engine.persist();
            return Response.ok(Engine.toDTO(engine)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error updating engine: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/delete/{id}")
    @Transactional
    public Response deleteEngine(@PathParam("id") Long id) {
        try {
            boolean deleted = Engine.deleteById(id);
            if (!deleted) {
                throw new EntityNotFoundException("Engine not found with ID: " + id);
            }
            return Response.noContent().build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error deleting engine: " + e.getMessage()).build();
        }
    }
}
