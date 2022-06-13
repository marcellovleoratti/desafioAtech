package br.com.dominio.desafioatech.security;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableOAuth2Sso
public class AuthorizationServerConfig {
    private AuthenticationManager authenticationManager;
    private static final int ACCESS_TOKEN_VALIDITY_IN_SECONDS= 1;
    private static final int REFRESH_TOKEN_VALIDITY_IN_SECONDS =1;

    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
    }

    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        try {
            clients.inMemory()
                    .withClient("client-id")
                    .secret("secret-id")
                    .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
                    .scopes("read", "write", "trust")
                    .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_IN_SECONDS)
                    .refreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY_IN_SECONDS);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        try {
            endpoints.authenticationManager(authenticationManager)
                    .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
