    //Roteiro do Planeta Estudo, Missão do Rei Flap
    public class MissaoFlap extends Missao {

        @Override
        public String getMensagemIntroducao() {
            return "O Planeta estudo compõe de 2 continentes, o continente Estudo e o continente Xipa. O continente Xipa, por alguma razão desconhecida, ninguem evoluia lá, todos os habitantes continuavam na forma neanderthal dos tempos da pré-história, no qual ninguém era capaz de conseguir pensar, e tudo seguia o instinto de querer batalhar um com o outro ou acasalar. Porém, esse continente era uma fonte rica de banana. Por sua vez, o continente Estudo, capital, habitava 2 tipos de espécies: humanos e colossos. Um dos humanos era Flap. Flap nasceu com uma doença rara, que não podia apanhar luz solar nem luz artificial. Apenas luzes bem fracas eram permitidas. Quando a luz era ligada, Flap ficava enraivecido e começava a destruir tudo à sua volta. Porém, em seu estado normal, era estudador e estudava todo o tipo de espécie existente no planeta. Sua mãe era drogada viciada em crack e o abandonou em criança, então desde então seu pai Coronel teve que o sustentar. Seu estilo musical favorito é Eurobeat. Fez até amizades com diversos colossos durante seus estudos como: Traficante Malphite (atualmente reside no planeta Ritter), Doutor Estudo, Hydrus, Phalanx, entre outros. Doutor Estudo teve uma história perturbada, quando criança foi alvo de vários experimentos desumanos, e por conta disso ficou maluco da cabeça. Com roupas todas rasgadas, Doutor Estudo começou a fazer experimentos em outros pacientes, pois do estado que ele estava, ele pensava que seus experimentos era na verdade uma cura, o qual não era realmente. Doutor Estudo matou vários pacientes pensando que os estaria a ajudar. Uma vez, Flap tava fazendo experimentos no quarto de seu pai, mas seu pai não percebeu que ele estava lá, quando chegou seu pai acendeu a luz do seu quarto, no qual fez Flap ter um ataque cardíaco por causa do stress da luz. Flap então foi para o hospital, mas seu médico era Doutor Estudo. Flap acordou e viu que estava preso acorrentado a uma cama. Flap já tinha a noção do que iria acontecer, por no ramo dos Estudos, ele tinha uma noção de quem era Doutor Estudo. Flap com medo, dialogou com Doutor Estudo. Flap explicou que não precisava de operação, e que o que o Doutor fazia não era correto. Doutor ficou confuso, então Flap explicou que todos os experimentos faziam mal a todos os pacientes, mas que o iria ensinar a ser um Doutor de verdade caso o soltasse. Flap ofereceu bananas do Continente Xipa, para lhe mostrar o que havia a mais a Estudar se ele aceitasse a proposta. Doutor como era inocente demais, acreditou na proposta. Doutor iria começar a morar na casa de Flap, mas por conta de seu tamanho gigantesco, Coronel começou a fazer obras para fazer um laboratório gigante para Doutor. Depois de obras, Flap ensinou ele sobre os Estudos e ajudou ele a tirar Doutorado em Medicina e Estudo. Doutor então teve sua vida salva, largando suas roupas rasgadas começou a usar fato e gravata, e teve seu primeiro carro comprado. Depois da boa ação de Flap, o rei do Planeta Estudo ouviu falar sobre o caso, e ficou interessado em conhecer melhor Flap. Porém o rei já tinha 90 anos, e quase na idade da cova. Depois que Flap e o rei compartilharam seus conhecimentos um com o outro, o rei começou a gostar dele, pois via que Flap tinha a mesma paixão que ele sobre os Estudos. O rei sugeriu então que Flap o sucedesse, por beirava a morte, e que iria oferecer sua filha a Princesa Sofia em casamento. Flap aceitou, pois queria ter uma oportunidade melhor de Estudar todos os Colossos e todos os Xipos. Tudo corria bem, Flap se tornou rei, Doutor Estudo se tornou um médico renomado. Mas algo inesperado aconteceu, o vilão Portal, quem havia feito Doutor Estudo ficar maluco, percebeu que Doutor voltou ao normal. Flap havia arruinado seus planos. Mas não ficava por aqui, Portal então se quis vingar. Portal então sequestrou a princesa Sofia em troca de devolver o Doutor Estudo. Mas Flap não queria devolver, mas queria sua amada devolta. Flap então sem mais ideias, pensando no seu quarto escuro, chamou as forças Star Trek par ajudar a resgatar a princesa Sofia. Porém, Portal já havia pensado nessa possibilidade, e chamou as forças Star Trek para ligar a luz do quarto do Flap falando que tinha algo muito bom a oferecer. Por sua vez, Doutor Estudo na sua inocência, pesquisou sobre novos experimentos, e ouviu falar nas forças Star Trek. Foi então que pensou que Star Trek poderia ser uma ótima fonte de Estudo e chamou também as forças Star Trek. Tu então ficas na dúvida, será que salvas a princesa Sofia? Ou vais ver o que Portal tem a oferecer? Ou será que não te vais meter na confusão e apenas ser voluntariamente Estudado? A decisão é sua, mas lembre-se sempre, sua missão principal é coletar todas as substâncias ilícitas para Veggs.";
        }

        @Override
        public String[] getOpcoes() {
            return new String[]{
                    "Você oferece-se para ser estudado. (Custa 30 Energia, perde 70 de Saúde, mas ganha 80 de Moral)",
                    "O vilão Portal raptou a princesa Sofia. Sua missão é resgatá-la. (Custa 20 Energia, ganha 10 Moral)",
                    "Acender a luz do quarto do rei Flap (Custa 5 Bananas, mas ganha 20 Moral e chances de irritar o rei Flap)"
            };
        }

        @Override
        public String Executar(Nave nave, int escolha) {
            switch (escolha) {
                case 0:
                    JogoProgramacao jogo = new JogoProgramacao(nave.getPlayerName());
                    jogo.setUltimaMensagem("Você perdeu! O Estudo falhou, resultando em sérios danos cerebrais e transformando-o em um chimpanzé. Pelo menos, sua valentia foi reconhecida e você ganhou Moral.");
                    jogo.setMissaoAtual(null);
                    return jogo.getUltimaMensagem();
                case 1:
                    nave.UsarRecursos(20, 0);
                    nave.AumentarReputacao(10);
                    nave.AdicionarSubstancias();
                    return "Portal derrotado! O rei Flap recompensou você com o remédio Dosagem Máxima e prendeu o vilão Portal na cadeia, onde permanecerá eternamente.";
                default:
                    nave.UsarRecursos(0, 5);
                    nave.AumentarReputacao(20);
                    return "O rei Flap, completamente enfurecido, começou a destruir seu computador e ordenou ao Doutor Estudo que fosse atrás de você para Estudá-lo. Você correu o mais rápido possível e, por pouco, conseguiu escapar, já que o Doutor Estudo ficou distraído pegando as bananas que você deixou cair no caminho.";
            }
        }
    }
