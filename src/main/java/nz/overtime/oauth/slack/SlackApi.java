package nz.overtime.oauth.slack;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.extractors.AccessTokenExtractor;
import com.github.scribejava.core.model.OAuthConfig;
import com.github.scribejava.core.model.OAuthConstants;
import com.github.scribejava.core.utils.OAuthEncoder;
import com.github.scribejava.core.utils.Preconditions;

/**
 * @author Furze
 */
public class SlackApi extends DefaultApi20 {
    private static final String AUTHORIZE_URL = "https://slack.com/oauth/authorize?client_id=%s&scope=%s";


    @Override
    public String getAccessTokenEndpoint() {
        return "https://slack.com/api/oauth.access";
    }

    @Override
    public String getAuthorizationUrl(OAuthConfig config) {
        Preconditions.checkValidUrl(config.getCallback(), "Must provide a valid url as callback");
        final StringBuilder sb = new StringBuilder(String.format(AUTHORIZE_URL, config.getApiKey(), OAuthEncoder.encode(config.getScope())));


        if (!config.getCallback().isEmpty()) {
            sb.append('&').append(OAuthConstants.REDIRECT_URI).append('=').append(OAuthEncoder.encode(config.getCallback()));
        }

        if (!config.getState().isEmpty()) {
            sb.append('&').append(OAuthConstants.STATE).append('=').append(OAuthEncoder.encode(config.getState()));
        }

        //Needs Team
        return sb.toString();
    }

    @Override
    public AccessTokenExtractor getAccessTokenExtractor() {
        return new SlackTokenExtractor();
    }
}
