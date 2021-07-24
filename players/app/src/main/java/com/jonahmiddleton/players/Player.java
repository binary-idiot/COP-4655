package com.jonahmiddleton.players;

import androidx.annotation.NonNull;

public class Player{
    private String name;
    private int id, wins, losses, ties;

    public Player(int id, String name, int wins, int losses, int ties){
        this.id = id;
        this.name = name;
        this.wins = wins;
        this.losses = losses;
        this.ties = ties;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getTies() {
        return ties;
    }

    @NonNull
    @Override
    public String toString() {
        return getName();
    }
}
