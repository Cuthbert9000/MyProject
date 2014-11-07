package com.leilo.engine.world;

import com.leilo.engine.room.Room;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Creates the game world
 */
public class GameWorld implements Serializable {

    private Map<String, Room> m_gameRooms;
    private Room m_currentRoom = null;
    private int m_gameStatus; //Status integer used to indicate the current status of the game after a command has been entered and processed
    //todo add Player

    /**
     * Constructor
     */
    public GameWorld() {
        m_gameRooms = new HashMap<String, Room>();
        m_gameRooms.put("CurrentRoom", m_currentRoom);
        m_gameStatus = 0;
    }

    //Getters
    public Map<String, Room> getGameRooms() {
        return m_gameRooms;
    }

    public Room getCurrentRoom() {
        return m_currentRoom;
    }

    public int getGameStatus() {
        return m_gameStatus;
    }

    //Setters
    public void setGameRooms(Map<String, Room> gameRooms) {
        m_gameRooms = gameRooms;
    }

    public void setCurrentRoom(Room room) {
        m_currentRoom = room;
    }

    public void setGameStatus(int gameStatus) {
        m_gameStatus = gameStatus;
    }

    /**
     * Adds only new rooms out of the supplied Map
     */
    public void addRoomsToGame(Map<String, Room> gameRooms) {
        for(String roomID : gameRooms.keySet()) {
            if(!m_gameRooms.containsKey(roomID)) {
                m_gameRooms.put(roomID, gameRooms.get(roomID));
            }
        }
    }

    /**
     * Adds the specified room to the Game's rooms
     * Overwriting old rooms is possible
     */
    public void addRoomToGame(String roomID, Room room) {
        m_gameRooms.put(roomID, room);
    }

    public Room getRoomByID(String roomID) {
        return m_gameRooms.get(roomID);
    }


}
