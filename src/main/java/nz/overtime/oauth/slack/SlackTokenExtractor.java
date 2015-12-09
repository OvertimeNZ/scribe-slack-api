package nz.overtime.oauth.slack;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.core.exceptions.OAuthException;
import com.github.scribejava.core.extractors.AccessTokenExtractor;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.utils.Preconditions;
import java.io.IOException;

/**
 * @author Furze
 */

public class SlackTokenExtractor implements AccessTokenExtractor {

    public SlackTokenExtractor() {
    }
    public Token extract(String response) {
        Preconditions.checkEmptyString(response, "Response body is incorrect. Can\'t extract a token from an empty string");
         ObjectMapper mapper = new ObjectMapper();
         try{
             SlackTokenResponse slackTokenResponse =  mapper.readValue(response, SlackTokenResponse.class);
             return new Token(slackTokenResponse.getAccessToken(), "", response);
         } catch (IOException ioe){
             ioe.printStackTrace();
             throw new OAuthException("Response body is incorrect. Can\'t extract a token from this: \'" + response + "\'", (Exception)null);
         }
    }
}
class SlackTokenResponse {

    private String ok;
    private String access_token;
    private String scope;
    private String team_name;
    private String team_id;

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }

    public String getAccessToken() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }
}

