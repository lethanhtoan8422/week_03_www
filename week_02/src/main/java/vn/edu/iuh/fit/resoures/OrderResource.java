package vn.edu.iuh.fit.resoures;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import vn.edu.iuh.fit.entities.RequestOrderDate;
import vn.edu.iuh.fit.entities.ResponseOrderByDateBetween;
import vn.edu.iuh.fit.models.Customer;
import vn.edu.iuh.fit.models.Order;
import vn.edu.iuh.fit.repositories.OrderRepository;
import vn.edu.iuh.fit.services.OrderService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Path("/orders")
public class OrderResource {
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    public OrderResource() {
        orderRepository = new OrderRepository();
        orderService = new OrderService();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createOrder(Order order) {
        boolean isSuccess = orderService.createOrder(order);
        if (isSuccess) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("/{orderId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateOrder(@PathParam("orderId") long orderId, Order order) {
        order.setOrderId(orderId);
        boolean isSuccess = orderService.updateOrder(order);
        if (isSuccess) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("/{orderId}")
    public Response deleteOrder(@PathParam("orderId") long orderId) {
        boolean isSuccess = orderService.deleteOrder(orderId);
        if (isSuccess) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrderById(@PathParam("orderId") long orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order != null) {
            return Response.status(Response.Status.OK).entity(order).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return Response.status(Response.Status.OK).entity(orders).build();
    }

    @GET
    @Path("/getID")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrderId() {
        long id = orderService.getOrderId();
        if (id != 0) {
            return Response.ok(id).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/inforOrder")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerByCust(Order or) {
        Order order = orderService.getOrderByOrder(or);
        return Response.ok(order).build();
    }

    @POST
    @Path("/orders-by-date-between")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrderByDateBetWeen(RequestOrderDate requestOrderDate) {
        List<ResponseOrderByDateBetween> orders = orderRepository.getOrderByDateBetWeen(requestOrderDate);
        return Response.ok(orders).build();
    }
}