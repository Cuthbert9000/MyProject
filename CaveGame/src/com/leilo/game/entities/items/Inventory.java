package com.leilo.game.entities.items;

import java.io.InputStream;
import java.io.Serializable;
import java.util.*;

public class Inventory implements Serializable {

    private Map<String, Item> m_itemMap; //This map will contain ItemCode --> Item Object
    private Map<String, String> m_capsItemCodeToItemCodeMap;
    private Map<String, String> m_itemCodeToNameMap;

    public Inventory() {
        m_itemMap = new HashMap<String, Item>();
        m_capsItemCodeToItemCodeMap = new HashMap<String, String>();
        m_itemCodeToNameMap = new HashMap<String, String>();
    }

    public Inventory(Map<String, Item> itemMap) {
        m_itemMap = itemMap;
    }

    public void setItems(Map<String, Item> itemMap) {
        m_itemMap = itemMap;
    }

    public Map<String, Item> getItemMap() {
        return m_itemMap;
    }

    public void addItemToInventory(String itemCode, Item item) {
        //todo ADD IN A NEW MAP WHICH IS ALLCAPSITEMCODE --> ITEM CODE
        //First, we need to check if the Inventory already contains the item
        if(m_itemMap.containsKey(itemCode)) {
            //Get the number that already exist
            String itemName = m_itemCodeToNameMap.get(itemCode);
            if(itemName.contains("[")) {
                int startIndex = itemName.indexOf("[");
                int endIndex = itemName.indexOf("]");
                String itemCountString = itemName.substring(startIndex, endIndex);
                String newItemName = itemName.substring(endIndex).trim();
                int itemCount = Integer.parseInt(itemCountString);
                itemCount++;
                newItemName = "[ " + itemCount + " ] " + newItemName;
                m_itemCodeToNameMap.put(itemCode, newItemName);
            } else {
                String newItemName = "[ 2 ] " + itemName;
                m_itemCodeToNameMap.put(itemCode, newItemName);
            }
        } else {
            //todo add
        }
    }

    public void removeAllOfOneItem(String itemCode) {
        m_itemMap.remove(itemCode);
    }

     //todo need to modify
//    public void removeItemFromInventory(String itemCode) {
//        m_itemMap.remove(itemCode);
//    }


    //todo modify
    public Item getItemFromInventory(String itemCode) {
        return m_itemMap.get(itemCode);
    }

    public List<String> getItemCodesAsList() {
        List<String> itemCodeList = new ArrayList<String>(m_itemMap.size());
        for(String itemCode : m_itemMap.keySet()) {
            itemCodeList.add(itemCode);
        }
        return itemCodeList;
    }
}
