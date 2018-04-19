package com.pendulum;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Pane;

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
               //TODO
           }
       }
    }
    public void initialize()
    {
        Timer timer = new Timer();
        //TODO
    }

    //Zaimplementowałem zmianę stanu Buttonów przy kliknięciu, powinno działać prawidłowo.
    public void startAnimation()
    {
        boolean newState = !startBtn.isDisabled();
        startBtn.setDisable(newState);
        stopBtn.setDisable(!newState);
        keepDrawing = true;
        //TODO
    }

    public void stopAnimation()
    {
        boolean newState = !stopBtn.isDisabled();
        stopBtn.setDisable(newState);
        startBtn.setDisable(!newState);
        keepDrawing = false;
        //TODO
    }

    public void clearPaths()
    {
        //TODO
    }

}
