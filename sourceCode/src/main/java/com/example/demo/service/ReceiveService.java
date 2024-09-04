package com.example.demo.service;

import com.example.demo.dto.ReceiveBrowseDTO;
import com.example.demo.pojo.Receive;
import com.example.demo.pojo.Result;

import java.util.List;

public interface ReceiveService {
    void add(Receive receive);

    void delete(Integer id);

    void update(Receive receive);

    Receive findById(Integer id);

    Result<List<ReceiveBrowseDTO>> list(int page, int size);
}
