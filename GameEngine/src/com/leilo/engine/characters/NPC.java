package com.leilo.engine.characters;

import com.leilo.engine.items.Equipment;
import com.leilo.engine.items.Inventory;
import com.leilo.engine.items.Item;
import com.leilo.engine.room.Room;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The main player character
 */
public class NPC implements Serializable {
    private String m_name;
    private int m_health;
    private Room m_currentRoom;
    private Inventory m_inventory;

    public void setName(String name) {
        m_name = name;
    }

    public void setHealth(int health) {
        m_health = health;
    }

    public void setCurrentRoom(Room room) {
        m_currentRoom = room;
    }

    public void setInventory(Inventory inventory) {
        m_inventory = inventory;
    }

    public String getName() {
        return m_name;
    }

    public int getHealth() {
        return m_health;
    }

    public Room getCurrentRoom() {
        return m_currentRoom;
    }

    public Inventory getInventory() {
        return m_inventory;
    }

    public NPC() {
        this(null);
    }

    public NPC(Room currentRoom) {
        this("NPC", 25, currentRoom);
    }

    public NPC(String name, int health, Room currentRoom) {
        this(name, health, currentRoom, new Inventory(20));
    }

    public NPC(String name, int health, Room currentRoom, Inventory inventory) {
        m_name = name;
        m_health = health;
        m_currentRoom = currentRoom;
        m_inventory = inventory;
    }

    /**
     * Attempts to add the item to the player's inventory
     * @return true == success | false == failure
     */
    public boolean addItemToInventory(Item item, int itemCount) {
        return m_inventory.addItemToInventoryByCount(item, itemCount);
    }

    public void addItemsToInventory(List<Item> items) {
        m_inventory.addItemsToInventory(items);
    }
}