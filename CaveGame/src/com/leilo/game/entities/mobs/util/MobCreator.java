package com.leilo.game.entities.mobs.util;

import com.leilo.game.entities.mobs.Mob;

import java.io.InputStream;
import java.util.*;

public class MobCreator {

    private static MobCreator instance = null;

    InputStream m_in;
    Properties m_properties;

    protected MobCreator() {
        // Exists only to defeat instantiation.
    }
    public static MobCreator getInstance() {
        if(instance == null) {
            instance = new MobCreator();
        }
        return instance;
    }

    public Mob createMob(String targetMob, Properties mobProperties) {
        Mob mob = new Mob();
        String mobName = mobProperties.getProperty(targetMob+".name");
        int mobHP = Integer.parseInt(mobProperties.getProperty(targetMob+".health"));
        String experienceRange = mobProperties.getProperty(targetMob+".experience");
        String mobAttacks = mobProperties.getProperty(targetMob+".attacks");
        Map<String, Map<String, String>> mobAttackMap = createAttackMap(mobAttacks);
        mob.setName(mobName);
        mob.setHealth(mobHP);
        mob.setAttackMap(mobAttackMap);
        mob.setExperienceRange(experienceRange);
        return mob;
    }

    private Map<String, Map<String, String>> createAttackMap(String mobAttacks) {
        Map<String, Map<String, String>> attackMap = new HashMap<String, Map<String, String>>();
        //Split the Attack Property by | to get each the list of the Player-HP Specific attacks
        //Example Property: High:<Name>[<LowDam>-<HighDam>],<Name2>[<LowDam2>-<HighDam2>]|Med:<Name>[<LowDam>-<HighDam>]|Low:\
        List<String> attackVariants = Arrays.asList(mobAttacks.split("\\|"));
        //For each Player-HP Specific Attack List
        for(String hpSpecificAttackList : attackVariants) {
            int playerHPAttackRangeIndex = hpSpecificAttackList.indexOf(":");
            String playerHPAttackRange = hpSpecificAttackList.substring(0, playerHPAttackRangeIndex);
            String attackListString = hpSpecificAttackList.substring(playerHPAttackRangeIndex+1);
            Map<String, String> innerMap = new HashMap<String, String>();
            List<String> attackList = Arrays.asList(attackListString.split(","));
            //For each Individual Attack
            for(String attackInfo : attackList) {
                if(attackInfo.isEmpty()) {
                    break;
                }
                int attackNameIndex = attackInfo.indexOf("[");
                String attackName = attackInfo.substring(0, attackNameIndex);
                String damageRange = attackInfo.substring(attackNameIndex);
                innerMap.put(attackName, damageRange);
            } //End Individual Attack List
            attackMap.put(playerHPAttackRange, innerMap);
        } //End Player-HP Specific Attack List
        return attackMap;
    }
}
