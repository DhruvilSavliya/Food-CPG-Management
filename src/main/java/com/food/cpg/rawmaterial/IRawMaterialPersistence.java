package com.food.cpg.rawmaterial;

import java.util.List;

public interface IRawMaterialPersistence {

    List<RawMaterial> getAll(int manufacturerId);

    void load(RawMaterial rawMaterial);

    void save(RawMaterial rawMaterial);

    void update(RawMaterial rawMaterial);

    void delete(int rawMaterialId);
}