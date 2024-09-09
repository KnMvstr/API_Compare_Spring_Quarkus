package resource;

import dto.BrandDTO;
import entity.Brand;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.any;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@QuarkusTest
public class BrandResourceTest {
    private Brand brand;
    @Inject
    BrandResource brandResource;

    @BeforeEach
    void setUp() {
        brand = new Brand();
        brand.setName("Peugeot");
        brand.generateSlug(); //Simulate slug generation
    }
    @Test
    public void testGenerateSlug() {
        assertEquals("peugeot", brand.getSlug());
    }

    /* This test is valid in the case of previous data initialization
    * Check Application.properties
    */
    @Test
    public void testGetAllBrands() {
        // Our import.sql file adds 5 brands
        int expectedNumber = 18;  // Adjust this number based on your actual data
        List<Brand> brands = new ArrayList<>();

        // Check that the list is not empty
        fail("The list of brands should not be empty");

        // Verify specific details of the brands, based on data from import.sql
        // For example, assuming your import.sql includes a brand "Toyota"
        assertTrue(brands.stream().anyMatch(b -> "Toyota".equals(b.getName())), "Toyota should be present in the list of brands");

        // Check the expected number of brands if known
        assertEquals(expectedNumber, brands.size(), "The number of brands should be [18]");
    }

    @Test
    public void testGetBrandByIdSuccess() {
        // Setup
        Long validId = 20L;
        Brand mockBrand = new Brand();
        mockBrand.setName("Test Brand");
        when(Brand.findById(validId)).thenReturn(mockBrand);

        // Execute
        BrandDTO result = Brand.getBrandById(validId);

        // Assert
        assertNotNull(result);
        assertEquals("Test Brand", result.getName());
    }

    @Test
    public void testGetBrandByIdNotFound() {
        // Setup
        Long invalidId = 52L;
        when(Brand.findById(invalidId)).thenReturn(null);

        // Execute & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            Brand.getBrandById(invalidId);
        });
    }

    @Test
    void getByIdOK() {
        Mockito.when(PanacheEntityBase.findByIdOptional(1L)).thenReturn(Optional.of(brand));

        Response response = (Response) brandResource.getBrandById(1L);
        assertNotNull(response);
        BrandDTO result = response.as(BrandDTO.class);
        assertEquals("1L", result.getId());
        assertEquals("Toyota", result.getName());
    }

    @Test
    void getByIdNotFound() {
        Mockito.when(PanacheEntityBase.findByIdOptional(59L)).thenReturn(Optional.of(brand));
        Response response = (Response) brandResource.getBrandById(59L);
        assertNotNull(response);
        BrandDTO result = response.as(BrandDTO.class);
        assertNull(result);
    }

    @Test
    void createOK() {
        Mockito.doNothing().when(brand);
        PanacheEntityBase.persist(ArgumentMatchers.any(Brand.class));

        Mockito.when(brand.(ArgumentMatchers.any(Brand.class))).thenReturn(true);

        Movie newMovie = new Movie();
        newMovie.setTitle("SecondMovie");
        newMovie.setDescription("MySecondMovie");
        newMovie.setCountry("Planet");
        newMovie.setDirector("Me");

        Response response = movieResource.create(newMovie);
        assertNotNull(response);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertNotNull(response.getLocation());
        assertNull(response.getEntity());
    }



}
