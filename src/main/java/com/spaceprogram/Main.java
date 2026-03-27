package com.spaceprogram;

public class Main {
    public static void main(String[] args) {
        // El panel de donde sacan toda la informacion y pueden modificarla
        TelemetrySystem panel = new TelemetrySystem();

        // La interfaz ASCII
        SpaceDashboard dashboard = new SpaceDashboard();

        // Los hilos
        Enviroment enviroment = new Enviroment(panel); // El entorno se encarga de generar los daños a la nave | Es unicamente escritor

        Astronaut jebediah = new Astronaut(panel, "Jebediah");   // Astronauta 1 | Es escritor y lector
        Astronaut valentina = new Astronaut(panel, "Valentina"); // Astronauta 2 | Es escritor y lector

        Monitor monitor = new Monitor(panel, dashboard); // El que imprime el texto, es un lector, no modifica nada, solo muestra la información en la pantalla

        // Iniciamos los hilos
        new Thread(enviroment).start();
        new Thread(monitor).start();
        new Thread(jebediah).start();
        new Thread(valentina).start();
    }
}