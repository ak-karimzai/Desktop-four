package four;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class ConnectFour extends JFrame {
    Board board;
    private BorderLayout mainGuiLayout;
    private JPanel pane1;
    private JPanel pane2;
    public ConnectFour() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLayout(null);
        setTitle("Connect Four");

        pane1 = new JPanel();
        pane1.setLayout(new GridLayout(6, 7));
        board = new Board(pane1);

        pane2 = new JPanel();
        pane2.setLayout(new FlowLayout());

        JButton resetButton = new JButton("Reset");
        resetButton.setName("ButtonReset");
        resetButton.addActionListener(e -> {
            board.resetBoard();
        });

        pane2.add(resetButton);

        add(pane1);
        add(pane2);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setVisible(true);
    }
}