/*package org.example;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Vai executar o construtor da classe JogoTelaInicial
        // para abrir a Interfrace Gráfica de forma segura
        SwingUtilities.invokeLater(JogoTelaInicial::new);
    }
}*/

package org.example;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Paths;

public class JogoVideoInterface extends JFrame {
    private JPanel mainPanel;
    private JTextPane textArea;
    private JButton playVideoButton, exploreButton, restButton;
    private org.example.JFXPanel videoPanel; // Painel JavaFX para o vídeo
    private boolean videoPlaying = false;

    public interface JogoVideo<videoPath>(String VideoPath) {
        setTitle("Star Trek: Interface com Vídeo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Inicializa a interface gráfica
        setupGUI(videoPath);
        setVisible(true);
    }
    private void setupGUI(String videoPath) {
        mainPanel = new JPanel(new BorderLayout());

        // Painel para texto e botões
        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.setBackground(Color.BLACK);


        // Área de texto
        textArea = new JTextPane();
        textArea.setEditable(false);
        textArea.setForeground(Color.WHITE);
        textArea.setFont(new Font("Arial", Font.BOLD, 16));
        textArea.setBackground(Color.BLACK);
        textArea.setText("Bem-vindo à missão Star Trek! Clique no botão para iniciar o vídeo ou explore.");

        JScrollPane textScrollPane = new JScrollPane(textArea);
        textScrollPane.setPreferredSize(new Dimension(400, 150));
        controlPanel.add(textScrollPane, BorderLayout.NORTH);

        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);

        // Botão para reproduzir vídeo
        playVideoButton = new JButton("Assistir Vídeo");
        playVideoButton.addActionListener(e -> playVideo(videoPath));
        buttonPanel.add(playVideoButton);

        // Botões adicionais (Explorar, Descansar)
        exploreButton = new JButton("Explorar Local");
        exploreButton.addActionListener(e -> updateText("Você explorou o local!"));
        buttonPanel.add(exploreButton);

        restButton = new JButton("Descansar");
        restButton.addActionListener(e -> updateText("Você descansou e recuperou energia!"));
        buttonPanel.add(restButton);

        controlPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        // Painel para o vídeo
        videoPanel = new JFXPanel(); // Inicializa JavaFX
        videoPanel.setPreferredSize(new Dimension(800, 600));
        mainPanel.add(videoPanel, BorderLayout.CENTER);

        add(mainPanel);
    }
    private void playVideo(String videoPath) {
        if (videoPlaying) {
            JOptionPane.showMessageDialog(this, "O vídeo já está sendo reproduzido.");
            return;
        }
        Platform.runLater(() -> {
            try {
                String videoURI = Paths.get(videoPath).toUri().toString();
                Media media = new org.example.steckvideo.Media(videoURI);
                org.example.steckvideo.MediaPlayer mediaPlayer = new MediaPlayer(media);
                org.example.steckvideo.MediaView mediaView = new MediaView(mediaPlayer);

                StackPane root = new StackPane();
                root.getChildren().add(mediaView);

                Scene scene = new Scene(root, 800, 600);
                videoPanel.setScene(scene);

                mediaPlayer.play();
                videoPlaying = true;

                // Detecta quando o vídeo termina
                mediaPlayer.setOnEndOfMedia(() -> videoPlaying = false);
            } catch (Exception e) {
                updateText("Erro ao carregar o vídeo. Verifique o arquivo.");
                e.printStackTrace();
            }
        });
    }

    private void updateText(String message) {
        textArea.setText(message);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JogoVideoInterface("C:/Users/kgome/OneDrive/Ambiente de Trabalho/Star treck.mp4"));
    }

    private class MediaView extends org.example.steckvideo.MediaView {
        public MediaView(org.example.steckvideo.MediaPlayer mediaPlayer) {
            super();
        }
    }
}