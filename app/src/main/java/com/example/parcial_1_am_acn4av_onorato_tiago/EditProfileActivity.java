package com.example.parcial_1_am_acn4av_onorato_tiago;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

public class EditProfileActivity extends AppCompatActivity {

    private EditText etEditName, etEditBreed;
    private Button btnSaveProfile;

    @Override
    // ARREGLADO: Ahora la estructura del método de ciclo de vida es la correcta
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        etEditName = findViewById(R.id.etEditName);
        etEditBreed = findViewById(R.id.etEditBreed);
        btnSaveProfile = findViewById(R.id.btnSaveProfile);

        String currentName = getIntent().getStringExtra("current_name");
        String currentBreed = getIntent().getStringExtra("current_breed");

        etEditName.setText(currentName);
        etEditBreed.setText(currentBreed);

        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = etEditName.getText().toString();
                String newBreed = etEditBreed.getText().toString();

                // Preparamos el paquete de vuelta
                Intent returnIntent = new Intent();
                returnIntent.putExtra("new_name", newName);
                returnIntent.putExtra("new_breed", newBreed);

                // --- NUEVO: Ponelo acá para que avise al usuario que se guardó ---
                Toast.makeText(EditProfileActivity.this, getString(R.string.toast_profile_updated), Toast.LENGTH_SHORT).show();

                // Decimos que la operación fue exitosa y cerramos esta pantalla
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}