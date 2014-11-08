package com.leilo.engine.room;

import com.leilo.engine.items.Inventory;
import com.leilo.engine.items.Item;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * The Room object used for building the game map
 * May Contain:
 *      Items
 *      NPCs
 *      Enemies
 * Must Contain:
 *      Title
 *      Description
 *      Exits
 */
public class Room implements Serializable {
    private String m_roomID;
    private String m_roomTitle;
    private String m_roomDescription;
    private Map<String, String> m_exitMap; //Map<Direction, RoomID>
    private Inventory m_roomInventory;

    /*
    todo:
        NPCs
        Enemies
    */

    public Room(String roomID, String title, String description, Map<String, String> exitMap) {
        this(roomID, title, description, exitMap, null);
    }

    public Room(String roomID, String title, String description, Map<String, String> exitMap, Inventory roomInventory) {
        this(roomID, title, description, exitMap, roomInventory, null, null);
    }

    public Room(String roomID, String title, String description, Map<String, String> exitMap, Inventory roomInventory, List<String> sceneryItemIDList, Map<String, Item> sceneryItemIDToItemNameMap) {
        m_roomID = roomID.trim();
        m_roomTitle = title.trim();
        m_roomDescription = description.trim();
        m_exitMap = exitMap;
        m_roomInventory = roomInventory;
    }

    //Getters
    public String getRoomID() {
        return m_roomID;
    }

    public String getRoomTitle() {
        return m_roomTitle;
    }

    public String getRoomDescription() {
        return m_roomDescription;
    }

    public Map<String, String> getExitMap() {
        return m_exitMap;
    }

    public Inventory getRoomInventory() {
        return m_roomInventory;
    }

    //Setters
    public void setRoomID(String roomID) {
        m_roomID = m_roomID;
    }

    public void setRoomTitle(String roomTitle) {
        m_roomTitle = roomTitle;
    }

    public void setRoomDescription(String roomDescription) {
        m_roomDescription = roomDescription;
    }

    public void setExitMap(Map<String, String> exitMap) {
        m_exitMap = exitMap;
    }

    public void setRoomInventory(Inventory roomInventory) {
        m_roomInventory = roomInventory;
    }

    /**
     * Adds only new exits out of the supplied Map
     */
    public void addExitsToRoom(Map<String, String> exitMap) {
        for(String exitDirection : exitMap.keySet()) {
            if(!m_exitMap.containsKey(exitDirection)) {
                m_exitMap.put(exitDirection, exitMap.get(exitDirection));
            }
        }
    }

    public void addExitToRoom(String exitDirection, String roomID) {
        m_exitMap.put(exitDirection, roomID);
    }
}
