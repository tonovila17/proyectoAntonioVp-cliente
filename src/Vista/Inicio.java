package Vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import modelo.Coleccion;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.help.HelpSetException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.SystemColor;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class Inicio extends JFrame {

	private JPanel contentPane;
	private JTextField txtIP;
	public static Socket sc;
	public static int Puerto = 9999;
	private HelpSet helpset;
	private HelpBroker browser;
	private String[] idiomas = { "ES", "GL" };
	private JLabel lblIndicaIP;
	private JButton btnConectar;
	private JButton btnAyuda;
	private JButton btnSalir;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inicio frame = new Inicio();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Inicio() {
		setTitle("Inicio");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 513, 226);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		contentPane.add(panel, BorderLayout.NORTH);

		lblIndicaIP = new JLabel("Indica por favor la IP a la que quieres conectarte: ");
		lblIndicaIP.setForeground(Color.WHITE);
		panel.add(lblIndicaIP);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.DARK_GRAY);
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		contentPane.add(panel_1, BorderLayout.SOUTH);

		btnSalir = new JButton("Salir");
		btnSalir.setBackground(Color.WHITE);
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		JComboBox cmbIdiomas = new JComboBox();
		panel_1.add(cmbIdiomas);
		cmbIdiomas.setBackground(Color.WHITE);
		cmbIdiomas.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				if (cmbIdiomas.getSelectedItem().equals("ES")) {
					Locale.setDefault(new Locale("es", "ES"));

					try {
						URL helpURL = this.getClass().getResource("/ayuda/helpset.hs");

						helpset = new HelpSet(null, helpURL);

						browser = helpset.createHelpBroker();
						browser.enableHelpOnButton(btnAyuda, "inicio", helpset);

					} catch (HelpSetException e2) {
						System.out.print("No se pudo cargar la ayuda.");
						JOptionPane.showMessageDialog(btnAyuda, "No se ha podido encontrar el archivo de ayuda.");
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					traducir();

				}

				if (cmbIdiomas.getSelectedItem().equals("GL")) {
					Locale.setDefault(new Locale("es", "GL"));
					try {
						URL helpURL = this.getClass().getResource("/ayuda_ingles/helpset.hs");

						helpset = new HelpSet(null, helpURL);

						browser = helpset.createHelpBroker();
						browser.enableHelpOnButton(btnAyuda, "inicio", helpset);

					} catch (HelpSetException e3) {
						System.out.print("No se pudo cargar la ayuda.");
						JOptionPane.showMessageDialog(btnAyuda, "No se ha podido encontrar el archivo de ayuda.");
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
					traducir();
				}

			}
		});
		cmbIdiomas.setModel(new DefaultComboBoxModel(idiomas));

		btnAyuda = new JButton("Ayuda");
		btnAyuda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAyuda.setBackground(Color.WHITE);
		panel_1.add(btnAyuda);
		btnSalir.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(btnSalir);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.DARK_GRAY);
		contentPane.add(panel_2, BorderLayout.CENTER);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] { 96, 14, 86, 0, 77, 39, 0 };
		gbl_panel_2.rowHeights = new int[] { 23, 0, 0, 0, 0 };
		gbl_panel_2.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_2.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_2.setLayout(gbl_panel_2);

		btnConectar = new JButton("Conectar");
		btnConectar.setBackground(Color.WHITE);
		btnConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtIP.getText().isEmpty()) {
					JOptionPane.showMessageDialog(btnConectar, "No has indicado la IP");
					return;
				}

				Properties prop;
				try (OutputStream out = new FileOutputStream("./conexion_ip.properties")) {
					prop = new Properties();
					prop.setProperty("IP", txtIP.getText());
					prop.store(out, null);
					out.close();
				} catch (Exception e4) {
					System.out.print("No se encont� el archivo");
					e4.printStackTrace();
				}

				DataInputStream in;
				DataOutputStream out;
				ObjectOutputStream objectOut;
				ObjectInputStream objectInput;

				boolean conectado = false;
				try {
					Socket sc = ObtenerSocket();

					if (sc == null) {
						JOptionPane.showMessageDialog(getContentPane(),
								"No se ha podido establecer conexi�n con el servidor. \n "
										+ "Pruebe con una nueva IP si as� lo desea.");
						return;
					}

					out = new DataOutputStream(sc.getOutputStream());

					out.writeUTF("20");

					in = new DataInputStream(sc.getInputStream());

					conectado = in.readBoolean();

				} catch (IOException e2) {
					e2.printStackTrace();
				}

				if (conectado) {
					sc = ObtenerSocket();
					Menu dia = new Menu(sc);
					dia.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(btnConectar,
							"Lo sentimos, no se ha podido conectar con el servidor. \n  Prueba con otra IP.");
				}
			}
		});

		JLabel lblIP = new JLabel("IP:");
		lblIP.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblIP = new GridBagConstraints();
		gbc_lblIP.anchor = GridBagConstraints.EAST;
		gbc_lblIP.insets = new Insets(0, 0, 5, 5);
		gbc_lblIP.gridx = 2;
		gbc_lblIP.gridy = 1;
		panel_2.add(lblIP, gbc_lblIP);

		txtIP = new JTextField();
		GridBagConstraints gbc_txtIP = new GridBagConstraints();
		gbc_txtIP.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtIP.insets = new Insets(0, 0, 5, 5);
		gbc_txtIP.gridx = 3;
		gbc_txtIP.gridy = 1;
		panel_2.add(txtIP, gbc_txtIP);
		txtIP.setColumns(10);
		GridBagConstraints gbc_btnConectar = new GridBagConstraints();
		gbc_btnConectar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnConectar.anchor = GridBagConstraints.NORTH;
		gbc_btnConectar.insets = new Insets(0, 0, 5, 5);
		gbc_btnConectar.gridx = 3;
		gbc_btnConectar.gridy = 2;
		panel_2.add(btnConectar, gbc_btnConectar);

	}

	public Socket ObtenerSocket() {
		Properties prop = null;
		String ip;
		ArrayList<Coleccion> ListaColecciones = null;

		try (InputStream is = new FileInputStream("./conexion_ip.properties")) {
			prop = new Properties();
			prop.load(is);
			ip = prop.getProperty("IP");
			is.close();

		} catch (Exception e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(contentPane,
					"No se encontr� el archivo con su configuraci�n. Si es la primera conexi�n que realiza \n ");
			System.out.print("No se encontr� el archivo properties");
			return null;
		}

		DataInputStream in;
		DataOutputStream out;
		ObjectOutputStream objectOut;
		ObjectInputStream objectInput;
		Socket sc = null;
		try {
			sc = new Socket(ip, 9999);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sc;
	}

	public void traducir() {

		ResourceBundle rb = ResourceBundle.getBundle("traduccion.config");

		lblIndicaIP.setText(rb.getString("lblIndicaIP"));

		btnConectar.setText(rb.getString("btnConectar"));
		btnAyuda.setText(rb.getString("btnAyuda"));
		btnSalir.setText(rb.getString("btnSalir"));

	}
}
