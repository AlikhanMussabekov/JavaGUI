/*
 * Copyright (c) 2018. Alikhan Mussabekov
 * Gmail: alikhanmussabekov@gmail.com
 */

import java.awt.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

public class Citizens implements Serializable{
    private String name;
    private int age;
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    Date startingDate = new Date();
    GregorianCalendar gc = new GregorianCalendar();
    Date date;
    private String city;

    LocalDateTime localDateTime = LocalDateTime.now();

    Random rand = new Random();
    float r = rand.nextFloat();
    float g = rand.nextFloat();
    float b = rand.nextFloat();
    Color color = new Color(r, g, b);

    private float newR=r,newG=g,newB=b;
    private float rStep = r/7, gStep = (1-g) / 7, bStep = b/7;

    Color getNewColor(){
        newR-=rStep;
        newG+=gStep;
        newB-=bStep;

        if (newR < 0){
            newR = 0f;
        }
        if (newG>1){
            newG = 0.8f;
        }
        if (newB < 0){
            newB = 0f;
        }

        return new Color(newR,newG,newB);
    }

    Color getOldColor() {
        newR+=rStep;
        newG-=gStep;
        newB+=bStep;

        if (newR > r){
            newR = r;
        }
        if (newG < g) {
            newG = g;
        }
        if (newB > b){
            newB = b;
        }

        return new Color(newR,newG,newB);
    }

    void resetNewColor(){
        newR=r;
        newG=g;
        newB=b;
    }

    void setCity() {
        if((int)(Math.random()*2) == 1){
            city = "Санкт-Петербург";
        }else {
            city = "Москва";
        }
    }

    public Color getColor() {
        return color;
    }

    public Citizens(){
        setCity();
    }

    private void setCitizensDate() {
        gc.setTime(startingDate);
        gc.add(Calendar.YEAR, -getAge());
        date = gc.getTime();
    }

    public Citizens(String name, int age) {
        this.name = name;
        this.age = age;
        setCity();
        //setCitizensDate();
    }

    public Citizens(String name, String age) {
        this.name = name;
        this.age = Integer.parseInt(age);
        setCity();
        //setCitizensDate();
    }

    Date citizensDate(){
        return date;
    }

    String getDate() {
        gc.setTime(startingDate);
        gc.add(Calendar.YEAR, -getAge());
        date = gc.getTime();
        return dateFormat.format(date);
    }

    String getName() {

        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    int getAge() {
        return age;
    }

    void setAge(int age) {
        this.age = age;
        setCitizensDate();
    }

    void setAge(String age) {
        this.age = Integer.parseInt(age);
    }

    String getCity(){
        return city;
    }

    void printInfo(){
            System.out.printf("%s, %d, %s, %s \n", getName(), getAge(), getCity(), getDate());
        }

    @Override
    public String toString() {
        return name + " " + age;
    }


}
