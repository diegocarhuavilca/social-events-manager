package com.socialEventManager.backEnd.servicesImpl;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class FirebaseService {
    private final StorageClient storageClient;

    public FirebaseService(StorageClient storageClient) {
        this.storageClient = storageClient;
    }

    @Async
    public CompletableFuture<String> uploadImage(MultipartFile imageFile, String directory) throws IOException,IOException, InterruptedException  {

        String imageName = generateUniqueImageName();

        String directoryImage = directory + "/" + imageName;

        storageClient.bucket("jarvin-3cce1.appspot.com").create(directoryImage, imageFile.getInputStream(), imageFile.getContentType());

        return CompletableFuture.completedFuture(getStorageUrl(directoryImage));
    }

    private String generateUniqueImageName() {
        return UUID.randomUUID().toString();
    }

    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }

    private String getStorageUrl(String fileName) {
        return "https://storage.googleapis.com/" + "jarvin-3cce1.appspot.com" + "/" + fileName;
    }

}
