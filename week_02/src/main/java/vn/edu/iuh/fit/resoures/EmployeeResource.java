package vn.edu.iuh.fit.resoures;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import vn.edu.iuh.fit.models.Employee;
import vn.edu.iuh.fit.services.EmployeeService;
import java.util.List;

@Path("/employees")
public class EmployeeResource {
    private final EmployeeService employeeService;

    public EmployeeResource() {
        employeeService = new EmployeeService();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createEmployee(Employee employee) {
        boolean isSuccess = employeeService.createEmployee(employee);
        if (isSuccess) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("/{empId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEmployee(@PathParam("empId") long empId, Employee employee) {
        employee.setEmpId(empId);
        boolean isSuccess = employeeService.updateEmployee(employee);
        if (isSuccess) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("/{empId}")
    public Response deleteEmployee(@PathParam("empId") long empId) {
        boolean isSuccess = employeeService.deleteEmployee(empId);
        if (isSuccess) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/{empId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeById(@PathParam("empId") long empId) {
        Employee employee = employeeService.getEmployeeById(empId);
        if (employee != null) {
            return Response.status(Response.Status.OK).entity(employee).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return Response.status(Response.Status.OK).entity(employees).build();
    }
}
