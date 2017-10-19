/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jorge.ninjaturtles;

import javax.websocket.Session;

/**
 *
 * @author Jorge
 */
public class Player extends Entity{
    private String username;
    private Integer points = 0;
    private Session session;

    public Player(String username, Session session) {
        this.username = username;
        this.session = session;
    }

    public Player() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
    
    
}
