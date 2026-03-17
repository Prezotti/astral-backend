package astral.astralbackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class LocalStorageConfig implements WebMvcConfigurer {

    @Value("${application.storage.local:true}")
    private boolean storageLocal;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (storageLocal) {
            Path uploadsPath = Paths.get("uploads").toAbsolutePath().normalize();
            String location = "file:" + uploadsPath + "/";
            registry.addResourceHandler("/uploads/**")
                    .addResourceLocations(location);
        }
    }
}
