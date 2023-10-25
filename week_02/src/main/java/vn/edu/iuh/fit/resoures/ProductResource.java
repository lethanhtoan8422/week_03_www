package vn.edu.iuh.fit.resoures;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import vn.edu.iuh.fit.entities.InformationProduct;
import vn.edu.iuh.fit.models.Product;
import vn.edu.iuh.fit.services.ProductService;
import java.util.List;

@Path("/products")
public class ProductResource {
    private final ProductService productService;

    public ProductResource() {
        productService = new ProductService();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProduct(Product product) {
        boolean isSuccess = productService.createProduct(product);
        if (isSuccess) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("/{productId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProduct(@PathParam("productId") long productId, Product product) {
        product.setProductId(productId);
        boolean isSuccess = productService.updateProduct(product);
        if (isSuccess) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("/{productId}")
    public Response deleteProduct(@PathParam("productId") long productId) {
        boolean isSuccess = productService.deleteProduct(productId);
        if (isSuccess) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/{productId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductById(@PathParam("productId") long productId) {
        Product product = productService.getProductById(productId);
        if (product != null) {
            return Response.status(Response.Status.OK).entity(product).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return Response.status(Response.Status.OK).entity(products).build();
    }

    @GET
    @Path("/getInfoProduct")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInfoProduct() {
        List<InformationProduct> infoProduct = productService.getInfoProduct();
        return Response.status(Response.Status.OK).entity(infoProduct).build();
    }

    @GET
    @Path("/getAllNameOfProductOrdered")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllNameOfProductOrdered() {
        List<Object[]> objects = productService.getAllNameOfProductOrdered();
        return Response.status(Response.Status.OK).entity(objects).build();
    }

}