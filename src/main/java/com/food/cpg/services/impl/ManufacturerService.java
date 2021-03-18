package com.food.cpg.services.impl;

import java.util.List;

import com.food.cpg.dao.IManufacturerDAO;
import com.food.cpg.dao.IUserPersistence;
import com.food.cpg.dao.impl.ManufacturerDAOImpl;
import com.food.cpg.dao.impl.UserPersistence;
import com.food.cpg.models.Manufacturer;
import com.food.cpg.models.Role;
import com.food.cpg.models.User;
import com.food.cpg.services.IManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ManufacturerService implements IManufacturerService
{

    private final IManufacturerDAO manufacturerDAO;
    private final IUserPersistence userPersistence;

    @Autowired
    public ManufacturerService(ManufacturerDAOImpl manufacturerDAO, UserPersistence userPersistence) {
        this.manufacturerDAO = manufacturerDAO;
        this.userPersistence = userPersistence;
    }


    @Override
    public List<Manufacturer> getManufacturerList(int manufacturerId) {
        return manufacturerDAO.getManufacturerList(manufacturerId);
    }

    @Override
    public void saveManufacturer(Manufacturer manufacturer) {

        manufacturerDAO.saveManufacturer(manufacturer);
        User user = new User();
        user.setEmail(manufacturer.getEmail());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(manufacturer.getPassword());
        user.setPassword(encodedPassword);
        user.setRole(Role.MANUFACTURER.name());
        userPersistence.saveUser(user);

    }


    @Override
    public Manufacturer getManufacturer(int manufacturerId) {
        return manufacturerDAO.getManufacturer(manufacturerId);
    }

    @Override
    public void approveManufacturer(int manufacturerId) { manufacturerDAO.approveManufacturer(manufacturerId); }

    @Override
    public void blockManufacturer(int manufacturerId) {
        manufacturerDAO.blockManufacturer(manufacturerId);
    }


}
