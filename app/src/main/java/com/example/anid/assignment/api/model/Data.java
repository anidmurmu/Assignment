package com.example.anid.assignment.api.model;

import java.util.ArrayList;
import java.util.List;

public class Data {

    private int code;
    private List<Content> content = new ArrayList<>();

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public List<Content> getContent() {
        return content;
    }

    public void setContent(List<Content> content) {
        this.content = content;
    }
}
