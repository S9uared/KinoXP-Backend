package dat3.security.config;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.proc.SecurityContext;
import dat3.security.error.CustomOAuth2AccessDeniedHandler;
import dat3.security.error.CustomOAuth2AuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)

@Configuration
public class SecurityConfig {

  //Remove default value below BEFORE deployment
  @Value("${app.secret-key}")
  private String tokenSecret;

  @Autowired
  CorsConfigurationSource corsConfigurationSource;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
    MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
    http
            .cors(Customizer.withDefaults()) //Will use the CorsConfigurationSource bean declared in CorsConfig.java
            .csrf(csrf -> csrf.disable())  //We can disable csrf, since we are using token based authentication, not cookie based
            .httpBasic(Customizer.withDefaults())
            .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .oauth2ResourceServer((oauth2ResourceServer) ->
                    oauth2ResourceServer
                            .jwt((jwt) -> jwt.decoder(jwtDecoder())
                                    .jwtAuthenticationConverter(authenticationConverter())
                            )
                            .authenticationEntryPoint(new CustomOAuth2AuthenticationEntryPoint())
                            .accessDeniedHandler(new CustomOAuth2AccessDeniedHandler()));
    
    http.authorizeHttpRequests((authorize) -> authorize
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/api/auth/login")).permitAll()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/api/user-with-role")).permitAll() //Clients can create a user for themself


            //Allow index.html and everything else on root level. So make sure to put ALL your endpoints under /api
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET,"/*")).permitAll()
            .requestMatchers(mvcMatcherBuilder.pattern("/error")).permitAll()

            // MOVIECONTROLLER
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/movies")).permitAll()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/movies/imdbid/{imdbId}")).permitAll()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/api/movies/{imdbId}")).hasAuthority("ADMIN")

            // RESERVATIONCONTROLLER
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/reservations/{phoneNumber}")).hasAuthority("USER")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/reservations/showing/{showingId}")).hasAuthority("USER")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/reservations/reservation/{reservationId}")).hasAuthority("USER")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/api/reservations")).permitAll()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.DELETE, "/api/reservations/{reservationId}")).hasAuthority("USER")

            // SEATCONTROLLER
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/seats/theater/{id}")).permitAll()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/seats/{id}")).hasAuthority("ADMIN")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/seats/{id}/{type}")).hasAuthority("ADMIN")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/api/seats")).hasAuthority("ADMIN")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.PUT, "/api/seats/{id}")).hasAuthority("ADMIN")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.DELETE, "/api/seats/{id}")).hasAuthority("ADMIN")

            // SHOWINGCONTROLLER
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/showings")).permitAll()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/showings/date/{date}")).permitAll()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/showings/{id}")).permitAll()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/api/showings")).hasAuthority("ADMIN")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.PUT, "/api/showings/{id}")).hasAuthority("ADMIN")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.DELETE, "/api/showings/{id}")).hasAuthority("ADMIN")

            // STATISTICCONTROLLER
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/statistics")).hasAuthority("ADMIN")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/statistics/movie/{id}")).hasAuthority("ADMIN")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/api/statistics")).hasAuthority("ADMIN")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.DELETE, "/api/statistics/{id}")).hasAuthority("ADMIN")

            // THEATERCONTROLLER
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/theaters")).hasAuthority("ADMIN")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/theaters/{id}")).permitAll()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/api/theaters")).hasAuthority("ADMIN")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.PUT, "/api/theaters/{id}")).hasAuthority("ADMIN")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.DELETE, "/api/theaters/{id}")).hasAuthority("ADMIN")



            //.requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/test/user-only")).hasAuthority("USER")
            //.requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/test/admin-only")).hasAuthority("ADMIN")

            //Use this to completely disable security (Will not work if endpoints has been marked with @PreAuthorize)
            //.requestMatchers(mvcMatcherBuilder.pattern("/**")).permitAll());
            .anyRequest().authenticated());

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public JwtAuthenticationConverter authenticationConverter() {
    JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
    jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");

    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
    return jwtAuthenticationConverter;
  }

  @Bean
  public SecretKey secretKey() {
    return new SecretKeySpec(tokenSecret.getBytes(), "HmacSHA256");
  }

  @Bean
  public JwtDecoder jwtDecoder() {
    return NimbusJwtDecoder.withSecretKey(secretKey()).build();
  }

  @Bean
  public JwtEncoder jwtEncoder() {
    return new NimbusJwtEncoder(
            new ImmutableSecret<SecurityContext>(secretKey())
    );
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
          throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
}