package com.miempresa.tp1sharedprefmvvm.ui.registro;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.app.Person;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.miempresa.tp1sharedprefmvvm.Model.Usuario;
import com.miempresa.tp1sharedprefmvvm.request.ApiClient;
import com.miempresa.tp1sharedprefmvvm.ui.login.MainActivity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class RegistroActivityViewModel extends AndroidViewModel {
    private MutableLiveData<Usuario> usuario;
    public String url = null;
    public RegistroActivityViewModel(@NonNull Application application) {
        super(application);



    }

    public LiveData<Usuario> getUsuario(){;
        if(usuario==null){
            usuario = new MutableLiveData<>();
        }
        return usuario;
    }
    public void usuarioSiONo(boolean bool) {
        if(bool==true){
            File archivo = new File(getApplication().getApplicationContext().getFilesDir(), "usuario.dat");

            try {
                FileInputStream fis = new FileInputStream(archivo);
                BufferedInputStream bis = new BufferedInputStream(fis);
                ObjectInputStream ois = new ObjectInputStream(bis);
                Usuario usuarioRec= (Usuario)ois.readObject();
                usuario.setValue(usuarioRec);
                ois.close();

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }


        }

    }

    public void guardarDatos(String dni, String nombre, String apellido, String mail, String password, String url){
        long DNI=Long.parseLong(dni);
        Usuario usuario = new Usuario(DNI, nombre, apellido,mail,password, url);

        File archivo = new File(getApplication().getApplicationContext().getFilesDir(), "usuario.dat");

        try {
            FileOutputStream fo = new FileOutputStream(archivo);
            BufferedOutputStream bos = new BufferedOutputStream(fo);
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(usuario);
            bos.flush();
            oos.close();
            Intent intent=new Intent(getApplication().getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplication().getApplicationContext().startActivity(intent);
        }catch (FileNotFoundException e){
            throw  new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void respuetaDeCamara(int requestCode, int resultCode, @Nullable Intent data,int REQUEST_IMAGE_CAPTURE){
        Log.d("salida",requestCode+"");
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //Recupero los datos provenientes de la camara.
            Bundle extras = data.getExtras();
            //Casteo a bitmap lo obtenido de la camara.
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            //Rutina para optimizar la foto,
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG,100, baos);




            //Rutina para convertir a un arreglo de byte los datos de la imagen
            byte [] b=baos.toByteArray();


            //Aquí podría ir la rutina para llamar al servicio que recibe los bytes.
            File archivo =new File(getApplication().getApplicationContext().getFilesDir(),"foto1.png");
            url = archivo.getAbsolutePath();
            if(archivo.exists()){
                archivo.delete();
            }
            try {
                FileOutputStream fo=new FileOutputStream(archivo);
                BufferedOutputStream bo=new BufferedOutputStream(fo);
                bo.write(b);
                bo.flush();
                bo.close();
               // Intent segunda=new Intent(context,SegundaActivity.class);
              //  segunda.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               // context.startActivity(segunda);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
