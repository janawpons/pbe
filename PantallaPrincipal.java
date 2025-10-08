package com.example.appbe;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;
import android.os.Looper;
import android.widget.TableLayout;
import android.content.Intent;


public class PantallaPrincipal extends AppCompatActivity {

    private static final long SESSION_TIMEOUT = 5 * 60 * 1000;

    private Handler sessionHandler;
    private Runnable sessionRunnable;

    Button logout, send;

    EditText consulta;

    TextView missatgeWelcome, respostaError, titolTaula;

    TableLayout taula;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_principal);

        logout = findViewById(R.id.logout);
        send = findViewById(R.id.send);
        consulta = findViewById(R.id.consulta);
        missatgeWelcome = findViewById(R.id.missatgeWelcome);
        respostaError = findViewById(R.id.respostaError);
        titolTaula = findViewById(R.id.titolTaula);
        taula = findViewById(R.id.taula);



        TextView welcomeText = findViewById(R.id.missatgeWelcome);
        String username = getIntent().getStringExtra("username");
        String hostUrl = getIntent().getStringExtra("hostUrl");

        welcomeText.setText("Welcome: " + username + "!");

        send.setOnClickListener(view -> {
            startSessionTimer();
            String query = consulta.getText().toString().trim();
            String students_uid = getIntent().getStringExtra("students_uid");

            int index = hostUrl.indexOf('/') + 1;
            /*String fullUrl = "http://" + hostUrl.substring(0,index)+ query;;
            if (query.contains("marks")) {
                if (query.equals("marks"))  fullUrl += "?student_id=" + students_uid;
                else fullUrl = fullUrl+ "&student_id=" + students_uid;
            }*/

            String fullUrl = "http://" + hostUrl.substring(0, index) + query;
            if (query.equals("marks")) {
                fullUrl = fullUrl + "?student_id=" + students_uid;
            } else if (query.contains("marks")) {
                fullUrl = fullUrl + "&student_id=" + students_uid;
            }
            String dataType;
            if (query.indexOf('?') != -1) {
                dataType = query.substring(0, query.indexOf('?'));
            } else {
                dataType = query;
            }

            new Connexio(result -> {
                if (result.contains("Error")) {
                    taula.removeAllViews();
                    titolTaula.setText("");
                    respostaError.setText("Consulta amb format incorrecte");
                } else {
                    Taules.crearTaula(PantallaPrincipal.this, result, taula, respostaError, titolTaula, dataType);
                }
            }).execute(fullUrl);
        });

        logout.setOnClickListener(v -> {
            Intent intent = new Intent(PantallaPrincipal.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        sessionHandler = new Handler(Looper.getMainLooper());
        sessionRunnable = () -> {
            Intent intent = new Intent(PantallaPrincipal.this, MainActivity.class);
            startActivity(intent);
            finish();
        };

        startSessionTimer();
    }

    private void startSessionTimer() {
        sessionHandler.removeCallbacks(sessionRunnable);
        sessionHandler.postDelayed(sessionRunnable, SESSION_TIMEOUT);
    }
}