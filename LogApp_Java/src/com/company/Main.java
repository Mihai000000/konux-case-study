package com.company;

import com.sun.net.httpserver.HttpServer;


import java.io.IOException;
import java.net.InetSocketAddress;


public class Main {

    public static void main(String[] args) {
        // write your code here


        Main.createServer();
        JsonProcesser.demoSendPost();

        try {
            Thread.sleep(20000);
            JsonProcesser.demoSendPost();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static void createServer() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress( 8081), 0);
            server.createContext("/requests", new JsonProcesser());
            server.setExecutor(null); // creates a default executor
            ((Runnable) () -> server.start()).run();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}

