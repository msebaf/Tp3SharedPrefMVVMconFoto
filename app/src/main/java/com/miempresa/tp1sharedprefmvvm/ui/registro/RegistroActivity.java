package com.miempresa.tp1sharedprefmvvm.ui.registro;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.miempresa.tp1sharedprefmvvm.Model.Usuario;
import com.miempresa.tp1sharedprefmvvm.R;
import com.miempresa.tp1sharedprefmvvm.databinding.ActivityRegistroBinding;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class RegistroActivity extends AppCompatActivity {
    private ActivityRegistroBinding binding;
    private RegistroActivityViewModel mv;
    private static int REQUEST_IMAGE_CAPTURE=1;
    private Bitmap imageBitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroBinding.inflate(getLayoutInflater());
        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(RegistroActivityViewModel.class);

        //setContentView(R.layout.activity_registro);
        setContentView(binding.getRoot());
        Intent intent = getIntent();

        mv.getUsuario().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                binding.etApellido.setText(usuario.getApellido());
                binding.etDni.setText(usuario.getDni()+"");
                binding.etNombre.setText(usuario.getNombre());
                binding.etMail.setText(usuario.getMail());
                binding.etPassword.setText(usuario.getPassword());


                if (usuario.fotoUrl != null) {
                    // Carga la imagen en ivFoto utilizando Glide
                    Glide.with(RegistroActivity.this)
                            .load(usuario.fotoUrl)
                            .into(binding.ivFoto);
                } else {
                    // Si la URL de la imagen no está disponible, muestra una imagen predeterminada
                    binding.ivFoto.setImageResource(R.drawable.avatar);
                }
            }
        });

        mv.usuarioSiONo(intent.getBooleanExtra("Log", false));

        binding.btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mv.guardarDatos(binding.etDni.getText().toString(), binding.etNombre.getText().toString(),
                        binding.etApellido.getText().toString(), binding.etMail.getText().toString(),
                        binding.etPassword.getText().toString(), mv.url);
            }
        });

        binding.btTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tomarFoto();
            }
        });


    }

    private void tomarFoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    // método para manejar el resultado de la actividad de tomar foto
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            ImageView ivFoto = findViewById(R.id.ivFoto);
            ivFoto.setImageBitmap(imageBitmap);
            mv.respuetaDeCamara(requestCode,resultCode,data,REQUEST_IMAGE_CAPTURE);
        }
    }


}