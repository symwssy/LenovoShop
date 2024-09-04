package com.example.demo.controller;

import com.auth0.jwt.JWT;
import com.example.demo.pojo.Article;
import com.example.demo.pojo.Result;
import com.example.demo.service.ArticleService;
import com.example.demo.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping
    public Result add(@RequestBody @Validated Article article){
         articleService.add(article);
         return Result.success();
    }
    
}
