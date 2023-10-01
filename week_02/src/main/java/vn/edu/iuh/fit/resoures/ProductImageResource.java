package vn.edu.iuh.fit.resoures;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import vn.edu.iuh.fit.models.ProductImage;
import vn.edu.iuh.fit.services.ProductImageService;
import java.util.List;

@Path("/productimages")
public class ProductImageResource {
    private final ProductImageService productImageService;

    public ProductImageResource() {
        productImageService = new ProductImageService();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProductImage(ProductImage productImage) {
        boolean isSuccess = productImageService.createProductImage(productImage);
        if (isSuccess) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("/{imageId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProductImage(@PathParam("imageId") long imageId, ProductImage productImage) {
        productImage.setImageId(imageId);
        boolean isSuccess = productImageService.updateProductImage(productImage);
        if (isSuccess) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("/{imageId}")
    public Response deleteProductImage(@PathParam("imageId") long imageId) {
        boolean isSuccess = productImageService.deleteProductImage(imageId);
        if (isSuccess) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/{imageId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductImageById(@PathParam("imageId") long imageId) {
        ProductImage productImage = productImageService.getProductImageById(imageId);
        if (productImage != null) {
            return Response.status(Response.Status.OK).entity(productImage).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProductImages() {
        List<ProductImage> productImages = productImageService.getAllProductImages();
        return Response.status(Response.Status.OK).entity(productImages).build();
    }
}