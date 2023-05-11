package co.istad.moblie_banking.util;

import co.istad.moblie_banking.api.file.FileDto;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Component
public class FileUtil {
    @Value("${file.server-path}")
    private String fileServerPath;
    @Value("${file.base-url}")
    private String fileBaseUrl;
    @Value("${file.base-url-download}")
    private String fileBaseDownloadUrl;
    public FileDto upload(MultipartFile file) {

        String extension = getExtension(file.getOriginalFilename());

        long size = file.getSize();

        String name = String.format("%s.%s", UUID.randomUUID(), extension);

        String url = String.format("%s%s", fileBaseUrl, name);

        Path path = Paths.get(fileServerPath + name);

        try {
            Files.copy(file.getInputStream(), path);
            return FileDto.builder()
                    .name(name)
                    .url(url)
                    .downloadUrl(String.format("%s%s", fileBaseDownloadUrl, name))
                    .extension(extension)
                    .size(size)
                    .build();

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Upload file failed, please contact developer [012345678]!");
        }
    }

    public Resource findByName(String name) {
        Path path = Paths.get(fileServerPath + name);
        try {
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists()) {
                return resource;
            }

            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "File is not found!");
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage());
        }
    }
    public void deleteByName(String name) {
        Path path = Paths.get(fileServerPath + name);
        try {
            boolean isDeleted = Files.deleteIfExists(path);
            if (!isDeleted) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "File name is not found");
            }
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "File is failed to delete");
        }
    }
    public void removeAllFiles(){
        File file = new File(fileServerPath);
        File[] files = file.listFiles();
        try{
            assert files != null;
            for(File file1: files){
                file1.delete();
            }
        }catch (Exception exception){
            return;
        }
    }
    public String getExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastDotIndex + 1);
    }
}

