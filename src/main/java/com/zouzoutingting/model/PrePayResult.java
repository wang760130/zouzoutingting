package com.zouzoutingting.model;

import java.io.Serializable;

/**
 * 预付返回前端结果
 */
public class PrePayResult implements Serializable{

  private static final long serialVersionUID = -7514175575894473390L;

  /**
   * 订单id
   */
  private long orderid;
  /**
   * 结果：true:成功，flase:失败
   */
  private boolean result;
  
  /**
   * 错误信息，result=flase有值
   */
  private String errMsg;
  
  /**
   * 拼接参数结果字符串
   */
  private String paramStr;
  
  public boolean getResult() {
    return result;
  }
  public void setResult(boolean result) {
    this.result = result;
  }
  public String getErrMsg() {
    return errMsg;
  }
  public void setErrMsg(String errMsg) {
    this.errMsg = errMsg;
  }
  public String getParamStr() {
    return paramStr;
  }
  public void setParamStr(String paramStr) {
    this.paramStr = paramStr;
  }

  public long getOrderid() {
    return orderid;
  }

  public void setOrderid(long orderid) {
    this.orderid = orderid;
  }
}
