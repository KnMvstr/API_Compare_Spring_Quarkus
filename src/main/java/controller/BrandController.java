package controller;

import dto.BrandDTO;
import entity.Brand;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.BrandService;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/api/v2/brands")
public class BrandController {

    @Inject
    BrandService brandService;

    @GET
    public Response getAllBrands() {
        return Response.ok(brandService.getAllBrands()).build();
    }

    @GET
    @Path("/{id}")
    public Response getBrandById(@PathParam("id") Long id) {
        BrandDTO brand = brandService.getBrandById(id);
        return Response.ok(brand).build();
    }

    @POST
    @Path("/create")
    public Response createBrand(@Valid BrandDTO brand) {
        BrandDTO newBrand = brandService.persist(brand, null);
        return Response.status(Response.Status.CREATED).entity(newBrand).build();
    }

    @PUT
    @Path("/edit/{id}")
    public Response updateBrand(@PathParam("id") Long id, @Valid BrandDTO brand) {
        BrandDTO updatedBrand = brandService.persist(brand, id);
        return Response.status(Response.Status.ACCEPTED).entity(updatedBrand).build();
    }

    @DELETE
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") Long id) {
        brandService.delete(id);
        return Response.status(Response.Status.ACCEPTED).build();
    }
}