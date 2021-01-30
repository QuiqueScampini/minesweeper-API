package com.deviget.minesweeper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {

	@Bean
	public Docket getApiInfo() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.deviget.minesweeper.controller"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(this.metaData());
	}

	private ApiInfo metaData() {
		return new ApiInfoBuilder()
				.title("Minesweeper API")
				.description("Minesweeper Api Code Challenge")
				.contact(this.contact())
				.build();
	}

	private Contact contact() {
		return new Contact("Enrique Scampini",
				"https://github.com/QuiqueScampini/minesweeper-API",
				"enriquescampini@gmail.com");
	}
}
