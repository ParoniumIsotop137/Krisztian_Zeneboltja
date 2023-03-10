package zenebolt;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class ZeneBoltFoAblak {

	private JFrame frmZenebolt;
	private JTable tblAlbumok;

	private ABKezeloBolt kezelo;
	private ABKezeloVevok kezeloVevok;
	private Album album;
	private List<Album> albumok;

	private Vevo vevo;

	private String connectionURL = "jdbc:mysql://localhost:3306/Zenebolt?allowPublicKeyRetrieval=true&useSSL=false";
	private String connectionURLVevok = "jdbc:mysql://localhost:3306/Regisztracio_db?allowPublicKeyRetrieval=true&useSSL=false";
	private String userName = "root";
	private String password = "MySQLPassword";

	private DefaultTableModel tModel;
	private JLabel lblAlbumNeve;
	private JLabel lblAlbumAra;

	/**
	 * Launch the application.
	 */

	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					ZeneBoltFoAblak window = new ZeneBoltFoAblak();
					window.frmZenebolt.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ZeneBoltFoAblak() {

		// initialize();

		try {
			kezeloVevok = new ABKezeloVevok(connectionURLVevok, userName, password);
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(frmZenebolt, e1.getMessage(), "Hiba", JOptionPane.ERROR_MESSAGE);
		}

		BejelentKezoAblak loginAblak = new BejelentKezoAblak(kezeloVevok);
		loginAblak.setVisible(true);

		if (loginAblak.getVevo() != null) {
			vevo = loginAblak.getVevo();
			initialize();
			frmZenebolt.setTitle(
					frmZenebolt.getTitle() + vevo.getFelhasznaloNev() + ", egyenleged: " + vevo.getEgyenleg() + "Ft");

			try {

				kezelo = new ABKezeloBolt(connectionURL, userName, password);

				Beolvasas();
				TablaFeltoltes();

			} catch (SQLException e) {
				JOptionPane.showMessageDialog(frmZenebolt, e.getMessage(), "Hiba", JOptionPane.ERROR_MESSAGE);
			}

		} else {
			System.exit(0);
		}

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmZenebolt = new JFrame();
		frmZenebolt.getContentPane().setBackground(new Color(255, 235, 205));
		frmZenebolt.setBackground(new Color(255, 235, 205));
		frmZenebolt.setTitle("Bel??pve: ");
		frmZenebolt.setBounds(100, 100, 750, 500);
		frmZenebolt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmZenebolt.getContentPane().setLayout(null);

		albumok = new ArrayList<Album>();

		JLabel lblMuvek = new JLabel("Kriszti??n m??vek");
		lblMuvek.setForeground(new Color(210, 105, 30));
		lblMuvek.setFont(new Font("Segoe Script", Font.BOLD, 20));
		lblMuvek.setHorizontalAlignment(SwingConstants.CENTER);
		lblMuvek.setBounds(280, 10, 200, 40);
		frmZenebolt.getContentPane().add(lblMuvek);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 55, 481, 240);
		frmZenebolt.getContentPane().add(scrollPane);

		tblAlbumok = new JTable();
		tblAlbumok.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				Kiirasok();

			}
		});
		tblAlbumok.setFont(new Font("Segoe Print", Font.BOLD, 12));
		tblAlbumok.setForeground(new Color(65, 105, 225));
		tblAlbumok.setBackground(new Color(240, 248, 255));
		scrollPane.setViewportView(tblAlbumok);

		String[] oszlopok = new String[] { "Album c??me", "Megjelen??s ??ve", "Kiad??", "??r (Ft)" };

		tModel = new DefaultTableModel(null, oszlopok);

		JLabel lblKep = new JLabel("");
		lblKep.setBounds(505, 55, 213, 240);
		lblKep.setIcon(new ImageIcon(
				Toolkit.getDefaultToolkit().getImage(ZeneBoltFoAblak.class.getResource("KrisztianJava.png"))));
		frmZenebolt.getContentPane().add(lblKep);

		JButton btnFeltoltes = new JButton("Egyenlegfelt??lt??s");
		btnFeltoltes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Feltoltes();

			}
		});
		btnFeltoltes.setForeground(new Color(255, 255, 255));
		btnFeltoltes.setBackground(new Color(25, 25, 112));
		btnFeltoltes.setFont(new Font("Segoe Script", Font.BOLD, 12));
		btnFeltoltes.setBounds(31, 322, 140, 30);
		frmZenebolt.getContentPane().add(btnFeltoltes);

		JButton btnVasarlas = new JButton("Album v??s??rl??sa");
		btnVasarlas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Vasarlas();

			}
		});
		btnVasarlas.setForeground(Color.WHITE);
		btnVasarlas.setFont(new Font("Segoe Script", Font.BOLD, 12));
		btnVasarlas.setBackground(new Color(25, 25, 112));
		btnVasarlas.setBounds(196, 322, 145, 30);
		frmZenebolt.getContentPane().add(btnVasarlas);

		lblAlbumNeve = new JLabel("");
		lblAlbumNeve.setFont(new Font("Yu Gothic UI", Font.BOLD | Font.ITALIC, 13));
		lblAlbumNeve.setHorizontalAlignment(SwingConstants.CENTER);
		lblAlbumNeve.setForeground(new Color(119, 136, 153));
		lblAlbumNeve.setBounds(10, 386, 200, 25);
		frmZenebolt.getContentPane().add(lblAlbumNeve);

		lblAlbumAra = new JLabel("");
		lblAlbumAra.setHorizontalAlignment(SwingConstants.CENTER);
		lblAlbumAra.setForeground(new Color(119, 136, 153));
		lblAlbumAra.setFont(new Font("Yu Gothic UI", Font.BOLD | Font.ITALIC, 13));
		lblAlbumAra.setBounds(240, 386, 150, 25);
		frmZenebolt.getContentPane().add(lblAlbumAra);

		JButton btnKijelentkezes = new JButton("Kijelentkez??s");
		btnKijelentkezes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Object[] opciok = new Object[] { "Igen", "Nem" };

				if ((JOptionPane.showOptionDialog(frmZenebolt, "Biztosan ki akar l??pni?", "Kijelentkez??s",
						JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciok,
						opciok[1]) == JOptionPane.YES_OPTION)) {

					frmZenebolt.dispose();

					ZeneBoltFoAblak.main(null);

				}

			}
		});
		btnKijelentkezes.setBackground(new Color(176, 196, 222));
		btnKijelentkezes.setForeground(new Color(220, 20, 60));
		btnKijelentkezes.setFont(new Font("Segoe Script", Font.BOLD, 15));
		btnKijelentkezes.setBounds(550, 345, 160, 30);
		frmZenebolt.getContentPane().add(btnKijelentkezes);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(255, 255, 224));
		frmZenebolt.setJMenuBar(menuBar);

		JMenu mnKeszlet = new JMenu("K??szlet");
		mnKeszlet.setBackground(new Color(255, 250, 205));
		mnKeszlet.setForeground(new Color(70, 130, 180));
		mnKeszlet.setFont(new Font("Segoe Print", Font.BOLD | Font.ITALIC, 13));
		menuBar.add(mnKeszlet);

		JMenuItem mtmUjAlbum = new JMenuItem("??j Album felvitele");
		mtmUjAlbum.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Felvitel();

			}
		});
		mtmUjAlbum.setBackground(new Color(255, 255, 224));
		mtmUjAlbum.setForeground(new Color(70, 130, 180));
		mtmUjAlbum.setFont(new Font("Segoe Script", Font.PLAIN, 12));
		mnKeszlet.add(mtmUjAlbum);

		JMenuItem mtmModositas = new JMenuItem("Album m??dos??t??sa");
		mtmModositas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Modositas();

			}
		});
		mtmModositas.setForeground(new Color(70, 130, 180));
		mtmModositas.setFont(new Font("Segoe Script", Font.PLAIN, 12));
		mtmModositas.setBackground(new Color(255, 255, 224));
		mnKeszlet.add(mtmModositas);

		JMenuItem mntmTorles = new JMenuItem("Album t??rl??se");
		mntmTorles.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Torles();

			}
		});
		mntmTorles.setForeground(new Color(70, 130, 180));
		mntmTorles.setFont(new Font("Segoe Script", Font.PLAIN, 12));
		mntmTorles.setBackground(new Color(255, 255, 224));
		mnKeszlet.add(mntmTorles);
	}

	private void Kiirasok() {

		if (tblAlbumok.getSelectedRow() != -1) {
			lblAlbumNeve.setText(albumok.get(tblAlbumok.getSelectedRow()).getAlbumCime());
			lblAlbumAra.setText(String.valueOf((albumok.get(tblAlbumok.getSelectedRow()).getAr()) + "Ft"));
		} else {
			lblAlbumNeve.setText("");
			lblAlbumAra.setText("");
		}

	}

	private void Torles() {

		Object[] opciok = new Object[] { "Igen", "Nem" };

		try {
			if (tblAlbumok.getSelectedRow() != -1) {
				if ((JOptionPane.showOptionDialog(frmZenebolt, "Biztosan t??rli a kijel??lt albumot?", "T??rl??s",
						JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciok,
						opciok[1]) == JOptionPane.YES_OPTION)) {

					kezelo.AdatTorles(albumok.get(tblAlbumok.getSelectedRow()));
					albumok.remove(tblAlbumok.getSelectedRow());
					tModel.removeRow(tblAlbumok.getSelectedRow());

				}
			} else {
				JOptionPane.showMessageDialog(frmZenebolt, "Jel??lj??n ki egy albumot!", "Figyelmeztet??s",
						JOptionPane.WARNING_MESSAGE);
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(frmZenebolt, e.getMessage(), "Hiba", JOptionPane.ERROR_MESSAGE);
		}

	}

	private void Modositas() {

		if (tblAlbumok.getSelectedRow() != -1) {

			KeszletKezelo keszletAblak = new KeszletKezelo(kezelo, albumok.get(tblAlbumok.getSelectedRow()));
			keszletAblak.setVisible(true);

			if (keszletAblak.isRendben()) {

				albumok.get(tblAlbumok.getSelectedRow()).setAlbumCime(keszletAblak.getAlbum().getAlbumCime());
				albumok.get(tblAlbumok.getSelectedRow()).setMegjelenesEve(keszletAblak.getAlbum().getMegjelenesEve());
				albumok.get(tblAlbumok.getSelectedRow()).setKiadoNeve(keszletAblak.getAlbum().getKiadoNeve());
				albumok.get(tblAlbumok.getSelectedRow()).setAr(keszletAblak.getAlbum().getAr());

				tModel.setValueAt(keszletAblak.getAlbum().getAlbumCime(), tblAlbumok.getSelectedRow(), 0);
				tModel.setValueAt(keszletAblak.getAlbum().getMegjelenesEve(), tblAlbumok.getSelectedRow(), 1);
				tModel.setValueAt(keszletAblak.getAlbum().getKiadoNeve(), tblAlbumok.getSelectedRow(), 2);
				tModel.setValueAt(keszletAblak.getAlbum().getAr(), tblAlbumok.getSelectedRow(), 3);
			}

		} else {
			JOptionPane.showMessageDialog(frmZenebolt, "Nincsen kijel??lve album a m??dos??t??shoz!", "Figyelmeztet??s",
					JOptionPane.WARNING_MESSAGE);
		}

	}

	private void Felvitel() {

		KeszletKezelo keszletAblak = new KeszletKezelo(kezelo);
		keszletAblak.setVisible(true);

		if (keszletAblak.isRendben()) {

			album = keszletAblak.getAlbum();

			Object[] adatok = new Object[] { album.getAlbumCime(), album.getMegjelenesEve(), album.getKiadoNeve(),
					album.getAr() };

			tModel.addRow(adatok);
			albumok.add(album);

		}

	}

	private void Vasarlas() {

		try {
			Object[] opciok = new Object[] { "Igen", "M??gsem" };

			if (tblAlbumok.getSelectedRow() != -1) {
				if ((JOptionPane.showOptionDialog(frmZenebolt,
						"Biztosan megveszi a kijel??lt albumot? Fizetend??: "
								+ String.valueOf(albumok.get(tblAlbumok.getSelectedRow()).getAr()) + "Ft",
						"V??s??rl??s", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciok,
						opciok[0]) == JOptionPane.YES_OPTION)) {

					if (vevo.getEgyenleg() >= (albumok.get(tblAlbumok.getSelectedRow())).getAr()) {
						vevo.setEgyenleg((vevo.getEgyenleg() - (albumok.get(tblAlbumok.getSelectedRow())).getAr()));
						kezelo.AdatTorles(albumok.get(tblAlbumok.getSelectedRow()));
						albumok.remove(tblAlbumok.getSelectedRow());
						tModel.removeRow(tblAlbumok.getSelectedRow());

						kezeloVevok = new ABKezeloVevok(connectionURLVevok, userName, password);
						kezeloVevok.AdatModositas(vevo);

						frmZenebolt.setTitle(
								"Bel??pve: " + vevo.getFelhasznaloNev() + ", egyenleged: " + vevo.getEgyenleg() + "Ft");
						JOptionPane.showMessageDialog(frmZenebolt, "Sikeres v??s??rl??s!", "Figyelmeztet??s",
								JOptionPane.INFORMATION_MESSAGE);

					} else {
						JOptionPane.showMessageDialog(frmZenebolt,
								"Nem ??ll rendelkez??sre elegend?? egyenleg a fizet??shez!", "Figyelmeztet??s",
								JOptionPane.WARNING_MESSAGE);
					}

				}
			} else {
				JOptionPane.showMessageDialog(frmZenebolt, "Jel??lj??n ki egy albumot!", "Figyelmeztet??s",
						JOptionPane.WARNING_MESSAGE);
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(frmZenebolt, e.getMessage(), "Hiba", JOptionPane.ERROR_MESSAGE);
		}

	}

	private void Feltoltes() {

		EgyenlegFeltoltes feltoltesAblak = new EgyenlegFeltoltes();
		feltoltesAblak.setVisible(true);

		try {
			if (feltoltesAblak.isRendben()) {
				vevo.setEgyenleg((vevo.getEgyenleg() + feltoltesAblak.getEgyenleg()));
				kezeloVevok = new ABKezeloVevok(connectionURLVevok, userName, password);
				kezeloVevok.AdatModositas(vevo);
				frmZenebolt.setTitle(
						"Bel??pve: " + vevo.getFelhasznaloNev() + ", egyenleged: " + vevo.getEgyenleg() + "Ft");

				JOptionPane.showMessageDialog(frmZenebolt,
						"Egyenlege " + String.valueOf(feltoltesAblak.getEgyenleg()) + " Ft-al felt??lt??sre ker??lt!",
						"Sikeres egyenlegfelt??lt??s", JOptionPane.INFORMATION_MESSAGE);

			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(frmZenebolt, e.getMessage(), "Hiba", JOptionPane.ERROR_MESSAGE);
		}

	}

	private void TablaFeltoltes() {

		for (Album album : albumok) {

			Object[] adatok = new Object[] { album.getAlbumCime(), album.getMegjelenesEve(), album.getKiadoNeve(),
					album.getAr() };
			tModel.addRow(adatok);

		}
		tblAlbumok.setModel(tModel);

	}

	private void Beolvasas() {

		try {

			albumok = kezelo.AdatBeolvasas();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(frmZenebolt, e.getMessage(), "Hiba", JOptionPane.ERROR_MESSAGE);
		}

	}
}
