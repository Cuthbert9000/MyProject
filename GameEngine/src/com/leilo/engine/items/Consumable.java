package com.leilo.engine.items;

import java.io.Serializable;

/**
 * Consumables are a form of Item which can be used by the player
 */
public class Consumable extends Item implements Serializable {

    private String m_itemID;
    private String m_name;
    private String m_description;
    //Need some kind of stat or whatnot

    public Consumable(String itemID, String name, String description) {
        super(itemID, name, description);
    }

}
