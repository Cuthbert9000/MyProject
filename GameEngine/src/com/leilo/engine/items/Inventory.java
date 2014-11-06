package com.leilo.engine.items;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Inventory that each player will have, contains all of their Items
 */
public class Inventory implements Serializable {

    private Map<String, Item> m_itemMap; //Map<ItemID, Item>
    private Map<String, Integer> m_itemCountMap; //Map<ItemID, # in Inventory>
    private List<String> m_itemList; //All ItemIDs in the Inventory
    private List<String> m_itemNameList; //All Item Titles in the Inventory for easy Inventory Printing
    private int m_inventorySize;

    public Inventory(int initialSize) {
        m_itemMap = new HashMap<String, Item>();
        m_itemList = new ArrayList<String>();
        m_itemNameList = new ArrayList<String>();
        m_inventorySize = initialSize;
    }

    public void setInventorySize(int newSize) {
        m_inventorySize = newSize;
    }

    public int getInventorySize() {
        return m_inventorySize;
    }

    public List<String> getItemList() {
        return m_itemList;
    }

    public List<String> getItemNameList() {
        return m_itemNameList;
    }

    /**
     * Attempts to add the item to the player's inventory
     * @return true == success | false == failure
     */
    public boolean addItemToInventory(Item item, int itemCount) {
        String itemID = item.getItemID();
        if(m_itemList.contains(itemID)) {
            int existingCount = m_itemCountMap.get(itemID);
            existingCount = existingCount + itemCount;
            m_itemCountMap.put(itemID, existingCount);
            return true;
        } else {
            //First, check if there is room
            if(m_itemList.size() < m_inventorySize) {
                m_itemMap.put(itemID, item);
                m_itemCountMap.put(itemID, itemCount);
                m_itemNameList.add(item.getName());
                return true;
            } else {
                //Inventory full, return false
                return false;
            }
        }
    }
}
