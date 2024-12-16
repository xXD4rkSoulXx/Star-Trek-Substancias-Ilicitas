package org.example;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.net.URL;

public class JogoInterfaceGrafica {
    private JFrame frame;
    private JTextPane area_status, area_texto;
    private JPanel area_botoes, area_botoesmissao, painel_superior, painel_fundo;
    private JButton botao_explorar, botao_descansar, botao_avancar;
    private JogoProgramacao jogo;
    private boolean dentro_missao = false;
    private boolean game_over = false;
    private SwingWorker<Void, String> atualiza_texto;
    private String nome_jogador;

    public JogoInterfaceGrafica(String nome) {
        this.nome_jogador = nome;
        jogo = new JogoProgramacao(this.nome_jogador);
        area_status = new JTextPane();
        area_texto = new JTextPane();
        area_botoes = new JPanel();
        area_botoesmissao = new JPanel();
        painel_superior = new JPanel();
        painel_fundo = new JPanel();
        ComecarJogo();
    }

    private void ComecarJogo() {
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

        // Configuração do painel superior
        // --------
        painel_superior.setLayout(new BorderLayout());
        painel_superior.setBackground(Color.BLACK);
        // --------

        // Botão Explorar
        // ---------------------------------------
        botao_explorar = new JButton("Explorar Local");
        botao_explorar.setVisible(false);
        botao_explorar.addActionListener(e -> {
            // Verifica se o jogador já ganhou
            // --------
            if (jogo.getNave().getSubstancias() == 4){
                area_status.setText("Acabou o jogo!");
                return;
            }
            // --------

            // Verifica se o jogador já perdeu
            // --------
            if (!game_over && !dentro_missao) {
                jogo.Explorar();
                AtualizarTexto(jogo.getUltimaMensagem());
                if (jogo.DentroMissao()) {
                    dentro_missao = true;
                    MeterBotoesMissao(jogo.getListaOpcoes());
                }
            }
            // --------

            // Verifica se o jogador já perdeu
            // --------
            if (dentro_missao && !game_over) {
                jogo.MostrarStatus();
                area_status.setText(jogo.getUltimaMensagem());
            }
            // --------
        });
        // ---------------------------------------

        // Botão Descansar
        // ---------------------------------------
        botao_descansar = new JButton("Descansar");
        botao_descansar.setVisible(false);
        botao_descansar.addActionListener(e -> {
            // Verifica se o jogador já ganhou
            // --------
            if (jogo.getNave().getSubstancias() == 4){
                return;
            }
            // --------

            // Verifica se o jogador já perdeu
            // --------
            if (!game_over && !dentro_missao) {
                jogo.Descansar();
                AtualizarTexto(jogo.getUltimaMensagem());
            }
            // --------

            // Verifica se o jogador já perdeu
            // --------
            if (dentro_missao && !game_over) {
                jogo.MostrarStatus();
                area_status.setText(jogo.getUltimaMensagem());
            }
            // --------
        });
        // ---------------------------------------

        // Botão Avançar
        // ---------------------------------------
        botao_avancar = new JButton("Avançar");
        botao_avancar.setVisible(true);
        botao_avancar.addActionListener(e -> {
            // Mete o botão Avançar invisível e faz aparecer os outros botões
            // --------
            botao_avancar.setVisible(false);
            botao_explorar.setVisible(true);
            botao_descansar.setVisible(true);
            // --------

            // Quando clico no botão avançar, ele faz o texto parar de aparecer para skipar
            // --------
            if (atualiza_texto != null && !atualiza_texto.isDone()) {
                atualiza_texto.cancel(true);
            }
            // --------

            // Remove o texto que está lá ao skipar
            // -------------------------------------
            StyledDocument doc = area_texto.getStyledDocument();
            SimpleAttributeSet ponto_preto = new SimpleAttributeSet();
            StyleConstants.setForeground(ponto_preto, Color.white);

            SimpleAttributeSet estilo_default = new SimpleAttributeSet();
            StyleConstants.setForeground(estilo_default, Color.white);
            painel_fundo.revalidate();
            painel_fundo.repaint();

            try {
                doc.remove(0, doc.getLength());
                AjustarTamanhoTexto(".");
                doc.setCharacterAttributes(0, doc.getLength(), estilo_default, false);
                area_texto.setCaretPosition(doc.getLength());
            } catch (BadLocationException bl) {
                bl.printStackTrace();
            }
            // -------------------------------------
        });
        // ---------------------------------------

        // Painel dos botões
        // ---------------------------------------
        area_botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        area_botoes.setOpaque(false);
        area_botoes.add(botao_avancar);
        area_botoes.add(botao_explorar);
        area_botoes.add(botao_descansar);
        painel_superior.add(area_botoes, BorderLayout.EAST);
        // ---------------------------------------

        // Área de status da missão
        // ---------------------------------------
        area_status = new JTextPane();
        area_status.setOpaque(true);
        area_status.setBackground(Color.BLACK);
        area_status.setEditable(false);
        area_status.setFont(new Font("Arial", Font.BOLD, 16));
        area_status.setForeground(Color.WHITE);
        painel_superior.add(area_status, BorderLayout.CENTER);
        // ---------------------------------------

        // Painel flutuante do texto
        // ---------------------------------------
        JPanel painel_flutuante = new JPanel();
        painel_flutuante.setOpaque(false);
        painel_flutuante.setLayout(new BoxLayout(painel_flutuante, BoxLayout.Y_AXIS));
        painel_flutuante.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
        // ---------------------------------------

        // Painel de fundo do texto
        // ---------------------------------------
        painel_fundo.setBackground(Color.BLACK);
        painel_fundo.setPreferredSize(new Dimension(600, 100));
        painel_fundo.setLayout(new BorderLayout());
        // ---------------------------------------

        // Configuração da área do texto
        // ---------------------------------------
        area_texto.setOpaque(true);
        area_texto.setEditable(false);
        area_texto.setFont(new Font("Arial", Font.BOLD, 16));
        area_texto.setForeground(Color.WHITE);
        area_texto.setBackground(Color.BLACK);

        StyledDocument doc = area_texto.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        painel_fundo.add(area_texto, BorderLayout.CENTER);
        // ---------------------------------------

        //Faz com que o painel fundo apareça no centro do ecrã
        // ---------------------------------------
        //Faz um espaço grande em cima
        painel_flutuante.add(Box.createVerticalGlue());
        painel_flutuante.add(painel_fundo);
        //Faz um espaço grande em baixo
        painel_flutuante.add(Box.createVerticalGlue());
        // ---------------------------------------

        // Adiciona os painéis ao painel principal
        // --------------
        painel_principal.add(painel_superior, BorderLayout.NORTH);
        painel_principal.add(painel_flutuante, BorderLayout.SOUTH);
        // --------------

        // Configuração dos botões de missão
        // --------------
        area_botoesmissao = new JPanel();
        area_botoesmissao.setLayout(new GridBagLayout());
        area_botoesmissao.setOpaque(false);
        painel_principal.add(area_botoesmissao, BorderLayout.CENTER);
        frame.add(painel_principal);
        // --------------

        // Atualização do texto de introdução
        AtualizarTexto("""
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

        if (dentro_missao && !game_over) {
            jogo.MostrarStatus();
            area_status.setText(jogo.getUltimaMensagem());
        }
    }

    private void AjustarTamanhoTexto(String message) {
        FontMetrics metrics = area_texto.getFontMetrics(area_texto.getFont());
        int linha_vertical = metrics.getHeight();
        int linha_horizontal = area_texto.getWidth();
        int linhas = (int) Math.ceil(metrics.stringWidth(message) / (double) linha_horizontal);
        int altura_necessaria = linhas * linha_vertical + 20;

        painel_fundo.setPreferredSize(new Dimension(linha_horizontal, altura_necessaria));
        painel_fundo.revalidate();
    }

    private void AtualizarTexto(String message) {
        if (atualiza_texto != null && !atualiza_texto.isDone()) {
            atualiza_texto.cancel(true);
        }

        area_texto.setText("");
        AjustarTamanhoTexto(message);

        atualiza_texto = new SwingWorker<>() {
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
                    area_texto.setText(chunks.get(chunks.size() - 1));
                    AjustarTamanhoTexto(chunks.get(chunks.size() - 1));
                }
            }

            @Override
            protected void done() {
                if (!isCancelled() && message.contains("GAME OVER")) {
                    game_over = true;

                    botao_explorar.setEnabled(false);
                    botao_descansar.setEnabled(false);

                    for (Component comp : area_botoesmissao.getComponents()) {
                        if (comp instanceof JButton) {
                            comp.setEnabled(false);
                        }
                    }

                    JOptionPane.showMessageDialog(frame, "GAME OVER! Obrigado por jogar!", "Fim do Jogo", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        };

        atualiza_texto.execute();
    }

    private void MeterBotoesMissao(String[] options) {
        game_over = jogo.getNave().AcabouRecurso();

        if (game_over) {
            frame.dispose();
            JOptionPane.showMessageDialog(frame, "GAME OVER! Obrigado por jogar!", "Fim do Jogo", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
            return;
        }

        area_botoesmissao.removeAll();
        area_botoesmissao.setLayout(new BorderLayout());

        JButton botao_comecar = new JButton("Avançar");
        botao_comecar.setVisible(true);
        botao_comecar.addActionListener(e -> {
            area_texto.setText("");
            area_botoesmissao.removeAll();
            area_botoesmissao.setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(10, 20, 10, 20);

            for (int i = 0; i < options.length; i++) {
                int escolha = i;
                JButton botao_opcao = new JButton("Opção " + (i + 1) + ": " + options[i]);

                botao_opcao.setBackground(Color.BLACK);
                botao_opcao.setForeground(Color.WHITE);
                botao_opcao.setFont(new Font("Arial", Font.BOLD, 16));
                botao_opcao.setPreferredSize(new Dimension(frame.getWidth() - 200, 40));

                botao_opcao.addActionListener(choiceEvent -> {
                    jogo.EscolherOpcao(escolha);
                    AtualizarTexto(jogo.getUltimaMensagem());
                    area_botoesmissao.removeAll();
                    area_botoesmissao.revalidate();
                    area_botoesmissao.repaint();
                    dentro_missao = false;
                    if (dentro_missao && !game_over) {
                        jogo.MostrarStatus();
                        area_status.setText(jogo.getUltimaMensagem());
                    }
                });

                area_botoesmissao.add(botao_opcao, gbc);
                gbc.gridy++;
            }

            botao_comecar.setVisible(false);
            botao_explorar.setVisible(true);
            botao_descansar.setVisible(true);
            if (atualiza_texto != null && !atualiza_texto.isDone()) {
                atualiza_texto.cancel(true);
            }
            StyledDocument doc = area_texto.getStyledDocument();
            SimpleAttributeSet blackDot = new SimpleAttributeSet();
            StyleConstants.setForeground(blackDot, Color.white);

            SimpleAttributeSet defaultStyle = new SimpleAttributeSet();
            StyleConstants.setForeground(defaultStyle, Color.white);
            painel_fundo.revalidate();
            painel_fundo.repaint();

            try {
                doc.remove(0, doc.getLength());
                AjustarTamanhoTexto(".");
                doc.setCharacterAttributes(0, doc.getLength(), defaultStyle, false);
                area_texto.setCaretPosition(doc.getLength());
            } catch (BadLocationException bl) {
                bl.printStackTrace();
            }

            painel_fundo.revalidate();
            painel_fundo.repaint();

            if (game_over) {
                frame.dispose();
                JOptionPane.showMessageDialog(frame, "GAME OVER! Obrigado por jogar!", "Fim do Jogo", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
                return;
            }

            area_botoesmissao.revalidate();
            area_botoesmissao.repaint();
        });

        botao_explorar.setVisible(false);
        botao_descansar.setVisible(false);
        area_botoes.add(botao_comecar);

        area_botoesmissao.revalidate();
        area_botoesmissao.repaint();
    }
}