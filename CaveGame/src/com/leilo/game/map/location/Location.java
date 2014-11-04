package com.leilo.game.map.location;

import com.leilo.game.entities.items.Inventory;
import com.leilo.game.entities.items.Item;
import com.leilo.game.entities.mobs.Mob;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The room class -- essentially each individual "square" that a user can enter and explore
 */
public class Location implements Serializable {
    private String m_roomTitle;
    private String m_roomDescription;
    private Map<String, String> m_exits;
    private Map<String, String> m_exitNameMap;

    //Each location will have its own Inventory
    private Inventory m_locationInventory;

    //List of Mobs in Location
    private List<Mob> m_mobList;

    public Location() {
        m_roomTitle = "";
        m_roomDescription = "";
        m_exits = new HashMap<String, String>();
        m_locationInventory = new Inventory();
        m_mobList = new ArrayList<Mob>();
    }

    public void setRoomTitle(String title) {
        m_roomTitle = title;
    }

    public void setRoomDescription(String description) {
        m_roomDescription = description;
    }

    public void setExits(Map<String, String> exits) {
        m_exits = exits;
    }

    public void setExitsName(Map<String, String> exitNames) {
        m_exitNameMap = exitNames;
    }

    public void setInventory(Inventory inventory) {
        m_locationInventory = inventory;
    }

    public void addItemToLocation(String itemCode, Item item) {
        m_locationInventory.addItemToInventory(itemCode, item);
    }

    public void removeItemFromLocation() {
        //remove item etc.
    }

    public void setMobList(List<Mob> mobList) {
        m_mobList = mobList;
    }

    public void addMobToList(Mob mob) {
        m_mobList.add(mob);
    }

    public void removeMobFromList(Mob mob) {
        if(m_mobList.contains(mob)) {
            m_mobList.remove(mob);
        }
    }

    public String getRoomTitle() {
        return m_roomTitle;
    }

    public String getRoomDescription() {
        return m_roomDescription;
    }

    public Inventory getLocationInventory() {
        return m_locationInventory;
    }

    public List<Mob> getRoomMobs() {
        return m_mobList;
    }

    public Map<String, String> getExits() {
        return m_exits;
    }

    public String getExitAtDirection(String direction) {
        return m_exits.get(direction);
    }

    public boolean isValidExitDirection(String direction) {
        return m_exits.containsKey(direction);
    }

    public String getExitPrintString() {
        String exitString = "";
        if(m_exits.containsKey("N")) {
            exitString = exitString + "North: " + m_exitNameMap.get("North") + "|";
        }
        if(m_exits.containsKey("E")) {
            exitString = exitString + "East: " + m_exitNameMap.get("East") + "|";
        }
        if(m_exits.containsKey("S")) {
            exitString = exitString + "South: " + m_exitNameMap.get("South") + "|";
        }
        if(m_exits.containsKey("W")) {
            exitString = exitString + "West: " + m_exitNameMap.get("West") + "|";
        }
        return exitString;
    }

    public boolean containsMob() {
        return !m_mobList.isEmpty();
    }
}
