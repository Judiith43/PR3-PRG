
public class RegistreResidus {
    private int any;
    private String municipi;
    private String comarca;
    private int poblacio;
    private double materiaOrganica;
    private double podaJardineria;
    private double paperCarto;
    private double vidre;
    private double envasosLleugers;
    private double totalRecollidaSelect;
    private double sumaFraccioResta;
    private double generResidusMunicipalsTotals;

      /**
     * Constructor que inicialitza un registre de residus municipals.
     * Rep les dades del municipi i les quantitats de residus en tones.
     * Calcula la generació total de residus municipals sumant
     * la recollida selectiva i la fracció resta.
     *
     * @param any any al qual corresponen les dades.
     * @param muni nom del municipi.
     * @param com nom de la comarca.
     * @param pob nombre d'habitants del municipi.
     * @param matOrg quantitat de matèria orgànica en tones.
     * @param podaJar quantitat de poda i jardineria en tones.
     * @param paperCarto quantitat de paper i cartró en tones.
     * @param vidre quantitat de vidre en tones.
     * @param envLleug quantitat d'envasos lleugers en tones.
     * @param totalRec total de recollida selectiva en tones.
     * @param sumaFrac quantitat de la fracció resta en tones.
     */
    public RegistreResidus(int any, String muni, String com, int pob, double matOrg, double podaJar, double paperCarto, double vidre, double envLleug, 
                            double totalRec, double sumaFrac) {
        this.any = any;
        municipi = muni;
        comarca = com;
        poblacio = pob;
        materiaOrganica = matOrg;
        podaJardineria = podaJar;
        this.paperCarto = paperCarto;
        this.vidre = vidre;
        envasosLleugers = envLleug;
        totalRecollidaSelect = totalRec;
        sumaFraccioResta = sumaFrac;
        generResidusMunicipalsTotals = sumaFraccioResta + totalRecollidaSelect;    
    }

     /**
     * Obté l'any del registre de residus.
     *
     * @return any al qual corresponen les dades.
     */
    public int getAny() {
        return any;
    }

    /**
     * Obté el nom del municipi.
     *
     * @return String amb el nom del municipi.
     */
    public String getMunicipi() {
        return municipi;
    }

    /**
     * Obté el nom de la comarca.
     *
     * @return String amb el nom de la comarca.
     */
    public String getComarca() {
        return comarca;
    }

    /**
     * Obté la població del municipi.
     *
     * @return nombre d'habitants del municipi.
     */
    public int getPoblacio() {
        return poblacio;
    }

    /**
     * Obté la quantitat de matèria orgànica recollida.
     *
     * @return quantitat de matèria orgànica en tones.
     */
    public double getMateriaOrganica() {
        return materiaOrganica;
    }

    /**
     * Obté la quantitat de residus de poda i jardineria.
     *
     * @return quantitat de poda i jardineria en tones.
     */
    public double getPodaJardineria() {
        return podaJardineria;
    }

    /**
     * Obté la quantitat de paper i cartró recollida.
     *
     * @return quantitat de paper i cartró en tones.
     */
    public double getPaperCarto() {
        return paperCarto;
    }

    /**
     * Obté la quantitat de vidre recollida.
     *
     * @return quantitat de vidre en tones.
     */
    public double getVidre() {
        return vidre;
    }

    /**
     * Obté la quantitat d'envasos lleugers recollida.
     *
     * @return quantitat d'envasos lleugers en tones.
     */
    public double getEnvasosLleugers() {
        return envasosLleugers;
    }

    /**
     * Obté el total de residus de recollida selectiva.
     *
     * @return total de recollida selectiva en tones.
     */
    public double getTotalRecollidaSelect() {
        return totalRecollidaSelect;
    }

    /**
     * Obté la quantitat total de la fracció resta.
     *
     * @return quantitat de la fracció resta en tones.
     */
    public double getSumaFraccioResta() {
        return sumaFraccioResta;
    }

    /**
     * Obté la generació total de residus municipals.
     * Aquest valor és la suma de la recollida selectiva
     * i la fracció resta.
     *
     * @return generació total de residus municipals en tones.
     */
    public double getGenerResidusMunicipalsTotals() {
        return generResidusMunicipalsTotals;
    }

    /**
     * Crea una còpia del registre de residus actual.
     * La còpia conté les mateixes dades que el registre original,
     * però és un objecte independent.
     *
     * @return un nou objecte RegistreResidus amb les mateixes dades.
     */
    public RegistreResidus copia() {
        return new RegistreResidus(this.any, this.municipi, this.comarca, this.poblacio, this.materiaOrganica, this.podaJardineria, this.paperCarto, 
                                    this.vidre, this.envasosLleugers, this.totalRecollidaSelect, this.sumaFraccioResta);
    }

    /**
     * Compara el registre actual amb un altre objecte.
     * Dos registres són iguals si tenen el mateix municipi
     * i el mateix any.
     *
     * @param o objecte amb el qual es vol comparar.
     * @return true si tenen el mateix municipi i any;
     *         false en cas contrari.
     */
    @Override
    public boolean equals(Object o) {
        boolean iguals = false;

        if (o instanceof RegistreResidus) {
            RegistreResidus altre = (RegistreResidus) o;

            if (this.municipi.equalsIgnoreCase(altre.municipi) && this.any == altre.any) {
                iguals = true;
            }
        }

        return iguals;
    }
}

