package com.food.cpg.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.cpg.dao.IPackageDAO;
import com.food.cpg.dao.impl.PackageDAOImpl;
import com.food.cpg.models.Packages;
import com.food.cpg.services.IPackageService;

/**
 * @author Dhruvilkumar Savliya
 */
@Service
public class PackageService implements IPackageService {

    private final IPackageDAO packageDAO;

    @Autowired
    public PackageService(PackageDAOImpl packageDAO) {
        this.packageDAO = packageDAO;
    }

    @Override
    public List<Packages> getPackageList(int manufacturerId) {
        return packageDAO.getPackageList(manufacturerId);
    }

    @Override
    public void savePackages(Packages packages) {
        packageDAO.savePackages(packages);
    }

    @Override
    public Packages getPackages(int packageId) {
        return packageDAO.getPackages(packageId);
    }

    @Override
    public void updatePackages(Packages packages) {
        packageDAO.updatePackages(packages);
    }

    @Override
    public void deletePackages(int packageId) {
        packageDAO.deletePackages(packageId);
    }
}
