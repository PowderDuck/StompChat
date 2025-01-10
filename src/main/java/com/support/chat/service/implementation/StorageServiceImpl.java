package com.support.chat.service.implementation;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.support.chat.constant.ChatConstants;
import com.support.chat.model.IncomingContentFile;
import com.support.chat.service.StorageService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StorageServiceImpl implements StorageService {
    
    private final Encoder encoder = Base64.getEncoder();
    private final Decoder decoder = Base64.getDecoder();

    public String store(IncomingContentFile file)
    {
        try {
            var filePath = ChatConstants.FILE_PATH(
                encryptFilename(file.getName()));
            var fileBytes = decoder.decode(file.getBuffer());

            log.info(
                String.format("Stored the File in Path [%s]", filePath));

            return Files.write(filePath, fileBytes).toString();
        }
        catch (Exception exception)
        {
            throw new RuntimeException("[-] File Storage Failure");
        }
    }

    public String[] store(IncomingContentFile[] files)
    {
        return Arrays.asList(files)
                .stream()
                .map(file -> store(file))
                .toArray(String[]::new);
    }

    public IncomingContentFile retrieve(Path filePath)
    {
        try {
            var file = new IncomingContentFile();
            
            file.setBuffer(encoder.encodeToString(
                Files.readAllBytes(filePath)));
            file.setType(
                Files.probeContentType(filePath));
            file.setName(
                new File(filePath.toString()).getName());

            log.info(
                String.format("Retrieved the File with Name [%s] and Type [%s]", 
                    file.getName(), file.getType()));

            return file;
        }
        catch (Exception exception)
        {
            throw new RuntimeException(
                String.format("[-] File Retrieval Failure in Path %s", filePath.toString()));
        }
    }

    private String encryptFilename(String filename)
    {
        var extensionIndex = filename.lastIndexOf(".");
        
        if (extensionIndex < 0 && extensionIndex + 1 < filename.length())
        {
            throw new RuntimeException(String.format("[-] Filename [%s] without Extension", filename));
        }

        return String.format("%s.%s", 
            DigestUtils.md5DigestAsHex(
                filename.substring(0, extensionIndex).getBytes()), 
                filename.substring(extensionIndex + 1, filename.length()));
    }
}
