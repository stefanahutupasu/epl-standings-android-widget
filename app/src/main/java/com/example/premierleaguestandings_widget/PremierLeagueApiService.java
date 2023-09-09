package com.example.premierleaguestandings_widget;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface PremierLeagueApiService {
    @GET("https://api.football-data.org/v4/competitions/PL/standings")
    @Headers("X-Auth-Token: 17100ddf753a471f91cdecc2ccdfc2c2")
    Call<ApiResponseModel> getPremierLeagueStandings();
}
