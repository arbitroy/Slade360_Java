
package com.slade360.java;
import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {
        Token.getToken(token -> {
            // Set up the Spark GET route
            get("/greeting", (req, res) -> {
                return "Hello World! from our Spark REST API. Token: " + token;
            });
        });

    }
}
