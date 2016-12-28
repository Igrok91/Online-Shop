package ru.innopolis.uni.controller;

import ru.innopolis.uni.model.entityDao.Product;
import ru.innopolis.uni.model.service.Service;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by innopolis on 28.12.2016.
 */
public class HomeServlet extends HttpServlet {
    private HttpSession hs;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userPath = req.getServletPath();
        String getURL = "/" + userPath + ".jsp";
        Service service = new Service();
        if (userPath.equals("/home")) {
            List<Product> productsList = service.getAllProducts();
            getServletContext().setAttribute("productsList", productsList);

        }
        RequestDispatcher rd = getServletContext().getRequestDispatcher(getURL);
        rd.forward(req, resp);
    }
}
