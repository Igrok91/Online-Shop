package ru.innopolis.uni.model.dao;

import ru.innopolis.uni.model.dao.daoException.DataBaseException;
import ru.innopolis.uni.model.entityDao.Category;
import ru.innopolis.uni.model.entityDao.Product;
import ru.innopolis.uni.model.entityDao.SubCategory;

import java.util.List;

/**
 * Created by innopolis on 24.12.2016.
 */
public interface ProductDao {
    List<Product> getAllProducts() throws DataBaseException;
    Product getProductDetails(int idproduct) throws DataBaseException;
    List<Category> getAllCategories() throws DataBaseException;
    List<SubCategory> getSubCategory(Category category) throws DataBaseException;
    List<Product> getProductBySubCategory(String subCategory) throws DataBaseException;
    List<Product> getProductByCategory(String category) throws DataBaseException;
    String getCategoryBySubCategory(String subCategory) throws DataBaseException;

}
