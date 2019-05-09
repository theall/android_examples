package com.wx.multihero.variability.Hero;

public class HeroFactory {
    public enum ID {
        RYU,
        RASH
    }
    public Hero create(ID id) {
        switch (id) {
            case RYU:
                return new Ryu();
        }
        return null;
    }
}
