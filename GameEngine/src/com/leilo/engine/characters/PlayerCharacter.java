package com.leilo.engine.characters;

import com.leilo.engine.items.Equipment;
import com.leilo.engine.items.Inventory;
import com.leilo.engine.items.Item;
import com.leilo.engine.room.Room;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The main player character
 */
public class PlayerCharacter implements Serializable {
    private String m_playerName;
    private int m_health;
    private Room m_currentRoom;
    private Inventory m_playerInventory;
    private Map<Integer, Equipment> m_equipmentMap; //Map<[Equipment Slot #], [Equipment ID]>


    public void setName(String name) {
        m_playerName = name;
    }

    public void setHealth(int health) {
        m_health = health;
    }

    public void setCurrentRoom(Room room) {
        m_currentRoom = room;
    }

    public void setPlayerInventory(Inventory inventory) {
        m_playerInventory = inventory;
    }

    public void setPlayerEquipment(Map<Integer, Equipment> equipmentMap) {
        m_equipmentMap = equipmentMap;
    }


    public String getName() {
        return m_playerName;
    }

    public int getHealth() {
        return m_health;
    }

    public Room getCurrentRoom() {
        return m_currentRoom;
    }

    public Inventory getPlayerInventory() {
        return m_playerInventory;
    }

    public Map<Integer, Equipment> getPlayerEquipmentMap() {
        return m_equipmentMap;
    }


    public Map<Integer, Equipment> createEquipmentMap() {
        Map<Integer, Equipment> map = new HashMap<Integer, Equipment>();
        for(int i=0; i<6; i++ ) {
            map.put(i, null);
        }
        return map;
    }


    public PlayerCharacter(Room currentRoom) {
        this("Player", 25, currentRoom);
    }

    public PlayerCharacter(String name, int health, Room currentRoom) {
        this(name, health, currentRoom, new Inventory(20));
    }

    public PlayerCharacter(String name, int health, Room currentRoom, Inventory inventory) {
        m_playerName = name;
        m_health = health;
        m_currentRoom = currentRoom;
        m_playerInventory = inventory;
        m_equipmentMap = createEquipmentMap();
    }

    /**
     * Attempts to add the item to the player's inventory
     * @return true == success | false == failure
     */
    public boolean addItemToPlayerInventory(Item item, int itemCount) {
        return m_playerInventory.addItemToInventoryByCount(item, itemCount);
    }

    public void addItemsToPlayerInventory(List<Item> items) {
        m_playerInventory.addItemsToInventory(items);
    }

    public void equipItemToPlayer(String itemID) {
        if(m_playerInventory.doesInventoryContainItem(itemID)) {
            Equipment equipment = (Equipment) m_playerInventory.getItemFromInventory(itemID);
            equipItemToPlayer(equipment);
        }
    }

    public void equipItemToPlayer(Equipment equipment) {
        int equipmentSlot = equipment.getEquipmentSlot();
        Equipment alreadyEquipped = m_equipmentMap.get(equipmentSlot);
        m_equipmentMap.put(equipmentSlot, equipment);
        m_playerInventory.addItemToInventory(alreadyEquipped);
    }
}