package com.stockmaster.servlet;

import com.stockmaster.model.Customer;
import com.stockmaster.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/customers")
public class CustomerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private CustomerService customerService;

    @Override
    public void init() throws ServletException {
        customerService = new CustomerService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

     

        if (action == null) {
            request.setAttribute("customers", customerService.getAllCustomers());
            request.getRequestDispatcher("customer/index.jsp").forward(request, response);

        } else if (action.equals("create")) {
            request.getRequestDispatcher("customer/create.jsp").forward(request, response);

        } else if (action.equals("edit")) {
            String id = request.getParameter("id");
            Customer customer = customerService.getCustomerById(id);
            request.setAttribute("customer", customer);
            request.getRequestDispatcher("customer/update.jsp").forward(request, response);

        } else if (action.equals("delete")) {
            String id = request.getParameter("id");
            customerService.deleteCustomer(id);
            response.sendRedirect("customers");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action.equals("create")) {
            Customer customer = new Customer();
            customer.setCustomerId(request.getParameter("customerId"));
            customer.setFirstName(request.getParameter("firstName"));
            customer.setLastName(request.getParameter("lastName"));
            customer.setCusAddress(request.getParameter("cusAddress"));
            customer.setEmail(request.getParameter("email"));
            customer.setPhoneNumber(request.getParameter("phoneNumber"));

            if (customerService.createCustomer(customer)) {
                response.sendRedirect("customers");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }

        } else if (action.equals("update")) {
            Customer customer = new Customer();
            customer.setCustomerId(request.getParameter("customerId"));
            customer.setFirstName(request.getParameter("firstName"));
            customer.setLastName(request.getParameter("lastName"));
            customer.setCusAddress(request.getParameter("cusAddress"));
            customer.setEmail(request.getParameter("email"));
            customer.setPhoneNumber(request.getParameter("phoneNumber"));

            if (customerService.updateCustomer(customer)) {
                response.sendRedirect("customers");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }
}