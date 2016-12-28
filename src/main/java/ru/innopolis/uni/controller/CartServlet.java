package ru.innopolis.uni.controller;

import ru.innopolis.uni.model.entityDao.Product;
import ru.innopolis.uni.model.service.ProductService;
import ru.innopolis.uni.model.service.cart.ShoppingCart;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by innopolis on 28.12.2016.
 */
public class CartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userPath = req.getServletPath();
        String postURL = "/" + userPath + ".jsp";
        ProductService service = new ProductService();

        // Если пользователь добавляет продукт в корзину
        if (userPath.equals("/addProducts")) {


            HttpSession hs = req.getSession();
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
                resp.sendRedirect("product.jsp");
            }
        } else if (userPath.equals("/update")) {
            String prod_id = req.getParameter("productid");
            int productid = Integer.parseInt(prod_id);
            int quantity = Integer.parseInt(req.getParameter("quantity"));
            System.out.println(prod_id);

            Product product = (Product) service
                    .getProductDetails(productid);
            HttpSession s = req.getSession();
            ShoppingCart cart = (ShoppingCart) s.getAttribute("cart");
            System.out.println(cart);
            if (cart != null) {
                cart.updateQuantity(productid, quantity, product);
                System.out.println(quantity);
            }
            resp.sendRedirect("cart.jsp");
        }   // If user wants to remove an item from cart
        else if (userPath.equals("/remove")) {
            HttpSession httpSession = req.getSession();
            int id = Integer.parseInt(req.getParameter("pid"));
            ShoppingCart cart = (ShoppingCart) httpSession.getAttribute("cart");

            if (cart != null) {
                cart.remove(id);
                resp.sendRedirect("cart.jsp");
            }
        }
    }
}
