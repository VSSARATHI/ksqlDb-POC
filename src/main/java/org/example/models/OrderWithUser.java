package org.example.models;

import java.util.Objects;

public class OrderWithUser {

    private String orderId;
    private String userId;  // The user who placed the order
    private double orderAmount;
    private String orderDate;
    private String name;
    private String email;

    public OrderWithUser(String orderId, String userId, double orderAmount, String orderDate, String name, String email) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderAmount = orderAmount;
        this.orderDate = orderDate;
        this.name = name;
        this.email = email;
    }

    public OrderWithUser() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof OrderWithUser that)) return false;
        return Double.compare(orderAmount, that.orderAmount) == 0 && Objects.equals(orderId, that.orderId) && Objects.equals(userId, that.userId) && Objects.equals(orderDate, that.orderDate) && Objects.equals(name, that.name) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, userId, orderAmount, orderDate, name, email);
    }

    @Override
    public String toString() {
        return "OrderWithUser{" +
                "orderId='" + orderId + '\'' +
                ", userId='" + userId + '\'' +
                ", orderAmount=" + orderAmount +
                ", orderDate='" + orderDate + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
