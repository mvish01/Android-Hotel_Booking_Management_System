package com.example.project_hotel_booking.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.math.BigInteger;

public class User
{
    private int userId;
    private int employeeId;
    private String firstName;
    private String lastName;
    private String role;
    private String email;
    private BigInteger phoneNumber;

    private String password;

    public User() {
    }

    public User(int userId, int employeeId, String firstName, String lastName, String role, String email, BigInteger phoneNumber, String password) {
        this.userId = userId;
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    protected User(Parcel in) {
        userId = in.readInt();
        employeeId = in.readInt();
        firstName = in.readString();
        lastName = in.readString();
        role = in.readString();
        email = in.readString();
        password = in.readString();
    }



    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigInteger getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(BigInteger phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", employeeId=" + employeeId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", password='" + password + '\'' +
                '}';
    }

    public void register() {
        // Register user logic
    }

    public void login() {
        // User login logic
    }

    public void checkRoomAvailability() {
        // Check room availability logic
    }

    public void bookRoom() {
        // Book room logic
    }

    public void confirmBooking() {
        // Confirm booking logic
    }

    public void makePayment() {
        // Make payment logic
    }

    public void viewBookingHistory() {
        // View booking history logic
    }

    public void provideFeedback() {
        // Provide feedback logic
    }

    public void changePassword() {
        // Change password logic
    }



}
