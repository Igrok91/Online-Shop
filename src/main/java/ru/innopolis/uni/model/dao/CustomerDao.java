package ru.innopolis.uni.model.dao;

import ru.innopolis.uni.model.dao.daoException.DataBaseException;

/**
 * Created by innopolis on 24.12.2016.
 */
public interface CustomerDao {
    boolean registerCustomer(String email, String password)  throws DataBaseException;
    boolean verifyUser(String email, String password)  throws DataBaseException;

}
