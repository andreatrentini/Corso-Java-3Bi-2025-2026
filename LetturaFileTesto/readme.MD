# Lettura di un file CSV e successiva elaborazione dei dati

1. #### Aprire il file 
   > FileReader e BufferReader
2. #### Conteggio delle righe
   > Ciclo mentre le righe sono diverse da null
     - Incremento il contatore
     - Chiudere il file!!!
     - creare un array per i prezzi della dimensione delle righe presenti nel file (-1)
3. #### Lettura dell'intestazione con i nomi delle colonne
   - 1 solo br.readLine()
   - split -> array per dividere la stringa usando come separatore la virgola (CSV: comma separated value)
   - Visualizzazione dei nomi delle colonne 
4. #### Lettura dei valori
   - Ciclo mentre le righe sono diverse da null
   - lettura della riga
   - split -> array
   - dall'array con i valori della riga prendere l'elemento che contiene il prezzo e memorizzarlo nel vettore creato prima
   - Chiudere il file!!!
5. #### Usando l'array prezzi calcolare:
   - min
   - max
   - media
   - moda
   - mediana
   - varianza
   - scarto quadratico medio
6. #### Visualizzare tutti i dati calcolati