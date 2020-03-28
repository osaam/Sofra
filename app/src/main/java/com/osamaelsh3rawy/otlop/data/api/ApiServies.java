package com.osamaelsh3rawy.otlop.data.api;

import com.osamaelsh3rawy.otlop.data.model.contactUs.ContactUs;
import com.osamaelsh3rawy.otlop.data.model.restourantCategory.RestourantOneCategory;
import com.osamaelsh3rawy.otlop.data.model.restourantItemMeal.RestourantItemMeal;
import com.osamaelsh3rawy.otlop.data.model.restourantMyOrder.RestourantMyOrder;
import com.osamaelsh3rawy.otlop.data.model.restourantOffer.RestourantOffer;
import com.osamaelsh3rawy.otlop.data.model.restourantOffer.RestourantOfferData;
import com.osamaelsh3rawy.otlop.data.model.userCtegories.Categories;
import com.osamaelsh3rawy.otlop.data.model.items.Items;
import com.osamaelsh3rawy.otlop.data.model.listOfAreaNot.ListOfAreaNot;
import com.osamaelsh3rawy.otlop.data.model.userListOfRestourante.ListOfRestourante;
import com.osamaelsh3rawy.otlop.data.model.userListOfRestourante.RestourantesData;
import com.osamaelsh3rawy.otlop.data.model.userMyOrder.MyOrder;
import com.osamaelsh3rawy.otlop.data.model.userNotifications.Notifications;
import com.osamaelsh3rawy.otlop.data.model.restourantCategory.RestourantCategory;
import com.osamaelsh3rawy.otlop.data.model.userRestourantFilter.RestourantFilter;
import com.osamaelsh3rawy.otlop.data.model.userRestourantInfo.RestourantInfo;
import com.osamaelsh3rawy.otlop.data.model.restourantLogin.RestourantLogin;
import com.osamaelsh3rawy.otlop.data.model.userRestourantReview.RestourantReview;
import com.osamaelsh3rawy.otlop.data.model.userLogin.UserLogin;
import com.osamaelsh3rawy.otlop.data.model.userOffers.UserOffers;
import com.osamaelsh3rawy.otlop.data.model.userOffersDetaiels.UserOffersDetaiels;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiServies {

    @GET("restaurants")
    Call<ListOfRestourante> getAllRestourants(@Query("page") int page);

    @GET("items")
    Call<Items> getItems(@Query("restaurant_id") int restaurant_id,
                         @Query("category_id") int category_id,
                         @Query("page") int page);

    @GET("categories")
    Call<Categories> categories(@Query("restaurant_id") int restaurant_id);


    @GET("client/notifications")
    Call<Notifications> getNotifications(@Query("api_token") String api_token);


    @GET("restaurant/notifications")
    Call<Notifications> getNotificationsRestourant(@Query("api_token") String api_token);

    @GET("restaurants")
    Call<ListOfRestourante> restourantesFilter(@Query("keyword") String keword,
                                               @Query("region_id") int region_id);

    @GET("cities-not-paginated")
    Call<ListOfAreaNot> ListCities();


    @GET("regions-not-paginated")
    Call<ListOfAreaNot> ListArea(@Query("city_id") int city_id);

    @GET("offers")
    Call<UserOffers> offers(@Query("restaurant_id") int restaurant_id);


    @GET("offer")
    Call<UserOffersDetaiels> OffersDetaiels(@Query("offer_id") int offer_id);


    @POST("client/login")
    @FormUrlEncoded
    Call<UserLogin> UserLogin(@Field("email") String email,
                              @Field("password") String password);

    @POST("restaurant/login")
    @FormUrlEncoded
    Call<RestourantLogin> RestourantLogin(@Field("email") String email,
                                          @Field("password") String password);


    @GET("restaurant/reviews")
    Call<RestourantReview> Reviews(@Query("api_token") String api_token,
                                   @Query("restaurant_id") int restaurant_id,
                                   @Query("page") int page);

    @POST("client/new-order")
    @FormUrlEncoded
    Call<MyOrder> addNewOrder(@Field("restaurant_id") int restaurant_id,
                              @Field("note") String note,
                              @Field("address") String address,
                              @Field("payment_method_id") int payment_method_id,
                              @Field("phone") int phone,
                              @Field("name") String name,
                              @Field("api_token") String api_token,
                              @Field("items[]") List<Integer> items,
                              @Field("quantities[]") List<Integer> quantities,
                              @Field("notes[]") List<String> notes);

    @POST("client/change-password")
    @FormUrlEncoded
    Call<UserLogin> UserChangePass(@Field("api_token") String api_token,
                                   @Field("old_password") String old_password,
                                   @Field("password") String password,
                                   @Field("password_confirmation") String password_confirmation);


    @POST("reset-password")
    @FormUrlEncoded
    Call<UserLogin> UserResetPass(@Field("email") String email);


    @POST("restaurant/delete-category")
    @FormUrlEncoded
    Call<RestourantCategory> RestourantDeleteCategory(@Field("api_token") String api_token,
                                                      @Field("category_id") int category_id);

    @POST("restaurant/new-category")
    @Multipart
    Call<RestourantOneCategory> RestourantAddCategory(@Part("name") RequestBody name,
                                                      @Part MultipartBody.Part file,
                                                      @Part("api_token") RequestBody api_token);

    @POST("restaurant/update-category")
    @Multipart
    Call<RestourantOneCategory> RestourantUpdateCategory(@Part("name") RequestBody name,
                                                         @Part MultipartBody.Part file,
                                                         @Part("api_token") RequestBody api_token,
                                                         @Part("category_id") RequestBody category_id);

    @POST("client/restaurant/review")
    @FormUrlEncoded
    Call<RestourantReview> UserAddComment(@Field("rate") int rate,
                                          @Field("comment") String comment,
                                          @Field("restaurant_id") int restaurant_id,
                                          @Field("api_token") String api_token);

    @POST("contact")
    @FormUrlEncoded
    Call<ContactUs> sendMessage(@Field("name") String name,
                                @Field("email") String email,
                                @Field("phone") String phone,
                                @Field("type") String type,
                                @Field("content") String content);

    @POST("client/new-password")
    @FormUrlEncoded
    Call<UserLogin> UserNewPass(@Field("code") String code,
                                @Field("password") String password,
                                @Field("password_confirmation") String password_confirmation);

    @POST("client/sign-up")
    @Multipart
    Call<UserLogin> Register(@Part("name") RequestBody name,
                             @Part("email") RequestBody email,
                             @Part("password") RequestBody password,
                             @Part("password_confirmation") RequestBody password_confirmation,
                             @Part("phone") RequestBody phone,
                             @Part("region_id") RequestBody region_id,
                             @Part MultipartBody.Part profile_image);

    @POST("client/profile")
    @Multipart
    Call<UserLogin> UserEditProfile(@Part("api_token") RequestBody api_token,
                                    @Part("name") RequestBody name,
                                    @Part("phone") RequestBody phone,
                                    @Part("email") RequestBody email,
                                    @Part MultipartBody.Part profile_image,
                                    @Part("region_id") RequestBody region_id);


    @POST("client/confirm-order")
    @FormUrlEncoded
    Call<MyOrder> UserConfirmOrder(@Field("order_id") int order_id,
                                   @Field("api_token") String api_token);

    @POST("client/decline-order")
    @FormUrlEncoded
    Call<MyOrder> UserDeclineOrder(@Field("order_id") int order_id,
                                   @Field("api_token") String api_token);

    @GET("client/my-orders")
    Call<MyOrder> UserMyOrder(@Query("api_token") String api_token,
                              @Query("state") String state,
                              @Query("page") int page);


    /////////////////////// Restourant Regiester ////////////////////////////////

    @POST("restaurant/change-password")
    @FormUrlEncoded
    Call<RestourantLogin> RestourantChangePass(@Field("api_token") String api_token,
                                               @Field("old_password") String old_password,
                                               @Field("password") String password,
                                               @Field("password_confirmation") String password_confirmation);


    @POST("restaurant/sign-up")
    @Multipart
    Call<RestourantLogin> RestourantRegister(@Part("name") RequestBody name,
                                             @Part("email") RequestBody email,
                                             @Part("password") RequestBody password,
                                             @Part("password_confirmation") RequestBody password_confirmation,
                                             @Part("phone") RequestBody phone,
                                             @Part("whatsapp") RequestBody whatsapp,
                                             @Part("region_id") RequestBody region_id,
                                             @Part("delivery_cost") RequestBody delivery_cost,
                                             @Part("minimum_charger") RequestBody minimum_charger,
                                             @Part MultipartBody.Part profile_image,
                                             @Part("delivery_time") RequestBody delivery_time);


    @POST("restaurant/profile")
    @Multipart
    Call<RestourantLogin> RestourantModify(@Part("email") RequestBody email,
                                           @Part("name") RequestBody name,
                                           @Part("phone") RequestBody phone,
                                           @Part("region_id") RequestBody region_id,
                                           @Part("delivery_cost") RequestBody delivery_cost,
                                           @Part("minimum_charger") RequestBody minimum_charger,
                                           @Part("availability") RequestBody availability,
                                           @Part MultipartBody.Part profile_image,
                                           @Part("api_token") RequestBody api_token,
                                           @Part("delivery_time") RequestBody delivery_time,
                                           @Part("whatsapp") RequestBody whatsapp);

    @POST("restaurant/reset-password")
    @FormUrlEncoded
    Call<RestourantLogin> RestourantResetPass(@Field("email") String email);

    @POST("restaurant/new-password")
    @FormUrlEncoded
    Call<RestourantLogin> RestourantNewPass(@Field("code") String code,
                                            @Field("password") String password,
                                            @Field("password_confirmation") String password_confirmation);


    //////////////////////////////////////Restourant Food Item /////////////////////
    @POST("restaurant/new-item")
    @Multipart
    Call<RestourantItemMeal> RestourantAddMeal(@Part("description") RequestBody description,
                                               @Part("price") RequestBody price,
                                               @Part("preparing_time") RequestBody preparing_time,
                                               @Part("name") RequestBody name,
                                               @Part MultipartBody.Part photo,
                                               @Part("api_token") RequestBody api_token,
                                               @Part("offer_price") RequestBody offer_price,
                                               @Part("category_id") RequestBody category_id);


    @POST("restaurant/update-item")
    @Multipart
    Call<RestourantItemMeal> RestourantUpdateMeal(@Part("description") RequestBody description,
                                                  @Part("price") RequestBody price,
                                                  @Part("preparing_time") RequestBody preparing_time,
                                                  @Part("name") RequestBody name,
                                                  @Part MultipartBody.Part photo,
                                                  @Part("item_id") RequestBody item_id,
                                                  @Part("api_token") RequestBody api_token,
                                                  @Part("offer_price") RequestBody offer_price);

    @POST("restaurant/delete-item")
    @FormUrlEncoded
    Call<RestourantItemMeal> RestourantDeleteItem(@Field("item_id") int item_id,
                                                  @Field("api_token") String api_token);


    @GET("restaurant/my-items")
    Call<RestourantItemMeal> getMyItemMeal(@Query("api_token") String api_token,
                                           @Query("category_id") int category_id);

//////////////////////////Restourant Offer ///////////////////////////////

    @GET("restaurant/my-offers")
    Call<RestourantOffer> RestourantGetOffer(@Query("api_token") String api_token,
                                             @Query("page") int page);


    @POST("restaurant/new-offer")
    @Multipart
    Call<RestourantOffer> RestourantAddOffer(@Part("description") RequestBody description,
                                             @Part("price") RequestBody price,
                                             @Part("starting_at") RequestBody starting_at,
                                             @Part("name") RequestBody name,
                                             @Part MultipartBody.Part photo,
                                             @Part("ending_at") RequestBody ending_at,
                                             @Part("api_token") RequestBody api_token,
                                             @Part("offer_price") RequestBody offer_price);

    @POST("restaurant/update-offer")
    @Multipart
    Call<RestourantOffer> RestourantUpdateOffer(@Part("description") RequestBody description,
                                                @Part("price") RequestBody price,
                                                @Part("starting_at") RequestBody starting_at,
                                                @Part("name") RequestBody name,
                                                @Part MultipartBody.Part photo,
                                                @Part("ending_at") RequestBody ending_at,
                                                @Part("offer_id") RequestBody offer_id,
                                                @Part("api_token") RequestBody api_token);

    @POST("restaurant/delete-offer")
    @FormUrlEncoded
    Call<RestourantOffer> RestourantDeleteOffer(@Field("offer_id") int offer_id,
                                                @Field("api_token") String api_token);

    ////////////////////////////////////


    //change model

    //change model
//    @POST("client/new-order")
//    @FormUrlEncoded
//    Call<UserLogin> newOrder();

    @GET("client/my-orders")
    Call<MyOrder> MyOrder(@Query("api_token") String api_token,
                          @Query("state") String state,
                          @Query("page") int page);

    @GET("restaurant/my-orders")
    Call<RestourantMyOrder> RestourantMyOrder(@Query("api_token") String api_token,
                                              @Query("state") String state,
                                              @Query("page") int page);

    @POST("restaurant/reject-order")
    Call<RestourantMyOrder> RestourantRejectOrder(@Field("api_token") String api_token,
                                                  @Field("order_id") int order_id,
                                                  @Field("refuse_reason") String refuse_reason);

    @POST("restaurant/accept-order")
    @FormUrlEncoded
    Call<RestourantMyOrder> RestaurantAcceptOrder(@Field("order_id") int order_id,
                                                  @Field("api_token") String api_token);

    @POST("restaurant/confirm-order")
    @FormUrlEncoded
    Call<RestourantMyOrder> RestaurantConfirmOrder(@Field("order_id") int order_id,
                                                   @Field("api_token") String api_token);

    @GET("restaurant/my-categories")
    Call<RestourantCategory> getMyCategories(@Query("api_token") String api_token);

    @GET("restaurant")
    Call<RestourantInfo> RestaurantInfo(@Query("restaurant_id") int restaurant_id);


}



