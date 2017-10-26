package com.kjh.seoulapp;

import android.util.Log;

/**
 * Created by KMG on 2017-10-26.
 */

public class CulturalData {
    private String title;
    private static String content;
    private String pro1;
    private String pro2;
    private String pro3;
    private boolean ans1;
    private boolean ans2;
    private boolean ans3;

    public CulturalData() { }

    public CulturalData(String title, String content, String pro1, String pro2, String pro3, boolean ans1, boolean ans2, boolean ans3) {
        this.title = title;
        this.content = content;
        this.pro1 = pro1;
        this.pro2 = pro2;
        this.pro3 = pro3;
        this.ans1 = ans1;
        this.ans2 = ans2;
        this.ans3 = ans3;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        Log.v("asdf",this.title);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        Log.v("asdf",this.content);
    }

    public String getPro1(){
        return pro1;
    }

    public void setPro1(String pro1){
        this.pro1 = pro1;
    }

    public String getPro2(){
        return pro2;
    }

    public void setPro2(String pro2){
        this.pro2 = pro2;
    }

    public String getPro3(){
        return pro3;
    }

    public void setPro3(String pro3){
        this.pro3 = pro3;
    }

    public boolean getAns1(){
        return ans1;
    }

    public void setAns1(boolean ans1){
        this.ans1 = ans1;
        Log.v("asdf", String.valueOf(this.ans1));
}

    public boolean getAns2(){
        return ans2;
    }

    public void setAns2(boolean ans2){
        this.ans2 = ans2;
    }

    public boolean getAns3(){
        return ans3;
    }

    public void setAns3(boolean ans3){
        this.ans3 = ans3;
    }
}
