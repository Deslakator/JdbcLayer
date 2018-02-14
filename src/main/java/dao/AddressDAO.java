package dao;

import entity.Address;

import java.util.List;

public interface AddressDAO {

    void add(Address address);

    void update(Address address);

    void delete(Address address);

    List<Address> getAll();

    Address getById(long id);
}
