package com.qualitest.qualithon;
import java.net.URI;
import java.util.Map;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

/**
 *  This class makes Web socket connection to a server.
 */
public class WSClient extends WebSocketClient {

  public String token= "";
  public WSClient(URI serverUri, Draft draft) {
    super(serverUri, draft);
  }

  public WSClient(URI serverURI) {
    super(serverURI);
  }

  public WSClient(URI serverUri, Map<String, String> httpHeaders) {
    super(serverUri, httpHeaders);
  }

  @Override
  public void onOpen(ServerHandshake handshakedata) {
    send("Hello, it is me. Mario :)");

  }

  @Override
  public void onMessage(String message) {
    System.out.println("received: " + message);
    token=message;
  }

  @Override
  public void onClose(int code, String reason, boolean remote) {
    System.out.println(
        "Connection closed by " + (remote ? "remote peer" : "us") + " Code: " + code + " Reason: "
            + reason);
  }

  @Override
  public void onError(Exception ex) {
    ex.printStackTrace();
  }



}
