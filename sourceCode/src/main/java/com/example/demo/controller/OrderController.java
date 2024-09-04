package com.example.demo.controller;

import com.example.demo.dto.OrderBrowseDTO;
import com.example.demo.dto.OrderDetailDTO;
import com.example.demo.pojo.Category;
import com.example.demo.pojo.Order;
import com.example.demo.pojo.Result;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public Result<List<OrderBrowseDTO>> list(
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        return Result.success(orderService.list(page, size));
    }

    @GetMapping("/search")
    public Result<List<OrderBrowseDTO>> search(@RequestParam(required = false) String keyword,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        return Result.success(orderService.search(keyword, page, size));
    }

    @GetMapping("/searchBystatus")
    public Result<List<OrderBrowseDTO>> searchBystatus(@RequestParam(required = true) String status,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) {
        if (status!="待付款" && status !="待发货" && status!="待收货")
            return null;
        return Result.success(orderService.searchBystatus(status, page, size));
    }


    @PostMapping
    public Result add(@RequestBody @Validated(Order.Add.class) Order order){
        orderService.add(order);
        return Result.success();
    }
    @DeleteMapping
    public Result delete(Integer id){
        orderService.delete(id);
        return Result.success();
    }
    @PutMapping
    public Result update(@RequestBody @Validated(Order.Update.class) Order order){
        orderService.update(order);
        return Result.success();
    }

    @GetMapping("/detail/{id}")
    public Result<OrderDetailDTO> OrderDetail(@PathVariable Integer id) {
        OrderDetailDTO orderDetail = orderService.findOrderDetailById(id);
        return Result.success(orderDetail);
    }
}
