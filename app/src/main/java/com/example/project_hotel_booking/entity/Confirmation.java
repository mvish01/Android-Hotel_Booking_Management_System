package com.example.project_hotel_booking.entity;

public class Confirmation {
    private int confirmationId;
    private String userName;
    private int roomId;
    private String checkInDate;
    private float amount;
    private String paymentDate;

    public Confirmation() {
    }

    public Confirmation(int confirmationId, String userName, int roomId, String checkInDate, float amount, String paymentDate) {
        this.confirmationId = confirmationId;
        this.userName = userName;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }

    public int getConfirmationId() {
        return confirmationId;
    }

    public void setConfirmationId(int confirmationId) {
        this.confirmationId = confirmationId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public String toString() {
        return "Confirmation{" +
                "confirmationId=" + confirmationId +
                ", userName='" + userName + '\'' +
                ", roomId=" + roomId +
                ", checkInDate='" + checkInDate + '\'' +
                ", amount=" + amount +
                ", paymentDate='" + paymentDate + '\'' +
                '}';
    }

    public void generateConfirmation() {
        // Generate confirmation logic
    }
}
