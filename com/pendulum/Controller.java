package com.pendulum;

import javafx.application.Platform;
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
    private final static double  CONST_PI = 3.141592653589793;
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
           Utility.sim.computePositions();
           while(keepDrawing)
           {
               drawPath = drawPathCheckBox.isSelected();
               //wygodniej mi bylo te funkcje zawrzec w utility, zawsze w razie czego mozna je skopiowac do contollera
               Utility.calculateNewValues();
               Utility.translateFirstPendulum();
               Utility.translateSecondPendulum();
               if (drawPath)
               {
                   Platform.runLater(() -> Utility.drawPendulumPath(drawingPane, colors[colorIndex], 2));

               }
               try {wait(16);} catch (InterruptedException ex) {}
               //TODO
           }
       }
    }

    public void initialize()
    {
        TextField[] textFields = {firstMass, secondMass, firstAngle, secondAngle, firstLength, secondLength};
        Utility.setDefaultValues(textFields);
        Utility.setInitialValues(textFields);
        Utility.initializeFigures(drawingPane);
        Double[] parameters = getTextFieldValues();
        Utility.sim = new Simulation(parameters);
        Simulation.state.set(Simulation.startState);
    }

    //Zaimplementowałem zmianę stanu Buttonów przy kliknięciu, powinno działać prawidłowo.
    public void startAnimation()
    {
        boolean newState = !startBtn.isDisabled();
        startBtn.setDisable(newState);
        stopBtn.setDisable(!newState);
        resetPendulumsPositions();
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
        colorIndex = (colorIndex+1) % 10;
        resetPendulumsPositions();
    }

    public void clearPaths()
    {
        drawingPane.getChildren().clear();
        Utility.initializeFigures(drawingPane);
        //TODO
    }

    private Double[] getTextFieldValues()
    {
        Double[] parameters = new Double[6];
        parameters[0] = Double.parseDouble(firstMass.getText());
        parameters[1] = Double.parseDouble(secondMass.getText());
        parameters[2] = Double.parseDouble(firstLength.getText());
        parameters[3] = Double.parseDouble(secondLength.getText());
        parameters[4] = Double.parseDouble(firstAngle.getText());
        parameters[5] = Double.parseDouble(secondAngle.getText());
        while(parameters[4] > 2 * CONST_PI) parameters[4] -= 2*CONST_PI;
        while(parameters[5] > 2 * CONST_PI) parameters[5] -= 2*CONST_PI;
        while(parameters[4] < 0 * CONST_PI) parameters[4] += 2*CONST_PI;
        while(parameters[5] < 0 * CONST_PI) parameters[5] += 2*CONST_PI;
        return parameters;
    }

    private void resetPendulumsPositions()
    {
        TextField[] textFields = {firstMass, secondMass, firstAngle, secondAngle, firstLength, secondLength};
        Utility.setInitialValues(textFields);
        Utility.addNewValuesToSimulation();
        Utility.translateFirstPendulum();
        Utility.translateSecondPendulum();
        Double[] parameters = getTextFieldValues();
        Utility.sim.resetSimulationValues(parameters);
        Simulation.state.set(Simulation.startState);
    }

}
