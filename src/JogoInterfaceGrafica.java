import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.net.URL;

public class JogoInterfaceGrafica {
    private JFrame frame;
    private JTextPane areaStatus;
    private JTextPane areaTexto;
    private JPanel areaBotoes;
    private JPanel areaBotoesmissao;
    private JPanel painelSuperior;
    private JPanel painelFundo;
    private JButton botaoExplorar;
    private JButton botaoDescansar;
    private JButton botaoAvancar;
    private JogoProgramacao jogo;
    private boolean dentroMissao = false;
    private boolean gameOver = false;
    private SwingWorker<Void, String> atualizaTexto;
    private String nomeJogador;
    private String tipoLetra;
    private String mensagemGameOver;
    private String tituloGameOver;

    public JogoInterfaceGrafica(String nome) {
        this.nomeJogador = nome;
        tipoLetra = "Arial";
        mensagemGameOver = "GAME OVER! Obrigado por jogar!";
        tituloGameOver = "Fim do Jogo";
        jogo = new JogoProgramacao(this.nomeJogador);
        areaStatus = new JTextPane();
        areaTexto = new JTextPane();
        areaBotoes = new JPanel();
        areaBotoesmissao = new JPanel();
        painelSuperior = new JPanel();
        painelFundo = new JPanel();
        comecarJogo();
    }

    private void comecarJogo() {
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

        // Configuração do painel superior
        // --------
        painelSuperior.setLayout(new BorderLayout());
        painelSuperior.setBackground(Color.BLACK);
        // --------

        // Botão explorar
        // ---------------------------------------
        botaoExplorar = new JButton("Explorar Local");
        botaoExplorar.setVisible(false);
        botaoExplorar.addActionListener(e -> {
            // Verifica se o jogador já ganhou
            // --------
            if (jogo.getNave().getSubstancias() == 4){
                areaStatus.setText("Acabou o jogo!");
                return;
            }
            // --------

            // Verifica se o jogador já perdeu
            // --------
            if (!gameOver && !dentroMissao) {
                jogo.explorar();
                atualizarTexto(jogo.getUltimaMensagem());
                if (jogo.dentroMissao()) {
                    dentroMissao = true;
                    meterBotoesMissao(jogo.getListaOpcoes());
                }
            }
            // --------

            // Verifica se o jogador já perdeu
            // --------
            if (dentroMissao && !gameOver) {
                jogo.mostrarStatus();
                areaStatus.setText(jogo.getUltimaMensagem());
            }
            // --------
        });
        // ---------------------------------------

        // Botão descansar
        // ---------------------------------------
        botaoDescansar = new JButton("Descansar");
        botaoDescansar.setVisible(false);
        botaoDescansar.addActionListener(e -> {
            // Verifica se o jogador já ganhou
            // --------
            if (jogo.getNave().getSubstancias() == 4){
                return;
            }
            // --------

            // Verifica se o jogador já perdeu
            // --------
            if (!gameOver && !dentroMissao) {
                jogo.descansar();
                atualizarTexto(jogo.getUltimaMensagem());
            }
            // --------

            // Verifica se o jogador já perdeu
            // --------
            if (dentroMissao && !gameOver) {
                jogo.mostrarStatus();
                areaStatus.setText(jogo.getUltimaMensagem());
            }
            // --------
        });
        // ---------------------------------------

        // Botão Avançar
        // ---------------------------------------
        botaoAvancar = new JButton("Avançar");
        botaoAvancar.setVisible(true);
        botaoAvancar.addActionListener(e -> {
            // Mete o botão Avançar invisível e faz aparecer os outros botões
            // --------
            botaoAvancar.setVisible(false);
            botaoExplorar.setVisible(true);
            botaoDescansar.setVisible(true);
            // --------

            // Quando clico no botão avançar, ele faz o texto parar de aparecer para skipar
            // --------
            if (atualizaTexto != null && !atualizaTexto.isDone()) {
                atualizaTexto.cancel(true);
            }
            // --------

            // Remove o texto que está lá ao skipar
            // -------------------------------------
            StyledDocument doc = areaTexto.getStyledDocument();
            SimpleAttributeSet pontoPreto = new SimpleAttributeSet();
            StyleConstants.setForeground(pontoPreto, Color.white);

            SimpleAttributeSet estiloDefault = new SimpleAttributeSet();
            StyleConstants.setForeground(estiloDefault, Color.white);
            painelFundo.revalidate();
            painelFundo.repaint();

            try {
                doc.remove(0, doc.getLength());
                ajustarTamanhoTexto(".");
                doc.setCharacterAttributes(0, doc.getLength(), estiloDefault, false);
                areaTexto.setCaretPosition(doc.getLength());
            } catch (BadLocationException bl) {
                bl.printStackTrace();
            }
            // -------------------------------------
        });
        // ---------------------------------------

        // Painel dos botões
        // ---------------------------------------
        areaBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        areaBotoes.setOpaque(false);
        areaBotoes.add(botaoAvancar);
        areaBotoes.add(botaoExplorar);
        areaBotoes.add(botaoDescansar);
        painelSuperior.add(areaBotoes, BorderLayout.EAST);
        // ---------------------------------------

        // Área de status da missão
        // ---------------------------------------
        areaStatus = new JTextPane();
        areaStatus.setOpaque(true);
        areaStatus.setBackground(Color.BLACK);
        areaStatus.setEditable(false);
        areaStatus.setFont(new Font(tipoLetra, Font.BOLD, 16));
        areaStatus.setForeground(Color.WHITE);
        painelSuperior.add(areaStatus, BorderLayout.CENTER);
        // ---------------------------------------

        // Painel flutuante do texto
        // ---------------------------------------
        JPanel painelFlutuante = new JPanel();
        painelFlutuante.setOpaque(false);
        painelFlutuante.setLayout(new BoxLayout(painelFlutuante, BoxLayout.Y_AXIS));
        painelFlutuante.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
        // ---------------------------------------

        // Painel de fundo do texto
        // ---------------------------------------
        painelFundo.setBackground(Color.BLACK);
        painelFundo.setPreferredSize(new Dimension(600, 100));
        painelFundo.setLayout(new BorderLayout());
        // ---------------------------------------

        // Configuração da área do texto
        // ---------------------------------------
        areaTexto.setOpaque(true);
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font(tipoLetra, Font.BOLD, 16));
        areaTexto.setForeground(Color.WHITE);
        areaTexto.setBackground(Color.BLACK);

