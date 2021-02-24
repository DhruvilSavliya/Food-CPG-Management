package com.food.cpg.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.cpg.dao.IRawMaterialDAO;
import com.food.cpg.dao.impl.RawMaterialDAOImpl;
import com.food.cpg.models.RawMaterial;
import com.food.cpg.services.IRawMaterialService;

/**
 * @author Kartik Gevariya
 */
@Service
public class RawMaterialService implements IRawMaterialService {

    private final IRawMaterialDAO rawMaterialDAO;

    @Autowired
    public RawMaterialService(RawMaterialDAOImpl rawMaterialDAO) {
        this.rawMaterialDAO = rawMaterialDAO;
    }

    @Override
    public List<RawMaterial> getRawMaterialsList(int manufacturerId) {
        return rawMaterialDAO.getRawMaterialsList(manufacturerId);
    }

    @Override
    public void saveRawMaterial(RawMaterial rawMaterial) {
        rawMaterialDAO.saveRawMaterial(rawMaterial);
    }

    @Override
    public RawMaterial getRawMaterial(int rawMaterialId) {
        return rawMaterialDAO.getRawMaterial(rawMaterialId);
    }

    @Override
    public void updateRawMaterial(RawMaterial rawMaterial) {
        rawMaterialDAO.updateRawMaterial(rawMaterial);
    }

    @Override
    public void deleteRawMaterial(int rawMaterialId) {
        rawMaterialDAO.deleteRawMaterial(rawMaterialId);
    }
}
