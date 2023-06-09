package com.socialEventManager.backEnd.services;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class FirebaseService {
    private final StorageClient storageClient;

    public FirebaseService(StorageClient storageClient) {
        this.storageClient = storageClient;
    }

    public String uploadImage(MultipartFile imageFile) throws IOException {
        String imageName = generateUniqueImageName();
        BlobInfo blobInfo = storageClient.bucket("jarvin-3cce1.appspot.com").create(imageName, imageFile.getBytes());
        return blobInfo.getMediaLink();
    }

    private String generateUniqueImageName() {
        return UUID.randomUUID().toString();
    }
}
