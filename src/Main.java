import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Vai executar o construtor da classe JogoTelaInicial
        // para abrir a Interfrace Gráfica de forma segura
        SwingUtilities.invokeLater(JogoTelaInicial::new);
    }
}
