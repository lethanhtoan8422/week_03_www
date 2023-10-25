package vn.edu.iuh.fit.resoures;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import vn.edu.iuh.fit.models.OrderDetail;
import vn.edu.iuh.fit.services.OrderDetailService;
import java.util.List;

@Path("/orderdetails")
public class OrderDetailResource {
    private final OrderDetailService orderDetailService;

    public OrderDetailResource() {
        orderDetailService = new OrderDetailService();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createOrderDetail(OrderDetail orderDetail) {
        boolean isSuccess = orderDetailService.createOrderDetail(orderDetail);
        return Response.status(isSuccess ? Response.Status.CREATED : Response.Status.INTERNAL_SERVER_ERROR)
                .entity(isSuccess)
                .build();

    }

    @PUT
    @Path("/{orderDetailId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateOrderDetail(@PathParam("orderDetailId") long orderDetailId, OrderDetail orderDetail) {
        orderDetail.setOrderDetailId(orderDetailId);
        boolean isSuccess = orderDetailService.updateOrderDetail(orderDetail);
        if (isSuccess) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("/{orderDetailId}")
    public Response deleteOrderDetail(@PathParam("orderDetailId") long orderDetailId) {
        boolean isSuccess = orderDetailService.deleteOrderDetail(orderDetailId);
        if (isSuccess) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/{orderDetailId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrderDetailById(@PathParam("orderDetailId") long orderDetailId) {
        OrderDetail orderDetail = orderDetailService.getOrderDetailById(orderDetailId);
        if (orderDetail != null) {
            return Response.status(Response.Status.OK).entity(orderDetail).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOrderDetails() {
        List<OrderDetail> orderDetails = orderDetailService.getAllOrderDetails();
        return Response.status(Response.Status.OK).entity(orderDetails).build();
    }

    @GET
    @Path("/getID")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrderId() {
        long id = orderDetailService.getOrderDetailId();
        if (id != 0) {
            return Response.ok(id).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/statisticOrderDetailOfMonthAndYear")
    @Produces(MediaType.APPLICATION_JSON)
    public Response statisticOrderDetailOfMonthAndYear() {
        List<Object[]> list = orderDetailService.statisticOrderDetailOfMonthAndYear();
        return Response.status(Response.Status.OK).entity(list).build();
    }

    @GET
    @Path("/statisticOrderDetailOfMonthAndYearByMonth/{month}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response statisticOrderDetailOfMonthAndYearByMonth(@PathParam("month") int month) {
        List<Object[]> list = orderDetailService.statisticOrderDetailOfMonthAndYearByMonth(month);
        return Response.status(Response.Status.OK).entity(list).build();
    }

    @GET
    @Path("/getTotalPricesOfYear")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTotalPricesOfYear() {
        double totalPrice = orderDetailService.getTotalPricesOfYear();
        return Response.status(Response.Status.OK).entity(totalPrice).build();
    }
}
