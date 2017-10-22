/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jorge.ninjaturtles.game;

import java.awt.Point;

/**
 *
 * @author Jorge
 */
public class Tile {
    private final Point coords;
    private transient Tile topTile;
    private transient Tile bottomTile;
    private transient Tile rightTile;
    private transient Tile leftTile;
    private boolean hasItem;
    private transient Player player;

    public Tile(Point coords) {
        this.coords = coords;
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

    public boolean hasItem() {
        return hasItem;
    }

    public void setHasItem(boolean hasItem) {
        this.hasItem = hasItem;
    }

    public Point getCoords() {
        return coords;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
