package SMS_Shared.Swagger;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("M2D").version("V.2.0"))
// Components section defines Security Scheme "mySecretHeader"
                .components(new Components()
                        .addSecuritySchemes("TenantId", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name("X-TenantID")))
// AddSecurityItem section applies created scheme globally
                .addSecurityItem(new SecurityRequirement().addList("TenantId"));
    }
}

