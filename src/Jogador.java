import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Jogador {
    private String nome;
    private String dataHora;
    private int substancias;

    // Construtor para criar o jogador com nome, data e substância
    public Jogador(String nome, String dataHora, int substancias) {
        this.nome = nome;
        this.dataHora = dataHora;
        this.substancias = substancias;
    }

    // Getters para acessar os dados do jogador
    public String getNome() {
        return nome;
    }

    public String getDataHora() {
        return dataHora;
    }

    public int getSubstancias() {
        return substancias;
    }

    // Salva o jogador no arquivo especificado
    public static void saveToFile(Jogador jogador, String filePath) {
        // Garantir que o diretório existe antes de salvar
        File file = new File(filePath);
        file.getParentFile().mkdirs(); // Cria o diretório se não existir

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            // Corrigindo a formatação para garantir que não haja erro
            writer.write(String.format("%s;%s;%d\n", jogador.getNome(), jogador.getDataHora(), jogador.getSubstancias()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Carrega a lista de jogadores do arquivo
    public static List<Jogador> loadFromFile(String filePath) {
        List<Jogador> jogadores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 3) {
                    // Garantir que a conversão de substâncias seja feita corretamente
                    jogadores.add(new Jogador(dados[0], dados[1], Integer.parseInt(dados[2])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jogadores;
    }
}