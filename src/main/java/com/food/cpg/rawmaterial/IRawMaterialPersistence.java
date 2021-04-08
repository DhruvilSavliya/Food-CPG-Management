package com.food.cpg.rawmaterial;

import java.util.List;

public interface IRawMaterialPersistence {

    List<IRawMaterial> getAll(int manufacturerId);

    void load(RawMaterial rawMaterial);

    Integer save(RawMaterial rawMaterial);

    void update(RawMaterial rawMaterial);

    void delete(int rawMaterialId);
}