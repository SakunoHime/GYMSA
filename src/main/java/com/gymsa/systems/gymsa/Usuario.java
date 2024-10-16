package com.gymsa.systems.gymsa;

public class Usuario {
    private int id; // Nuevo campo para el ID del usuario
    private String nombre;
    private String password;
    private boolean isAdmin;

    // Constructor actualizado
    public Usuario(int id, String nombre, String password, boolean isAdmin) {
        this.id = id;
        this.nombre = nombre;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    // Getter y setter para el ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setEsAdministrador(boolean esAdministrador) {
        this.isAdmin = esAdministrador;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id + // Incluir el ID en la representación de cadena
                ", nombre='" + nombre + '\'' +
                ", esAdministrador=" + isAdmin +
                '}';
    }
}
