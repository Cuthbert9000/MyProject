package testGame.driver;

import com.leilo.engine.characters.PlayerCharacter;
import com.leilo.engine.items.Consumable;
import com.leilo.engine.items.Equipment;
import com.leilo.engine.items.Item;
import com.leilo.engine.room.Room;
import com.leilo.engine.utils.*;
import com.leilo.engine.world.GameWorld;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Main game driver class
 */
public class TestGameDriver {
    private String m_testFile = "./test/testGame/testSaves/testGame.dat";
    private File m_mapPropertiesFile = new File("./test/resources/properties/TestMap.properties");
    private File m_itemPropertiesFile = new File("./test/resources/properties/TestItems.properties");
    //todo add this nonsense private File m_mobPropertiesFile = new File("./test/resources/properties/TestMobs.properties");
    private Map<String, Room> m_gameMap;
    private GameWorld m_game;
    private PlayerCharacter m_player;

    // Output stream for writing to console
    transient private WidthLimitedOutputStream m_output;

    public TestGameDriver() {
        m_output = new WidthLimitedOutputStream(System.out, 60);
        try {
            init();
        } catch (Exception e) {
            m_output.println("Error initializing the game world");
            return;
        }
    }

    private void shutdown() {
        m_output.close();

    }

    public static void main(String[] args) {
        TestGameDriver testDriver = new TestGameDriver();
        testDriver.play();
        testDriver.shutdown();
    }

    public void init() throws Exception {
        Properties roomProperties = PropertyUtils.getInstance().loadPropertyFile(m_mapPropertiesFile);
        Properties itemProperties = PropertyUtils.getInstance().loadPropertyFile(m_itemPropertiesFile);
        //todo add this nonsense Properties mobProperties = PropertyUtils.getInstance().loadPropertyFile(m_mobPropertiesFile);
        m_gameMap = MapCreator.getInstance().createGameMap(roomProperties, null, itemProperties, m_output);

        //Create the Player
        m_player = new PlayerCharacter("TestPlayer", 100, m_gameMap.get("testroom1"));
        m_player.addItemsToPlayerInventory(createItemList());

        //Create the game World
        m_game = new GameWorld();
        m_game.addRoomsToGame(m_gameMap);
        //m_game.addPlayerCharacter(m_player);
        m_game.setCurrentRoom(m_gameMap.get(roomProperties.getProperty("StartingRoom")));

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
        m_output.println("Game data created at " + m_testFile);
    }

    public void play() {
        String command = null;
        // Create a BufferedReader
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        //Show initial location
        PrintUtils.getInstance().printCurrentRoom(m_game.getCurrentRoom(), m_game, m_output);
        Room currentRoom = m_game.getCurrentRoom();
        while(m_game.isGameActive())
        {
            if(currentRoom != m_game.getCurrentRoom()) {
                PrintUtils.getInstance().printCurrentRoom(m_game.getCurrentRoom(), m_game, m_output);
                currentRoom = m_game.getCurrentRoom();
            }
            try {
                // Get user input
                command = reader.readLine().trim();
                if(command.isEmpty()) {
                    continue;
                }
                m_game = CommandParser.getInstance().parseCommand(m_game, command, m_output);
                m_output.println();
            } catch (Exception e)  {
                m_output.println("ERROR");
                return;
            }
        }
    }

    private List<Item> createItemList() {
        List<Item> itemList = new ArrayList<Item>();
        itemList.add(new Consumable("ID_1", "A Consumable Item Item Item Item Item ", "Description"));
        itemList.add(new Consumable("ID_2", "Beer", "Description"));
        itemList.add(new Consumable("ID_2", "Beer", "Description"));
        itemList.add(new Consumable("ID_3", "Consumable Item", "Description"));
        itemList.add(new Consumable("ID_3", "Consumable Item", "Description"));
        itemList.add(new Consumable("ID_3", "Consumable Item", "Description"));
        itemList.add(new Consumable("ID_4", "Derp", "Description"));
        itemList.add(new Consumable("ID_5", "Barnacles", "Description"));
        itemList.add(new Equipment("ID_6", "Sword", "Description", 0));
        itemList.add(new Equipment("ID_7", "Axe", "Description", 0));
        itemList.add(new Equipment("ID_8", "Mace", "Description", 0));
        itemList.add(new Equipment("ID_9", "Helmet", "Description", 1));
        itemList.add(new Equipment("ID_9", "Helmet", "Description", 1));
        itemList.add(new Equipment("ID_10", "Hat", "Description", 1));
        itemList.add(new Equipment("ID_11", "Mail Cuirass", "Description", 2));
        itemList.add(new Equipment("ID_11", "Mail Cuirass", "Description", 2));
        itemList.add(new Equipment("ID_12", "Pants", "Description", 4));
        itemList.add(new Equipment("ID_13", "Boots", "Description", 5));
        return itemList;
    }
}