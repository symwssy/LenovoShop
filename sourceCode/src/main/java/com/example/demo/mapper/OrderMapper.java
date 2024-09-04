package com.example.demo.mapper;

import com.example.demo.dto.OrderBrowseDTO;
import com.example.demo.dto.OrderDetailDTO;
import com.example.demo.pojo.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {
    @Select("SELECT " +
            "o.id AS id, o.goods_number AS goodsNumber, o.status AS status, " +
            "g.goods_name AS goodsName, g.goods_pic AS goodsPic, g.goods_price AS goodsPrice " +
            "FROM orders o " +
            "JOIN goods g ON o.goods_id = g.id " +
            "WHERE o.create_user = #{createUser} " +
            "LIMIT #{size} OFFSET #{offset}")
    List<OrderBrowseDTO> list(@Param("createUser") Integer createUser,
                                               @Param("offset") int offset,
                                               @Param("size") int size);

    @Insert("insert into orders(goods_id,goods_number,receive_id,status,create_user,create_time,update_time)"+
            "values(#{goodsId},#{goodsNumber},#{receiveId},#{state},#{createUser},#{createTime},#{updateTime})")
    void add(Order order);

    @Delete("delete from orders where id = #{id}")
    void delete(Integer id);
    @Update("update orders set goods_id=#{goodsId},goods_number=#{goodsNumber},receive_id=#{receiveId},update_time=#{updateTime} where id=#{id}")
    void update(Order order);

    @Select("select * from orders where id=#{id}")
    Order findById(Integer id);

    @Select("SELECT " +
            "o.id AS id, o.goods_number AS goodsNumber, o.status AS status, " +
            "g.goods_name AS goodsName, g.goods_pic AS goodsPic, g.goods_price AS goodsPrice " +
            "FROM orders o " +
            "JOIN goods g ON o.goods_id = g.id " +
            "WHERE o.create_user = #{createUser} " +
            "AND g.goods_name LIKE CONCAT('%', #{keyword}, '%') " +
            "ORDER BY o.create_time DESC " +
            "LIMIT #{size} OFFSET #{offset}")
    List<OrderBrowseDTO> search(String keyword, int offset, int size, Integer createUser);
    @Select("SELECT " +
            "o.id AS id, o.goods_number AS goodsNumber, o.status AS status, " +
            "g.goods_name AS goodsName, g.goods_pic AS goodsPic, g.goods_price AS goodsPrice " +
            "FROM orders o " +
            "JOIN goods g ON o.goods_id = g.id " +
            "WHERE o.status = #{status} " +
            "LIMIT #{size} OFFSET #{offset}")
    List<OrderBrowseDTO> searchBystatus(String status, int offset, int size, Integer createUser);
    @Select("SELECT " +
            "o.id AS id, o.goods_number AS goodsNumber, o.status AS status, " +
            "o.create_user AS createUser, o.create_time AS createTime, o.update_time AS updateTime, " +
            "g.goods_name AS goodsName, g.goods_pic AS goodsPic, g.goods_price AS goodsPrice, " +
            "r.consignee AS consignee, r.rec_location AS recLocation, r.phone_number AS phoneNumber " +
            "FROM orders o " +
            "JOIN receive r ON o.receive_id = r.id " +
            "JOIN goods g ON o.goods_id = g.id " +
            "WHERE o.id = #{id}")
    OrderDetailDTO findOrderDetailById(Integer id);
}
