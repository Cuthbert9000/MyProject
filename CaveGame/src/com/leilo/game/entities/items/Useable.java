package com.leilo.game.entities.items;

public class Useable extends Item {
    private String m_name;
    private String m_description;
    //Need some kind of stat or whatnot

    public Useable(String name, String description) {
        super(name, description);
    }
}
