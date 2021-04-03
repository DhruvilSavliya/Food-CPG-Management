package com.food.cpg.packaging;

import java.util.List;

public interface IPackagesPersistence {
    List<Packages> getAll(int manufacturerId);

    void load(Packages packages);

    void save(Packages packages);

    void update(Packages packages);

    void delete(int packageId);
}
