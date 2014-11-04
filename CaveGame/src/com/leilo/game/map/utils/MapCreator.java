package com.leilo.game.map.utils;

import com.leilo.game.engine.display.WidthLimitedOutputStream;
import com.leilo.game.entities.items.Inventory;
import com.leilo.game.entities.items.Item;
import com.leilo.game.entities.items.util.ItemCreator;
import com.leilo.game.entities.mobs.Mob;
import com.leilo.game.entities.mobs.util.MobCreator;
import com.leilo.game.map.location.Location;

import java.io.InputStream;
import java.util.*;

/**
 *  Creates the Map<String, Location> of LocationTitle --> Location Object
 */
public class MapCreator {

    private static MapCreator instance = null;

    protected MapCreator() {
        // Exists only to defeat instantiation.
    }
    public static MapCreator getInstance() {
        if(instance == null) {
            instance = new MapCreator();
        }
        return instance;
    }

    public Map<String, Location> createMap(Properties roomProperties, Properties mobProperties, Properties itemProperties, WidthLimitedOutputStream output) {
        String locationList = roomProperties.getProperty("LocationList");
        String[] locationArray = locationList.split(",");

        Map<String, Location> locationMap = new HashMap<String, Location>();

        for(String location : locationArray) {
            locationMap.put(location, new Location());
        }

        for(String location : locationArray) {
            String roomTitle = roomProperties.getProperty(location+".title");
            String roomDescription = roomProperties.getProperty(location+".description");
            boolean itemExists = roomProperties.containsKey(location + ".items");
            boolean mobExists = roomProperties.containsKey(location + ".mobs");
            String roomExits = roomProperties.getProperty(location+".exits");

            String[] exitArray = roomExits.split(",");

            Location tempLocation = locationMap.get(location);
            tempLocation.setRoomTitle(roomTitle);
            tempLocation.setRoomDescription(roomDescription);
            Map<String, String> exitMap = new HashMap<String, String>();
            Map<String, String> exitNameMap = new HashMap<String, String>();

            if(itemExists) {
                String itemProperty = roomProperties.getProperty(location + ".items");
                String[] itemArray = itemProperty.split(",");
                Inventory locationInventory = new Inventory();
                for(String itemCode : itemArray) {
                    String itemType = itemProperties.getProperty(itemCode + ".itemtype");
                    int itemTypeInt = -1;
                    try {
                        itemTypeInt = Integer.parseInt(itemType);
                    } catch (NumberFormatException nfe) {
                        output.println("ERROR: Item creation failed for " + itemCode + "in " + location);
                    }
                    if(itemTypeInt > -1) {
                        Item item = ItemCreator.getInstance().createItem(itemCode, itemTypeInt, itemProperties);
                        locationInventory.addItemToInventory(itemCode, item);
                    }
                }
            }

            if(mobExists) {
                String mobProperty = roomProperties.getProperty(location+".mobs");
                String[] mobArray = mobProperty.split(",");
                List<Mob> mobList = new ArrayList<Mob>();
                for(String mob : mobArray) {
                    Mob roomMob = MobCreator.getInstance().createMob(mob, mobProperties);
                    mobList.add(roomMob);
                }
                tempLocation.setMobList(mobList);
            }
            for(String exit : exitArray) {
                String[] exitTokens = exit.split(":");
                String exitDirection = exitTokens[0];
                String exitLocationTitle = exitTokens[1];
                String shortExitDirection = exitDirection.substring(0, 1).toUpperCase();
                exitMap.put(shortExitDirection, exitLocationTitle);
                exitNameMap.put(exitDirection, roomProperties.getProperty(exitLocationTitle+".title"));
            }
            tempLocation.setExits(exitMap);
            tempLocation.setExitsName(exitNameMap);
        }

        return locationMap;

    }

}
