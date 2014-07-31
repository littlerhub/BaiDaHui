package com.littletools.tool.search;

import java.io.Serializable;


public class Ems implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -721798038490704608L;
	
	private String message; //消息�?
	private String time; //时间
	private String context; //状�?
	private String status; //返回值状�?0,查询失败;1:查询成功
	
	private String company; //快�?公司名称
	private String order; //单号
	
	
	
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String toString(){
		
		StringBuilder sb = new StringBuilder();
		
		return sb.append(this.getStatus()).append(",")
				 .append(this.getContext()).append("")
				 .toString();
	}

}
