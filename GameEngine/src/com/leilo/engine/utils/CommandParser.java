package com.leilo.engine.utils;

import com.leilo.engine.world.GameWorld;

/**
 *
 */
public class CommandParser {
    /*
    Movement Commands:
        - Go (g) [North(N/n)|East(E/e)|South(S/s)|West(W/w)]
        - North(N/n)
        - East(E/e)
        - South(S/s)
        - West(W/w)
    Item Commands:
        - Get [Item Name]
     */
    
    private static CommandParser m_instance = null;

    protected CommandParser() {
        // Exists only to defeat instantiation.
    }
    public static CommandParser getInstance() {
        if(m_instance == null) {
            m_instance = new CommandParser();
        }
        return m_instance;
    }

    public GameWorld parseCommand(GameWorld gameWorld, String command, WidthLimitedOutputStream output) {

        GameWorld tempWorld = gameWorld;


        return tempWorld;
    }
}
