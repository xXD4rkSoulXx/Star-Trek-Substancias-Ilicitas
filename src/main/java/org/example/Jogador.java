package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Jogador {
    private String nome;
    private String data_atual;
    private int substancias;

    // Os parâmetros do construtor é para obrigar um jogador a apenas ser criado
    // quando tiver estes status essências
    public Jogador(String nome, String data_atual, int substancias) {
        this.nome = nome;
        this.data_atual = data_atual;
        this.substancias = substancias;
    }

    // Encapsulamento necessário
    // --------------------------------------
    public String getNome() {
        return nome;
    }

    public String getDataAtual() {
        return data_atual;
    }

    public int getSubstancias() {
        return substancias;
    }
    // --------------------------------------

    // Metodo responsável por salvar os dados do jogador no ficheiro txt
    // --------------------------------------
    public static void SalvarFicheiro(Jogador jogador, String caminho) {
        File ficheiro = new File(caminho);
        // Cria a pasta e o ficheiro caso não exista, no caso se jogar pela primeira vez
        ficheiro.getParentFile().mkdirs();

        // Escreve no ficheiro com o BufferedWriter
        try (BufferedWriter escrever_ficheiro = new BufferedWriter(new FileWriter(caminho, true))) {
            escrever_ficheiro.write(String.format("%s;%s;%d\n", jogador.getNome(), jogador.getDataAtual(), jogador.getSubstancias()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // --------------------------------------

    // Metodo responsável por carregar os dados do jogador do ficheiro txt
    // --------------------------------------
    public static List<Jogador> CarregarFicheiro(String caminho) {
        List<Jogador> jogadores = new ArrayList<>();
        // Lê o ficheiro com o BufferedReader
        try (BufferedReader ler_ficheiro = new BufferedReader(new FileReader(caminho))) {
            String linha;
            // Enquanto não chegar ao final do ficheiro, vai continuar a ler
            while ((linha = ler_ficheiro.readLine()) != null) {
                // Vê os dados contidos por ponto e vírgula para distinguir entre o nome, data e substâncias
                String[] dados = linha.split(";");
                // Quando for 3 dados, ele adiciona o jogador, pois cada jogador tem 3 status
                // e o quarto seria o primeiro status do jogador seguinte
                if (dados.length == 3) {
                    jogadores.add(new Jogador(dados[0], dados[1], Integer.parseInt(dados[2])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jogadores;
    }
    // --------------------------------------
}