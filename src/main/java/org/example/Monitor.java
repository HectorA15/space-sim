package org.example;

public class Monitor implements Runnable {
    TelemetrySystem panel;
    SpaceDashboard dashboard; // Referencia a la ventana gráfica

    // Constructor que recibe el panel y la ventana
    public Monitor(TelemetrySystem panel, SpaceDashboard dashboard) {
        this.panel = panel;
        this.dashboard = dashboard;
    }

    @Override
    public void run() {
        while (true) {

            StringBuilder frame = new StringBuilder();

            frame.append("=========================================================\n");
            frame.append("             KSP-1 TELEMETRY COMMAND CENTER              \n");
            frame.append("=========================================================\n\n");

            // Alertas de Mantenimiento Activo


            // Panel de Soporte Vital
            frame.append(">> SISTEMAS DE SOPORTE VITAL:\n");
            // Formato %5.1f%% para alinear los números de 2 y 3 dígitos
            frame.append(String.format("   Oxigeno:     %s %5.1f%%%n", loadingBar(panel.getOxygenLevel()), panel.getOxygenLevel()));
            frame.append(String.format("   Propulsores: %s %5.1f%%%n", loadingBar(panel.getThrustersLevel()), panel.getThrustersLevel()));
            frame.append(String.format("   Energia:     %s %5.1f%%%n", loadingBar(panel.getEnergyLevel()), panel.getEnergyLevel()));
            frame.append("---------------------------------------------------------\n");

            // Panel de Investigación Científica
            frame.append(">> INVESTIGACION:\n");
            frame.append(String.format("   Temperatura %-11s: %s %5.1f%%%n", formatName(panel.getWorkerTemperature()), loadingBar(panel.getTemperatureProgress()), panel.getTemperatureProgress()));
            frame.append(String.format("   Presión     %-11s: %s %5.1f%%%n", formatName(panel.getWorkerPressure()), loadingBar(panel.getPressureProgress()), panel.getPressureProgress()));
            frame.append(String.format("   Gravedad    %-11s: %s %5.1f%%%n", formatName(panel.getWorkerGravity()), loadingBar(panel.getGravityProgress()), panel.getGravityProgress()));
            frame.append("---------------------------------------------------------\n");

            // Sensores del Entorno
            frame.append(">> SENSORES DEL ENTORNO EXTERIOR:\n");
            frame.append(String.format("   Temp: %.1f °C | Presion: %.3f atm | Grav: %.3f m/s²%n",
                    panel.getTemperature(), panel.getPressure(), panel.getGravity()));
            frame.append("=========================================================\n");

            boolean hayEmergencia = panel.isRepairingOxygen() || panel.isRepairingThrusters() || panel.isRepairingEnergy();
            if (hayEmergencia) {
                frame.append(">> ALERTAS DE MANTENIMIENTO:\n");
                if (panel.isRepairingOxygen())    frame.append(String.format("   [!] Rep. Oxigeno     %-11s: %s%n", formatName(panel.getWorkerOxygen()), loadingBar(panel.getOxygenLevel())));
                if (panel.isRepairingThrusters()) frame.append(String.format("   [!] Rep. Propulsores %-11s: %s%n", formatName(panel.getWorkerThrusters()), loadingBar(panel.getThrustersLevel())));
                if (panel.isRepairingEnergy())    frame.append(String.format("   [!] Rep. Energia     %-11s: %s%n", formatName(panel.getWorkerEnergy()), loadingBar(panel.getEnergyLevel())));
                frame.append("---------------------------------------------------------\n");
            }

            // Condición de Victoria
            if (panel.getTemperatureProgress() >= 100 && panel.getPressureProgress() >= 100 && panel.getGravityProgress() >= 100) {
                frame.append("\nMISION COMPLETADA.\n");
                frame.append("INICIANDO SECUENCIA DE RETORNO A LA TIERRA...\n");
                dashboard.updateTerminal(frame.toString());
                break;
            }

            // Actualizar la interfaz gráfica
            dashboard.updateTerminal(frame.toString());

            // Frecuencia de actualización de la pantalla
            try {
                Thread.sleep(250); // 250ms hace que se vea fluido
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // La barra de carga
    public String loadingBar(double quantity) {
        // Restringe el valor entre 0 y 100
        quantity = Math.max(0, Math.min(100, quantity));

        int totalBars = 20;
        int filledBars = (int) (quantity / 100 * totalBars);
        StringBuilder bar = new StringBuilder();

        bar.append("[");
        for (int i = 0; i < filledBars; i++) {
            bar.append("#");
        }
        for (int i = filledBars; i < totalBars; i++) {
            bar.append("-");
        }
        bar.append("]");
        return bar.toString();
    }

    // Formatea el nombre del trabajador para mostrarlo en la barra de carga
    private String formatName(String name) {
        if (name == null || name.equals("")) return "";
        return "(" + name + ")";
    }
}