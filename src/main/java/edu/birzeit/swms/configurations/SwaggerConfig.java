package edu.birzeit.swms.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static com.google.common.base.Predicates.or;


@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket swaggerConfiguration() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(or(PathSelectors.regex("/bins.*"),
                        PathSelectors.regex("/areas.*"),
                        PathSelectors.regex("/employees.*")))
                .apis(RequestHandlerSelectors.basePackage("edu.birzeit"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("SWMS API", "Iot-Based Smart Waste Management System", "1.0", "Terms os Service", new Contact("layth abufarhah", "laythabufarhah", "laythabufarhah@gmail.com"), "Apache-2.0 License ", "http://www.apache.org/licenses/LICENSE-2.0", Collections.emptyList());
    }

}
