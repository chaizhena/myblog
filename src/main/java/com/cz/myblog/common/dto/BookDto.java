package com.cz.myblog.common.dto;


import com.cz.myblog.entity.Category;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDto {

    private Integer id;

    private String cover;

    private String title;

    private String author;

    private String date;

    private String press;

    private String abs;

    private Category category;


}
