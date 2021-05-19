package com.tx.utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(chain = true)
public class CommonResult {

    @ApiModelProperty(value = "是否成功")
    private Boolean success;
    @ApiModelProperty(value = "返回码")
    private Integer code;
    @ApiModelProperty(value = "返回信息")
    private String message;
    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<>();

    // 私有构造器，外部不能直接new对象
    private CommonResult() {}

    // 成功方法
    public static CommonResult ok() {
        CommonResult result = new CommonResult();
        result.setSuccess(true).setCode(ResponseCode.SUCCESS).setMessage("成功");
        return result;
    }

    public static CommonResult error() {
        CommonResult result = new CommonResult();
        result.setSuccess(false).setCode(ResponseCode.ERROR).setMessage("失败");
        return result;
    }

    // 方便链式编程
    // 设置各个参数
    public CommonResult success(boolean success) {
        this.setSuccess(success);
        return this;
    }

    public CommonResult code(Integer code) {
        this.setCode(code);
        return this;
    }

    public CommonResult message(String message) {
        this.setMessage(message);
        return this;
    }

    public CommonResult data(String key,Object value) {
        this.data.put(key,value);
        return this;
    }

    public CommonResult data(Map<String,Object> data) {
        this.setData(data);
        return this;
    }
}
