package com.saludvital.mssaludvital.config;

import com.saludvital.mssaludvital.security.JwtAuthenticationEntryPoint;
import com.saludvital.mssaludvital.security.JwtAuthenticationFilter;
import com.saludvital.mssaludvital.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Allow Angular development server
        configuration.setAllowedOrigins(Arrays.asList(
            "http://localhost:4200", 
            "http://127.0.0.1:4200"
        ));
        
        // Allow all HTTP methods
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        
        // Allow all headers
        configuration.setAllowedHeaders(List.of("*"));
        
        // Allow credentials (cookies, authorization headers)
        configuration.setAllowCredentials(true);
        
        // Expose Authorization header so frontend can read it
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Content-Type"));
        
        // Cache preflight response for 1 hour
        configuration.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(AbstractHttpConfigurer::disable)
            .exceptionHandling(ex -> ex.authenticationEntryPoint(unauthorizedHandler))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authz -> authz
                // Public endpoints (sin /api porque ya está en context-path)
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/public/**").permitAll()
                // Swagger endpoints
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/swagger-ui.html").permitAll()
                .requestMatchers("/api-docs/**").permitAll()
                
                // Admin endpoints - Solo ADMIN
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/admin/pacientes").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/admin/pacientes/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/admin/pacientes/*").hasRole("ADMIN")
                
                // Médico endpoints - Solo MEDICO (algunos también ADMIN)
                .requestMatchers("/medico/perfil").hasRole("MEDICO")
                .requestMatchers("/medico/disponibles").permitAll() // Público para que pacientes vean médicos
                .requestMatchers("/medico/{id}").authenticated() // Cualquier usuario autenticado
                .requestMatchers("/medico/**").hasAnyRole("ADMIN", "MEDICO")
                
                // Paciente endpoints - Solo PACIENTE
                .requestMatchers("/paciente/**").hasRole("PACIENTE")
                
                // Citas endpoints - Según funcionalidad
                .requestMatchers("/citas/nueva").hasRole("PACIENTE") // Solo pacientes pueden crear citas
                .requestMatchers("/citas/mis-citas").hasRole("PACIENTE") // Solo pacientes ven sus citas
                .requestMatchers("/citas/medico").hasRole("MEDICO") // Solo médicos ven sus citas
                .requestMatchers("/citas/*/completar").hasAnyRole("MEDICO", "ADMIN") // Solo médicos/admin completan
                .requestMatchers("/citas/*/cancelar").hasAnyRole("PACIENTE", "ADMIN") // Pacientes/admin cancelan
                .requestMatchers("/citas/*").hasAnyRole("PACIENTE", "ADMIN") // Modificar citas
                .requestMatchers("/citas").hasRole("ADMIN") // Solo admin ve todas las citas
                
                // Recetas endpoints - Según funcionalidad
                .requestMatchers("/recetas/nueva").hasRole("MEDICO") // Solo médicos emiten recetas
                .requestMatchers("/recetas/mis-recetas").hasRole("PACIENTE") // Solo pacientes ven sus recetas
                .requestMatchers("/recetas/medico").hasRole("MEDICO") // Solo médicos ven sus recetas emitidas
                .requestMatchers("/recetas").hasRole("ADMIN") // Solo admin ve todas las recetas
                .requestMatchers("/recetas/*").hasAnyRole("PACIENTE", "MEDICO", "ADMIN") // Ver receta específica
                
                // Medicamentos endpoints - Según método HTTP
                .requestMatchers(HttpMethod.GET, "/medicamentos/**").hasAnyRole("MEDICO", "ADMIN", "PACIENTE") // Todos pueden consultar
                .requestMatchers(HttpMethod.POST, "/medicamentos").hasRole("ADMIN") // Solo admin crea
                .requestMatchers(HttpMethod.PUT, "/medicamentos/*").hasRole("ADMIN") // Solo admin modifica
                .requestMatchers(HttpMethod.DELETE, "/medicamentos/*").hasRole("ADMIN") // Solo admin elimina
                
                // All other requests need authentication
                .anyRequest().authenticated()
            );

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}