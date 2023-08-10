package com.example.cs348;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


        // Find the button by its ID
    Button nextButton = findViewById(R.id.getPythonInfo);

    // Set OnClickListener for the button
        nextButton.setOnClickListener(new View.OnClickListener(){

        public void onClick(View v){

        Intent intent = new Intent(MainActivity.this, GetPythonInfo.class);
        startActivity(intent);
    }
        });

}
}