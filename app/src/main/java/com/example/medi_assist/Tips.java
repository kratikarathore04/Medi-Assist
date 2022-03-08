package com.example.medi_assist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Tips extends AppCompatActivity {
    TextView et1,et2;
    String s1="\t\t\t\t\t\t\n" +
            "\n" +
            "* Think Positive.\n" +
            "* Eat a healthy diet.\n" +
            "* Consume less salt and sugar.\n" +
            "* Reduce intake of harmful fats.\n" +
            "* Clean your hands properly.\n" +
            "* Check your blood pressure regularly.\n" +
            "* Be active.\n" +
            "* Get Vaccinated.\n";

    String s2="\t\t\t\t\t\t\t \n" +
            "* You should also eat a wide range of foods to make sure you're getting a balanced diet and your body is receiving all the nutrients it needs.\n" +
            "* If you eat or drink more than your body needs, you'll put on weight because the energy you do not use is stored as fat. If you eat and drink too little, you'll lose weight.\n" +
            "* The key to a healthy diet is to eat the right amount of calories for how active you are so you balance the energy you consume with the energy you use.\n" +
            "* It's recommended that men have around 2,500 calories a day (10,500 kilojoules). Women should have around 2,000 calories a day (8,400 kilojoules).\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        et1=findViewById(R.id.tip1);
        et2=findViewById(R.id.tip2);
        et1.setText(s1);
        et2.setText(s2);
    }
}