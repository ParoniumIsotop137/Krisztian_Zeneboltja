package zenebolt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class BejelentKezoAblak extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtUser;
	private JPasswordField pwMezo;
	private Vevo vevo;

	private ABKezeloVevok kezeloVevok;
	private String connectionURL = "jdbc:mysql://localhost:3306/Regisztracio_db?useSSL=false";
	private String user = "root";

	private String password = "Plutonium-36";

	public Vevo getVevo() {
		return vevo;
	}

	/**
	 * Launch the application.
	 */

	/**
	 * Create the dialog.
	 * 
	 * @param kezeloVevok
	 */
	public BejelentKezoAblak(ABKezeloVevok kezeloVevok) {

		this.kezeloVevok = kezeloVevok;

		setModal(true);
		setForeground(new Color(139, 69, 19));
		setFont(new Font("Consolas", Font.BOLD, 13));
		setTitle("Bejelentkezés");
		setBounds(100, 100, 450, 400);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(245, 222, 179));
		contentPanel.setForeground(new Color(255, 127, 80));
		contentPanel.setBorder(new LineBorder(new Color(244, 164, 96), 7));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblUdvozlet = new JLabel("Üdvözöljök Krisztián ");
		lblUdvozlet.setHorizontalAlignment(SwingConstants.CENTER);
		lblUdvozlet.setForeground(new Color(65, 105, 225));
		lblUdvozlet.setFont(new Font("MS Gothic", Font.BOLD | Font.ITALIC, 15));
		lblUdvozlet.setBounds(60, 15, 300, 25);
		contentPanel.add(lblUdvozlet);

		JLabel lblUdvozletAlso = new JLabel("online zeneboltjában!");
		lblUdvozletAlso.setHorizontalAlignment(SwingConstants.CENTER);
		lblUdvozletAlso.setForeground(new Color(65, 105, 225));
		lblUdvozletAlso.setFont(new Font("MS Gothic", Font.BOLD | Font.ITALIC, 15));
		lblUdvozletAlso.setBounds(60, 51, 300, 25);
		contentPanel.add(lblUdvozletAlso);

		JLabel lblBejelenkezes = new JLabel("Felhasználónév:");
		lblBejelenkezes.setBackground(new Color(255, 250, 205));
		lblBejelenkezes.setForeground(new Color(100, 149, 237));
		lblBejelenkezes.setHorizontalAlignment(SwingConstants.CENTER);
		lblBejelenkezes.setFont(new Font("Segoe Print", Font.BOLD | Font.ITALIC, 13));
		lblBejelenkezes.setBounds(142, 107, 150, 25);
		contentPanel.add(lblBejelenkezes);

		txtUser = new JTextField();
		txtUser.setFont(new Font("Segoe Print", Font.PLAIN, 12));
		txtUser.setBounds(155, 143, 120, 25);
		contentPanel.add(txtUser);
		txtUser.setColumns(10);

		JLabel lblJelszo = new JLabel("Jelszó:");
		lblJelszo.setHorizontalAlignment(SwingConstants.CENTER);
		lblJelszo.setForeground(new Color(100, 149, 237));
		lblJelszo.setFont(new Font("Segoe Print", Font.BOLD | Font.ITALIC, 13));
		lblJelszo.setBackground(new Color(255, 250, 205));
		lblJelszo.setBounds(142, 179, 150, 25);
		contentPanel.add(lblJelszo);

		pwMezo = new JPasswordField();
		pwMezo.setForeground(new Color(25, 25, 112));
		pwMezo.setFont(new Font("Segoe Print", Font.BOLD, 12));
		pwMezo.setBounds(155, 215, 120, 25);
		contentPanel.add(pwMezo);

		JButton btnBelepes = new JButton("Bejelentkezés");
		btnBelepes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (!txtUser.getText().isEmpty() && !new String(pwMezo.getPassword()).isEmpty()) {
					Ellenorzes();
				} else {
					JOptionPane.showMessageDialog(null, "Felhasználónév/jelszó megadása szükséges!", "Figyelmeztetés",
							JOptionPane.WARNING_MESSAGE);
				}

			}
		});
		btnBelepes.setBackground(new Color(222, 184, 135));
		btnBelepes.setForeground(new Color(139, 69, 19));
		btnBelepes.setFont(new Font("Segoe UI Emoji", Font.BOLD | Font.ITALIC, 13));
		btnBelepes.setBounds(155, 259, 120, 30);
		contentPanel.add(btnBelepes);

		JButton btnRegisztracio = new JButton("Regisztráció");
		btnRegisztracio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Regisztracio();

			}
		});
		btnRegisztracio.setForeground(new Color(95, 158, 160));
		btnRegisztracio.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		btnRegisztracio.setBackground(new Color(222, 184, 135));
		btnRegisztracio.setBounds(23, 305, 120, 30);
		contentPanel.add(btnRegisztracio);
	}

	private void Regisztracio() {

		RegisztraciosAblak regAblak = new RegisztraciosAblak(kezeloVevok);
		regAblak.setVisible(true);

	}

	/*
	 * private void Csatlakozas() {
	 * 
	 * try { kezelo = new ABKezeloVevok(connectionURL, user, password); } catch
	 * (SQLException e) { JOptionPane.showMessageDialog(null, e.getMessage(),
	 * "Hiba!", JOptionPane.ERROR_MESSAGE); }
	 * 
	 * }
	 */
	private void Ellenorzes() {

		try {

			vevo = kezeloVevok.Belepes(txtUser.getText(), Jelszo.Jelszotitkositas(new String(pwMezo.getPassword())));

			if (vevo == null) {
				JOptionPane.showMessageDialog(null, "Helytelen felhasználónév és/vagy jelszó!", "Belépés megtagadva!",
						JOptionPane.WARNING_MESSAGE);
			} else {
				dispose();
				kezeloVevok.Lekapcsolodas();
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Hiba!", JOptionPane.ERROR_MESSAGE);
		}

	}
}
