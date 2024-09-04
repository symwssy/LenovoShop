package com.example.demo.pojo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Receive {
    @NotNull(groups = Receive.Update.class)
    private Integer id;  //主键
    @NotNull(groups = {Receive.Add.class})
    private Integer createUser;
    @NotEmpty(groups = {Receive.Add.class,Receive.Update.class})
    private String consignee;
    @NotEmpty(groups = {Receive.Add.class,Receive.Update.class})
    private String recLocation;
    @NotEmpty(groups = {Receive.Add.class,Receive.Update.class})
    private String phoneNumber;


    public interface Add{

    }

    public interface Update{

    }
}
