package Vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import modelo.Coleccion;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JComboBox;

public class Informes extends JDialog {
	public static Connection miConexion;
	private final JPanel contentPanel = new JPanel();
	private JComboBox cmbColecciones;
	private JButton btnVolver;
	private JButton btnInfColecciones;
	private JLabel lblSelecciona;
	private JButton btnInfNumeros;
	private JButton btnInfNumerosXColeccion;
	private JButton btnInfComicEstado;
	private JComboBox cmbEstado;

	public static void main(String[] args) {
		try {
			Informes dialog = new Informes(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Informes(Socket sc) throws ClassNotFoundException {
		setTitle("Informes");
		setBounds(100, 100, 1112, 216);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setForeground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBackground(Color.DARK_GRAY);
			contentPanel.add(panel, BorderLayout.NORTH);
			{
				lblSelecciona = new JLabel("\u00BFQue informe quieres visualizar?");
				lblSelecciona.setForeground(Color.WHITE);
				panel.add(lblSelecciona);
			}
		}
		{
			JPanel panel = new JPanel();
			panel.setBackground(Color.DARK_GRAY);
			contentPanel.add(panel, BorderLayout.CENTER);
			{
				btnInfColecciones = new JButton("Ver Informe Colecciones");
				btnInfColecciones.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						try {
							CrearSocketPedirInformeColecciones(ObtenerSocket());
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}

				});
				panel.add(btnInfColecciones);
			}
			{
				btnInfNumeros = new JButton("Ver Informe Numeros");
				btnInfNumeros.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						try {
							CrearSocketPedirInformeNumeros(ObtenerSocket());
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				panel.add(btnInfNumeros);
			}
			{

				cmbColecciones = new JComboBox();
				ArrayList<Coleccion> colecciones = new ArrayList();
				colecciones = ObtenerArrayColecciones(sc);
				String[] array_colecciones = new String[colecciones.size()];

				for (int i = 0; i < colecciones.size(); i++) {
					array_colecciones[i] = colecciones.get(i).getTitulo();
				}
				{
					btnInfNumerosXColeccion = new JButton("Ver Informe de N\u00FAmeros Seg\u00FAn Colecci\u00F3n");
					btnInfNumerosXColeccion.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {

							String coleccion = (String) cmbColecciones.getSelectedItem();
							int id_coleccion = ObtenerIdColeccion(ObtenerSocket(), coleccion);

							try {
								System.out.println("Valor del id coleccion" + id_coleccion);
								ObtenerInformeNumXColeccion(ObtenerSocket(), id_coleccion);
							} catch (ClassNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						}
					});
					panel.add(btnInfNumerosXColeccion);
				}
				cmbColecciones.setModel(new DefaultComboBoxModel(array_colecciones));
				panel.add(cmbColecciones);

			}
			{
				cmbEstado = new JComboBox();
				cmbEstado.setModel(new DefaultComboBoxModel(
						new String[] { "MAL ESTADO", "ACEPTABLE", "BUEN ESTADO", "PERFECTO" }));

				btnInfComicEstado = new JButton();
				btnInfComicEstado.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String estado = (String) cmbEstado.getSelectedItem();
						try {
							System.out.print("Estado elegido: " + estado);
							ObtenerInformeXEstado(ObtenerSocket(), estado);
						} catch (ClassNotFoundException e1) {
							// TODO: handle exception
							e1.printStackTrace();
						}
					}
				});
				panel.add(btnInfComicEstado);
			}
			{
				panel.add(cmbEstado);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.DARK_GRAY);
			buttonPane.setForeground(Color.WHITE);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnVolver = new JButton("Volver");
				btnVolver.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						Menu dia = null;
						dia = new Menu(ObtenerSocket());
						dia.setVisible(true);
						dispose();
					}
				});
				btnVolver.setActionCommand("Cancel");
				buttonPane.add(btnVolver);
			}
		}
		traducir();

	}

	public void traducir() {

		ResourceBundle rb = ResourceBundle.getBundle("traduccion.config");

		btnInfColecciones.setText(rb.getString("btnInfColecciones"));
		lblSelecciona.setText(rb.getString("lblSelecciona"));
		btnVolver.setText(rb.getString("btnVolver"));
		btnInfNumeros.setText(rb.getString("btnInfNumeros"));
		btnInfNumerosXColeccion.setText(rb.getString("btnInfNumerosXColeccion"));
		btnInfComicEstado.setText(rb.getString("btnInfComicEstado"));

	}

	public void CrearSocketPedirInformeColecciones(Socket socket) throws ClassNotFoundException {

		DataOutputStream out;
		ObjectOutputStream objectOut;
		DataInputStream in;

		try {

			out = new DataOutputStream(socket.getOutputStream());
			out.writeUTF("15");
			out.flush();

			ObjectInputStream InputJVisor = new ObjectInputStream(socket.getInputStream());
			JasperPrint visor = (JasperPrint) InputJVisor.readObject();

			System.out.println("Objecto visor numeros recibido en cliente");

			JasperViewer.viewReport(visor, false);

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void CrearSocketPedirInformeNumeros(Socket socket) throws ClassNotFoundException {

		DataOutputStream out;
		ObjectOutputStream objectOut;
		DataInputStream in;

		try {

			out = new DataOutputStream(socket.getOutputStream());
			out.writeUTF("16");
			out.flush();

			ObjectInputStream InputJVisor = new ObjectInputStream(socket.getInputStream());
			JasperPrint visor = (JasperPrint) InputJVisor.readObject();

			System.out.println("Objecto visor numeros recibido en cliente");

			JasperViewer.viewReport(visor, false);

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void visualizarInforme(JasperPrint visor) {

		JasperViewer.viewReport(visor, false);

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

	public void TestConexion(Socket sc) {

		DataOutputStream outputStream = null;

		try {

			outputStream = new DataOutputStream(sc.getOutputStream());

			outputStream.writeUTF("20");
			outputStream.flush();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public ArrayList<Coleccion> ObtenerArrayColecciones(Socket socket) throws ClassNotFoundException {

		ArrayList<Coleccion> listaColecciones = null;
		DataOutputStream outArray = null;
		ObjectInputStream inputArray = null;

		try {
			outArray = new DataOutputStream(socket.getOutputStream());

			outArray.writeUTF("1");
			outArray.flush();

			inputArray = new ObjectInputStream(socket.getInputStream());
			listaColecciones = (ArrayList<Coleccion>) inputArray.readObject();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listaColecciones;

	}

	public int ObtenerIdColeccion(Socket socket, String titulo_coleccion) {

		DataOutputStream dataout = null;
		DataInputStream datainput = null;
		int idcoleccion = 0;
		try {
			dataout = new DataOutputStream(socket.getOutputStream());

			dataout.writeUTF("5");
			dataout.flush();

			dataout.writeUTF(titulo_coleccion);
			dataout.flush();

			datainput = new DataInputStream(socket.getInputStream());
			idcoleccion = datainput.readInt();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return idcoleccion;

	}

	public void ObtenerInformeNumXColeccion(Socket socket, int id) throws ClassNotFoundException {

		JasperPrint report = null;
		DataOutputStream salida = null;
		ObjectInputStream entradareport = null;

		try {
			salida = new DataOutputStream(socket.getOutputStream());
			salida.writeUTF("17");
			salida.flush();

			salida.writeInt(id);
			salida.flush();

			entradareport = new ObjectInputStream(socket.getInputStream());
			report = (JasperPrint) entradareport.readObject();

			visualizarInforme(report);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void ObtenerInformeXEstado(Socket socket, String estado) throws ClassNotFoundException {

		JasperPrint report = null;
		DataOutputStream salida = null;
		ObjectInputStream entradareport = null;

		try {
			salida = new DataOutputStream(socket.getOutputStream());
			salida.writeUTF("23");
			salida.flush();

			salida.writeUTF(estado);
			salida.flush();

			entradareport = new ObjectInputStream(socket.getInputStream());
			report = (JasperPrint) entradareport.readObject();

			visualizarInforme(report);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
