package com.leilo.game.driver;

import com.leilo.engine.items.Equipment;
import com.leilo.engine.room.Room;
import com.leilo.engine.utils.*;
import com.leilo.engine.world.GameWorld;

import java.io.*;
import java.util.Map;
import java.util.Properties;

/**
 * Main game driver class
 */
public class GameDriver {
    private String m_fileName = "./resources/saves/gameworld.dat";
    private File m_mapPropertiesFile = new File("./resources/properties/Map.properties");
    private File m_itemPropertiesFile = new File("./resources/properties/Items.properties");
    private File m_mobPropertiesFile = new File("./resources/properties/Mobs.properties");
    private Map<String, Room> m_gameMap;
    private GameWorld m_game;

    // Output stream for writing to console
    transient private WidthLimitedOutputStream m_output;

    public GameDriver() {
        m_output = new WidthLimitedOutputStream(System.out, 80);
        try {
            init();
        } catch (Exception e) {
            m_output.println("Error initializing the game world");
            return;
        }
    }

    public static void main(String[] args) {
        new GameDriver().play();
    }

    public void init() throws Exception {
        Properties roomProperties = PropertyUtils.getInstance().loadPropertyFile(m_mapPropertiesFile);
        Properties itemProperties = PropertyUtils.getInstance().loadPropertyFile(m_itemPropertiesFile);
        Properties mobProperties = PropertyUtils.getInstance().loadPropertyFile(m_mobPropertiesFile);
        File gameFile = new File(m_fileName);
        if(!gameFile.exists()) {
            gameFile.createNewFile();
            //If the game file doesn't exist, set up a new game.
            //Create the Game Map
            m_gameMap = MapCreator.getInstance().createGameMap(roomProperties, mobProperties, itemProperties, m_output);

            //todoCreate the Player
            //m_player = new PlayerCharacter(100, 8);

            //Create the game World
            m_game = new GameWorld();
            m_game.addRoomsToGame(m_gameMap);
            //m_game.addPlayerCharacter(m_player);
            m_game.setCurrentRoom(m_gameMap.get("StartingRoom"));
            saveGame(gameFile);
        } else {
            // Create a file input stream
            FileInputStream fin = new FileInputStream(m_fileName);
            // Create an object input stream
            ObjectInputStream objectIn = new ObjectInputStream(fin);
            // Read an object in from object store, and cast it to a GameWorld
            m_game = (GameWorld)objectIn.readObject();
        }
    }

    private void saveGame(File gameFile) throws Exception{
        // Create a file to write game system
        FileOutputStream out = new FileOutputStream(gameFile, false);
        // Create an object output stream, linked to out
        ObjectOutputStream objectOut = new ObjectOutputStream(out);
        // Write game system to object store
        objectOut.writeObject(m_game);
        // Close object output stream
        objectOut.close();
        m_output.println("Game data created at " + m_fileName);
    }

    public void play() {
        String command = null;
        // Create a BufferedReader
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        for (;;)
        {
            // Show location
            PrintUtils.getInstance().printCurrentRoom(m_game.getCurrentRoom(), m_game, m_output);
            // Get user input
            try {
                command = reader.readLine().trim();
                m_game = CommandParser.getInstance().parseCommand(m_game, command, m_output);
                // Print a new line
                System.out.println();
                //todo add in handling stuff about game status
            } catch (Exception e)  {
                m_output.println("ERROR");
            }
        }
    }
}