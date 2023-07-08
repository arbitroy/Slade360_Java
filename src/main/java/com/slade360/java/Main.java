
package com.slade360.java;
import static spark.Spark.*;

public class Main {
    static Token token;
    public static void main(String[] args) {
        Token.getToken(new TokenCallback() {
            @Override
            public void onTokenReceived(String token) {
                // Set up the Spark GET route
                get("/greeting", (req, res) -> {
                    return "Hello World! from our Spark REST API. Token: " + token;
                });
            }
        });

    }
}
