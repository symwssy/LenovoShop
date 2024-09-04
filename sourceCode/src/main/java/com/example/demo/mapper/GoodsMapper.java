package com.example.demo.mapper;

import com.example.demo.dto.GoodsDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GoodsMapper {
    @Select("select id as id,goods_name as goodsName,goods_pic as goodsPic,goods_price as goodsPrice,brief_intro as briefIntro" +
            " from lenovo_market.goods where goods_type=#{goodsType}" +
            " ORDER BY goods_price DESC " +
            "LIMIT #{size} OFFSET #{offset}")
    List<GoodsDTO> searchByGroup(Integer goodsType,int offset,int size);

    @Select("select id as id,goods_name as goodsName,goods_pic as goodsPic,goods_price as goodsPrice, brief_intro as briefIntro" +
            " from lenovo_market.goods where id=#{id}")
    GoodsDTO findGoodsDetailById(Integer id);
}
