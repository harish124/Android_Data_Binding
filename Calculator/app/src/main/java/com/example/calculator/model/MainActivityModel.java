package com.example.calculator.model;

import java.util.ArrayList;

public class MainActivityModel {
    private ArrayList<String> arrayList;

    private double val1,val2;

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

    public double computeRes(int currOpr)
    {
        switch (currOpr){
            case 1:
                return (val1+val2);
            case 2:
                return (val1-val2);

            case 3:
                return (val1*val2);
            case 4:
                if(val2!=0)
                {

                    return (val1/val2);
                }
                else{
                    return 999999999999.0;
                }
        }

        return 0.0;
    }
}
