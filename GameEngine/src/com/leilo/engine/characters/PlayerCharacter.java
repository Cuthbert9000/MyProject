package com.leilo.engine.characters;

import com.leilo.engine.items.Inventory;
import com.leilo.engine.items.Item;

import java.io.Serializable;

/**
 * The main player character
 */
public class PlayerCharacter implements Serializable {
    private String m_playerName;
    private int m_health;
    private String m_currentRoomID;
    private Inventory m_playerInventory;

    public void setName(String name) {
        m_playerName = name;
    }

    public void setHealth(int health) {
        m_health = health;
    }

    public void setCurrentRoomID(String roomID) {
        m_currentRoomID = roomID;
    }

    public String getName() {
        return m_playerName;
    }

    public int getHealth() {
        return m_health;
    }

    public String getCurrentRoomID() {
        return m_currentRoomID;
    }

    public PlayerCharacter(String currentRoomID) {
        this("Player", 25, currentRoomID);
    }

    public PlayerCharacter(String name, int health, String currentRoomID) {
        this(name, health, currentRoomID, new Inventory(20));
    }

    public PlayerCharacter(String name, int health, String currentRoomID, Inventory inventory) {
        m_playerName = name;
        m_health = health;
        m_currentRoomID = currentRoomID;
        m_playerInventory = inventory;
    }

    /**
     * Attempts to add the item to the player's inventory
     * @return true == success | false == failure
     */
    public boolean addItemToInventory(Item item, int itemCount) {
        return m_playerInventory.addItemToInventory(item, itemCount);
    }
}
