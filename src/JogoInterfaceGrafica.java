import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class JogoInterfaceGrafica {
    private JFrame frame;
    private JTextPane missionStatusArea;
    private JPanel buttonPanel, missionButtonPanel;
    private JButton exploreButton, restButton;
    private JogoProgramacao jogo;
    private boolean inMission = false;
    private boolean gameOver = false; // Controle de Game Over
    private JButton avancarButton; // Botão de avançar
    private boolean introducaoExibida = false; // Controle da introdução
    private SwingWorker<Void, String> textUpdater; // Para controlar a exibição do texto

    JPanel topPanel = new JPanel();
    JPanel textBackgroundPanel = new JPanel();
    JTextPane textArea = new JTextPane();
    String nome;

    public JogoInterfaceGrafica(String nome) {
        this.nome = nome;
        jogo = new JogoProgramacao(this.nome);
        setupGUI();
    }

    private void setupGUI() {
        frame = new JFrame("Star Trek: A rota das substâncias proibidas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

        // Carregamento da imagem de fundo
        ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("Images/image.png"));
        Image backgroundImage = backgroundIcon.getImage().getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);

        // Painel principal com fundo customizado
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setLayout(new BorderLayout());

        // Configuração do painel superior
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(Color.BLACK);

        // Botão "Explorar Local"
        exploreButton = new JButton("Explorar Local");
        exploreButton.setVisible(false); // Inicialmente escondido
        exploreButton.addActionListener(e -> {
            if (!gameOver && !inMission) {
                jogo.Explorar();
                updateText(jogo.getUltimaMensagem());
                if (jogo.DentroMissao()) {
                    inMission = true;
                    setMissionButtons(jogo.getListaOpcoes());
                }
            }
            showStatus();
            executeAdditionalAction();
        });

        // Botão "Descansar"
        restButton = new JButton("Descansar");
        restButton.setVisible(false); // Inicialmente escondido
        restButton.addActionListener(e -> {
            if (!gameOver && !inMission) {
                jogo.Descansar();
                updateText(jogo.getUltimaMensagem());
            }
            showStatus();
            executeAdditionalAction();
        });

        // Botão "Avançar"
        avancarButton = new JButton("Avançar");
        avancarButton.setVisible(true); // Visível desde o início
        avancarButton.addActionListener(e -> {
            avancarButton.setVisible(false); // Esconde o botão "Avançar"
            exploreButton.setVisible(true); // Mostra o botão "Explorar Local"
            restButton.setVisible(true); // Mostra o botão "Descansar"
            if (textUpdater != null && !textUpdater.isDone()) {
                textUpdater.cancel(true); // Cancela o texto gradual
            }
            updateTextWithDot(); // Atualiza o texto com ponto
        });

        // Painel dos botões
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(avancarButton); // Adiciona o botão "Avançar"
        buttonPanel.add(exploreButton); // Adiciona o botão "Explorar Local"
        buttonPanel.add(restButton); // Adiciona o botão "Descansar"
        topPanel.add(buttonPanel, BorderLayout.EAST);

        // Área de status da missão
        missionStatusArea = new JTextPane();
        missionStatusArea.setOpaque(true);
        missionStatusArea.setBackground(Color.BLACK);
        missionStatusArea.setEditable(false);
        missionStatusArea.setFont(new Font("Arial", Font.BOLD, 16));
        missionStatusArea.setForeground(Color.WHITE);
        topPanel.add(missionStatusArea, BorderLayout.CENTER);

        // Painel flutuante para o texto
        JPanel floatingPanel = new JPanel();
        floatingPanel.setOpaque(false);
        floatingPanel.setLayout(new BoxLayout(floatingPanel, BoxLayout.Y_AXIS));
        floatingPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));

        // Painel de fundo do texto
        textBackgroundPanel.setBackground(Color.BLACK);
        textBackgroundPanel.setPreferredSize(new Dimension(600, 100));
        textBackgroundPanel.setLayout(new BorderLayout());

        // Configuração do JTextArea
        textArea.setOpaque(true);
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.BOLD, 16));
        textArea.setForeground(Color.WHITE);
        textArea.setBackground(Color.BLACK);

        StyledDocument doc = textArea.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        textBackgroundPanel.add(textArea, BorderLayout.CENTER);

        floatingPanel.add(Box.createVerticalGlue());
        floatingPanel.add(textBackgroundPanel);
        floatingPanel.add(Box.createVerticalGlue());

        // Adiciona os painéis ao painel principal
        mainPanel.add(floatingPanel, BorderLayout.SOUTH);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Painel de botões de missão
        missionButtonPanel = new JPanel();
        missionButtonPanel.setLayout(new GridBagLayout());
        missionButtonPanel.setOpaque(false);
        mainPanel.add(missionButtonPanel, BorderLayout.CENTER);

        frame.add(mainPanel);

        // Atualização do texto de introdução
        updateText("Veggs, conhecido por suas loucuras, é um ex utilizador de drogas, porém reformado. Conhecido por já usar todos os tipos de drogas: Cannabis, Cocaína, Crack, Extasy, entre outras; ele cansou de usar as mesmas drogas repetitivamente, então ele pretende inovar e fazer uma livestream ingerido todas as drogas desconhecidas provenientes de outros planetas da mesma Galáxia, então a nossa missão é coletar novos tipos de drogas ao longo de vários planetas, em que a decisão certa trará uma nova droga, mas apenas uma decisão, então as outras duas decisões poderão não conter a droga, que por mais que progrida na história, o Veggs poderá ter um ataque de fúria por não estar todas as drogas coletadas, então pense bem antes de cada decisão. Veggs é viciado em Trance Psicodélico e sempre fala que horas são de hora em hora e ao mesmo tempo agradece aos membros do planeta por pertencerem ao tal. Veggs cobra muito impostos, mais que o António Costa. Veggs localiza-se no planeta Pó, onde o chão é constituído todo puramente de cocaína. Veggs nasceu com um nariz grande e apurado, sendo que na infância era comparado ao Phineas do Phineas e Ferb ou comparado a um Tucano. Através duma Análise SWOT, ele transformou essa fraqueza de nariz grande em uma força, já que usou o nariz a seu favor para cheirar tudo o que houvesse ao redor e criar a fama que tem.");

        showStatus();
    }


    private void adjustTextSize(String message) {
        FontMetrics metrics = textArea.getFontMetrics(textArea.getFont());
        int lineHeight = metrics.getHeight();
        int textWidth = textArea.getWidth();

        int lines = (int) Math.ceil(metrics.stringWidth(message) / (double) textWidth);
        int requiredHeight = lines * lineHeight + 20; // Ajustar margem

        textBackgroundPanel.setPreferredSize(new Dimension(textWidth, requiredHeight));
        textBackgroundPanel.revalidate();
    }

    private void updateText(String message) {
        if (textUpdater != null && !textUpdater.isDone()) {
            textUpdater.cancel(true); // Cancela o SwingWorker atual
        }

        textArea.setText("");
        adjustTextSize(message); // Ajusta o tamanho dinamicamente

        textUpdater = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws InterruptedException {
                for (int i = 0; i < message.length(); i++) {
                    if (isCancelled()) break; // Interrompe se for cancelado
                    publish(message.substring(0, i + 1));
                    Thread.sleep(30);
                }
                return null;
            }

            @Override
            protected void process(java.util.List<String> chunks) {
                if (!isCancelled()) {
                    textArea.setText(chunks.get(chunks.size() - 1));
                    adjustTextSize(chunks.get(chunks.size() - 1)); // Atualiza o tamanho ao adicionar texto
                }
            }

            @Override
            protected void done() {
                if (!isCancelled() && message.contains("GAME OVER")) {
                    gameOver = true;
                    disableGameInteraction();
                }
            }
        };

        textUpdater.execute();
    }

    private void updateTextWithDot() {
        StyledDocument doc = textArea.getStyledDocument();
        SimpleAttributeSet blackDot = new SimpleAttributeSet();
        StyleConstants.setForeground(blackDot, Color.white);

        SimpleAttributeSet defaultStyle = new SimpleAttributeSet();
        StyleConstants.setForeground(defaultStyle, Color.white); // Define o estilo padrão
        textBackgroundPanel.revalidate();
        textBackgroundPanel.repaint();

        try {
            doc.remove(0, doc.getLength()); // Remove o texto existente
            adjustTextSize(".");
            doc.setCharacterAttributes(0, doc.getLength(), defaultStyle, false); // Restaura o estilo padrão
            textArea.setCaretPosition(doc.getLength()); // Move o cursor para o final do texto
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }


    public void disableGameInteraction() {
        exploreButton.setEnabled(false);
        restButton.setEnabled(false);

        for (Component comp : missionButtonPanel.getComponents()) {
            if (comp instanceof JButton) {
                comp.setEnabled(false);
            }
        }


        JOptionPane.showMessageDialog(frame, "GAME OVER! Obrigado por jogar!", "Fim do Jogo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateMissionStatus(String statusMessage) {
        missionStatusArea.setText(statusMessage);
    }

    private void showStatus() {
        if (!inMission && !gameOver) { // Só mostra status se o jogo não acabou
            jogo.MostrarStatus();
            updateMissionStatus(jogo.getUltimaMensagem());

            // Adicionando a impressão da missão atual na linha de comandos
            Missao missaoAtual = jogo.getMissaoAtual();  // Obtém a missão atual
            if (missaoAtual != null) {
                // Imprime o que for acessível da missão, como título ou descrição
                System.out.println("Missão Atual: " + missaoAtual);  // Usando o método toString() da Missao, se disponível
            }
        }
    }


    private void executeAdditionalAction() {
        System.out.println("Ação adicional executada!");
    }

    private void setMissionButtons(String[] options) {
        gameOver = jogo.getNave().algumRecursoZerado();

        if (gameOver) {
            frame.dispose();
            JOptionPane.showMessageDialog(frame, "GAME OVER! Obrigado por jogar!", "Fim do Jogo", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
            return;
        }

        missionButtonPanel.removeAll();
        missionButtonPanel.setLayout(new BorderLayout());

        // Usa o botão já existente
        JButton avancarButton = new JButton("Avançar"); // Atualiza o texto, se necessário
        //avancarButton.setText("Avançar"); // Atualiza o texto, se necessário
        avancarButton.setVisible(true); // Certifica-se de que ele está visível
        avancarButton.addActionListener(e -> {
            textArea.setText(""); // Limpa o texto de missão
            missionButtonPanel.removeAll();
            missionButtonPanel.setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(10, 20, 10, 20);

            for (int i = 0; i < options.length; i++) {
                int choice = i;
                JButton optionButton = new JButton("Opção " + (i + 1) + ": " + options[i]);

                optionButton.setBackground(Color.BLACK);
                optionButton.setForeground(Color.WHITE);
                optionButton.setFont(new Font("Arial", Font.BOLD, 16));
                optionButton.setPreferredSize(new Dimension(frame.getWidth() - 200, 40));

                optionButton.addActionListener(choiceEvent -> {
                    jogo.EscolherOpcao(choice);
                    updateText(jogo.getUltimaMensagem());
                    clearMissionButtons();
                    inMission = false;
                    showStatus();
                    executeAdditionalAction();
                });

                missionButtonPanel.add(optionButton, gbc);
                gbc.gridy++;
            }

            avancarButton.setVisible(false); // Esconde o botão "Avançar"
            exploreButton.setVisible(true); // Mostra o botão "Explorar Local"
            restButton.setVisible(true); // Mostra o botão "Descansar"
            if (textUpdater != null && !textUpdater.isDone()) {
                textUpdater.cancel(true); // Cancela o texto gradual
            }
            updateTextWithDot(); // Atualiza o texto com ponto

            textBackgroundPanel.revalidate();
            textBackgroundPanel.repaint();



            if (gameOver) {
                frame.dispose();
                JOptionPane.showMessageDialog(frame, "GAME OVER! Obrigado por jogar!", "Fim do Jogo", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
                return;
            }

            missionButtonPanel.revalidate();
            missionButtonPanel.repaint();
        });

        exploreButton.setVisible(false);
        restButton.setVisible(false);
        buttonPanel.add(avancarButton);

        //missionButtonPanel.add(introPanel, BorderLayout.CENTER);
        missionButtonPanel.revalidate();
        missionButtonPanel.repaint();
    }



    private void clearMissionButtons() {
        missionButtonPanel.removeAll();
        missionButtonPanel.revalidate();
        missionButtonPanel.repaint();
    }
}