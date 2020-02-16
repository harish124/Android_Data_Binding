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
            reset();

            Log.d(tag,"val1 = "+obj.getVal1()+"\nval2 = "+obj.getVal2());
            currOpr.setValue("C");
        } else if (txt.equals("=")) {
            computeResult(prevOpr);
            if (!inpText.getValue().equals("Can\'t divide by zero")) {
                inpText.setValue("" + obj.getTot());
            }
            Log.d(tag, "Zero = " + inpText.getValue());
            currOpr.setValue("=");
            prevOpr = txt;
        } else if (txt.equals("%")) {
            //obj.setTot(Double.parseDouble(inpText.getValue().toString())/100.0);
            String temp = "" + (Double.parseDouble(inpText.getValue().toString()) / 100.0);
            Log.d(tag, Double.parseDouble(inpText.getValue().toString()) + "% = " + obj.getTot());
            inpText.setValue(temp);
            //prevOpr=txt;
        }

        else if(txt.equals("+")||
                txt.equals("-")||
                txt.equals("*")||
                txt.equals("/")
        ) {

            if (obj.isInitState()) {
                currOpr.setValue(txt);
                if (inpText.getValue().equals("Can\'t divide by zero") ||
                        inpText.getValue().isEmpty()) {
                    reset();
                    return;

                }
                obj.setTot(Double.parseDouble(inpText.getValue().toString()));
                Log.d(tag, "inpText = " + inpText.getValue().toString());
                prevOpr = txt;
                obj.setInitState(false);
            } else {
                if (isArithmeticOpr(currOpr.getValue()) == 1) {
                    currOpr.setValue(txt);
                    prevOpr = txt;
                    Log.d(tag, "currOpr is also arithmetic");
                    return;
                }
                currOpr.setValue(txt);
                computeResult(prevOpr);
                prevOpr = txt;
            }
        }

        else{
            if (prevOpr.equals("+") ||
                    prevOpr.equals("-") ||
                    prevOpr.equals("*") ||
                    prevOpr.equals("/") ||
                    prevOpr.equals("%")) {
                if (currOpr.getValue().equals("+") ||
                        currOpr.getValue().equals("-") ||
                        currOpr.getValue().equals("*") ||
                        currOpr.getValue().equals("/") ||
                        currOpr.getValue().equals("%")) {
                    inpText.setValue("");
                }
            }
            btnTextHelperFn(txt);
            currOpr.setValue(txt);
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

    void computeResult(String prvOpr) {
        Log.d(tag, "prvOpr = " + prvOpr);
        if (inpText.getValue().isEmpty()) {
            return;
        }
        if (inpText.getValue().equals("Can\'t divide by zero"))
        {
            reset();
            return;
        }
        if (prvOpr.equals("+")) {
            Log.d(tag, "inpText = " + inpText.getValue().toString());
            obj.setTot(obj.getTot() + Double.parseDouble(inpText.getValue().toString()));
            inpText.setValue("" + obj.getTot());
        }

        if (prvOpr.equals("-")) {
            obj.setTot(obj.getTot() - Double.parseDouble(inpText.getValue().toString()));
            inpText.setValue("" + obj.getTot());
        }

        if (prvOpr.equals("*")) {
            obj.setTot(obj.getTot() * Double.parseDouble(inpText.getValue().toString()));
            inpText.setValue("" + obj.getTot());
        }

        if (prvOpr.equals("/")) {
            obj.setVal1(Double.parseDouble(inpText.getValue().toString()));
            if (obj.getVal1() != 0.0) {
                obj.setTot(obj.getTot() / obj.getVal1());
                inpText.setValue("" + obj.getTot());
            } else {
                inpText.setValue("Can't divide by zero");
                obj.setInitState(true);
                obj.setTot(0.0);
            }
        }
        if (prvOpr.equals("%"))
        {
//            obj.setTot(Double.parseDouble(inpText.getValue().toString())/100.0);
//            Log.d(tag,Double.parseDouble(inpText.getValue().toString())+"% = "+obj.getTot());
//            inpText.setValue(""+obj.getTot());
        }


        if (prvOpr.equals("="))
        {
            prevOpr = currOpr.getValue();
        }

    }

    public int isArithmeticOpr(String opr) {
        if (opr.equals("+") ||
                opr.equals("-") ||
                opr.equals("*") ||
                opr.equals("/") ||
                opr.equals("%"))
        {
            Log.d(tag, "isArithmeticOpr = 1");
            return 1;

        }
        Log.d(tag, "isArithmeticOpr = 0");
        return 0;
    }

    void reset() {
        obj.setInitState(true);
        obj.setTot(0.0);
        inpText.setValue("");
    }


}
