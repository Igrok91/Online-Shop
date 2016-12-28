package ru.innopolis.uni.controller;

import ru.innopolis.uni.model.entityDao.Product;
import ru.innopolis.uni.model.service.ProductService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by IgorRyabtsev on 28.12.2016.
 */
public class CategoryServlet extends HttpServlet {
    private HttpSession hs;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userPath = req.getServletPath();
        String getURL = "/" + userPath + ".jsp";
        ProductService service = new ProductService();

        //  Если пользователь запрашивает поиск категории
        if (userPath.equals("/category")) {
            String subCategory = req.getParameter("subcat");
            String categoryName = req.getParameter("categ");
            System.out.println(subCategory);
            System.out.println(categoryName);
            if (categoryName != null) {
                List<Product> productsCategoryList = service.getProductByCategory(categoryName);
                req.setAttribute("productByCategory", productsCategoryList);
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
        }  // Если пользователь запрашивает продукт
        else if (userPath.equals("/product")) {
            int productId = Integer.parseInt(req.getParameter("productId"));
            Product product = (Product) service
                    .getProductDetails(productId);
            hs = req.getSession();
            hs.setAttribute("product", product);
            hs.setAttribute("productID", productId);

            getServletContext().setAttribute("productCategory",
                    product.getCategoryName().getCategoryid());
            getServletContext().setAttribute("productSubCategory",
                    product.getSubCategory().getCategoryid());
        }

        RequestDispatcher rd = getServletContext().getRequestDispatcher(getURL);
        rd.forward(req, resp);
    }
}
