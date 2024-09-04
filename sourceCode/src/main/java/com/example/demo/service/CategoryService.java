package com.example.demo.service;

import com.example.demo.pojo.Category;

import java.util.List;

public interface CategoryService {
    //新增文章分类
    void add(Category category);


    List<Category> list();
    //根据ID查询分类信息
    Category findById(Integer id);

    void update(Category category);
}
