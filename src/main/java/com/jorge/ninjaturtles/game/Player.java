/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jorge.ninjaturtles.game;

import java.awt.Point;
import javax.websocket.Session;

/**
 *
 * @author Jorge
 */
public class Player{
    private String username;
    private int points = 0;
    
    private transient Tile position;
    private Point coords;
    
    private transient Session session;

    public Player(String username, Session session, Tile tile) {
        this.position = tile;
        this.coords = tile.getCoords();
        this.username = username;
        this.session = session;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
    
    public Tile grabItem(){
        if(this.position.hasItem()){
            this.position.setHasItem(false);
            this.points += Game.itemPoints;
            return this.position;
        }
        return null;
    }
    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Tile getPosition() {
        return position;
    }

    public void setPosition(Tile position) {
        this.position = position;
        this.coords = this.position.getCoords();
    }

    public Point getCoords() {
        return coords;
    }
    
    public boolean move(String direction){
        switch(direction){
            case "right":
                if(getPosition().getRightTile() != null){
                    getPosition().getRightTile().setPlayer(this);
                    this.getPosition().setPlayer(null);
                    this.setPosition(getPosition().getRightTile());
                    return true;
                }
                break;
            case "left":
                if (getPosition().getLeftTile() != null){
                    getPosition().getLeftTile().setPlayer(this);
                    this.getPosition().setPlayer(null);
                    this.setPosition(getPosition().getLeftTile());
                    return true;
                }
                break;
            case "up":
                if (getPosition().getTopTile() != null){
                    getPosition().getTopTile().setPlayer(this);
                    this.getPosition().setPlayer(null);
                    this.setPosition(getPosition().getTopTile());
                    return true;
                }
                break;
                
            case "down":
                if (getPosition().getBottomTile() != null) {
                    getPosition().getBottomTile().setPlayer(this);
                    this.getPosition().setPlayer(null);
                    this.setPosition(getPosition().getBottomTile());
                    return true;
                }
                break;
            }
        return false;
    }
}
