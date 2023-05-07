package co.istad.moblie_banking.util;

import co.istad.moblie_banking.api.file.FileDto;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
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
    /**
     *
     * @param multipartFile used for uploading file
     * @return FileDto
     */
    public FileDto uploadFile(MultipartFile multipartFile){
        int lastDotIndex = Objects.requireNonNull(multipartFile.getOriginalFilename()).lastIndexOf(".");
        String extension = multipartFile.getOriginalFilename().substring(lastDotIndex+1);
        long size = multipartFile.getSize();
        UUID uuid = UUID.randomUUID();
        String fileName = String.format("%s.%s", uuid,extension);
        String url = String.format("%s%s",fileBaseUrl,fileName);
        Path path = Paths.get(fileServerPath + fileName);
        try {
            Files.copy(multipartFile.getInputStream(), path);
            return FileDto.builder()
                    .name(fileName)
                    .url(url)
                    .downloadUrl(fileBaseDownloadUrl + uuid)
                    .extension(extension)
                    .size(size)
                    .build();
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Uploading failed...!");
//            throw new MultipartException("Cannot upload file");
        }
    }
    /**
     *
     * @param fileName used for finding file
     * @return FileDto
     */
    public FileDto findFileByName(String fileName){
        File file = new File(fileServerPath);
        File[] files = file.listFiles();
        assert files != null;// use to make sure that files is not null
        for(File file1: files){
            String name = this.name(fileName, file1);//-> use this to get file name without extension
            if(name.equals(fileName)){
                int getExtension = file1.getName().lastIndexOf(".") + 1;
                return FileDto
                        .builder()
                        .name(file1.getName())
                        .url(fileBaseUrl + file1.getName())
                        .downloadUrl(fileBaseDownloadUrl + fileName)
                        .extension(file1.getName().substring(getExtension))
                        .size(file1.length())
                        .build();
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"File is not found.");
    }
    /**
     *
     * @param fileName used for deleting file by a specific name
     * @return String
     */
    public String removeFileByName(String fileName){
        File file = new File(fileServerPath);
        File[] files = file.listFiles();
        assert files != null;//we used for checking NullPointer Exception
        for(File file1: files){
            String name = this.name(fileName, file1);//-> use this to get file name without extension
            System.out.println(name);
            if(name.equals(fileName)){//we can use String name above as well.
                file1.delete();
                return "File is removed successfully.";
            }
//            assert file1.getName().startsWith(fileName);// we can use assert instead of (if or throw) statement
//            file1.delete();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File is not found.");
    }
    /**
     * used for remove all file in folder server
     */
    public void removeAllFiles(){
        File file = new File(fileServerPath);
        File[] files = file.listFiles();
        try{
            assert files != null;//we used for checking NullPointer Exception or to make sure that files is not null
            for(File file1: files){
                file1.delete();
            }
        }catch (Exception exception){
            return;
        }
    }
    @NotNull
    public String name(String name, File file1){
        if(file1.getName().length()==40){
            name = file1
                    .getName()
                    .substring(0,file1.getName().length()-4);
        }else {
            name = file1
                    .getName()
                    .substring(0,file1.getName().length()-5);
        }
        System.out.println(name);// -> use this to get file name without extension
        return name;
    }
}

