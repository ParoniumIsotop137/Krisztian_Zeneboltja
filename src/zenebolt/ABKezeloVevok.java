package zenebolt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ABKezeloVevok {

	private static Connection conn;
	private static PreparedStatement stm;

	public ABKezeloVevok(String connectionURL, String userName, String password) throws SQLException {

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

	public void Regisztracio(Vevo vevo) throws SQLException {

		try {
			stm = conn.prepareStatement("Insert into Vevok (FelhasznaloNev, Jelszo, Egyenleg) values (?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			stm.setString(1, vevo.getFelhasznaloNev());
			stm.setString(2, vevo.getJelszo());
			stm.setInt(3, vevo.getEgyenleg());

			stm.executeUpdate();

			ResultSet rs = stm.getGeneratedKeys();

			if (rs.next()) {
				vevo.setId(rs.getInt(1));
			}

			stm.clearParameters();

		} catch (Exception e) {
			if (e.getMessage().contains("Duplicate")) {
				throw new SQLException("Létező felhasználónév!");
			} else {
				throw new SQLException("Adatbázis hiba! Az adatok rögzítése sikertelen volt: " + e.getMessage());
			}

		}

	}

	public Vevo Belepes(String user, String password) throws SQLException {

		try {
			stm = conn.prepareStatement("Select * from Vevok where FelhasznaloNev=? and Jelszo=?");

			stm.setString(1, user);
			stm.setString(2, password);

			ResultSet rs = stm.executeQuery();

			if (rs.next()) {

				Vevo vevo = new Vevo(rs.getInt("ID"), rs.getString("FelhasznaloNev"), rs.getString("Jelszo"),
						rs.getInt("Egyenleg"));
				return vevo;
			}

		} catch (Exception e) {
			throw new SQLException("Adatbázis hiba! Az adatok ellenőrzése sikertelen volt: " + e.getMessage());
		}
		return null;

	}

	public void AdatModositas(Vevo vevo) throws SQLException {

		try {

			stm = conn.prepareStatement("Update Vevok set Egyenleg=? where ID=?");
			stm.setInt(1, vevo.getEgyenleg());
			stm.setInt(2, vevo.getId());

			stm.executeUpdate();

			stm.clearParameters();

		} catch (Exception e) {
			throw new SQLException("Adatbázis hiba! Az adatok módosítás sikertelen volt: " + e.getMessage());
		}

	}

}
