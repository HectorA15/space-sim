package org.example;

import java.util.Random;

/**
 * El panel de donde sacan toda la informacion y pueden modificarla, es un recurso compartido, es un monitor, tiene metodos sincronizados para evitar condiciones de carrera
 * 1. Tiene datos de la nave como temperatura, presion, gravedad, nivel de oxigeno, nivel de energia, nivel de thrusters
 * 2. Tiene metodos para actualizar los datos de la nave, como actualizar los daños, actualizar los datos del entorno, reparar los daños, investigar las cosas
 * 3. Tiene metodos para verificar si alguien esta reparando algo o investigando algo, para evitar que dos astronautas reparen o investiguen lo mismo
 * 4. Tiene metodos para verificar si la misión se ha completada, si se ha completada se regresa a la tierra, se acaba el hilo del astronauta y el monitor
 * 5. Tiene metodos para verificar si hay una emergencia, si hay una emergencia se tiene que reparar lo antes posible, si no hay emergencia se puede investigar
 * 6. Tiene metodos para verificar si el progreso de una investigacion no ha alcanzado su maximo y si alguien lo esta investigando actualmente, para evitar que dos astronautas investiguen lo mismo
 *
 */
public class TelemetrySystem {
    // things to research
    double temperature;
    double pressure;
    double gravity;

    // progress of research
    double gravityProgress;
    double pressureProgress;
    double temperatureProgress;

    // things to maintain
    double thrustersLevel;
    double oxygenLevel;
    double energyLevel;

    // booleans
    String repairingOxygen = "";
    String repairingThrusters = "";
    String repairingEnergy = "";

    String researchingTemperature = "";
    String researchingPressure = "";
    String researchingGravity = "";

    Random random = new Random();
    public TelemetrySystem() {
        this.temperature = -1.0;
        this.pressure = 101.3;
        this.gravity = 8.7;

        this.gravityProgress = 0.0;
        this.pressureProgress = 0.0;
        this.temperatureProgress = 0.0;

        this.oxygenLevel  = 100.0;
        this.thrustersLevel = 100.0;
        this.energyLevel = 100.0;
    }
    // =============== SETTERS ================

    // son booleanos algo diferente reciben algo como  setRepairingOxygen(true) o setRepairingOxygen(false) para indicar si se esta reparando o no,
    // pero internamente se guarda el nombre del astronauta que lo esta reparando, si no hay nadie reparando se guarda una cadena vacia
    // es decir !true (osea si es falso) entonces se guarda una cadena vacia, si es true
    // entonces no se hace nada porque ya se guardo el nombre del astronauta que lo esta reparando anteriormente con el metodo occupyOxygen, occupyThrusters o occypyEnergy por ejemplo
    public synchronized void setRepairingOxygen(boolean status) { if (!status) this.repairingOxygen = ""; }
    public synchronized void setRepairingThrusters(boolean status) { if (!status) this.repairingThrusters = ""; }
    public synchronized void setRepairingEnergy(boolean status) { if (!status) this.repairingEnergy = "";}

    public synchronized void setResearchingTemperature(boolean researchingTemperature) { if (!researchingTemperature) this.researchingTemperature = "";}
    public synchronized void setResearchingPressure(boolean researchingPressure) {if (!researchingPressure) this.researchingPressure = "";}
    public synchronized void setResearchingGravity(boolean researchingGravity) { if (!researchingGravity) this.researchingGravity = "";}
    // =============== GETTERS ================
    public synchronized double getTemperature() { return this.temperature;}
    public synchronized double getPressure() { return this.pressure;}
    public synchronized double getGravity() { return this.gravity;}

    public synchronized double getGravityProgress() { return this.gravityProgress;}
    public synchronized double getPressureProgress() { return this.pressureProgress;}
    public synchronized double getTemperatureProgress() { return this.temperatureProgress;}

    public synchronized double getOxygenLevel() { return this.oxygenLevel;}
    public synchronized double getThrustersLevel() { return this.thrustersLevel;}
    public synchronized double getEnergyLevel() { return this.energyLevel;}

    public synchronized String getWorkerOxygen() { return this.repairingOxygen; }
    public synchronized String getWorkerThrusters() { return this.repairingThrusters; }
    public synchronized String getWorkerEnergy() { return this.repairingEnergy; }
    public synchronized String getWorkerTemperature() { return this.researchingTemperature; }
    public synchronized String getWorkerPressure() { return this.researchingPressure; }
    public synchronized String getWorkerGravity() { return this.researchingGravity; }

