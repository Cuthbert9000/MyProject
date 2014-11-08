package com.leilo.engine.utils;

import com.leilo.engine.items.Equipment;
import com.leilo.engine.items.Inventory;
import com.leilo.engine.items.Item;
import com.leilo.engine.room.Room;
import com.leilo.engine.world.GameWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Utility class used for printing the game to the console
 */
public class PrintUtils {
    
    private static PrintUtils m_instance = null;

    //Inventory String Constants
    private final String EMPTY = "*  It's empty!";
    private final String HEADER = " Your Inventory ";
    private final String CONSUMABLES = "****Consumables:";
    private final String EQUIPMENT = "****Equipment:";
    private final String WEAPONS = "***Weapons:";
    private final String HEAD = "***Head:";
    private final String BODY = "***Body:";
    private final String HANDS = "***Hands:";
    private final String LEGS = "***Legs:";
    private final String FEET = "***Feet:";
    //Direction Constants
    private final List<String> DIRECTIONS = new ArrayList<String>() {{
        add(0, "north");
        add(1, "east");
        add(2, "south");
        add(3, "west");
    }};

    protected PrintUtils() {
        // Exists only to defeat instantiation.
    }
    public static PrintUtils getInstance() {
        if(m_instance == null) {
            m_instance = new PrintUtils();
        }
        return m_instance;
    }

    public String toMixedCase(String str) {
        if(str.isEmpty()) {
            return str;
        }
        if(str.length() == 1) {
            return str.toUpperCase();
        }
        String firstLetter = str.substring(0, 1);
        firstLetter = firstLetter.toUpperCase();
        return firstLetter + str.substring(1);
    }

    private String addItemsToDescription(String description, Room currentRoom) {
        Inventory roomInventory = currentRoom.getRoomInventory();
        List<String> itemIDList = roomInventory.getItemList(); //Used to keep track of which items have been printed
        Map<String, String> tokenNumberToItemIDMap = currentRoom.getTokenNumberToItemIDMap();
        String returnString = description;
        while(returnString.contains("{")) {
            int bracketStartIndex = returnString.indexOf("{");
            int bracketEndIndex = returnString.indexOf("}");
            String tokenNumber = returnString.substring(bracketStartIndex + 1, bracketEndIndex);
            String tokenReplacement = "";
            if(tokenNumberToItemIDMap == null) {
                returnString = returnString.replace("{" + tokenNumber + "}", tokenReplacement);
                continue;
            }
            String itemID = tokenNumberToItemIDMap.get(tokenNumber);
            if(roomInventory.doesInventoryContainItem(itemID)) {
                Item item = roomInventory.getItemCopy(itemID);
                tokenReplacement = item.getRoomDescriptionsMap().get(currentRoom.getRoomID()) + " ";
                itemIDList.remove(itemID);
            }
            returnString = returnString.replace("{" + tokenNumber + "}", tokenReplacement);
        }
        //Add general item descriptions for other items located in the room
        for(String itemID : itemIDList) {
            String itemDescription = "There is a " + roomInventory.getItemCopy(itemID).getName() + " lying on the ground. ";
            returnString = returnString + itemDescription;
        }
        return returnString;
    }

    /**
     * Returns a | delimited string of the current game location
     */
    public void printCurrentRoom(Room currentRoom, GameWorld gameWorld, WidthLimitedOutputStream outputStream) {
        //Print the room title
        outputStream.println("You are in " + currentRoom.getRoomTitle());
        //Print the room description
        String description = addItemsToDescription(currentRoom.getRoomDescription(), currentRoom);
        outputStream.println(description);
        //todo print mobs
        //todo print items
        //Print the room exits
        outputStream.println("Exits:");
        Map<String, String> currentRoomExitMap = currentRoom.getExitMap();
        String exitRoomID;
        for(String direction : DIRECTIONS) {
            if(currentRoomExitMap.containsKey(direction)) {
                exitRoomID = currentRoomExitMap.get(direction);
                outputStream.println(toMixedCase(direction) + ": " + gameWorld.getRoomByID(exitRoomID).getRoomTitle());
            }
        }
    }

    private String createInventoryHeader(int boxWidth) {
        String headerString = "";
        for(int i=0; i<boxWidth; i++) {
            headerString = headerString + "*";
            if(i+1 == (boxWidth / 2)) {
                headerString = headerString + HEADER;
            }
        }
        return headerString;
    }

    private String createInventoryFooter(int boxWidth) {
        String footerString = "";
        for(int i=0; i<boxWidth; i++) {
            footerString = footerString + "*";
        }
        return footerString;
    }

    private String createWhiteSpace(int targetSpace) {
        String whiteSpace = "";
        for(int i=0; i<targetSpace; i++) {
            whiteSpace = whiteSpace + " &nbsp; ";
        }
        return whiteSpace;
    }

    private String createBlankInventoryLine(int boxWidth) {
        String blankLine = "*";
        for(int i=0; i<boxWidth-3; i++) {
            blankLine = blankLine + " &nbsp; ";
        }
        return blankLine + "*";
    }


