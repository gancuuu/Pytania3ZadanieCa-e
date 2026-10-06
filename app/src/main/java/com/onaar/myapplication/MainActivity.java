package com.onaar.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {

                    Insets systemBars =
                            insets.getInsets(WindowInsetsCompat.Type.systemBars());

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                }
        );

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(this)
        );

        pobierzWydarzenia();
    }

    private void pobierzWydarzenia() {

        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService =
                retrofit.create(ApiService.class);

        Call<WydarzenieResponse> call =
                apiService.pobierzWydarzenia();

        call.enqueue(new Callback<WydarzenieResponse>() {

            @Override
            public void onResponse(
                    Call<WydarzenieResponse> call,
                    Response<WydarzenieResponse> response) {

                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {

                    List<Wydarzenie> wydarzenia =
                            response.body().getWydarzenia();

                    WydarzenieAdapter adapter =
                            new WydarzenieAdapter(wydarzenia);

                    recyclerView.setAdapter(adapter);

                } else {

                    Toast.makeText(
                            MainActivity.this,
                            "Błąd pobierania danych",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(
                    Call<WydarzenieResponse> call,
                    Throwable t) {

                progressBar.setVisibility(View.GONE);

                Toast.makeText(
                        MainActivity.this,
                        "Brak połączenia z internetem",
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }
}
