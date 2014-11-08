package com.leilo.engine.utils;

import com.leilo.engine.items.Consumable;
import com.leilo.engine.items.Equipment;
import com.leilo.engine.items.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Item creator utility class
 */
public class ItemCreator {
    private static ItemCreator m_instance = null;

    protected ItemCreator() {
        // Exists only to defeat instantiation.
    }
    public static ItemCreator getInstance() {
        if(m_instance == null) {
            m_instance = new ItemCreator();
        }
        return m_instance;
    }

    public List<Item> createItems(List<String> itemIDs, Properties itemProperties) {
        List<Item> itemList = new ArrayList<Item>();
        for(String itemID : itemIDs) {
            Item item = createItem(itemID, itemProperties);
            if(item != null) {
                itemList.add(item);
            }
        }
        return itemList;
    }

    public Item createItem(String itemID, Properties itemProperties) {
        String itemName = itemProperties.getProperty(itemID + ".name");
        String itemDescription = itemProperties.getProperty(itemID + ".itemdescription");
        String roomDescriptions = itemProperties.getProperty(itemID + ".roomdescriptions");
        int itemType = Integer.parseInt(itemProperties.getProperty(itemID + ".itemtype"));
        switch (itemType) {
            case 0:
                return new Consumable(itemID, itemName, itemDescription, roomDescriptions);
            case 1:
                int equipmentSlot = Integer.parseInt(itemProperties.getProperty(itemID + ".equipmentslot"));
                return new Equipment(itemID, itemName, itemDescription, roomDescriptions, equipmentSlot);
            case 2:
                return new Item(itemID, itemName, itemDescription, roomDescriptions, 2);
            default:
                return null;
        }
    }
}
