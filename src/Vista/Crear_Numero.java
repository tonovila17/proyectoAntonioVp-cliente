package Vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import modelo.Coleccion;
import modelo.Numero;

import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Component;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;

import java.awt.Dimension;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Insets;

public class Crear_Numero extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtTitulo;
	private JTextField txtPrecio;
	private JTextField txtfecha_publi;
	private String[] estados = { "MAL ESTADO", "ACEPTABLE", "BUEN ESTADO", "PERFECTO" };
	private String imagen;
	private Image newImage;
	private File archivo;
	private byte[] foto;
	private ImageIcon imageicon;
	private JLabel lblfoto;
	private JComboBox cmbEstado;
	private JComboBox cmbColeccion;
	private JTextArea txtAreaSinopsis;
	private JButton btnCrear_Numero;
	private JButton btnVolver;
	private JLabel lblColeccion;
	private JLabel lblEstado;
	private JLabel lbl_fecha_publicacion;
	private JLabel lblSinopsis;
	private JLabel lblPrecio;
	private JLabel lblTitulo;
	private JButton btnSeleccionarFoto;

	public static void main(String[] args) {
		try {
			Crear_Numero dialog = new Crear_Numero(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Crear_Numero(Socket sc) throws ClassNotFoundException {
		setTitle("Crear C\u00F3mic");
		setBounds(100, 100, 1231, 544);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel center_sinopsis = new JPanel();
			contentPanel.add(center_sinopsis, BorderLayout.CENTER);
			center_sinopsis.setLayout(new BorderLayout(0, 0));
			{
				txtAreaSinopsis = new JTextArea();
				txtAreaSinopsis.setMargin(new Insets(5, 5, 5, 5));
				txtAreaSinopsis.setLineWrap(true);
				txtAreaSinopsis.setWrapStyleWord(true);
				center_sinopsis.add(txtAreaSinopsis);
			}
		}
		{
			JPanel north = new JPanel();
			north.setBackground(Color.DARK_GRAY);
			FlowLayout flowLayout = (FlowLayout) north.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			contentPanel.add(north, BorderLayout.NORTH);
			{
				lblTitulo = new JLabel("T\u00EDtulo : ");
				lblTitulo.setForeground(Color.WHITE);
				lblTitulo.setHorizontalAlignment(SwingConstants.LEFT);
				north.add(lblTitulo);
			}
			{
				txtTitulo = new JTextField();
				north.add(txtTitulo);
				txtTitulo.setColumns(10);
			}
			{
				Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
				north.add(rigidArea);
			}
			{
				lblPrecio = new JLabel("Precio (\u20AC)  : ");
				lblPrecio.setForeground(Color.WHITE);
				north.add(lblPrecio);
			}
			{
				txtPrecio = new JTextField();
				north.add(txtPrecio);
				txtPrecio.setColumns(10);
			}
			{
				Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
				north.add(rigidArea);
			}
			{
				lbl_fecha_publicacion = new JLabel("Feha Publicaci\u00F3n (dd/MM/aaaa): ");
				lbl_fecha_publicacion.setForeground(Color.WHITE);
				north.add(lbl_fecha_publicacion);
			}
			{
				txtfecha_publi = new JTextField();
				north.add(txtfecha_publi);
				txtfecha_publi.setColumns(10);
			}
			{
				Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
				north.add(rigidArea);
			}
			{
				lblEstado = new JLabel("Estado : ");
				lblEstado.setForeground(Color.WHITE);
				north.add(lblEstado);
			}
			{
				cmbEstado = new JComboBox();
				cmbEstado.setModel(new DefaultComboBoxModel(estados));
				north.add(cmbEstado);
			}
			{
				Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
				north.add(rigidArea);
			}
			{
				lblColeccion = new JLabel("Colecci\u00F3n :");
				lblColeccion.setForeground(Color.WHITE);
				north.add(lblColeccion);
			}
			{
				cmbColeccion = new JComboBox();
				ArrayList<Coleccion> colecciones = new ArrayList();
				colecciones = crearSocketPedirColecciones(sc);
				String[] array_colecciones = new String[colecciones.size()];

				for (int i = 0; i < colecciones.size(); i++) {
					array_colecciones[i] = colecciones.get(i).getTitulo();
				}
				cmbColeccion.setModel(new DefaultComboBoxModel(array_colecciones));
				north.add(cmbColeccion);
			}
		}
		{
			JPanel west = new JPanel();
			west.setBackground(Color.DARK_GRAY);
			contentPanel.add(west, BorderLayout.WEST);
			west.setLayout(new BorderLayout(0, 0));
			{
				lblfoto = new JLabel("");
				lblfoto.setIcon(new ImageIcon(Crear_Numero.class.getResource("/imagenes_placeholders/camera.jpg")));
				lblfoto.setPreferredSize(new Dimension(300, 400));
				west.add(lblfoto, BorderLayout.CENTER);
			}
			{
				JPanel panel = new JPanel();
				panel.setForeground(Color.DARK_GRAY);
				panel.setBackground(Color.DARK_GRAY);
				FlowLayout flowLayout = (FlowLayout) panel.getLayout();
				flowLayout.setAlignment(FlowLayout.RIGHT);
				west.add(panel, BorderLayout.NORTH);
				{
					lblSinopsis = new JLabel("Sinopsis :");
					lblSinopsis.setForeground(Color.WHITE);
					panel.add(lblSinopsis);
				}
			}
			{
				JPanel panel = new JPanel();
				west.add(panel, BorderLayout.SOUTH);
				panel.setLayout(new BorderLayout(0, 0));
				{
					btnSeleccionarFoto = new JButton("Seleccionar Imagen (.jpg)");
					btnSeleccionarFoto.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							JFileChooser ficheros = new JFileChooser();
							String directorioUsu = System.getProperty("user.home");
							String separador = System.getProperty("file.separator");
							String ruta = directorioUsu + separador + "Desktop";
							ficheros.setCurrentDirectory(new File(ruta));
							FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imagenes", "jpg", "png");
							ficheros.setFileFilter(filtro);

							int respuesta = ficheros.showOpenDialog(rootPane);

							if (respuesta == JFileChooser.APPROVE_OPTION) {
								archivo = ficheros.getSelectedFile();
								String f = archivo.toString();
								imagen = f;
								ImageIcon img = new ImageIcon(imagen);
								Image image = img.getImage();
								newImage = image.getScaledInstance(lblfoto.getWidth(), lblfoto.getHeight(),
										java.awt.Image.SCALE_FAST);
								imageicon = new ImageIcon(newImage);
								lblfoto.setIcon(imageicon);

							}
						}
					});
					panel.add(btnSeleccionarFoto, BorderLayout.NORTH);
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.DARK_GRAY);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnCrear_Numero = new JButton("Crear");
				btnCrear_Numero.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						boolean validado = true;

						String titulo = txtTitulo.getText();
						double precio;
						String estado = (String) cmbEstado.getSelectedItem();
						String coleccion = (String) cmbColeccion.getSelectedItem();
						int id_coleccion = ObtenerIdColeccion(ObtenerSocket(), coleccion);
						String sinopsis = txtAreaSinopsis.getText();

						SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
						Date fecha_publi;
						java.sql.Date fecha_enviar = null;
						try {
							fecha_publi = formato.parse(txtfecha_publi.getText());
							fecha_enviar = new java.sql.Date(fecha_publi.getTime());
						} catch (ParseException e1) {
							JOptionPane.showMessageDialog(btnCrear_Numero,
									"El campo de fecha no se encuentra en un formato correcto.");
							fecha_publi = null;
							e1.printStackTrace();
							return;
						}

						String mensaje = "";

						if (titulo.isEmpty()) {
							validado = false;
							mensaje = mensaje + "El campo de T�tulo es obligatorio. \n";

						}

						try {
							precio = Double.valueOf(txtPrecio.getText());
						} catch (NumberFormatException j) {
							mensaje = mensaje + "El precio debe estar entre 0 y 999 euros. \n";
							JOptionPane.showMessageDialog(btnCrear_Numero, mensaje);
							return;
						}

						if (precio <= 0 || precio > 999) {
							validado = false;
							mensaje = mensaje + "El precio debe estar entre 0 y 999 euros. \n";

						}

						if (archivo == null) {
							validado = false;
							mensaje = mensaje + "No se ha seleccionado una foto \n";
						}

						if (validado) {
							Numero numero = new Numero(0, titulo, sinopsis, precio, estado, fecha_enviar, id_coleccion,
									imageicon);
							try {
								int id_nuevonumero = 0;
								try {
									id_nuevonumero = crearSocketCrearNumero(numero, ObtenerSocket());
								} catch (ClassNotFoundException e2) {
									// TODO Auto-generated catch block
									e2.printStackTrace();
								}
								if (id_nuevonumero != -1) {
									JOptionPane.showMessageDialog(getContentPane(), "C�mic creado con �xito.");
									Gestion_Numeros dia = null;
									try {
										dia = new Gestion_Numeros(ObtenerSocket());
									} catch (ClassNotFoundException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									dia.setVisible(true);
									dispose();
								} else {
									JOptionPane.showMessageDialog(btnCrear_Numero, "No se ha podido crear el C�mic.");
									return;
								}
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} else {
							JOptionPane.showMessageDialog(btnCrear_Numero, mensaje);
							return;
						}

					}
				});
				btnCrear_Numero.setActionCommand("OK");
				buttonPane.add(btnCrear_Numero);
				getRootPane().setDefaultButton(btnCrear_Numero);
			}
			{
				btnVolver = new JButton("Volver");
				btnVolver.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Gestion_Numeros dia = null;
						try {
							dia = new Gestion_Numeros(ObtenerSocket());
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
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

		btnCrear_Numero.setText(rb.getString("btnCrear_Numero"));
		btnVolver.setText(rb.getString("btnVolver"));
		lblTitulo.setText(rb.getString("lblTitulo"));
		lblPrecio.setText(rb.getString("lblPrecio"));
		lbl_fecha_publicacion.setText(rb.getString("lbl_fecha_publicacion"));
		lblEstado.setText(rb.getString("lblEstado"));
		btnSeleccionarFoto.setText(rb.getString("btnSeleccionarFoto"));
		lblColeccion.setText(rb.getString("lblColeccion"));
		lblSinopsis.setText(rb.getString("lblSinopsis"));

	}

	public int crearSocketCrearNumero(Numero numero, Socket socket) throws ClassNotFoundException, IOException {

		byte bytes[] = null;
		bytes = PasarImagenaBytes(archivo);

		int id = 0;

		DataOutputStream out;
		ObjectOutputStream objectOut;
		DataInputStream in;

		try {
			System.out.println("Puerto" + socket.getLocalPort());
			out = new DataOutputStream(socket.getOutputStream());
			out.flush();
			out.writeUTF("7");
			out.flush();

			objectOut = new ObjectOutputStream(socket.getOutputStream());
			objectOut.writeObject(numero);
			objectOut.flush();

			out.writeInt(bytes.length);
			out.flush();

			for (int i = 0; i < bytes.length; i++) {
				out.writeByte(bytes[i]);
			}

			in = new DataInputStream(socket.getInputStream());
			id = in.readInt();

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		System.out.println(id);
		return id;

	}

	public byte[] PasarImagenaBytes(File f) throws IOException {

		byte[] bytes;
		BufferedImage bImage = ImageIO.read(f);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(bImage, "jpg", bos);

		bytes = bos.toByteArray();
		return bytes;
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

	public ArrayList<Coleccion> crearSocketPedirColecciones(Socket sc) throws ClassNotFoundException {

		DataOutputStream out;
		ObjectInputStream objectInput;
		ArrayList<Coleccion> listaColecciones = null;
		if (sc == null) {
			JOptionPane.showMessageDialog(getContentPane(), "No se ha podido conectar con el servidor.");
			return null;
		}

		try {

			out = new DataOutputStream(sc.getOutputStream());

			out.writeUTF("1");
			out.flush();

			objectInput = new ObjectInputStream(sc.getInputStream());
			listaColecciones = (ArrayList<Coleccion>) objectInput.readObject();

		} catch (IOException e1) {
			e1.printStackTrace();
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

}
