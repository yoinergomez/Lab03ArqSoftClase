/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udea.ws;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author esteban
 */
@ServerEndpoint("/chat")
public class EchoServer {
    
    /**
     * Creamos la lista de peers para usar el endPoint
     */
    private static final Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());
    
    
    
    /**
     * Adicionar nuevo peer a la sesion
     * @param peer 
     */
    @OnOpen
    public void onOpen(Session peer){
        peers.add(peer);
    }
    
    @OnMessage
    public void onMessage(String message, Session client) throws IOException, EncodeException {
        for(Session peer:peers){
            //Con esto se envian los mensajes a todos los clientes
            peer.getBasicRemote().sendObject(message);
        }
    }
    
    /**
     * Cerramos la conexion del peer
     * @param peer 
     */
    @OnClose
    public void onClose(Session peer){
        peers.remove(peer);
    }
    
}
