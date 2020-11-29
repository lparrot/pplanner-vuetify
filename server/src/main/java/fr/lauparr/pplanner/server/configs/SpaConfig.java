package fr.lauparr.pplanner.server.configs;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.TransformedResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class SpaConfig implements WebMvcConfigurer {

  private static final String PATH_PATTERNS = "/**";
  private static final String FRONT_CONTROLLER = "index.html";
  private static final String CONTEXT_PATH_PLACEHOLDER = "#context-path#";
  private static final String FRONT_CONTROLLER_ENCODING = StandardCharsets.UTF_8.name();
  private final String contextPath;
  private final WebProperties.Resources resourceProperties;

  @Value("${app.api.prefix}")
  private String apiPrefix;

  public SpaConfig(@Value("${server.servlet.context-path:/}") String contextPath, WebProperties.Resources resourceProperties) {
    this.contextPath = contextPath;
    this.resourceProperties = resourceProperties;
  }

  @Override
  public void addViewControllers(ViewControllerRegistry pRegistry) {
    pRegistry.addViewController("/").setViewName("forward:/" + SpaConfig.FRONT_CONTROLLER);
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler(SpaConfig.PATH_PATTERNS)
      .addResourceLocations(this.resourceProperties.getStaticLocations())
      .resourceChain(true)
      .addResolver(new SinglePageAppResourceResolver());
  }

  private class SinglePageAppResourceResolver extends PathResourceResolver {

    public static final String URL_SEPARATOR = "/";

    private TransformedResource transformedResource(Resource resource) throws IOException {
      String fileContent = IOUtils.toString(resource.getInputStream(), SpaConfig.FRONT_CONTROLLER_ENCODING);
      fileContent = fileContent.replace(SpaConfig.CONTEXT_PATH_PLACEHOLDER, SpaConfig.this.contextPath + SinglePageAppResourceResolver.URL_SEPARATOR);
      return new TransformedResource(resource, fileContent.getBytes());
    }

    @Override
    protected Resource getResource(String resourcePath, Resource location) throws IOException {
      Resource resource = location.createRelative(resourcePath);
      if (resource.exists() && resource.isReadable()) {
        // if the asked resource is index.html, we serve it with the base-href rewritten
        if (resourcePath.contains(SpaConfig.FRONT_CONTROLLER)) {
          return this.transformedResource(resource);
        }

        return resource;
      }

      // do not serve a Resource on an reserved URI
      if ((SinglePageAppResourceResolver.URL_SEPARATOR + resourcePath).startsWith(SpaConfig.this.apiPrefix)) {
        return null;
      }

      // we have just refreshed a page, no ?
      resource = location.createRelative(SpaConfig.FRONT_CONTROLLER);
      if (resource.exists() && resource.isReadable()) {
        return this.transformedResource(resource);
      }

      return null;
    }
  }

}
