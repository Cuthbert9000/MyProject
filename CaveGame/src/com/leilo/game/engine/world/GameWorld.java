package com.leilo.game.engine.world;

import com.leilo.game.engine.display.WidthLimitedOutputStream;
import com.leilo.game.entities.mobs.Mob;
import com.leilo.game.entities.mobs.PlayerCharacter;
import com.leilo.game.map.location.Location;

import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Creates and prints the game world
 */
public class GameWorld implements Serializable {

    private Map<String, Location> m_gameMap;
    private Location m_currentLocation;
    private PlayerCharacter m_player;

    /*
     * GameWorld constructor
     */
    public GameWorld() {
        //Create the Game Map
        m_gameMap = new HashMap<String, Location>();
        m_gameMap.put("CurrentLocation", null);
        // By default, use standard output
    }

    /**
     * Returns the current location of the player
     */
    public Location getCurrentLocation() {
        return m_currentLocation;
    }

    public Map<String, Location> getGameMap() {
        return m_gameMap;
    }

    /**
     * Adds the PlayerCharacter
     */
    public void addPlayerCharacter(PlayerCharacter player) {
        m_player = player;
    }

    /**
     * Assigns a new location to the current location of the player
     */
    public void setCurrentLocation(Location currentLocation) {
        m_currentLocation = currentLocation;
    }

    /**
     * Adds the GameMap
     */
    public void addGameMap(Map<String, Location> gameMap) {
        for(String key : gameMap.keySet()) {
            if(!m_gameMap.containsKey(key)) {
                m_gameMap.put(key, gameMap.get(key));
            }
        }
    }

    /**
     * Shows the current game location
     */
    public void showLocation(WidthLimitedOutputStream output) {
        // Show title
        output.println("You are in " + m_currentLocation.getRoomTitle());
        // Show description
        output.print(m_currentLocation.getRoomDescription());
        if(m_currentLocation.containsMob()) {
            output.println();
            output.print("Before you stands ");
            List<Mob> mobList = m_currentLocation.getRoomMobs();
            for(int i=0; i<mobList.size(); i++) {
                Mob mob = mobList.get(i);
                if(i == mobList.size()-1) {
                    if(mobList.size() == 1) {
                        output.println("a " + mob.getName() + "!");
                    } else {
                        output.print("and a " + mob.getName() + "!");
                    }
                } else {
                    output.print("a " + mob.getName() + ", ");
                }
            }
        }
        output.println();
        output.println();
        // Show available exits
        output.println ("Exits:");
        String exits = m_currentLocation.getExitPrintString();
        String[] exitArray = exits.split("\\|");
        for(String exit : exitArray) {
            output.println(exit);
        }
    }
}
