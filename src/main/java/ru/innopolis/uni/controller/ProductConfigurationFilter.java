package ru.innopolis.uni.controller;

import ru.innopolis.uni.model.dao.ProductDao;
import ru.innopolis.uni.model.dao.impl.ProductDaoImpl;
import ru.innopolis.uni.model.entityDao.Category;
import ru.innopolis.uni.model.entityDao.SubCategory;

import javax.servlet.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Servlet Filter implementation class ProductConfigurationFilter
 */
public class ProductConfigurationFilter implements Filter {

	private ServletContext context;

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		this.context = fConfig.getServletContext();
		this.context.log("Configuring Products and Categories");
	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		 HashMap<String, List<SubCategory>> hashMap=new HashMap<>(1);
		 
		 ProductDao service = new ProductDaoImpl();
		 List<Category> categoryList = service.getAllCategories();
		
		for (Category category : categoryList) {

			hashMap.put(category.getProductCategory(), service.getSubCategory(category));
		
			this.context.log("Category Names available are:"+category.getProductCategory());
		}

		this.context.setAttribute("categories", hashMap);
		

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}
	@Override
	public void destroy() {
	}

}
