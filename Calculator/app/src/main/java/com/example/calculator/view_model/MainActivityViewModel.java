package com.example.calculator.view_model;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.calculator.model.MainActivityModel;

import java.util.ArrayList;

public class MainActivityViewModel extends ViewModel {
    private MutableLiveData<String> inpText;
    private ArrayList<String> arrayList;
    private MainActivityModel obj;

    private String tag = this.getClass().getSimpleName();
    private long valLength;

    private MutableLiveData<String> resText;


    private MutableLiveData<String> currOpr;
    private String prevOpr;

    public void init(){
        if(inpText==null){
            inpText=new MutableLiveData<>();
            inpText.setValue(""+0);
            resText=new MutableLiveData<>();
            resText.setValue(""+0);
            prevOpr="=";
            currOpr=new MutableLiveData<>();
            currOpr.setValue("=");
            obj=new MainActivityModel();
            arrayList=obj.getArrayList();
            valLength=0;
        }
    }

    public MutableLiveData<String> getCurrOpr() {
        return currOpr;
    }

    public void setCurrOpr(String currOpr) {
        this.currOpr.setValue(currOpr);
    }

    public String getPrevOpr() {
        return prevOpr;
    }

    public void setPrevOpr(String prevOpr) {
        this.prevOpr = prevOpr;
    }

    public MutableLiveData<String> getResText() {
        return resText;
    }

    public void setResText(String resText) {
        this.resText.setValue(resText);
    }

    public ArrayList<String> getArrayList() {
        return arrayList;
    }


    public MutableLiveData<String> getInpText() {
        return inpText;
    }

    public long getValLength() {
        return valLength;
    }

    public void setValLength(long valLength) {
        this.valLength = valLength;
    }

    public void setInpText(String text) {
        this.inpText.setValue(text);

    }

    public MainActivityModel getObj() {
        return obj;
    }

    public void setObj(MainActivityModel obj) {
        this.obj = obj;
    }

    public void setBtnText(String txt){
        Log.d(tag,"Text = "+txt);


        if(txt.equalsIgnoreCase("C")){
            inpText.setValue("");
            resText.setValue("0");
            obj.setVal2(0.0);
            obj.setVal1(0.0);

            Log.d(tag,"val1 = "+obj.getVal1()+"\nval2 = "+obj.getVal2());
        }
        else if(txt.equals("=")){

            obj.setVal2(Double.parseDouble(inpText.getValue()));

            Log.d(tag,""+currOpr.getValue());
            performMath();

            currOpr.setValue(txt);

        }
        else if(txt.equals("+")||
                txt.equals("-")||
                txt.equals("*")||
                txt.equals("/")){

            currOpr.setValue(txt);
            Log.d(tag,"val1 = "+obj.getVal1()+"\nval2 = "+obj.getVal2());
            obj.setVal1(Double.parseDouble(inpText.getValue()));    //get inp from inpText
            obj.setVal2(obj.getVal2()+obj.getVal1());
            Log.d(tag,"val1 = "+obj.getVal1()+"\nval2 = "+obj.getVal2());

            obj.setVal1(-9999.0);   //acts as signal
            inpText.setValue((""+(int)(obj.getVal2())).trim());

            obj.setVal1(-9999.0);   //acts as signal . This line is required for some reason, i have added it to fix some logical issue
            Log.d(tag,"val1 = "+obj.getVal1()+"\nval2 = "+obj.getVal2());
            valLength=inpText.getValue().length();



        }
        else{
            btnTextHelperFn(txt);
        }

    }

    private void btnTextHelperFn(String txt){
        Log.d(tag,"btnTextHelperFn");

        if(getInpText().getValue().trim().equalsIgnoreCase("0"))
        {
            inpText.setValue(txt);
        }
        else {
            inpText.setValue(getInpText().getValue().trim()+txt);
        }
    }

    public void performMath(){
        Log.d(tag,"performMath");
        if(prevOpr.equals("+"))
        {
            inpText.setValue(""+((int)(obj.computeRes(1))));
        }
        else if(prevOpr.equals("-"))
        {
            inpText.setValue(""+((int)(obj.computeRes(2))));
        }
        else if(prevOpr.equals("*"))
        {
            inpText.setValue(""+((int)(obj.computeRes(3))));
        }
        else if(prevOpr.equals("/"))
        {
            inpText.setValue(""+((int)(obj.computeRes(4))));
        }
        resText.setValue(inpText.getValue());
    }

    public void performResult(){

        Log.d(tag,"performResult");
        obj.setVal2(Double.parseDouble(inpText.getValue()));
        if(prevOpr.equals("+"))
        {
            resText.setValue(""+((int)(obj.computeRes(1))));
        }
        else if(prevOpr.equals("-"))
        {
            resText.setValue(""+((int)(obj.computeRes(2))));
        }
        else if(prevOpr.equals("*"))
        {
            resText.setValue(""+((int)(obj.computeRes(3))));
        }
        else if(prevOpr.equals("/"))
        {
            resText.setValue(""+((int)(obj.computeRes(4))));
        }
    }
}
