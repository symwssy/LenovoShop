package com.example.demo.controller;

import com.example.demo.dto.GoodsDTO;
import com.example.demo.pojo.Result;
import com.example.demo.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/index")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    @GetMapping  //默认商品按照降序排列
    public Result<List<GoodsDTO>> searchByGroup(Integer goodsType,@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "8") int size){
        return Result.success(goodsService.searchByGroup(goodsType,page,size));
    }
    @GetMapping("/detail/{id}")
    public Result<GoodsDTO> GoodsDetail(@PathVariable Integer id){
        GoodsDTO goodsDTO = goodsService.findGooodsDetailById(id);
        return Result.success(goodsDTO);
    }
}
