package ru.innopolis.uni.controller;

import ru.innopolis.uni.model.entityDao.Product;
import ru.innopolis.uni.model.service.Service;
import ru.innopolis.uni.model.service.ShoppingCart;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by innopolis on 28.12.2016.
 */
public class CustomerServlet extends HttpServlet {
    private HttpSession hs;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userPath = req.getServletPath();
        String getURL = "/" + userPath + ".jsp";
        Service service = new Service();
        if (userPath.equals("/checkout")) {
            hs = req.getSession();
            ShoppingCart cart = (ShoppingCart) hs.getAttribute("cart");
            RequestDispatcher rd = getServletContext().getRequestDispatcher(
                    "/checkout_unreg.jsp");
            rd.forward(req, resp);
            return;
        }// Если пользователь запрашивает Logout
        else if (userPath.equals("/logout")){
            req.getSession().invalidate();
            req.getSession().removeAttribute("email");
            RequestDispatcher rd = getServletContext().getRequestDispatcher(
                    "/home.jsp");
            rd.forward(req, resp);
            return;
        }

        RequestDispatcher rd = getServletContext().getRequestDispatcher(getURL);
        rd.forward(req, resp);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        String userPath = request.getServletPath();
        String postURL = "/" + userPath + ".jsp";
        Service service = new Service();

        // Если пользователь добавляет продукт в корзину
        if (userPath.equals("/addProducts")) {


            HttpSession hs = request.getSession();
            ShoppingCart cart = (ShoppingCart) hs.getAttribute("cart");


            if (cart == null) {
                cart = new ShoppingCart();
                hs.setAttribute("cart", cart);
            }

            int prodID = Integer.parseInt((hs.getAttribute("productID")
                    .toString()));
            Integer productID = new Integer(prodID);

            if (productID != null) {
                Product p = service.getProductDetails(productID);
                cart.add(productID, p);
                response.sendRedirect("product.jsp");
            }
        } else if (userPath.equals("/update")) {
            String prod_id = request.getParameter("productid");
            int productid = Integer.parseInt(prod_id);
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            System.out.println(prod_id);

            Product product = (Product) service
                    .getProductDetails(productid);
            HttpSession s = request.getSession();
            ShoppingCart cart = (ShoppingCart) s.getAttribute("cart");
            System.out.println(cart);
            if (cart != null) {
                cart.updateQuantity(productid, quantity, product);
                System.out.println(quantity);
            }
            response.sendRedirect("cart.jsp");
        }
        // If user request to purchase the products
        else if (userPath.equals("/purchase")) {
            HttpSession s = request.getSession();
            ShoppingCart cart = (ShoppingCart) s.getAttribute("cart");
            cart.clear();
            response.sendRedirect("orderconfirm.jsp");
        }
        // If user registers
        else if (userPath.equals("/register")) {
            String email = request.getParameter("inputEmail");
            String password = request.getParameter("password");
            boolean success = service.registerCustomer(email, password);
            System.out.println(success);

            if (success) {
                HttpSession hs = request.getSession();
                hs.setAttribute("regstatus", 1);
                //request.setAttribute("regstatus", 1);
                response.sendRedirect("login.jsp?regStatus=Success");
            } else {
                response.sendRedirect("checkout_unreg.jsp?regStatus=Fail");
            }
        }
        // If user logs in
        else if (userPath.equals("/login")) {
            String email = request.getParameter("inputEmail");
            String password = request.getParameter("password");

            boolean flag = service.verifyUser(email, password);
            if (flag) {
                HttpSession hs = request.getSession();
                hs.setAttribute("email", email);
                response.sendRedirect("final_checkout.jsp");
            } else {
                response.sendRedirect("login.jsp?regStatus=Fail");
            }

        }
        // If user wants to remove an item from cart
        else if (userPath.equals("/remove")) {
            int id = Integer.parseInt(request.getParameter("pid"));
            ShoppingCart cart = (ShoppingCart) hs.getAttribute("cart");

            if (cart != null) {
                cart.remove(id);
                response.sendRedirect("cart.jsp");
            }
        }

    }
}
