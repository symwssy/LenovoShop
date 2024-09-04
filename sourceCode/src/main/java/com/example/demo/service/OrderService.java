package com.example.demo.service;

import com.example.demo.dto.OrderBrowseDTO;
import com.example.demo.dto.OrderDetailDTO;
import com.example.demo.pojo.Category;
import com.example.demo.pojo.Order;

import java.util.List;

public interface OrderService {
    List<OrderBrowseDTO> list(int page,int size);

    void add(Order order);

    void delete(Integer id);

    void update(Order order);


    List<OrderBrowseDTO> search(String keyword, int page, int size);

    List<OrderBrowseDTO> searchBystatus(String status, int page, int size);

    OrderDetailDTO findOrderDetailById(Integer id);
}
