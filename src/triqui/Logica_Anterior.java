/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package triqui;

/**
 *
 * @author Administrador
 */
public class Logica_Anterior {

    private char[] tablero;
    private byte movimientos;
    private final char jugador = 'X';
    private final char ia = 'O';

    public Logica_Anterior() {
        movimientos = 9;
        this.tablero = new char[9];
    }

    public void update(byte id, char texto) {
        this.tablero[id] = texto;
        this.movimientos--;
    }

    public byte IA() {
        byte desicion = -1;
        if (getMovimientos() == 8) {
            desicion = inicio();
        } else {
            int max = Integer.MIN_VALUE;
            System.out.println("======================");
            for (byte i = 0; i < this.tablero.length; i++) {
                if (this.tablero[i] == '\0') {
                    char[] copy = (char[]) this.tablero.clone();
                    copy[i] = ia;
                    int resultado = arbol(copy, this.movimientos, false);
                    System.out.println(i + "|" + resultado);
                    if (resultado > max) {
                        desicion = i;
                        max = resultado;
                    }
                }
            }
        }

        this.update(desicion, 'O');
        return desicion;
    }

    private byte inicio() {
        if (tablero[4] == '\0') {
            return 4;
        } else {
            return 0;
        }
    }

    private int arbol(char[] tablero, byte movimientos, boolean turno_IA) {
        //System.out.println(movimientos);
        if (movimientos == 1) {
            return resultado(tablero);
        } else {
            byte total = 0;
            for (byte i = 0; i < tablero.length; i++) {
                if (tablero[i] == '\0') {
                    char[] copia = (char[]) tablero.clone();
                    copia[i] = (turno_IA) ? this.ia : this.jugador;
                    total += arbol(copia, (byte) (movimientos - 1), !turno_IA);
                }
            }
            return total;
        }
    }

    private byte resultado(char[] tablero) {
        /* for (int i = 0; i < 9; i++) {
            System.out.print(tablero[i] + "|");
            if ((i + 1) % 3 == 0) {
                System.out.println("");
            }
        }
        System.out.println("************************");*/
        //HORIZONTAL  
        if (tablero[0] != '\0' && tablero[0] == tablero[1] && tablero[1] == tablero[2]) {
            return (byte) ((tablero[0] == this.jugador) ? 1 : -1);
        } else if (tablero[3] != '\0' && tablero[3] == tablero[4] && tablero[4] == tablero[5]) {
            return (byte) ((tablero[3] == this.jugador) ? 1 : -1);
        } else if (tablero[6] != '\0' && tablero[6] == tablero[7] && tablero[7] == tablero[8]) {
            return (byte) ((tablero[6] == this.jugador) ? 1 : -1);
        } //VERTICAL
        else if (tablero[0] != '\0' && tablero[0] == tablero[3] && tablero[3] == tablero[6]) {
            return (byte) ((tablero[0] == this.jugador) ? 1 : -1);
        } else if (tablero[1] != '\0' && tablero[1] == tablero[4] && tablero[4] == tablero[7]) {
            return (byte) ((tablero[1] == this.jugador) ? 1 : -1);
        } else if (tablero[2] != '\0' && tablero[2] == tablero[5] && tablero[5] == tablero[8]) {
            return (byte) ((tablero[2] == this.jugador) ? 1 : -1);
        } //DIAGONAL PRINCIPAL
        else if (tablero[0] != '\0' && tablero[0] == tablero[4] && tablero[4] == tablero[8]) {
            return (byte) ((tablero[0] == this.jugador) ? 1 : -1);
        } //DIAGONAL SECUNDARIA
        else if (tablero[2] != '\0' && tablero[2] == tablero[4] && tablero[4] == tablero[6]) {
            return (byte) ((tablero[2] == this.jugador) ? 1 : -1);
        } else {
            return 0;
        }
    }

    public byte fin() {
        return resultado(this.tablero);
    }

    public byte getMovimientos() {
        return movimientos;
    }

}
