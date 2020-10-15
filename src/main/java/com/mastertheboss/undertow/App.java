package com.mastertheboss.undertow;


/*public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
}*/


import io.undertow.Undertow;
import io.undertow.server.*;
import io.undertow.util.Headers;

public class App {


    public static void main(final String[] args) {
        Undertow server = Undertow.builder().addHttpListener(8080, "localhost")
                .setHandler(new HttpHandler() {

                    public void handleRequest(final HttpServerExchange exchange)
                            throws Exception {
                        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                        exchange.getResponseSender().send("Hello World 3");
                    }
                }).build();



        server.start();
    }
}