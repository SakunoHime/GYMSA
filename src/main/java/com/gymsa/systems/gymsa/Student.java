package com.gymsa.systems.gymsa;

public class Student {
    private String id; // ID generada aleatoriamente de 5 caracteres
    private String nombres; // Nombres del estudiante
    private String apellidoPaterno; // Apellido paterno del estudiante
    private String apellidoMaterno; // Apellido materno del estudiante
    private String telefono; // Teléfono del estudiante
    private int sexo; // 0 para masculino, 1 para femenino
    private int edad; // Edad del estudiante
    private double estatura; // Estatura del estudiante
    private double peso; // Peso del estudiante
    private int horario; // Horario del estudiante (0 a 2)

    // Constructor
    public Student(String id, String nombres, String apellidoPaterno, String apellidoMaterno,
                   String telefono, int sexo, int edad, double estatura, double peso, int horario) {
        this.id = id;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.sexo = sexo;
        this.edad = edad;
        this.estatura = estatura;
        this.peso = peso;
        this.horario = horario;
    }

    public Student() {
        // Puedes inicializar valores por defecto si lo deseas
        this.id = "00000";
        this.nombres = "Nombres";
        this.apellidoPaterno = "Apellido";
        this.apellidoMaterno = "Apellido";
        this.telefono = "0";
        this.sexo = 0; // Masculino por defecto
        this.edad = 0;
        this.estatura = 0.0;
        this.peso = 0.0;
        this.horario = 0; // Horario por defecto
    }

    // Métodos getters y setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getSexo() {
        return sexo;
    }

    public void setSexo(int sexo) {
        this.sexo = sexo;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public double getEstatura() {
        return estatura;
    }

    public void setEstatura(double estatura) {
        this.estatura = estatura;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public int getHorario() {
        return horario;
    }

    public void setHorario(int horario) {
        this.horario = horario;
    }
}
