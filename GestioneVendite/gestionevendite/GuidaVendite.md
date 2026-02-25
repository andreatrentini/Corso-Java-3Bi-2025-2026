# Guida Java — Classe `Vendite`

Questa guida analizza la classe `Vendite.java` e illustra i principali concetti del linguaggio Java attraverso esempi tratti direttamente dal codice sorgente.

---

## Indice

1. [Classe Vendite e metodo main](#1-classe-vendite-e-metodo-main)
2. [Variabili e tipi di dato](#2-variabili-e-tipi-di-dato)
3. [Strutture di controllo: if, while, for](#3-strutture-di-controllo-if-while-for)
4. [Array](#4-array)
5. [Metodi statici](#5-metodi-statici)
6. [Blocco try…catch e gestione delle eccezioni](#6-blocco-trycatch-e-gestione-delle-eccezioni)
7. [Lettura di file di testo (CSV)](#7-lettura-di-file-di-testo-csv)
8. [Algoritmi di ordinamento](#8-algoritmi-di-ordinamento)
9. [Algoritmi di ricerca](#9-algoritmi-di-ricerca)
10. [Calcoli statistici](#10-calcoli-statistici)

---

## 1. Classe Vendite e metodo main

### La classe

In Java tutto il codice risiede all'interno di una **classe**. Una classe è un modello (template) che raggruppa dati e comportamenti correlati. La classe `Vendite` appartiene al package `gestionevendite.gestionevendite` e raccoglie tutti i metodi necessari per caricare, analizzare e ordinare dati di vendita.

```java
package gestionevendite.gestionevendite;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Vendite {
    // ... metodi statici ...
}
```

Le righe `import` importano classi da librerie standard di Java che verranno usate all'interno della classe.

### Il metodo main

Il metodo `main` è il **punto di ingresso** di ogni programma Java. Quando si esegue un programma, la JVM (Java Virtual Machine) cerca e invoca automaticamente questo metodo. La sua firma è sempre la stessa:

| Parte della firma | Significato |
|---|---|
| `public` | Il metodo è accessibile da qualunque punto del programma |
| `static` | Il metodo appartiene alla classe, non a una sua istanza |
| `void` | Il metodo non restituisce alcun valore |
| `String[] args` | Array di stringhe contenente eventuali argomenti passati da riga di comando |

Il `main` è organizzato in **6 sezioni** che testano in sequenza tutti i metodi statici della classe.

#### Sezione 1 — Lettura file

Testa `nrRighe`, `getHeaders`, `caricaDati` e `visualizzaRiga`. Carica il file CSV e mostra le prime 3 righe come anteprima.

```java
public static void main(String[] args) {

    String nomeCSV = "SupermarketSales2.csv";
    String separatore = ",";

    // --- 1. LETTURA FILE ---
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
```

#### Sezione 2 — Estrazione colonna

Testa `getIntColumn`. Estrae la colonna 6 (Quantity) come array di interi e ne mostra i primi 10 valori.

```java
    // --- 2. ESTRAZIONE COLONNA ---
    System.out.println("\n=== ESTRAZIONE COLONNA QUANTITY (col. 6) ===");
    int quantita[] = getIntColumn(dati, 6, separatore);

    // Mostra i primi 10 valori estratti.
    System.out.print("Primi 10 valori: ");
    for (int i = 0; i < 10 && i < quantita.length; i++) {
        System.out.print(quantita[i] + " ");
    }
    System.out.println();
```

#### Sezione 3 — Statistiche

Testa `somma`, `media`, `mediana`, `moda`, `varianza` e `sqm` sull'array `quantita`.

```java
    // --- 3. STATISTICHE ---
    System.out.println("\n=== STATISTICHE SULLA COLONNA QUANTITY ===");
    System.out.println("Somma    : " + somma(quantita));
    System.out.printf("Media    : %.2f%n", media(quantita));
    System.out.println("Mediana  : " + mediana(quantita));
    System.out.println("Moda     : " + moda(quantita));
    System.out.printf("Varianza : %.4f%n", varianza(quantita));
    System.out.printf("SQM      : %.4f%n", sqm(quantita));
```

> `System.out.printf` usa i **format specifier**: `%.2f` stampa un `double` con 2 decimali, `%n` è il carattere di ritorno a capo indipendente dalla piattaforma.

#### Sezione 4 — Ordinamento

Testa `copia`, `selectionSort` e `bubbleSort`. Si lavora su copie dell'array originale per non alterarne l'ordine, poi si verifica che i due algoritmi producano lo stesso risultato.

```java
    // --- 4. ORDINAMENTO ---
    System.out.println("\n=== ORDINAMENTO ===");

    // Selection Sort su una copia
    int copiaSelection[] = copia(quantita);
    selectionSort(copiaSelection);
    System.out.print("Selection Sort (primi 10): ");
    for (int i = 0; i < 10 && i < copiaSelection.length; i++) {
        System.out.print(copiaSelection[i] + " ");
    }
    System.out.println();

    // Bubble Sort su un'altra copia
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
```

#### Sezione 5 — Ricerca

Testa `cercaPrimo` cercando: il primo valore dell'array, il valore centrale e un valore assente (`999`).

```java
    // --- 5. RICERCA LINEARE ---
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
```

> Il costrutto `for (int v : valoriDaCercare)` è il **for-each**: scorre automaticamente tutti gli elementi dell'array senza gestire l'indice manualmente.

#### Sezione 6 — Scarti

Testa il metodo `scarti` su un singolo valore, mostrando la media, il valore e il suo scarto quadratico.

```java
    // --- 6. SCARTI ---
    System.out.println("\n=== SCARTO QUADRATICO DI UN SINGOLO VALORE ===");
    double m = media(quantita);
    int primoValore = quantita[0];
    System.out.printf("Media: %.2f | Primo valore: %d | Scarto quadratico: %.4f%n",
            m, primoValore, scarti(primoValore, m));
}
```

---

## 2. Variabili e tipi di dato

Una **variabile** è uno spazio in memoria con un nome, usato per conservare un valore durante l'esecuzione del programma. In Java ogni variabile deve essere dichiarata specificando il suo **tipo**.

### Tipi primitivi

I tipi primitivi memorizzano direttamente il valore nella variabile.

| Tipo | Descrizione | Esempio |
|---|---|---|
| `int` | Numero intero (32 bit) | `int nrRighe = 0;` |
| `double` | Numero decimale (64 bit) | `double media = 3.14;` |
| `boolean` | Valore logico vero/falso | `boolean ordinato = false;` |
| `char` | Singolo carattere | `char lettera = 'A';` |

Esempi tratti da `Vendite.java`:

```java
int nrRighe = 0;          // contatore di righe
int i = 0;                // indice di ciclo
boolean ordinato = false; // flag per il bubbleSort
double m = media(dati);   // valore medio calcolato
```

### Tipi riferimento

I tipi riferimento non contengono direttamente il valore, ma un **indirizzo di memoria** (riferimento) che punta all'oggetto. Esempi importanti sono `String` e gli **array**.

```java
String nomeCSV = "SupermarketSales2.csv"; // riferimento a un oggetto String
String separatore = ",";
String dati[];    // riferimento a un array di String (vedi sezione Array)
```

> **Attenzione:** una variabile di tipo riferimento può valere `null`, ovvero non puntare a nessun oggetto. Usare un riferimento `null` causa una `NullPointerException`.

---

## 3. Strutture di controllo: if, while, for

### if — esecuzione condizionale

Il costrutto `if` esegue un blocco di codice solo se una condizione è vera. Può essere seguito da `else` per il caso opposto.

```java
// Da caricaDati: salta la prima riga se il file ha un'intestazione
if (intestazione) {
    br.readLine();
}
```

```java
// Da mediana: la formula cambia a seconda che il numero di elementi sia pari o dispari
if (valori.length % 2 == 0) {
    return (valori[valori.length / 2 - 1] + valori[valori.length / 2]) / 2;
} else {
    return valori[valori.length / 2];
}
```

```java
// Da moda: aggiorna il valore più frequente se le occorrenze del valore controllato
// sono maggiori di quelle del valore più frequente finora trovato
if (occorrenzeControllato > occorrenzePiuFrequente) {
    valorePiuFrequente = valoreControllato;
    occorrenzePiuFrequente = occorrenzeControllato;
}
```

### while — ciclo a condizione

Il ciclo `while` ripete un blocco finché la condizione rimane vera. La condizione viene valutata **prima** di ogni iterazione.

```java
// Da nrRighe: conta le righe leggendo fino alla fine del file
while (br.readLine() != null) {
    nrRighe++;
}
```

```java
// Da bubbleSort: continua a passare sull'array finché non è ordinato
boolean ordinato = false;
int fine = dati.length - 1;
while (!ordinato) {
    ordinato = true;
    for (int i = 0; i < fine; i++) {
        if (dati[i] > dati[i + 1]) {
            int tmp = dati[i];
            dati[i] = dati[i + 1];
            dati[i + 1] = tmp;
            ordinato = false;
        }
    }
    fine--;
}
```

### for — ciclo con contatore

Il ciclo `for` è adatto quando si conosce in anticipo il numero di iterazioni. La sua sintassi compatta riunisce inizializzazione, condizione e aggiornamento in un'unica riga.

```java
// Sintassi:  for (inizializzazione; condizione; aggiornamento)

// Da somma: somma tutti gli elementi di un array
for (int i = 0; i < dati.length; i++) {
    tmp += dati[i];
}
```

```java
// Da selectionSort: doppio ciclo for per confrontare ogni coppia di elementi
for (int i = 0; i < dati.length - 1; i++) {
    for (int j = i + 1; j < dati.length; j++) {
        if (dati[i] > dati[j]) {
            int tmp = dati[i];
            dati[i] = dati[j];
            dati[j] = tmp;
        }
    }
}
```

---

## 4. Array

Un **array** è una struttura dati che memorizza una sequenza di elementi dello **stesso tipo**, accessibili tramite un indice intero (che parte da `0`).

### Caratteristiche fondamentali

- **Tipo riferimento:** la variabile array non contiene i dati, ma un riferimento all'area di memoria che li contiene.
- **Dimensione statica:** la dimensione viene fissata al momento della creazione con `new` e non può essere modificata in seguito.
- **Accesso per indice:** il primo elemento è all'indice `0`, l'ultimo è all'indice `lunghezza - 1`.
- **Proprietà `.length`:** restituisce il numero di elementi dell'array.

### Dichiarazione e creazione

```java
// Dichiarazione: la variabile dati è un riferimento a un array di String
String dati[];

// Creazione: si alloca l'array in memoria con new, specificando la dimensione
// La dimensione è il numero di righe del CSV (senza intestazione)
dati = new String[nrRighe(nomeCSV, true)];

// Dichiarazione e creazione in un'unica istruzione
int quantita[] = new int[dati.length];
```

### Accesso agli elementi

```java
// Da caricaDati: scrittura nell'array — assegna la riga letta alla posizione i
dati[i] = riga;

// Da somma: lettura dall'array — legge l'elemento alla posizione i
tmp += dati[i];
```

### Array come parametro

Poiché l'array è un tipo riferimento, passarlo come parametro a un metodo significa passare il riferimento, **non una copia** dei dati. Qualsiasi modifica fatta all'interno del metodo si riflette sull'array originale (vedi sezione 5).

```java
// bubbleSort riceve il riferimento all'array e lo modifica direttamente
public static void bubbleSort(int dati[]) { ... }

// copia crea un nuovo array e restituisce il riferimento al nuovo array
public static int[] copia(int[] dati) {
    int tmp[] = new int[dati.length];
    for (int i = 0; i < dati.length; i++) {
        tmp[i] = dati[i];
    }
    return tmp;
}
```

### Array di String

Ogni elemento di un array di `String` è a sua volta un tipo riferimento:

```java
// Da getHeaders: split divide la stringa usando il separatore e restituisce
// un array di String contenente i singoli campi
return riga.split(separatore);
```

---

## 5. Metodi statici

Un **metodo** è un blocco di codice con un nome, che può ricevere dati in ingresso (parametri) ed eventualmente restituire un risultato. I metodi **statici** appartengono alla classe stessa e possono essere chiamati senza creare un oggetto.

### Struttura di un metodo statico

```
[modificatore] static [tipoRestituito] nomeMetodo([tipoParam nomeParam, ...]) {
    // corpo del metodo
    return valore; // solo se tipoRestituito != void
}
```

### Valore restituito

Il tipo restituito è dichiarato prima del nome del metodo. Se il metodo non deve restituire nulla si usa `void`.

```java
// Restituisce un int: il numero di righe lette
public static int nrRighe(String nomeFile, boolean intestazione) { ... }

// Restituisce un double: il valore medio
public static double media(int dati[]) {
    return (double) somma(dati) / dati.length;
}

// Restituisce un array di int
public static int[] getIntColumn(String[] dati, int nrColonna, String separatore) { ... }

// Non restituisce nulla (void): modifica l'array passato come parametro
public static void selectionSort(int dati[]) { ... }
```

### Parametri e passaggio per valore

I tipi **primitivi** vengono passati **per valore**: il metodo riceve una copia del valore. Modificare il parametro all'interno del metodo non influisce sulla variabile del chiamante.

```java
// valore e media sono primitivi (int e double): vengono copiati
public static double scarti(int valore, double media) {
    return Math.pow(valore - media, 2);
}
```

Chiamata:
```java
double risultato = scarti(42, 35.7); // 42 e 35.7 vengono copiati nel metodo
```

### Parametri e passaggio per riferimento

I tipi **riferimento** (array, oggetti) vengono passati **per riferimento**: il metodo riceve l'indirizzo dell'oggetto in memoria. Modificare l'oggetto all'interno del metodo **modifica l'originale**.

```java
// dati è un array (tipo riferimento): bubbleSort modifica l'array originale
public static void bubbleSort(int dati[]) {
    boolean ordinato = false;
    int fine = dati.length - 1;
    while (!ordinato) {
        ordinato = true;
        for (int i = 0; i < fine; i++) {
            if (dati[i] > dati[i + 1]) {
                int tmp = dati[i];
                dati[i] = dati[i + 1];
                dati[i + 1] = tmp;
                ordinato = false;
            }
        }
        fine--;
    }
}
```

Per evitare di alterare l'array originale, si crea prima una copia:

```java
// mediana e moda usano copia() per non alterare i dati originali
public static int mediana(int dati[]) {
    int valori[] = copia(dati);  // lavoro su una copia
    bubbleSort(valori);          // ordino solo la copia
    ...
}
```

### Panoramica dei metodi di Vendite

| Metodo | Parametri | Ritorna | Descrizione |
|---|---|---|---|
| `nrRighe` | `String nomeFile`, `boolean intestazione` | `int` | Conta le righe del file |
| `caricaDati` | `String nomeFile`, `String[] dati`, `boolean intestazione` | `int` | Carica le righe del CSV nell'array |
| `getHeaders` | `String nomeFile`, `String separatore` | `String[]` | Legge la prima riga (intestazione) |
| `visualizzaRiga` | `String[] riga`, `String separatore` | `void` | Stampa un array di stringhe |
| `getIntColumn` | `String[] dati`, `int nrColonna`, `String separatore` | `int[]` | Estrae una colonna numerica |
| `somma` | `int[] dati` | `int` | Somma degli elementi |
| `media` | `int[] dati` | `double` | Media aritmetica |
| `selectionSort` | `int[] dati` | `void` | Ordinamento per selezione |
| `bubbleSort` | `int[] dati` | `void` | Ordinamento a bolle |
| `copia` | `int[] dati` | `int[]` | Crea una copia dell'array |
| `mediana` | `int[] dati` | `int` | Mediana |
| `moda` | `int[] dati` | `int` | Moda |
| `scarti` | `int valore`, `double media` | `double` | Scarto quadratico di un valore |
| `varianza` | `int[] dati` | `double` | Varianza |
| `sqm` | `int[] dati` | `double` | Scarto quadratico medio |
| `cercaPrimo` | `int[] dati`, `int valore` | `int` | Ricerca lineare |

---

## 6. Blocco try…catch e gestione delle eccezioni

Un'**eccezione** è un evento anomalo che interrompe il normale flusso del programma (es. file non trovato, conversione di stringa in numero fallita). Il blocco `try...catch` permette di intercettare queste situazioni e gestirle senza mandare in crash il programma.

### Struttura

```java
try {
    // codice che potrebbe lanciare un'eccezione
} catch (TipoEccezione nomeVariabile) {
    // codice eseguito se si verifica l'eccezione
}
```

### Gestione di IOException (errori su file)

`IOException` viene lanciata quando si verificano errori di input/output (file non trovato, permessi insufficienti, ecc.).

```java
// Da nrRighe: gestione di un errore di lettura file
try {
    fr = new FileReader(nomeFile);
    br = new BufferedReader(fr);

    if (intestazione) {
        br.readLine();
    }

    int nrRighe = 0;

    while (br.readLine() != null) {
        nrRighe++;
    }

    br.close();
    return nrRighe;
} catch (IOException e) {
    // Se si verifica un errore di I/O, restituiamo -1 come segnale di errore
    return -1;
}
```

Se `new FileReader(nomeFile)` fallisce (ad esempio perché il file non esiste), il controllo passa immediatamente al blocco `catch`, saltando tutto il codice rimanente nel `try`.

### Gestione di NumberFormatException (conversione di stringhe in numeri)

`NumberFormatException` viene lanciata da `Integer.parseInt()` quando la stringa non rappresenta un numero valido.

```java
// Da getIntColumn: gestione di una cella non numerica nel CSV
try {
    tmp[i] = Integer.parseInt(riga[nrColonna]);
} catch (NumberFormatException error) {
    // Se la cella non è un numero (es. stringa vuota o testo), inseriamo 0
    tmp[i] = 0;
}
```

Questo approccio rende il codice **robusto** rispetto a dati non conformi nel file CSV.

### Relazione tra eccezione e valore di ritorno

In `Vendite.java` la convenzione adottata è restituire `-1` per segnalare un errore al chiamante:

```java
public static int caricaDati(...) {
    ...
    try {
        ...
        return nrRighe; // successo
    } catch (IOException e) {
        return -1;       // errore
    }
}
```

---

## 7. Lettura di file di testo (CSV)

Un file **CSV** (Comma-Separated Values) è un file di testo in cui ogni riga rappresenta un record e i campi sono separati da un carattere delimitatore (tipicamente la virgola).

```
Invoice ID,Branch,City,Customer type,Gender,Product line,Quantity,...
750-67-8428,A,Yangon,Member,Female,Health and beauty,7,...
226-31-3081,C,Naypyitaw,Normal,Female,Electronic accessories,7,...
```

### Classi utilizzate

- **`FileReader`**: apre il file e gestisce l'accesso a basso livello.
- **`BufferedReader`**: legge il file attraverso un buffer in RAM per migliorare le prestazioni; fornisce il metodo `readLine()` che legge una riga per volta e restituisce `null` a fine file.

### Conteggio delle righe

```java
public static int nrRighe(String nomeFile, boolean intestazione) {
    FileReader fr;
    BufferedReader br;

    try {
        fr = new FileReader(nomeFile);
        br = new BufferedReader(fr);

        // Salta l'intestazione se presente
        if (intestazione) {
            br.readLine();
        }

        int nrRighe = 0;

        // Legge riga per riga fino alla fine del file
        while (br.readLine() != null) {
            nrRighe++;
        }

        br.close(); // importante: chiude il file e libera le risorse
        return nrRighe;
    } catch (IOException e) {
        return -1;
    }
}
```

### Lettura dell'intestazione

```java
public static String[] getHeaders(String nomeFile, String separatore) {
    ...
    try {
        fr = new FileReader(nomeFile);
        br = new BufferedReader(fr);

        String riga = br.readLine(); // legge solo la prima riga
        br.close();

        // split divide la stringa in un array usando il separatore
        return riga.split(separatore);
    } catch (IOException e) {
        return null;
    }
}
```

### Caricamento dei dati

```java
public static int caricaDati(String nomeFile, String dati[], boolean intestazione) {
    ...
    int nrRighe = nrRighe(nomeFile, intestazione); // prima si conta il numero di righe
    if (nrRighe > 0) {
        try {
            fr = new FileReader(nomeFile);
            br = new BufferedReader(fr);

            String riga;
            int i = 0;

            if (intestazione) {
                br.readLine(); // salta l'intestazione
            }

            // Legge ogni riga e la memorizza nell'array
            while ((riga = br.readLine()) != null) {
                dati[i] = riga;
                i++;
            }

            br.close();
            return nrRighe;
        } catch (IOException e) {
            return -1;
        }
    } else {
        return -1;
    }
}
```

### Estrazione di una colonna numerica

Ogni riga del CSV è una stringa. Per ottenere un valore specifico si usa `split()` e poi `Integer.parseInt()`:

```java
public static int[] getIntColumn(String[] dati, int nrColonna, String separatore) {
    int tmp[] = new int[dati.length];
    for (int i = 0; i < dati.length; i++) {
        String riga[] = dati[i].split(separatore); // divide la riga nei suoi campi
        try {
            tmp[i] = Integer.parseInt(riga[nrColonna]); // converte il campo in int
        } catch (NumberFormatException error) {
            tmp[i] = 0;
        }
    }
    return tmp;
}
```

### Utilizzo nel main

```java
String nomeCSV = "SupermarketSales2.csv";
String separatore = ",";

// 1. Conta le righe
System.out.println("Numero di righe: " + nrRighe(nomeCSV, true));

// 2. Legge le intestazioni
String headers[] = getHeaders(nomeCSV, separatore);
visualizzaRiga(headers, " | ");

// 3. Crea l'array della giusta dimensione e carica i dati
String dati[] = new String[nrRighe(nomeCSV, true)];
caricaDati(nomeCSV, dati, true);

// 4. Estrae la colonna 6 (Quantity) come array di interi
int quantita[] = getIntColumn(dati, 6, ",");
```

---

## 8. Algoritmi di ordinamento

Ordinare un array significa disporre i suoi elementi in un ordine definito (crescente o decrescente). La classe `Vendite` implementa due classici algoritmi.

### Selection Sort (Ordinamento per Selezione)

**Idea:** ad ogni passo trova il minimo tra gli elementi non ancora ordinati e lo scambia con l'elemento in posizione `i`.

```java
public static void selectionSort(int dati[]) {
    for (int i = 0; i < dati.length - 1; i++) {
        for (int j = i + 1; j < dati.length; j++) {
            if (dati[i] > dati[j]) {
                // scambio dati[i] con dati[j]
                int tmp = dati[i];
                dati[i] = dati[j];
                dati[j] = tmp;
            }
        }
    }
}
```

**Traccia su array `[5, 3, 8, 1]`:**

| Passo | i | j | Array |
|---|---|---|---|
| Inizio | - | - | `[5, 3, 8, 1]` |
| i=0 | 0 | 1 | scambio 5↔3 → `[3, 5, 8, 1]` |
| i=0 | 0 | 3 | scambio 3↔1 → `[1, 5, 8, 3]` |
| i=1 | 1 | 3 | scambio 5↔3 → `[1, 3, 8, 5]` |
| i=2 | 2 | 3 | scambio 8↔5 → `[1, 3, 5, 8]` |

**Complessità:** O(n²) — non adatto a grandi dataset.

> Nota: poiché `selectionSort` riceve l'array come tipo riferimento, **modifica direttamente l'array originale** nel chiamante.

### Bubble Sort (Ordinamento a Bolle)

**Idea:** confronta coppie di elementi adiacenti e li scambia se sono nell'ordine sbagliato. Ripete finché non viene effettuato nessuno scambio in un passaggio completo.

```java
public static void bubbleSort(int dati[]) {
    boolean ordinato = false;
    int fine = dati.length - 1;
    while (!ordinato) {
        ordinato = true;
        for (int i = 0; i < fine; i++) {
            if (dati[i] > dati[i + 1]) {
                int tmp = dati[i];
                dati[i] = dati[i + 1];
                dati[i + 1] = tmp;
                ordinato = false; // almeno uno scambio: non è ancora ordinato
            }
        }
        fine--; // l'ultimo elemento è già al suo posto: non serve confrontarlo
    }
}
```

L'ottimizzazione `fine--` riduce il numero di confronti ad ogni iterazione perché gli elementi "grandi" vengono portati gradualmente in fondo.

**Complessità:** O(n²) nel caso peggiore, O(n) nel caso migliore (array già ordinato).

### Copia di un array prima dell'ordinamento

Poiché gli algoritmi di ordinamento modificano l'array originale, quando si vuole preservare l'ordine originale si usa il metodo `copia`:

```java
public static int[] copia(int[] dati) {
    int tmp[] = new int[dati.length];
    for (int i = 0; i < dati.length; i++) {
        tmp[i] = dati[i];
    }
    return tmp;
}
```

Questo metodo alloca un **nuovo array** in memoria e copia elemento per elemento.

---

## 9. Algoritmi di ricerca

Un algoritmo di ricerca individua la posizione di un elemento all'interno di una struttura dati.

### Ricerca Lineare (o Sequenziale)

**Idea:** scorre l'array dall'inizio alla fine confrontando ogni elemento con il valore cercato. Restituisce l'indice del primo elemento trovato, o `-1` se l'elemento non è presente.

```java
public static int cercaPrimo(int dati[], int valore) {
    for (int i = 0; i < dati.length; i++) {
        if (valore == dati[i]) {
            return i;  // trovato: restituisce l'indice
        }
    }
    return -1; // non trovato
}
```

**Caratteristiche:**
- Funziona su **array non ordinati**.
- **Complessità:** O(n) — nel caso peggiore esamina tutti gli n elementi.
- Il valore `-1` è usato come convenzione per indicare "elemento non trovato".

**Esempio d'uso:**

```java
int quantita[] = {7, 7, 6, 5, 8};
int pos = cercaPrimo(quantita, 6);
// pos == 2 (indice dell'elemento 6)

int pos2 = cercaPrimo(quantita, 99);
// pos2 == -1 (99 non è presente)
```

> **Ricerca Binaria (non implementata ma concettualmente collegata):** se l'array è **ordinato**, si può usare la ricerca binaria che ha complessità O(log n), molto più efficiente per grandi dataset. `bubbleSort` e `selectionSort` servono proprio anche a preparare i dati per algoritmi più efficienti.

---

## 10. Calcoli statistici

La classe `Vendite` implementa i principali indici statistici per analizzare i dati numerici di una colonna del CSV.

### Somma

```java
public static int somma(int dati[]) {
    int tmp = 0;
    for (int i = 0; i < dati.length; i++) {
        tmp += dati[i];
    }
    return tmp;
}
```

### Media aritmetica

La media è la somma di tutti i valori divisa per il numero di valori.

```java
public static double media(int dati[]) {
    return (double) somma(dati) / dati.length;
}
```

Il cast `(double)` è necessario perché la divisione tra due `int` in Java produce un `int` (divisione intera). Convertendo il numeratore in `double` si ottiene la divisione decimale.

### Mediana

La mediana è il valore centrale di un insieme ordinato. Se il numero di elementi è pari, è la media dei due valori centrali.

```java
public static int mediana(int dati[]) {
    int valori[] = copia(dati);   // copia per non alterare i dati originali
    bubbleSort(valori);           // ordina la copia

    if (valori.length % 2 == 0) {
        // numero pari di elementi: media dei due centrali
        return (valori[valori.length / 2 - 1] + valori[valori.length / 2]) / 2;
    } else {
        // numero dispari di elementi: elemento centrale
        return valori[valori.length / 2];
    }
}
```

**Esempio:** array `[3, 1, 4, 1, 5]` → ordinato `[1, 1, 3, 4, 5]` → mediana = `3` (indice 2).

### Moda

La moda è il valore che compare più frequentemente nell'insieme.

```java
public static int moda(int dati[]) {
    int valori[] = copia(dati);
    bubbleSort(valori); // ordinamento per raggruppare valori uguali

    int valorePiuFrequente = valori[0];
    int occorrenzePiuFrequente = 1;

    int valoreControllato = valori[0];
    int occorrenzeControllato = 1;

    for (int i = 1; i < valori.length; i++) {
        if (valori[i] == valoreControllato) {
            occorrenzeControllato++; // stesso valore: incrementa il contatore
        } else {
            // nuovo valore: controlla se quello precedente era più frequente
            if (occorrenzeControllato > occorrenzePiuFrequente) {
                valorePiuFrequente = valoreControllato;
                occorrenzePiuFrequente = occorrenzeControllato;
            }
            valoreControllato = valori[i];
            occorrenzeControllato = 1;
        }
    }
    // controllo finale per l'ultimo gruppo
    if (occorrenzeControllato > occorrenzePiuFrequente) {
        valorePiuFrequente = valoreControllato;
        occorrenzePiuFrequente = occorrenzeControllato;
    }

    return valorePiuFrequente;
}
```

### Varianza e Scarto Quadratico Medio (SQM)

La **varianza** misura quanto i valori si disperdono intorno alla media. Lo **scarto quadratico medio** (o deviazione standard) è la radice quadrata della varianza e si esprime nelle stesse unità dei dati originali.

```java
// Calcola lo scarto quadratico di un singolo valore rispetto alla media
public static double scarti(int valore, double media) {
    return Math.pow(valore - media, 2); // (valore - media)²
}

// Calcola la varianza: media degli scarti quadratici
public static double varianza(int dati[]) {
    double m = media(dati);
    double sommaScarti = 0;
    for (int i = 0; i < dati.length; i++) {
        sommaScarti += scarti(dati[i], m);
    }
    return sommaScarti / dati.length;
}

// Calcola lo scarto quadratico medio (deviazione standard)
public static double sqm(int dati[]) {
    return Math.sqrt(varianza(dati)); // radice quadrata della varianza
}
```

### Riepilogo degli indici statistici

| Indice | Formula | Metodo |
|---|---|---|
| Somma | Σ xᵢ | `somma(dati)` |
| Media | Σ xᵢ / n | `media(dati)` |
| Mediana | Valore centrale dell'insieme ordinato | `mediana(dati)` |
| Moda | Valore più frequente | `moda(dati)` |
| Varianza | Σ (xᵢ - media)² / n | `varianza(dati)` |
| Scarto Quad. Medio | √varianza | `sqm(dati)` |

### Dipendenze tra i metodi

```
somma ──────────────────────────────► media
                                         │
media ──────────────────────► scarti ──► varianza ──► sqm

copia + bubbleSort ──► mediana
copia + bubbleSort ──► moda
```

Questa struttura mostra come i metodi siano composti l'uno sull'altro seguendo il principio di **riuso del codice**.

---

*Guida generata analizzando `Vendite.java` — package `gestionevendite.gestionevendite`*
