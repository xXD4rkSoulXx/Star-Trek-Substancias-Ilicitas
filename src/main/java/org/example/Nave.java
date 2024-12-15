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
    private List<String> mensagem_acordar; // Mensagens que aparecem depois de descansar
    private List<String> mensagem_jadescansou; // Mensagens que aparecem quando quer descansar, mas os Status já estão no máximo
    private Random r; // Objeto para usar o método random
    private int random; // Número random para selecionar de forma randômica a mensagem de acordar
    private boolean gameOver; // Variável para controlar o estado do jogo
    private String jogadorNome; // Nome do jogador
    private JFrame frame; // A instância do JFrame para manipular a janela

    public Nave(String jogadorNome) {
        this.energia = 100;
        this.comida = 100;
        this.reputacao = 100;
        this.vida = 100;
        this.substancias = 0;
        this.gameOver = false; // Inicialmente, o jogo não está em estado de Game Over
        this.jogadorNome = jogadorNome; // Define o nome do jogador
        r = new Random();

        // Configurar as possíveis mensagens ao acordar
        this.mensagem_acordar = new ArrayList<>();
        this.mensagem_acordar.add("Já chega de descanso, Baiano! É hora de acordar.\nVocê restaurou toda sua energia, comida e vida.");
        this.mensagem_acordar.add("Baiano, o tempo de descanso acabou!\nVocê recuperou toda sua energia, comida e vida.");
        this.mensagem_acordar.add("Ei, Baiano, já dormiu o bastante! Levanta!\nVocê recuperou a energia, comida e saúde.");
        this.mensagem_acordar.add("Chega de soneca, Baiano! Hora de levantar.\nSua vida, energia e suprimentos foram restaurados.");
        this.mensagem_acordar.add("Descanso finalizado, Baiano! Hora de se levantar.\nVocê está com energia, comida e vida completas.");
        this.mensagem_acordar.add("Vamos acordar, Baiano! Já descansou o suficiente.\nSua vida, energia e fome foram restauradas.");
        this.random = r.nextInt(mensagem_acordar.size());

        // Configurar as possíveis mensagens ao querer descansar com os Status máximo
        this.mensagem_jadescansou = new ArrayList<>();
        this.mensagem_jadescansou.add("Baiano, eu sei que o sono ainda te chama, mas já acordaste! Agora é hora de ação!");
        this.mensagem_jadescansou.add("Nada de descanso agora, Baiano! Já estás mais do que recuperado.");
        this.mensagem_jadescansou.add("Baiano, nem mais um cochilo! Estás desperto, nada de tentar dormir de novo!");
        this.mensagem_jadescansou.add("Baiano, nem adianta! Acabaste de acordar, então nada de voltas ao sono!");
        this.mensagem_jadescansou.add("Descansar mais? Nem pensar, Baiano! Já estás pronto para a próxima!");
    }

    public String getStatus() {
        if (gameOver) {
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

    public void UsarRecursos(int custoenergia, int custocomida) {
        if (gameOver) {
            return;
        }

        this.energia -= custoenergia;
        this.comida -= custocomida;

        if (this.comida <= 0) {
            this.comida = 0;
            gameOver = true;
            redirecionarParaTelaInicial(this.frame); // Redirecionar ao Game Over
        }
    }

    public void AdicionarSubstancias() {
        if (gameOver) {
            return;
        }
        this.substancias++;

        // Após coletar uma substância, salvar o estado no arquivo
        salvarEstado();
    }

    public int getSubstancias() {
        return this.substancias;
    }

    public void AumentarReputacao(int quantidade) {
        if (gameOver) {
            return;
        }
        this.reputacao += quantidade;
    }

    public void AumentarVida(int quantidade) {
        if (gameOver) {
            return;
        }
        this.vida += quantidade;
        if (this.vida <= 0) {
            this.vida = 0;
            gameOver = true;
            redirecionarParaTelaInicial(this.frame); // Redirecionar ao Game Over
        }
    }

    public String Descansar() {
        if (gameOver) {
            return "Você perdeu!";
        }

        if ((this.energia == 100) && (this.vida == 100)) {
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
    }

    private void salvarEstado() {
        try {
            File file = new File("src/Players/jogadores.txt"); // Arquivo onde os dados são salvos
            List<String> registros = new ArrayList<>();

            // Lê os registros existentes
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String linha;
                while ((linha = br.readLine()) != null) {
                    registros.add(linha);
                }
                br.close();
            }

            // Obter a data e hora atual para salvar
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dataHora = sdf.format(new Date());

            // Substitui o último registro ou adiciona um novo caso o arquivo esteja vazio
            String novoRegistro = String.format("%s;%s;%d", jogadorNome, dataHora, substancias);
            if (!registros.isEmpty()) {
                registros.set(registros.size() - 1, novoRegistro); // Atualiza o último registro
            } else {
                registros.add(novoRegistro); // Adiciona o primeiro registro
            }

            // Reescreve o arquivo com os registros atualizados
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for (String registro : registros) {
                bw.write(registro);
                bw.newLine();
            }
            bw.close();

            System.out.println("Jogador atualizado com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getVida() {
        return this.vida;
    }

    public int getComida() {
        return this.comida;
    }

    public String getPlayerName() {
        return this.jogadorNome;
    }

    private void redirecionarParaTelaInicial(JFrame frame) {
        System.out.println(".........................");
        System.out.println(this.frame);
        System.out.println(this.getStatus());
        System.out.println(".........................");

        SwingUtilities.invokeLater(() -> {
            frame.dispose();
            SwingUtilities.invokeLater(JogoTelaInicial::new);

        });
    }

    public boolean algumRecursoZerado() {
        if (this.vida <= 0) {
            return true;
        } else if (this.energia <= 0 ) {
            return true;
        } else if (this.comida <= 0 ) {
            return true;
        } else if (this.reputacao <= 0 ) {
            return true;
        } else return false;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
}