package com.leilo.engine.utils;

import com.leilo.engine.items.Inventory;
import com.leilo.engine.items.Item;
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
        List<String> roomList = Arrays.asList(roomListString.split("\\|"));
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

            List<String> exitList = Arrays.asList(roomExits.split("\\|"));
            Map<String, String> exitMap = new HashMap<String, String>();

            for(String exit : exitList) {
                String[] exitTokens = exit.split(":");
                String exitDirection = exitTokens[0];
                String exitRoomID = exitTokens[1];
                exitMap.put(exitDirection, exitRoomID);
            }

            Inventory roomInventory = new Inventory(50);
            if(itemExists && itemProperties != null) {
                Map<String, String> tokenNumberToItemRoomDescriptionMap = new HashMap<String, String>();
                String[] roomItemList = roomProperties.getProperty(roomID + ".items").split("\\|");
                List<Item> itemList = new ArrayList<Item>();
                for(String itemProp : roomItemList) {
                    String itemID;
                    String tokenNumber = "-1";
                    if(itemProp.contains("{")) {
                        int bracketStartIndex = itemProp.indexOf("{");
                        int bracketEndIndex = itemProp.indexOf("}");
                        itemID = itemProp.substring(0, bracketStartIndex);
                        tokenNumber = itemProp.substring(bracketStartIndex + 1, bracketEndIndex);
                    } else {
                        itemID = itemProp;
                    }
                    Item tempItem = ItemCreator.getInstance().createItem(itemID, itemProperties);
                    tokenNumberToItemRoomDescriptionMap.put(tokenNumber, tempItem.getRoomDescription());
                    itemList.add(tempItem);
                }
                roomInventory.addItemsToInventory(itemList);
                roomDescription = replaceDescriptionTokens(roomDescription, tokenNumberToItemRoomDescriptionMap);
            }
            Room tempRoom = new Room(roomID, roomTitle, roomDescription, exitMap);
            tempRoom.setRoomInventory(roomInventory);

            if(mobExists && mobProperties != null) {
                //todo add mob stuff
            }
            roomMap.put(roomID, tempRoom);
        }
        return roomMap;
    }

    private String replaceDescriptionTokens(String roomDescription, Map<String, String> tokenNumberToItemRoomDescriptionMap) {
        String returnString = roomDescription;
        while(returnString.contains("{")) {
            int bracketStartIndex = returnString.indexOf("{");
            int bracketEndIndex = returnString.indexOf("}");
            String tokenNumber = returnString.substring(bracketStartIndex + 1, bracketEndIndex);
            String tokenReplacement = tokenNumberToItemRoomDescriptionMap.get(tokenNumber);
            returnString = returnString.replace("{" + tokenNumber + "}", tokenReplacement);
        }
        return returnString;
    }
}