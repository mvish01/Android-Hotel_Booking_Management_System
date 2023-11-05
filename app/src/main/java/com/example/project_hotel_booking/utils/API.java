package com.example.project_hotel_booking.utils;

import com.example.project_hotel_booking.entity.User;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface API {
    String BASE_URL ="http://192.168.5.110:4000";

    @POST("/user/login/mobile")
    Call<JsonObject> loginUser(@Body User user);

    @POST("/user/register")
    Call<JsonObject> registerUser(@Body User user);

    @GET("/room/getAllRooms")
    Call<JsonObject> getAllRooms();

    @GET("/mobile/")
    Call<JsonObject> getAllMobiles();

    @POST("/reservation/book_Reservation")
    Call<JsonObject> bookReservation(@Body JsonObject reservation);

    @GET("/reservation/user_reservations/{userId}")
    Call<JsonObject> getUserReservations(@Path("userId") int userId);

    @GET("/reservation/user_reservation/{userId}/{reservationId}")
    Call<JsonObject> getUserReservationDetails(@Path("userId") int userId, @Path("reservationId") int reservationId);
    @GET("/reservation/user_c_reservation/{userId}")
    Call<JsonObject> getUserReservationDetail(@Path("userId") int userId);

    @POST("/feedback/post")
    Call<JsonObject> postFeedback(@Body JsonObject feedbackData);

    @GET("/user/{id}")
    Call<JsonObject> RetriveUser(@Path("id") Integer id);

    @PUT("/user/change_password/{id}")
    Call<JsonObject> UpdatePassword(@Body JsonObject jsonObject, @Path("id") Integer id);
    @POST("/room/availability")
    Call<JsonObject> checkRoomAvailability(@Body JsonObject availabilityData);

    @DELETE("/confirmation/cancel/{confirmationId}")
    Call<JsonObject> cancelReservation(@Path("confirmationId") int confirmationId);


}
