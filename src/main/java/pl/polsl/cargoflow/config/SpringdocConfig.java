package pl.polsl.cargoflow.config;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;

@Configuration
@SecurityScheme(
  type = SecuritySchemeType.HTTP,
  name = "basicAuth",
  scheme = "basic")
public class SpringdocConfig {}
