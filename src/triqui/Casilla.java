/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package triqui;

import java.awt.Point;

/**
 *
 * @author Administrador
 */
public class Casilla {

    private char jugador;
    private Point coordenadas;

    public Casilla(byte x, byte y) {
        coordenadas = new Point(x, y);
    }

    public Point getCoordenadas() {
        return coordenadas;
    }

    public char getJugador() {
        return jugador;
    }

    public void setJugador(char jugador) {
        this.jugador = jugador;
    }

}
