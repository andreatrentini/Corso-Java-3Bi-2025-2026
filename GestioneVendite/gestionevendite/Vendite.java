package gestionevendite.gestionevendite;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Vendite {

    /**
     * Conta il numero di righe dati presenti in un file CSV.
     * Se il file contiene una riga di intestazione, questa non viene conteggiata.
     *
     * @param nomeFile    Percorso completo del file da leggere.
     * @param intestazione true se la prima riga è un'intestazione da saltare,
     *                     false se tutte le righe sono dati.
     * @return Numero di righe dati lette, oppure -1 in caso di errore di I/O.
     */
    public static int nrRighe(String nomeFile, boolean intestazione) {

        // FileReader apre il file sul filesystem e gestisce la lettura
        // a basso livello (byte per byte).
        FileReader fr;

        // BufferedReader avvolge FileReader aggiungendo un buffer in RAM:
        // carica blocchi di dati in memoria e offre il metodo readLine()
        // che restituisce una riga alla volta (null quando il file è finito).
        BufferedReader br;

        try {
            fr = new FileReader(nomeFile);
            br = new BufferedReader(fr);

            // Se il file ha un'intestazione, la prima readLine() la legge
            // e la scarta: non viene conteggiata.
            if (intestazione) {
                br.readLine();
            }

            int nrRighe = 0;

            // Legge una riga per volta finché readLine() non restituisce null
            // (fine del file). Ogni riga letta incrementa il contatore.
            while (br.readLine() != null) {
                nrRighe++;
            }

            br.close(); // chiude il file e libera le risorse di sistema
            return nrRighe;
        } catch (IOException e) {
            // IOException viene lanciata se il file non esiste, non è leggibile
            // o si verifica un qualsiasi errore di I/O durante la lettura.
            // Restituiamo -1 come valore convenzionale di errore.
            return -1;
        }
    }

    /**
     * Carica le righe dati di un file CSV in un array di stringhe già allocato
     * dal chiamante. Ogni elemento dell'array corrisponde a una riga del file.
     * L'array deve avere dimensione sufficiente a contenere tutte le righe
     * (usare nrRighe() per determinare la dimensione corretta).
     *
     * @param nomeFile     Percorso completo del file da leggere.
     * @param dati         Array di String in cui verranno memorizzate le righe.
     *                     Viene modificato direttamente (passaggio per riferimento).
     * @param intestazione true se la prima riga è un'intestazione da saltare.
     * @return Numero di righe caricate, oppure -1 in caso di errore.
     */
    public static int caricaDati(String nomeFile, String dati[], boolean intestazione) {

        // FileReader gestisce l'accesso fisico al file sul disco.
        FileReader fr;

        // BufferedReader aggiunge il buffering e il metodo readLine().
        BufferedReader br;

        // Prima di aprire il file, contiamo le righe per verificare che
        // il file non sia vuoto e che l'array sia stato allocato correttamente.
        int nrRighe = nrRighe(nomeFile, intestazione);
        if (nrRighe > 0) {
            try {
                fr = new FileReader(nomeFile);
                br = new BufferedReader(fr);

                String riga;  // variabile temporanea per la riga corrente
                int i = 0;    // indice per scrivere nell'array dati[]

                // Salta l'intestazione se presente (non deve finire nell'array)
                if (intestazione) {
                    br.readLine();
                }

                // Legge ogni riga e la salva nella posizione i dell'array.
                // L'assegnazione (riga = br.readLine()) restituisce null a fine file,
                // il che termina il ciclo.
                while ((riga = br.readLine()) != null) {
                    dati[i] = riga;
                    i++;
                }

                br.close();
                return nrRighe;
            } catch (IOException e) {
                // Errore durante l'apertura o la lettura del file.
                return -1;
            }
        } else {
            // Il file è vuoto o non esiste: non c'è nulla da caricare.
            return -1;
        }
    }

    /**
     * Legge la prima riga di un file CSV e la suddivide nei singoli campi,
     * restituendo un array con i nomi delle colonne (intestazioni).
     *
     * @param nomeFile   Percorso completo del file da leggere.
     * @param separatore Carattere (o stringa) usato come delimitatore nel CSV
     *                   (es. "," oppure ";").
     * @return Array di String con i nomi delle colonne, oppure null in caso
     *         di errore o file vuoto.
     */
    public static String[] getHeaders(String nomeFile, String separatore) {

        FileReader fr;
        BufferedReader br;

        // Verifica che il file contenga almeno una riga prima di aprirlo.
        // Passiamo false perché vogliamo contare anche la riga di intestazione.
        int nrRighe = nrRighe(nomeFile, false);
        if (nrRighe > 0) {
            try {
                fr = new FileReader(nomeFile);
                br = new BufferedReader(fr);

                // Legge solo la prima riga del file (l'intestazione).
                String riga = br.readLine();
                br.close();

                // split() divide la stringa in un array di sottostringhe usando
                // il separatore come delimitatore.
                // Es. "ID,Nome,Qty".split(",") → ["ID", "Nome", "Qty"]
                return riga.split(separatore);
            } catch (IOException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Stampa su console gli elementi di un array di stringhe separati da
     * un delimitatore, con l'ultimo elemento seguito da un ritorno a capo.
     * Utile per visualizzare le intestazioni o una singola riga del CSV.
     *
     * @param riga       Array di String da visualizzare.
     * @param separatore Stringa inserita tra un elemento e il successivo
     *                   (es. " | " oppure ",").
     */
    public static void visualizzaRiga(String riga[], String separatore) {
        // Stampa tutti gli elementi tranne l'ultimo, seguiti dal separatore.
        for (int i = 0; i < riga.length - 1; i++) {
            System.out.print(riga[i] + separatore);
        }
        // Stampa l'ultimo elemento con println (aggiunge '\n' alla fine).
        System.out.println(riga[riga.length - 1]);
    }

    /**
     * Estrae una colonna numerica intera da un array di righe CSV e la
     * restituisce come array di int. Se un valore non è convertibile in
     * intero (campo vuoto, testo, ecc.), viene sostituito con 0.
     *
     * @param dati       Array di String in cui ogni elemento è una riga CSV.
     * @param nrColonna  Indice (0-based) della colonna da estrarre.
     * @param separatore Carattere delimitatore del CSV.
     * @return Array di int con i valori della colonna specificata.
     */
    public static int[] getIntColumn(String[] dati, int nrColonna, String separatore) {
        // Crea l'array risultato con la stessa lunghezza dell'array di input.
        int tmp[] = new int[dati.length];
        for (int i = 0; i < dati.length; i++) {
            // Divide la riga nei suoi campi usando il separatore.
            String riga[] = dati[i].split(separatore);
            try {
                // Integer.parseInt() converte la stringa in int.
                // Lancia NumberFormatException se la stringa non è un numero valido.
                tmp[i] = Integer.parseInt(riga[nrColonna]);
            } catch (NumberFormatException error) {
                // Valore non numerico: impostiamo 0 come valore di fallback.
                tmp[i] = 0;
            }
        }
        return tmp;
    }

    /**
     * Calcola la somma di tutti gli elementi di un array di interi.
     *
     * @param dati Array di int di cui calcolare la somma.
     * @return Somma di tutti gli elementi.
     */
    public static int somma(int dati[]) {
        int tmp = 0; // accumulatore inizializzato a 0
        for (int i = 0; i < dati.length; i++) {
            tmp += dati[i]; // aggiunge il valore corrente all'accumulatore
        }
        return tmp;
    }

    /**
     * Calcola la media aritmetica degli elementi di un array di interi.
     * Il risultato è un double per preservare la parte decimale.
     *
     * @param dati Array di int di cui calcolare la media.
     * @return Media aritmetica come valore double.
     */
    public static double media(int dati[]) {
        // Il cast (double) è necessario: senza di esso la divisione int/int
        // sarebbe una divisione intera e perderebbe la parte decimale.
        return (double) somma(dati) / dati.length;
    }

    /**
     * Ordina in modo crescente un array di interi usando l'algoritmo
     * Selection Sort (ordinamento per selezione).
     * L'algoritmo scorre l'array e, ad ogni posizione i, trova il minimo
     * tra gli elementi non ancora ordinati (da i+1 in poi) e lo scambia
     * con l'elemento in posizione i.
     * Complessità temporale: O(n²).
     * ATTENZIONE: modifica direttamente l'array passato come parametro.
     *
     * @param dati Array di int da ordinare in modo crescente.
     */
    public static void selectionSort(int dati[]) {
        // i avanza la "frontiera" tra la parte ordinata (sinistra) e
        // quella non ancora ordinata (destra).
        for (int i = 0; i < dati.length - 1; i++) {
            // j scorre tutti gli elementi a destra di i cercando
            // eventuali valori minori di dati[i].
            for (int j = i + 1; j < dati.length; j++) {
                if (dati[i] > dati[j]) {
                    // dati[j] è minore di dati[i]: li scambia.
                    // tmp è la variabile d'appoggio necessaria per lo scambio.
                    int tmp = dati[i];
                    dati[i] = dati[j];
                    dati[j] = tmp;
                }
            }
        }
    }

    /**
     * Ordina in modo crescente un array di interi usando l'algoritmo
     * Bubble Sort (ordinamento a bolle) con ottimizzazione.
     * Ad ogni passata, confronta coppie adiacenti e le scambia se sono
     * nell'ordine sbagliato. La variabile {@code ordinato} permette di
     * terminare anticipatamente se nessuno scambio avviene in una passata
     * (array già ordinato). La variabile {@code fine} riduce il numero di
     * confronti ad ogni iterazione, poiché il valore massimo "sale" in fondo.
     * Complessità temporale: O(n²) nel caso peggiore, O(n) nel caso migliore.
     * ATTENZIONE: modifica direttamente l'array passato come parametro.
     *
     * @param dati Array di int da ordinare in modo crescente.
     */
    public static void bubbleSort(int dati[]) {
        boolean ordinato = false; // diventa true quando nessuno scambio avviene
        int fine = dati.length - 1; // indice dell'ultimo elemento da confrontare

        while (!ordinato) {
            ordinato = true; // assume che sia ordinato finché non trova uno scambio
            for (int i = 0; i < fine; i++) {
                if (dati[i] > dati[i + 1]) {
                    // Coppia fuori ordine: scambia i due elementi adiacenti.
                    int tmp = dati[i];
                    dati[i] = dati[i + 1];
                    dati[i + 1] = tmp;
                    ordinato = false; // almeno uno scambio: potrebbe non essere ordinato
                }
            }
            // Dopo ogni passata il valore più grande è già in posizione finale:
            // non serve più confrontarlo, quindi riduciamo fine.
            fine--;
        }
    }

    /**
     * Crea e restituisce una copia indipendente di un array di interi.
     * Utile per preservare l'array originale prima di operazioni distruttive
     * come l'ordinamento.
     *
     * @param dati Array di int da copiare.
     * @return Nuovo array di int con gli stessi valori di {@code dati}.
     */
    public static int[] copia(int[] dati) {
        // Alloca un nuovo array della stessa dimensione.
        int tmp[] = new int[dati.length];
        // Copia elemento per elemento dall'array originale al nuovo array.
        for (int i = 0; i < dati.length; i++) {
            tmp[i] = dati[i];
        }
        return tmp;
    }

    /**
     * Calcola la mediana di un array di interi.
     * La mediana è il valore centrale dell'insieme ordinato:
     * se il numero di elementi è dispari è l'elemento centrale;
     * se è pari è la media intera dei due elementi centrali.
     * L'array originale NON viene modificato (si lavora su una copia).
     *
     * @param dati Array di int di cui calcolare la mediana.
     * @return Valore della mediana.
     */
    public static int mediana(int dati[]) {
        // Lavora su una copia per non alterare l'array originale.
        int valori[] = copia(dati);
        // Ordina la copia: necessario per trovare il valore centrale.
        bubbleSort(valori);

        if (valori.length % 2 == 0) {
            // Numero pari di elementi: media dei due valori centrali.
            // Es. [1,3,5,7] → (3+5)/2 = 4
            return (valori[valori.length / 2 - 1] + valori[valori.length / 2]) / 2;
        } else {
            // Numero dispari di elementi: elemento esattamente centrale.
            // Es. [1,3,5,7,9] → 5 (indice 2)
            return valori[valori.length / 2];
        }
    }

    /**
     * Calcola la moda di un array di interi, cioè il valore che compare
     * con la frequenza più alta. Se più valori hanno la stessa frequenza
     * massima, restituisce il primo trovato in ordine crescente.
     * L'array originale NON viene modificato (si lavora su una copia).
     *
     * @param dati Array di int di cui calcolare la moda.
     * @return Il valore più frequente nell'array.
     */
    public static int moda(int dati[]) {
        // Lavora su una copia ordinata: raggruppare i valori uguali
        // adiacenti semplifica il conteggio delle occorrenze.
        int valori[] = copia(dati);
        bubbleSort(valori);

        // Traccia il valore con la frequenza massima trovata finora.
        int valorePiuFrequente = valori[0];
        int occorrenzePiuFrequente = 1;

        // Traccia il valore che si sta analizzando in questo momento.
        int valoreControllato = valori[0];
        int occorrenzeControllato = 1;

        // Scorre l'array ordinato dal secondo elemento.
        for (int i = 1; i < valori.length; i++) {
            if (valori[i] == valoreControllato) {
                // Stesso valore del precedente: incrementa il contatore.
                occorrenzeControllato++;
            } else {
                // Nuovo valore: prima di passare avanti, verifica se il valore
                // appena concluso ha più occorrenze del massimo attuale.
                if (occorrenzeControllato > occorrenzePiuFrequente) {
                    valorePiuFrequente = valoreControllato;
                    occorrenzePiuFrequente = occorrenzeControllato;
                }
                // Inizia a monitorare il nuovo valore.
                valoreControllato = valori[i];
                occorrenzeControllato = 1;
            }
        }
        // Controlla l'ultimo gruppo di valori uguali che rimane fuori dal ciclo.
        if (occorrenzeControllato > occorrenzePiuFrequente) {
            valorePiuFrequente = valoreControllato;
            occorrenzePiuFrequente = occorrenzeControllato;
        }

        return valorePiuFrequente;
    }

    /**
     * Calcola lo scarto quadratico di un singolo valore rispetto alla media,
     * ovvero (valore - media)². È un passo intermedio per il calcolo
     * della varianza.
     *
     * @param valore Valore intero di cui calcolare lo scarto.
     * @param media  Media aritmetica dell'insieme di riferimento.
     * @return (valore - media)² come valore double.
     */
    public static double scarti(int valore, double media) {
        // Math.pow(base, esponente) calcola base^esponente.
        // Elevare al quadrato rende positivi tutti gli scarti.
        return Math.pow(valore - media, 2);
    }

    /**
     * Calcola la varianza di un array di interi.
     * La varianza è la media degli scarti quadratici rispetto alla media:
     * varianza = Σ(xᵢ - media)² / n
     * Misura quanto i valori si disperdono intorno alla media.
     *
     * @param dati Array di int di cui calcolare la varianza.
     * @return Varianza come valore double.
     */
    public static double varianza(int dati[]) {
        double m = media(dati); // calcola la media una volta sola
        double sommaScarti = 0;
        // Somma gli scarti quadratici di ogni elemento rispetto alla media.
        for (int i = 0; i < dati.length; i++) {
            sommaScarti += scarti(dati[i], m);
        }
        // Divide per n per ottenere la media degli scarti quadratici.
        return sommaScarti / dati.length;
    }

    /**
     * Calcola lo scarto quadratico medio (deviazione standard) di un array
     * di interi. È la radice quadrata della varianza e si esprime nelle
     * stesse unità di misura dei dati originali.
     * Un valore basso indica che i dati sono concentrati intorno alla media;
     * un valore alto indica grande dispersione.
     *
     * @param dati Array di int di cui calcolare lo scarto quadratico medio.
     * @return Scarto quadratico medio come valore double.
     */
    public static double sqm(int dati[]) {
        // Math.sqrt() calcola la radice quadrata.
        return Math.sqrt(varianza(dati));
    }

    /**
     * Ricerca lineare (sequenziale): restituisce l'indice della prima
     * occorrenza di {@code valore} nell'array {@code dati}.
     * Scorre l'array dall'inizio e si ferma al primo elemento uguale al
     * valore cercato. Non richiede che l'array sia ordinato.
     * Complessità temporale: O(n).
     *
     * @param dati   Array di int in cui cercare.
     * @param valore Valore intero da cercare nell'array.
     * @return Indice della prima occorrenza di {@code valore}, oppure -1
     *         se il valore non è presente nell'array.
     */
    public static int cercaPrimo(int dati[], int valore) {
        for (int i = 0; i < dati.length; i++) {
            if (valore == dati[i]) {
                // Trovato: restituisce immediatamente l'indice corrente.
                return i;
            }
        }
        // Il ciclo è terminato senza trovare il valore: restituisce -1.
        return -1;
    }

    /**
     * Punto di ingresso del programma. Carica un file CSV di vendite al
     * supermercato e testa tutti i metodi statici della classe Vendite:
     * lettura file, estrazione colonne, ordinamento, ricerca e statistiche.
     *
     * @param args Argomenti da riga di comando (non utilizzati).
     */
    public static void main(String[] args) {

        String nomeCSV = "SupermarketSales2.csv";
        String separatore = ",";

        // ----------------------------------------------------------------
        // 1. LETTURA FILE: nrRighe, getHeaders, caricaDati
        // ----------------------------------------------------------------
        System.out.println("=== LETTURA FILE ===");

        int righe = nrRighe(nomeCSV, true);
        System.out.println("Numero di righe dati: " + righe);

        // Legge e visualizza le intestazioni delle colonne.
        String headers[] = getHeaders(nomeCSV, separatore);
        System.out.print("Intestazioni: ");
        visualizzaRiga(headers, " | ");

        // Alloca l'array con la dimensione esatta e carica tutte le righe.
        String dati[] = new String[righe];
        caricaDati(nomeCSV, dati, true);

        // Mostra le prime 3 righe come anteprima.
        System.out.println("Prime 3 righe dati:");
        for (int i = 0; i < 3 && i < dati.length; i++) {
            System.out.println("  " + dati[i]);
        }

        // ----------------------------------------------------------------
        // 2. ESTRAZIONE COLONNA: getIntColumn
        //    Colonna 6 = Quantity (numero di articoli venduti per transazione)
        // ----------------------------------------------------------------
        System.out.println("\n=== ESTRAZIONE COLONNA QUANTITY (col. 6) ===");
        int quantita[] = getIntColumn(dati, 6, separatore);

        // Mostra i primi 10 valori estratti.
        System.out.print("Primi 10 valori: ");
        for (int i = 0; i < 10 && i < quantita.length; i++) {
            System.out.print(quantita[i] + " ");
        }
        System.out.println();

        // ----------------------------------------------------------------
        // 3. STATISTICHE: somma, media, mediana, moda, varianza, sqm
        // ----------------------------------------------------------------
        System.out.println("\n=== STATISTICHE SULLA COLONNA QUANTITY ===");
        System.out.println("Somma    : " + somma(quantita));
        System.out.printf("Media    : %.2f%n", media(quantita));
        System.out.println("Mediana  : " + mediana(quantita));
        System.out.println("Moda     : " + moda(quantita));
        System.out.printf("Varianza : %.4f%n", varianza(quantita));
        System.out.printf("SQM      : %.4f%n", sqm(quantita));

        // ----------------------------------------------------------------
        // 4. ORDINAMENTO: selectionSort e bubbleSort
        //    Si lavora su copie per non alterare l'array originale quantita[].
        // ----------------------------------------------------------------
        System.out.println("\n=== ORDINAMENTO ===");

        // --- Selection Sort ---
        int copiaSelection[] = copia(quantita);
        selectionSort(copiaSelection);
        System.out.print("Selection Sort (primi 10): ");
        for (int i = 0; i < 10 && i < copiaSelection.length; i++) {
            System.out.print(copiaSelection[i] + " ");
        }
        System.out.println();

        // --- Bubble Sort ---
        int copiaBubble[] = copia(quantita);
        bubbleSort(copiaBubble);
        System.out.print("Bubble Sort   (primi 10): ");
        for (int i = 0; i < 10 && i < copiaBubble.length; i++) {
            System.out.print(copiaBubble[i] + " ");
        }
        System.out.println();

        // Verifica che i due algoritmi producano lo stesso risultato.
        boolean uguale = true;
        for (int i = 0; i < copiaSelection.length; i++) {
            if (copiaSelection[i] != copiaBubble[i]) {
                uguale = false;
                break;
            }
        }
        System.out.println("Selection Sort == Bubble Sort: " + uguale);

        // ----------------------------------------------------------------
        // 5. RICERCA: cercaPrimo
        //    Cerca alcuni valori nell'array originale (non ordinato).
        // ----------------------------------------------------------------
        System.out.println("\n=== RICERCA LINEARE ===");

        int[] valoriDaCercare = { quantita[0], quantita[quantita.length / 2], 999 };
        for (int v : valoriDaCercare) {
            int pos = cercaPrimo(quantita, v);
            if (pos >= 0) {
                System.out.println("Valore " + v + " trovato all'indice " + pos);
            } else {
                System.out.println("Valore " + v + " NON trovato nell'array");
            }
        }

        // ----------------------------------------------------------------
        // 6. SCARTI: test del metodo scarti() su un singolo valore
        // ----------------------------------------------------------------
        System.out.println("\n=== SCARTO QUADRATICO DI UN SINGOLO VALORE ===");
        double m = media(quantita);
        int primoValore = quantita[0];
        System.out.printf("Media: %.2f | Primo valore: %d | Scarto quadratico: %.4f%n",
                m, primoValore, scarti(primoValore, m));
    }
}
