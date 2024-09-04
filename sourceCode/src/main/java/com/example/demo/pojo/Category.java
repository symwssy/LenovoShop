package com.example.demo.pojo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Category {
    @NotNull(groups = Update.class)
    private Integer id;
    @NotEmpty(groups = {Add.class,Update.class})
    private String categoryName;
    @NotEmpty(groups = {Add.class,Update.class})
    private String categoryAlias;
    private Integer createUser;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public interface Add{

    }

    public interface Update{

    }
}
