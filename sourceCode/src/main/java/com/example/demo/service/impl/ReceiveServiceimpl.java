package com.example.demo.service.impl;

import com.example.demo.dto.ReceiveBrowseDTO;
import com.example.demo.mapper.ReceiveMapper;
import com.example.demo.pojo.Order;
import com.example.demo.pojo.Receive;
import com.example.demo.pojo.Result;
import com.example.demo.service.ReceiveService;
import com.example.demo.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ReceiveServiceimpl implements ReceiveService {
    @Autowired
    private ReceiveMapper receiveMapper;

    @Override
    public void add(Receive receive) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        receive.setCreateUser(userId);
        receiveMapper.add(receive);
    }

    @Override
    public void delete(Integer id) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        Receive receive = receiveMapper.findById(id);
        if (receive.getCreateUser().equals(userId))
            receiveMapper.delete(id);
    }

    @Override
    public void update(Receive receive) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        Receive original_receive = receiveMapper.findById(receive.getId());
        receive.setCreateUser(userId);
        if (original_receive.getCreateUser().equals(userId))
              receiveMapper.update(receive);
    }

    @Override
    public Receive findById(Integer id) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        Receive receive = receiveMapper.findById(id);
        if (receive.getCreateUser().equals(userId))
            return receive;
        else return null;
    }

    @Override
    public Result<List<ReceiveBrowseDTO>> list(int page, int size) {
        int offset = page * size;
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        return receiveMapper.list(userId, offset, size);
    }
}
