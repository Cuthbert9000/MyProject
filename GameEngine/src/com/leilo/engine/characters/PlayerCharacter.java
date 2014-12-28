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
public class PlayerCharacter extends NPC implements Serializable {
    private String m_playerName;
    private int m_health;
    private Room m_currentRoom;
    private Inventory m_playerInventory;
    private Map<Integer, Equipment> m_equipmentMap; //Map<[Equipment Slot #], [Equipment ID]>

    public void setPlayerEquipment(Map<Integer, Equipment> equipmentMap) {
        m_equipmentMap = equipmentMap;
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
        super(name, health, currentRoom, inventory);
        m_equipmentMap = createEquipmentMap();
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