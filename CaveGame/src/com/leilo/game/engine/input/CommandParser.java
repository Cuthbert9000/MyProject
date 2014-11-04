package com.leilo.game.engine.input;

import com.leilo.game.driver.GameDriver;
import com.leilo.game.engine.combat.CombatEngine;
import com.leilo.game.engine.display.WidthLimitedOutputStream;
import com.leilo.game.engine.world.GameWorld;
import com.leilo.game.entities.mobs.Mob;
import com.leilo.game.map.location.Location;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CommandParser {
    private static CommandParser instance = null;
    private List<String> travelCommandList = Arrays.asList(new String[]{"NORTH", "EAST", "SOUTH", "WEST", "UP", "DOWN"});
    private List<String> shortTravelCommandList = Arrays.asList(new String[]{"N", "E", "S", "W", "U", "D"});

    protected CommandParser() {
        // Exists only to defeat instantiation.
    }
    public static CommandParser getInstance() {
        if(instance == null) {
            instance = new CommandParser();
        }
        return instance;
    }

    public GameWorld parseCommand(String command, GameWorld gameWorld, BufferedReader reader, WidthLimitedOutputStream output) throws Exception{
        return parseCommand(command, null, gameWorld, reader, output);
    }

    public GameWorld parseCommand(String command, GameDriver driver, GameWorld gameWorld, BufferedReader reader, WidthLimitedOutputStream output) throws Exception {
        Location currentLocation = gameWorld.getCurrentLocation();
        if(!isValidCommand(command)) {
            output.println("Huh? Invalid command!");
            output.println();
        }
        command = command.toUpperCase(); //Kick the command to all upper case for easier parsing
        if(command.equals("QUIT") || command.equals("EXIT")) {
            output.println("Ok, goodbye.");
            driver.saveGame(gameWorld);
            output.close();
            System.exit(0);
        }
        if(isTravelCommand(command)) {
            Map<String, Location> gameMap = gameWorld.getGameMap();
            if(command.startsWith("GO ")) {
                command.substring(2);
            }
            if(command.startsWith("G ")) {
                command = command.substring(1);
            }
            command = command.trim();
            command = command.substring(0, 1);
            if(shortTravelCommandList.contains(command)) {
                if(currentLocation.isValidExitDirection(command)) {
                    Location nextLocation = gameMap.get(currentLocation.getExitAtDirection(command));
                    gameWorld.setCurrentLocation(nextLocation);
                } else{
                    output.println("You cannot move in that direction");
                }
            } else {
                output.println("Huh? Go where?");
            }
        } else if(isAttackCommand(command)) {
            if(currentLocation.containsMob()) {
                gameWorld = CombatEngine.getInstance().performCombat(gameWorld, reader, output);
            } else {
                output.println("There is no one to attack.");
            }
        } else if(isGetCommand(command)) {
            if(command.equals("GET")) {
                output.println("Get? Get what?");
            } else {
                String itemName = command.substring(3);
            }
        } else if(isLookCommand(command)) {

        }
        return gameWorld;
    }

    private boolean isValidCommand(String command) {
        if(command == null) {
            return false;
        }
        command = command.trim();
        if(command.length() == 0) {

        }
        return true;
    }

    private boolean isTravelCommand(String command) {
        return (command.startsWith("GO ")) || (command.startsWith("G "));
    }

    private boolean isAttackCommand(String command) {
        return (command.equals("ATTACK")) || (command.equals("A"));
    }

    private boolean isGetCommand(String command) {
        return command.startsWith("GET ");
    }

    private boolean isLookCommand(String command) {
        return (command.startsWith("LOOK ")) || (command.startsWith("L "));
    }
}
