public class LlistaRegistreResidus {
    private RegistreResidus[] llista;
    private int nElem;


    /**
     * Constructor que crea una llista de registres de residus
     * amb una capacitat màxima determinada.
     * Inicialment la llista està buida.
     *
     * @param capacitat nombre màxim de registres que pot contenir la llista.
     */
    public LlistaRegistreResidus (int capacitat) {
        llista = new RegistreResidus[capacitat];
        nElem = 0; 
    }

     /**
     * Consulta el nombre de registres emmagatzemats a la llista.
     *
     * @return nombre actual de registres de la llista.
     */
    public int getNElem() {
        return nElem;
    }
/**
     * Consulta el registre que es troba en una posició determinada.
     * Si la posició és vàlida, retorna una còpia del registre.
     *
     * @param pos posició del registre que es vol consultar.
     * @return una còpia del registre de la posició indicada,
     *         o null si la posició no és vàlida.
     */
    public RegistreResidus getRegistre(int pos) {
        RegistreResidus aux = null;

        if (pos >= 0 && pos < nElem) {
            aux = llista[pos].copia();
        }

        return aux;
    }

    /**
     * Comprova si la llista de registres està plena.
     *
     * @return true si la llista està plena, false en cas contrari.
     */
    public boolean esPlena() {
        return nElem == llista.length;
    }

    /**
     * Crea una còpia de la llista de registres de residus.
     *
     * @return una nova llista amb les mateixes dades.
     */
    public LlistaRegistreResidus copia() {
        LlistaRegistreResidus aux = new LlistaRegistreResidus(llista.length);
        for (int i = 0; i < nElem; i++) {
            aux.llista[i] = llista[i].copia();
        }
        aux.nElem = nElem;
        return aux;
    }

   /**
     * Mostra les dades de tots els registres de la llista.
     *
     * @return cadena de text amb els registres de residus.
     */
    @Override
    public String toString() {
        String text = "Llista de residus amb " + nElem + " registres.";

        for (int i = 0; i < nElem; i++) {
            text = text + "\n\t" + llista[i];
        }

        return text;
    }

    /**
     * Afegeix un nou registre de residus a la llista si hi ha espai disponible.
     *
     * @param nou registre de residus que es vol afegir.
     */
    public void afegir(RegistreResidus nou) {
        if (nElem < llista.length) {
            llista[nElem] = nou.copia();
            nElem++;
        }
    }
    
    /**
     * Consulta el registre del municipi amb més quilos de matèria
     * orgànica recollits per habitant en un any determinat.
     *
     * @param any any del qual es vol consultar el registre.
     * @return el registre trobat o null si no n'hi ha cap.
     */
    public RegistreResidus mesKgFracOrganicaAny(int any) {
        RegistreResidus aux = null;
        double max = 0;
        double kgPerHab = 0;
        
        for (int i = 0; i <nElem; i++) {
            if(llista[i].getAny() == any && llista[i].getPoblacio() > 0) {
                kgPerHab = llista[i].getMateriaOrganica()*1000/llista[i].getPoblacio();     
            
                if(aux == null || kgPerHab > max) {
                    max = kgPerHab;
                    aux = llista[i];
                }
            }
        }
        return aux;
    }

    /**
     * Consulta tots els registres de residus d'un municipi.
     *
     * @param municipi nom del municipi que es vol consultar.
     * @return una nova llista amb els registres del municipi.
     */
    public LlistaRegistreResidus registresMunicipis(String municipi) {
        LlistaRegistreResidus aux = new LlistaRegistreResidus(nElem);
        for(int i = 0; i < nElem; i++) {
            if(llista[i].getMunicipi().equalsIgnoreCase(municipi)) {
                aux.llista[aux.nElem] = llista[i].copia();
                aux.nElem++;                                         
            }
        }
        return aux;
    }

