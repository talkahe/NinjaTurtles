
package com.jorge.ninjaturtles;

import com.google.gson.Gson;
import com.jorge.ninjaturtles.game.Game;
import com.jorge.ninjaturtles.game.Player;
import com.jorge.ninjaturtles.game.Tile;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * 
 *
 * @author Jorge
 */
@ServerEndpoint( 
  value="/game/{username}", 
  decoders = MessageDecoder.class, 
  encoders = MessageEncoder.class )
public class GameServer {
    private static Game game = new Game(20, 20);

    public GameServer(){
    }

    @OnMessage
    public void onMessage(Session session, Message message) {
        Player player = game.getPlayerBySession(session);
        switch(message.getAction()){
            case "move":
                if(player.move(message.getData().get("direction").toString())){
                    Tile tile = player.grabItem();
                    if(tile != null){
                        game.getItems().remove(tile);
                        Message removeItemMsg = new Message("removeItem");
                        removeItemMsg.addData("tile", tile);
                        this.broadcast(removeItemMsg);
                    }
                    Message newPlayerMsg = new Message("playerUpdate");
                    newPlayerMsg.addData("player", player);
                    this.broadcast(newPlayerMsg);

                    // Are there items?
                    if(game.getItems().isEmpty()){
                        Message gameOverMsg = new Message("gameOver");
                        gameOverMsg.addData("winner", game.getWinner());
                        broadcast(gameOverMsg);
                        GameServer.game = new Game(20, 20);
                    }
                }
                break;
        }
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        Gson gson = new Gson();    
        System.out.println("User " + username + " connected.");
       
        Player player = game.addPlayer(username, session);
        
        // send players new player info
        Message newPlayerMsg = new Message("playerUpdate");
        newPlayerMsg.addData("player", player);
        this.broadcast(newPlayerMsg, player.getSession());
        
        // send map and players to new player
        Message startMsg = new Message("startingGame");
        startMsg.addData("map", game.getMap());
        startMsg.addData("players", game.getPlayers());
        this.send(startMsg, session);
    }

    @OnError
    public void onError(Throwable t) {
    }

    @OnClose
    public void onClose(Session session) {
        Player player = game.getPlayerBySession(session);
        game.removePlayer(player);
        
        Message newPlayerMsg = new Message("removePlayer");
        newPlayerMsg.addData("player", player);
        this.broadcast(newPlayerMsg);

    }   
    
    private void broadcast(Message message, Session except){
        for(Player player : game.getPlayers()){
            if(except != player.getSession()){
                try {
                    player.getSession().getBasicRemote().sendObject(message);
                } catch (IOException | EncodeException ex) {
                    Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    private void broadcast(Message message){
        for(Player player : game.getPlayers()){
            try {
                player.getSession().getBasicRemote().sendObject(message);
            } catch (IOException | EncodeException ex) {
                Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void send(Message message, Session session){
        try {
            session.getBasicRemote().sendObject(message);
        } catch (IOException | EncodeException ex) {
            Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
