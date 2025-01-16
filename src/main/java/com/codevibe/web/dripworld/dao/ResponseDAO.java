package com.codevibe.web.dripworld.dao;

import java.io.Serializable;
import java.util.Date;

public class ResponseDAO implements Serializable {
    private final String timestamp = new Date().toString();
    private Integer code;
    private Object data;
    private String message;
    private Long totalElements;
    private Integer totalPages;

    public ResponseDAO() {
    }

    public ResponseDAO(Integer code, Object data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public ResponseDAO(Integer code, Object data, String message, Long totalElements, Integer totalPages) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
}
