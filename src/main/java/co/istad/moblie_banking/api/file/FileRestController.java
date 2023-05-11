package co.istad.moblie_banking.api.file;

import co.istad.moblie_banking.base.BaseRest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
@Slf4j
@RequiredArgsConstructor
public class FileRestController {
    private final FileService fileService;
    @PostMapping
    public BaseRest<?> uploadSingle(@RequestPart MultipartFile file) {
        FileDto fileDto = fileService.uploadSingle(file);
        return BaseRest
                .builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .data(fileDto)
                .message("File uploaded successfully.")
                .build();
    }
    @PostMapping("/multiple")
    public BaseRest<?> uploadMultiple(@RequestPart List<MultipartFile> files) {
        log.info("File Request = {}", files);

        List<FileDto> filesDto = fileService.uploadMultiple(files);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("File has been uploaded")
                .timestamp(LocalDateTime.now())
                .data(filesDto)
                .build();
    }

    @GetMapping("/{name}")
    public BaseRest<?> findByName(@PathVariable String name) throws IOException {
        FileDto fileDto = fileService.findByName(name);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("File has been found")
                .timestamp(LocalDateTime.now())
                .data(fileDto)
                .build();
    }
    @GetMapping
    public BaseRest<?> findAllFile() {
        return BaseRest
                .builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .data(fileService.findAllFile())
                .message("Files found successfully.")
                .build();
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{name}")
    public void deleteByName(@PathVariable String name) {
        fileService.deleteByName(name);
    }
    @DeleteMapping
    public BaseRest<?> removeAllFiles() {
        fileService.removeAllFiles();
        return BaseRest
                .builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .data("All files have been removed.")
                .message("Files have been removed successfully.")
                .build();
    }

    @GetMapping("/download/{name}")
    public ResponseEntity<?> download(@PathVariable String name) {
        Resource resource = fileService.download(name);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=" + resource.getFilename())
                .body(resource);
    }

}
