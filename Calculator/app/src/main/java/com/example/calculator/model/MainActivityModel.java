package com.example.calculator.model;

import java.util.ArrayList;

public class MainActivityModel {
    private ArrayList<String> arrayList;

    private double val1, val2, tot = 0.0;
    private boolean initState = true;

    public MainActivityModel(Double val1, Double val2) {
        this.val1 = val1;
        this.val2 = val2;
    }

    public MainActivityModel() {
        arrayList=new ArrayList<>();
        for(int i=-1;i<9;i++){
            arrayList.add(""+(i+1));
        }

        arrayList.add("+"); //10
        arrayList.add("-"); //11
        arrayList.add("*"); //12
        arrayList.add("/"); //13
        arrayList.add("%"); //14

        arrayList.add("C"); //15
        arrayList.add("="); //16

        val1=0.0;
        val2=0.0;
    }

    public boolean isInitState() {
        return initState;
    }

    public void setInitState(boolean initState) {
        this.initState = initState;
    }

    public double getTot() {
        return tot;
    }

    public void setTot(double tot) {
        this.tot = tot;
    }

    public ArrayList<String> getArrayList() {
        return arrayList;
    }

    public double getVal1() {
        return val1;
    }

    public void setVal1(Double val1) {
        this.val1 = val1;
    }

    public double getVal2() {
        return val2;
    }

    public void setVal2(Double val2) {
        this.val2 = val2;
    }


}
