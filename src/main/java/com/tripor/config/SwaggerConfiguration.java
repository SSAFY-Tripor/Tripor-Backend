package com.tripor.config;


import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

//Swagger-UI 확인
//http://localhost/swagger-ui.html

//@OpenAPIDefinition(
//	    info = @Info(
//	        title = "SSAFY Board API 명세서",
//	        description = "<h3>SSAFY API Reference for Developers</h3>Swagger를 이용한 Board API<br><img src=\"/assets/img/ssafy_logo.png\" width=\"150\">",
//	        version = "v1",
//	        contact = @Contact(
//	            name = "hissam",
//	            email = "hissam@ssafy.com",
//	            url = "http://edu.ssafy.com"
//	        )
//	    )
//	)

@Configuration
public class SwaggerConfiguration {

	@Bean
	public OpenAPI openAPI() {
		Info info = new Info().title("Tripor API 명세서");
				
				
//				.description(
//				"<h3>SSAFY API Reference for Developers</h3>Swagger를 이용한 Board API<br><img src=\"/assets/img/ssafy_logo.png\" width=\"150\">")
//				.version("v1").contact(new io.swagger.v3.oas.models.info.Contact().name("hissam")
//						.email("hissam@ssafy.com").url("http://edu.ssafy.com"));

		return new OpenAPI().components(new Components()).info(info);
	}

	@Bean
	public GroupedOpenApi articleApi() {
		return GroupedOpenApi.builder().group("tripor-article").pathsToMatch("/article/**").build();
	}

	@Bean
	public GroupedOpenApi memberApi() {
		return GroupedOpenApi.builder().group("tripor-member").pathsToMatch("/member/**").build();
	}
	
	@Bean
	public GroupedOpenApi tripApi() {
		return GroupedOpenApi.builder().group("tripor-trip").pathsToMatch("/trip/**").build();
	}
//	
//	@Bean
//	public GroupedOpenApi fileApi() {
//		return GroupedOpenApi.builder().group("ssafy-file").pathsToMatch("/file/**").build();
//	}

}