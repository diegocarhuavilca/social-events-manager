package com.socialEventManager.backEnd.configs;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {
    private final ResourceLoader resourceLoader;

    @Autowired
    public FirebaseConfig(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:firebase_auth.json");
        File file = resource.getFile();
        FileInputStream serviceAccount = new FileInputStream(file);

        System.out.println(serviceAccount);

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        return FirebaseApp.initializeApp(options);
    }

    @Bean
    public StorageClient storage(FirebaseApp firebaseApp) {
        return StorageClient.getInstance(firebaseApp);
    }
}