package com.leilo.game.entities.items.util;

import com.leilo.game.entities.items.Equipment;
import com.leilo.game.entities.items.Item;
import com.leilo.game.entities.items.Useable;

import java.util.Properties;

public class ItemCreator {
    private static ItemCreator instance = null;

    protected ItemCreator() {
        // Exists only to defeat instantiation.
    }
    public static ItemCreator getInstance() {
        if(instance == null) {
            instance = new ItemCreator();
        }
        return instance;
    }

    public Item createItem(String itemCode, int itemType, Properties itemProperties) {
        Item item = new Item();
        switch (itemType) {
            case 0:
                item = createUseable(itemCode, itemProperties);
            case 1:
                item = createEquipment(itemCode, itemProperties);
            case 2:
                String itemName = itemProperties.getProperty(itemCode + ".title");
                String itemDescription = itemProperties.getProperty(itemCode + ".description");
                item = new Item(itemName, itemDescription);
        }
        return item;
    }

    private Useable createUseable(String itemCode, Properties itemProperties) {
        String useableTitle = itemProperties.getProperty(itemCode + ".title");
        String useableDescription = itemProperties.getProperty(itemCode + ".description");
        return new Useable(useableTitle, useableDescription);
    }

    private Equipment createEquipment(String itemCode, Properties itemProperties) {
        String equipmentTitle = itemProperties.getProperty(itemCode + ".title");
        String equipmentDescription = itemProperties.getProperty(itemCode + ".description");
        String equipmentSlot = itemProperties.getProperty(itemCode + ".slot");
        Equipment equipment = new Equipment(equipmentTitle, equipmentDescription);
        equipment.createBonusStatMap(itemCode, equipmentSlot, itemProperties);
        return equipment;
    }
}
