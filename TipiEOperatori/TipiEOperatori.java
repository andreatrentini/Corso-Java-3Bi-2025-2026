package TipiEOperatori;

import java.util.Scanner;

public class TipiEOperatori {
    public static void main(String[] args) {
        /*
         * Tipi di variabile
         * Perché: devo dire al sistema operativo quanta ram assegnare per la variabile
         * Attenzione che anche gli operatori matematici possono comportarsi in maniera diversa
         */
        int nomeVariabile;
        double altraVariabile;
        float altroDecimale;
        boolean veroFalso;
        char singoloCarattere;
        String testo;

        nomeVariabile = 4;
        altraVariabile = 5.2;
        // Stesso valore per il tipo float: ATTENZIONE alla f finale
        altroDecimale = 5.2f;
        veroFalso = false;
        // Le due operazioni successive sono IDENTICHE!!!!!
        singoloCarattere = 'a';        
        singoloCarattere = 97;
        testo = "Ciao come stai?";

        /*
         * Operatori aritmetici binari (richiedono due valori)
         * +: somma
         * -: sottrazione
         * *: moltiplicazione
         * /: divisione sia intera che reale
         * %: resto della divisione intera
         * 
         * Operatori artimetici unari
         * ++: incrementa di uno il valore della variabile
         * --: decrementa di uno il valore della variabile
         */

         /*
          * Operatori logici binari di confronto
          Sono operatori che coinvolgono due valori
          NB: sono chiamati operatori perché restituiscono un valore
          ==
          !=
          >
          <
          >=
          <=     
          
          Operatore logico unario
          ! nega il valore
          */

            /*
             * Acquisizione valori da tastiera
             */
            Scanner tastiera;
            tastiera = new Scanner(System.in);

            // Scanner tastiera = new Scanner(System.in);

            int x, y;

            System.out.print("Inserisci un intero: ");
            x = tastiera.nextInt();
            System.out.print("Inserisci un intero: ");
            y = tastiera.nextInt();

          /*
          Blocco di codice con esempio da non seguire */
          boolean controllo;
          controllo = (x == y);
          // Il confornto nella condizione dell'istruzione if NON SERVE!!!
          if (controllo == false) {
            // Tutte le istruzioni comprese nelle parentesi graffe verranno eseguite se la condizione è vera
          } 
          else {
            // Tutte le istruzioni comprese nelle parentesi graffe verranno eseguite se la condizione è falsa
          }         

          // Versione corretta
          if (controllo) {
            // ...
          }

          // In alternativa...
          if (x == y) {
            //...
          }

          /*
           * Operatori booleani binari (usano due valori):
           * &&: and logico
           * !! or logico
           */

         
    }
}
