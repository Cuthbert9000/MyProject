package com.leilo.game.entities.mobs;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Enemy mob class
 */
public class Mob implements Serializable {

    private String  m_name = "";
    private int m_health = -1;
    //todo descriptions
    private String m_experienceRange = "";
    private Map<String, Map<String, String>> m_attackMap = new HashMap<String, Map<String, String>>();

    public void setName(String name) {
        m_name = name;
    }

    public void setHealth(int health) {
        m_health = health;
    }

    public void setExperienceRange(String experienceRange) {
        m_experienceRange = experienceRange;
    }

    public void setAttackMap(Map<String, Map<String, String>> attackMap) {
        m_attackMap = attackMap;
    }

    public String getName() {
        return m_name;
    }

    public int getHealth() {
        return m_health;
    }

    public String  getExperienceRange() {
        return m_experienceRange;
    }

    public Map<String, Map<String, String>> getAttackMap() {
        return m_attackMap;
    }


}
