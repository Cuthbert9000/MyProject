package com.leilo.engine.utils;

import com.leilo.engine.room.Room;
import com.leilo.engine.world.GameWorld;

import java.util.Map;

/**
 * Utility class used for printing the game to the console
 */
public class PrintUtils {
    
    private static PrintUtils m_instance = null;

    protected PrintUtils() {
        // Exists only to defeat instantiation.
    }
    public static PrintUtils getInstance() {
        if(m_instance == null) {
            m_instance = new PrintUtils();
        }
        return m_instance;
    }

    /**
     * Returns a | delimited string of the current game location
     */
    public String printCurrentLocation(Room currentRoom, GameWorld gameWorld) {
        StringBuilder currentLocationStringBuilder = new StringBuilder();
        //Append the room title
        currentLocationStringBuilder.append("You are in " + currentRoom.getRoomTitle() + "|");
        //Append the room description
        currentLocationStringBuilder.append(currentRoom.getRoomDescription() + "|");
        //todo append mobs
        //todo append items
        //Append
        currentLocationStringBuilder.append("||Exits:");
        Map<String, String> currentRoomExitMap = currentRoom.getExitMap();
        String exitRoomID;
        if(currentRoomExitMap.containsKey("North")) {
            exitRoomID = currentRoomExitMap.get("North");
            currentLocationStringBuilder.append("North: " + gameWorld.getRoomByID(exitRoomID).getRoomTitle() + "|");
        }
        if(currentRoomExitMap.containsKey("East")) {
            exitRoomID = currentRoomExitMap.get("East");
            currentLocationStringBuilder.append("East: " + gameWorld.getRoomByID(exitRoomID).getRoomTitle() + "|");
        }
        if(currentRoomExitMap.containsKey("South")) {
            exitRoomID = currentRoomExitMap.get("South");
            currentLocationStringBuilder.append("South: " + gameWorld.getRoomByID(exitRoomID).getRoomTitle() + "|");
        }
        if(currentRoomExitMap.containsKey("West")) {
            exitRoomID = currentRoomExitMap.get("West");
            currentLocationStringBuilder.append("West: " + gameWorld.getRoomByID(exitRoomID).getRoomTitle() + "|");
        }
        return currentLocationStringBuilder.toString();
    }
}