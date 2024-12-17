import java.net.URL;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


public class JogoTelaInicial {
    private static final String caminho_pontuacao = "src/Players/jogadores.txt";
    private JFrame frame;
    private String nome_player;

    public JogoTelaInicial() {
        // Necessário incializar a null, se não não deixa meter o nome do jogador
        // depois que ele cancela o JOptionPane
        nome_player = null;
        IniciarJogo();
    }

    private void IniciarJogo() {
        // Título do Frame em cima da página
        frame = new JFrame("Star Trek: A rota das substâncias proibidas");
        // Faz com que feche o frame quando clicar no x de fechar
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Faz com que quando abra o frame, estique automaticamente a tela do ecrã
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        // Faz o frame ficar visível
        frame.setVisible(true);

        // Bloco de código responsável por definir qual é o background
        // ---------------------------------------
        ImageIcon icone_fundo;
		Image imagem_fundo;
		try {
			URL caminho_imagem = getClass().getResource("/Images/image.png");
			if (caminho_imagem == null) {
				throw new IllegalArgumentException("Imagem não encontrada: /Images/image.png");
			}
			icone_fundo = new ImageIcon(caminho_imagem);
			imagem_fundo = icone_fundo.getImage().getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);
        } catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Falha ao carregar a imagem.");
		}
        // ---------------------------------------

        // Bloco de código responsável por fazer a imagem de fundo configurada aparecer
        // ---------------------------------------
        JPanel painel_principal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(imagem_fundo, 0, 0, getWidth(), getHeight(), this);
            }
        };
        painel_principal.setLayout(new BorderLayout());
        // ---------------------------------------

        // Cria a área dos botões
        // ---------------------------------------
        JPanel painel_botoes = new JPanel();
        painel_botoes.setOpaque(false);
        painel_botoes.setLayout(new BoxLayout(painel_botoes, BoxLayout.Y_AXIS));
        // ---------------------------------------

        // Cria um espaço em cima dos botões para não ficar colado em cima
        painel_botoes.add(Box.createVerticalStrut(50));

        // Configurações do botão começar jogo
        // ---------------------------------------
        JButton botao_comecar = new JButton("Iniciar");
        // Fonte e letra do botão
        botao_comecar.setFont(new Font("Arial", Font.BOLD, 24));
        // Alinhado ao centro do frame
        botao_comecar.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Quando clicar no botão começar
        // -------
        botao_comecar.addActionListener(e -> {
            // Se o nome estiver vazio vai dizer que só posso inciar o jogo quando meter o nome
            if (nome_player == null || nome_player.trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Por favor, insira o seu nome antes de começar!", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                // Guarda o nome do jogador no ficheiro
                // --------------------------------------
                Nave nave = new Nave(nome_player);
                nave.setFrame(frame);

                // Formata a data para dia/mês/ano hora/minuto/segundo
                DateTimeFormatter formato_data = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                // Obtém a data atual
                String data_atual = LocalDateTime.now().format(formato_data);

                Jogador jogador = new Jogador(nome_player, data_atual, nave.getSubstancias());
                Jogador.SalvarFicheiro(jogador, caminho_pontuacao);
                // --------------------------------------
                // Fecha o frame atual da tela incial
                frame.dispose();
                // Abre o frame do jogo
                new JogoInterfaceGrafica(nome_player);
            }
        });
        // -------
        painel_botoes.add(botao_comecar);
        // ---------------------------------------

        // Espaço entre os botões começar e nome
        painel_botoes.add(Box.createVerticalStrut(20));

        // Mesma lógica do botão de cima, só muda o nome do botão e a funcionalidade ao clicar
        // ---------------------------------------
        JButton botao_nome = new JButton("Inserir Nome");
        botao_nome.setFont(new Font("Arial", Font.BOLD, 24));
        botao_nome.setAlignmentX(Component.CENTER_ALIGNMENT);
        botao_nome.addActionListener(e -> {
            // Enquanto o nome tiver vazio, vai ficar a pedir nome
            while (nome_player == null || nome_player.trim().isEmpty()) {
                nome_player = JOptionPane.showInputDialog(frame, "Digite o seu nome:", "Nome do Jogador", JOptionPane.QUESTION_MESSAGE);
                if (nome_player == null) {
                    JOptionPane.showMessageDialog(frame, "Você precisa inserir um nome para continuar!", "Aviso", JOptionPane.WARNING_MESSAGE);
                } else if (nome_player.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "O nome não pode estar vazio. Tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
            JOptionPane.showMessageDialog(frame, "Bem-vindo, " + nome_player + "!", "Nome Registado", JOptionPane.INFORMATION_MESSAGE);
        });

        painel_botoes.add(botao_nome);
        // ---------------------------------------

        painel_botoes.add(Box.createVerticalStrut(20));

        // Botão Pontuação
        // ---------------------------------------
        JButton botao_pontuacao = new JButton("Pontuação");
        botao_pontuacao.setFont(new Font("Arial", Font.BOLD, 24));
        botao_pontuacao.setAlignmentX(Component.CENTER_ALIGNMENT);
        botao_pontuacao.addActionListener(e -> {
            //Abro uma tabela que vai aparecer todos os nomes do txt
            JFrame tabela_pontuacao = new JFrame("Pontuação");
            tabela_pontuacao.setSize(600, 400);
            tabela_pontuacao.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // Título da tabela
            // -----
            String[] lista_nomes = {"Nome do Jogador", "Data e Hora", "Substâncias"};
            DefaultTableModel modelo_tabela = new DefaultTableModel(lista_nomes, 0) {
                // Faz com que os elementos da tabela não possam ser editados,
                // atravês do uso de polimorfismo dum metodo criado na classe DefaultTableModel
                @Override
                public boolean isCellEditable(int linha, int coluna) {
                    return false;
                }
            };
            // -----

            // Elementos da tabela
            // -----
            // Vai buscar os dados ao txt
            List<Jogador> jogadores = Jogador.CarregarFicheiro(caminho_pontuacao);
            // Mete os dados na tabela
            for (Jogador jogador : jogadores) {
                modelo_tabela.addRow(new String[]{jogador.getNome(), jogador.getDataAtual(), String.valueOf(jogador.getSubstancias())});
            }
            // -----

            JTable tabela = new JTable(modelo_tabela);
            JScrollPane scrollPane = new JScrollPane(tabela);

            tabela_pontuacao.add(scrollPane);
            tabela_pontuacao.setVisible(true);
        });
        painel_botoes.add(botao_pontuacao);
        // ---------------------------------------

        // Dá aquele espaço grande entre o botão pontuação e o botão sair
        painel_botoes.add(Box.createVerticalGlue());

        // Botão Sair
        // ------
        JButton botao_sair = new JButton("Sair");
        botao_sair.setFont(new Font("Arial", Font.BOLD, 24));
        botao_sair.setAlignmentX(Component.CENTER_ALIGNMENT);
        botao_sair.addActionListener(e -> System.exit(0));
        painel_botoes.add(botao_sair);
        // ---------------------------------------

        painel_botoes.add(Box.createVerticalStrut(50));

        // Adiciona os botões ao painel
        painel_principal.add(painel_botoes, BorderLayout.CENTER);
        frame.add(painel_principal);
    }
}
