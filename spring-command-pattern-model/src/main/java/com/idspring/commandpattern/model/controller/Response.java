package com.idspring.commandpattern.model.controller;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @author Eko Kurniawan Khannedy
 * @since 30/06/17
 */
@Data
@Builder
public class Response<T> {

    private Integer code;

    private String status;

    private T data;

    public static <T> Response<T> ok(T data) {
        return Response.status(HttpStatus.OK, data);
    }

    public static <T> Response<T> status(HttpStatus httpStatus, T data) {
        return Response.<T>builder()
                .code(httpStatus.value())
                .status(httpStatus.getReasonPhrase())
                .data(data)
                .build();
    }

}
