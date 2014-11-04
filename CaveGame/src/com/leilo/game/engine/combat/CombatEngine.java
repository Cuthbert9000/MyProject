package com.leilo.game.engine.combat;

import com.leilo.game.engine.display.WidthLimitedOutputStream;
import com.leilo.game.engine.world.GameWorld;
import com.leilo.game.entities.mobs.Mob;
import com.leilo.game.map.location.Location;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CombatEngine {
    private static CombatEngine instance = null;

    protected CombatEngine() {
        // Exists only to defeat instantiation.
    }
    public static CombatEngine getInstance() {
        if(instance == null) {
            instance = new CombatEngine();
        }
        return instance;
    }

    public GameWorld performCombat(GameWorld gameWorld, BufferedReader reader, WidthLimitedOutputStream output) {
        Location currentLocation = gameWorld.getCurrentLocation();
        List<Mob> mobList = currentLocation.getRoomMobs();
        Mob mob;
        //If the mobList contains only 1 mob, attack that mob, else determine which to attack
        if(mobList.size()==1) {
            mob = mobList.get(0);
        } else {
             mob = getMobForCombat(mobList, reader, output);
        }
        if(mob == null) {
            return gameWorld;
        }
        boolean combatIsOngoing = true;
        while(combatIsOngoing) {
            //First the Player Attacks
            //todo combat
        }

        // IF SUCCESSFUL ? --> currentLocation.removeMobFromList(mob);
                /*    todo for combat
        String[] experienceTokens = experienceRange.split("-");
        int experienceLow = Integer.parseInt(experienceTokens[0]);
        int experienceHigh = Integer.parseInt(experienceTokens[1]);
        int experience = experienceLow + (int)(Math.random() * ((experienceHigh - experienceLow) + 1));
        */
        gameWorld.setCurrentLocation(currentLocation);
        return gameWorld;
    }

    public Mob getMobForCombat(List<Mob> mobList, BufferedReader reader, WidthLimitedOutputStream output) {
        Mob mob = null;
        String decision = "";
        int decisionValue = -1;
        Map<Integer, Mob> mobMap = new HashMap<Integer, Mob>(mobList.size());
        output.println("Attack which mob:");
        for(int m=0; m<mobList.size(); m++) {
            mob = mobList.get(m);
            mobMap.put(m, mob);
            output.println("( " + m + " ) " + mob.getName());
        }
        boolean decisionMade = false;
        while(!decisionMade) {
            try {
                decision = reader.readLine();
                decisionValue = Integer.valueOf(decision);
                if(mobMap.keySet().contains(decisionValue)) {
                    mob = mobMap.get(decisionValue);
                    decisionMade = true;
                } else {
                    output.println("Invalid selection. Please enter the digit of the mob you'd like to attack.");
                    output.println("Attack which mob:");
                    for(int m=0; m<mobList.size(); m++) {
                        mob = mobList.get(m);
                        mobMap.put(m, mob);
                        output.println("( " + m + " ) " + mob.getName());
                    }
                }
            } catch (IOException e) {
                output.println("ERROR");
            } catch (NumberFormatException nfe) {
                output.println("Invalid selection. Please enter the digit of the mob you'd like to attack.");
                output.println("Attack which mob:");
                for(int m=0; m<mobList.size(); m++) {
                    mob = mobList.get(m);
                    mobMap.put(m, mob);
                    output.println("( " + m + " ) " + mob.getName());
                }
                continue;
            }
        }
        return mob;
    }
}
