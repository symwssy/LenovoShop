package com.example.demo.service.impl;

import com.example.demo.dto.GoodsDTO;
import com.example.demo.mapper.GoodsMapper;
import com.example.demo.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceimpl implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public List<GoodsDTO> searchByGroup(Integer goodsType, int page,int size) {
        int offset = page*size;
        return goodsMapper.searchByGroup(goodsType,offset,size);
    }

    @Override
    public GoodsDTO findGooodsDetailById(Integer id) {
        return goodsMapper.findGoodsDetailById(id);
    }


}
