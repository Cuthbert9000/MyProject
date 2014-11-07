package com.leilo.engine.utils;

import com.leilo.engine.items.Inventory;
import com.leilo.engine.room.Room;
import com.leilo.engine.world.GameWorld;

import java.util.List;
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
    public void printCurrentRoom(Room currentRoom, GameWorld gameWorld, WidthLimitedOutputStream outputStream) {
        //Print the room title
        outputStream.println("You are in " + currentRoom.getRoomTitle());
        //Print the room description
        outputStream.println(currentRoom.getRoomDescription());
        //todo print mobs
        //todo print items
        //Print the room exits
        outputStream.println("Exits:");
        Map<String, String> currentRoomExitMap = currentRoom.getExitMap();
        String exitRoomID;
        if(currentRoomExitMap.containsKey("North")) {
            exitRoomID = currentRoomExitMap.get("North");
            outputStream.println("North: " + gameWorld.getRoomByID(exitRoomID).getRoomTitle());
        }
        if(currentRoomExitMap.containsKey("East")) {
            exitRoomID = currentRoomExitMap.get("East");
            outputStream.println("East: " + gameWorld.getRoomByID(exitRoomID).getRoomTitle());
        }
        if(currentRoomExitMap.containsKey("South")) {
            exitRoomID = currentRoomExitMap.get("South");
            outputStream.println("South: " + gameWorld.getRoomByID(exitRoomID).getRoomTitle());
        }
        if(currentRoomExitMap.containsKey("West")) {
            exitRoomID = currentRoomExitMap.get("West");
            outputStream.println("West: " + gameWorld.getRoomByID(exitRoomID).getRoomTitle());
        }
    }

    public void printInventory(final Inventory inventory, WidthLimitedOutputStream outputStream) {
        outputStream.updateWidth(150);
        outputStream.println("************** Your Inventory **************");
        if(inventory.isEmpty()) {
            outputStream.println("It's empty!");
        } else {
            List<String> consumableItemIDList = inventory.getConsumableItemList();
            if(!consumableItemIDList.isEmpty()) {
                outputStream.println("****Consumables:");
                int itemCounter = 1;
                for(String itemID : consumableItemIDList) {
                    outputStream.println("*  " + itemCounter++ +".  " +  inventory.getItemNameByItemID(itemID) + " x " + inventory.getItemCountByItemID(itemID));
                }
                outputStream.println("*");
            }
            Map<Integer, List<String>> equipmentSlotToItemIDMap = inventory.getEquipmentSlotToItemIDMap();
            boolean equipmentPrinted = false;
            for(int i=0; i<=5; i++) {
                List<String> equipmentItemIDList = equipmentSlotToItemIDMap.get(i);
                if(!equipmentItemIDList.isEmpty()) {
                    if(!equipmentPrinted) {
                        outputStream.println("****Equipment:");
                        equipmentPrinted = true;
                    }
                    switch (i) {
                        case 0:
                            outputStream.println("***Weapons:");
                            break;
                        case 1:
                            outputStream.println("***Head:");
                            break;
                        case 2:
                            outputStream.println("***Body:");
                            break;
                        case 3:
                            outputStream.println("***Hands:");
                            break;
                        case 4:
                            outputStream.println("***Legs:");
                            break;
                        case 5:
                            outputStream.println("***Feet:");
                            break;
                    }
                    int itemCounter = 1;
                    for(String itemID : equipmentItemIDList) {
                        outputStream.println("*  " + itemCounter++ +".  " +  inventory.getItemNameByItemID(itemID) + " x " + inventory.getItemCountByItemID(itemID));
                    }
                }
            }
            outputStream.println("*");
            outputStream.println("********************************************");
        }
    }
}