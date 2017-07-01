package com.idspring.commandpattern.model.controller;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.Objects;

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
        Assert.notNull(data, "Data must not null");
        return Response.status(HttpStatus.OK, data);
    }

    public static <T> Response<T> okOrNotFound(@Nullable T data) {
        if (Objects.isNull(data)) {
            return Response.status(HttpStatus.NOT_FOUND, null);
        } else {
            return Response.ok(data);
        }
    }

    public static <T> Response<T> status(HttpStatus httpStatus, @Nullable T data) {
        return Response.<T>builder()
                .code(httpStatus.value())
                .status(httpStatus.getReasonPhrase())
                .data(data)
                .build();
    }

}
