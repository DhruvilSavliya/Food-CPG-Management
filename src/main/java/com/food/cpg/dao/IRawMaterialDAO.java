package com.food.cpg.dao;

import java.util.List;

import com.food.cpg.models.RawMaterial;

/**
 * Raw Material DAO interface
 *
 * @author Kartik Gevariya
 */
public interface IRawMaterialDAO {

    List<RawMaterial> getRawMaterialsList(int manufacturerId);

    RawMaterial getRawMaterial(int rawMaterialId);

    void saveRawMaterial(RawMaterial rawMaterial);

    void updateRawMaterial(RawMaterial rawMaterial);

    void deleteRawMaterial(int rawMaterialId);
}