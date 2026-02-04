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
    public static int caricaDati(String nomeFile, String dati[]) {
        // FileReader è una classe che contiene metodi che ci consentono, fra le altre cose,
        // di accedere ad un file presente sul file system (disco fisso...)
        FileReader fr;

        // BufferedReader è una classe che contiene metodi che ci sonsentono di caricare il file
        // in una zona temporanea della RAM (buffer) e di leggerne una riga alla volta.
        BufferedReader br;

        int nrRighe = nrRighe(nomeFile);
        if (nrRighe > 0) {            
            try {
                fr = new FileReader(nomeFile);
                br = new BufferedReader(fr);    
                
                String riga;
                int i = 0;
                
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
    public static int nrRighe(String nomeFile) {

        // FileReader è una classe che contiene metodi che ci consentono, fra le altre cose,
        // di accedere ad un file presente sul file system (disco fisso...)
        FileReader fr;

        // BufferedReader è una classe che contiene metodi che ci sonsentono di caricare il file
        // in una zona temporanea della RAM (buffer) e di leggerne una riga alla volta.
        BufferedReader br;

        try {
            fr = new FileReader(nomeFile);
            br = new BufferedReader(fr);
            
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
}
