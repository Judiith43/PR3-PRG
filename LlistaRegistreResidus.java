public class LlistaRegistreResidus {
    private RegistreResidus[] llista;
    private int nElem;

    public LlistaRegistreResidus (int capacitat) {
        llista = new RegistreResidus[capacitat];
        nElem = 0; 
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
    
}