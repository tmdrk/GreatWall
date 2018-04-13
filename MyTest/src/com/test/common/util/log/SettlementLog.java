package com.test.common.util.log;

public class SettlementLog extends LogVo{
	private int id;
	private int orderId;
	private String settlementId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getSettlementId() {
		return settlementId;
	}
	public void setSettlementId(String settlementId) {
		this.settlementId = settlementId;
	}
	@Override
	public String toString() {
		return "SettlementLog [id=" + id + ", orderId=" + orderId + ", settlementId=" + settlementId + ", toString()="
				+ super.toString() + "]";
	}
	
}
