package com.support.chat.service;

import java.nio.file.Path;

import com.support.chat.model.incoming.IncomingContentFile;

public interface StorageService {
    
    String store(IncomingContentFile file);
    String[] store(IncomingContentFile[] files);

    IncomingContentFile retrieve(Path filePath);
}
