package co.istad.moblie_banking.api.file;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Service
public interface FileService {
    FileDto uploadSingle(MultipartFile file);

    List<FileDto> uploadMultiple(List<MultipartFile> files);

    FileDto findByName(String name) throws IOException;
    Resource download(String name);
    void deleteByName(String name);

    List<FileDto> findAllFile();

    void removeAllFiles();

}
