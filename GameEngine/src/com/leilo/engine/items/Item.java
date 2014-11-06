package com.leilo.engine.items;

import java.io.Serializable;

/**
 * Item class
 */
public class Item implements Serializable {

    private String m_itemID;
    private String m_name;
    private String m_description;

    public Item() {}

    public Item(String itemID, String name, String description) {
        m_itemID = itemID;
        m_name = name;
        m_description = description;
    }

    public void setItemID(String itemID) {
        m_itemID = itemID;
    }

    public void setName(String name) {
        m_name = name;
    }

    public void setDescription(String description) {
        m_description = description;
    }

    public String getItemID() {
        return m_itemID;
    }

    public String getName() {
        return m_name;
    }

    public String getDescription() {
        return m_description;
    }
}
