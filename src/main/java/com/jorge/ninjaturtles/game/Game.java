/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jorge.ninjaturtles.game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.websocket.Session;

/**
 *
 * @author Jorge
 */
public class Game {
    private int width;
    private int height;
    
    private final List<List<Tile>> map;
    private boolean[][] mazeBlueprint;
    private final List<Tile> tileList;
    
    private final List<Player> players;
    private int visionArea;
    
    private final List<Tile> items;
    private final int itemProbability;
    public static int itemPoints;

    public Game(int width, int height) {
        Game.itemPoints = 10;
        this.width = width;
        this.height = height;
        this.tileList = new ArrayList<>();
        
        this.items = new ArrayList<>();
        this.players = new ArrayList<>();
        this.itemProbability = 13; //%
        
        this.map = new ArrayList<>();
        this.buildMaze();        
    }    

    public final void buildMaze() {

        MazeGenerator mazeGenerator = new MazeGenerator(this.width, this.height);
        this.mazeBlueprint = mazeGenerator.createMaze();
        
        for (int x = 0; x < this.width; x++) {
            this.map.add(new ArrayList());
            for (int y = 0; y < this.height; y++) {
                if (!this.mazeBlueprint[x][y]) {
                    Tile tile = new Tile(new Point(x,y));
                    this.map.get(x).add(tile);
                    this.tileList.add(tile);

                    //Add items
                    int addItem = new Random().nextInt(this.itemProbability);
                    if (addItem == 0) {
                        tile.setHasItem(true);
                        this.items.add(tile);
                    }
                } else {
                    this.map.get(x).add(null);
                }
            }
        }

        // Set neiberghound
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                Tile tile = this.map.get(x).get(y);
                if (tile != null) {
                    if (x > 0 && this.mazeBlueprint[x - 1][y] == false) {
                        tile.setTopTile(this.map.get(x - 1).get(y));
                    }
                    if (x < this.map.size() - 1 && this.mazeBlueprint[x + 1][y] == false) {
                        tile.setBottomTile(this.map.get(x + 1).get(y));
                    }
                    if (y > 0 && this.mazeBlueprint[x][y - 1] == false) {
                        tile.setLeftTile(this.map.get(x).get(y - 1));
                    }
                    if (y < this.map.get(x).size() - 1 && this.mazeBlueprint[x][y + 1] == false) {
                        tile.setRightTile(this.map.get(x).get(y + 1));
                    }
                }
            }
        }
    }
    
    public Player addPlayer(String username, Session session) {
        int index = new Random().nextInt(this.getTileList().size() - 1);
        Tile tile = this.getTileList().get(index);
        
        Player player = new Player(username, session, tile);
        tile.setPlayer(player);
        
        this.players.add(player);
        return player;
    }
    
    public void removePlayer(Player player) {
        this.players.remove(player);
    }
    
    public Player getPlayerBySession(Session session){
        for (Player player : this.getPlayers()) {
            if (player.getSession() == session) {
                return player;
            }
        }
        return null;
    }
    
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getVisionArea() {
        return visionArea;
    }

    public void setVisionArea(int visionArea) {
        this.visionArea = visionArea;
    }

    public int getItemProbability() {
        return itemProbability;
    }

    public List<List<Tile>> getMap() {
        return map;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Tile> getItems() {
        return items;
    }
    
    public List<Tile> getTileList(){
        return tileList;
    }

    public boolean[][] getMazeBlueprint() {
        return mazeBlueprint;
    }
    
    public Player getWinner(){
        Player tempWinner = null;
        for(Player player: this.getPlayers()){
            if(tempWinner == null){
                tempWinner = player;
            }else if(tempWinner.getPoints() < player.getPoints()){
                tempWinner = player;
            }
        }
        return tempWinner;
    }

}
