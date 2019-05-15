package com.wx.multihero.variability.Hero;

import com.wx.multihero.entity.Character;

import java.util.HashMap;

import static com.wx.multihero.variability.Hero.HeroFactory.ID.RYU;

public class HeroFactory {
    public enum ID {
        RYU,
        RASH
    }
    private static final HashMap<Integer, ID> ID_MAP = new HashMap<Integer, ID>();
    static {
        ID_MAP.put(0, RYU);
    }
    public static Hero create(Character character) {
        ID id = ID_MAP.get(character.getId());
        switch (id) {
            case RYU:
                return new Ryu(character);
        }
        return null;
    }
}
