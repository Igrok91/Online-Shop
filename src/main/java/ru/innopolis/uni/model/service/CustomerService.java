package ru.innopolis.uni.model.service;

import ru.innopolis.uni.model.dao.CustomerDao;
import ru.innopolis.uni.model.dao.ProductDao;
import ru.innopolis.uni.model.dao.impl.CustomerDaoImpl;

/**
 * Created by innopolis on 28.12.2016.
 */
public class CustomerService implements CustomerDao {
    private CustomerDao customerDao;

    public CustomerService(){
        customerDao = new CustomerDaoImpl();
    }
    @Override
    public boolean registerCustomer(String email, String password) {
        return customerDao.registerCustomer(email,password);
    }

    @Override
    public boolean verifyUser(String email, String password) {
        return customerDao.verifyUser(email,password);
    }
}
