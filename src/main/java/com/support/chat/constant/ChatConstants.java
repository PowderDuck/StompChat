package com.support.chat.constant;

import java.nio.file.Path;

public class ChatConstants 
{
    public static final int CHAT_BUFFER_SIZE = 2048 * 2048;

    public static final String LOCAL_STORAGE_PATH = "src/main/resources/static/";
    public static final String URL_STORAGE_PATH = "/";

    private static final String FILE_NAME = "uploaded-%s";

    private static final String CHAT_ENDPOINT = "/chat/%s";


    public static Path FILE_PATH(String filename)
    {
        return Path.of(LOCAL_STORAGE_PATH)
            .resolve(FILE_NAME(filename));
    }

    public static String FILE_NAME(String filename)
    {
        return String.format(FILE_NAME, filename);
    }

    public static String CHAT_ENDPOINT(String chatId)
    {
        return String.format(CHAT_ENDPOINT, chatId);
    }
}
