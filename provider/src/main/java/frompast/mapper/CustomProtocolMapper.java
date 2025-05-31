package frompast.mapper;

import org.keycloak.models.*;
import org.keycloak.protocol.oidc.mappers.*;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.representations.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class CustomProtocolMapper extends AbstractOIDCProtocolMapper implements OIDCAccessTokenMapper,
        OIDCIDTokenMapper, UserInfoTokenMapper {
    public static final String PROVIDER_ID = "custom-protocol-mapper";
    private static final Logger log = LoggerFactory.getLogger(CustomProtocolMapper.class);

    private static final List<ProviderConfigProperty> configProperties = new ArrayList<>();

    static {
        OIDCAttributeMapperHelper.addTokenClaimNameConfig(configProperties);
        OIDCAttributeMapperHelper.addIncludeInTokensConfig(configProperties, CustomProtocolMapper.class);
    }

    @Override
    public String getDisplayCategory() {
        return "Token Mapper";
    }

    @Override
    public String getDisplayType() {
        return "Custom Token Mapper";
    }

    @Override
    public String getHelpText() {
        return "Adds a Help text to the claim";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return configProperties;
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public AccessToken transformAccessToken(AccessToken token, ProtocolMapperModel mappingModel, KeycloakSession keycloakSession,
                                            UserSessionModel userSession, ClientSessionContext clientSessionCtx) {
        String userSessionId = userSession.getUser().getId();
        log.info(userSessionId);
        UserModel userModel = keycloakSession.users().getUserById(keycloakSession.getContext().getRealm(), userSession.getUser().getId());
        String userId = userModel.getFirstAttribute("LDAP_ID"); //user_guid
        log.info(userId);
        String encode = keycloakSession.tokens().encode(token);
        String roles = FetchService.fetchRolesForUser(UUID.fromString(userId), encode);

        token.getOtherClaims().put("custom_roles", roles);
        setClaim(token, mappingModel, userSession, keycloakSession, clientSessionCtx);
        return token;
    }
}