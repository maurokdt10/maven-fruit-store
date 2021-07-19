package com.store.model;

public class SalesDetail {
	private String id;
	private String hour;
	private String detail;
	private double total;
	public SalesDetail(String id, String hour, String detail, double total) {
		this.id = id;
		this.hour = hour;
		this.detail = detail;
		this.total = total;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
}
