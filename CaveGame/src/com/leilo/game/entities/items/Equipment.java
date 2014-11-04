package com.leilo.game.entities.items;

import java.util.Map;
import java.util.Properties;

public class Equipment extends Item {
    private String m_name;
    private String m_description;
    private String m_intendedSlot;
    private String m_attackRange;
    private String m_defenseValue;
    private Map<String, String> m_bonusStats;

    public Equipment(String name, String description) {
        super(name, description);
    }

    public Equipment(String name, String description, String intendedSlot) {
        super(name, description);
        m_intendedSlot = intendedSlot;
    }

    public void setIntendedSlot(String intendedSlot) {
        m_intendedSlot = intendedSlot;
    }

    public String getIntendedSlot() {
        return m_intendedSlot;
    }

    public void createBonusStatMap(String itemCode, String slot, Properties mobProperties) {
        boolean isWeapon = slot.equals("RHand");
        if(isWeapon) {
            m_attackRange = mobProperties.getProperty(itemCode + ".attack");
        }
        String defenseStat = mobProperties.contains(itemCode + ".defense") ? mobProperties.getProperty(itemCode + ".defense") : "";
        if(!defenseStat.equals("")) {
            m_defenseValue = defenseStat;
        }

        String bonusStats = mobProperties.contains(itemCode + ".bonusstats") ? mobProperties.getProperty(itemCode + ".bonusstats") : "";
        if(!bonusStats.equals("")) {
            String[] bonusStatTokens = bonusStats.split(",");
            for(String bonusStat : bonusStatTokens) {
                String[] bonusTokens = bonusStat.split("|");
                String stat = bonusTokens[0];
                String bonus = bonusTokens[1];
                m_bonusStats.put(stat, bonus);
            }
        }
    }
}
