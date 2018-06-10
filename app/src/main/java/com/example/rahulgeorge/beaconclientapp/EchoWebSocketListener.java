package com.example.rahulgeorge.beaconclientapp;

import android.content.Context;
import android.util.Log;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public final class EchoWebSocketListener extends WebSocketListener {
    private static final int NORMAL_CLOSURE_STATUS = 1000;
    private Context parentcontext;
    public EchoWebSocketListener(){

    }
    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        webSocket.send("Hello, it's SSaurel !");
        webSocket.send("What's up ?");
        webSocket.send(ByteString.decodeHex("deadbeef"));
        webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye !");
    }
    @Override
    public void onMessage(WebSocket webSocket, String text) {
        outputData(text);

    }
    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        output("Receiving bytes : " + bytes.hex());
    }
    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(NORMAL_CLOSURE_STATUS, null);
        output("Closing : " + code + " / " + reason);
    }
    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        output("Error : " + t.getMessage());
    }

    public void output(String input)
    {
        Log.e("Websocket",input);
    }

    public void outputData(String input)
    {
    }
}