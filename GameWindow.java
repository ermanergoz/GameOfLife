import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameWindow extends JFrame {
    private JFrame frame;
    private GamePanel panel;
    private JPanel statPanel;
    private JButton startButton;
    private JButton gridButton;
    private static JLabel statLabel;
    private boolean isGridOn;
    private boolean isStarted;
    private GameEngine gameEngine;

    private WindowAdapter exit = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    };

    public GameWindow() {
        this.frame = new JFrame("Life");
        this.panel = new GamePanel();
        statLabel = new JLabel();
        statPanel = new JPanel();
        statPanel.setLayout(null);
        frame.addWindowListener(exit);
        isStarted = false;
        isGridOn = true;
        frame.setSize(1366, 768);
        frame.setResizable(true);
        frame.setLayout(null);
        startButton = new JButton(" S t a r t ");
        gridButton = new JButton("Turn Off Grid");
        startButton.setBounds(0, 0, 105, 15);
        gridButton.setBounds(106, 0, 105, 15);
        startButton.setBackground(Color.GRAY);
        gridButton.setBackground(Color.GRAY);
        startButton.setFont(new Font("Arial", Font.BOLD, 10));
        gridButton.setFont(new Font("Arial", Font.BOLD, 10));
        statPanel.add(startButton);
        statPanel.add(gridButton);
        statPanel.setBounds(0, 0, 1350, 15);
        panel.setBounds(0, 15, 1350, 715);
        statLabel.setBounds(220, 0, 200, 15);
        statLabel.setForeground(Color.WHITE);
        statPanel.setBackground(Color.BLACK);
        frame.add(panel);
        frame.add(statPanel);
        statPanel.add(statLabel);
        frame.setVisible(true);

        gridButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isGridOn) {
                    panel.removeGrid();
                    isGridOn = false;
                    gridButton.setText("Turn On Grid");
                } else {
                    panel.addGrid();
                    isGridOn = true;
                    gridButton.setText("Turn Off Grid");

                }
            }
        });

        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!isStarted)
                {
                    isStarted=true;
                    gameEngine=new GameEngine(panel);
                    panel.changeMouseEventState();
                    gameEngine.changeGameState();
                    startButton.setText(" S t o p ");
                }
                else
                {
                    isStarted=false;
                    panel.changeMouseEventState();
                    gameEngine.changeGameState();
                    startButton.setText(" S t a r t ");
                }
            }
        });
    }

    public static void updateLabel(int gen) {
        statLabel.setText("Generation: " + gen);
    }
}