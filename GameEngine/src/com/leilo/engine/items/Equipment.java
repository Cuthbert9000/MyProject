package com.leilo.engine.items;

import java.io.Serializable;

/**
 * Equipment is a form of an Item which can be equipped by the player
 */
public class Equipment extends Item implements Serializable {
    private String m_itemID;
    private String m_name;
    private String m_description;
    private String m_intendedSlot;

    public Equipment(String itemID, String name, String description, String intendedSlot) {
        super(itemID, name, description);
        m_intendedSlot = intendedSlot;
    }
}
