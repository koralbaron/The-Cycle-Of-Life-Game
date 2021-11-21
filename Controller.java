import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import java.util.Random;

/*
* The controller of this game
* */
public class Controller {
    private final int MATRIX_SIZE = 10;
    @FXML
    private Canvas canvas;
    @FXML
    private Button btn;// The btn to display next generation
    @FXML
    private Label genCounter;// label for generation counter
    @FXML
    void click(ActionEvent event) {
        proceedNextGeneration();
        drawMatrix();
        updateGenCounterLabel();
    }

    private GraphicsContext graphicsContext;
    private boolean[][] gameMatrix = new boolean[MATRIX_SIZE][MATRIX_SIZE];
    private double xCell;// x size of cell
    private double yCell;// y size of cell
    private boolean initGen = false;// if the first load of the game
    private int generationCounter = 0;
    Random rand = new Random();

    public void initialize(){
        this.xCell = canvas.getWidth() / MATRIX_SIZE;
        this.yCell = canvas.getHeight() / MATRIX_SIZE;
        graphicsContext = canvas.getGraphicsContext2D();
        if(!initGen) {// if the first load of the game
            initMatrix();// creates random matrix
            drawMatrix();
            initGen = true;// not first run anymore
        }
    }

    /*
    * Function to draw rectangle by given x,y and Color
    * */
    private void drawRect(double x, double y, Color color){
        graphicsContext.setFill(color);
        graphicsContext.fillRect(x,y,this.xCell,this.yCell);
    }

    /*
    * update GenCounter Label
    * */
    private void updateGenCounterLabel(){
        genCounter.setText(Integer.toString(generationCounter));
    }
    /*
    * First run - generate a random matrix to start with
    * */
    private void initMatrix(){
        for(int r = 0; r < MATRIX_SIZE; r++){
            for (int c = 0; c < MATRIX_SIZE; c++){
                gameMatrix[r][c] = rand.nextBoolean();
            }
        }
    }
    /*
    * Draw the new matrix on the screen
    * */
    private void drawMatrix(){
        graphicsContext.clearRect(0,0,canvas.getWidth(), canvas.getHeight());// clear canvas
        double x = 0;
        double y = 0;
        for (int r = 0; r < MATRIX_SIZE; r++){
            for (int c = 0; c < MATRIX_SIZE; c++){
                if(gameMatrix[r][c] == true)
                    this.drawRect(x,y, Color.GRAY);
                else
                    this.drawRect(x,y,Color.WHITE);
                x = x + this.xCell;
            }
            x = 0;
            y = y + this.yCell;
        }
    }

    /*
    * Update the Matrix for the next generation
    * */
    private void proceedNextGeneration(){
        boolean[][] newMatrix = new boolean[MATRIX_SIZE][MATRIX_SIZE];
        for (int r = 0; r < MATRIX_SIZE; r++){
            for (int c = 0; c < MATRIX_SIZE; c++){
                int counter = numberOfAliveNeighbors(r, c);
                if(counter == 0 || counter == 1 || counter >= 4)// death
                    newMatrix[r][c] = false;
                else if(counter == 3)//birth
                    newMatrix[r][c] = true;
                else
                    newMatrix[r][c] = gameMatrix[r][c];
            }
        }
        generationCounter++;
        this.gameMatrix = newMatrix;
    }
    /*
    * Function that counts and returns the number Of Alive Neighbors for given x,y coordinate.
    * */
    private int numberOfAliveNeighbors(int x, int y){
        int counter = 0;// counts neighbors
        if(x > 0 && x< MATRIX_SIZE-1 && y > 0 && y< MATRIX_SIZE-1){// not a boundary
            counter += (gameMatrix[x-1][y]? 1 : 0) + (gameMatrix[x+1][y]? 1: 0) + (gameMatrix[x][y-1]? 1:0) + (gameMatrix[x][y+1]? 1:0)
                    + (gameMatrix[x-1][y-1]? 1:0) + (gameMatrix[x-1][y+1]? 1:0) + (gameMatrix[x+1][y-1]? 1:0) + (gameMatrix[x+1][y+1]? 1:0);
        }
        if(x == 0){//upper boundary + edges
            if (y == 0){
                counter += (gameMatrix[x+1][y]? 1: 0) + (gameMatrix[x][y+1]? 1:0) + (gameMatrix[x+1][y+1]? 1:0);
            }else if (y == MATRIX_SIZE-1){
                counter += (gameMatrix[x+1][y]? 1 : 0) + (gameMatrix[x][y-1]? 1:0) + (gameMatrix[x+1][y-1]? 1:0);
            }else
                counter += (gameMatrix[x+1][y]? 1 : 0) + (gameMatrix[x][y-1]? 1: 0) + (gameMatrix[x][y+1]? 1:0)
                        + (gameMatrix[x+1][y+1]? 1:0) + (gameMatrix[x+1][y-1]? 1:0);
        }else if (x == MATRIX_SIZE-1){//bottom boundary + edges
            if(y == 0){
                counter += (gameMatrix[x-1][y]? 1 : 0) + (gameMatrix[x][y+1]? 1 : 0) + (gameMatrix[x-1][y+1]? 1 : 0);
            }else if(y == MATRIX_SIZE-1)
                counter += (gameMatrix[x-1][y]? 1 : 0) + (gameMatrix[x][y-1]? 1 : 0) + (gameMatrix[x-1][y-1]? 1 : 0);
            else
                counter += (gameMatrix[x-1][y]? 1 : 0) + (gameMatrix[x][y-1]? 1 : 0) + (gameMatrix[x][y+1]? 1 : 0)
                        + (gameMatrix[x-1][y-1]? 1 : 0) + (gameMatrix[x-1][y+1]? 1 : 0);
        }else if(y == 0){// left boundary
            counter += (gameMatrix[x-1][y]? 1 : 0) + (gameMatrix[x+1][y]? 1 : 0) + (gameMatrix[x][y+1]? 1 : 0)
                    + (gameMatrix[x-1][y+1]? 1 : 0) + (gameMatrix[x+1][y+1]? 1 : 0);
        }else if(y == MATRIX_SIZE-1)// right boundary
            counter += (gameMatrix[x-1][y]? 1 : 0) + (gameMatrix[x+1][y]? 1 : 0) + (gameMatrix[x][y-1]? 1 : 0)
                    + (gameMatrix[x-1][y-1]? 1 : 0) + (gameMatrix[x+1][y-1]? 1 : 0);
        return counter;
    }

}

