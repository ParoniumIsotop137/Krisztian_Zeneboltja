package zenebolt;

import java.time.LocalDate;

public class Album {

	private Integer id;
	private String albumCime;
	private int megjelenesEve;
	private String kiadoNeve;
	private int ar;

	public Album(Integer id, String albumCime, int megjelenesEve, String kiadoNeve, int ar) {
		setId(id);
		this.albumCime = albumCime;
		this.megjelenesEve = megjelenesEve;
		this.kiadoNeve = kiadoNeve;
		this.ar = ar;
	}

	public Album(String albumCime, int megjelenesEve, String kiadoNeve, int ar) {
		setAlbumCime(albumCime);
		setMegjelenesEve(megjelenesEve);
		setKiadoNeve(kiadoNeve);
		setAr(ar);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		if (this.id == null) {
			this.id = id;
		} else {
			throw new IllegalStateException("Az ID csak egyszer kaphat értéket!");
		}

	}

	public String getAlbumCime() {
		return albumCime;
	}

	public void setAlbumCime(String albumCime) {

		if (albumCime != null && !albumCime.isBlank()) {
			this.albumCime = albumCime;
		} else {
			throw new IllegalArgumentException("Adja meg az album címét!");
		}

	}

	public int getMegjelenesEve() {
		return megjelenesEve;
	}

	public void setMegjelenesEve(int megjelenesEve) {

		if (megjelenesEve > 1990 && megjelenesEve <= LocalDate.now().getYear()) {
			this.megjelenesEve = megjelenesEve;
		} else {
			throw new IllegalArgumentException("A megjelenés éve 1990-től a jelenlegi évig bezárólag adható meg!");
		}
	}

	public String getKiadoNeve() {
		return kiadoNeve;
	}

	public void setKiadoNeve(String kiadoNeve) {

		if (kiadoNeve != null && !kiadoNeve.isBlank()) {
			this.kiadoNeve = kiadoNeve;
		} else {
			throw new IllegalArgumentException("Adja meg az album kiadójának nevét!");
		}
	}

	public int getAr() {
		return ar;
	}

	public void setAr(int ar) {

		if (ar >= 0) {
			this.ar = ar;
		} else {
			throw new IllegalArgumentException("Az ár nem lehet negatív szám!");
		}
	}

	@Override
	public String toString() {
		return "Album címe: " + this.albumCime + ", megjelenés éve: " + String.valueOf(this.megjelenesEve)
				+ ", kiadó neve: " + this.kiadoNeve + ", ára: " + String.valueOf(this.ar) + "Ft";
	}

}
