package com.ityang.ui;
public class GameState {
    private int[][] data;
    private String imagePath;

    public GameState(int[][] data, String imagePath) {
        this.data = data;
        this.imagePath = imagePath;
    }

    public int[][] getData() {
        return data;
    }

    public void setData(int[][] data) {
        this.data = data;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
