//Roteiro do Planeta Ritter, Missão do Rei Josué
// ---------------------------------
// Uso de herança
public class MissaoJosue extends Missao {
    // Uso de Polimorfismo, vindo da classe abstrata Missão
    // Os métodos mais abaixo com @override também são polimorfismo,
    // mas não preciso de repetir de novo os comentários
    @Override
    public String getMensagemIntroducao() {
        return """
                Josué, diferente de Paz, nasceu herdeiro, com várias terras. Sua mãe era dona da famosa fábrica de \
                chocolate Ritter e dona do planeta Ritter já que sua fábrica era o que gerava maior lucro para o \
                planeta inteiro. Já seu pai era historiador, e sempre saia para buscar evidências históricas do planeta \
                Ritter. Então Josué não dava valor às pequenas coisas já que era rico e mimado. Josué era gordo, 110Kg e \
                sempre implorava a sua mãe por comida. Big Mac, Cheetos, Pizza, todo o tipo de fast food ele devorava e \
                achava pouco. E fazia birra quando sua mãe pedia para ele comer algo saudável como sopa ou um prato de \
                peixe. Josué não queria encarar a verdade, e sempre falava para seus amigos na internet que os 110Kg dele \
                era de massa magra e que era musculado e alto de 2,10m, o que na verdade era mentira. Seu estilo musical \
                favorito é HardStyle. Por mais que fosse gordo e ingrato, Josué nasceu com um dom bizarro de inteligência, \
                ele faltava às aulas para jogar um joguinho de fadas online, mas sempre na véspera estudava a matéria e \
                passava nos exames, depois pagava nos professores para anularem as faltas dele, já que era herdeiro. \
                Josué, com a genética de seu pai, amava história também, então ele ficava explorando na internet sob \
                evidências históricas. O pai sabendo isso, ofereceu o primeiro computador de todos, o famoso ENIAC, \
                criado em 1946. Josué se fascinava com isso, porém como é uma relíquia bem antiga, poderia ser vendido \
                no mercado negro por um preço bem elevado. Lá no mercado negro existe vários traficantes, o mais famoso \
                é o Traficante Malphite, um Colossal de Rochas vindo do planeta Estudo, que posteriormente será apresentado \
                o planeta de origem dele. Ele pagaria bem caro para ter um ENIAC na sua posse, pois ele conseguiria extrair \
                os minerais de lá, e consequentemente criar novas invenções com isso. Certo dia, sua mãe teve que ficar mais \
                tempo na fábrica e Josué ficou sozinho, pois seu pai viajou para trabalho. Josué no meio do seu joguinho, \
                ouviu gritos na vizinhança, porém ficava com preguiça ele não se levantou. Trinta minutos se passaram e os \
                gritos continuavam. Josué se levantou então porque estava cansado dos gritos e foi ver o que se passava. \
                Quando se deparou, seu vizinho Beraldo estava agredindo sua irmã de 5 anos para descansar sua raiva. \
                Beraldo notou que havia alguém espreitando e apontou uma arma para Josué. Beraldo então ameaçou Josué que \
                se contasse o que visse, que o mataria na hora. Josué com medo foi embora. Porém Beraldo era mais astuto. \
                No dia seguinte, Beraldo foi na casa dele e viu que a mãe de Josué estava. Beraldo forjou uma história para \
                a mãe dele, que Josué agrediu sua irmã de 5 anos. Josué se defendeu falando que era mentira. Beraldo então \
                ameaçou de processo judicial e foi embora. Josué com a raiva, queria afogar suas mágoas e encomendou comida \
                na Star Trek. Sua mãe, desesperada com que seu filho fosse preso e sua imagem fosse sujada, pediu ajuda na \
                força Star Trek para eliminar Beraldo e evitasse que ele o processasse judicialmente, e adicionou o detalhe \
                de não dar comida no Josué como castigo. Entretanto, Malphite como era dono do Submundo, estava a par de todo \
                o acontecimento, e viu que seria a oportunidade perfeita de roubar o ENIAC para ter na sua posse, já que Josué \
                e sua mãe estariam ocupados com o processo. Mas Malphite não queria sujar suas mãos e queria permanecer nas \
                sombras, então Malphite chamou as forças Star Trek e falou que em troca do ENIAC, poderia dar algo muito \
                interessante, já que era experiente nas inovações. E tu, o que vais escolher? Ajudar o traficante Malphite? \
                Ou salvar Josué de sua injustiça e eliminar o Beraldo? Ou preferes não te envolver no caso e apenas dar \
                comida no Josué já que é o pedido original? A escolha está nas suas mãos, pense bem na decisão mais correta, \
                e lembre-se sempre, a prioridade é coletar as substâncias ilícitas para o Veggs.""";
    }

    @Override
    public String[] getOpcoes() {
        return new String[]{
                "Roubar um ENIAC, relíquia de 1946, e vender no mercado negro (Custa Energia, ganha Moral)",
                "Enfrentar o vilão Beraldo para para salvar o rei Josué de um processo judicial (Custa Energia, chance de aumentar Moral)",
                "Entregar um Big Mac e uma Barrinha de Chocolate Ritter(Custa Energia e Suprimentos)",
        };
    }

    @Override
    public String Executar(Nave nave, int escolha) {
        switch (escolha) {
            case 0:
                nave.UsarRecursos(30, 0);
                nave.AumentarReputacao(30);
                nave.AdicionarSubstancias();
                return "ENIAC vendido com sucesso! Como pagamento, você recebeu o mineral Germânico do Traficante Malphite.";
            case 1:
                nave.UsarRecursos(30, 0);
                return "Derrotou o Beraldo! Agora o rei Josué já não vai ser processado.";
            default:
                nave.UsarRecursos(20, 30);
                return """
                        Após devorar seu Big Mac e Barrinha Ritter, o rei Josué sofreu um infarto devido à obesidade e foi \
                        imediatamente levado ao hospital. Você fugiu rapidamente do planeta, antes que alguém suspeitasse de \
                        sua culpa.""";
        }
    }
}
