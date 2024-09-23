package fileManagment.file.cacheConfig;

import fileManagment.file.cache.CacheStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CatchConfiguration {
    @Bean
    CacheStore<String,Integer> userCache(){
        return new CacheStore<>(900, TimeUnit.SECONDS);
    }
}
