package com.miempresa.tp1sharedprefmvvm.ui.login;

import static androidx.core.content.PermissionChecker.checkSelfPermission;

import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.miempresa.tp1sharedprefmvvm.Model.Usuario;
import com.miempresa.tp1sharedprefmvvm.request.ApiClient;
import com.miempresa.tp1sharedprefmvvm.ui.registro.RegistroActivity;
import static android.Manifest.permission.CAMERA;

import java.io.Serializable;

public class MainActivityViewModel extends AndroidViewModel {
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public void maLogin(String mail, String password){
        if(mail.length()>0 && password.length()>0) {
            boolean log = ApiClient.login(getApplication().getApplicationContext(), mail, password);
            Log.d("revisar",mail);
            if(log){
                Log.d("entro", "si");
            }

            if (log == true) {
                Intent intent = new Intent(getApplication().getApplicationContext(), RegistroActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Log", log);
                getApplication().getApplicationContext().startActivity(intent);

            }
        }
    }
}
