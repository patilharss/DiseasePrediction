package com.example.diseaseprediction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    CardView diabetesButton,cancerButton,pneumoniaButton,maleriaButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        diabetesButton=findViewById(R.id.dbutton);
        cancerButton=findViewById(R.id.cancerButton);
        pneumoniaButton=findViewById(R.id.pbutton);
        maleriaButton=findViewById(R.id.maleriabutton);

        diabetesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),DiabetesActivity.class);
                startActivity(i);
            }
        });


        cancerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),CancerActivity.class);
                startActivity(i);
            }
        });

        pneumoniaButton.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View v){
               Intent i = new Intent(getApplicationContext(),HeartActivity.class);
               startActivity(i);
           }
        });
//
//        maleriaButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                Intent i=new Intent(getApplicationContext(),MaleriaActivity.class);
//                startActivity(i);
//            }
//        });

    }
}