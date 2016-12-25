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
import java.util.List;

/**
 * Created by innopolis on 24.12.2016.
 */
public class DispatcherServlet extends HttpServlet {
    private HttpSession hs;


    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        String userPath = request.getServletPath();
        String getURL = "/" + userPath + ".jsp";
        Service service = new Service();

        // If user requested category page
        if (userPath.equals("/category")) {
            String subCategory = request.getParameter("subcat");
            String categoryName = request.getParameter("category");
            // If the user requested only products
            // of specific category
            if (categoryName != null) {
                List<Product> productsCategoryList = service.getProductByCategory(categoryName);
                request.setAttribute("productByCategory", productsCategoryList);
            }

            // If the user requested only products
            // of specific subcategory
            if (subCategory != null) {
                List<Product> categoryProducts = service
                        .getProductBySubCategory(subCategory);
                String cat = service
                        .getCategoryBySubCategory(subCategory);
                getServletContext().setAttribute("categoryProducts",
                        categoryProducts);
                getServletContext().setAttribute("cat", cat);
            }

            getServletContext().setAttribute("subCat", subCategory);
        }
        // If user requested cart page
        else if (userPath.equals("/cart")) {
            // Retrieve all the items available in the cart
            hs = request.getSession();
            ShoppingCart cart = (ShoppingCart) hs.getAttribute("cart");
            RequestDispatcher rd = getServletContext().getRequestDispatcher(
                    getURL);
            rd.forward(request, response);
            return;
        }
        // If user requested checkout page
        else if (userPath.equals("/checkout")) {
            hs = request.getSession();
            ShoppingCart cart = (ShoppingCart) hs.getAttribute("cart");
            RequestDispatcher rd = getServletContext().getRequestDispatcher(
                    "/checkout_unreg.jsp");
            rd.forward(request, response);
            return;
        }
        // If user request home page
        else if (userPath.equals("/home")) {
            List<Product> productsList = service.getAllProducts();
            getServletContext().setAttribute("productsList", productsList);

        }
        // If user request product page
        else if (userPath.equals("/product")) {
            int productId = Integer.parseInt(request.getParameter("productId"));
            Product product = (Product) service
                    .getProductDetails(productId);
            hs = request.getSession();
            hs.setAttribute("product", product);
            hs.setAttribute("productID", productId);
            // Set Product Category and SubCategory in the Context Attribute
            getServletContext().setAttribute("productCategory",
                    product.getCategoryName());
            getServletContext().setAttribute("productSubCategory",
                    product.getSubCategory());
        }
        // If user request Logout
        else if (userPath.equals("/logout")){
            request.getSession().invalidate();
            request.getSession().removeAttribute("email");
            RequestDispatcher rd = getServletContext().getRequestDispatcher(
                    "/home.jsp");
            rd.forward(request, response);
            return;
        }

        // Forward the request to appropriate
        // views (JSP's)
        RequestDispatcher rd = getServletContext().getRequestDispatcher(getURL);
        rd.forward(request, response);

    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        String userPath = request.getServletPath();
        String postURL = "/" + userPath + ".jsp";
        Service service = new Service();

        // If user request to add products
        // to shopping cart
        if (userPath.equals("/addProducts")) {

            // Request the Session
            HttpSession hs = request.getSession();
            ShoppingCart cart = (ShoppingCart) hs.getAttribute("cart");

            // Checks whether cart is available
            // If not, then will create a cart object
            if (cart == null) {
                cart = new ShoppingCart();
                hs.setAttribute("cart", cart);
            }

            int prodID = Integer.parseInt((hs.getAttribute("productID")
                    .toString()));
            Integer productID = new Integer(prodID);
            // Check whether the product id is not null
            // If not null then add the product to the cart
            if (productID != null) {
                Product p = service.getProductDetails(productID);
                cart.add(productID, p);
                response.sendRedirect("product.jsp");
            }
        }

        // If user request to update the products
        else if (userPath.equals("/update")) {
            String prod_id = request.getParameter("productid");
            int productid = Integer.parseInt(prod_id);
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            Product product = (Product) service
                    .getProductDetails(productid);

            ShoppingCart cart = (ShoppingCart) hs.getAttribute("cart");

            if (cart != null) {
                cart.updateQuantity(productid, quantity, product);
            }
            response.sendRedirect("cart.jsp");
        }
        // If user request to purchase the products
        else if (userPath.equals("/purchase")) {
            ShoppingCart cart = (ShoppingCart) hs.getAttribute("cart");
            cart.clear();
            response.sendRedirect("orderconfirm.jsp");
        }
        // If user registers
        else if (userPath.equals("/register")) {
            String email = request.getParameter("inputEmail");
            String password = request.getParameter("password");
            boolean success = service.registerCustomer(email, password);

            if (success) {
                request.setAttribute("regstatus", "success");
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
                response.sendRedirect("login.jsp?regStatus=fail");
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
