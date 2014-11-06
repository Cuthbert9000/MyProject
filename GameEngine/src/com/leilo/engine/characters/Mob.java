package com.leilo.engine.characters;

import com.leilo.engine.items.Item;

import java.io.Serializable;
import java.util.Map;

/**
 * The main player character
 */
public class Mob implements Serializable {
    private String m_playerName;
    private int m_health;
    private String m_currentRoomID;
    private Map<String, Item> m_mobItemMap;

    public void setName(String name) {
        m_playerName = name;
    }

    public void setHealth(int health) {
        m_health = health;
    }

    public void setCurrentRoomID(String roomID) {
        m_currentRoomID = roomID;
    }

    public void setMobItemMap(Map<String, Item> mobItemMap) {
        m_mobItemMap = mobItemMap;
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

    public Map<String, Item> getMobItemMap() {
        return m_mobItemMap;
    }

    public Mob(String currentRoomID) {
        this("Mob", 10, currentRoomID);
    }

    public Mob(String name, int health, String currentRoomID) {
        this(name, health, currentRoomID, null);
    }

    public Mob(String name, int health, String currentRoomID, Map<String, Item> mobItemMap) {
        m_playerName = name;
        m_health = health;
        m_currentRoomID = currentRoomID;
        m_mobItemMap = mobItemMap;
    }

}
