
package com.slade360.java;
import spark.Request;
import spark.Response;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {


            get("/greeting", (req, res) -> {
                final String[] tok = {""};
                    Token.getToken(token -> tok[0] = token );
                    return "Hello World! from our Spark REST API. Token: " + tok[0];
            });

        get("/member-eligibility", (Request request, Response response) -> {
            String memberNumber = request.queryParams("memberNumber");
            String payerSladeCode = request.queryParams("payerSladeCode");

            // Return the response body to the client
            return memberEligibility.getMemberDets(memberNumber,payerSladeCode);
        });
    }
}
