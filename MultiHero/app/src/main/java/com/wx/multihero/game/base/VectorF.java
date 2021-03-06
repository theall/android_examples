package com.wx.multihero.game.base;

public class VectorF implements Cloneable {
    public Item x = new Item();
    public Item y = new Item();

    public enum Type {
        ABSOLUTE,
        RELATIVE
    }

    public class Item {
        public float value;
        public Type type;

        public Item() {
            value = 0;
            type = Type.ABSOLUTE;
        }
        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }

        public void copyFrom(Item item) {
            value = item.value;
            type = item.type;
        }

        public boolean isEmpty() {
            return value==0 && type==Type.RELATIVE;
        }
    }

    public VectorF() {
        this.x.value = 0;
        this.y.value = 0;
    }

    @Override
    protected java.lang.Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public VectorF(float x, float y) {
        this.x.value = x;
        this.y.value = y;
    }

    public boolean isEmpty() {
        return x.isEmpty() && y.isEmpty();
    }

    public void copyFrom(VectorF vector) {
        x.copyFrom(vector.x);
        y.copyFrom(vector.y);
    }

    public void setType(VectorF.Type type) {
        x.type = type;
        y.type = type;
    }

    public void setType(VectorF.Type tx, VectorF.Type ty) {
        x.type = tx;
        y.type = ty;
    }

    public void setValue(float x, float y) {
        this.x.value = x;
        this.y.value = y;
    }
}
