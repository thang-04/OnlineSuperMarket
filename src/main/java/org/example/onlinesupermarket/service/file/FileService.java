package org.example.onlinesupermarket.service.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String storeFile(MultipartFile file);

}