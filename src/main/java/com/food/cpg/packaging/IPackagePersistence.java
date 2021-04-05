package com.food.cpg.packaging;

import java.util.List;

public interface IPackagePersistence {
    List<Package> getAll(int manufacturerId);

    void load(Package packages);

    void save(Package packages);

    void update(Package packages);

    void delete(int packageId);
}
