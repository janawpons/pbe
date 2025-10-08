/*package com.example.appbe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText hostport, username, password;
    Button loginButton;
    TextView respostaError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hostport = findViewById(R.id.hostport);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        respostaError = findViewById(R.id.respostaError);

        loginButton.setOnClickListener(view -> {
            String hostUrl = hostport.getText().toString().trim();
            String user = username.getText().toString().trim();
            String pass = password.getText().toString().trim();

            String fullUrl ="http://" + hostUrl + "?name=" + user + "&students_uid=" + pass;

            new Connexio(result -> {
                if (result.contains("Error")) {
                    respostaError.setText("Algun dels camps és incorrecte.");
                } else {
                    Intent intent = new Intent(MainActivity.this, PantallaPrincipal.class);
                    intent.putExtra("username", user);
                    intent.putExtra("hostUrl", hostUrl);
                    intent.putExtra("students_uid", pass);
                    startActivity(intent);
                    finish();
                }
            }).execute(fullUrl);
        });
    }
}
*/
package com.example.appbe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText hostport, username, password;
    Button loginButton;
    TextView respostaError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hostport = findViewById(R.id.hostport);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        respostaError = findViewById(R.id.respostaError);

        loginButton.setOnClickListener(view -> {
            String hostUrl = hostport.getText().toString().trim();
            String user = username.getText().toString().trim();
            String pass = password.getText().toString().trim();

            String fullUrl ="http://" + hostUrl + "?name=" + user + "&students_uid=" + pass;

            new Connexio(result -> {

                if (result.contains("Error")) {
                    respostaError.setText("Algun dels camps és incorrecte.");
                } else {
                    Intent intent = new Intent(MainActivity.this, PantallaPrincipal.class);
                    intent.putExtra("username", user);
                    intent.putExtra("hostUrl", hostUrl);
                    intent.putExtra("students_uid", pass);
                    startActivity(intent);
                    finish();
                }
            }).execute(fullUrl);
        });
    }
}
