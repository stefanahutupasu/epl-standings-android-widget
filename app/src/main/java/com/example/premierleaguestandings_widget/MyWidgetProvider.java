package com.example.premierleaguestandings_widget;



import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

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



    @RequiresApi(api = Build.VERSION_CODES.M)
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scheduleWidgetUpdates(context);
        }
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
                public void onResponse(@NonNull Call<ApiResponseModel> call, @NonNull Response<ApiResponseModel> response) {
                    if (response.isSuccessful()) {
                        ApiResponseModel apiResponse = response.body();

                        // Access the data as needed
                        assert apiResponse != null;
                        List<Standings> standings = apiResponse.getStandings();
                        Log.d("StandingsData", "Standings size: " + standings.size());
                        /*for (Standings standing : standings) {
                            Log.d("StandingsData", "Stage: " + standing.getStage());
                            Log.d("StandingsData", "Type: " + standing.getType());
                            // Log other fields as needed
                            List<Table> table = standing.getTable();
                            for (Table item : table) {
                                Log.d("StandingsData", "Position: " + item.getPosition());
                                Log.d("StandingsData", "Team Name: " + item.getTeam().getName());
                                Log.d("StandingsData", "Team Crest: " + item.getTeam().getCrest());
                                Log.d("StandingsData", "Played Games: " + item.getPlayedGames());
                                Log.d("StandingsData", "Won: " + item.getWon());
                                Log.d("StandingsData", "Draw: " + item.getDraw());
                                Log.d("StandingsData", "Lost: " + item.getLost());
                                Log.d("StandingsData", "Points: " + item.getPoints());
                                Log.d("StandingsData", "Goals For: " + item.getGoalsFor());
                                Log.d("StandingsData", "Goals Against: " + item.getGoalsAgainst());
                                Log.d("StandingsData", "Goal Difference: " + item.getGoalDifference());
                            }
                        }*/

                       // formatData(standings);
                        // Process and format the data as needed
                        /*String formattedData = formatData(standings);

                        // Update the widget's TextView with the fetched data.
                        views.setTextViewText(R.id.position_1, standings.);
*/

                        populateStandingsData(context, views, standings);
                        appWidgetManager.updateAppWidget(appWidgetId, views);


                        // Update the widget.

                    } else {
                        // Handle API request error
                        Toast.makeText(context, "API request failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ApiResponseModel> call, @NonNull Throwable t) {
                    // Handle network or other errors
                    Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error fetching data", Toast.LENGTH_SHORT).show();
        }
    }


    private void populateStandingsData(Context context, RemoteViews views, List<Standings> standingsList) {
        for (Standings standings : standingsList) {
            for (int i = 0; i < 2; i++) {
                Table table = standings.getTable().get(i);



                // Set data to TextViews and ImageView
                Log.d("setting pos on row" + table.getPosition(), "column: " + getPositionViewId(context, i) + " " + R.id.position_1);
                views.setTextViewText(getPositionViewId(context, i), String.valueOf(table.getPosition()));
                /*Uri imageUri = getTeamLogoUri(table.getTeam().getCrest());

                views.setImageViewUri(getLogoViewId(context, i), imageUri);*/

                //ImageView logoImageView = views.findViewById(getLogoViewId(context, i));
                //glide.with(context).load(table.getTeam().getCrest()).into(getLogoViewId(context, i));
                Log.d("setting club on row " + table.getTeam().getName(), "column");

                /*AsyncTaskParams params = new AsyncTaskParams();
                params.url = table.getTeam().getCrest();
                params.imageViewId = getLogoViewId(context, i);
                params.context = context;

                new GetTeamLogoAsyncTask().execute(params);*/

                String tag = table.getTeam().getTla().toLowerCase(Locale.ROOT);
                views.setImageViewResource(getLogoViewId(context, i), getImageId(context, tag));
                Log.d(R.id.logo_1+ " " + R.drawable.mci, getLogoViewId(context, i) +" "+ getImageId(context, tag));
                //views.setImageViewResource(getLogoViewId(context, i), R.drawable.mci);





                views.setTextViewText(getClubViewId(context, i), table.getTeam().getShortName());
                Log.d(String.valueOf(table.getPlayedGames()), "");
                Log.d(table.getTeam().getCrest(), "");
                views.setTextViewText(getPlayedViewId(context, i), String.valueOf(table.getPlayedGames()));
                Log.d(String.valueOf(table.getWon()), "");
                views.setTextViewText(getWonViewId(context, i), String.valueOf(table.getWon()));
                Log.d(String.valueOf(table.getDraw()), "");
                views.setTextViewText(getDrawViewId(context, i), String.valueOf(table.getDraw()));
                Log.d(String.valueOf(table.getLost()), "");
                views.setTextViewText(getLostViewId(context, i), String.valueOf(table.getLost()));
                Log.d(String.valueOf(table.getGoalDifference()), "");
                views.setTextViewText(getGDViewId(context, i), String.valueOf(table.getGoalDifference()));
                Log.d(String.valueOf(table.getPoints()), "");
                views.setTextViewText(getPointsViewId(context, i), String.valueOf(table.getPoints()));
            }
        }
    }

    private int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
    }



    // Helper methods to get the view IDs based on position
    private int getPositionViewId(Context context, int position) {
        return getResourceIdByName(context, "position_" + (position + 1));
    }

    private int getLogoViewId(Context context, int position) {
        return getResourceIdByName(context, "logo_" + (position + 1));
    }

    private int getClubViewId(Context context, int position) {
        return getResourceIdByName(context, "club_" + (position + 1));
    }

    private int getPlayedViewId(Context context, int position) {
        return getResourceIdByName(context, "played_" + (position + 1));
    }

    private int getWonViewId(Context context, int position) {
        return getResourceIdByName(context, "won_" + (position + 1));
    }

    private int getDrawViewId(Context context, int position) {
        return getResourceIdByName(context, "draw_" + (position + 1));
    }

    private int getLostViewId(Context context, int position) {
        return getResourceIdByName(context, "lost_" + (position + 1));
    }

    private int getGDViewId(Context context, int position) {
        return getResourceIdByName(context, "gd_" + (position + 1));
    }

    private int getPointsViewId(Context context, int position) {
        return getResourceIdByName(context, "points_" + (position + 1));
    }

    @SuppressLint("DiscouragedApi")
    private int getResourceIdByName(Context context, String resourceName) {
        return context.getResources().getIdentifier(resourceName, "id", context.getPackageName());
    }


    private Uri getTeamLogoUri(String crestUrl) {
        // Convert the crestUrl (String) to a URI
        return Uri.parse(crestUrl);
    }





    private void formatData(List<Standings> standingsList) {
        StringBuilder formattedData = new StringBuilder();

        for (Standings standings : standingsList) {
            for (Table table : standings.getTable()) {
                int position = table.getPosition();
                String teamLogo = table.getTeam().getCrest();
                String teamName = table.getTeam().getName();
                int Pl = table.getPlayedGames();
                int W = table.getWon();
                int D = table.getDraw();
                int L = table.getLost();
                int GD = table.getGoalDifference();
                int points = table.getPoints();



                //formattedData.append(String.format("%d. %s - %d points\n", position, teamName, points));
            }
        }

        //return formattedData.toString();
    }

}
