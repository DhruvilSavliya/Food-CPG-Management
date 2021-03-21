package com.food.cpg.services;

import java.util.List;

import com.food.cpg.models.RawMaterial;

/**
 * @author Kartik Gevariya
 */
public interface IRawMaterialService {

    List<RawMaterial> getRawMaterialsList(int manufacturerId);
}
