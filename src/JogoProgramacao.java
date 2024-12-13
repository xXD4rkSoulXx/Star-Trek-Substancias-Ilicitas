import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class JogoProgramacao {
    private Nave nave;
    private List<Missao> missoes;
    private Missao missao_atual;
    private int contagem_missao;
    private String ultima_mensagem;
    private boolean gameOver;

    public JogoProgramacao(String playerName) {
        this.missoes = CriarMissoes();
        this.missao_atual = null;
        this.contagem_missao = 0;
        this.nave = new Nave(playerName);
        this.gameOver = false;
    }

    public String getUltimaMensagem() {
        if(nave.getSubstancias()==4) {
            return "Chegaste ao Planeta Veggs, com as substâncias para o rei Veggs: Sal Grosso (Planeta Solana), Mineral Germânico (Planeta Ritter), Dosagem Máxima (Planeta Estudo) e Opioide (Planeta Kosky). Ao chegar, vê-se o Rei Veggs ansioso pelas as drogas gritando como louco e falando que horas são. Ao entregar as drogas, Veggs abre Transmissão ao Vivo para todos os planetas da Galáxia Estudo conseguirem ver. Era um momento imperdível. Veggs então liga o seu Trance Psicodélico, e começa a picar o Germânico para ficar em picadinhos e o mesmo com o Sal Gross. Misturou os picadinhos do Germânico com os picadinhos Sal Grosso e ainda com as beatas dos cigarros que ele fumou em estado nervoso. Em seguida esperou pelo drop do Trance. Ao chegar ao drop, o Veggs começa a aspirar tudo com o nariz, muito melhor dos aspiradores que se vê na Worten. Ao aspirar tudo, Veggs em seguida bebe a Dosagem e injeta o Opioide em suas veias. Ao ingerir tudo, Veggs dá um grito de alegria e começa a rebolar só de boxers e Durag em comemoração. Com isso sua missão estaria completa. E infelizmente, o rei Veggs, morre de Overdose em Livestream, passando a melhor mensagem de todos os tempos “Não usem drogas”.";
        }
        return this.ultima_mensagem;
    }

    public void setUltimaMensagem(String mensagem) {
        this.ultima_mensagem = mensagem;
    }

    public void setMissaoAtual(Missao missao) {
        this.missao_atual = missao;
    }

    public Nave getNave() {
        return this.nave;
    }

    public boolean isGameOver() {
        return this.gameOver;
    }

    public boolean DentroMissao() {
        return this.missao_atual != null && !gameOver;
    }

    public String[] getListaOpcoes() {
        return this.missao_atual != null && !gameOver ? this.missao_atual.getOpcoes() : new String[0];
    }

    public void EscolherOpcao(int escolha) {
        if (this.missao_atual != null && !gameOver) {
            this.ultima_mensagem = this.missao_atual.Executar(nave, escolha);
            System.out.println(this.nave.getStatus());

            // Verifica se a missão é a que causa o game over
            if (this.ultima_mensagem.contains("Você perdeu!")) {
                gameOver = true;
                JOptionPane.showMessageDialog(null, "Game Over! Você perdeu nesta missão.", "Fim de Jogo", JOptionPane.ERROR_MESSAGE);
            } else {
                if (contagem_missao < 4) {  // Exibe o pop-up apenas para as primeiras 4 missões
                    eventoAleatorio();
                }
            }
        }
    }

    public void Explorar() {
        if (!gameOver) {
            if (contagem_missao < missoes.size()) {
                this.missao_atual = this.missoes.get(contagem_missao);
                this.ultima_mensagem = this.missao_atual.getMensagemIntroducao();

                // Remover a exibição da missão, sem pop-up ou mensagem extra
                // Não mostra nada sobre a missão aqui.

                this.contagem_missao++;
            } else {
                this.ultima_mensagem = "Você completou todas as missões!";
                JOptionPane.showMessageDialog(null, "Parabéns! Você completou todas as missões!", "Jogo Finalizado", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            this.ultima_mensagem = "Jogo já acabou. Você perdeu.";
            JOptionPane.showMessageDialog(null, "Jogo já acabou. Você perdeu.", "Fim de Jogo", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void MostrarStatus() {
        if (!gameOver) {
            this.ultima_mensagem = this.nave.getStatus();
            // Exibe o status no console em vez de um pop-up
            System.out.println("Status da Nave: " + this.ultima_mensagem);
        }
    }

    public void Descansar() {
        if (!gameOver) {
            this.ultima_mensagem = this.nave.Descansar();
            JOptionPane.showMessageDialog(null, "Descansando... " + this.ultima_mensagem, "Descanso", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Método para criar as missões
    private List<Missao> CriarMissoes() {
        List<Missao> missoes = new ArrayList<>();

        missoes.add(new MissaoPaz());
        missoes.add(new MissaoJosue());
        missoes.add(new MissaoFlap());
        missoes.add(new MissaoKosky());
        missoes.add(new Final());

        return missoes;
    }

    private void eventoAleatorio() {
        Random rand = new Random();

        if (rand.nextInt(2) == 0) {
            int evento = rand.nextInt(4);

            switch (evento) {
                case 0:
                    int perdaVida = rand.nextInt(20) + 10;
                    this.nave.AumentarVida(-perdaVida);
                    // Não escreve no bloco de texto
                    break;
                case 1:
                    int ganhaVida = rand.nextInt(20) + 10;
                    this.nave.AumentarVida(ganhaVida);
                    if (this.nave.getVida() > 100) {
                        this.nave.AumentarVida(100 - this.nave.getVida());
                    }
                    // Não escreve no bloco de texto
                    break;
                case 2:
                    int perdaComida = rand.nextInt(10) + 5;
                    this.nave.UsarRecursos(0, perdaComida);
                    // Não escreve no bloco de texto
                    break;
                case 3:
                    int ganhaComida = rand.nextInt(10) + 5;
                    this.nave.UsarRecursos(0, -ganhaComida);
                    if (this.nave.getComida() > 100) {
                        this.nave.UsarRecursos(0, this.nave.getComida() - 100);
                    }
                    // Não escreve no bloco de texto
                    break;
            }
        } else {
            // Não escreve no bloco de texto
        }

        // Pop-up de evento aleatório com botão de OK
        JOptionPane.showMessageDialog(null, "Nada de especial aconteceu após a missão. Você segue em frente.", "Evento Aleatório", JOptionPane.INFORMATION_MESSAGE);
    }
}