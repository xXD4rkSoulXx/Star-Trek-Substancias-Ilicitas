package main.java.org.example;

//Roteiro do Planeta Veggs, Missão Final do Rei Veggs, entregar todas as Substâncias Proibidas
// ---------------------------------
// Uso de herança
public class Final extends Missao {
    // Uso de Polimorfismo, vindo da classe abstrata Missão
    // Os métodos mais abaixo com @override também são polimorfismo,
    // mas não preciso de repetir de novo os comentários
    @Override
    public String getMensagemIntroducao() {
        // Este é o final quando não coleto todas as 4 substâncias, o que equivale ao GameOver
        return """
                Ao chegar ao planeta Veggs, vê-se Veggs todo impaciente à espera das drogas falando que horas são. \
                Ao te ver, Veggs começa a ficar feliz com a esperança de ter as drogas. O problema é que não possuias \
                as 4 substâncias todas. Veggs ao perceber da sua falta, pois 4 era fundamental, começou a enlouquecer. \
                O grito de Veggs começa a ecoar pelo Planeta inteiro falando “Come feijão” o que no planeta dele \
                significava “Estás morto”. Veggs então antes de tudo fala que horas são e começa  e começa a mandar \
                guardas atrás de você. Porém, você não consegue fugir, pois o são de pó já estava em domínio pelo Veggs, \
                Veggs conseguia cheirar o chão que nem um aspirador, começando a te atrair. Ao chegar lá, Veggs agarra-te \
                e começa a te bater, bater e bater sem parar. Veggs começa falar que se tu deres 100 euros ele para de te \
                bater, porém tu não tens a quantia de momento. Veggs não aceita a espera e continua a te bater. Depois de \
                você todo ensanguentado. Veggs te tranca numa prisão e começa a mandar ser Estudado. Você não tem mais \
                escapatória, e perde o jogo. Este foi o jogo Star Trek, depois de tantas aventuras, suas escolhas pelos \
                vistos não foram as mais corretas, porque embora fazendo o certo e o bem para a humanidade, nem sempre \
                essas escolhas geram um bom retorno. Jesus fazendo o bom foi morto no final, e todos os heróis podem \
                servir de sacrifício, pois nada é perfeito. E com isso o jogo finaliza.""";
    }

    // Tive que polimorfar estes métodos mesmo sem usar porque métodos abstratos são obrigatórios serem usados
    // ----------------------------
    @Override
    public String[] getOpcoes() {
        return new String[]{};
    }

    @Override
    public String executar(Nave nave, int escolha) {
        return "";
    }
    // ----------------------------
}