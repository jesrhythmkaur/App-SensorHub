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
        @GET("/sensors/")
        Call<List<PlantData>> getJsonData();
    }

    // Custom data class
    public class PlantData {
        private int sensor_id;
        private String sensor_name;
        private String manufacturer_id;
        private boolean isOnline;

        // Constructor, getters, setters, etc.
        public int getId(){
            return sensor_id;
        }
        public String getSensorName() {
            return sensor_name;
        }
        public String getManufacturerId(){
            return manufacturer_id;
        }
        public boolean isOnline(){
            return isOnline;}
    }

    // Method to fetch JSON data using Retrofit
    private void fetchJsonData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonApiService jsonApiService = retrofit.create(JsonApiService.class);
        Call<List<PlantData>> call = jsonApiService.getJsonData();
        call.enqueue(new Callback<List<PlantData>>() {
            @Override
            public void onResponse(Call<List<PlantData>> call, Response<List<PlantData>> response) {
                if (response.isSuccessful()) {
                    List<PlantData> plantDataList = response.body();

                    updateUI(plantDataList);
                } else {

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


    private void updateUI(List<PlantData> plantDataList) {
        if (plantDataList != null && !plantDataList.isEmpty()) {
            for(int i =0; i < plantDataList.size(); i++) {
                TextView textView = findViewById(R.id.jsonTextView);
                textView.append("\nId: " + plantDataList.get(i).getId());
                textView.append("\nSensor Name: " + plantDataList.get(i).getSensorName());
                textView.append("\nManufacturer Id: " + plantDataList.get(i).getManufacturerId());
                textView.append("\nIs Online?: " + plantDataList.get(i).isOnline());
            }
        } else {

            TextView textView = findViewById(R.id.jsonTextView);
            textView.setText("No data available");
        }
    }


}
