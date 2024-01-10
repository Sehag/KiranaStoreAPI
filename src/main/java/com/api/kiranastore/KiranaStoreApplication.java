package com.api.kiranastore;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;



@SpringBootApplication
@EnableCaching
public class KiranaStoreApplication {

    public static void main(String[] args) {

        /*
        Iterator<CachingProvider> iterator = Caching.getCachingProviders(Caching.getDefaultClassLoader()).iterator();

        while(iterator.hasNext()) {
            CachingProvider provider = iterator.next();
            if (!(provider instanceof EhcacheCachingProvider)) {
                iterator.remove();
            }
        }

         */
        SpringApplication.run(KiranaStoreApplication.class, args);
    }

}
