package org.example;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Nave {
    private int energia;
    private int comida;
    private int reputacao;
    private int vida;
    private int substancias;
    // Mensagens que aparecem depois de descansar
    private List<String> mensagem_acordar;
    // Mensagens que aparecem quando quer descansar, mas os Status já estão no máximo
    private List<String> mensagem_jadescansou;
    // Objeto para usar o metodo random
    private Random r;
    // Número random para selecionar de forma randômica a mensagem de acordar
    private int random;
    // Verifica se já é GameOver ou não
    private boolean game_over;
    private String nome_jogador;
    private JFrame frame;

    public Nave(String nome_jogador) {
        this.nome_jogador = nome_jogador;
        this.energia = 100;
        this.comida = 100;
        this.reputacao = 10;
        this.vida = 100;
        this.substancias = 0;
        this.game_over = false;
        r = new Random();

        // Configurar as possíveis mensagens ao acordar
        // --------------------------------------
        this.mensagem_acordar = new ArrayList<>();
        this.mensagem_acordar.add("""
                Já chega de descanso, Baiano! É hora de acordar.
                Você restaurou toda sua energia, comida e vida.""");
        this.mensagem_acordar.add("""
                Baiano, o tempo de descanso acabou!
                Você recuperou toda sua energia, comida e vida.""");
        this.mensagem_acordar.add("""
                Ei, Baiano, já dormiu o bastante! Levanta!
                Você recuperou a energia, comida e saúde.""");
        this.mensagem_acordar.add("""
                Chega de soneca, Baiano! Hora de levantar.
                Sua vida, energia e suprimentos foram restaurados.""");
        this.mensagem_acordar.add("""
                Descanso finalizado, Baiano! Hora de se levantar.
                Você está com energia, comida e vida completas.""");
        this.mensagem_acordar.add("""
                Vamos acordar, Baiano! Já descansou o suficiente.
                Sua vida, energia e fome foram restauradas.""");
        this.random = r.nextInt(mensagem_acordar.size());
        // --------------------------------------

        // Configurar as possíveis mensagens ao querer descansar com os Status máximo
        // --------------------------------------
        this.mensagem_jadescansou = new ArrayList<>();
        this.mensagem_jadescansou.add("""
                Baiano, eu sei que o sono ainda te chama, mas já acordaste! Agora é hora de ação!""");
        this.mensagem_jadescansou.add("""
                Nada de descanso agora, Baiano! Já estás mais do que recuperado.""");
        this.mensagem_jadescansou.add("""
                Baiano, nem mais um cochilo! Estás desperto, nada de tentar dormir de novo!""");
        this.mensagem_jadescansou.add("""
                Baiano, nem adianta! Acabaste de acordar, então nada de voltas ao sono!""");
        this.mensagem_jadescansou.add("""
                Descansar mais? Nem pensar, Baiano! Já estás pronto para a próxima!""");
        // --------------------------------------
    }

    // Encapsulamentos necessários
    // ------------------------------------------------------------------------
    // Mostrar Status
    // --------
    public String getStatus() {
        if (game_over) {
            return "Você perdeu!";
        }

        return String.format("Energia: %d | " +
                        "Comida: %d | " +
                        "Reputação: %d | " +
                        "Saúde da Tripulação: %d | " +
                        "Substâncias Proibidas: %d/4",
                        this.energia,
                        this.comida,
                        this.reputacao,
                        this.vida,
                        this.substancias);
    }
    // --------

    public void UsarRecursos(int custo_energia, int custo_comida) {
        if (game_over) {
            return;
        }

        this.energia -= custo_energia;
        this.comida -= custo_comida;

        if (this.comida <= 0) {
            // Para não haver status negativos, pûs para receber 0
            this.comida = 0;
            game_over = true;
        }
    }

    public void AdicionarSubstancias() {
        if (game_over) {
            return;
        }
        this.substancias++;

        // Atualiza no txt a quantidade de substâncias já que a substância é a pontuação
        // --------------------------------------
        try {
            File ficheiro = new File("src/Players/jogadores.txt");
            List<String> dados = new ArrayList<>();

            // Vai buscar os dados ao txt para depois poder atualizar para não estar a zerar os dados
            // ------------
            if (ficheiro.exists()) {
                BufferedReader ler_ficheiro = new BufferedReader(new FileReader(ficheiro));
                String linha;
                // Vai buscar os dados enquanto não for null, porque quando for null significa que cheguei
                // ao EOF (Fim do ficheiro)
                while ((linha = ler_ficheiro.readLine()) != null) {
                    dados.add(linha);
                }
                ler_ficheiro.close();
            }
            // ------------

            // Substitui os dados antigos pelos novos
            // ------------
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String data_atual = formato.format(new Date());

            String atualizacao = String.format("%s;%s;%d", nome_jogador, data_atual, substancias);
            if (!dados.isEmpty()) {
                dados.set(dados.size() - 1, atualizacao);
            } else {
                dados.add(atualizacao);
            }
            // ------------

            // Atualiza os novos dados no ficheiro
            // ------------
            BufferedWriter escrever_ficheiro = new BufferedWriter(new FileWriter(ficheiro));
            for (String registo : dados) {
                escrever_ficheiro.write(registo);
                escrever_ficheiro.newLine();
            }
            escrever_ficheiro.close();
            // ------------

            System.out.println("Jogador atualizado com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // --------------------------------------
    }

    public int getSubstancias() {
        return this.substancias;
    }

    public void AumentarReputacao(int quantidade) {
        if (game_over) {
            return;
        }
        this.reputacao += quantidade;
    }

    public void PerderVida(int quantidade) {
        if (game_over) {
            return;
        }
        this.vida -= quantidade;
        if (this.vida <= 0) {
            this.vida = 0;
            game_over = true;
        }
    }

    public String Descansar() {
        if (game_over) {
            return "Você perdeu!";
        }

        // Verifica se a energia ou a vida já estão no máximo para impôr um limite
        // ------------
        if ((this.energia == 100) || (this.vida == 100)) {
            this.random = r.nextInt(mensagem_jadescansou.size());
            return this.mensagem_jadescansou.get(random);
        } else {
            this.energia += 10;
            this.comida += 5;
            this.vida += 5;

            if (this.energia > 100)
                this.energia = 100;
            if (this.vida > 100)
                this.vida = 100;

            this.random = r.nextInt(mensagem_acordar.size());
            return this.mensagem_acordar.get(random);
        }
        // ------------
    }

    public int getVida() {
        return this.vida;
    }

    public int getComida() {
        return this.comida;
    }

    public String getNomeJogador() {
        return this.nome_jogador;
    }
    // ------------------------------------------------------------------------


    // Metodos necessários para a Interface
    // ---------------------------------
    public boolean AcabouRecurso() {
        if (this.vida <= 0 || this.comida <= 0) {
            return true;
        } else return false;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
    // ---------------------------------
}