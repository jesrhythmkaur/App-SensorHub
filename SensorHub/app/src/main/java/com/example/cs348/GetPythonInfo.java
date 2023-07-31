package com.example.cs348;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class GetPythonInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_python_info);
        getSupportActionBar().hide();


        // Call the method to fetch JSON data
        fetchJsonData();
    }


    public interface JsonApiService {
        @GET("/json_data")
        Call<List<PlantData>> getJsonData();
    }

    // Your custom data class
    public class PlantData {
        private int id;
        private double measurement;
        private String location;

        // Constructor, getters, setters, etc.
        public int getId(){
            return id;
        }
        public double getMeasurement() {
            return measurement;
        }
        public String getLocation(){
            return location;
        }
    }

    // Method to fetch JSON data using Retrofit
    private void fetchJsonData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5050") // Replace with your actual Python server IP
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonApiService jsonApiService = retrofit.create(JsonApiService.class);
        Call<List<PlantData>> call = jsonApiService.getJsonData();
        call.enqueue(new Callback<List<PlantData>>() {
            @Override
            public void onResponse(Call<List<PlantData>> call, Response<List<PlantData>> response) {
                if (response.isSuccessful()) {
                    List<PlantData> plantDataList = response.body();
                    // Now you have the JSON data in plantDataList, do what you need with it.
                    // For example, you can update your UI with the fetched data.
                    updateUI(plantDataList);
                } else {
                    // Handle error case
                    Log.e("GetPythonInfo", "Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<PlantData>> call, Throwable t) {
                // Handle failure case
                Log.e("GetPythonInfo", "Failure: " + t.getMessage());
            }
        });
    }

    // Method to update the UI with the fetched data
    private void updateUI(List<PlantData> plantDataList) {
        if (plantDataList != null && !plantDataList.isEmpty()) {
            for(int i =0; i < plantDataList.size(); i++) {
                TextView textView = findViewById(R.id.jsonTextView);
                textView.append("\nId: " + plantDataList.get(i).getId());
                textView.append("\nMeasurement: " + plantDataList.get(i).getMeasurement());
                textView.append("\nLocation: " + plantDataList.get(i).getLocation());
            }
        } else {

            TextView textView = findViewById(R.id.jsonTextView);
            textView.setText("No data available");
        }
    }


}
