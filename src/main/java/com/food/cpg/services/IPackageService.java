package com.food.cpg.services;

import java.util.List;

import com.food.cpg.models.Packages;

/**
 * @author Dhruvilkumar Savliya
 */
public interface IPackageService {

    List<Packages> getPackageList(int manufacturerId);

    void savePackages(Packages packages);

    Packages getPackages(int packageId);

    void updatePackages(Packages packages);

    void deletePackages(int packageId);
}
