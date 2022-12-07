package Vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.help.HelpSetException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import modelo.Coleccion;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JComboBox;

public class Menu extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JButton btnSalir;
	private JButton btnColecciones;
	private JButton btnNumeros;
	private JButton btnInformes;
	private JLabel lblDeseaVisualizar;
	private HelpSet helpset;
	private HelpBroker browser;

	public static void main(String[] args) {
		try {
			Menu dialog = new Menu(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Menu(Socket sc) {
		System.out.println("Puerto : " + sc.getLocalPort());
		setTitle("Men\u00FA Principal");
		setBounds(100, 100, 508, 161);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			panel.setLayout(new BorderLayout(0, 0));
			{
				JPanel panel_1 = new JPanel();
				panel_1.setBackground(Color.DARK_GRAY);
				panel.add(panel_1, BorderLayout.CENTER);
				{
					btnColecciones = new JButton("Colecciones de C�mics");
					btnColecciones.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							Gestion_colecciones dia = null;
							try {
								dia = new Gestion_colecciones(sc);
							} catch (ClassNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (UnknownHostException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							if (dia != null)
								dia.setVisible(true);
							dispose();
						}
					});
					panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
					panel_1.add(btnColecciones);
				}
				{
					btnNumeros = new JButton("Todos los C\u00F3mics");
					btnNumeros.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							Gestion_Numeros dia = null;
							try {
								dia = new Gestion_Numeros(sc);
							} catch (ClassNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							dia.setVisible(true);
							dispose();
						}
					});
					panel_1.add(btnNumeros);
				}
				{
					btnInformes = new JButton("Informes");
					btnInformes.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {

							Informes dia = null;
							try {
								dia = new Informes(sc);
							} catch (ClassNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							dia.setVisible(true);
							dispose();
						}
					});
					panel_1.add(btnInformes);
				}
			}
			{
				JPanel panel_1 = new JPanel();
				panel_1.setBackground(Color.DARK_GRAY);
				panel.add(panel_1, BorderLayout.NORTH);
				{
					lblDeseaVisualizar = new JLabel("\u00BFQue deseas visualizar?");
					lblDeseaVisualizar.setForeground(Color.WHITE);
					panel_1.add(lblDeseaVisualizar);
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.DARK_GRAY);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnSalir = new JButton("Salir");
				btnSalir.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						System.exit(0);
					}
				});
				btnSalir.setActionCommand("Cancel");
				buttonPane.add(btnSalir);
			}
		}
		traducir();
	}

	public void traducir() {

		ResourceBundle rb = ResourceBundle.getBundle("traduccion.config");

		lblDeseaVisualizar.setText(rb.getString("lblDeseaVisualizar"));
		btnColecciones.setText(rb.getString("btnColecciones"));
		btnNumeros.setText(rb.getString("btnNumeros"));
		btnInformes.setText(rb.getString("btnInformes"));
		btnSalir.setText(rb.getString("btnSalir"));

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
			JOptionPane.showMessageDialog(getContentPane(),
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

}