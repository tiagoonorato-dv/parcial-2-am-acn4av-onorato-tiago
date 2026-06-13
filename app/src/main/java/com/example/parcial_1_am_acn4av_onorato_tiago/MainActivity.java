package com.example.parcial_1_am_acn4av_onorato_tiago;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Pantalla de inicio por defecto: Noticias (Home)
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        }

        // Manejo de clics en la barra de navegación inferior
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (id == R.id.nav_profile) {
                selectedFragment = new ProfileFragment();
            } else if (id == R.id.nav_appointment) {
                selectedFragment = new AppointmentFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }
            return true;
        });
    }

    // PESTAÑA 1: NOTICIAS (HomeFragment)
    public static class HomeFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_home, container, false);
        }
    }

    // PESTAÑA 2: PERFIL DE ROCCO (ProfileFragment)
    public static class ProfileFragment extends Fragment {
        private ImageView ivPetProfile;
        private EditText etVaccineName;
        private Button btnAddVaccine, btnEditProfile, btnLogout;
        private LinearLayout containerVaccines;
        private FirebaseAuth mAuth;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_profile, container, false);

            mAuth = FirebaseAuth.getInstance();

            // Vincular componentes usando la vista "view" del fragmento
            ivPetProfile = view.findViewById(R.id.ivPetProfile);
            etVaccineName = view.findViewById(R.id.etVaccineName);
            btnAddVaccine = view.findViewById(R.id.btnAddVaccine);
            btnEditProfile = view.findViewById(R.id.btnEditProfile);
            btnLogout = view.findViewById(R.id.btnLogout);
            containerVaccines = view.findViewById(R.id.containerVaccines);

            // 1. Carga remota de la foto con Glide
            if (ivPetProfile != null) {
                Glide.with(this)
                        .load("https://images.unsplash.com/photo-1543466835-00a7907e9de1?auto=format&fit=crop&q=80&w=500")
                        .placeholder(android.R.drawable.ic_menu_gallery)
                        .error(android.R.drawable.stat_notify_error)
                        .into(ivPetProfile);
            }

            // 2. Lógica para Agregar Vacunas
            btnAddVaccine.setOnClickListener(v -> {
                String nombreVacuna = etVaccineName.getText().toString().trim();
                if (!nombreVacuna.isEmpty()) {
                    TextView nuevaVacunaEntry = new TextView(getActivity());
                    String textoFormateado = getString(R.string.prefix_vaccine, nombreVacuna);
                    nuevaVacunaEntry.setText(textoFormateado);
                    nuevaVacunaEntry.setTextSize(16);
                    nuevaVacunaEntry.setPadding(0, 8, 0, 8);
                    nuevaVacunaEntry.setTextColor(getResources().getColor(R.color.text_main));

                    containerVaccines.addView(nuevaVacunaEntry, 0);
                    etVaccineName.setText("");
                } else {
                    Toast.makeText(getActivity(), "Escribí el nombre de la vacuna", Toast.LENGTH_SHORT).show();
                }
            });

            // 3. Botón Editar Perfil (Navegación a EditProfileActivity)
            btnEditProfile.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
            });

            // 4. --- BOTÓN CERRAR SESIÓN ---
            btnLogout.setOnClickListener(v -> {
                mAuth.signOut(); // Cierra sesión en Firebase Auth
                Toast.makeText(getActivity(), "Sesión cerrada correctamente", Toast.LENGTH_SHORT).show();

                // Te saca de la app hacia el Login limpiando el historial de pantallas
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            });

            return view;
        }
    }

    // PESTAÑA 3: CONSULTAS (AppointmentFragment)
    public static class AppointmentFragment extends Fragment {
        private EditText etAppointmentDetails;
        private Button btnScheduleAppointment;
        private LinearLayout containerAppointments;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_appointment, container, false);

            // Vincular componentes usando "view"
            etAppointmentDetails = view.findViewById(R.id.etAppointmentDetails);
            btnScheduleAppointment = view.findViewById(R.id.btnScheduleAppointment);
            containerAppointments = view.findViewById(R.id.containerAppointments);

            // Lógica para Agendar Consultas
            btnScheduleAppointment.setOnClickListener(v -> {
                String detallesTurno = etAppointmentDetails.getText().toString().trim();
                if (!detallesTurno.isEmpty()) {
                    TextView nuevoTurnoEntry = new TextView(getActivity());
                    nuevoTurnoEntry.setText(detallesTurno);
                    nuevoTurnoEntry.setTextSize(16);
                    nuevoTurnoEntry.setPadding(0, 8, 0, 8);
                    nuevoTurnoEntry.setTextColor(getResources().getColor(R.color.text_main));

                    containerAppointments.addView(nuevoTurnoEntry, 0);
                    etAppointmentDetails.setText("");
                    Toast.makeText(getActivity(), "Consulta agendada con éxito", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Por favor ingresá la fecha y hora", Toast.LENGTH_SHORT).show();
                }
            });

            return view;
        }
    }
}