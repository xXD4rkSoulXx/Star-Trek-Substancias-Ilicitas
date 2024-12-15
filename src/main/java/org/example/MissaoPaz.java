package org.example;

//Roteiro do Planeta Solana, Missão do Rei Paz
public class MissaoPaz extends Missao {
    @Override
    public String getMensagemIntroducao() {
        return "Paz nasceu na pobreza e morava numa casa feita de barro. Filho de Roberto e de Flávia. Família de cor preta, porém Paz foi o único que nasceu laranja, mas ele nunca quis aceitar e sempre se achou preto igual os restantes familiares. Roberto para sustentar a família começou a vender gelo, e a Flávia começou a vender balão. Com 12 anos, Paz largou a escola e começou a dedicar sua vida aos treinos. Quanto mais ele treinava, mais dinheiro arranjava para ajudar os pais, porém não muito dinheiro. Roberto vendo seu filho começou, começou também a treinar sua arte do gelo. E Dona Flávia, por sua vez achou que Roberto não devesse treinar por já estarem idosos. Roberto não quis ouvir e continuou treinando. Entretanto, Paz foi atacado por uma criatura misteriosa que afetou seu o dente. Paz teria então que remover urgentemente o dente, porém, ele não tinha dinheiro para ir ao dentista. Seus pais mal tinham dinheiro para sustentar sua família, e os treinamentos davam pouco dinheiro. Paz continuava chorando e vagando por aí e conheceu um espantalho falante chamado Piu Piu falou que conhecia um método infalível de ganhar dinheiro chamado Criptomoeda. Paz não pensou duas vezes, e no desespero roubou todo o dinheiro acumulado de seus pais. Roberto ficou muito chateado pois trabalhava duro para o sustentar, porém Flávia, falou que ele poderia ter bons motivos. Paz então meteu todo o pouco dinheiro que conseguiu, 100€, meteu em Solana. Paz conseguiu lucrar 1 milhão de euros com Solana. Paz sem pensar duas vezes foi logo fazer sua operação. Paz ensinou sua mãe a investir em Solana, e conseguiu ganhar 1000 euros. No entanto, Roberto não havia perdoado Paz, pois as chances de ter dado errado e ter perdido o dinheiro todo eram muito altas. Paz então divulgou sua palavra e levou mais pessoas a investir, a quem os chamara de filhos. Os seus filhos eram Nali, Icaros e outros. Esses filhos usaram o dinheiro para coisas improdutivas. Sempre faziam encomendas sem autorização para a casa do Paz como Gelo, Balão, Pastilha Trindent, Chocolate Nescau Ball, Sal Grosso, o que irritava Paz. Até fizeram compras de outros planetas como encomendar Barrinha de Chocolate Ritter do planeta Ritter, algo que será explorado futuramente. Seus filhos estranhavam o facto de Paz ser o único ser laranja do planeta já que o resto era de cor preta ou branca. Paz se havia casado com Nicole. Paz com seu sucesso se tornou rei por convencer várias pessoas a investir em Solana e torná-las ricas, denominado seu planeta por Planeta Solana. Roberto com a raiva, perdeu o controle de seus poderes e se transformou num homem gelo. Roberto já não era mais o mesmo, ele foi completamente dominado pela raiva, se tornado uma nova pessoa. Roberto ganhou o apelido de Robert Iceman. Robert Iceman havia entrado no maior grupo de vilões existentes, Marvel. Robert começa o seu contra ataque e tenta congelar Paz. Porém, Nicole se sacrifica e ficou na frente e ela acabou por ser congelada e acabara de morrer. Paz com a riqueza da Solana ele havia parado de treinar e por sua vez enferrojado. Paz no desespero tenta fugir do planeta e leva seus filhos. Paz pede para seus filhos pedir ajuda na empresa Star Trek já que Paz não estava mais em forma. Seu filho Nali pediu para as forças Star Trek enfrentar o vilão Robert Iceman. E para você, acabaria por ser útil ajudar pois seria uma oportunidade de verificar se o Planeta Solana tem alguma substância ilícita. Enfrentar Robert poderia ser arriscado. Por sua vez, seu filho Icaros ligou em segredo, que já não seria preciso enfrentar Robert, mas sim trazer um saco de laranjas. Icaros preferiu fazer de novo outra encomenda só para fazer piada sobre o Paz ser laranja e não ligava para o perigo de ter um vilão à solta. E tu ficas na dúvida, será que é pra enfrentar o vilão Robert Iceman? Ou será mesmo para entregar um pacote de laranja? Poderá Icaros ter algo para entregar em troca? Ou seria para recrutar um novo investidor em Solana? O que é mais importante: Proteger o planeta, ficar rico em criptomoedas ou entregar um saco de laranjas em troca de algo? A escolha é sua, mas lembre-se sempre, cada escolha tem sua ação, e seu objetivo é coletar a substância ilícita.";
    }

    @Override
    public String[] getOpcoes() {
        return new String[]{
                "Enfrentar o vilão Robert Iceman (Custa 10 Energia, 5 Bananas, pode aumentar Moral e recebe o poder de voltar no tempo)",
                "Impedir que o rei Paz se vicie em Criptomoedas (Custa 5 Energia, risco de virar viciado também e perder de Saúde)",
                "Entregar um saco de laranja (Custa 5 Laranjas, chances de irritar o rei Paz)"
        };
    }

    @Override
    public String Executar(Nave nave, int escolha) {
        switch (escolha) {
            case 0:
                nave.UsarRecursos(10, 5);
                nave.AumentarReputacao(5);
                return "Robert Iceman foi derrotado! Recursos gastos, mas a Moral subiu.";
            case 1:
                nave.UsarRecursos(5, 0);
                nave.AumentarVida(-10);
                return "Golpe financeiro! A perda de 500 euros em Solana abalou sua Energia e prejudicou sua Saúde.";
            default:
                nave.UsarRecursos(0, 5);
                nave.AdicionarSubstancias();
                return "Como pagamento, o Rei Paz entregou-lhe um pacote de Sal Grosso, mas em seguida ouviu-se sua voz irritada: ‘Quem foi o filho idiota que encomendou laranjas sem minha permissão? Não aguento mais! Já falei que não sou laranja!'";
        }
    }
}