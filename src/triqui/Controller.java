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
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
    private Label titulo;

    @FXML
    private void jugar(ActionEvent event) {
        //Turno Jugador
        JFXButton btn = (JFXButton) event.getSource();
        Scene scene = btn.getScene();
        byte id = Byte.parseByte(btn.getId());
        logica.actualizar(id, logica.JUGADOR);
        btn.setText("X");
        btn.setDisable(true);

        byte resultado = this.logica.fin();
        if (resultado == logica.FINAL_JUGADOR) {
            fin("Ganaste", "#AFB42B","#F9FBE7");
        } else {
            if (this.logica.getMovimientos() > 0) {
                //Turno PC
                byte decision = this.logica.decidir();
                this.logica.actualizar(decision, this.logica.PC);
                btn = (JFXButton) scene.lookup("#" + decision);
                btn.setText("O");
                btn.setDisable(true);
                resultado = this.logica.fin();
                if (resultado == logica.FINAL_PC) {
                    fin("Perdiste", "#D32F2F","#FFEBEE");
                }
            } else {
                fin("Empate", "#FFA000","#FFF3E0");
            }
        }
    }

    @FXML
    private void reiniciar(ActionEvent event) {
        Scene scene = this.panel.getScene();
        this.panel.setStyle("-fx-background-color: transparent;-fx-border-color: transparent;");
        this.logica = new Logica();
        this.titulo.setText("Triqui");
        this.titulo.setStyle("-fx-text-fill: #455A64;");
        for (byte i = 0; i < 9; i++) {
            JFXButton btn = (JFXButton) scene.lookup("#" + i);
            btn.setDisable(false);
            btn.setText("");
            btn.setCursor(Cursor.HAND);
        }
    }

    public void fin(String mensaje, String color, String fondo) {
        Scene scene = this.panel.getScene();
        this.panel.setStyle("-fx-background-color: " + fondo + ";-fx-border-color: " + color + ";-fx-border-width:5px;");
        for (byte i = 0; i < 9; i++) {
            JFXButton btn = (JFXButton) scene.lookup("#" + i);
            btn.setDisable(true);
        }
        this.titulo.setText(mensaje);
        this.titulo.setStyle("-fx-text-fill: " + color + ";");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.logica = new Logica();
    }

}
