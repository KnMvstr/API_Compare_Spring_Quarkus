package controller;

import dto.ColorDTO;
import entity.Color;
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
@Path("/api/v2/colors")
public class ColorController {

    @GET
    @Path("/all")
    public Response getAllColors() {
        List<ColorDTO> colors = Color.getAllColors();
        return Response.ok(colors).build();
    }

    @GET
    @Path("/{id}")
    public Response getColorById(@PathParam("id") Long id) {
        try {
            ColorDTO color = Color.getColorById(id);
            return Response.ok(color).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @POST
    @Transactional
    @Path("/create")
    public Response createColor(ColorDTO colorDTO) {
        try {
            Color color = new Color();
            color.setName(colorDTO.getName());
            color.setHexRef(colorDTO.getHexRef());
            color.setRvbRef(color.getRvbRef());
            color.persist();
            return Response.status(Response.Status.CREATED).entity(Color.toDTO(color)).build();
        } catch (PersistenceException pe) {
            Throwable cause = pe.getCause();
            if (cause instanceof ConstraintViolationException) {
                // Handle specific constraint violations (e.g., duplicate keys)
                return Response.status(Response.Status.CONFLICT).entity("A color with the same ID already exists.").build();
            }
            // Generic error handling for other types of persistence exceptions
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating color: " + cause.getMessage()).build();
        } catch (Exception e) {
            // Handle general exceptions
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating color: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/edit/{id}")
    @Transactional
    public Response updateColor(@PathParam("id") Long id, @Valid ColorDTO colorDTO) {
        try {
            Color color = Color.findById(id);
            if (color == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Color not found").build();
            }
            color.updateFromDTO(colorDTO);
            color.persist();
            return Response.ok(Color.toDTO(color)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error updating color: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/delete/{id}")
    @Transactional
    public Response deleteColor(@PathParam("id") Long id) {
        try {
            boolean deleted = Color.deleteById(id);
            if (!deleted) {
                throw new EntityNotFoundException("Color not found with ID: " + id);
            }
            return Response.noContent().build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error deleting color: " + e.getMessage()).build();
        }
    }
}