        StyledDocument doc = areaTexto.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        painelFundo.add(areaTexto, BorderLayout.CENTER);
        // ---------------------------------------

        //Faz com que o painel fundo apareça no centro do ecrã
        // ---------------------------------------
        //Faz um espaço grande em cima
        painelFlutuante.add(Box.createVerticalGlue());
        painelFlutuante.add(painelFundo);
        //Faz um espaço grande em baixo
        painelFlutuante.add(Box.createVerticalGlue());
        // ---------------------------------------

        // Adiciona os painéis ao painel principal
        // --------------
        painelPrincipal.add(painelSuperior, BorderLayout.NORTH);
        painelPrincipal.add(painelFlutuante, BorderLayout.SOUTH);
        // --------------

        // Configuração dos botões de missão
        // --------------
        areaBotoesmissao = new JPanel();
        areaBotoesmissao.setLayout(new GridBagLayout());
        areaBotoesmissao.setOpaque(false);
        painelPrincipal.add(areaBotoesmissao, BorderLayout.CENTER);
        frame.add(painelPrincipal);
        // --------------

        // Atualização do texto de introdução
        atualizarTexto("""
                Veggs, conhecido por suas loucuras, é um ex utilizador de drogas, porém reformado. \
                Conhecido por já usar todos os tipos de drogas: Cannabis, Cocaína, Crack, Extasy, entre outras; \
                ele cansou de usar as mesmas drogas repetitivamente, então ele pretende inovar e fazer uma livestream \
                ingerido todas as drogas desconhecidas provenientes de outros planetas da mesma Galáxia, então a nossa \
                missão é coletar novos tipos de drogas ao longo de vários planetas, em que a decisão certa trará uma \
                nova droga, mas apenas uma decisão, então as outras duas decisões poderão não conter a droga, que por \
                mais que progrida na história, o Veggs poderá ter um ataque de fúria por não estar todas as drogas \
                coletadas, então pense bem antes de cada decisão. Veggs é viciado em Trance Psicodélico e sempre fala \
                que horas são de hora em hora e ao mesmo tempo agradece aos membros do planeta por pertencerem ao tal. \
                Veggs cobra muito impostos, mais que o António Costa. Veggs localiza-se no planeta Pó, onde o chão é \
                constituído todo puramente de cocaína. Veggs nasceu com um nariz grande e apurado, sendo que na infância \
                era comparado ao Phineas do Phineas e Ferb ou comparado a um Tucano. Através duma Análise SWOT, ele \
                transformou essa fraqueza de nariz grande em uma força, já que usou o nariz a seu favor para cheirar \
                tudo o que houvesse ao redor e criar a fama que tem.""");

