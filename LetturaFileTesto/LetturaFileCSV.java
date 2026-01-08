import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class LetturaFileCSV {
    private static double minimoDouble(double[] dati) {
        double min = Double.MAX_VALUE;
        for (int i = 0; i < dati.length; i++) {
            if (dati[i] < min) {
                min = dati[i];
            }
        }
        return min;
    }
    
    private static double massimoDouble(double[] dati) {
        double max = Double.MIN_VALUE;
        for (int i = 0; i < dati.length; i++) {
            if (dati[i] > max) {
                max = dati[i];
            }
        }
        return max;
    }

    private static double mediaDouble(double[] dati) {
        double somma = 0; 
        for (int i = 0; i < dati.length; i++) {
            somma = somma+ dati[i];
        } 
        return somma / dati.length;
    }

    public static void main(String[] args) {
        // Oggetto che ci consente di accedere al buffer di memoria nel quale
        // il PC memorizza ogni riga letta dal file
        BufferedReader br;
        // Oggetto che ci consente di leggere (reader) un file da un disco fisso
        FileReader fr;
        try {
            fr = new FileReader("SupermarketSales2.csv");
            br = new BufferedReader(fr);
            
            String riga;
            
            // Conto il numero di righe presenti nel file
            
            int nrRighe = 0;
            while ((riga = br.readLine()) != null) {
                nrRighe++;
            }
            System.out.println("Numero di righe presenti: " + nrRighe);
            
            // Chiudo il BufferedReader per rileggere il file da capo.
            // Quando chiudo il BufferedReader viene chiuso anche il file, 
            // fr.close() NON SERVE.

            br.close();

            double[] unitPrices = new double[nrRighe - 1];

            fr = new FileReader("SupermarketSales2.csv");
            br = new BufferedReader(fr);

            // Leggo la prima riga e la memorizzo nella variabile riga
            riga = br.readLine();
            // La prima riga del file csv contiene i nomi delle colonne
            // Memorizzo i nomi delle colonne in un array chiamato nomiColonne
            String[] nomiColonne = riga.split(",");
            System.out.println("Colonne presenti nel file:");
            for (int i = 0; i < nomiColonne.length; i++) {
                System.out.println(nomiColonne[i]);
            }

            int contatore = 0;
                      
            // Ripeto il blocco di istruzione mentre ho righe compilate nel file
            while ((riga = br.readLine()) != null) {
                // Divido i valori presenti nella riga usando la virgola come separatore.
                // L'array di String datiRiga conterrà nelle singole celle i valori letti
                // dalla riga.
                // ATTENZIONE: tutti i dati sono in formato string
                String[] datiRiga = riga.split(",");

                // Converto datiRiga[5] (contiene i prezzi della merce acquistata) in fomato double
                Double prezzoRiga = Double.parseDouble(datiRiga[5]);
                unitPrices[contatore] = prezzoRiga;
                System.out.println(unitPrices[contatore]);
                contatore++;
            }  
            // e' sempre buona pratica chiudere il BufferedReader        
            // fr.close() NON SERVE.  
            br.close();

            // Adesso ho un array di double con tutti i prezzi degli articoli

            // Calcolo di min, max, media, moda, mediana, varianza sqm

            System.out.println("Prezzo minimo: " + minimoDouble(unitPrices));
            System.out.println("Prezzo massimo: " + massimoDouble(unitPrices));


        }
        catch (IOException error) {
            System.out.println("Errore durante la lettura del file: " + error.getMessage());
        }
    }
}