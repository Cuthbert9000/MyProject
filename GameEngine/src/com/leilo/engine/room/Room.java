package com.leilo.engine.room;

import java.io.Serializable;
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

    /*
    todo:
        items, including an item count (See Inventory.java for an example of item counts)
        NPCs
        Enemies
    */

    public Room(String roomID, String title, String description, Map<String, String> exitMap) {
        m_roomID = roomID;
        m_roomTitle = title;
        m_roomDescription = description;
        m_exitMap = exitMap;
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
