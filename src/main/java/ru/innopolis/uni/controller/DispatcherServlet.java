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

        //  Если пользователь запрашивает поиск категории
        if (userPath.equals("/category")) {
            String subCategory = request.getParameter("subcat");
            String categoryName = request.getParameter("categ");
            System.out.println(subCategory);
            System.out.println(categoryName);
            if (categoryName != null) {
                List<Product> productsCategoryList = service.getProductByCategory(categoryName);
                request.setAttribute("productByCategory", productsCategoryList);
            }

            // Если пользователь запрашивает поиск в подкатегории
            if (subCategory != null) {
                List<Product> categoryProducts = service
                        .getProductBySubCategory(subCategory);
                System.out.println(categoryProducts);
                String cat = service
                        .getCategoryBySubCategory(subCategory);
                getServletContext().setAttribute("categoryProducts",
                        categoryProducts);
                getServletContext().setAttribute("cat", cat);
            }

            getServletContext().setAttribute("subCat", subCategory);
        }
        // Если пользователь запрашивает корзину
        else if (userPath.equals("/cart")) {
            hs = request.getSession();
            ShoppingCart cart = (ShoppingCart) hs.getAttribute("cart");
            RequestDispatcher rd = getServletContext().getRequestDispatcher(
                    getURL);
            rd.forward(request, response);
            return;
        }
        // Если пользователь запрашивает
        else if (userPath.equals("/checkout")) {
            hs = request.getSession();
            ShoppingCart cart = (ShoppingCart) hs.getAttribute("cart");
            RequestDispatcher rd = getServletContext().getRequestDispatcher(
                    "/checkout_unreg.jsp");
            rd.forward(request, response);
            return;
        }
        // Если пользователь запрашивает домашнюю страницу
        else if (userPath.equals("/home")) {
            List<Product> productsList = service.getAllProducts();
            getServletContext().setAttribute("productsList", productsList);

        }
        // Если пользователь запрашивает продукт
        else if (userPath.equals("/product")) {
            int productId = Integer.parseInt(request.getParameter("productId"));
            Product product = (Product) service
                    .getProductDetails(productId);
            hs = request.getSession();
            hs.setAttribute("product", product);
            hs.setAttribute("productID", productId);

            getServletContext().setAttribute("productCategory",
                    product.getCategoryName().getCategoryid());
            getServletContext().setAttribute("productSubCategory",
                    product.getSubCategory().getCategoryid());
        }
        // Если пользователь запрашивает Logout
        else if (userPath.equals("/logout")){
            request.getSession().invalidate();
            request.getSession().removeAttribute("email");
            RequestDispatcher rd = getServletContext().getRequestDispatcher(
                    "/home.jsp");
            rd.forward(request, response);
            return;
        }

        RequestDispatcher rd = getServletContext().getRequestDispatcher(getURL);
        rd.forward(request, response);

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
