package ru.innopolis.uni.model.dao.impl;

import ru.innopolis.uni.database.DBConnection;
import ru.innopolis.uni.model.dao.CustomerDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by innopolis on 24.12.2016.
 */
public class CustomerDaoImpl implements CustomerDao {
    private Connection conn;

    // This method is used to register customer
    @Override
    public boolean registerCustomer(String email, String password){
        PreparedStatement ps = null;

        try {
            conn = DBConnection.getConnecton();
            String sql = "insert into user(password, email) values(?,?)";

            ps = conn.prepareStatement(sql);
            ps.setString(2, email);
            ps.setString(1, password);

            int result = ps.executeUpdate();

            if (result > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return false;
    }

    // This method is used to verify if the customer is registered
    // or not
    @Override
    public boolean verifyUser(String email, String password) {
        conn = DBConnection.getConnecton();
        PreparedStatement ps = null;
        String sql = "select iduser from user where email=? AND password=?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (rs.getString("iduser") != null) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return false;
    }
}
