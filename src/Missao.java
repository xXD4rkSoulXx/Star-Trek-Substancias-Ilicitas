// Classe Abstrada de Missão, essa classe vai servir depois para fazer a missão de cada planeta, uso de polimorfismo
public abstract class Missao {
    public abstract String getMensagemIntroducao(); //Essa vai ser o metodo sobre a mensagem introdutória de cada planeta
    public abstract String[] getOpcoes(); //Essa vai ser o metodo responsavel pelas opções a serem escolhidas por casa missão
    public abstract String Executar(Nave nave, int escolha); //Essa vai ser o metodo responsável por aplicar o que o jogador escolheu
}