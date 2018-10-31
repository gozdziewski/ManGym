package pl.edu.zut.wi.pgozdziewski.mangym.model;

public class WorkoutPlan {
    int id;
    int day;
    String exercise;

    public WorkoutPlan(int id, int day, String exercise) {
        this.id = id;
        this.day = day;
        this.exercise = exercise;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }
}
