package com.leilo.engine.combat;

/**
 * Engine to run combat
 */
public class CombatEngine {

    private static CombatEngine m_instance = null;

    protected CombatEngine() {
        // Exists only to defeat instantiation.
    }
    public static CombatEngine getInstance() {
        if(m_instance == null) {
            m_instance = new CombatEngine();
        }
        return m_instance;
    }



}
