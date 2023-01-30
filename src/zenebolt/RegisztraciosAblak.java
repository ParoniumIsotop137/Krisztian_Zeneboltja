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
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

public class RegisztraciosAblak extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtFelhasznaloNev;
	private JPasswordField pwMezo1;
	private JPasswordField pwMezo2;

	private ABKezeloVevok kezelo;
	private Vevo vevo;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the dialog.
	 */
	public RegisztraciosAblak(ABKezeloVevok kezelo) {

		this.kezelo = kezelo;

		setModal(true);
		setFont(new Font("Dialog", Font.BOLD, 13));
		setForeground(new Color(65, 105, 225));
		setTitle("Regisztráció");
		setBounds(150, 150, 420, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(255, 228, 181));
		contentPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(244, 164, 96),
				new Color(255, 255, 224), new Color(244, 164, 96), new Color(255, 255, 224)));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblFelhasznalo = new JLabel("Felhasználónév:");
			lblFelhasznalo.setHorizontalAlignment(SwingConstants.CENTER);
			lblFelhasznalo.setForeground(new Color(139, 69, 19));
			lblFelhasznalo.setFont(new Font("Verdana", Font.BOLD, 12));
			lblFelhasznalo.setBounds(28, 15, 120, 25);
			contentPanel.add(lblFelhasznalo);
		}

		JLabel lblJelszo = new JLabel("Jelszó:");
		lblJelszo.setHorizontalAlignment(SwingConstants.CENTER);
		lblJelszo.setForeground(new Color(139, 69, 19));
		lblJelszo.setFont(new Font("Verdana", Font.BOLD, 12));
		lblJelszo.setBounds(28, 75, 120, 25);
		contentPanel.add(lblJelszo);

		JLabel lblJelszo2 = new JLabel("Jelszó ismétlése:");
		lblJelszo2.setHorizontalAlignment(SwingConstants.CENTER);
		lblJelszo2.setForeground(new Color(139, 69, 19));
		lblJelszo2.setFont(new Font("Verdana", Font.BOLD, 12));
		lblJelszo2.setBounds(28, 134, 120, 25);
		contentPanel.add(lblJelszo2);

		txtFelhasznaloNev = new JTextField();
		txtFelhasznaloNev.setBackground(new Color(255, 250, 250));
		txtFelhasznaloNev.setFont(new Font("Verdana", Font.BOLD, 12));
		txtFelhasznaloNev.setBounds(213, 20, 150, 25);
		contentPanel.add(txtFelhasznaloNev);
		txtFelhasznaloNev.setColumns(10);

		pwMezo1 = new JPasswordField();
		pwMezo1.setFont(new Font("Times New Roman", Font.BOLD, 12));
		pwMezo1.setBackground(new Color(255, 250, 250));
		pwMezo1.setBounds(213, 78, 150, 25);
		contentPanel.add(pwMezo1);

		pwMezo2 = new JPasswordField();
		pwMezo2.setFont(new Font("Times New Roman", Font.BOLD, 12));
		pwMezo2.setBackground(new Color(255, 250, 250));
		pwMezo2.setBounds(213, 134, 150, 25);
		contentPanel.add(pwMezo2);

		JButton btnRegisztracio = new JButton("Regisztráció");
		btnRegisztracio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (!txtFelhasznaloNev.getText().isEmpty()) {
					if (!new String(pwMezo1.getPassword()).isEmpty() && !new String(pwMezo2.getPassword()).isEmpty()) {
						if (new String(pwMezo1.getPassword()).equals(new String(pwMezo2.getPassword()))) {
							Regisztracio();

						} else {
							JOptionPane.showMessageDialog(null, "Az jelszavak nem egyeznek!", "Figylmeztettés",
									JOptionPane.WARNING_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null,
								"A jelszó és a megismételt jelszó mező nem maradhat üresen!", "Figylmeztettés",
								JOptionPane.WARNING_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "A felhasználónév mező nem maradhat üresen!", "Figylmeztettés",
							JOptionPane.WARNING_MESSAGE);
				}

			}
		});
		btnRegisztracio.setFont(new Font("Segoe Print", Font.BOLD | Font.ITALIC, 14));
		btnRegisztracio.setForeground(new Color(139, 0, 0));
		btnRegisztracio.setBackground(new Color(244, 164, 96));
		btnRegisztracio.setBounds(231, 198, 120, 30);
		contentPanel.add(btnRegisztracio);

		JButton btnMegsem = new JButton("Mégsem");
		btnMegsem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				dispose();

			}
		});
		btnMegsem.setForeground(new Color(139, 0, 0));
		btnMegsem.setFont(new Font("Segoe Print", Font.BOLD | Font.ITALIC, 14));
		btnMegsem.setBackground(new Color(244, 164, 96));
		btnMegsem.setBounds(28, 198, 100, 30);
		contentPanel.add(btnMegsem);
	}

	private void Regisztracio() {

		try {

			vevo = new Vevo(txtFelhasznaloNev.getText(), new String(pwMezo1.getPassword()), 0);
			kezelo.Regisztracio(vevo);
			dispose();

		} catch (IllegalArgumentException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(), "Figylmeztettés", JOptionPane.WARNING_MESSAGE);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Hiba", JOptionPane.ERROR_MESSAGE);

		}

	}

}
