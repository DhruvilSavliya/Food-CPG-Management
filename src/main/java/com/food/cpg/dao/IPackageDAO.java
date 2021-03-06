package com.food.cpg.dao;

import java.util.List;

import com.food.cpg.models.Packages;


/**
 * Package DAO interface
 *
 * @author Dhruvilkumar Savliya
 */
public interface IPackageDAO {

    List<Packages> getPackageList(int packageId);

    Packages getPackages(int packageId);

    void savePackages(Packages packages);

    void updatePackages(Packages packages);

    void deletePackages(int packageId);
}