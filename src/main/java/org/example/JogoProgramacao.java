package org.example;

import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class JogoProgramacao {
    private Nave nave;
    private List<Missao> missoes;
    private Missao missaoAtual;
    private int contagemMissao;
    private String ultimaMensagem;
    private boolean gameOver;

    public JogoProgramacao(String nomeJogador) {
        this.missoes = CriarMissoes();
        this.missaoAtual = null;
        this.contagemMissao = 0;
        this.nave = new Nave(nomeJogador);
        this.gameOver = false;
    }

    // Encapsulamento
    // ----------------------------------------------------------------
    public void setMissaoAtual(Missao missao) {
        this.missaoAtual = missao;
    }

    public void setUltimaMensagem(String mensagem) {
        this.ultimaMensagem = mensagem;
    }

    public String getUltimaMensagem() {
        // Se já tiver as 4 substâncias, significa que ganhaste e já estás no planeta Veggs,
        // então é a hora de entregar tudo
        if (nave.getSubstancias() == 4) {
            return """
                    Chegaste ao Planeta Veggs, com as substâncias para o rei Veggs: Sal Grosso (Planeta Solana), \
                    Mineral Germânico (Planeta Ritter), Dosagem Máxima (Planeta Estudo) e Opioide (Planeta Kosky). \
                    Ao chegar, vê-se o Rei Veggs ansioso pelas as drogas gritando como louco e falando que horas são. \
                    Ao entregar as drogas, Veggs abre Transmissão ao Vivo para todos os planetas da Galáxia Estudo \
                    conseguirem ver. Era um momento imperdível. Veggs então liga o seu Trance Psicodélico, e começa a \
                    picar o Germânico para ficar em picadinhos e o mesmo com o Sal Gross. Misturou os picadinhos do \
                    Germânico com os picadinhos Sal Grosso e ainda com as beatas dos cigarros que ele fumou em estado \
                    nervoso. Em seguida esperou pelo drop do Trance. Ao chegar ao drop, o Veggs começa a aspirar tudo \
                    com o nariz, muito melhor dos aspiradores que se vê na Worten. Ao aspirar tudo, Veggs em seguida \
                    bebe a Dosagem e injeta o Opioide em suas veias. Ao ingerir tudo, Veggs dá um grito de alegria e \
                    começa a rebolar só de boxers e Durag em comemoração. Com isso sua missão estaria completa. \
                    E infelizmente, o rei Veggs, morre de Overdose em Livestream, passando a melhor mensagem de todos \
                    os tempos “Não usem drogas”.""";
        }
        // Se não cair no if, então aparece a história que é para aparecer, configurado em cada missão específica
        return this.ultimaMensagem;
    }

    public Nave getNave() {
        return this.nave;
    }

    public boolean DentroMissao() {
        return this.missaoAtual != null && !gameOver;
    }

    public String[] getListaOpcoes() {
        return this.missaoAtual != null && !gameOver ? this.missaoAtual.getOpcoes() : new String[0];
    }
    // ----------------------------------------------------------------

    public void EscolherOpcao(int escolha) {
        // Verifica se já não é GameOver
        if (this.missaoAtual != null && !gameOver) {
            // Executa a missão e obtém a mensagem que aparece depois de cada escolha
            this.ultimaMensagem = this.missaoAtual.Executar(nave, escolha);

            // Verifica se a mensagem tem "Você perdeu!" para ver se a opção escolhida dá GameOVer
            if (this.ultimaMensagem.contains("Você perdeu!")) {
                gameOver = true;
                JOptionPane.showMessageDialog(null, "Game Over! Você perdeu nesta missão.", "Fim de Jogo", JOptionPane.ERROR_MESSAGE);
            } else {
                // Faz com que não haja evento aleatório depois do Final
                if (contagemMissao < 4) {
                    // Evento aleatório
                    // ----------------
                    Random rand = new Random();

                    if (rand.nextInt(2) == 0) {
                        int evento = rand.nextInt(4);

                        switch (evento) {
                            case 0:
                                this.nave.PerderVida(rand.nextInt(20) + 10);
                                JOptionPane.showMessageDialog(null, "Evento especial! Você perdeu vida inesperadamente.", "Evento Aleatório", JOptionPane.INFORMATION_MESSAGE);
                                break;
                            case 1:
                                this.nave.PerderVida(-rand.nextInt(20) + 10);
                                JOptionPane.showMessageDialog(null, "Evento especial! Você ganhou vida inesperadamente.", "Evento Aleatório", JOptionPane.INFORMATION_MESSAGE);
                                break;
                            case 2:
                                this.nave.UsarRecursos(0, rand.nextInt(10) + 5);
                                JOptionPane.showMessageDialog(null, "Evento especial! Você perdeu comida inesperadamente.", "Evento Aleatório", JOptionPane.INFORMATION_MESSAGE);
                                break;
                            case 3:
                                this.nave.UsarRecursos(0, -rand.nextInt(10) + 5);
                                JOptionPane.showMessageDialog(null, "Evento especial! Você perdeu ganhou inesperadamente.", "Evento Aleatório", JOptionPane.INFORMATION_MESSAGE);
                                break;
                        }
                    }
                    // ----------------
                }
            }
        }
    }

    // Programação do Botão Explorar
    // ------------------
    public void Explorar() {
        if (!gameOver) {
            // Verifica se já ganhou o jogo
            if (nave.getSubstancias() == 4) {
                this.ultimaMensagem = "Você completou todas as missões!";
                this.setMissaoAtual(null);
            } else {
                // Verifica a contagem do planeta atual para dar a missão certa
                this.missaoAtual = this.missoes.get(contagemMissao);
                // Recebe a história inicial do planeta
                this.ultimaMensagem = this.missaoAtual.getMensagemIntroducao();
                // Passa para a próxima missão
                this.contagemMissao++;
            }
        } else {
            this.ultimaMensagem = "Jogo já acabou. Você perdeu.";
            JOptionPane.showMessageDialog(null, "Jogo já acabou. Você perdeu.", "Fim de Jogo", JOptionPane.ERROR_MESSAGE);
        }
    }
    // ------------------

    // Programação do Botão Descansar
    // ------------------
    public void Descansar() {
        if (!gameOver) {
            // Verifica se já ganhou o jogo
            if (contagemMissao == missoes.size()-1) {
                this.ultimaMensagem = "Você completou todas as missões!";
                this.setMissaoAtual(null);
            } else {
                this.ultimaMensagem = this.nave.Descansar();
                JOptionPane.showMessageDialog(null, "Descansando... " + this.ultimaMensagem, "Descanso", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    // ------------------

    public void MostrarStatus() {
        if (!gameOver) {
            this.ultimaMensagem = this.nave.getStatus();
        }
    }

    // Metodo para criar as missões
    private List<Missao> CriarMissoes() {
        List<Missao> missoes = new ArrayList<>();

        // Mete as missões por ordem
        // -----
        missoes.add(new MissaoPaz());
        missoes.add(new MissaoJosue());
        missoes.add(new MissaoFlap());
        missoes.add(new MissaoKosky());
        missoes.add(new Final());
        // -----

        return missoes;
    }
}