package zenebolt;

import java.security.MessageDigest;

import javax.swing.JOptionPane;

public class Jelszo {

	public static boolean JelszoEllenorzes(String jelszo) {

		if (jelszo.length() >= 8 && !jelszo.equals(jelszo.toLowerCase()) && !jelszo.equals(jelszo.toUpperCase())) {
			for (int i = 0; i < 10; i++) {
				if (jelszo.contains(String.valueOf(i))) {
					return true;
				}

			}
		}

		return false;
	}

	public static String Jelszotitkositas(String jelszo) {

		String eredmeny = "";

		try {

			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(jelszo.getBytes("UTF-8"));

			eredmeny = bytestoHex(hash);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Sikertelen titkosítás!", "Hiba", JOptionPane.ERROR_MESSAGE);
		}

		return eredmeny;
	}

	private static String bytestoHex(byte[] hash) {

		StringBuilder hexString = new StringBuilder(2 * hash.length);

		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}

		return hexString.toString();
	}

}
