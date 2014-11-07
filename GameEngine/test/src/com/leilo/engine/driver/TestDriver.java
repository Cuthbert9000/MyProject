package src.com.leilo.engine.driver;

import com.leilo.engine.items.Consumable;
import com.leilo.engine.items.Equipment;
import com.leilo.engine.items.Inventory;
import com.leilo.engine.items.Item;
import com.leilo.engine.room.Room;
import com.leilo.engine.utils.MapCreator;
import com.leilo.engine.utils.PrintUtils;
import com.leilo.engine.utils.PropertyUtils;
import com.leilo.engine.utils.WidthLimitedOutputStream;
import com.leilo.engine.world.GameWorld;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 *
 */
public class TestDriver {

    private static File m_testMapPropertiesFile = new File("./test/resources/properties/TestMap.properties");
    private static File m_testItemPropertiesFile = new File("./test/resources/properties/TestItems.properties");


    public static void main(String[] args) {
        WidthLimitedOutputStream outputStream = new WidthLimitedOutputStream(System.out, 80);
        testPrintUtils(outputStream);
    }

    private static void testPrintUtils(WidthLimitedOutputStream outputStream) {
        //Test Printing of Current Location
        outputStream.println("Testing Printing of Current Location");
        try {
            GameWorld testWorld = new GameWorld();
            Properties roomProperties = PropertyUtils.getInstance().loadPropertyFile(m_testMapPropertiesFile);
            Properties itemProperties = PropertyUtils.getInstance().loadPropertyFile(m_testItemPropertiesFile);
            Map<String, Room> testGameMap = MapCreator.getInstance().createGameMap(roomProperties, null, itemProperties, outputStream);
            testWorld.addRoomsToGame(testGameMap);
            for(int i=1; i<=5; i++) {
                testWorld.setCurrentRoom(testGameMap.get("testroom"+i));
                PrintUtils.getInstance().printCurrentRoom(testWorld.getCurrentRoom(), testWorld, outputStream);
                outputStream.println();
            }
        } catch (Exception e) {
            outputStream.println("Error occurred during Current Location Print Testing: " + e.getLocalizedMessage());
        }

        outputStream.println();
        outputStream.println();
        outputStream.println("*****************************************");
        outputStream.println();
        outputStream.println();

        //Test Printing of Inventory
        outputStream.println("Testing Printing of Inventory");
        Inventory testInventory = new Inventory(50);
        List<Item> itemList = new ArrayList<Item>();
        itemList.add(new Consumable("ID_1", "A Consumable Item", "Description"));
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
        testInventory.addItemsToInventory(itemList);
        PrintUtils.getInstance().printInventory(testInventory, outputStream);
    }
}
