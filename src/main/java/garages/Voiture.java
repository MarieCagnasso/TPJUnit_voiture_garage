package garages;

import java.io.PrintStream;
import java.util.*;

public class Voiture {

	private final String immatriculation;
	private final List<Stationnement> myStationnements = new LinkedList<>();

	public Voiture(String i) {
		if (null == i) {
			throw new IllegalArgumentException("Une voiture doit avoir une immatriculation");
		}

		immatriculation = i;
	}

	public String getImmatriculation() {
		return immatriculation;
	}

	/**
	 * Fait rentrer la voiture au garage 
         * Précondition : la voiture ne doit pas être déjà dans un garage
	 *
	 * @param g le garage où la voiture va stationner
	 * @throws java.lang.Exception Si déjà dans un garage
	 */
	public void entreAuGarage(Garage g) throws Exception {
		// Et si la voiture est déjà dans un garage ?

		if (this.estDansUnGarage()){
			throw new IllegalArgumentException("La voiture est déjà dans un garage");
		}

		Stationnement s = new Stationnement(this, g);
		myStationnements.add(s);
	}

	/**
	 * Fait sortir la voiture du garage 
         * Précondition : la voiture doit être dans un garage
	 *
	 * @throws java.lang.Exception si la voiture n'est pas dans un garage
	 */
	public void sortDuGarage() throws Exception {
		// Trouver le dernier stationnement de la voiture
		// Terminer ce stationnement
		if (!estDansUnGarage()){
			throw new IllegalArgumentException("La voiture n'est pas dans un garage.");
		}
		for(Stationnement s : this.myStationnements ) {
			if (s.estEnCours()) {
				s.terminer();
			}
		}
	}

	/**
	 * @return l'ensemble des garages visités par cette voiture
	 */
	public Set<Garage> garagesVisites() {
		Set<Garage> list = new HashSet<Garage>();
		for (Stationnement s : myStationnements){
			list.add(s.getGarage());
		}
		return list;
	}

	/**
	 * @return vrai si la voiture est dans un garage, faux sinon
	 */
	public boolean estDansUnGarage() {

		boolean res = false;
		if (!myStationnements.isEmpty()){
			res = myStationnements.get(myStationnements.size()-1).estEnCours();
		}
		return res;
	}

	/**
	 * pour un garage donne la liste des stationnements
	 * @param g Garage
	 * @return List de stationnement
	 */
	public List<Stationnement> stationnementsDansGarage(Garage g){
		List<Stationnement> list = new ArrayList<>();
		for (Stationnement s : myStationnements){
			if (s.getGarage() == g){
				list.add(s);
			}
		}
		return list;
	}

	/**
	 * Pour chaque garage visité, imprime le nom de ce garage suivi de la liste des dates d'entrée / sortie dans ce
	 * garage
	 * <br>Exemple :
	 * <pre>
	 * Garage Castres:
	 *		Stationnement{ entree=28/01/2019, sortie=28/01/2019 }
	 *		Stationnement{ entree=28/01/2019, en cours }
	 *  Garage Albi:
	 *		Stationnement{ entree=28/01/2019, sortie=28/01/2019 }
	 * </pre>
	 *
	 * @param out l'endroit où imprimer (ex: System.out)
	 */
	public void imprimeStationnements(PrintStream out) {
		Set<Garage> garages = garagesVisites();

		for (Garage g : garages){
			out.append(g.toString());
			for (Stationnement s : stationnementsDansGarage(g)){
				out.append(s.toString());
			}
		}

	}

}
