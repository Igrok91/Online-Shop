package ru.innopolis.uni.model.service;

import org.apache.commons.codec.digest.DigestUtils;
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

        return customerDao.registerCustomer(email, crypt(password));
    }

    @Override
    public boolean verifyUser(String email, String password) {
        return customerDao.verifyUser(email,crypt(password));
    }

    private String crypt(String password) {
        String md5Hex = DigestUtils.md5Hex(password);
        return  md5Hex;
    }

}