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
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class KeszletKezelo extends JDialog {

	public Album getAlbum() {
		return album;
	}

	public boolean isRendben() {
		return rendben;
	}

	private final JPanel contentPanel = new JPanel();
	private JTextField txtAlbumCime;
	private JSpinner spnMegjelnesEve;
	private JSpinner spnAr;
	private JTextField txtKiadoNeve;

	private Album album;
	private boolean rendben;

	private ABKezeloBolt kezelo;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the dialog.
	 */
	public KeszletKezelo(ABKezeloBolt kezelo) {

		this.kezelo = kezelo;

		setModal(true);
		setTitle("Készlet kezelés");
		setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 13));
		setBounds(200, 150, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(176, 196, 222));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblAlbumCime = new JLabel("Album címe:");
		lblAlbumCime.setForeground(new Color(205, 133, 63));
		lblAlbumCime.setHorizontalAlignment(SwingConstants.CENTER);
		lblAlbumCime.setFont(new Font("Segoe Print", Font.BOLD, 14));
		lblAlbumCime.setBounds(30, 20, 140, 25);
		contentPanel.add(lblAlbumCime);

		JLabel lblMegjelenesEve = new JLabel("Megjelenés éve:");
		lblMegjelenesEve.setHorizontalAlignment(SwingConstants.CENTER);
		lblMegjelenesEve.setForeground(new Color(205, 133, 63));
		lblMegjelenesEve.setFont(new Font("Segoe Print", Font.BOLD, 14));
		lblMegjelenesEve.setBounds(40, 56, 140, 25);
		contentPanel.add(lblMegjelenesEve);

		JLabel lblKiado = new JLabel("Kiadó neve:");
		lblKiado.setHorizontalAlignment(SwingConstants.CENTER);
		lblKiado.setForeground(new Color(205, 133, 63));
		lblKiado.setFont(new Font("Segoe Print", Font.BOLD, 14));
		lblKiado.setBounds(30, 97, 140, 25);
		contentPanel.add(lblKiado);

		JLabel lblAr = new JLabel("Album ára:");
		lblAr.setHorizontalAlignment(SwingConstants.CENTER);
		lblAr.setForeground(new Color(205, 133, 63));
		lblAr.setFont(new Font("Segoe Print", Font.BOLD, 14));
		lblAr.setBounds(30, 133, 140, 25);
		contentPanel.add(lblAr);

		txtAlbumCime = new JTextField();
		txtAlbumCime.setBounds(195, 20, 200, 25);
		contentPanel.add(txtAlbumCime);
		txtAlbumCime.setColumns(10);

		spnMegjelnesEve = new JSpinner();
		spnMegjelnesEve.setModel(
				new SpinnerNumberModel(Integer.valueOf(1990), Integer.valueOf(1990), null, Integer.valueOf(1)));
		spnMegjelnesEve.setForeground(new Color(65, 105, 225));
		spnMegjelnesEve.setFont(new Font("Sitka Heading", Font.BOLD, 12));
		spnMegjelnesEve.setBounds(195, 60, 100, 25);
		contentPanel.add(spnMegjelnesEve);

		spnAr = new JSpinner();
		spnAr.setModel(new SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
		spnAr.setForeground(new Color(65, 105, 225));
		spnAr.setFont(new Font("Sitka Heading", Font.BOLD, 12));
		spnAr.setBounds(195, 136, 100, 25);
		contentPanel.add(spnAr);

		txtKiadoNeve = new JTextField();
		txtKiadoNeve.setColumns(10);
		txtKiadoNeve.setBounds(195, 100, 200, 25);
		contentPanel.add(txtKiadoNeve);

		JButton btnMegsem = new JButton("Mégsem");
		btnMegsem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				dispose();

			}
		});
		btnMegsem.setBackground(new Color(222, 184, 135));
		btnMegsem.setFont(new Font("Segoe Script", Font.BOLD | Font.ITALIC, 12));
		btnMegsem.setForeground(new Color(255, 69, 0));
		btnMegsem.setBounds(62, 194, 100, 30);
		contentPanel.add(btnMegsem);

		JButton btnRendben = new JButton("Rendben");
		btnRendben.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (!txtAlbumCime.getText().isEmpty() && !txtKiadoNeve.getText().isEmpty()) {

					AdatRogzites();

				} else {
					JOptionPane.showMessageDialog(null, "Nem töltött ki minden mezőt!", "Figyelmeztetés",
							JOptionPane.WARNING_MESSAGE);
				}

			}
		});
		btnRendben.setForeground(new Color(255, 69, 0));
		btnRendben.setFont(new Font("Segoe Script", Font.BOLD | Font.ITALIC, 12));
		btnRendben.setBackground(new Color(222, 184, 135));
		btnRendben.setBounds(226, 194, 100, 30);
		contentPanel.add(btnRendben);
	}

	public KeszletKezelo(ABKezeloBolt kezelo, Album album) {

		this(kezelo);
		this.album = album;
		txtAlbumCime.setText(album.getAlbumCime());
		spnMegjelnesEve.setValue(album.getMegjelenesEve());
		txtKiadoNeve.setText(album.getKiadoNeve());
		spnAr.setValue(album.getAr());

	}

	private void AdatRogzites() {

		try {
			if (album == null) {

				album = new Album(txtAlbumCime.getText(), ((int) (spnMegjelnesEve.getValue())), txtKiadoNeve.getText(),
						((int) (spnAr.getValue())));

				kezelo.AdatFelvitel(album);
				rendben = true;
				dispose();

			} else {

				album.setAlbumCime(txtAlbumCime.getText());
				album.setMegjelenesEve((int) spnMegjelnesEve.getValue());
				album.setKiadoNeve(txtKiadoNeve.getText());
				album.setAr((int) spnAr.getValue());

				kezelo.AdatModositas(album);
				rendben = true;
				dispose();

			}

		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Figyelmeztetés", JOptionPane.WARNING_MESSAGE);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Hiba", JOptionPane.ERROR_MESSAGE);
		}

	}
}
