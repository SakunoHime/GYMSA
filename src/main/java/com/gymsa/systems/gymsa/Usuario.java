package com.gymsa.systems.gymsa;

public class Usuario {
    private String nombre;
    private String password;
    private boolean esAdministrador;

    // Constructor
    public Usuario(String nombre, String password, boolean esAdministrador) {
        this.nombre = nombre;
        this.password = password;
        this.esAdministrador = esAdministrador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEsAdministrador() {
        return esAdministrador;
    }

    public void setEsAdministrador(boolean esAdministrador) {
        this.esAdministrador = esAdministrador;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", esAdministrador=" + esAdministrador +
                '}';
    }
}
