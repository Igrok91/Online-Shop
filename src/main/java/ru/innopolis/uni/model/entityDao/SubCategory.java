package ru.innopolis.uni.model.entityDao;

/**
 * Created by innopolis on 24.12.2016.
 */
public class SubCategory {
    private int categoryid;
    private String productCategory;
    private Category category;


    public SubCategory(int categoryid, String productCategory, Category category) {
        this.categoryid = categoryid;
        this.productCategory = productCategory;
        this.category = category;
    }

    public SubCategory() {
    }

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
