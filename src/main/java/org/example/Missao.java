package org.example;

// Classe Abstrata de Missão, essa classe vai servir depois para fazer a missão de cada planeta
// ---------------------------------
public abstract class Missao {
    // Essa vai ser o metodo sobre a mensagem introdutória de cada planeta
    public abstract String getMensagemIntroducao();
    // Essa vai ser o metodo responsável pelas opções a serem escolhidas por casa missão
    public abstract String[] getOpcoes();
    // Essa vai ser o metodo responsável por aplicar o que o jogador escolheu
    public abstract String Executar(Nave nave, int escolha);
}
// ---------------------------------