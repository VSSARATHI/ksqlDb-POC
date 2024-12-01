package org.example.models;

import java.util.Objects;

public class Order {
    private String orderId;
    private String userId;  // The user who placed the order
    private double orderAmount;
    private String orderDate;

    public Order(String orderId, String userId, double orderAmount, String orderDate) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderAmount = orderAmount;
        this.orderDate = orderDate;
    }

    public Order() {
    }

    public String getOrderId() {
        return orderId;
    }

    public String getUserId() {
        return userId;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Order order)) return false;
        return Double.compare(orderAmount, order.orderAmount) == 0 && Objects.equals(orderId, order.orderId) && Objects.equals(userId, order.userId) && Objects.equals(orderDate, order.orderDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, userId, orderAmount, orderDate);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", userId='" + userId + '\'' +
                ", orderAmount=" + orderAmount +
                ", orderDate='" + orderDate + '\'' +
                '}';
    }
}
