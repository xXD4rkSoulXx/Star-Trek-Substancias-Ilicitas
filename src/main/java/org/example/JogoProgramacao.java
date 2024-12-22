package main.java.org.example;

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
    private Random rand;
    private String tituloEvento;

    public JogoProgramacao(String nomeJogador) {
        nave = new Nave(nomeJogador);
        missoes = criarMissoes();
        missaoAtual = null;
        contagemMissao = 0;
        gameOver = false;
        rand = new Random();
        tituloEvento = "Evento Aleatório";
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

    public boolean dentroMissao() {
        return this.missaoAtual != null && !gameOver;
    }

    public String[] getListaOpcoes() {
        return this.missaoAtual != null && !gameOver ? this.missaoAtual.getOpcoes() : new String[0];
    }
    // ----------------------------------------------------------------

    public void escolherOpcao(int escolha) {
        // Verifica se já não é GameOver
        if (missaoAtual != null && !gameOver) {
            // Executa a missão e obtém a mensagem que aparece depois de cada escolha
            ultimaMensagem = missaoAtual.executar(nave, escolha);

            // Verifica se a mensagem tem "Você perdeu!" para ver se a opção escolhida dá GameOVer
            if (ultimaMensagem.contains("Você perdeu!")) {
                gameOver = true;
                JOptionPane.showMessageDialog(null, "Game Over! Você perdeu nesta missão.", "Fim de Jogo", JOptionPane.ERROR_MESSAGE);
            } else {
                // Faz com que não haja evento aleatório depois do Final
                if (contagemMissao < 4 && rand.nextInt(2) == 0) {
                    // Evento aleatório
                    // ----------------
                    int evento = rand.nextInt(4);

                    switch (evento) {
                        case 0:
                            this.nave.perderVida(rand.nextInt(20) + 10);
                            JOptionPane.showMessageDialog(null, "Evento especial! Você perdeu vida inesperadamente.", tituloEvento, JOptionPane.INFORMATION_MESSAGE);
                            break;
                        case 1:
                            this.nave.perderVida(-rand.nextInt(20) + 10);
                            JOptionPane.showMessageDialog(null, "Evento especial! Você ganhou vida inesperadamente.", tituloEvento, JOptionPane.INFORMATION_MESSAGE);
                            break;
                        case 2:
                            this.nave.usarRecursos(0, rand.nextInt(10) + 5);
                            JOptionPane.showMessageDialog(null, "Evento especial! Você perdeu comida inesperadamente.", tituloEvento, JOptionPane.INFORMATION_MESSAGE);
                            break;
                        case 3:
                            this.nave.usarRecursos(0, -rand.nextInt(10) + 5);
                            JOptionPane.showMessageDialog(null, "Evento especial! Você ganhou comida inesperadamente.", tituloEvento, JOptionPane.INFORMATION_MESSAGE);
                            break;
                        default:
                            break;
                    }
                    // ----------------
                }
            }
        }
    }

    // Programação do Botão explorar
    // ------------------
    public void explorar() {
        if (!gameOver) {
            // Verifica se já ganhou o jogo
            if (nave.getSubstancias() == 4) {
                ultimaMensagem = "Você completou todas as missões!";
                setMissaoAtual(null);
            } else {
                // Verifica a contagem do planeta atual para dar a missão certa
                missaoAtual = missoes.get(contagemMissao);
                // Recebe a história inicial do planeta
                ultimaMensagem = missaoAtual.getMensagemIntroducao();
                // Passa para a próxima missão
                contagemMissao++;
            }
        } else {
            ultimaMensagem = "Jogo já acabou. Você perdeu.";
            JOptionPane.showMessageDialog(null, "Jogo já acabou. Você perdeu.", "Fim de Jogo", JOptionPane.ERROR_MESSAGE);
        }
    }
    // ------------------

    // Programação do Botão descansar
    // ------------------
    public void descansar() {
        if (!gameOver) {
            // Verifica se já ganhou o jogo
            if (contagemMissao == missoes.size()-1) {
                ultimaMensagem = "Você completou todas as missões!";
                setMissaoAtual(null);
            } else {
                ultimaMensagem = nave.descansar();
                JOptionPane.showMessageDialog(null, "Descansando... " + this.ultimaMensagem, "Descanso", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    // ------------------

    public void mostrarStatus() {
        if (!gameOver) {
            ultimaMensagem = nave.getStatus();
        }
    }

    // Metodo para criar as missões
    private List<Missao> criarMissoes() {
        List<Missao> criarMissoes = new ArrayList<>();

        // Mete as missões por ordem
        // -----
        criarMissoes.add(new MissaoPaz());
        criarMissoes.add(new MissaoJosue());
        criarMissoes.add(new MissaoFlap());
        criarMissoes.add(new MissaoKosky());
        criarMissoes.add(new Final());
        // -----

        return criarMissoes;
    }
}