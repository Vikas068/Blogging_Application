package com.backend.blogging.serviceimplementation;

import com.backend.blogging.entities.Post;
import com.backend.blogging.repository.PostRepository;
import com.backend.blogging.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImplementation implements FileService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public String uploadImage(String path, MultipartFile multipartFile) throws IOException {
        String name=multipartFile.getOriginalFilename();
        String randomId= UUID.randomUUID().toString();
        //concating the file format with random id. Ex:if file name is abc.png.
        //randomid+.png=randomid.png
        String filename1=randomId.concat(name.substring(name.lastIndexOf(".")));
        String filePath=path+ File.separator+filename1;
        //to create the local folder to store the file.
        File file=new File(path);
        if(!file.exists())
        {
            file.mkdir();
        }
        Files.copy(multipartFile.getInputStream(), Paths.get(filePath));
        return filename1;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath=path+File.separator+fileName;
        InputStream is=new FileInputStream(fullPath);
        return is;
    }
}
