package com.support.chat.model.dto;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<T> 
{
    private Boolean success;
    private String message;
    private LocalDateTime submittedTime = LocalDateTime.now();

    private MetaDto meta;
    private T data;

    public static ResponseDto<?> ok()
    {
        var response = new ResponseDto<>();
        response.setSuccess(true);

        return response;
    }

    public static <T> ResponseDto<T> ok(Optional<T> data)
    {
        var response = new ResponseDto<T>();
        if (!data.isEmpty())
        {
            response.setSuccess(true);
            response.setData(data.get());

            return response;
        }

        response.setSuccess(false);
        return response;
    }

    public static <T> ResponseDto<T> ok(T data)
    {
        var response = new ResponseDto<T>();
        response.setSuccess(true);
        response.setData(data);

        return response;
    }

    public static <T> ResponseDto<T> ok(T data, Long count, Pageable pageable)
    {
        var response = new ResponseDto<T>();

        var meta = new MetaDto();
        meta.setCount(count);
        meta.setPageNumber(pageable.getPageNumber());
        meta.setPageSize(pageable.getPageSize());

        response.setSuccess(true);
        response.setData(data);
        response.setMeta(meta);

        return response;
    }

    public static ResponseDto<?> error(String reason)
    {
        var response = new ResponseDto<>();
        response.setSuccess(false);
        response.setMessage(reason);
        
        return response;
    }
}
