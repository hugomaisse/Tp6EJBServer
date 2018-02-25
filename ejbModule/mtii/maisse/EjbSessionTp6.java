package mtii.maisse;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import javax.ejb.Stateless;

/**
 * Session Bean implementation class EjbSessionTp6
 */
@Stateless
public class EjbSessionTp6 implements EjbSessionTp6Remote {
	private static ArrayList<BulletinMeteo> bulletinsMeteo = new ArrayList<BulletinMeteo>();
    /**
     * Default constructor. 
     */
    public EjbSessionTp6() {
        // TODO Auto-generated constructor stub
    	EjbSessionTp6.genererUnHistorique();
    }

	@Override
	public String afficherBulletincourant() {
		String message = "Bulletin actuel:\n"+ this.getBulletin_courant().toString()+"\n";
		return message;
	}
	/*public String afficherBulletincourant(){
		//this.setMessage("Bulletin actuel:\t"+	this.getBulletin_courant().toString() + "\n");
		String message = "Bulletin actuel:\n"+ this.getBulletin_courant().toString()+"\n";
		return message;
	}
	*/
	
	//=========================Méthode=====================================
	public static void genererUnHistorique() {

		String[] tempsQuilFait = {"Grand beau temps", "Pluie", "Quelques averses",
				"Brouillard givrant", "Vent fort", "Nuageux"};

		String[] temperatures = {"Doux", "Chaud", "Froid", "De saison"};

		String[] geoZones = {"Annecy", "Paris", "Lyon", "Chambery"};

		int randomTempsQuilFaitNum, randomTemperaturesNum, randomGeoZonesNum;

		BulletinMeteo bulletin;

		String avis;

		for (int i = 0; i < 10; i++) {

			randomTempsQuilFaitNum = ThreadLocalRandom.current().nextInt(0,
					tempsQuilFait.length);
			randomTemperaturesNum = ThreadLocalRandom.current().nextInt(0,
					temperatures.length);
			randomGeoZonesNum = ThreadLocalRandom.current().nextInt(0,
					geoZones.length);
			avis = tempsQuilFait[randomTempsQuilFaitNum] +
					" - " + temperatures[randomTemperaturesNum];
			bulletin = new BulletinMeteo(avis);
			bulletin.setZone_geo(geoZones[randomGeoZonesNum]);
			bulletinsMeteo.add(bulletin);
		}

	}
	public void afficherBulletins() {
		System.out.println("===== Historique des bulletins meteo =====\n");
		for (BulletinMeteo bulletin : this.bulletinsMeteo) { // this.bulletinsMeteo est la collection d'objets
			System.out.println(bulletin.toString()); // element joue
		}
	}



	// Modifications exigees
	public BulletinMeteo getBulletin_courant() {
		int nbBulletins = this.nbBulletins();
		if (nbBulletins >= 1)
			// le dernier se trouve a la place (la taille de la collection - 1)
			return this.bulletinsMeteo.get(nbBulletins-1);  
		else
			return null;
	}

	// apres question 15
	public void ajouterBulletin(BulletinMeteo bulletin) {
		if (!this.bulletinsMeteo.contains(bulletin))
			this.bulletinsMeteo.add(bulletin);
	}

	public int nbBulletins() {
		return this.bulletinsMeteo.size();
	}
	public BulletinMeteo rechercherBulletin(String zoneG) {
		BulletinMeteo trouve = null;
		int i = 0;
		while (trouve == null && i < this.bulletinsMeteo.size()) {
			trouve = this.bulletinsMeteo.get(i);
			if (trouve.getZone_geo() != zoneG)
				trouve = null;
			i++;
		}
		return trouve;
	}

}
