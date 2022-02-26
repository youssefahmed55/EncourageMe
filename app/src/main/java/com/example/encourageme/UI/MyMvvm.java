package com.example.encourageme.UI;


import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;


public class MyMvvm extends ViewModel {

  private MutableLiveData<ArrayList<String>> mutableLiveData ;
  private ArrayList<String> arrayList = new ArrayList<>()  ;

    public LiveData<ArrayList<String>> getUsers() {

        if (mutableLiveData == null) {
            mutableLiveData = new MutableLiveData<ArrayList<String>>();
            mutableLiveData.setValue(loadUsers());
        }
        return mutableLiveData;
    }

    public void ONstartsetMutableLiveDataANDSETARRAYLIST(ArrayList<String> arrayList1) {
        arrayList = arrayList1;
        this.mutableLiveData = new MutableLiveData<ArrayList<String>>();
        mutableLiveData.setValue(arrayList);
    }

    private ArrayList<String> loadUsers() {
        arrayList.add("You Can Do it");
        arrayList.add("Trust me , You Can Do it");
        arrayList.add("Don’t give up");
        arrayList.add("Keep pushing");
        arrayList.add("Keep fighting");
        arrayList.add("Stay strong");
        arrayList.add("Never give up");
        arrayList.add("Never say ‘die’");
        arrayList.add("Come on! You can do it");
        return arrayList;
    }
    public void  addnewuser(String s){
        arrayList.add(s);
        mutableLiveData.setValue(arrayList);
    }

    public void  removeuser(int postion){
        arrayList.remove(postion);
        mutableLiveData.setValue(arrayList);
    }
}
