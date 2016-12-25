package ru.innopolis.uni.model.dao;

import ru.innopolis.uni.model.entityDao.Category;
import ru.innopolis.uni.model.entityDao.Product;
import ru.innopolis.uni.model.entityDao.SubCategory;

import java.util.List;

/**
 * Created by innopolis on 24.12.2016.
 */
public interface ProductDao {
    List<Product> getAllProducts();
    Product getProductDetails(int idproduct);
    List<Category> getAllCategories();
    List<String> getSubCategory(Category category);
    List<Product> getProductBySubCategory(String subCategory);
    List<Product> getProductByCategory(String category);
    String getCategoryBySubCategory(String subCategory);

}
