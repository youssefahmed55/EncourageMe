package com.encourage.encourageme.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.encourage.encourageme.R;
import com.encourage.encourageme.databinding.ActivityMainBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding binding;
    private MyRecycleAdapter myRecycleAdapter;
    private MyMvvm myMvvm;
    private ArrayList<String> arrayList;
    private Bundle bundle;
    private static final String TAG = "meyoussef";
    private AlarmManager alarmManager;
    private ArrayList<Integer> arrayListtimer;
    private Intent intentNotification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.addMain.setOnClickListener(this);
        binding.startMain.setOnClickListener(this);
        binding.stopMain.setOnClickListener(this);
        inti();
        load();
        observedata();
        recycleOnclick();

    }
   private void inti(){
        myRecycleAdapter = new MyRecycleAdapter();
        bundle = new Bundle();
        myMvvm = new ViewModelProvider(MainActivity.this).get(MyMvvm.class);
        arrayList = new ArrayList<>();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        arrayListtimer= new ArrayList<Integer>();
        intentNotification = new Intent(MainActivity.this,ReminderBroadcast.class);
   }

    @Override
    public void onClick(View v) {
        if(v.getId() == binding.addMain.getId()){
         if(!binding.edittextMain.getText().toString().trim().equals("")) {
             myMvvm.addnewuser(binding.edittextMain.getText().toString().trim());
             binding.edittextMain.setText("");
         }else{
             Toast.makeText(MainActivity.this, "Write Your Text First Please", Toast.LENGTH_SHORT).show();
         }
        }else if (v.getId() == binding.startMain.getId()){
               if(arrayList.size() != 0) {


                   if(binding.radiobutton1Main.isChecked()){
                       CreateNoficationwithTimer(15);
                   }else if(binding.radiobutton2Main.isChecked()){
                       CreateNoficationwithTimer(30);
                   }else if(binding.radiobutton3Main.isChecked()) {
                       CreateNoficationwithTimer(60);
                   }else{
                       Toast.makeText(MainActivity.this, "Choose One Of The Timer Options", Toast.LENGTH_SHORT).show();
                   }

               }else {
                   Toast.makeText(MainActivity.this, "Empty List , Please Add item to list First", Toast.LENGTH_SHORT).show();
               }
        }else if(v.getId() == binding.stopMain.getId()){
            StopNotifications();
        }
    }

    private void recycleOnclick(){

        myRecycleAdapter.setOnItemClick(new MyRecycleAdapter.OnItemClick() {
            @Override
            public void onClick2(int postion, View view) {
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_window, null);


                final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);

                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        myMvvm.removeuser(postion);
                        popupWindow.dismiss();
                        return true;
                    }
                });


            }
        });


    }


    public void CreatNotificationChannel(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {


            String channelId = "notifylemubit";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager  mNotificationManager2 =
                    getSystemService(NotificationManager.class);
            mNotificationManager2.createNotificationChannel(channel);

        }

    }


    protected void save(ArrayList<String> arrayList){
        SharedPreferences sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        Gson gson = new Gson();

        String json = gson.toJson(arrayList);
        sharedPreferences.edit().putString("key",json).apply();

    }
    protected void load(){
        SharedPreferences sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("key", null);

        Type type = new TypeToken<ArrayList<String>>() {}.getType();


        if (gson.fromJson(json, type) != null) {
            myMvvm.ONstartsetMutableLiveDataANDSETARRAYLIST(gson.fromJson(json, type));
        }

    }
    protected void observedata(){
        myMvvm.getUsers().observe(MainActivity.this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                save(strings);
                arrayList = strings;
                myRecycleAdapter.setList(strings);
                binding.recycleMain.setAdapter(myRecycleAdapter);
            }
        });

    }
    protected void CreateNoficationwithTimer(int minuits){
        Toast.makeText(MainActivity.this, "Start", Toast.LENGTH_SHORT).show();
        CreatNotificationChannel();
        arrayListtimer.clear();
        for(int n = 1 ; n <= arrayList.size();n++){      //Create Timer for Each Notification
            arrayListtimer.add(n*1000*60*minuits);
        }

        for(int i = 0 ; i < arrayList.size() ; i++){      //Set Alarm with Timer for Each Notification
            bundle.putString("myid",arrayList.get(i));
            intentNotification.putExtras(bundle);
            alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis() + arrayListtimer.get(i),PendingIntent.getBroadcast(MainActivity.this, i, intentNotification, 0));
        }
    }
    protected void StopNotifications(){
        if(arrayListtimer.size() != 0) {
            Toast.makeText(MainActivity.this, "Stop", Toast.LENGTH_SHORT).show();
            for (int i = 0; i < arrayListtimer.size(); i++) {
                alarmManager.cancel(PendingIntent.getBroadcast(MainActivity.this, i, intentNotification, 0));
            }
            arrayListtimer.clear();
        }else{
            Toast.makeText(MainActivity.this, "Not Found Notifications Started", Toast.LENGTH_SHORT).show();
        }
    }
}