    /**
     * Consulta els municipis que en algun any superen el límit de quilos de recollida
     * selectiva per habitant. Retorn noms sense repetits, ignorant majus i minus
     * @param limitQuilos - límit de quilos de recollida selectiva per habitant
     * @return - taula de mida exacta dels noms dels municipis que superen el límit
     */
    public String[] municMesQuilosRecollida(double limitQuilos) {
        String[] noms = new String[nElem];
        int numNom = 0; 

        for(int i = 0; i < nElem; i++) {
            // Evitem dividir per zero o utilitzar una població negativa.
            if(llista[i].getPoblacio() > 0) {
                // Convertim les tones a quilos i calculem els quilos per habitant.
                double quilosTot = llista[i].getTotalRecollidaSelect() * 1000;
                double quilosXHab = quilosTot / llista[i].getPoblacio();

                if(quilosXHab > limitQuilos) {
                    String nom = llista[i].getMunicipi();
                    boolean repetit = false; 

                    // Un municipi pot aparèixer en diversos anys.
                    // Busquem el nom només entre les posicions ocupades.
                    for (int j = 0; j < numNom; j++) {
                        if(noms[j].equalsIgnoreCase(nom)) {
                            repetit = true;
                        }
                    }
                    // Si és un nom nou, el guardem i actualitzem el comptador.
                    if(repetit == false) {
                        noms[numNom] = nom;
                        numNom++;

                    }
                }
            }

        }
        // Copiem els noms a una taula de mida exacta, sense posicions sobrants.
        String[] resultat = new String[numNom];

        for(int i = 0; i < numNom; i++) {
            resultat[i] = noms[i];
        }
        return resultat;
    }

    /**
     * Consulta el primer registre d'un municipi que no ha reciclat
     * envasos lleugers en alguna anualitat.
     *
     * @return el primer registre trobat o null si no n'hi ha cap.
     */
    public RegistreResidus municipiNoReciclatEnvasos() {
        RegistreResidus aux = null;
        int i = 0;
        while(i < nElem && aux == null) {
            if(llista[i].getEnvasosLleugers() == 0) {
                aux = llista[i];
            }
            else { 
                i++;
            }
        }
        return aux;
    }

    /**
     * Calcula variació d'envasos reciclats d'un municipi entre el primer i últim any.
     * @param municipi - nom del municipi que es vol consultar
     * @return - diferència en tones entre últim any i primer
     */
    public double variacioEnvasos(String municipi) {
        double envasosPrimerAny = 0; 
        double envasosUltimAny = 0; 
        boolean trobat = false; 
        for (int i = 0; i < nElem; i++) {
            // Només considerem els registres del municipi indicat.
            if(llista[i].getMunicipi().equalsIgnoreCase(municipi)) {
                if(trobat == false) { 
                    envasosPrimerAny = llista[i].getEnvasosLleugers();
                    trobat = true;
                }
                // Actualitzem el valor a cada coincidència que hi hagi.
                envasosUltimAny = llista[i].getEnvasosLleugers();
            }
        }
        // Calculem l'increment o decrement en tones.
        return envasosUltimAny - envasosPrimerAny;
    }
    
    /**
     * Calcula els quilos de residus totals generats per habitant en una comarca
     * i un en un any determinat
     * @param comarca - nom de la comerca que es vol consultar.
     * @param any - any de les dades que es volen consultar. 
     * @return - quilos de residus totals per habitant, retorna 0 si no és positiva
     */
    public double quilosResidusComarca(String comarca, int any) {
        double residusTotals = 0;
        int poblacio = 0; 

        for(int i = 0; i < nElem; i++) {
            // Seleccionem els registres de la comarca i de l'any indicats.
            if(llista[i].getComarca().equalsIgnoreCase(comarca)) {
                if(llista[i].getAny() == any) {
                    // Acumulem les tones de residus i el nombre d'habitants.
                    residusTotals += llista[i].getGenerResidusMunicipalsTotals();
                    poblacio += llista[i].getPoblacio();
                }
            }
        }
        // Només calculem la proporció si la població és positiva.
        if(poblacio > 0) {
            // Convertim les tones a quilos i dividim pel total d'habitants.
            residusTotals = residusTotals * 1000;
            residusTotals = residusTotals / poblacio;
            return residusTotals;
        }
        // No hi ha població suficient per calcular la proporció.
        return 0;
    }
    
    /**
     * Elimina tots els registres d'un municipi de la llista.
     *
     * @param municipi nom del municipi del qual s'eliminen els registres.
     */
    public void esborrarDadesMunicipi(String municipi) {
        for (int i = 0; i < nElem; i++) {
            if (llista[i].getMunicipi().equalsIgnoreCase(municipi)) {
                for (int j = i; j < nElem - 1; j++) {
                    llista[j] = llista[j + 1];
                }
                nElem--;
                i--;
            }
        }
    }
}