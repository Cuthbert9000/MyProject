package com.leilo.engine.items;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Consumables are a form of Item which can be used by the player
 */
public class Consumable extends Item implements Serializable {
    //Need some kind of stat or whatnot

    public Consumable(String itemID, String name, String itemDescription) {
        this(itemID, name, itemDescription, null);
    }

    public Consumable(String itemID, String name, String itemDescription, String roomDescriptions) {
        super(itemID, name, itemDescription, roomDescriptions, 0);
    }
}
