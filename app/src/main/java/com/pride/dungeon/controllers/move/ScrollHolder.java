package com.pride.dungeon.controllers.move;

public class ScrollHolder
{
    float distanceX;
    float distanceY;

    public ScrollHolder() {
    }

    public void set(float dX, float dY) {
        this.distanceX = dX;
        this.distanceY = dY;
    }

    public void clear(){
        this.distanceX = 0;
        this.distanceY = 0;
    }
}