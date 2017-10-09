package com.kjh.seoulapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FirebaseAPI
{
    @GET("/member/{uid}.json")
    Call<MemberData> getMemberData(@Path("uid") String uid);

//    @GET("/member.json")
//    Call<String> getMemberData();
}
