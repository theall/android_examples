package com.wx.multihero.variability.collide;

import android.graphics.RectF;

import com.wx.multihero.variability.hero.Hero;
import com.wx.multihero.variability.object.Plat;

public class Collide {

    public static boolean test(RectF r1, RectF r2) {
        return r1.intersect(r2);
    }

    public static boolean test(Hero hero, Plat plat) {
        RectF footRect = hero.getFootRect();
        boolean r = false;
        if((footRect.left>=plat.x && footRect.left<=plat.right) || (footRect.right>=plat.x && footRect.right<=plat.right)) {
            if(footRect.bottom>=plat.y) {
                r = true;
            }
        }
        return r;
    }
}
