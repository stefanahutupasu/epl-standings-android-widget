package com.example.premierleaguestandings_widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d("MyWidgetProvider", "onUpdate called");

        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

            // Call the modified method to fetch and update the widget
            fetchDataFromAPI(context, appWidgetId, appWidgetManager, views);
        }
    }



    private void scheduleWidgetUpdates(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        try {
            // Replace your existing PendingIntent creation code with this:
            Intent intent = new Intent(context, MyWidgetProvider.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

            // Schedule updates every X milliseconds (adjust as needed).
            long updateIntervalMillis = 3600000; // 1 hour
            alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), updateIntervalMillis, pendingIntent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error scheduling widget updates", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onEnabled(Context context) {
        // This method is called when the first instance of your widget is added to the home screen.
        // You can use it to initialize any resources or setup tasks.

        // Schedule periodic updates when the first widget instance is added.
        scheduleWidgetUpdates(context);
    }

    private void fetchDataFromAPI(Context context, int appWidgetId, AppWidgetManager appWidgetManager, RemoteViews views) {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.football-data.org/v4/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            PremierLeagueApiService apiService = retrofit.create(PremierLeagueApiService.class);

            Call<ApiResponseModel> call = apiService.getPremierLeagueStandings();

            call.enqueue(new Callback<ApiResponseModel>() {
                @Override
                public void onResponse(Call<ApiResponseModel> call, Response<ApiResponseModel> response) {
                    if (response.isSuccessful()) {
                        ApiResponseModel apiResponse = response.body();

                        // Access the data as needed
                        List<Standings> standings = apiResponse.getStandings();
                        Log.d("StandingsData", "Standings size: " + standings.size());
                        for (Standings standing : standings) {
                            Log.d("StandingsData", "Stage: " + standing.getStage());
                            Log.d("StandingsData", "Type: " + standing.getType());
                            // Log other fields as needed
                            List<Table> table = standing.getTable();
                            for (Table item : table) {
                                Log.d("StandingsData", "Position: " + item.getPosition());
                                Log.d("StandingsData", "Team Name: " + item.getTeam().getName());
                                Log.d("StandingsData", "Played Games: " + item.getPlayedGames());
                                Log.d("StandingsData", "Won: " + item.getWon());
                                Log.d("StandingsData", "Draw: " + item.getDraw());
                                Log.d("StandingsData", "Lost: " + item.getLost());
                                Log.d("StandingsData", "Points: " + item.getPoints());
                                Log.d("StandingsData", "Goals For: " + item.getGoalsFor());
                                Log.d("StandingsData", "Goals Against: " + item.getGoalsAgainst());
                                Log.d("StandingsData", "Goal Difference: " + item.getGoalDifference());
                            }
                        }

                        // Process and format the data as needed
                        String formattedData = formatData(standings);

                        // Update the widget's TextView with the fetched data.
                        views.setTextViewText(R.id.widget_text, formattedData);

                        // Update the widget.
                        appWidgetManager.updateAppWidget(appWidgetId, views);
                    } else {
                        // Handle API request error
                        Toast.makeText(context, "API request failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponseModel> call, Throwable t) {
                    // Handle network or other errors
                    Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error fetching data", Toast.LENGTH_SHORT).show();
        }
    }



    private String formatData(List<Standings> standingsList) {
        StringBuilder formattedData = new StringBuilder();

        for (Standings standings : standingsList) {
            for (Table table : standings.getTable()) {
                String teamName = table.getTeam().getName();
                int position = table.getPosition();
                int points = table.getPoints();

                formattedData.append(String.format("%d. %s - %d points\n", position, teamName, points));
            }
        }

        return formattedData.toString();
    }

}
