package org.example;


/**
 * El entorno se encarga de generar los daños a la nave | Es unicamente escritor
 *
 */
public class Enviroment implements Runnable {
    TelemetrySystem panel;

    public Enviroment(TelemetrySystem panel) {
        this.panel = panel;
    }

    @Override
    public void run() {

            while (true) {

                // veriifica si estan reprando los daños, si nadie lo esta reparando se comienza a dañar la nave
                if (!panel.isRepairingOxygen()){
                    panel.updateOxygen();
                }

                if (!panel.isRepairingThrusters()){
                    panel.updateThrusters();
                }

                if (!panel.isRepairingEnergy()){
                    panel.updateEnergy();
                }

                // actualiza datos que no afectan en nada como temperatura, presion, gravedad solo esta para que choquen los hilos
                panel.updateTemperature();
                panel.updatePressure();
                panel.updateGravity();


                // si ya se investigo tdo se acaba porque se regresan a la tierra
                if (panel.getGravityProgress() > 100 && panel.getPressureProgress() > 100 && panel.getTemperatureProgress() > 100) {
                    break;
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

    }
}
