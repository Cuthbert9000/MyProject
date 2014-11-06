package com.leilo.engine.utils;

import com.leilo.engine.room.Room;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Utility class used to generate the game map
 */
public class MapCreator {

    private static MapCreator m_instance = null;

    protected MapCreator() {
        // Exists only to defeat instantiation.
    }
    public static MapCreator getInstance() {
        if(m_instance == null) {
            m_instance = new MapCreator();
        }
        return m_instance;
    }

    public Map<String, Room> createGameMap(Properties roomProperties, WidthLimitedOutputStream output) {
        return createGameMap(roomProperties, null, output);
    }

    public Map<String, Room> createGameMap(Properties roomProperties, Properties mobProperties, WidthLimitedOutputStream output) {
        return createGameMap(roomProperties, mobProperties, null, output);
    }

    public Map<String, Room> createGameMap(Properties roomProperties, Properties mobProperties, Properties itemProperties, WidthLimitedOutputStream output) {
        String roomListString = roomProperties.getProperty("RoomList");
        List<String> roomList = Arrays.asList(roomListString.split(","));
        Map<String, Room> roomMap = new HashMap<String, Room>();
        for(String roomID : roomList) {
            String roomTitle = roomProperties.getProperty(roomID+".title");
            String roomDescription = roomProperties.getProperty(roomID+".description");
            String roomExits = roomProperties.getProperty(roomID + ".exits");
            boolean itemExists = roomProperties.containsKey(roomID + ".items");
            boolean mobExists = roomProperties.containsKey(roomID+".mobs");

            //Ensure that no required fields are missing
            if(roomTitle == null) {
                output.println(roomID + " is missing a title");
                roomTitle = "ERROR ROOM";
            }
            if(roomDescription == null) {
                output.println(roomID + " is missing a description");
                roomDescription = "ERROR ROOM";
            }
            if(roomExits == null) {
                output.println(roomID + " is missing exits. It will not be created.");
                continue;
            }

            List<String> exitList = Arrays.asList(roomExits.split(","));
            Map<String, String> exitMap = new HashMap<String, String>();

            for(String exit : exitList) {
                String[] exitTokens = exit.split(":");
                String exitDirection = exitTokens[0];
                String exitRoomID = exitTokens[1];
                exitMap.put(exitDirection, exitRoomID);
            }

            Room tempRoom = new Room(roomID, roomTitle, roomDescription, exitMap);

            if(itemExists && itemProperties != null) {
                //todo add item stuff
            }
            if(mobExists && mobProperties != null) {
                //todo add mob stuff
            }
            roomMap.put(roomID, tempRoom);
        }
        return roomMap;
    }



}
