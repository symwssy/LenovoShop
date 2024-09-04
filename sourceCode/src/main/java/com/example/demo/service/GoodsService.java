package com.example.demo.service;

import com.example.demo.dto.GoodsDTO;

import java.util.List;

public interface GoodsService {

     List<GoodsDTO> searchByGroup(Integer goodsType,int page,int size);

     GoodsDTO findGooodsDetailById(Integer id);
}
