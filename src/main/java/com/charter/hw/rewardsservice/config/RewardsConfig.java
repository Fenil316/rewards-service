package com.charter.hw.rewardsservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class RewardsConfig {

    @Value("${api.title}")
    private String apiTitle;

    @Value("${api.description}")
    private String apiDescription;

    @Value("${api.terms-url}")
    private String apiTermsUrl;

    @Value("${api.license.name}")
    private String apiLicense;

    @Value("${api.license.url}")
    private String apiLicenseURL;

    @Value("${api.version}")
    private String apiVersion;

    @Bean
    public Docket apiConfig() {
        return new Docket(DocumentationType.SWAGGER_2).groupName(apiTitle)
                .apiInfo(getRewardsInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.charter.hw.rewardsservice"))
                .paths(PathSelectors.regex("/rewards.*"))
                .build()
                .useDefaultResponseMessages(false);
    }

    private ApiInfo getRewardsInfo() {
        return new ApiInfoBuilder().title(apiTitle)
                .description(apiDescription)
                .termsOfServiceUrl(apiTermsUrl)
                .license(apiLicense)
                .licenseUrl(apiLicenseURL)
                .version(apiVersion)
                .build();
    }
}
