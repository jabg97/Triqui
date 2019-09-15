/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package triqui;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;

/**
 *
 * @author Administrador
 */
public class Controller implements Initializable {

    private Logica logica;

    @FXML
    private Pane panel;

    @FXML
    private void jugar(ActionEvent event) {
        //Turno Jugador
        JFXButton btn = (JFXButton) event.getSource();
        Scene scene = btn.getScene();
        byte id = Byte.parseByte(btn.getId());
        logica.update(id, 'X');
        btn.setText("X");
        btn.setDisable(true);

        byte resultado = logica.fin();
        if (resultado == 1) {
            new Alert(Alert.AlertType.INFORMATION, "Ganaste").showAndWait();
            reiniciar();
        } else {
            if (logica.getMovimientos() > 0) {
                //Turno PC
                byte desicion = logica.IA();
                btn = (JFXButton) scene.lookup("#" + desicion);
                btn.setText("O");
                btn.setDisable(true);
                System.out.println("Fin");
                resultado = logica.fin();
                if (resultado == -1) {
                    new Alert(Alert.AlertType.ERROR, "Perdiste").showAndWait();
                    reiniciar();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Empate").showAndWait();
                reiniciar();
            }
        }
    }

    public void reiniciar() {
        Scene scene = panel.getScene();
        this.logica = new Logica();
        for (byte i = 0; i < 9; i++) {
            JFXButton btn = (JFXButton) scene.lookup("#" + i);
            btn.setDisable(false);
            btn.setText("");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.logica = new Logica();
    }

}
