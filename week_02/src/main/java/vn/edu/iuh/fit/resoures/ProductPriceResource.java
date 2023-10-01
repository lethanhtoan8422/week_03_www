package vn.edu.iuh.fit.resoures;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import vn.edu.iuh.fit.models.ProductPrice;
import vn.edu.iuh.fit.services.ProductPriceService;
import java.util.List;

@Path("/productprices")
public class ProductPriceResource {
    private final ProductPriceService productPriceService;

    public ProductPriceResource() {
        productPriceService = new ProductPriceService();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProductPrice(ProductPrice productPrice) {
        boolean isSuccess = productPriceService.createProductPrice(productPrice);
        if (isSuccess) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("/{priceId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProductPrice(@PathParam("priceId") long priceId, ProductPrice productPrice) {
        productPrice.setPriceId(priceId);
        boolean isSuccess = productPriceService.updateProductPrice(productPrice);
        if (isSuccess) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("/{priceId}")
    public Response deleteProductPrice(@PathParam("priceId") long priceId) {
        boolean isSuccess = productPriceService.deleteProductPrice(priceId);
        if (isSuccess) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/{priceId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductPriceById(@PathParam("priceId") long priceId) {
        ProductPrice productPrice = productPriceService.getProductPriceById(priceId);
        if (productPrice != null) {
            return Response.status(Response.Status.OK).entity(productPrice).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProductPrices() {
        List<ProductPrice> productPrices = productPriceService.getAllProductPrices();
        return Response.status(Response.Status.OK).entity(productPrices).build();
    }
}