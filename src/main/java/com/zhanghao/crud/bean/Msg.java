package com.zhanghao.crud.bean;

import java.util.HashMap;
import java.util.Map;

/**
 *	通用的返回类.
 * @author ZhangHao
 *  2020-10-5
 */
public class Msg {
	//状态码 100-成功 200-服务器处理失败.  自己定义的. 随便的一个数.
	private int code ;
	
	//提示信息
	private String msg;
	
	//用户要返回给浏览器的数据 key(字符串) -value(存对象)
	private Map<String, Object> extend = new HashMap<String, Object>();
	
	/**
	 * 	成功提示消息.
	 * @return
	 */
	public static Msg success() {
		Msg resultMsg=new Msg();
		resultMsg.setCode(100);
		resultMsg.setMsg("张艺苒拍了拍你 说 小伙子处理的不错!!");
		return resultMsg;
	}
	
	/**
	 * 	失败提示消息.
	 * @return
	 */
	public static Msg fail() {
		Msg resultMsg=new Msg();
		resultMsg.setCode(200);
		resultMsg.setMsg("张艺苒撅了噘嘴,  看来不太满意哦");
		return resultMsg;
	}
	
	/**
	 * 	链式的给浏览器返回数据,  可以一直存, 存到extend集合中.
	 * @param key
	 * @param value
	 * @return
	 */
	public Msg add(String key, Object value) {
		this.getExtend().put(key,value);
		return this;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Map<String, Object> getExtend() {
		return extend;
	}

	public void setExtend(Map<String, Object> extend) {
		this.extend = extend;
	}
	
	
	
}
