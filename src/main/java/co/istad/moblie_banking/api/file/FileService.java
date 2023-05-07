package co.istad.moblie_banking.api.file;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Service
public interface FileService {
    FileDto uploadSingle(MultipartFile file);

    List<FileDto> uploadMultipleFile(List<MultipartFile> files);

    FileDto findFileByName(String fileName);

    List<FileDto> findAllFile();

    void removeAllFiles();

    String removeFileByName(String fileName);

    String downloadFile(String fileName);
}
