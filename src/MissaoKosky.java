//Roteiro do Planeta Ansiedade, Missão do Rei Kosky
public class MissaoKosky extends Missao {
    // Uso de Polimorfismo, vindo da classe abstrata Missão
    // Os métodos mais abaixo com @override também são polimorfismo,
    // mas não preciso de repetir de novo os comentários
    @Override
    public String getMensagemIntroducao() {
        return """
                Kosky nasceu no planeta Ansiedade na cidade Bancada Lurker. Kosky nasceu com uma ansiedade \
                super elevada por conta da sua genética em relação aos reis fundadores deste planeta. \
                Kosky vive com sua amada cadela chamada Princesinha, no qual ela o acompanha desde a \
                infância. Porém, infelizmente a Princesinha foi diagnosticada como faze terminal, o qual \
                desanimou demasiado o Kosky. Kosky então para aliviar começou com o uso de drogas relaxantes \
                como Opioide mas às vezes nem isso fazia efeito e ele continuava a ter ataques de ansiedade na \
                mesma. No entanto, a vida do Kosky não era só má. Kosky seguiu faculdade carreira de jornalismo \
                e consegui completar com sucesso. Sempre antes de cada entrevista ele prepara um roteiro com as \
                perguntas boas a questionar, embora algumas perguntas ele acabe por não fazer por conta da \
                ansiedade. Kosky já fez várias entrevistas, entrevistou o Paz, o Flap, o Josué, tudo depois do \
                trono deles mas antes da tragédia. Kosky pensou em entrevistar Veggs, mas sempre era impossível \
                por Veggs nunca estava sóbrio das substâncias. Kosky gostava de muita coisa. Além de jornalismo, \
                ele gostava de música e de sair em festas. Ele sempre entrava em uma festa, mas depois de alguns \
                minutos saia logo a achar que já fez algo de errado. Kosky numa vez, em certa festa, acabou \
                conheceu uma mulher chamada Giovanna Carlinhos. Quando o Kosky saiu da festa nervoso a achar que \
                correu mal de novo, Giovanna foi atrás dele. Quando Kosky percebeu, estranhou porque uma dama \
                delicada estaria seguindo ele. Kosky então começou a pedir desculpa por algo que tenha feito, mas \
                Giovanna falou que precisava dele. O coração de Kosky se encheu de alegria a achar que já pode ser \
                útil em algo. Giovanna falou que apenas precisava de companhia e andar abraçado a ele. \
                Kosky estranhou mas aceitou. Kosky então ficou corado a achar que podesse ser o começo de um romance. \
                Porém ela explicou depois o motivo, que faz algum tempo que andam vários perseguidores atrás dela. \
                Kosky então desanima ao perceber que na verdade não era nenhum romance, mas aceita na mesma por ser um \
                cavalheiro protetor e fala para Giovanna que está segura com o Little K (nome que ele inventou no momento \
                apenas para impressionar). Giovanna dá uma risada e ambos prosseguem o seu caminho. Giovanna entra na \
                casa de Kosky, que por sinal está arrumada, e os perseguidores anotam a morada para mais tarde fazerem \
                alguma maldade. Giovanna e Kosky se conhecem melhor ao conversar, e Kosky conta para ela o sonho de ser \
                cantor mas que tem medo que as pessoas achem ridículo. Porém, Giovanna fala que não faz sentido ele ser \
                pessimista enquanto não tentar, porque é os erros e falhas que fazem a evolução, e ele nunca será bom \
                enquanto não tentar. Palavras sábias de Giovanna fez Kosky voltar com seu sorriso apaixonado e motivado \
                para começar a cantar. Kosky então cantou uma letra que ele já havia preparado anteriormente mas abandonou. \
                A música chamava-se Desvalorizado e fala sobre alguém que tem muito potencial na vida mas que sempre achavam \
                que ele não valia nada no qual faziam ele desistir. Ouvindo a letra, Giovanna se comoveu e começou a se \
                encher de lágrimas. Mas Kosky começou com ansiedade a achar que fez algo de errado ou que cantou mal e \
                começou a pedir desculpas. Porém, Giovanna afirma que está a chorar por se ter comovido e que a letra é \
                maravilhosa. Kosky então fica motivado e começa a escrever mais músicas. Kosky confessa também que seu \
                cantor favorito é o Post Malone, e que um dia queria ser como ele, mas que parece um sonho impossível. \
                Mas agora que a primeira música correu bem, seu objetivo é criar várias músicas para um dia desafiar seu \
                ídolo Malone. Kosky então escreveu várias músicas com os títulos: Foice no carro, Morte, Controlar Insetos \
                e Tempo. Kosky então com 5 músicas, pensou que já teria o treinamento possível para desafiar Post Malone. \
                O problema, é que quando ele mais pensava no embate, mais ele ficava ansioso. Kosky então teve a ideia de \
                ligar para as forças Star Trek e mandar três pedidos: Ajudar ele a enfrentar Post Malone, Proteger a Giovanna \
                Carlinho dos perseguidores já que ele estaria ausente, e alimentar a Princesinha já que precisava de todos \
                os cuidados por estar perto de sua fase final. Mas Kosky se esqueceu isso seria um custo muito caro, e \
                Star Trek só poderia fazer um dos pedidos. Kosky então entra em ataque de ansiedade e fala que fica a nosso \
                critério a escolha. E tu? Qual vais escolher? Lembra-se da tua missão de coletar substâncias.""";
    }

    @Override
    public String[] getOpcoes() {
        return new String[]{
                "Enfrentar o Post Malone numa batalha de música (Custa Energia, chance de aumentar Moral)",
                "Proteger a princesa Giovanna de possíveis perseguidores (Custa Energia e Bananas)",
                "Alimentar a cadela do rei Kosky (Custa Rações)"
        };
    }

    @Override
    public String Executar(Nave nave, int escolha) {
        switch (escolha) {
            case 0:
                nave.UsarRecursos(20, 0);
                nave.AdicionarSubstancias();
                return """
                        Infelizmente, Post Malone superou sua performance musical com facilidade. O rei Kosky, ansioso, \
                        recorreu ao Opioide para lidar com a frustração e te ofereceu um também.""";
            case 1:
                // Esta opção não tira recursos pelo motivo de ser uma opção de GameOver
                // -----------------------
                JogoProgramacao jogo = new JogoProgramacao(nave.getNomeJogador());
                jogo.setUltimaMensagem("""
                        Você perdeu! O rei Kosky, tomado pela ansiedade, confundiu você com um dos \
                        perseguidores da princesa Giovanna e, em um acesso de paranoia, mandou prendê-lo na prisão \
                        Bancada Lurker.""");
                jogo.setMissaoAtual(null);
                return jogo.getUltimaMensagem();
                // -----------------------
            default:
                nave.UsarRecursos(0, 5);
                nave.PerderVida(20);
                return """
                        O rei Kosky, tomado pela ansiedade, acreditou que você havia dado chocolate à sua cadela \
                        por causa da cor, e agrediu-o sem pensar. Porém, ao perceber que era apenas ração, ele se \
                        desculpou e deixou você escapar.""";
        }
    }

}