    public void printInventory(Inventory inventory, WidthLimitedOutputStream outputStream) {
        WidthLimitedOutputStream inventoryStream = outputStream;
        int boxWidth = inventory.getMaxNameLength();
        inventoryStream.updateWidth(boxWidth + 80);
        String header = createInventoryHeader(boxWidth);
        boxWidth = header.length();
        inventoryStream.println(header);
        inventoryStream.println(createBlankInventoryLine(boxWidth));
        if(inventory.isEmpty()) {
            inventoryStream.println(EMPTY + createWhiteSpace(boxWidth-EMPTY.length()-1) + "*");
            inventoryStream.println(createBlankInventoryLine(boxWidth));
        } else {
            printConsumableItems(inventory, inventoryStream, boxWidth);
            printEquipmentItems(inventory, inventoryStream, boxWidth);
        }
        inventoryStream.println(createInventoryFooter(boxWidth));
    }

    public void printConsumableItems(Inventory inventory, WidthLimitedOutputStream outputStream, int boxWidth) {
        List<String> consumableItemIDList = inventory.getConsumableItemList();
        if(!consumableItemIDList.isEmpty()) {
            outputStream.println(CONSUMABLES + createWhiteSpace(boxWidth-CONSUMABLES.length()-2) + "*");
            int itemCounter = 1;
            for(String itemID : consumableItemIDList) {
                String itemLine = "*  " + itemCounter++ +".  " +  inventory.getItemNameByItemID(itemID) + " x " + inventory.getItemCountByItemID(itemID);
                outputStream.println(itemLine + createWhiteSpace(boxWidth-itemLine.length()) + "*");
            }
            outputStream.println(createBlankInventoryLine(boxWidth));
        }
    }

    public void printEquipmentItems(Inventory inventory, WidthLimitedOutputStream outputStream, int boxWidth) {
        Map<Integer, List<String>> equipmentSlotToItemIDMap = inventory.getEquipmentSlotToItemIDMap();
        boolean equipmentPrinted = false;
        for (int i = 0; i <= 5; i++) {
            List<String> equipmentItemIDList = equipmentSlotToItemIDMap.get(i);
            if (!equipmentItemIDList.isEmpty()) {
                if (!equipmentPrinted) {
                    outputStream.println(EQUIPMENT + createWhiteSpace(boxWidth-EQUIPMENT.length()-2) + "*");
                    equipmentPrinted = true;
                }
                switch (i) {
                    case 0:
                        outputStream.println(WEAPONS + createWhiteSpace(boxWidth-WEAPONS.length()-2) + "*");
                        break;
                    case 1:
                        outputStream.println(HEAD + createWhiteSpace(boxWidth-HEAD.length()-2) + "*");
                        break;
                    case 2:
                        outputStream.println(BODY + createWhiteSpace(boxWidth-BODY.length()-2) + "*");
                        break;
                    case 3:
                        outputStream.println(HANDS + createWhiteSpace(boxWidth-HANDS.length()-2) + "*");
                        break;
                    case 4:
                        outputStream.println(LEGS + createWhiteSpace(boxWidth-LEGS.length()-2) + "*");
                        break;
                    case 5:
                        outputStream.println(FEET + createWhiteSpace(boxWidth-FEET.length()-2) + "*");
                        break;
                }
                int itemCounter = 1;
                for(String itemID : equipmentItemIDList) {
                    String itemLine = "*  " + itemCounter++ +".  " +  inventory.getItemNameByItemID(itemID) + " x " + inventory.getItemCountByItemID(itemID);
                    outputStream.println(itemLine + createWhiteSpace(boxWidth-itemLine.length()) + "*");
                }
            }
        }
        outputStream.println(createBlankInventoryLine(boxWidth));
    }

    public void printEquipmentMap(Map<Integer, Equipment> equipmentMap, WidthLimitedOutputStream outputStream) {
        String equipmentName;
        outputStream.println("**Weapon:");
        equipmentName = equipmentMap.get(0)==null ? "" : equipmentMap.get(0).getName();
        outputStream.println("* " + equipmentName);
        outputStream.println("**Head:");
        equipmentName = equipmentMap.get(1)==null ? "" : equipmentMap.get(1).getName();
        outputStream.println("* " + equipmentName);
        outputStream.println("**Body:");
        equipmentName = equipmentMap.get(2)==null ? "" : equipmentMap.get(2).getName();
        outputStream.println("* " + equipmentName);
        outputStream.println("**Hands:");
        equipmentName = equipmentMap.get(3)==null ? "" : equipmentMap.get(3).getName();
        outputStream.println("* " + equipmentName);
        outputStream.println("**Legs:");
        equipmentName = equipmentMap.get(4)==null ? "" : equipmentMap.get(4).getName();
        outputStream.println("* " + equipmentName);
        outputStream.println("**Feet:");
        equipmentName = equipmentMap.get(5)==null ? "" : equipmentMap.get(5).getName();
        outputStream.println("* " + equipmentName);
    }
}