    // =============== BOOLEANS ================
    public synchronized boolean isRepairingOxygen() { return !this.repairingOxygen.equals(""); }
    public synchronized boolean isRepairingThrusters() { return !this.repairingThrusters.equals(""); }
    public synchronized boolean isRepairingEnergy() { return !this.repairingEnergy.equals(""); }

    public synchronized boolean isResearchingTemperature() { return !this.researchingTemperature.equals("");}
    public synchronized boolean isResearchingPressure() { return !this.researchingPressure.equals("");}
    public synchronized boolean isResearchingGravity() { return !this.researchingGravity.equals("");}
    // =============== METHODS ================

    // actualizamos los daños
    public synchronized void updateOxygen(){this.oxygenLevel -= 8;}
    public synchronized void updateThrusters(){this.thrustersLevel -= 3;}
    public synchronized void updateEnergy(){this.energyLevel -= 5;}

    // datos sin importancia, solo simula que cambia
    public synchronized void updateTemperature(){
        if (random.nextBoolean()) {
            this.temperature += 1;
        } else {
            this.temperature -= 1;
        }
    }
    public synchronized void updatePressure(){
        if (random.nextBoolean()) {
            this.pressure += 0.001;
        } else {
            this.pressure -= 0.001;
        }
    }
    public synchronized void updateGravity(){
        if (random.nextBoolean()) {
            this.gravity += 0.05;
        } else {
            this.gravity -= 0.05;
        }
    }

    // metodos que se utilizan para reparar la nave
    public synchronized void addOxygen() {this.oxygenLevel += 1.0;}
    public synchronized void addThrusters() {this.thrustersLevel += 1.0;}
    public synchronized void addEnergy() {this.energyLevel += 1.0;}

    // metodos para investigar
    public synchronized void researchTemperature(){         // si no hay energia no se puede investigar, pues no hay luz
        if (this.energyLevel > 10){
            this.temperatureProgress += 2.0;
        }
    }
    public synchronized void researchPressure() {
        if (this.oxygenLevel > 10 && this.energyLevel > 10){// si no hay oxigeno ni energia no se puede investigar, pues no tienen energia ni oxigeno par sali
            this.pressureProgress += 2.0;
        }
    }
    public synchronized void researchGravity(){
        if( this.oxygenLevel > 10){                         // si no hay oxigeno no se puede investigar la gravedad pues no pueden salir
            this.gravityProgress += 2.0;
        }
    }


    // metodos que verifican si el progreso de una investigacion no ha alcanzado su maximo y si alguien lo esta investigando actualmente
    // aqui es donde se guarda el nombre del astronauta que esta investigando,
    // si no hay nadie investigando se guarda una cadena vacia, si ya hay alguien investigando se devuelve false para que el otro astronauta no pueda investigar lo mismo
    public synchronized boolean ocuppyTemperature(String name) {
        if(this.getTemperatureProgress() < 100 && !this.isResearchingTemperature()){
            this.researchingTemperature = name;
            return true;
        }
        return false;
    }
    public synchronized boolean ocuppyPressure(String name) {
        if(this.pressureProgress < 100 && !this.isResearchingPressure()){
            this.researchingPressure = name;
            return true;
        }
        return false;
    }
    public synchronized boolean ocuppyGravity(String name) {
        if(this.gravityProgress < 100 && !this.isResearchingGravity()){
            this.researchingGravity = name;
            return true;
        }
        return false;
    }


    // metodos que verifican si necesitan reparacion (tiene menos de 40%) y si alguien lo esta reparando actualmente
    public synchronized boolean ocuppyOxygen(String name) {
        if(this.oxygenLevel < 40 && !this.isRepairingOxygen()){
            this.repairingOxygen = name;
            return true;
        }
        return false;
    }
    public synchronized boolean ocuppyThrusters(String name) {
        if(this.thrustersLevel < 40 && !this.isRepairingThrusters()){
            this.repairingThrusters = name;
            return true;
        }
        return false;
    }
    public synchronized boolean ocuppyEnergy(String name) {
        if(this.energyLevel < 40 && !this.isRepairingEnergy()){
            this.repairingEnergy = name;
            return true;
        }
        return false;
    }

}
