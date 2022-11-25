package pkg;

public class DayBirth {
    //variables
    private int year;
    private int month;
    private Integer day;
    private char gender;
    private int births;
    //constructor
    public DayBirth(int year, int month, Integer day, char gender, int births) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.gender = gender;
        this.births = births;
    }
    //set
    public void setYear(int year) {
        this.year = year;
    }
    public void setMonth(int month) {
        this.month = month;
    }
    public void setDay(Integer day) {
        this.day = day;
    }
    public void setGender(char gender) {
        this.gender = gender;
    }
    public void setBirths(int births) {
        this.births = births;
    }
    //get
    public int getYear() {
        return year;
    }
    public int getMonth() {
        return month;
    }
    public Integer getDay() {
        return day;
    }
    public char getGender() {
        return gender;
    }
    public int getBirths() {
        return births;
    }
}