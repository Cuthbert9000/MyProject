package com.leilo.game.entities.items;

import java.io.Serializable;

/**
 * Item class
 */
public class Item implements Serializable {
    private String m_name;
    private String m_description;

    public Item() {

    }

    public Item(String name, String description) {
        m_name = name;
        m_description = description;
    }

    public void setName(String name) {
        m_name = name;
    }

    public void setDescription(String description) {
        m_description = description;
    }

    public String getName() {
        return m_name;
    }

    public String getDescription() {
        return m_description;
    }

}
