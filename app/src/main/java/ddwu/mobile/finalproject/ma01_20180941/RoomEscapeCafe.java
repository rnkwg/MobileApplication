package ddwu.mobile.finalproject.ma01_20180941;

import java.io.Serializable;

public class RoomEscapeCafe implements Serializable {
    long _id;
    String cafeName;
    String cafeLocation;
    String themeName;
    boolean escaped;
    int difficulty;
    String genre;
    int grade;
    String review;
    String picture;

//    public RoomEscapeCafe(long _id, String cafeName, String cafeLocation, String themeName, boolean escaped, int difficulty, int horror, String genre, int grade, String review, String picture) {
//        this._id = _id;
//        this.cafeName = cafeName;
//        this.cafeLocation = cafeLocation;
//        this.themeName = themeName;
//        this.escaped = escaped;
//        this.difficulty = difficulty;
//        this.horror = horror;
//        this.genre = genre;
//        this.grade = grade;
//        this.review = review;
//        this.picture = picture;
//    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getCafeName() {
        return cafeName;
    }

    public void setCafeName(String cafeName) {
        this.cafeName = cafeName;
    }

    public String getCafeLocation() {
        return cafeLocation;
    }

    public void setCafeLocation(String cafeLocation) {
        this.cafeLocation = cafeLocation;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public boolean isEscaped() {
        return escaped;
    }

    public void setEscaped(boolean escaped) {
        this.escaped = escaped;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "RoomEscapeCafe{" +
                "_id=" + _id +
                ", cafeName='" + cafeName + '\'' +
                ", cafeLocation='" + cafeLocation + '\'' +
                ", themeName='" + themeName + '\'' +
                ", escaped=" + escaped +
                ", difficulty=" + difficulty +
                ", genre='" + genre + '\'' +
                ", grade=" + grade +
                ", review='" + review + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }
}


