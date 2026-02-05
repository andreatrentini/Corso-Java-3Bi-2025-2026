package GestioneVendite.gestionevendite;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Vendite {
    /**
     * Carica i dati delle vendite da un file csv.
     * @param nomeFile Nome del file da leggere. Deve essere completo del percorso
     * @param dati Array di string con i dati letti dal file csv,
     * @return Numero di righe lette.
     */
    public static int caricaDati(String nomeFile, String dati[], boolean intestazione) {
        // FileReader è una classe che contiene metodi che ci consentono, fra le altre cose,
        // di accedere ad un file presente sul file system (disco fisso...)
        FileReader fr;

        // BufferedReader è una classe che contiene metodi che ci sonsentono di caricare il file
        // in una zona temporanea della RAM (buffer) e di leggerne una riga alla volta.
        BufferedReader br;

        int nrRighe = nrRighe(nomeFile, intestazione);
        if (nrRighe > 0) {            
            try {
                fr = new FileReader(nomeFile);
                br = new BufferedReader(fr);    
                
                String riga;
                int i = 0;

                if (intestazione) {
                    br.readLine();
                }
                
                while((riga = br.readLine()) != null) {
                    dati[i] = riga;
                    i++;
                }
                
                br.close();
                return nrRighe;
            } catch (IOException e) {
                // TODO: handle exception
                return -1;
            }        
        }
        else {
            return -1;
        }
    }

    /**
     * 
     * @param nomeFile Nome del file da leggere. Deve essere completo del percorso
     * @return Numero di righe lette. -1 se ci sono stati errori nella lettura del file.
     */
    public static int nrRighe(String nomeFile, boolean intestazione) {

        // FileReader è una classe che contiene metodi che ci consentono, fra le altre cose,
        // di accedere ad un file presente sul file system (disco fisso...)
        FileReader fr;

        // BufferedReader è una classe che contiene metodi che ci sonsentono di caricare il file
        // in una zona temporanea della RAM (buffer) e di leggerne una riga alla volta.
        BufferedReader br;

        try {
            fr = new FileReader(nomeFile);
            br = new BufferedReader(fr);
            
            if (intestazione) {
                br.readLine();
            }

            int nrRighe = 0;

            while(br.readLine() != null) {
                nrRighe++;
            }

            br.close();
            return nrRighe;
        } catch (IOException e) {
            // TODO: handle exception
            return -1;
        }
    }

    public static String[] getHeaders(String nomeFile, String separatore) {
        // FileReader è una classe che contiene metodi che ci consentono, fra le altre cose,
        // di accedere ad un file presente sul file system (disco fisso...)
        FileReader fr;

        // BufferedReader è una classe che contiene metodi che ci sonsentono di caricare il file
        // in una zona temporanea della RAM (buffer) e di leggerne una riga alla volta.
        BufferedReader br;

        int nrRighe = nrRighe(nomeFile, false);
        if (nrRighe > 0) {            
            try {
                fr = new FileReader(nomeFile);
                br = new BufferedReader(fr);    
                
                String riga = br.readLine();
                br.close();

                return riga.split(separatore);                
            } catch (IOException e) {
                // TODO: handle exception
                return null;
            }        
        }
        else {
            return null;
        }
    }

    public static void visualizzaRiga(String riga[], String separatore) {
        for (int i = 0; i < riga.length - 1; i++) {
            System.out.print(riga[i] + separatore);
        }
        System.out.println(riga[riga.length - 1]);        
    }

    public static int[] getIntColumn(String[] dati, int nrColonna, String separatore) {
        int tmp[] = new int[dati.length];
        for (int i = 0; i < dati.length; i++) {
            String riga[] = dati[i].split(separatore);
            tmp[i] = Integer.parseInt(riga[nrColonna]);
        }
        return tmp;
    }

    public static void main(String[] args) {

        String nomeCSV = "SupermarketSales2.csv";
        String separatore = ",";
        
        System.out.println("Numero di righe: " + nrRighe(nomeCSV, true));
        
        String headers[] = getHeaders(nomeCSV, separatore);

        visualizzaRiga(headers, " | ");
        
        String dati[] = new String[nrRighe(nomeCSV, true)];

        caricaDati(nomeCSV, dati, true);

        for (int i = 0; i < dati.length; i++) {
            System.out.println(dati[i]);
        }

3

        for (int i = 0; i < quantita.length; i++) {
            System.out.println(quantita[i]);
        }
    }
}
