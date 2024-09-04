package com.example.demo.mapper;

import com.example.demo.dto.ReceiveBrowseDTO;
import com.example.demo.pojo.Receive;
import com.example.demo.pojo.Result;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReceiveMapper {
    @Insert("insert into receive(create_user,consignee,rec_location,phone_number)"+
            "values(#{createUser},#{consignee},#{recLocation},#{phoneNumber})")
    void add(Receive receive);

    @Select("select * from receive where id=#{id}")
    Receive findById(Integer id);
    @Delete("delete from receive where id = #{id}")
    void delete(Integer id);
    @Update("update receive set create_user=#{createUser},consignee=#{congsignee},rec_location=#{recLocation},phone_number=#{phoneNumber} where id=#{id}")
    void update(Receive receive);
    @Select("SELECT " +
            "r.consignee AS consignee, r.rec_location AS recLocation, r.phone_number AS phoneNumber " +
            "FROM receive r WHERE r.create_user = #{createUser} LIMIT #{size} OFFSET #{offset}")
    Result<List<ReceiveBrowseDTO>> list(Integer createUser, int offset, int size);
}
