package com.food.cpg.rawmaterial;

public class RawMaterialDatabaseQuery {

    public static final String SELECT_ALL_RAWMATERIALS = "select * from raw_materials where manufacturer_id = ?";

    public static final String LOAD_RAWMATERIALS = "select * from raw_materials where raw_material_id = ?";

    public static final String INSERT_RAWMATERIALS = "insert into raw_materials (raw_material_name, vendor_id, unit_cost, unit_measurement, unit_measurement_uom, reorder_point_quantity, reorder_point_quantity_uom, manufacturer_id) " +
            "values (?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String UPDATE_RAWMATERIALS = "update raw_materials set raw_material_name = ?, vendor_id = ?, unit_cost = ?, unit_measurement = ?, unit_measurement_uom = ?, reorder_point_quantity = ?, reorder_point_quantity_uom = ? where raw_material_id = ?";

    public static final String DELETE_RAWMATERIALS = "delete from raw_materials where raw_material_id = ?";

    private RawMaterialDatabaseQuery() {
    }
}
