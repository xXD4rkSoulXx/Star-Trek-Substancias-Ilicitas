    //Roteiro do Planeta Estudo, Missão do Rei Flap
    public class MissaoFlap extends Missao {

        @Override
        public String getMensagemIntroducao() {
            return "okokokokokokokokokokokokokokokokoko";
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