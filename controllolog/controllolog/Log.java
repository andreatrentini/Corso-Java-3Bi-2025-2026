package controllolog.controllolog;

/**
 * Descrizione della classe
 */

public class Log {
    // Dichiarazione degli attributi
    private int id;
    private String tipoUtente;
    private String allarmeIDS;
    
    // Definizione dei costruttore
    /**
     * Costruisce un log a partire da una stringa che contiene id,tipoUtente,allarmeIDS
     * @param riga
     * @param separatore
     */
    public Log(String riga, String separatore) {
        try {            
            String datiGrezzi[] = riga.split(separatore);
            this.id = Integer.parseInt(datiGrezzi[0]);
            this.tipoUtente = datiGrezzi[1];
            this.allarmeIDS = datiGrezzi[2];
        } catch (Exception e) {
            this.id = 0;
            this.tipoUtente = null;
            this.allarmeIDS = null;
        }
    }

    public Log(String riga) {
        this(riga, ",");
    }

    public Log(int id, String tipoUtente, String allarmeIDS) {
        this.id = id;
        this.tipoUtente = tipoUtente;
        this.allarmeIDS = allarmeIDS;
    }

}
