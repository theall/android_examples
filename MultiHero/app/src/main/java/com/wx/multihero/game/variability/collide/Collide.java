package com.wx.multihero.game.variability.collide;

import android.graphics.RectF;

import com.wx.multihero.game.variability.hero.Hero;
import com.wx.multihero.game.variability.object.Plat;

public class Collide {
    public enum Type {
        LAND,
        HEAD,
        LEFT,
        RIGHT
    }
    private Type mType;

    public static boolean test(RectF r1, RectF r2) {
        return r1.intersect(r2);
    }

    public static boolean testLand(Hero hero, Plat plat) {
        if(hero.virtualized || plat.virtualized)
            return false;

        boolean r = false;
        if((hero.rect.left>=plat.rect.left && hero.rect.left<=plat.rect.right) || (hero.rect.right>=plat.rect.left && hero.rect.right<=plat.rect.right)) {
            if(hero.sy-plat.sy>0 && hero.rect.bottom>=plat.y && hero.lastRect.bottom<plat.y) {
                r = true;
            }
        }
        return r;
    }

    public static boolean testFly(Hero hero, Plat plat) {
        if(hero.virtualized || plat.virtualized)
            return true;

        boolean r = true;
        if((hero.rect.left>=plat.x && hero.rect.left<=plat.rect.right) || (hero.rect.right>=plat.x && hero.rect.right<=plat.rect.right)) {
            if(hero.rect.bottom == plat.rect.top)
                r = false;
        }
        return r;
    }

    public static boolean testMoveUp(Hero hero, Plat plat) {
        if(hero.virtualized || plat.virtualized)
            return false;

        boolean r = false;
        if((hero.rect.left>=plat.x && hero.rect.left<=plat.rect.right) || (hero.rect.right>=plat.x && hero.rect.right<=plat.rect.right)) {
            if(hero.sy-plat.sy<0 && hero.rect.top<=plat.rect.bottom && hero.lastRect.top>=plat.rect.bottom) {
                r = true;
            }
        }
        return r;
    }

    public static boolean testMoveLeft(Hero hero, Plat plat) {
        if(hero.virtualized || plat.virtualized)
            return false;

        boolean r = false;
        if((hero.rect.top>=plat.rect.top && hero.rect.top<=plat.rect.bottom) || (hero.rect.bottom>=plat.y && hero.rect.bottom<=plat.rect.bottom)) {
            if(plat.rect.right>=hero.rect.left && plat.rect.right<hero.rect.right && hero.lastRect.left>=plat.rect.right) {
                r = true;
            }
        }
        return r;
    }

    public static boolean testMoveRight(Hero hero, Plat plat) {
        if(hero.virtualized || plat.virtualized)
            return false;

        boolean r = false;
        if((hero.rect.top>=plat.rect.top && hero.rect.top<=plat.rect.bottom) || (hero.rect.bottom>=plat.y && hero.rect.bottom<=plat.rect.bottom)) {
            if(plat.x>hero.rect.left && plat.x<=hero.rect.right && plat.x>hero.lastRect.right) {
                r = true;
            }
        }
        return r;
    }
}
