package com.leilo.engine.items;

import com.leilo.engine.items.Equipment;
import com.leilo.engine.items.Item;

import java.io.Serializable;
import java.util.*;

/**
 * The Inventory that each player will have, contains all of their Items
 */
public class Inventory implements Serializable {

    private Map<String, Item> m_itemMap; //Map<ItemID, Item>
    private Map<String, Integer> m_itemCountMap; //Map<ItemID, # in Inventory>
    private Map<String, String> m_itemNameMap; //Map<ItemID, Item Name>
    private List<String> m_consumableItemList; //Map<Item Type, List<ItemID>>
    private Map<Integer, List<String>> m_equipmentSlotToItemIDMap; //Map<Item Type, List<ItemID>>
    private List<String> m_itemList; //All ItemIDs in the Inventory
    private int m_inventorySize;

    public Inventory() {
        this(25);
    }

    public Inventory(int initialSize) {
        m_itemMap = new HashMap<String, Item>();
        m_itemCountMap = new HashMap<String, Integer>();
        m_itemNameMap = new HashMap<String, String>();
        m_consumableItemList = new ArrayList<String>();
        m_equipmentSlotToItemIDMap = createEquipmentSlotToItemIDtMap();
        m_itemList = new ArrayList<String>();
        m_inventorySize = initialSize;
    }

    private Map<Integer, List<String>> createEquipmentSlotToItemIDtMap() {
        Map<Integer, List<String>> map = new HashMap<Integer, List<String>>();
        map.put(0, new ArrayList<String>()); //Weapons
        map.put(1, new ArrayList<String>()); //Head
        map.put(2, new ArrayList<String>()); //Body
        map.put(3, new ArrayList<String>()); //Hands
        map.put(4, new ArrayList<String>()); //Legs
        map.put(5, new ArrayList<String>()); //Feet
        return map;
    }

    public void setInventorySize(int newSize) {
        m_inventorySize = newSize;
    }

    public int getInventorySize() {
        return m_inventorySize;
    }

    public int getInventoryFreeSlots() {
        return m_itemList.size();
    }

    public List<String> getItemList() {
        return m_itemList;
    }

    public Map<String, String> getItemNameMap() {
        return m_itemNameMap;
    }

    public List<String> getConsumableItemList() {
        return m_consumableItemList;
    }

    public Map<Integer, List<String>> getEquipmentSlotToItemIDMap() {
        return m_equipmentSlotToItemIDMap;
    }


    public String getItemNameByItemID(String itemID) {
        return m_itemNameMap.get(itemID);
    }

    public Integer getItemCountByItemID(String itemID) {
        return m_itemCountMap.get(itemID);
    }

    public boolean isEmpty() {
        return m_itemList.isEmpty();
    }

    public void addItemsToInventory(List<Item> items) {
        for(Item item : items) {
            addItemToInventory(item, 1);
        }
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
                m_itemList.add(itemID);
                m_itemCountMap.put(itemID, itemCount);
                m_itemNameMap.put(itemID, item.getName());
                int itemType = item.getItemType();
                List<String> itemList;
                switch (itemType) {
                    case 0:
                        if(!m_consumableItemList.contains(itemID)) {
                            m_consumableItemList.add(itemID);
                            Collections.sort(m_consumableItemList, new Comparator<String>() {
                                @Override
                                public int compare(String itemID1, String itemID2) {
                                    String itemName1 = m_itemNameMap.get(itemID1);
                                    String itemName2 = m_itemNameMap.get(itemID2);
                                    return itemName1.compareTo(itemName2);
                                }
                            });
                        }
                        break;
                    case 1:
                        Equipment tempEquipment = (Equipment) item;
                        int equipmentSlot = tempEquipment.getEquipmentSlot();
                        itemList = m_equipmentSlotToItemIDMap.get(equipmentSlot);
                        itemList.add(itemID);
                        Collections.sort(itemList, new Comparator<String>() {
                            @Override
                            public int compare(String itemID1, String itemID2) {
                                String itemName1 = m_itemNameMap.get(itemID1);
                                String itemName2 = m_itemNameMap.get(itemID2);
                                return itemName1.compareTo(itemName2);
                            }
                        });
                        m_equipmentSlotToItemIDMap.put(equipmentSlot, itemList);
                        break;
                }
                return true;
            } else {
                //Inventory full, return false
                return false;
            }
        }
    }

    public boolean doesInventoryContainItem(String itemID) {
        return m_itemList.contains(itemID);
    }

    public boolean canItemBeTakenFromInventory(String itemID, int itemCount) {
        if(doesInventoryContainItem(itemID)) {
            int existingCount = m_itemCountMap.get(itemID);
            return itemCount <= existingCount;
        }
        return false;
    }

    public Item getItemFromInventory(String itemID) {
        return getItemsFromInventory(itemID, 1);
    }

    public Item getItemsFromInventory(String itemID, int itemCount) {
        if (canItemBeTakenFromInventory(itemID, itemCount)) {
            int existingCount = m_itemCountMap.get(itemID);
            Item tempItem = m_itemMap.get(itemID);
            if(existingCount-itemCount == 0) {
                completelyRemoveItemFromInventory(itemID);
            } else {
                partiallyRemoveItemFromInventory(itemID, itemCount);
            }
            return tempItem;
        }
        return null;
    }

    public void completelyRemoveItemFromInventory(String itemID) {
        Item tempItem = m_itemMap.get(itemID);
        m_itemList.remove(itemID);
        m_itemMap.remove(itemID);
        m_itemCountMap.remove(itemID);
        m_itemNameMap.remove(itemID);
        int itemType = tempItem.getItemType();
        List<String> itemList;
        switch (itemType) {
            case 0:
                m_consumableItemList.remove(itemID);
                break;
            case 1:
                Equipment tempEquipment = (Equipment) tempItem;
                int equipmentSlot = tempEquipment.getEquipmentSlot();
                itemList = m_equipmentSlotToItemIDMap.get(equipmentSlot);
                itemList.remove(itemID);
                m_equipmentSlotToItemIDMap.put(equipmentSlot, itemList);
                break;
        }
    }

    public void partiallyRemoveItemFromInventory(String itemID, int itemCount) {
        int existingCount = m_itemCountMap.get(itemID);
        int newCount = existingCount - itemCount;
        m_itemCountMap.put(itemID, newCount);
    }
}
