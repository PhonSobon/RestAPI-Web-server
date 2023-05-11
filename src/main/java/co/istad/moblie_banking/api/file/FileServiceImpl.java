package co.istad.moblie_banking.api.file;

import co.istad.moblie_banking.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService{
    private FileUtil fileUtil;
    @Value("${file.server-path}")
    private String fileServerPath;
    @Value("${file.base-url}")
    private String fileBaseUrl;
    @Value("${file.base-url-download}")
    private String fileBaseUrlDownload;
    @Autowired
    private void setFileUtil(FileUtil fileUtil){
        this.fileUtil = fileUtil;
    }
    @Override
    public FileDto uploadSingle(MultipartFile file) {
        return fileUtil.upload(file);
    }

    @Override
    public List<FileDto> uploadMultiple(List<MultipartFile> files) {
        List<FileDto> filesDto = new ArrayList<>();

        for (MultipartFile file : files) {
            filesDto.add(fileUtil.upload(file));
        }

        return filesDto;
    }
    @Override
    public FileDto findByName(String name) throws IOException {
        Resource resource = fileUtil.findByName(name);
        return FileDto.builder()
                .name(resource.getFilename())
                .extension(fileUtil.getExtension(resource.getFilename()))
                .url(String.format("%s%s",fileBaseUrl, resource.getFilename()))
                .downloadUrl(String.format("%s%s", fileBaseUrlDownload, name))
                .size(resource.contentLength())
                .build();
    }
    @Override
    public List<FileDto> findAllFile() {
        File file = new File(fileServerPath);
        File[] files = file.listFiles();
        List<FileDto> fileDtoList = new ArrayList<>();
        assert files != null;
        for(File folderFile: files){
            fileDtoList
                    .add(
                            new FileDto(
                                    folderFile.getName()
                                    ,fileBaseUrl + folderFile.getName()
                                    ,fileBaseUrlDownload + folderFile.getName().substring(0,folderFile.getName().length()-4)
                                    ,folderFile.getName().substring(folderFile.getName().lastIndexOf(".") + 1)
                                    ,folderFile.length()
                            )
                    );
        }
        return fileDtoList;
    }
    @Override
    public void removeAllFiles() {
        fileUtil.removeAllFiles();
    }
    @Override
    public void deleteByName(String name) {
        fileUtil.deleteByName(name);
    }
    @Override
    public Resource download(String name) {
        return fileUtil.findByName(name);
    }

}
