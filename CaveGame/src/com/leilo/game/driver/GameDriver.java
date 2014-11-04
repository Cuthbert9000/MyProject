package com.leilo.game.driver;

import com.leilo.game.engine.display.WidthLimitedOutputStream;
import com.leilo.game.engine.input.CommandParser;
import com.leilo.game.engine.world.GameWorld;
import com.leilo.game.entities.mobs.PlayerCharacter;
import com.leilo.game.map.utils.MapCreator;
import com.leilo.game.map.location.Location;

import java.io.*;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

/**
 * The main game driver class
 */
public class GameDriver {

    private String m_filename = "C:/TextGame/gameworld.dat";
    private Map<String, Location> m_gameMap;
    private PlayerCharacter m_player;
    private GameWorld m_game;


    // Output stream for gaming system
    transient private WidthLimitedOutputStream m_output;


    // Game demo constructor
    public GameDriver() throws Exception {
        init();
    }


    public static void main(String[] args) throws Exception {
        new GameDriver().play();
    }

    public void init() {
        setOutputStream(System.out, 80);
        Properties roomProperties = new Properties();
        Properties mobProperties = new Properties();
        Properties itemProperties = new Properties();
        loadProperties(roomProperties, mobProperties, itemProperties);
        try {
            File gameFile = new File(m_filename);
            if(!gameFile.exists()) {
                gameFile.createNewFile();
                //If the game file doesn't exist, set up a new game.
                //Create the Game Map
                m_gameMap = MapCreator.getInstance().createMap(roomProperties, mobProperties, itemProperties, m_output);
                //Create the Player
                m_player = new PlayerCharacter(100, 8);

                //Create the game World
                m_game = new GameWorld();
                m_game.addGameMap(m_gameMap);
                m_game.addPlayerCharacter(m_player);
                m_game.setCurrentLocation(m_gameMap.get("StartingRoom"));
            } else {
                // Create a file input stream
                FileInputStream fin = new FileInputStream(m_filename);
                // Create an object input stream
                ObjectInputStream objectIn = new ObjectInputStream(fin);
                // Read an object in from object store, and cast it to a GameWorld
                m_game = (GameWorld)objectIn.readObject();
                // Set the object stream to standard output
            }
            saveGame(gameFile);
        }
        catch (Exception e) {
            System.err.println ("Unable to create game data");
        }
    }

    private void loadProperties(Properties roomProperties, Properties mobProperties, Properties itemProperties) {
        InputStream roomIn = GameDriver.class.getResourceAsStream("/com/leilo/game/properties/Map.properties");
        InputStream mobIn = GameDriver.class.getResourceAsStream("/com/leilo/game/properties/Mobs.properties");
        InputStream itemIn = GameDriver.class.getResourceAsStream("/com/leilo/game/properties/Items.properties");
        try {
            roomProperties.load(roomIn);
            mobProperties.load(mobIn);
            itemProperties.load(itemIn);
            roomIn.close();
            mobIn.close();
            itemIn.close();
        } catch (Exception e) {
            System.err.print("Error loading property file");  //todo maybe eventually add in which property file
        }
    }

    /**
     * Sets the output stream for the gaming systewm
     */
    private void setOutputStream(OutputStream out, int width) {
        m_output = new WidthLimitedOutputStream(out, width);
    }

    public void saveGame(GameWorld gameWorld) throws Exception {
        m_game = gameWorld;
        File gameFile = new File(m_filename);
        if(!gameFile.exists()) {
            gameFile.createNewFile();
        }
        saveGame(gameFile);
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
        System.out.println ("Game data created as " + m_filename );
    }


    public void play() {
        String command = null;
        // Create a BufferedReader
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        for (;;)
        {
            // Show location
            m_game.showLocation(m_output);
            // Get user input
            try {
                command = reader.readLine();
                m_game = CommandParser.getInstance().parseCommand(command, this, m_game, reader, m_output);
                // Print a new line
                System.out.println();
            } catch (Exception e)  {
                m_output.println("ERROR");
            }
        }
    }
}


