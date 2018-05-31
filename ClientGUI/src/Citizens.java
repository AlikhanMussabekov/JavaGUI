/*
 * Copyright (c) 2018. Alikhan Mussabekov
 * Gmail: alikhanmussabekov@gmail.com
 */

import java.awt.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Citizens implements Serializable{
    private String name;
    private int age;
    public DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public Date date = new Date();
    private String city;
    private String err = "";

    Random rand = new Random();
    float r = rand.nextFloat();
    float g = rand.nextFloat();
    float b = rand.nextFloat();
    Color color = new Color(r, g, b);

    private float newR=r,newG=g,newB=b;
    private float rStep = r/7, gStep = (1-g) / 7, bStep = b/7;

    public Color getNewColor(){
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

    public Color getOldColor() {
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

    public void resetNewColor(){
        newR=r;
        newG=g;
        newB=b;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCity() {
        if((int)(Math.random()*2) == 1){
            city = "Санкт-Петербург";
        }else {
            city = "Москва";
        }
    }

    public Random getRand() {
        return rand;
    }

    public void setRand(Random rand) {
        this.rand = rand;
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }

    public float getG() {
        return g;
    }

    public void setG(float g) {
        this.g = g;
    }

    public float getB() {
        return b;
    }

    public void setB(float b) {
        this.b = b;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }




    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public Citizens(){
        setCity();
    }

    public Citizens(String name, int age) {
        this.name = name;
        this.age = age;
        setCity();
    }

    public Citizens(String name, String age) {
        this.name = name;
        this.age = Integer.parseInt(age);
        setCity();
    }

    public Date creationDate(){
        return date;
    }

    public String getDate() {
        return dateFormat.format(date);
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAge(String age) {
        this.age = Integer.parseInt(age);
    }

    public String getCity(){
        return city;
    }

    public void printInfo(){
        if(err!="")
            System.out.printf("%s, %d, %s, %s \n", getName(), getAge(), getCity(), getDate());
        else
            System.out.println(err);
    }

    @Override
    public String toString() {
        return name + " " + age;
    }


}
