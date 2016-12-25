package ru.innopolis.uni.controller;

import ru.innopolis.uni.model.dao.ProductDao;
import ru.innopolis.uni.model.dao.impl.ProductDaoImpl;
import ru.innopolis.uni.model.entityDao.Category;

import javax.servlet.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Servlet Filter implementation class ProductConfigurationFilter
 */
public class ProductConfigurationFilter implements Filter {

	private ServletContext context;

	

	public void init(FilterConfig fConfig) throws ServletException {
		this.context = fConfig.getServletContext();
		this.context.log("Configuring Products and Categories");
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		 HashMap<String, List<String>> hashMap=new HashMap<>(1);
		 
		 ProductDao service = new ProductDaoImpl();
		 List<Category> categoryList = service.getAllCategories();
		
		for (Category category : categoryList) {

			hashMap.put(category.getProductCategory(), service.getSubCategory(category));
		
			this.context.log("Category Names available are:"+category.getProductCategory());
		}

		this.context.setAttribute("categories", hashMap);
//		this.context.setAttribute("subcategory", subCategories);
		

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	public void destroy() {
	}

}
