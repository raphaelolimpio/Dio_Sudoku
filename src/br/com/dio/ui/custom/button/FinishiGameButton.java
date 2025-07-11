package br.com.dio.ui.custom.button;

import java.awt.event.ActionListener;
import javax.swing.JButton;

public class FinishiGameButton extends JButton {
    public FinishiGameButton(final ActionListener actionListener) {
        this.setText("Concluir jogo");
        this.addActionListener(actionListener);
    }
}