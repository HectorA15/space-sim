package org.example;

/**
 * El astronauta se encarga de reparar los daños y de investigar las cosas, es un hilo escritor y lector, puede modificar el panel y leerlo para saber que hacer
 * 1. Reparar algo
 * 2. Investigar algo
 * 3. Modificar el panel
 * 4. Leer el panel
 * 5. Verificar si la misión se ha completada
 * 6. Verificar si hay una emergencia, si hay una emergencia se tiene que reparar lo antes posible, si no hay emergencia se puede investigar
 * 7. Si la misión se ha completada se acaba el hilo, se regresa a la tierra
 */


public class Astronaut implements Runnable{
    TelemetrySystem panel;
    String name;

    public Astronaut(TelemetrySystem panel, String name) {
        this.panel = panel;
        this.name = name;
    }

    @Override
    public void run() {
        while (true) {

            // check if mission is completed // revisar si la misión se ha completada
            if (panel.getGravityProgress() > 100 && panel.getPressureProgress() > 100 && panel.getTemperatureProgress() > 100) {
                break;
            }

            // repair something // reparar algo
            if(panel.ocuppyOxygen(this.name)) {
                while(panel.getOxygenLevel() < 100){
                    panel.setRepairingOxygen(true);
                    panel.addOxygen();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                // aqui como se puso en false, se guardo el nombre asignado a esta tarea con un ""
                panel.setRepairingOxygen(false);

            }
            else if(panel.ocuppyThrusters(this.name)){
                while (panel.getThrustersLevel() < 100){
                    panel.setRepairingThrusters(true);
                    panel.addThrusters();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                // aqui como se puso en false, se guardo el nombre asignado a esta tarea con un ""
                panel.setRepairingThrusters(false);
            }
            else if (panel.ocuppyEnergy(this.name)) {
                while (panel.getEnergyLevel() < 100){
                    panel.setRepairingEnergy(true);
                    panel.addEnergy();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                // aqui como se puso en false, se guardo el nombre asignado a esta tarea con un ""
                panel.setRepairingEnergy(false);
            }

            // research something // investigar algo
            if (panel.ocuppyTemperature(this.name)) {
                while (panel.getTemperatureProgress() < 100) {
                    panel.researchTemperature();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    if (panel.getOxygenLevel() < 20 || panel.getThrustersLevel() < 20 || panel.getEnergyLevel() < 20) {
                        break;
                    }
                }
                panel.setResearchingTemperature(false);
            }
            else if (panel.ocuppyPressure(this.name)) {
                while (panel.getPressureProgress() < 100) {
                    panel.researchPressure();

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if (panel.getOxygenLevel() < 20 || panel.getThrustersLevel() < 20 || panel.getEnergyLevel() < 20) {
                        break;
                    }
                }
                panel.setResearchingPressure(false);

            }
            else if (panel.ocuppyGravity(this.name)) {
                while (panel.getGravityProgress() < 100) {
                    panel.researchGravity();

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if (panel.getOxygenLevel() < 20 || panel.getThrustersLevel() < 20 || panel.getEnergyLevel() < 20) {
                        break;
                    }
                }
                panel.setResearchingGravity(false);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
