package com.leilo.engine.items;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Item class
 */
public class Item implements Serializable {

    private String m_itemID;
    private String m_name;
    private String m_itemDescription;
    private Map<String, String> m_roomDescriptionsMap; //Map<RoomID, Room Description>
    private int m_itemType;

    public Item() {}

    public Item(String itemID, String name, String itemDescription, int itemType) {
        this(itemID, name, itemDescription, null, itemType);
    }

    public Item(String itemID, String name, String itemDescription, String roomDescriptions, int itemType) {
        m_itemID = itemID.trim();
        m_name = name.trim();
        m_itemDescription = itemDescription.trim();
        if(roomDescriptions != null) {
            createRoomDescriptionsMap(roomDescriptions);
        }
        m_itemType = itemType;
    }

    public void setItemID(String itemID) {
        m_itemID = itemID;
    }

    public void setName(String name) {
        m_name = name;
    }

    public void setItemDescription(String itemDescription) {
        m_itemDescription = itemDescription;
    }

    public void setItemType(int itemType) {
        m_itemType = itemType;
    }

    public String getItemID() {
        return m_itemID;
    }

    public String getName() {
        return m_name;
    }

    public Map<String, String> getRoomDescriptionsMap() {
        return m_roomDescriptionsMap;
    }

    public String getItemDescription() {
        return m_itemDescription;
    }

    public int getItemType() {
        return m_itemType;
    }

    public void createRoomDescriptionsMap(String roomDescriptions) {
        m_roomDescriptionsMap = new HashMap<String, String>();
        String[] tokens = roomDescriptions.split("\\|");
        for(String token : tokens) {
            String[] roomDescriptionTokens = token.trim().split(":");
            m_roomDescriptionsMap.put(roomDescriptionTokens[0], roomDescriptionTokens[1]);
        }
    }
}
