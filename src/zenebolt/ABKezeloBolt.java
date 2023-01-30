package zenebolt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ABKezeloBolt {

	private static Connection conn;

	private static PreparedStatement stm;

	public ABKezeloBolt(String connectionURL, String userName, String password) throws SQLException {

		Kapcsolodas(connectionURL, userName, password);

	}

	private void Kapcsolodas(String connectionURL, String userName, String password) throws SQLException {

		try {
			conn = DriverManager.getConnection(connectionURL, userName, password);

		} catch (Exception e) {
			throw new SQLException("Adatbázis hiba! Sikertelen csatlakozás: " + e.getMessage());
		}

	}

	public void Lekapcsolodas() throws SQLException {
		try {

			conn.close();
		} catch (Exception e) {
			throw new SQLException("Adatbázis hiba! Sikertelen lecsatlakozás: " + e.getMessage());
		}
	}

	public List<Album> AdatBeolvasas() throws SQLException {

		List<Album> albumLista = new ArrayList<Album>();

		try {

			stm = conn.prepareStatement("Select * from Albumok");

			ResultSet rs = stm.executeQuery();

			while (rs.next()) {

				albumLista.add(new Album(rs.getInt("ID"), rs.getString("AlbumCime"), rs.getInt("MegjelenesEve"),
						rs.getString("Kiado"), rs.getInt("Ar")));

			}
			rs.close();
			stm.clearParameters();

		} catch (Exception e) {
			throw new SQLException("Az adatok beolvasása sikertelen volt: " + e.getMessage());
		}

		return albumLista;

	}

	public void AdatTorles(Album album) throws SQLException {

		try {
			stm = conn.prepareStatement("Delete from albumok where ID=?");
			stm.setInt(1, album.getId());

			stm.executeUpdate();
			stm.clearParameters();

		} catch (Exception e) {
			throw new SQLException("Az adatok törlése sikertelen volt: " + e.getMessage());
		}

	}

	public void AdatFelvitel(Album album) throws SQLException {

		try {
			stm = conn.prepareStatement("Insert into Albumok (AlbumCime, MegjelenesEve, Kiado, Ar) values (?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			stm.setString(1, album.getAlbumCime());
			stm.setInt(2, album.getMegjelenesEve());
			stm.setString(3, album.getKiadoNeve());
			stm.setInt(4, album.getAr());

			stm.executeUpdate();
			ResultSet rs = stm.getGeneratedKeys();

			if (rs.next()) {
				album.setId(rs.getInt(1));
			}

			stm.clearParameters();

		} catch (Exception e) {
			throw new SQLException("Az adatok rögzítése sikertelen volt: " + e.getMessage());
		}

	}

	public void AdatModositas(Album album) throws SQLException {
		try {

			stm = conn.prepareStatement("Update Albumok set AlbumCime=?, MegjelenesEve=?, Kiado=?, Ar=? where ID=?");
			stm.setString(1, album.getAlbumCime());
			stm.setInt(2, album.getMegjelenesEve());
			stm.setString(3, album.getKiadoNeve());
			stm.setInt(4, album.getAr());
			stm.setInt(5, album.getId());

			stm.executeUpdate();

			stm.clearParameters();

		} catch (SQLException e) {
			throw new SQLException("Az adatok módosítása sikertelen volt: " + e.getMessage());
		}
	}

}
