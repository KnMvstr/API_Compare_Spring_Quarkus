package controller;

import dto.ModelDTO;
import entity.Model;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

@Path("/api/v2/models")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ModelController {

    @GET
    @Path("/all")
    public Response getAllModels() {
        List<ModelDTO> models = new Model().getAllModels();
        return Response.ok(models).build();
    }

    @GET
    @Path("/{id}")
    public Response getModelById(@PathParam("id") Long id) {
        try {
            ModelDTO model = new Model().getModelById(id);
            return Response.ok(model).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @POST
    @Transactional
    @Path("/create")
    public Response createModel(@Valid ModelDTO modelDTO) {
        try {
            ModelDTO createdModel = Model.persist(modelDTO, (Long) null);
            return Response.status(Response.Status.CREATED).entity(createdModel).build();
        } catch (PersistenceException pe) {
            Throwable cause = pe.getCause();
            if (cause instanceof ConstraintViolationException) {
                // Handle specific constraint violations (e.g., duplicate keys)
                return Response.status(Response.Status.CONFLICT).entity("A model with the same ID already exists.").build();
            }
            // Generic error handling for other types of persistence exceptions
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating model: " + cause.getMessage()).build();
        } catch (Exception e) {
            // Handle general exceptions
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating model: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/edit/{id}")
    @Transactional
    public Response updateModel(@PathParam("id") Long id, @Valid ModelDTO modelDTO) {
        try {
            Model model = Model.findById(id);
            if (model == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Model not found").build();
            }
            model.updateFromDTO(modelDTO);
            model.persist();
            return Response.ok(Model.toDTO(model)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error updating model: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/delete/{id}")
    @Transactional
    public Response deleteModel(@PathParam("id") Long id) {
        try {
            boolean deleted = Model.deleteById(id);
            if (!deleted) {
                throw new EntityNotFoundException("Model not found with ID: " + id);
            }
            return Response.noContent().build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error deleting model: " + e.getMessage()).build();
        }
    }
}