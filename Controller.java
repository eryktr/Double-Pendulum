package com.pendulum;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

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

    private Circle FirstPendulum;

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
               //Poki co wszystko co dodalem do Controllera to testy czy animacja dziala
               Utility.calculateNewValues();
               Utility.TranslateCircle(FirstPendulum,Utility.positionX1,Utility.positionY1);
               try {wait(16);} catch (InterruptedException ex) {}
               //TODO
           }
       }
    }

    public void initialize()
    {
        Utility.setInitialValues();
        final double RADIUS = 25;
        FirstPendulum = new Circle (Utility.positionX1, Utility.positionY1, RADIUS);
        drawingPane.getChildren().add(FirstPendulum);
        //TODO
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
        //TODO
    }

    public void stopAnimation()
    {
        boolean newState = !stopBtn.isDisabled();
        stopBtn.setDisable(newState);
        startBtn.setDisable(!newState);
        keepDrawing = false;
        initialize();
        //TODO
    }

    public void clearPaths()
    {
        //TODO
    }

}
