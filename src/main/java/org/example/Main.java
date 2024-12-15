package org.example;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        //Vai executar o construtor da classe JogoInterfaceGrafica para abrir a Interfrace Gráfica de forma segura
        SwingUtilities.invokeLater(JogoTelaInicial::new);
    }
}