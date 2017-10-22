/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jorge.ninjaturtles;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Jorge
 */
public class Message {
    private String action;
    private Map<String, Object> data = new HashMap<>();

    public Message(String action) {
        this.action = action;
    }
    
    public Message(String action, Map<String, Object> data) {
        this.action = action;
        this.data = data;
    }

    public Message() {
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
    
    public void addData(String key, Object value){
        this.data.put(key, value);
    }

}
