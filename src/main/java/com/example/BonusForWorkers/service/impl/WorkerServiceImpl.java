package com.example.BonusForWorkers.service.impl;

import com.example.BonusForWorkers.service.WorkerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkerServiceImpl implements WorkerService {

    @Value("${years}")
    private int years;

    @Override
    public void uploadWorker(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        List<String> result = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .filter(s -> Integer.parseInt(s.split("\\.")[1]) >= years)
                .sorted(Comparator.comparing(s -> s.split("\\.")[0]))
                .map(s -> s.split("\\.")[0])
                .collect(Collectors.toList());

        String stringResult = String.join(", ", result.toArray(new String[0]));
        System.out.println(stringResult);

        try ( FileWriter writer = new FileWriter(new File("result.txt"))){
            writer.write(stringResult);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Resource downloadWorker(File file) {
        try {
            Path path = Paths.get(file.getAbsolutePath());
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
            return resource;
        } catch (RuntimeException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
