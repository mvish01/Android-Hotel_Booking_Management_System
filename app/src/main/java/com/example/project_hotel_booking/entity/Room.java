package com.example.project_hotel_booking.entity;

public class Room {
    private int roomNumber;
    private String roomType;
    private int capacity;
    private float pricePerNight;
    private String images;

    public Room() {
    }

    public Room(int roomNumber, String roomType, int capacity, float pricePerNight, String images) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.capacity = capacity;
        this.pricePerNight = pricePerNight;
        this.images = images;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public float getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(float pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public  String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber=" + roomNumber +
                ", roomType='" + roomType + '\'' +
                ", capacity=" + capacity +
                ", pricePerNight=" + pricePerNight +
                ", images='" + images + '\'' +
                '}';
    }

    public void getRoomDetails() {
        // Get room details logic
    }

    public void addRoom() {
        // Add room logic
    }

    public void editRoom() {
        // Edit room logic
    }

    public void deleteRoom() {
        // Delete room logic
    }
}
