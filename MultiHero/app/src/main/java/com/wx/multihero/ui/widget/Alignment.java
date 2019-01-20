package com.wx.multihero.ui.widget;

public class Alignment {
    public static final int HORIZONTAL_CENTER = 0x1;
    public static final int VERTICAL_CENTER = 0x2;
    public static final int CENTER = HORIZONTAL_CENTER|VERTICAL_CENTER;
    private int mValue;
    public Alignment() {
        mValue = CENTER;
    }
    public Alignment(int alignment) {
        mValue = alignment;
    }

    public boolean testFlag(int aligment) {
        return (mValue&aligment)==aligment;
    }

    public void set(int alignment) {
        mValue |= alignment;
    }

    public void unset(int alignment) {
        mValue ^= alignment;
    }

    public void setValue(int alignment) {
        mValue = alignment;
    }

    public boolean hasValue() {
        return mValue!=0;
    }
}
