import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements MouseListener {
    private final int row = 119;
    private final int column = 225;
    private boolean cells[][];
    private int gridGap;
    private boolean disableMouseEvent;
    private int squareSize;

    public GamePanel() {
        this.cells = new boolean[row][column];   //true is alive, false is dead

        for (int rowI = 0; rowI < row; ++rowI) {
            for (int columnI = 0; columnI < column; ++columnI) {
                cells[rowI][columnI] = false;
            }
        }
        gridGap = 1;
        addMouseListener(this);
        this.setBackground(Color.darkGray);
        squareSize = 5;
        disableMouseEvent = false;
    }

    public int getRowNumber() {
        return row;
    }

    public int getColumnNumber() {
        return column;
    }

    public boolean[][] getCells() {
        return cells;
    }

    public void setCells(boolean c[][]) {
        cells = c;
        refreshGUI();
    }

    public int[] getCellAt(int x, int y) {
        int coords[] = new int[2];
        coords[0] = y / (squareSize + gridGap);  //row
        coords[1] = x / (squareSize + gridGap);  //column

        return coords;
    }

    public void removeGrid() {
        gridGap = 0;
        squareSize = 6;
        refreshGUI();
    }

    public void addGrid() {
        gridGap = 1;
        squareSize = 5;
        refreshGUI();
    }

    public void changeMouseEventState() {
        disableMouseEvent = !disableMouseEvent;
    }

    public void setAlive(int coords[]) {
        this.cells[coords[0]/*row*/][coords[1]/*column*/] = true;
    }

    public void setDead(int coords[]) {
        this.cells[coords[0]/*row*/][coords[1]/*column*/] = false;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int posX = 0;
        int posY = 0;

        for (int rowI = 0; rowI < row; ++rowI) {
            for (int columnI = 0; columnI < column; ++columnI) {

                if (cells[rowI][columnI])
                    g.setColor(Color.WHITE);
                else
                    g.setColor(Color.BLACK);

                g.fillRect(posX, posY, squareSize, squareSize);
                posX = posX + squareSize + gridGap;
            }
            posY = posY + squareSize + gridGap;
            posX = 0;
        }
    }

    public void refreshGUI() {
        this.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1 && !disableMouseEvent) {
            setAlive(getCellAt(e.getX(), e.getY()));
            refreshGUI();
        }

        if (e.getButton() == MouseEvent.BUTTON3 && !disableMouseEvent) {
            //Right Click
            setDead(getCellAt(e.getX(), e.getY()));
            refreshGUI();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}