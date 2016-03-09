package demo.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;

/**
 * Created by robson.andrade on 08/03/2016.
 *
 * curl -u my-client-id:secret localhost:8080/oauth/token -d "grant_type=client_credentials&scope=read%20write"
 *
 */
@Configuration /**Indica pro spring que esta classe terá configurações*/
@EnableAuthorizationServer /** Habilita o oauth2*/
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{

    @Autowired
    private DataSource dataSource;

    @Autowired
    private RedisConnectionFactory cf;

    @Bean
    public TokenStore tokenStore(){
        return new RedisTokenStore(cf);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(this.dataSource);
    }

    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(this.tokenStore());
    }

    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.passwordEncoder(this.passwordEncoder());
    }

//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory().withClient("my-client-id")
//                .secret("secret")
//                .accessTokenValiditySeconds(300)
//                .authorities("ROLE_CLIENT")
//                .authorizedGrantTypes("client_credentials")
//                .scopes("read","write");
//    }

}
