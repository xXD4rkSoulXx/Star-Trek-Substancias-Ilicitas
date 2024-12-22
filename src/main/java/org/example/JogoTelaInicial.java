package main.java.org.example;

import java.net.URL;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


public class JogoTelaInicial {
    private static final String CAMINHO_PONTUACAO = "src/Players/jogadores.txt";
    private String tipoLetra;
    private JFrame frame;
    private String nomePlayer;

    public JogoTelaInicial() {
        tipoLetra="Arial";
        // Necessário incializar a null, se não não deixa meter o nome do jogador
        // depois que ele cancela o JOptionPane
        nomePlayer = null;
        iniciarJogo();
    }

    private void iniciarJogo() {
        // Título do Frame em cima da página
        frame = new JFrame("Star Trek: A rota das substâncias proibidas");
        // Faz com que feche o frame quando clicar no x de fechar
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // Faz com que quando abra o frame, estique automaticamente a tela do ecrã
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        // Faz o frame ficar visível
        frame.setVisible(true);

        // Bloco de código responsável por definir qual é o background
        // ---------------------------------------
        ImageIcon iconeFundo;
		Image imagemFundo;
		try {
			URL caminhoImagem = getClass().getResource("/Images/image.png");
			if (caminhoImagem == null) {
				throw new IllegalArgumentException("Imagem não encontrada: /Images/image.png");
			}
			iconeFundo = new ImageIcon(caminhoImagem);
			imagemFundo = iconeFundo.getImage().getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);
        } catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Falha ao carregar a imagem.");
		}
        // ---------------------------------------

        // Bloco de código responsável por fazer a imagem de fundo configurada aparecer
        // ---------------------------------------
        JPanel painelPrincipal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(imagemFundo, 0, 0, getWidth(), getHeight(), this);
            }
        };
        painelPrincipal.setLayout(new BorderLayout());
        // ---------------------------------------

        // Cria a área dos botões
        // ---------------------------------------
        JPanel painelBotoes = new JPanel();
        painelBotoes.setOpaque(false);
        painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.Y_AXIS));
        // ---------------------------------------

        // Cria um espaço em cima dos botões para não ficar colado em cima
        painelBotoes.add(Box.createVerticalStrut(50));

        // Configurações do botão começar jogo
        // ---------------------------------------
        JButton botaoComecar = new JButton("Iniciar");
        // Fonte e letra do botão
        botaoComecar.setFont(new Font(tipoLetra, Font.BOLD, 24));
        // Alinhado ao centro do frame
        botaoComecar.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Quando clicar no botão começar
        // -------
        botaoComecar.addActionListener(e -> {
            // Se o nome estiver vazio vai dizer que só posso inciar o jogo quando meter o nome
            if (nomePlayer == null || nomePlayer.trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Por favor, insira o seu nome antes de começar!", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                // Guarda o nome do jogador no ficheiro
                // --------------------------------------
                Nave nave = new Nave(nomePlayer);
                nave.setFrame(frame);

                // Formata a data para dia/mês/ano hora/minuto/segundo
                DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                // Obtém a data atual
                String dataAtual = LocalDateTime.now().format(formatoData);

                Jogador jogador = new Jogador(nomePlayer, dataAtual, nave.getSubstancias());
                Jogador.salvarFicheiro(jogador, CAMINHO_PONTUACAO);
                // --------------------------------------
                // Fecha o frame atual da tela incial
                frame.dispose();
                // Abre o frame do jogo
                new JogoInterfaceGrafica(nomePlayer);
            }
        });
        // -------
        painelBotoes.add(botaoComecar);
        // ---------------------------------------

        // Espaço entre os botões começar e nome
        painelBotoes.add(Box.createVerticalStrut(20));

        // Mesma lógica do botão de cima, só muda o nome do botão e a funcionalidade ao clicar
        // ---------------------------------------
        JButton botaoNome = new JButton("Inserir Nome");
        botaoNome.setFont(new Font(tipoLetra, Font.BOLD, 24));
        botaoNome.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoNome.addActionListener(e -> {
            // Enquanto o nome tiver vazio, vai ficar a pedir nome
            while (nomePlayer == null || nomePlayer.trim().isEmpty()) {
                nomePlayer = JOptionPane.showInputDialog(frame, "Digite o seu nome:", "Nome do Jogador", JOptionPane.QUESTION_MESSAGE);
                if (nomePlayer == null) {
                    JOptionPane.showMessageDialog(frame, "Você precisa inserir um nome para continuar!", "Aviso", JOptionPane.WARNING_MESSAGE);
                } else if (nomePlayer.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "O nome não pode estar vazio. Tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
            JOptionPane.showMessageDialog(frame, "Bem-vindo, " + nomePlayer + "!", "Nome Registado", JOptionPane.INFORMATION_MESSAGE);
        });

        painelBotoes.add(botaoNome);
        // ---------------------------------------

        painelBotoes.add(Box.createVerticalStrut(20));

        // Botão Pontuação
        // ---------------------------------------
        JButton botaoPontuacao = new JButton("Pontuação");
        botaoPontuacao.setFont(new Font(tipoLetra, Font.BOLD, 24));
        botaoPontuacao.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoPontuacao.addActionListener(e -> {
            //Abro uma tabela que vai aparecer todos os nomes do txt
            JFrame tabelaPontuacao = new JFrame("Pontuação");
            tabelaPontuacao.setSize(600, 400);
            tabelaPontuacao.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            // Título da tabela
            // -----
            String[] listaNomes = {"Nome do Jogador", "Data e Hora", "Substâncias"};
            DefaultTableModel modeloTabela = new DefaultTableModel(listaNomes, 0) {
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
            List<Jogador> jogadores = Jogador.carregarFicheiro(CAMINHO_PONTUACAO);
            // Mete os dados na tabela
            for (Jogador jogador : jogadores) {
                modeloTabela.addRow(new String[]{jogador.getNome(), jogador.getDataAtual(), String.valueOf(jogador.getSubstancias())});
            }
            // -----

            JTable tabela = new JTable(modeloTabela);
            // Barra de scroll
            JScrollPane scroll = new JScrollPane(tabela);

            tabelaPontuacao.add(scroll);
            tabelaPontuacao.setVisible(true);
        });
        painelBotoes.add(botaoPontuacao);
        // ---------------------------------------

        // Dá aquele espaço grande entre o botão pontuação e o botão sair
        painelBotoes.add(Box.createVerticalGlue());

        // Botão Sair
        // ------
        JButton botaoSair = new JButton("Sair");
        botaoSair.setFont(new Font(tipoLetra, Font.BOLD, 24));
        botaoSair.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoSair.addActionListener(e -> System.exit(0));
        painelBotoes.add(botaoSair);
        // ---------------------------------------

        painelBotoes.add(Box.createVerticalStrut(50));

        // Adiciona os botões ao painel
        painelPrincipal.add(painelBotoes, BorderLayout.CENTER);
        frame.add(painelPrincipal);
    }
}