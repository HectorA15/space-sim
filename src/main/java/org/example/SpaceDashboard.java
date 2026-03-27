package org.example;

import javax.swing.*;
import java.awt.*;

public class SpaceDashboard extends JFrame {
    private JTextArea terminalArea;

    public SpaceDashboard() {
        setTitle("KSP-1 Telemetry Command Center");
        setSize(570, 510);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Agregamos el terminal a la ventana
        terminalArea = new JTextArea();
        terminalArea.setEditable(false);
        terminalArea.setBackground(Color.BLACK);
        terminalArea.setForeground(new Color(0, 255, 0));

        terminalArea.setFont(new Font("Monospaced", Font.PLAIN, 15));

        add(new JScrollPane(terminalArea), BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    //  Metdo para actualizar el contenido del terminal desde otros hilos o threads
    public void updateTerminal(String content) {
        SwingUtilities.invokeLater(() -> {
            terminalArea.setText(content);
        });
    }
}