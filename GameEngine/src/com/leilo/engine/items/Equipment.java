package com.leilo.engine.items;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Equipment is a form of an Item which can be equipped by the player
 */
public class Equipment extends Item implements Serializable {
    private int m_equipmentSlot;
    /*
    Intended Slot Mapping:
        0 = Weapons
        1 = Head
        2 = Body
        3 = Hands
        4 = Legs
        5 = Feet
     */

    public Equipment(String itemID, String name, String itemDescription, int equipmentSlot) {
        this(itemID, name, itemDescription, null, equipmentSlot);
    }

    public Equipment(String itemID, String name, String itemDescription, String roomDescription, int equipmentSlot) {
        super(itemID, name, itemDescription, roomDescription, 1);
        m_equipmentSlot = equipmentSlot;
    }

    public int getEquipmentSlot() {
        return m_equipmentSlot;
    }

    public void setEquipmentSlot(int equipmentSlot) {
        m_equipmentSlot = equipmentSlot;
    }
}
