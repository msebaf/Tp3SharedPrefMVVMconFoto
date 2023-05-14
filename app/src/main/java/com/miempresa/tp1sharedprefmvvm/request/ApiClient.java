package com.miempresa.tp1sharedprefmvvm.request;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.miempresa.tp1sharedprefmvvm.Model.Usuario;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ApiClient {

    private static SharedPreferences sp;

    private static SharedPreferences conectar(Context context){
        if(sp==null){
            sp = context.getSharedPreferences("datos.xml", 0);
        }
        return sp;
    }

    public static void guardar(Context context, Usuario usuario){
        sp = conectar(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong("dni", usuario.getDni());
        editor.putString("nombre", usuario.getNombre());
        editor.putString("apellido", usuario.getApellido());
        editor.putString("mail", usuario.getMail());
        editor.putString("password", usuario.getPassword());
        editor.putString("url", usuario.getFotoUrl());
        editor.commit();

    }

    public static Usuario leer(Context context){
        sp = conectar(context);
        Long dni = sp.getLong("dni", -1);
        String apellido = sp.getString("apellido", "-1");
        String nombre = sp.getString("nombre", "-1");
        String mail = sp.getString("mail", "-1");
        String password = sp.getString("password", "-1");
        String url = sp.getString("url", null);

        Usuario usuario= new Usuario(dni,nombre,apellido,mail,password, url);
        return usuario;
    }

    public static Boolean login(Context context, String email, String pass){
        File archivo = new File(context.getApplicationContext().getFilesDir(), "usuario.dat");
        boolean log = false;
        try {
            FileInputStream fis = new FileInputStream(archivo);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);
            Usuario usuarioRec= (Usuario)ois.readObject();
            Log.d("salida", usuarioRec.apellido);
            if (usuarioRec.getMail().equals(email)&& pass.equals(usuarioRec.getPassword())){
                log=true;
            }
            ois.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    return log;
    }
}
