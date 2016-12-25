package ru.innopolis.uni.model.service;

import ru.innopolis.uni.model.dao.CustomerDao;
import ru.innopolis.uni.model.dao.ProductDao;
import ru.innopolis.uni.model.dao.impl.CustomerDaoImpl;
import ru.innopolis.uni.model.dao.impl.ProductDaoImpl;
import ru.innopolis.uni.model.entityDao.Category;
import ru.innopolis.uni.model.entityDao.Product;
import ru.innopolis.uni.model.entityDao.SubCategory;

import java.util.List;

/**
 * Created by innopolis on 24.12.2016.
 */
public class Service implements ProductDao,CustomerDao{
    private ProductDao productDao;
    private CustomerDao customerDao;

    public Service() {
        this.productDao = new ProductDaoImpl();
        this.customerDao = new CustomerDaoImpl();
    }



    @Override
    public boolean registerCustomer(String email, String password)  {
        return customerDao.registerCustomer(email,password);
    }

    @Override
    public boolean verifyUser(String email, String password)  {
        return customerDao.verifyUser(email,password);
    }

    @Override
    public List<Product> getAllProducts()  {
        return productDao.getAllProducts();
    }

    @Override
    public Product getProductDetails(int idproduct)  {
        return productDao.getProductDetails(idproduct);
    }

    @Override
    public List<Category> getAllCategories()  {
        return productDao.getAllCategories();
    }

    @Override
    public List<SubCategory> getSubCategory(Category category) {
        return productDao.getSubCategory(category);
    }

    @Override
    public List<Product> getProductBySubCategory(String subCategory)  {
        return productDao.getProductBySubCategory(subCategory);
    }

    @Override
    public String getCategoryBySubCategory(String subCategory) {
        return productDao.getCategoryBySubCategory(subCategory);
    }

    @Override
    public List<Product> getProductByCategory(String category)  {
        return productDao.getProductByCategory(category);
    }
}
