package com.pendulum;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;


public class Controller
{
    //Referencja do Pane'a, po którym będziemy rysować (Tego z czarnym borderem)
    @FXML
    private Pane drawingPane;
    @FXML
    private Button startBtn;
    @FXML
    private Button stopBtn;
    @FXML
    private CheckBox drawPathCheckBox;
    //Tutaj dostep do TextFieldow:
    @FXML
    private TextField firstMass, secondMass, firstAngle, secondAngle, firstLength, secondLength;

    //Poki co zrobilem to tak, ale zdecydowanie trzeba to zastapic czyms lepszym
    private Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.YELLOW, Color.BROWN, Color.CORAL,
        Color.AQUAMARINE, Color.INDIGO, Color.LIMEGREEN};
    private int colorIndex = 0;
    //TODO

    //Z wiadomych powodów nie mogłem użyć "continue" jako nazwy zmiennej.
    private boolean keepDrawing, drawPath;

    //Klasa implementująca wątek, który będzie służył nam za Timer
    private class Timer extends Thread
    {
       public synchronized void run()
       {
           while(keepDrawing)
           {
               drawPath = drawPathCheckBox.isSelected();
               //wygodniej mi bylo te funkcje zawrzec w utility, zawsze w razie czego mozna je skopiowac do contollera
               Utility.calculateNewValues();
               Utility.TranslateFirstPendulum();
               Utility.TranslateSecondPendulum();
               if (drawPath)
               {
                   Utility.drawPendulumPath(drawingPane, colors[colorIndex], 1);
                   Utility.drawPendulumPath(drawingPane, colors[colorIndex+1], 2);
               }
               try {wait(16);} catch (InterruptedException ex) {}
               //TODO
           }
       }
    }

    public void initialize()
    {
        Utility.setInitialValues();
        Utility.initializeFigures(drawingPane);
    }

    //Zaimplementowałem zmianę stanu Buttonów przy kliknięciu, powinno działać prawidłowo.
    public void startAnimation()
    {
        boolean newState = !startBtn.isDisabled();
        startBtn.setDisable(newState);
        stopBtn.setDisable(!newState);
        Timer timer = new Timer();
        timer.start();
        keepDrawing = true;
    }

    public void stopAnimation()
    {
        boolean newState = !stopBtn.isDisabled();
        stopBtn.setDisable(newState);
        startBtn.setDisable(!newState);
        keepDrawing = false;
        //To tez bedzie do zmiany jak ustalimy lepszy sposob zmiany koloru
        if (colorIndex<8)
        {
            colorIndex+=2;
        }
        else
        {
            colorIndex=0;
        }
        //TODO
    }

    public void clearPaths()
    {
        //TODO
    }

}
