package zenebolt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class EgyenlegFeltoltes extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JSpinner spnEgyelnleg;
	private boolean rendben = false;
	private int egyenleg;

	public int getEgyenleg() {
		return egyenleg;
	}

	public boolean isRendben() {
		return rendben;
	}

	/**
	 * Launch the application.
	 */

	/**
	 * Create the dialog.
	 */
	public EgyenlegFeltoltes() {

		setModal(true);
		setTitle("Egyenleg feltöltés");
		setBounds(100, 100, 300, 220);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(245, 245, 220));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblSzoveg = new JLabel("Adja meg az új egyenleg összegét:");
			lblSzoveg.setHorizontalAlignment(SwingConstants.CENTER);
			lblSzoveg.setFont(new Font("Segoe Print", Font.BOLD | Font.ITALIC, 12));
			lblSzoveg.setBounds(40, 29, 220, 25);
			contentPanel.add(lblSzoveg);
		}

		spnEgyelnleg = new JSpinner();
		spnEgyelnleg.setForeground(new Color(65, 105, 225));
		spnEgyelnleg.setFont(new Font("Sitka Heading", Font.BOLD, 13));
		spnEgyelnleg.setBackground(new Color(173, 216, 230));
		spnEgyelnleg
				.setModel(new SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1000)));
		spnEgyelnleg.setBounds(90, 80, 110, 25);
		contentPanel.add(spnEgyelnleg);

		JButton btnMégsem = new JButton("Mégsem");
		btnMégsem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				dispose();

			}
		});
		btnMégsem.setForeground(new Color(255, 255, 255));
		btnMégsem.setFont(new Font("Yu Gothic", Font.BOLD, 13));
		btnMégsem.setBackground(new Color(65, 105, 225));
		btnMégsem.setBounds(10, 134, 110, 30);
		contentPanel.add(btnMégsem);

		JButton btnRendben = new JButton("Rendben");
		btnRendben.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Ellenorzes();

			}
		});
		btnRendben.setForeground(Color.WHITE);
		btnRendben.setFont(new Font("Yu Gothic", Font.BOLD, 13));
		btnRendben.setBackground(new Color(65, 105, 225));
		btnRendben.setBounds(166, 134, 110, 30);
		contentPanel.add(btnRendben);
	}

	private void Ellenorzes() {

		if (((int) spnEgyelnleg.getValue()) > 0) {
			egyenleg = ((int) spnEgyelnleg.getValue());
			rendben = true;
			dispose();
		} else {
			JOptionPane.showMessageDialog(null, "Adjon meg egy összeget!", "Egyenlegfeltöltés",
					JOptionPane.WARNING_MESSAGE);
		}

	}

}
