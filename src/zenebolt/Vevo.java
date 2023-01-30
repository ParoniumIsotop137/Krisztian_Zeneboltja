package zenebolt;

public class Vevo {

	private Integer id;
	private String felhasznaloNev;
	private String jelszo;
	private int egyenleg;

	public Vevo(Integer id, String felhasznaloNev, String jelszo, int egyenleg) {
		setId(id);
		this.felhasznaloNev = felhasznaloNev;
		this.jelszo = jelszo;
		this.egyenleg = egyenleg;
	}

	public Vevo(String felhasznaloNev, String jelszo, int egyenleg) {

		this.felhasznaloNev = felhasznaloNev;
		setJelszo(jelszo);
		this.egyenleg = egyenleg;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		if (this.id == null) {
			this.id = id;
		} else {
			throw new IllegalArgumentException("Az ID csak egyszer kaphat értéket!");
		}
	}

	public String getFelhasznaloNev() {
		return felhasznaloNev;
	}

	public void setFelhasznaloNev(String felhasznaloNev) {
		this.felhasznaloNev = felhasznaloNev;
	}

	public String getJelszo() {
		return jelszo;
	}

	public void setJelszo(String jelszo) {

		if (Jelszo.JelszoEllenorzes(jelszo)) {
			this.jelszo = Jelszo.Jelszotitkositas(jelszo);
		} else {
			throw new IllegalArgumentException(
					"A jelszónak legalább 8 karakter hosszúnak kell lennie, tartalmaznia kell kisbetűt, nagybetűt és számjegyet is!");
		}

	}

	public int getEgyenleg() {
		return egyenleg;
	}

	public void setEgyenleg(int egyenleg) {
		this.egyenleg = egyenleg;
	}

	@Override
	public String toString() {
		return "Vevő azonosító: " + this.id + ", felhasználónév: " + this.felhasznaloNev + ", jelszó: " + this.jelszo
				+ ", egyenleg: " + this.egyenleg;
	}

}
