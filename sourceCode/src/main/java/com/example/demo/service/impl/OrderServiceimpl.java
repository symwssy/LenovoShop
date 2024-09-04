package com.example.demo.service.impl;

import com.example.demo.dto.OrderBrowseDTO;
import com.example.demo.dto.OrderDetailDTO;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.pojo.Order;
import com.example.demo.pojo.Result;
import com.example.demo.service.OrderService;
import com.example.demo.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceimpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<OrderBrowseDTO> list(int page, int size) {
        int offset = page * size;
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
            return orderMapper.list(userId, offset, size);
    }

    @Override
    public void add(Order order) {
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        order.setCreateUser(userId);
        order.setState("待付款");
        orderMapper.add(order);
    }

    @Override
    public void delete(Integer id) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        Order order = orderMapper.findById(id);
        if (order.getCreateUser().equals(userId))
              orderMapper.delete(id);
    }

    @Override
    public void update(Order order) {
        order.setUpdateTime(LocalDateTime.now());
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        order.setCreateUser(userId);
        Order original_order = orderMapper.findById(order.getId());
        if (original_order.getCreateUser().equals(userId))
             orderMapper.update(order);
    }

    @Override
    public OrderDetailDTO findOrderDetailById(Integer id) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        OrderDetailDTO orderdetail = orderMapper.findOrderDetailById(id);
        if (orderdetail.getCreateUser().equals(userId))
            return orderdetail;
        else return null;
    }

    @Override
    public List<OrderBrowseDTO> search(String keyword, int page, int size) {
        int offset = page * size;
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        return orderMapper.search(keyword, offset, size,userId);
    }

    @Override
    public List<OrderBrowseDTO> searchBystatus(String status, int page, int size) {
        int offset = page * size;
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        return orderMapper.searchBystatus(status, offset, size,userId);
    }


}
