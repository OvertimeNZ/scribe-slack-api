package nz.overtime.oauth.examples;

import java.util.Random;
import java.util.Scanner;
import nz.overtime.oauth.slack.SlackApi;
import com.github.scribejava.core.builder.ServiceBuilder;;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.model.Verifier;
import com.github.scribejava.core.oauth.OAuthService;

/**
 * @author Furze
 */

public class SlackExample {

    private static final String NETWORK_NAME = "Slack";
    private static final Token EMPTY_TOKEN = null;

    // Replace these with your client id and secret
    private static final String clientId = "";
    private static final String clientSecret = "";

    public static void main(String[] args) {

        final String secretState = "secret" + new Random().nextInt(999_999);
        final OAuthService service = new ServiceBuilder()
                .provider(SlackApi.class)
                .apiKey(clientId)
                .apiSecret(clientSecret)
               // .state(secretState)
                .scope("users:read")
                .callback("http://www.example.com/oauth_callback/")
                .build();
        final Scanner in = new Scanner(System.in, "UTF-8");

        System.out.println("=== " + NETWORK_NAME + "'s OAuth Workflow ===");
        System.out.println();

        // Obtain the Authorization URL
        System.out.println("Fetching the Authorization URL...");
        final String authorizationUrl = service.getAuthorizationUrl(EMPTY_TOKEN);
        System.out.println("Got the Authorization URL!");
        System.out.println("Now go and authorize ScribeJava here:");
        System.out.println(authorizationUrl);
        System.out.println("And paste the authorization code here");
        System.out.print(">>");
        final Verifier verifier = new Verifier(in.nextLine());
        System.out.println();

        System.out.println("And paste the state from server here. We have set 'secretState'='" + secretState + "'.");
        System.out.print(">>");
        final String value = in.nextLine();
        if (secretState.equals(value)) {
            System.out.println("State value does match!");
        } else {
            System.out.println("Ooops, state value does not match!");
            System.out.println("Expected = " + secretState);
            System.out.println("Got      = " + value);
            System.out.println();
        }

        // Trade the Request Token and Verfier for the Access Token
        System.out.println("Trading the Request Token for an Access Token...");
        final Token accessToken = service.getAccessToken(EMPTY_TOKEN, verifier);
        System.out.println("Got the Access Token!");
        System.out.println("(if your curious it looks like this: " + accessToken.getToken() + " )");
        System.out.println();

        // All we need is the token, the rest is done by https://github.com/OvertimeNZ/java-slack-api as it is a lot nicer to use.
    }
}
