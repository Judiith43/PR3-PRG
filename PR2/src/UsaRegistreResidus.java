
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class UsaRegistreResidus {
	static Scanner teclat = new Scanner(System.in);

	public static void main(String[] args) throws FileNotFoundException {
		int liniaIni, liniaFi;

		System.out.println("Indica les línies a llegir del fitxer");
		System.out.println("Quina és la primera línia a llegir del fitxer (valor >=1)");
		liniaIni = Integer.parseInt(teclat.nextLine());
		System.out.println("Quina és l'última línia a llegir del fitxer (valor <=10429)");
		liniaFi = Integer.parseInt(teclat.nextLine());

		String[] dataset = llegirLiniesFitxer("ResidusMunicipals.csv",liniaIni, liniaFi);

		// Completar el codi a partir d'aquí
        // Creem la llista i carreguem els registres del fitxer
        LlistaRegistreResidus llista = new LlistaRegistreResidus(dataset.length);
        for(int i = 0; i < dataset.length; i++) {
            // Separem els camps de cada línia
            String[] camps = dataset[i].split(";");
            int any = Integer.parseInt(camps[0]);
            String municipi = camps[1];
            String comarca = camps[2];
            // Eliminem els punts de milers i adaptem el separador decimal
            int poblacio = Integer.parseInt(camps[3].replace(".", ""));
            double materiaOrganica = Double.parseDouble(camps[4].replace(".", "").replace(",", "."));
            double podaJardineria = Double.parseDouble(camps[5].replace(".", "").replace(",", "."));
            double paperCartró = Double.parseDouble(camps[6].replace(".", "").replace(",", "."));
            double vidre = Double.parseDouble(camps[7].replace(".", "").replace(",", "."));
            double envasosLleugers = Double.parseDouble(camps[8].replace(".", "").replace(",", "."));
            double totalRecollidaSelect = Double.parseDouble(camps[9].replace(".", "").replace(",", "."));
            double sumaFraccioResta = Double.parseDouble(camps[10].replace(".", "").replace(",", "."));
            double generResidusMunicipalsTotals = Double.parseDouble(camps[11].replace(".", "").replace(",", "."));
            

            RegistreResidus registre = new RegistreResidus(any, municipi, comarca, poblacio, materiaOrganica, podaJardineria, paperCartró, vidre, envasosLleugers, totalRecollidaSelect, sumaFraccioResta, generResidusMunicipalsTotals);
            llista.afegir(registre);
        }
		int opcio = 0;
        do { 
            mostrarMenu();
            try {
                String entrada = teclat.nextLine();
                if(entrada.isEmpty()) {
                    opcio = 0;
                } else {
                    opcio = Integer.parseInt(entrada);
                }

                switch(opcio) {
                    case 1:
                        System.out.println("==== Mostrar tots els registres de la llista ====");
                        System.out.println(llista.toString());
                        System.out.println("=================================================");
						break;

                    case 2: 
                        System.out.println("==== Eliminar els registres d’un municipi ====");
                        System.out.println("Introdueix nom del municipi a eliminar");
                        String nomEliminar = teclat.nextLine();

						System.out.println("\n==== DADES ABANS D'ELIMINAR ===="); //mostrem dades del municipi 
                        LlistaRegistreResidus dadesAbans = llista.registresMunicipis(nomEliminar);
                        System.out.println(dadesAbans.toString());

                        llista.esborrarDadesMunicipi(nomEliminar);
                        System.out.println("\n~~~ S'han eliminat els registres de '" + nomEliminar + "' ~~~~\n");

                        System.out.println("\n==== DADES DESPRÉS D'ELIMINAR ===="); //mostrem dades del municipi
                        LlistaRegistreResidus dadesDespres = llista.registresMunicipis(nomEliminar);
                        System.out.println(dadesDespres.toString());

						System.out.println("=================================================");
                        break;

                    case 3:
                        System.out.println("==== Mostrar el municipi amb més quilos de matèria orgànica per habitant en un any ====");
                        System.out.println("Introdueix l'any a consultar");
                        int anyConsulta = Integer.parseInt(teclat.nextLine());

                        RegistreResidus registreMesOrganic = llista.mesKgFracOrganicaAny(anyConsulta);

                        if(registreMesOrganic != null) {
                            System.out.println("El municipi amb més quilos de matèria orgànica per habitant en l'any " + anyConsulta + " és: " + registreMesOrganic);
                        } else {
                            System.out.println("No s'han trobat registres per l'any " + anyConsulta);
                        }

                        System.out.println("=================================================");
                        break;

                        case 4: 
                        System.out.println("==== Mostrar els registres d’un municipi de tots els anys ====");
                        System.out.println("Introdueix nom del municipi a consultar");
                        String nomConsultar = teclat.nextLine();

                        LlistaRegistreResidus dadesConsultades = llista.registresMunicipis(nomConsultar);
                        System.out.println(dadesConsultades.toString());
                        System.out.println("=================================================");
                        break;

                        case 5: 
                        System.out.println("==== Mostrar els municipis que superen un límit de quilos de recollida selectiva per habitant ====");
                        System.out.println("Introdueix el límit de quilos per habitant");
                        double limitQuilos = Double.parseDouble(teclat.nextLine().replace(",", "."));

                        String[] nomsMunicipis = llista.municMesQuilosRecollida(limitQuilos);

                        if(nomsMunicipis.length == 0) {
                            System.out.println("No s'ha trobat cap");

                        } else {
                        for(String nom : nomsMunicipis) {
                            System.out.println("- " + nom);
                            }
                        }
                        System.out.println("=================================================");
                        break; 

                        case 6: 
                        System.out.println("==== Mostrar el primer registre d'un municipi que no hagi reciclat envasos ====");
                        RegistreResidus registreSenseEnvasos = llista.municipiNoReciclatEnvasos();
                        if(registreSenseEnvasos != null) {
                            System.out.println("El primer registre sense reciclatge d'envasos és: " + registreSenseEnvasos);
                        } else {
                            System.out.println("No s'han trobat registres de cap municipi");
                        }
                        System.out.println("=================================================");
                        break;

                        case 7: 
                        System.out.println("==== Mostrar la variació d'envasos reciclats d'un municipi ====");
                        System.out.println("Introdueix nom del municipi a consultar");
                        String nomVariacio = teclat.nextLine();

                        double variacioEnvasos = llista.variacioEnvasos(nomVariacio);
                        System.out.println("La variacio d'envasos de " + nomVariacio + " es de " + variacioEnvasos + " tones.");
                        System.out.println("=================================================");
                        break;

                        case 8:
                        System.out.println("==== Mostrar els quilos de residus totals per habitant d'una comarca en un any ====");
                        System.out.println("Introdueix l'any a consultar");
                        int anyComarca = Integer.parseInt(teclat.nextLine());
                        System.out.println("Introdueix nom de la comarca a consultar");
                        String nomComarca = teclat.nextLine();

                        double quilosPerHabitant = llista.quilosResidusComarca(nomComarca, anyComarca);
                        System.out.println("Els residus totals generats a la comarca de " + nomComarca + " l'any " + anyComarca 
                                            + " son de " + quilosPerHabitant + " kg per habitant.");
                        System.out.println("=================================================");
                        break;

                        case 9:
                        System.out.println("Adeu!");
                		break;

                        default:
                            if(opcio != 0) { // no mostrar error
                            System.out.println("Opció no vàlida. Tria entre 1 i 9.");
                            }
                            break;
                        }
                    } catch (NumberFormatException e) {
					System.err.println("Error: Has d'introduir un numero valid (1-9).");
					opcio = 0; 
					}
                } while (opcio != 9);
				teclat.close();
	}

	private static String[] llegirLiniesFitxer(String nomFitxer, int liniaIni, int liniaFi) throws FileNotFoundException {
		String[] result;
		String dada;
		int nLinies;
		if (liniaIni < 1)
			liniaIni = 1;
		if (liniaFi > 10429)
			liniaFi = 10429;
		nLinies = liniaFi - liniaIni + 1;
		result = new String[nLinies];
		Scanner f = new Scanner(new File(nomFitxer));

		String capcalera = f.nextLine();
		System.out.println("El format de les dades en cada línia és el següent\n" + capcalera);
		// ens saltem les línies fins a la liniaIni
		for (int i = 1; i < liniaIni; i++) {
			dada = f.nextLine();
		}
		// ara anirem guardant les nLinies que sí volem llegir
		for (int i = 0; i < nLinies; i++) {
			result[i] = f.nextLine();
		}
		f.close();
		return result;
	}

    /**
     * Mostra les opcions disponibles del menú principal.
     */
    private static void mostrarMenu() {
        System.out.println("\n==== MENÚ PRINCIPAL ====");
        System.out.println("1. Mostrar tots els registres de la llista.");
        System.out.println("2. Eliminar els registres d'un municipi.");
        System.out.println("3. Mostrar el municipi amb més quilos de matèria orgànica per habitant en un any.");
        System.out.println("4. Mostrar els registres d'un municipi de tots els anys.");
        System.out.println("5. Mostrar els municipis que superen un límit de quilos de recollida selectiva per habitant.");
        System.out.println("6. Mostrar el primer registre d'un municipi que no hagi reciclat envasos.");
        System.out.println("7. Mostrar la variació d'envasos reciclats d'un municipi.");
        System.out.println("8. Mostrar els quilos de residus totals per habitant d'una comarca en un any.");
        System.out.println("9. Sortir.");
        System.out.print("Tria una opció (1-9): ");
    }

}
