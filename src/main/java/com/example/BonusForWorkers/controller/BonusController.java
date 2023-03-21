package com.example.BonusForWorkers.controller;

import com.example.BonusForWorkers.service.WorkerService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@RestController
@RequestMapping("/bonus")
public class BonusController {


    private final WorkerService workerService;

    public BonusController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        workerService.uploadWorker(file);
        return ResponseEntity.ok("created");
    }


    @GetMapping("/download")
    public ResponseEntity<Resource> download() {
        File file = new File("result.txt");

        return ResponseEntity.ok()
                .headers(fetchHeaders())
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(workerService.downloadWorker(file));
    }

    private HttpHeaders fetchHeaders() {
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bonus.txt");
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        return header;
    }
}

