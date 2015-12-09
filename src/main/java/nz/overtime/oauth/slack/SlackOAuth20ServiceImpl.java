package nz.overtime.oauth.slack;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.model.OAuthConfig;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.oauth.OAuth20ServiceImpl;


public class SlackOAuth20ServiceImpl extends OAuth20ServiceImpl {

    public SlackOAuth20ServiceImpl (DefaultApi20 api, OAuthConfig config) {
        super(api, config);
    }

    public String getAuthorizationUrl(Token requestToken, String team) {
        SlackApi slackApi = (SlackApi)this.getApi();
        slackApi.setTeam(team);
        return super.getAuthorizationUrl(requestToken);
    }
}
