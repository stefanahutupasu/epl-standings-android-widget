package com.example.premierleaguestandings_widget;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import java.util.List;

public class ApiResponseModel {
    private Filters filters;
    private Area area;
    private Competition competition;
    private Season season;
    private List<Standings> standings;

    public Filters getFilters() {
        return filters;
    }

    public void setFilters(Filters filters) {
        this.filters = filters;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public List<Standings> getStandings() {
        return standings;
    }

    public void setStandings(List<Standings> standings) {
        this.standings = standings;
    }
}