        if (dentroMissao && !gameOver) {
            jogo.mostrarStatus();
            areaStatus.setText(jogo.getUltimaMensagem());
        }
    }

    private void ajustarTamanhoTexto(String messagem) {
        FontMetrics metrics = areaTexto.getFontMetrics(areaTexto.getFont());
        int linhaVertical = metrics.getHeight();
        int linhaHorizontal = areaTexto.getWidth();
        int linhas = (int) Math.ceil(metrics.stringWidth(messagem) / (double) linhaHorizontal);
        int alturaNecessaria = linhas * linhaVertical + 20;

        painelFundo.setPreferredSize(new Dimension(linhaHorizontal, alturaNecessaria));
        painelFundo.revalidate();
    }

    private void atualizarTexto(String message) {
        if (atualizaTexto != null && !atualizaTexto.isDone()) {
            atualizaTexto.cancel(true);
        }

        areaTexto.setText("");
        ajustarTamanhoTexto(message);

        atualizaTexto = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws InterruptedException {
                for (int i = 0; i < message.length(); i++) {
                    if (isCancelled()) break;
                    publish(message.substring(0, i + 1));
                    Thread.sleep(30);
                }
                return null;
            }

            @Override
            protected void process(java.util.List<String> chunks) {
                if (!isCancelled()) {
                    areaTexto.setText(chunks.get(chunks.size() - 1));
                    ajustarTamanhoTexto(chunks.get(chunks.size() - 1));
                }
            }

            @Override
            protected void done() {
                if (!isCancelled() && message.contains("GAME OVER")) {
                    gameOver = true;

                    botaoExplorar.setEnabled(false);
                    botaoDescansar.setEnabled(false);

                    for (Component comp : areaBotoesmissao.getComponents()) {
                        if (comp instanceof JButton) {
                            comp.setEnabled(false);
                        }
                    }

                    JOptionPane.showMessageDialog(frame, mensagemGameOver, tituloGameOver, JOptionPane.INFORMATION_MESSAGE);
                }
            }
        };

        atualizaTexto.execute();
    }

    private void meterBotoesMissao(String[] opcoes) {
        gameOver = jogo.getNave().acabouRecurso();

        if (gameOver) {
            frame.dispose();
            JOptionPane.showMessageDialog(frame, mensagemGameOver, tituloGameOver, JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
            return;
        }

        areaBotoesmissao.removeAll();
        areaBotoesmissao.setLayout(new BorderLayout());

        JButton botaoComecar = new JButton("Avançar");
        botaoComecar.setVisible(true);
        botaoComecar.addActionListener(e -> {
            areaTexto.setText("");
            areaBotoesmissao.removeAll();
            areaBotoesmissao.setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(10, 20, 10, 20);

            for (int i = 0; i < opcoes.length; i++) {
                int escolha = i;
                JButton botaoOpcao = new JButton("Opção " + (i + 1) + ": " + opcoes[i]);

                botaoOpcao.setBackground(Color.BLACK);
                botaoOpcao.setForeground(Color.WHITE);
                botaoOpcao.setFont(new Font(tipoLetra, Font.BOLD, 16));
                botaoOpcao.setPreferredSize(new Dimension(frame.getWidth() - 200, 40));

                botaoOpcao.addActionListener(choiceEvent -> {
                    jogo.escolherOpcao(escolha);
                    atualizarTexto(jogo.getUltimaMensagem());
                    areaBotoesmissao.removeAll();
                    areaBotoesmissao.revalidate();
                    areaBotoesmissao.repaint();
                    dentroMissao = false;
                    if (dentroMissao && !gameOver) {
                        jogo.mostrarStatus();
                        areaStatus.setText(jogo.getUltimaMensagem());
                    }
                });

                areaBotoesmissao.add(botaoOpcao, gbc);
                gbc.gridy++;
            }

            botaoComecar.setVisible(false);
            botaoExplorar.setVisible(true);
            botaoDescansar.setVisible(true);
            if (atualizaTexto != null && !atualizaTexto.isDone()) {
                atualizaTexto.cancel(true);
            }
            StyledDocument doc = areaTexto.getStyledDocument();
            SimpleAttributeSet blackDot = new SimpleAttributeSet();
            StyleConstants.setForeground(blackDot, Color.white);

            SimpleAttributeSet defaultStyle = new SimpleAttributeSet();
            StyleConstants.setForeground(defaultStyle, Color.white);
            painelFundo.revalidate();
            painelFundo.repaint();

            try {
                doc.remove(0, doc.getLength());
                ajustarTamanhoTexto(".");
                doc.setCharacterAttributes(0, doc.getLength(), defaultStyle, false);
                areaTexto.setCaretPosition(doc.getLength());
            } catch (BadLocationException bl) {
                bl.printStackTrace();
            }

            painelFundo.revalidate();
            painelFundo.repaint();

            if (gameOver) {
                frame.dispose();
                JOptionPane.showMessageDialog(frame, mensagemGameOver, tituloGameOver, JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
                return;
            }

            areaBotoesmissao.revalidate();
            areaBotoesmissao.repaint();
        });

        botaoExplorar.setVisible(false);
        botaoDescansar.setVisible(false);
        areaBotoes.add(botaoComecar);

        areaBotoesmissao.revalidate();
        areaBotoesmissao.repaint();
    }
}
