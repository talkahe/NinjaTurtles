/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jorge.ninjaturtles;

/**
 *
 * @author Jorge
 */
public class Tile {
    private Tile topTile;
    private Tile bottomTile;
    private Tile rightTile;
    private Tile leftTile;
    private boolean isSolid;

    public Tile(Tile topTile, Tile bottomTile, Tile rightTile, Tile leftTile, boolean isSolid) {
        this.topTile = topTile;
        this.bottomTile = bottomTile;
        this.rightTile = rightTile;
        this.leftTile = leftTile;
        this.isSolid = isSolid;
    }

    public Tile() {
    }

    public Tile getTopTile() {
        return topTile;
    }

    public void setTopTile(Tile topTile) {
        this.topTile = topTile;
    }

    public Tile getBottomTile() {
        return bottomTile;
    }

    public void setBottomTile(Tile bottomTile) {
        this.bottomTile = bottomTile;
    }

    public Tile getRightTile() {
        return rightTile;
    }

    public void setRightTile(Tile rightTile) {
        this.rightTile = rightTile;
    }

    public Tile getLeftTile() {
        return leftTile;
    }

    public void setLeftTile(Tile leftTile) {
        this.leftTile = leftTile;
    }

    public boolean isIsSolid() {
        return isSolid;
    }

    public void setIsSolid(boolean isSolid) {
        this.isSolid = isSolid;
    }
    
    
}
