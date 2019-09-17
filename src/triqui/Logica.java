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
public class Logica {

    /**
     * Arreglo que representa el tablero del triqui<BR>
     * |0|1|2|<BR>
     * |3|4|5|<BR>
     * |6|7|8|
     */
    private byte[] tablero;

    /**
     * Arreglo con todas las jugadas posibles
     */
    private byte[][] jugadas = {
        {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, //Horizontal
        {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, //Vertical
        {0, 4, 8}, {2, 4, 6} //Diagonal
    };

    /**
     * Los movimientos del JUGADOR valen (1)
     */
    public final byte JUGADOR = 1;

    /**
     * El estado final del JUGADOR vale (3)
     */
    public final byte FINAL_JUGADOR = 3;

    /**
     * Los movimientos del PC valen (4)
     */
    public final byte PC = 4;

    /**
     * El estado semifinal del PC vale (8)
     */
    public final byte SEMIFINAL_PC = 8;

    /**
     * El estado final del PC vale (12)
     */
    public final byte FINAL_PC = 12;

    /**
     * Cantidad de movimientos
     */
    private byte movimientos;

    public Logica() {
        this.tablero = new byte[9];
        this.movimientos = 9;
    }

    public void actualizar(byte id, byte dato) {
        this.tablero[id] = dato;
        this.movimientos--;
    }

    public byte decidir() {
        byte desicion = -1;
        if (this.movimientos == 8) {
            desicion = inicio();
        } else {
            for (byte i = 0; i < this.tablero.length; i++) {
                if (this.tablero[i] == 0) {
                    /*se hace una copia del tablero actual y se prueban todas
                    las posibilidades con el turno siguiente*/
                    byte[] copia = (byte[]) this.tablero.clone();
                    copia[i] = this.JUGADOR;
                    for (byte[] jugada : this.jugadas) {
                        byte[] resultado = resultado(copia, jugada);
                        /* si una fila suma 8 significa que tengo 2,
                        en linea y puedo ganar inmediamente */
                        if (resultado[0] == this.SEMIFINAL_PC) {
                            return resultado[1];
                        }/* si una fila suma 3 significa que el rival,
                        puede tener 3 en linea y debo bloquear */
                        if (resultado[0] == this.FINAL_JUGADOR) {
                            return resultado[1];
                        } /* si no hay posibilidad de perder ni de ganar,
                        entonces se escoge en un espacio que este disponible
                         */ else if (resultado[1] > desicion) {
                            desicion = resultado[1];
                        }
                    }
                }
            }
        }
        return desicion;
    }

    /**
     * Este metodo es usado para la jugada inicial de la maquina.<BR>
     * si la posicion central esta disponible se juega alli, en caso contrario
     * se escoge la esquina superior izquierda
     *
     * @return byte retorna el indice donde se debe jugar.
     */
    private byte inicio() {
        //Escoger la posicion central si esta disponible
        if (this.tablero[4] == 0) {
            return 4;
        } //sino la posicion superior izquierda
        else {
            return 0;
        }
    }

    /**
     * Este metodo es usado para calcular el valor de una jugada.<BR>
     * Dependiendo de los valores retornados por esta funcion, se puede saber si
     * se gana o se pierde.
     *
     * @param byte[] jugada fila a la que se va a calcular su valor
     * @param byte[] tablero Tablero original o copia que se va a calcular
     * @return byte[] retorna un arreglo de bytes que representa, la suma de esa
     * jugada y el indice de la posicion vacia de esa jugada.
     */
    private byte[] resultado(byte[] tablero, byte[] jugada) {
        byte suma = 0;
        byte vacio = -1;
        for (byte i : jugada) {
            suma += tablero[i];
            vacio = (this.tablero[i] == 0) ? i : vacio;
            /*encontrar una casilla vacia para jugar en el tablero original*/
        }
        return new byte[]{suma, vacio};
    }

    public byte fin() {
        for (byte[] jugada : this.jugadas) {
            byte resultado = resultado(this.tablero, jugada)[0];
            if (resultado == this.FINAL_JUGADOR || resultado == this.FINAL_PC) {
                return resultado;
            }
        }
        return 0;
    }

    /**
     * Este metodo es usado para saber cuando el juego ha terminado.
     *
     * @return byte retorna la cantidad de movimientos restantes para finalizar
     * el juego.
     */
    public byte getMovimientos() {
        return this.movimientos;
    }

}
