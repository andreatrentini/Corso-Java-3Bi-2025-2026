package AlgoritmiDiOrdinamento;

import java.util.Random;

public class SelectionSort {

    public static void riempi(int[] vettore, int min, int max) {
        Random generatore = new Random();
        for (int i = 0; i < vettore.length; i++) {
            vettore[i] = generatore.nextInt(max - min + 1) + min;
        }
    }

    public static void selectionSort(int[] vettore) {
        for (int i = 0; i < vettore.length - 1; i++) {
            for (int j = i + 1; j < vettore.length; j++) {
                if (vettore[i] > vettore[j]) {
                    // Faccio lo scambio
                    int tmp = vettore[i];
                    vettore[i] = vettore[j];
                    vettore[j] = tmp;
                }
            }
        }
    }

    public static void main(String[] args) {
        // Dichiaro l'array
        int DIMARRAY = 100000;
        int[] v;
        v = new int[DIMARRAY];

        // Popolo l'array di valori casuali compresi fra 1 e 1000
        riempi(v, 0, 1000);

        // Ordino con Selection sort
        selectionSort(v);

    }
}
