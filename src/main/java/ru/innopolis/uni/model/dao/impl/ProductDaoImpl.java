package ru.innopolis.uni.model.dao.impl;

import ru.innopolis.uni.database.DBConnection;
import ru.innopolis.uni.model.dao.ProductDao;
import ru.innopolis.uni.model.dao.daoException.DataBaseException;
import ru.innopolis.uni.model.entityDao.Category;
import ru.innopolis.uni.model.entityDao.Product;
import ru.innopolis.uni.model.entityDao.SubCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by innopolis on 24.12.2016.
 */
public class ProductDaoImpl implements ProductDao {
    private List<Product> products = null;
    private List<Category> categories = null;
    private List<SubCategory> subCategories = null;
    private String categoryName;

    // Method to get all products available
    @Override
    public List<Product> getAllProducts()  throws DataBaseException {
        Connection conn = null;
        Statement ps = null;
        ResultSet rs = null;
        String sql;
        conn = DBConnection.getConnecton();
        sql = "select * from products";
        products = new ArrayList<Product>();

        try {
            ps = conn.createStatement();
            rs = ps.executeQuery(sql);

            while (rs.next()) {
                SubCategory subCategory = new SubCategory();
                subCategory.setProductCategory( rs.getString("subCategory"));

                Product p = new Product(rs.getInt("idproduct"),
                        rs.getString("productName"),
                        rs.getDouble("productPrice"),
                        rs.getString("description"),
                        rs.getString("productManufacturer"));
                products.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

                try {
                    if (rs != null ) {
                        rs.close();
                    }
                    if (ps != null) {
                        ps.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        return products;
    }

    // Method to get the required Product Details
    @Override
    public Product getProductDetails(int idproduct)  throws DataBaseException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql;
        conn = DBConnection.getConnecton();
        Product p = new Product();
        Category cat = new Category();
        SubCategory sub = new SubCategory();
        sql = "select productName,productPrice,description,categoryName,subCategory," +
                "productManufacturer from products where idproduct=?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idproduct);
            rs = ps.executeQuery();
            while (rs.next()) {
                cat.setCategoryid(rs.getInt(4));
                sub.setCategoryid(rs.getInt(5));
               // cat.setProductCategory(rs.getString(4));
              //  sub.setProductCategory(rs.getString(5));
                p.setProductId(idproduct);
                p.setProductName(rs.getString(1));
                p.setProductPrice(rs.getDouble(2));
                p.setDescription(rs.getString(3));
                p.setCategoryName(cat);
                p.setSubCategory(sub);
                p.setProductManufacturer(rs.getString(6));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null ) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return p;
    }

    // Method to get all the available Categories
    @Override
    public List<Category> getAllCategories()  throws DataBaseException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql;
        conn = DBConnection.getConnecton();
        sql = "select idcategory, productCategory from category";
        categories = new ArrayList<Category>();

        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Category c = new Category(rs.getString("productCategory"));
                c.setCategoryid(rs.getInt("idcategory"));
                categories.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null ) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return categories;
    }

    // Method to get all the available Subcategories under a Category
    @Override
    public List<SubCategory> getSubCategory(Category category) throws DataBaseException  {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql;
        conn = DBConnection.getConnecton();

        sql = "SELECT idsubCategory,subCategoryName FROM subcategory  where category_id = ? ";

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, category.getCategoryid());
            rs = ps.executeQuery();
            subCategories = new ArrayList<>();
            while (rs.next()) {
                SubCategory subCategory = new SubCategory();
                subCategory.setProductCategory(rs.getString("subCategoryName"));
                subCategory.setCategoryid(rs.getInt("idsubCategory"));
                subCategories.add(subCategory);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (rs != null ) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return subCategories;
    }

    // Method to get all the Products based on specified SubCategory
    @Override
    public List<Product> getProductBySubCategory(String subCategory)   throws DataBaseException{
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql;
        conn = DBConnection.getConnecton();
        sql = "select idproduct, productName,productPrice,description,categoryName," +
                "productManufacturer from products where subCategory=?";
        products = new ArrayList<Product>();
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(subCategory));
            rs = ps.executeQuery();
            while (rs.next()) {
                SubCategory cat = new SubCategory();
                cat.setProductCategory(rs.getString(5));
                Product p = new Product(rs.getInt(1), rs.getString(2),
                        rs.getDouble(3), rs.getString(4),cat ,
                        rs.getString(6));
                products.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (rs != null ) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return products;
    }
    @Override
    public List<Product> getProductByCategory(String category)  throws DataBaseException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql;
        conn = DBConnection.getConnecton();
        sql = "select idproduct, productName,productPrice,description,categoryName," +
                "productManufacturer from products where categoryName=?";
        products = new ArrayList<Product>();
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, category);
            rs = ps.executeQuery();
            while (rs.next()) {
                Category cat = new Category();
                cat.setProductCategory(rs.getString(5));
                Product p = new Product(rs.getInt(1), rs.getString(2),
                        rs.getDouble(3), rs.getString(4),cat ,
                        rs.getString(6));
                products.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
        }
        return products;
    }



    // Method to get Product Category
    // based on Sub Category
    @Override
    public String getCategoryBySubCategory(String subCategory)  throws DataBaseException{
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql;
        conn = DBConnection.getConnecton();
        sql = "select category_id from subcategory where subCategoryName=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, subCategory);
            rs = ps.executeQuery();
            while (rs.next()) {
                categoryName = rs.getString("category_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return categoryName;
    }


}
