package com.example.parcial_1_am_acn4av_onorato_tiago;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editVaccine;
    private Button btnAddVaccine;
    private LinearLayout containerVaccines;
    private EditText editAppointment;
    private Button btnSchedule;
    private LinearLayout containerAppointments;
    private TextView tvPetName, tvPetBreed;
    private Button btnEditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editVaccine = findViewById(R.id.editVaccine);
        btnAddVaccine = findViewById(R.id.btnAddVaccine);
        containerVaccines = findViewById(R.id.containerVaccines);
        editAppointment = findViewById(R.id.editAppointment);
        btnSchedule = findViewById(R.id.btnSchedule);
        containerAppointments = findViewById(R.id.containerAppointments);
        tvPetName = findViewById(R.id.tvPetName);
        tvPetBreed = findViewById(R.id.tvPetBreed);
        btnEditProfile = findViewById(R.id.btnEditProfile);

        // --- MOCK DE DATOS INICIALES ---
        registrarVacunaDinamica("Antirrábica");
        agregarConsultaDinamica("Control Mensual - 15:00hs");

        btnSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = editAppointment.getText().toString().trim();
                if (!info.isEmpty()) {
                    agregarConsultaDinamica(info);
                    editAppointment.setText("");
                }
            }
        });

        btnAddVaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vaccineName = editVaccine.getText().toString().trim();

                if (!vaccineName.isEmpty()) {
                    registrarVacunaDinamica(vaccineName);
                    editVaccine.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "Por favor, escribe el nombre de la vacuna", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoEdicion();
            }
        });
    }

    private void registrarVacunaDinamica(String nombreVacuna) {
        TextView nuevaVacunaEntry = new TextView(this);
        nuevaVacunaEntry.setText("Vacuna aplicada: " + nombreVacuna);
        nuevaVacunaEntry.setTextSize(16);
        nuevaVacunaEntry.setPadding(0, 8, 0, 8);
        nuevaVacunaEntry.setTextColor(getResources().getColor(R.color.text_main));
        containerVaccines.addView(nuevaVacunaEntry, 0);
    }

    private void agregarConsultaDinamica(String datos) {
        TextView tv = new TextView(this);
        tv.setText("Turno: " + datos);
        tv.setTextColor(getResources().getColor(R.color.white));
        tv.setTextSize(16);
        tv.setPadding(0, 8, 0, 8);
        containerAppointments.addView(tv, 0);
    }

    private void mostrarDialogoEdicion() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Editar Perfil de Mascota");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 10);

        final EditText inputName = new EditText(this);
        inputName.setHint("Nuevo nombre");
        inputName.setText(tvPetName.getText().toString());
        layout.addView(inputName);

        final EditText inputBreed = new EditText(this);
        inputBreed.setHint("Nueva raza");
        inputBreed.setText(tvPetBreed.getText().toString());
        layout.addView(inputBreed);

        builder.setView(layout);

        builder.setPositiveButton("Guardar", (dialog, which) -> {
            tvPetName.setText(inputName.getText().toString());
            tvPetBreed.setText(inputBreed.getText().toString());
            Toast.makeText(this, "Perfil actualizado", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}