package com.example.demo.controller;

import com.example.demo.dto.ReceiveBrowseDTO;
import com.example.demo.pojo.Category;
import com.example.demo.pojo.Order;
import com.example.demo.pojo.Receive;
import com.example.demo.pojo.Result;
import com.example.demo.service.ReceiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("receive")
public class ReceiveController {
    @Autowired
    private ReceiveService receiveService;

    @PostMapping
    public Result add(@RequestBody @Validated(Receive.Add.class) Receive receive){
        receiveService.add(receive);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(Integer id){
        receiveService.delete(id);
        return Result.success();
    }
    @PutMapping
    public Result update(@RequestBody @Validated(Receive.Update.class) Receive receive){
        receiveService.update(receive);
        return Result.success();
    }

    @GetMapping("/detail/{id}")
    public Result<Receive> detail(Integer id){
        Receive c = receiveService.findById(id);
        return Result.success(c);
    }

    @GetMapping
    public Result<List<ReceiveBrowseDTO>> list(
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size){
        return receiveService.list(page, size);
    }
}
