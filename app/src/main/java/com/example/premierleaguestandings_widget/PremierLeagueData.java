package com.example.premierleaguestandings_widget;

import com.google.gson.annotations.SerializedName;

public class PremierLeagueData {
    @SerializedName("teamName")
    private String teamName;

    @SerializedName("position")
    private int position;

    @SerializedName("playedGames")
    private int playedGames;

    @SerializedName("won")
    private int won;

    @SerializedName("draw")
    private int draw;

    @SerializedName("lost")
    private int lost;

    @SerializedName("points")
    private int points;

    @SerializedName("goalsFor")
    private int goalsFor;

    @SerializedName("goalsAgainst")
    private int goalsAgainst;

    @SerializedName("goalDifference")
    private int goalDifference;

    @SerializedName("form")
    private String form;

    // Add more fields as needed

    // Getters
    public String getTeamName() {
        return teamName;
    }

    public int getPosition() {
        return position;
    }

    public int getPlayedGames() {
        return playedGames;
    }

    public int getWon() {
        return won;
    }

    public int getDraw() {
        return draw;
    }

    public int getLost() {
        return lost;
    }

    public int getPoints() {
        return points;
    }

    public int getGoalsFor() {
        return goalsFor;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public int getGoalDifference() {
        return goalDifference;
    }

    public String getForm() {
        return form;
    }

    // Setters
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setPlayedGames(int playedGames) {
        this.playedGames = playedGames;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setGoalsFor(int goalsFor) {
        this.goalsFor = goalsFor;
    }

    public void setGoalsAgainst(int goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }

    public void setGoalDifference(int goalDifference) {
        this.goalDifference = goalDifference;
    }

    public void setForm(String form) {
        this.form = form;
    }

    // Add more setters as needed
}

