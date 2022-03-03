package com.tencent.wxcloudrun.config;

import lombok.Data;

import java.util.HashMap;

@Data
public final class ApiResponse {

  private Integer code;
  private String msg;
  private Object data;

  private ApiResponse(int code, String msg, Object data) {
    this.code = code;
    this.msg = msg;
    this.data = data;
  }
  
  public static ApiResponse ok() {
    return new ApiResponse(0, "success", new HashMap<>());
  }

  public static ApiResponse ok(Object data) {
    return new ApiResponse(0, "", data);
  }

  public static ApiResponse error(String msg) {
    return new ApiResponse(100, msg, new HashMap<>());
  }

  public static ApiResponse unLogin(String msg) {
    return new ApiResponse(501, msg, new HashMap<>());
  }

  public static ApiResponse unLogin() {
    return new ApiResponse(501, "", new HashMap<>());
  }
}
