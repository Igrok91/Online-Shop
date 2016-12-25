package ru.innopolis.uni.model.dao;

/**
 * Created by innopolis on 24.12.2016.
 */
public interface CustomerDao {
    boolean registerCustomer(String email, String password);
    boolean verifyUser(String email, String password);

}
