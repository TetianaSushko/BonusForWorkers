package com.example.BonusForWorkers.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

public interface WorkerService {
    void uploadWorker(MultipartFile file) throws IOException;

    Resource downloadWorker(File file);
}
