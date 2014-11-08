package com.leilo.engine.items;

import java.io.Serializable;

/**
 * Item class
 */
public class Item implements Serializable {

    private String m_itemID;
    private String m_name;
    private String m_itemDescription;
    private String m_roomDescription;
    private int m_itemType;

    public Item() {}

    public Item(String itemID, String name, String itemDescription, int itemType) {
        this(itemID, name, itemDescription, null, itemType);
    }

    public Item(String itemID, String name, String itemDescription, String roomDescription, int itemType) {
        m_itemID = itemID.trim();
        m_name = name.trim();
        m_itemDescription = itemDescription.trim();
        m_roomDescription = roomDescription == null ? roomDescription : roomDescription.trim();
        m_itemType = itemType;
    }

    public void setItemID(String itemID) {
        m_itemID = itemID;
    }

    public void setName(String name) {
        m_name = name;
    }

    public void setRoomDescription(String roomDescription) {
        m_roomDescription = roomDescription;
    }

    public void setItemDescription(String itemDescription) {
        m_itemDescription = itemDescription;
    }

    public void setItemType(int itemType) {
        m_itemType = itemType;
    }

    public String getItemID() {
        return m_itemID;
    }

    public String getName() {
        return m_name;
    }

    public String getRoomDescription() {
        return m_roomDescription;
    }

    public String getItemDescription() {
        return m_itemDescription;
    }

    public int getItemType() {
        return m_itemType;
    }
}
