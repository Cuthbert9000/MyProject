package com.leilo.game.entities.mobs;

import com.leilo.game.entities.items.Inventory;
import com.leilo.game.entities.items.Item;
import com.leilo.game.map.location.Location;

import java.io.Serializable;

/**
 * The main player character
 */
public class PlayerCharacter implements Serializable{
    private int m_health;
    private int m_strength;
    private int m_experience;
    private Location m_currentLocation;
    private Inventory m_playerInventory;

    public void setHealth(int health) {
        m_health = health;
    }

    public void setStrength(int strength) {
        m_strength = strength;
    }

    public void setExperience(int experience) {
        m_experience = experience;
    }

    public void setCurrentLocation(Location location) {
        m_currentLocation = location;
    }

    public int getHealth() {
        return m_health;
    }

    public int getStrength() {
        return m_strength;
    }

    public int getExperience() {
        return m_experience;
    }

    public Location getCurrentLocation() {
        return m_currentLocation;
    }

    public void getItem(String itemCode, Item item) {
        m_playerInventory.addItemToInventory(itemCode, item);
    }

    public PlayerCharacter(int health, int strength) {
        m_health = health;
        m_strength = strength;
        m_experience = 0;
        m_playerInventory = new Inventory();
    }



}
