import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


public class JogoTelaInicial {
    private static final String FILE_PATH = "src/Players/jogadores.txt"; // Caminho para a pasta src/Players
    private JFrame frame;
    private String playerName = null;

    public JogoTelaInicial() {
        setupGUI();
    }

    private void setupGUI() {
        frame = new JFrame("Star Trek: A rota das substâncias proibidas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Obtendo as dimensões da tela
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        // Carregando a imagem e redimensionando para a tela
        ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("Images/image.png"));
        Image backgroundImage = backgroundIcon.getImage().getScaledInstance(screenWidth, screenHeight, Image.SCALE_SMOOTH);

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Star Trek: A rota das substâncias proibidas");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(titleLabel);

        buttonPanel.add(Box.createVerticalStrut(50)); // Espaço vertical

        JButton startButton = new JButton("Iniciar");
        startButton.setFont(new Font("Arial", Font.BOLD, 24));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.addActionListener(e -> {
            if (playerName == null || playerName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Por favor, insira o seu nome antes de começar!", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                savePlayerName(); // Chama para salvar o jogador
                frame.dispose(); // Fecha a tela inicial
                new JogoInterfaceGrafica(playerName); // Abre a tela do jogo
            }
        });
        buttonPanel.add(startButton);

        buttonPanel.add(Box.createVerticalStrut(20)); // Espaço vertical

        JButton nameButton = new JButton("Inserir Nome");
        nameButton.setFont(new Font("Arial", Font.BOLD, 24));
        nameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameButton.addActionListener(e -> {
            // Não redefine a variável playerName, usamos a variável global
            while (playerName == null || playerName.trim().isEmpty()) {
                playerName = JOptionPane.showInputDialog(frame, "Digite o seu nome:", "Nome do Jogador", JOptionPane.QUESTION_MESSAGE);
                if (playerName == null) {
                    JOptionPane.showMessageDialog(frame, "Você precisa inserir um nome para continuar!", "Aviso", JOptionPane.WARNING_MESSAGE);
                } else if (playerName.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "O nome não pode estar vazio. Tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
            JOptionPane.showMessageDialog(frame, "Bem-vindo, " + playerName + "!", "Nome Registrado", JOptionPane.INFORMATION_MESSAGE);
        });

        buttonPanel.add(nameButton);

        buttonPanel.add(Box.createVerticalStrut(20)); // Espaço vertical

        JButton scoreButton = new JButton("Pontuação");
        scoreButton.setFont(new Font("Arial", Font.BOLD, 24));
        scoreButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreButton.addActionListener(e -> showScoreboard());
        buttonPanel.add(scoreButton);

        buttonPanel.add(Box.createVerticalGlue()); // Preenche o espaço restante

        // Novo botão "Sair"
        JButton exitButton = new JButton("Sair");
        exitButton.setFont(new Font("Arial", Font.BOLD, 24));
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener(e -> System.exit(0)); // Fecha o programa
        buttonPanel.add(exitButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        frame.add(mainPanel);

        frame.setVisible(true);
    }

    private void savePlayerName() {
        // Criação do jogador com o nome e dados necessários
        Nave nave = new Nave(playerName); // Criação da nave
        nave.setFrame(frame);
        int substancias = nave.getSubstancias(); // Obtendo as substâncias coletadas

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String currentTime = LocalDateTime.now().format(formatter);
        System.out.println("Nome do jogador: " + playerName + " | Data e Hora: " + currentTime + " | Substâncias: " + substancias);

        // Criando o jogador com as substâncias coletadas
        Jogador jogador = new Jogador(playerName, currentTime, substancias);
        Jogador.saveToFile(jogador, FILE_PATH);
    }

    private void showScoreboard() {
        JFrame scoreFrame = new JFrame("Pontuação");
        scoreFrame.setSize(600, 400);
        scoreFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnNames = {"Nome do Jogador", "Data e Hora", "Substâncias"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        List<Jogador> jogadores = Jogador.loadFromFile(FILE_PATH);
        for (Jogador jogador : jogadores) {
            tableModel.addRow(new String[]{jogador.getNome(), jogador.getDataHora(), String.valueOf(jogador.getSubstancias())});
        }

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        scoreFrame.add(scrollPane);
        scoreFrame.setVisible(true);
    }
}