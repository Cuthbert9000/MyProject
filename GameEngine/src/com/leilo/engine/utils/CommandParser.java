package com.leilo.engine.utils;

import com.leilo.engine.room.Room;
import com.leilo.engine.world.GameWorld;

import java.util.*;

/**
 *
 */
public class CommandParser {
    /*
    Movement Commands:
        - go [north(n)|east(e)|south(s)|west(w)]
        - north(n)
        - east(e)
        - south(s)
        - west(w)
    Item Commands:
        - Get [Item Name]
     */

    //Command Constants
    private final List<String> NORTH = new ArrayList<String>() {{
        add("north");
        add("n");
    }};
    private final List<String> EAST = new ArrayList<String>() {{
        add("east");
        add("e");
    }};
    private final List<String> SOUTH = new ArrayList<String>() {{
        add("south");
        add("s");
    }};
    private final List<String> WEST = new ArrayList<String>() {{
        add("west");
        add("w");
    }};

    private final List<String> DIRECTIONS = new ArrayList<String>() {{
        addAll(NORTH);
        addAll(EAST);
        addAll(SOUTH);
        addAll(WEST);
    }};

    private final List<List<String>> DIRECTION_LISTS = new ArrayList<List<String>>() {{
        add(NORTH);
        add(EAST);
        add(SOUTH);
        add(WEST);
    }};


    private static CommandParser m_instance = null;

    protected CommandParser() {
        // Exists only to defeat instantiation.
    }
    public static CommandParser getInstance() {
        if(m_instance == null) {
            m_instance = new CommandParser();
        }
        return m_instance;
    }

    private List<String> getCommands(String[] commandTokens) {
        List<String> commands = new ArrayList<String>();
        for(String command : commandTokens) {
            if(!command.isEmpty()) {
                commands.add(command);
            }
        }
        return commands;
    }

    public GameWorld parseCommand(GameWorld gameWorld, String input, WidthLimitedOutputStream output) {
        GameWorld tempWorld = gameWorld;
        input = input.trim().toLowerCase();
        List<String> commands = getCommands(input.split(" "));
        String command = commands.get(0);
        if(command.equals("go")) {
            if(commands.size() < 2) {
                output.println("You need to provide a direction!");
            }
            command = commands.get(1);
        }
        if(DIRECTIONS.contains(command)) {
            tempWorld = processGoCommand(tempWorld, commands.get(1), output);
        } else if(command.equals("get")) {

        } else if(command.equals("equip")) {

        } else if(command.equals("attack")) {

        }
        return tempWorld;
    }

    private GameWorld processGoCommand(GameWorld gameWorld, String direction, WidthLimitedOutputStream outputStream) {
        GameWorld tempWorld = gameWorld;
        Room currentRoom = tempWorld.getCurrentRoom();
        String newRoomID = null;
        Map<String, String> currentRoomExitMap = currentRoom.getExitMap();
        for(List<String> directionList : DIRECTION_LISTS) {
            if(directionList.contains(direction)) {
                newRoomID = currentRoomExitMap.get(directionList.get(0));
            }
        }
        if(newRoomID != null) {
            tempWorld.updateCurrentRoom(newRoomID);
        }
        return tempWorld;
    }
}
