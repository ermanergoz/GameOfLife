import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class GameEngine {

    GamePanel gamePanel;
    boolean cells[][];
    private int aliveNeighbours[][];
    private int generation;
    private boolean gameState;
    private int row;
    private int column;
    private Clip clip;
    private AudioInputStream audioInputStream;
    private File soundFile;
    InputStream path;

    public GameEngine(GamePanel gamePanel) {
        row = gamePanel.getRowNumber();
        column = gamePanel.getColumnNumber();
        this.aliveNeighbours = new int[row][column];
        this.cells = new boolean[row][column];
        this.gamePanel = gamePanel;
        cells = gamePanel.getCells();
        generation = 0;
        gameState = false;

        try {
            soundFile = new File("src/MoonlightSonata.wav");
            audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
        } catch (Exception err) {
            err.printStackTrace();
        }

    }

    public void changeGameState() {
        if (gameState) {
            gameState = false;
            stopSound();

        } else {
            gameState = true;
            playSound();
            run();
        }
    }

    public void run() {
        Thread thread = new Thread("Runs the game") {
            public void run() {
                while (gameState) {
                    GameWindow.updateLabel(++generation);
                    checkNeighbors();
                    applyRules();
                    gamePanel.setCells(cells);
                    try {
                        sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }

    public void checkNeighbors() {
        int aliveCellNum = 0;

        for (int r = 0; r < row; ++r) {
            for (int c = 0; c < column; ++c) {

                if ((r - 1 > -1 && c - 1 > -1) && cells[r - 1][c - 1]) {
                    aliveCellNum++;
                }
                if ((r - 1 > -1) && cells[r - 1][c]) {
                    aliveCellNum++;
                }
                if ((r - 1 > -1 && c + 1 < column) && cells[r - 1][c + 1]) {
                    aliveCellNum++;
                }
                if ((c - 1 > -1) && cells[r][c - 1]) {
                    aliveCellNum++;
                }
                if ((c + 1 < column) && cells[r][c + 1]) {
                    aliveCellNum++;
                }
                if ((r + 1 < row && c - 1 > -1) && cells[r + 1][c - 1]) {
                    aliveCellNum++;
                }
                if ((r + 1 < row) && cells[r + 1][c]) {
                    aliveCellNum++;
                }
                if ((r + 1 < row && c + 1 < column) && cells[r + 1][c + 1]) {
                    aliveCellNum++;
                }
                aliveNeighbours[r][c] = aliveCellNum;
                aliveCellNum = 0;
            }
        }
    }

    private void applyRules() {
        for (int r = 0; r < row; ++r) {
            for (int c = 0; c < column; ++c) {

                if (cells[r][c] && aliveNeighbours[r][c] < 2) //underpopulation
                    cells[r][c] = false;

                if (cells[r][c] && aliveNeighbours[r][c] > 3) //overpopulation
                    cells[r][c] = false;

                if (!cells[r][c] && aliveNeighbours[r][c] == 3) //reproduction
                    cells[r][c] = true;

            }
        }
    }

    private void playSound() {
        try {
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    private void stopSound() {
        clip.close();
        clip.stop();
    }
 
}


