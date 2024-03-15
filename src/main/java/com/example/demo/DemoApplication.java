package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.server.DefaultServerOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationEntryPoint;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;

@SpringBootApplication
@Configuration
@EnableWebSecurity
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@Bean
	 ClientRegistrationRepository clientRegistrationRepository() {
		return new InMemoryClientRegistrationRepository(this.googleClientRegistration(),this.facebookClientRegistration());
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(authorize->authorize
					.requestMatchers("/login").permitAll()
					.anyRequest().authenticated())
					.oauth2Login(oauth2 -> oauth2
							.loginPage("/login")
							.defaultSuccessUrl("/success",true));
		return http.build();
	}
	
	
	private ClientRegistration facebookClientRegistration() {
		return ClientRegistration.withRegistrationId("facebook")
			.clientId("368660299401945")
			.clientSecret("0e8c1cf9221a193582f44751b3abb806")
			.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
			.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
			.redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
			.scope("email", "public_profile")
			.authorizationUri("https://www.facebook.com/dialog/oauth")
			.tokenUri("https://graph.facebook.com/v9.0/oauth/access_token")
			.userInfoUri("https://graph.facebook.com/me")
			.userNameAttributeName("id")
			.clientName("crm-facebook-login")
			.build();
	}
	
	private ClientRegistration googleClientRegistration() {
		return ClientRegistration.withRegistrationId("google")
				.clientId("1038290133762-aoi2ic60efu8de71c8o6oefkqm7gvuot.apps.googleusercontent.com")
				.clientSecret("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")
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
				.scope("openid", "profile", "email", "address", "phone")
				.authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
				.tokenUri("https://www.googleapis.com/oauth2/v4/token")
				.userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
				.userNameAttributeName(IdTokenClaimNames.SUB).jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
				.clientName("crm-google-login").build();
	}

}
