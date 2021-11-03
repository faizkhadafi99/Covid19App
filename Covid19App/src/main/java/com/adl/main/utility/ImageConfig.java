package com.adl.main.utility;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ImageConfig implements WebMvcConfigurer {

	
	@Override
	public void addResourceHandlers (ResourceHandlerRegistry registry) {
		exposeDirectory (registry, "prevention-image");
		
	}
	
	private void exposeDirectory (ResourceHandlerRegistry registry, String directori) {
		Path uploadDirPath = Paths.get(directori);
		String uploadPath = uploadDirPath.toFile().getAbsolutePath();
		
		if (directori.startsWith("../")) {
			directori =directori.replace("../","");
		}
		
		registry.addResourceHandler("/"+directori+"/**").addResourceLocations("file:/"+ uploadPath +"/");
		}
	
}

