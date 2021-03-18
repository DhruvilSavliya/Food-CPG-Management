package com.food.cpg.services.impl;

import com.food.cpg.dao.IItemDAO;
import com.food.cpg.dao.impl.ItemDAOImpl;
import com.food.cpg.models.Item;
import com.food.cpg.models.RawMaterial;
import com.food.cpg.services.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService implements IItemService {

    private final IItemDAO itemDAO;

    @Autowired
    public ItemService(ItemDAOImpl itemDAO) {

        this.itemDAO = itemDAO;
    }

    @Override
    public List<Item> getItemsList(int manufacturerId) {
        return itemDAO.getItemsList(manufacturerId);
    }

    @Override
    public void saveItem(Item item) {
         itemDAO.saveItem(item);
    }

    @Override
    public Item getItem(int itemId) {
        return itemDAO.getItem(itemId);
    }

    @Override
    public void updateItem(Item item) { itemDAO.updateItem(item);
    }

    @Override
    public void deleteItem(int itemId) {

        itemDAO.deleteItem(itemId);
    }
}
