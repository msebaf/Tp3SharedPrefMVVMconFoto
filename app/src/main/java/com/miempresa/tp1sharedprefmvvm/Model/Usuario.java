package com.miempresa.tp1sharedprefmvvm.Model;

import java.io.Serializable;

public class Usuario implements Serializable {
    public long dni;
    public String Nombre;
    public String apellido;
    public String mail;
    public String password;
    public String fotoUrl;


    public Usuario(Long dni, String nombre, String apellido, String mail, String password, String url) {
        this.dni = dni;
        Nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.password = password;
        this.fotoUrl=url;
    }

    public Usuario(){};

    public long getDni() {
        return dni;
    }

    public void setDni(long dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "dni=" + dni +
                ", Nombre='" + Nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
