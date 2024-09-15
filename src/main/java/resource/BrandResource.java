package resource;

import dto.BrandDTO;
import entity.Brand;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

@Path("/api/v2/brands")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BrandResource  {

    @GET
    @Path("/all")
    public Response getAllBrands() {
        List<BrandDTO> brands = Brand.getAllBrands();
        return Response.ok(brands).build();
    }

    @GET
    @Path("/{id}")
    public Response getBrandById(@PathParam("id") Long id) {
        try {
            BrandDTO brand = Brand.getBrandById(id);
            return Response.ok(brand).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @POST
    @Transactional
    @Path("/create")
    public Response createBrand(BrandDTO brandDTO) {
        try {
            Brand brand = new Brand();
            brand.setName(brandDTO.getName());
            brand.persist();
            return Response.status(Response.Status.CREATED).entity(Brand.toDTO(brand)).build();
        } catch (PersistenceException pe) {
            Throwable cause = pe.getCause();
            if (cause instanceof ConstraintViolationException) {
                // Handle specific constraint violations (e.g., duplicate keys)
                return Response.status(Response.Status.CONFLICT).entity("A brand with the same ID already exists.").build();
            }
            // Generic error handling for other types of persistence exceptions
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating brand: " + cause.getMessage()).build();
        } catch (Exception e) {
            // Handle general exceptions
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating brand: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/edit/{id}")
    @Transactional
    public Response updateBrand(@PathParam("id") Long id, @Valid BrandDTO brandDTO) {
        try {
            Brand brand = Brand.findById(id);
            if (brand == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Brand not found").build();
            }
            brand.updateFromDTO(brandDTO);
            brand.persist();
            return Response.ok(Brand.toDTO(brand)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error updating brand: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/delete/{id}")
    @Transactional
    public Response deleteBrand(@PathParam("id") Long id) {
        try {
            boolean deleted = Brand.deleteById(id);
            if (!deleted) {
                throw new EntityNotFoundException("Brand not found with ID: " + id);
            }
            return Response.noContent().build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error deleting brand: " + e.getMessage()).build();
        }
    }
}