package fileManagment.file.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class CacheStore <k,v>{
    private final Cache<k,v> cache;

    public CacheStore(int expireDuration, TimeUnit timeUnit) {
        this.cache = CacheBuilder.newBuilder().expireAfterAccess(expireDuration,timeUnit)
                .concurrencyLevel(Runtime.getRuntime().availableProcessors()).build();
    }

    public v get(@NonNull k key){
        log.info("Retrieving from Cache  with key{}", key.toString());
        return cache.getIfPresent(key);
    }
     public void put(@NonNull k key,v value){
         log.info("Storing Records in Cache for key{}", key.toString());
         cache.put(key,value);
     }
     public void evict(@NonNull k key){
        log.info("Removing from Cache with key{}",key.toString());
        cache.invalidate(key);
     }
}
