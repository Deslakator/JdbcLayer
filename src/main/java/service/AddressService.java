package service;

import dao.AddressDAO;
import database.Util;
import entity.Address;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressService implements AddressDAO {

    private final static String sqlInsert = "INSERT INTO address(city, postcode) VALUES(?,?)";
    private final static String sqlUpdate = "UPDATE address SET city = (?), postcode = (?) WHERE id = (?)";
    private final static String sqlDelete = "DELETE address WHERE id = (?)";

    private final static String sqlSelectById = "SELECT id, city, postcode FROM address WHERE id = (?)";
    private final static String sqlSelect = "SELECT id, city, postcode FROM address";

    private final Util util;

    public AddressService(Util util) {
        this.util = util;
    }

    @Override
    public void add(Address address) {
        try (Connection connection = util.getConnection();
             PreparedStatement prSt = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
            prSt.setString(1, address.getCity());
            prSt.setString(2, address.getPostcode());
            prSt.executeUpdate();
            try (ResultSet rs = prSt.getGeneratedKeys()) {
                if (rs.next()) {
                    long key = rs.getLong(1);
                    address.setId(key);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Address address) {
        try (Connection connection = util.getConnection();
             PreparedStatement prSt = connection.prepareStatement(sqlUpdate)) {
            prSt.setString(1, address.getCity());
            prSt.setString(2, address.getPostcode());
            prSt.setLong(3, address.getId());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Address address) {
        try (Connection connection = util.getConnection();
             PreparedStatement prSt = connection.prepareStatement(sqlDelete)) {
            prSt.setLong(1, address.getId());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Address> getAll() {
        List<Address> addressCollection = new ArrayList<>();
        try (Connection connection = util.getConnection();
             PreparedStatement prSt = connection.prepareStatement(sqlSelect)) {
            try (ResultSet rs = prSt.executeQuery()) {
                if (rs.next()) {
                    Address address = new Address();
                    address.setId(rs.getLong("id"));
                    address.setCity(rs.getString("city"));
                    address.setPostcode(rs.getString("postcode"));
                    addressCollection.add(address);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addressCollection;
    }

    @Override
    public Address getById(long id) {
        try (Connection connection = util.getConnection();
             PreparedStatement prSt = connection.prepareStatement(sqlSelectById)) {
            prSt.setLong(1, id);
            try (ResultSet rs = prSt.executeQuery()) {
                if (rs.next()) {
                    Address address = new Address();
                    address.setId(rs.getLong("id"));
                    address.setCity(rs.getString("city"));
                    address.setPostcode(rs.getString("postcode"));
                    return address;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
