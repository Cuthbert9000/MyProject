package com.leilo.engine.world;

import com.leilo.engine.characters.PlayerCharacter;
import com.leilo.engine.room.Room;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Creates the game world
 */
public class GameWorld implements Serializable {

    private Map<String, Room> m_gameRooms;
    private Room m_currentRoom;
    private boolean m_activeGame = true;
    private PlayerCharacter m_player;

    /**
     * Constructor
     */
    public GameWorld() {
        this(new HashMap<String, Room>(), null);
    }

    public GameWorld(Map<String, Room> gameRooms, Room currentRoom) {
        this(gameRooms, currentRoom, new PlayerCharacter(currentRoom));
    }

    public GameWorld(Map<String, Room> gameRooms, Room currentRoom, PlayerCharacter player) {
        m_gameRooms = gameRooms;
        m_currentRoom = currentRoom;
        m_player = player;
    }


    //Getters
    public Map<String, Room> getGameRooms() {
        return m_gameRooms;
    }

    public Room getCurrentRoom() {
        return m_currentRoom;
    }

    public PlayerCharacter getPlayer() {
        return m_player;
    }

    public boolean isGameActive() {
        return m_activeGame;
    }

    //Setters
    public void setGameRooms(Map<String, Room> gameRooms) {
        m_gameRooms = gameRooms;
    }

    public void setCurrentRoom(Room room) {
        m_currentRoom = room;
    }

    public void setGameStatus(boolean gameStatus) {
        m_activeGame = gameStatus;
    }

    public void setPlayer(PlayerCharacter player) {
        m_player = player;
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

    public void updateCurrentRoom(String newRoomID) {
        Room newRoom = m_gameRooms.get(newRoomID);
        m_player.setCurrentRoom(newRoom);
        m_currentRoom = newRoom;
    }
}