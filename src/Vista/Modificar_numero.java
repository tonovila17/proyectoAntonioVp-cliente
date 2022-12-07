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
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Component;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JComboBox;
import java.awt.Color;

public class Modificar_numero extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtTitulo;
	private JTextField txtPrecio;
	private JTextField txtFecha_publi;
	private JComboBox cmbEstado;
	private String[] estados = { "MAL ESTADO", "ACEPTABLE", "BUEN ESTADO", "PERFECTO" };
	private JComboBox cmbColecciones;
	private JLabel lblfoto;
	private String imagen;
	private Image newImage;
	private File archivo;
	private byte[] foto;
	private ImageIcon imageicon;
	private JTextArea txtAreaSinopsis;
	private JLabel lblTitulo;
	private JLabel lblPrecio;
	private JLabel lblSinopsis;
	private JLabel lbl_fecha_publicacion;
	private JLabel lblEstado;
	private JLabel lblColeccion;
	private JButton btnSeleccionarFoto;
	private JButton btnModificar;
	private JButton btnCancelar;

	public static void main(String[] args) {
		try {
			Modificar_numero dialog = new Modificar_numero();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Modificar_numero() {
		setTitle("Modificar C\u00F3mic");
		setBounds(100, 100, 1235, 545);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel north = new JPanel();
			north.setBackground(Color.DARK_GRAY);
			FlowLayout flowLayout = (FlowLayout) north.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			contentPanel.add(north, BorderLayout.NORTH);
			{
				lblTitulo = new JLabel("Titulo :");
				lblTitulo.setForeground(Color.WHITE);
				north.add(lblTitulo);
			}
			{
				txtTitulo = new JTextField();
				txtTitulo.setPreferredSize(new Dimension(15, 20));
				north.add(txtTitulo);
				txtTitulo.setColumns(20);
			}
			{
				Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
				north.add(rigidArea);
			}
			{
				lblPrecio = new JLabel("Precio :");
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
				lbl_fecha_publicacion = new JLabel("Fecha Publicaci\u00F3n(dd/MM/aaaa) :");
				lbl_fecha_publicacion.setForeground(Color.WHITE);
				north.add(lbl_fecha_publicacion);
			}
			{
				txtFecha_publi = new JTextField();
				north.add(txtFecha_publi);
				txtFecha_publi.setColumns(10);
			}
			{
				Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
				north.add(rigidArea);
			}
			{
				lblEstado = new JLabel("Estado: ");
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
				lblColeccion = new JLabel("Colecci\u00F3n : ");
				lblColeccion.setForeground(Color.WHITE);
				north.add(lblColeccion);
			}
			{
				cmbColecciones = new JComboBox();
				ArrayList<Coleccion> colecciones = new ArrayList();
				colecciones = null;
				String[] array_colecciones = new String[colecciones.size()];

				for (int i = 0; i < colecciones.size(); i++) {
					array_colecciones[i] = colecciones.get(i).getTitulo();
				}
				cmbColecciones.setModel(new DefaultComboBoxModel(array_colecciones));
				north.add(cmbColecciones);
			}
		}
		{
			JPanel west = new JPanel();
			contentPanel.add(west, BorderLayout.WEST);
			west.setLayout(new BorderLayout(0, 0));
			{
				lblfoto = new JLabel("");
				lblfoto.setIcon(new ImageIcon(Modificar_numero.class.getResource(
						"/imagenes_placeholders/businessman-aspiration-becoming-superhero-166065389.jpg")));
				lblfoto.setPreferredSize(new Dimension(300, 400));
				west.add(lblfoto);
			}
			{
				JPanel panel = new JPanel();
				panel.setBackground(Color.DARK_GRAY);
				FlowLayout flowLayout = (FlowLayout) panel.getLayout();
				flowLayout.setAlignment(FlowLayout.RIGHT);
				west.add(panel, BorderLayout.NORTH);
				{
					lblSinopsis = new JLabel("Sinopsis:");
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
			JPanel center_sinopsis = new JPanel();
			contentPanel.add(center_sinopsis, BorderLayout.CENTER);
			center_sinopsis.setLayout(new BorderLayout(0, 0));
			{
				txtAreaSinopsis = new JTextArea();
				txtAreaSinopsis.setLineWrap(true);
				txtAreaSinopsis.setWrapStyleWord(true);
				center_sinopsis.add(txtAreaSinopsis);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.DARK_GRAY);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnModificar = new JButton("Guardar Cambio");
				btnModificar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

					}
				});
				btnModificar.setActionCommand("OK");
				buttonPane.add(btnModificar);
				getRootPane().setDefaultButton(btnModificar);
			}
			{
				btnCancelar = new JButton("Cancelar");
				btnCancelar.addActionListener(new ActionListener() {
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
				btnCancelar.setActionCommand("Cancel");
				buttonPane.add(btnCancelar);
			}
		}
	}

	public Modificar_numero(int id, Socket socket) throws ClassNotFoundException {
		setTitle("Modificar C\u00F3mic");
		setBounds(100, 100, 1235, 545);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel north = new JPanel();
			FlowLayout flowLayout = (FlowLayout) north.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			north.setBackground(Color.DARK_GRAY);
			contentPanel.add(north, BorderLayout.NORTH);
			{
				lblTitulo = new JLabel("Titulo :");
				lblTitulo.setForeground(Color.WHITE);
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
				lblPrecio = new JLabel("Precio :");
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
				lbl_fecha_publicacion = new JLabel("Fecha Publicaci\u00F3n(dd/MM/aaaa) :");
				lbl_fecha_publicacion.setForeground(Color.WHITE);
				north.add(lbl_fecha_publicacion);
			}
			{
				txtFecha_publi = new JTextField();
				north.add(txtFecha_publi);
				txtFecha_publi.setColumns(10);
			}
			{
				Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
				north.add(rigidArea);
			}
			{
				lblEstado = new JLabel("Estado: ");
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
				lblColeccion = new JLabel("Colecci\u00F3n : ");
				lblColeccion.setForeground(Color.WHITE);
				north.add(lblColeccion);
			}
			{
				cmbColecciones = new JComboBox();
				ArrayList<Coleccion> colecciones = new ArrayList();
				colecciones = crearSocketPedirColecciones(socket);
				String[] array_colecciones = new String[colecciones.size()];

				for (int i = 0; i < colecciones.size(); i++) {
					array_colecciones[i] = colecciones.get(i).getTitulo();
				}
				cmbColecciones.setModel(new DefaultComboBoxModel(array_colecciones));
				north.add(cmbColecciones);
			}
		}
		{
			JPanel west = new JPanel();
			west.setBackground(Color.DARK_GRAY);
			contentPanel.add(west, BorderLayout.WEST);
			west.setLayout(new BorderLayout(0, 0));
			{
				lblfoto = new JLabel("");
				lblfoto.setIcon(new ImageIcon(Modificar_numero.class.getResource("/imagenes_placeholders/camera.jpg")));
				lblfoto.setPreferredSize(new Dimension(300, 400));
				west.add(lblfoto);
			}
			{
				JPanel panel = new JPanel();
				FlowLayout flowLayout = (FlowLayout) panel.getLayout();
				flowLayout.setAlignment(FlowLayout.RIGHT);
				panel.setBackground(Color.DARK_GRAY);
				west.add(panel, BorderLayout.NORTH);
				{
					lblSinopsis = new JLabel("Sinopsis:");
					lblSinopsis.setForeground(Color.WHITE);
					panel.add(lblSinopsis);
				}
			}
			{
				JPanel panel = new JPanel();
				panel.setBackground(Color.DARK_GRAY);
				west.add(panel, BorderLayout.SOUTH);
				panel.setLayout(new BorderLayout(0, 0));
				{
					btnSeleccionarFoto = new JButton("Seleccionar Imagen");
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
			JPanel center_sinopsis = new JPanel();
			center_sinopsis.setBackground(Color.DARK_GRAY);
			contentPanel.add(center_sinopsis, BorderLayout.CENTER);
			center_sinopsis.setLayout(new BorderLayout(0, 0));
			{
				txtAreaSinopsis = new JTextArea();
				txtAreaSinopsis.setLineWrap(true);
				txtAreaSinopsis.setWrapStyleWord(true);
				center_sinopsis.add(txtAreaSinopsis);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.DARK_GRAY);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnModificar = new JButton("Guardar Cambio");
				btnModificar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						boolean validado = true;

						String titulo = txtTitulo.getText();
						double precio = Double.valueOf(txtPrecio.getText());
						String estado = (String) cmbEstado.getSelectedItem();
						String coleccion = (String) cmbColecciones.getSelectedItem();
						int id_coleccion = ObtenerIdColeccion(ObtenerSocket(), coleccion);
						String sinopsis = txtAreaSinopsis.getText();

						SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
						Date fecha_publi;
						java.sql.Date fecha_enviar = null;
						try {
							fecha_publi = formato.parse(txtFecha_publi.getText());
							fecha_enviar = new java.sql.Date(fecha_publi.getTime());
						} catch (ParseException e1) {
							JOptionPane.showMessageDialog(btnModificar,
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

						if (precio <= 0 || precio > 999) {
							validado = false;
							mensaje = mensaje + "El precio debe estar entre 0 y 999 euros. \n";

						}

						if (archivo == null) {
							validado = false;
							mensaje = mensaje + "Es necesario seleccionar una imagen. \n";
						}
						if (validado) {
							boolean modificado = false;

							Numero numero = new Numero(id, titulo, sinopsis, precio, estado, fecha_enviar, id_coleccion,
									imageicon);

							try {
								modificado = CrearSocketModificarNumero(numero, ObtenerSocket());
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							if (modificado) {
								JOptionPane.showMessageDialog(getContentPane(), "Modificado con �xito");
								Gestion_Numeros dialogo = null;
								try {
									dialogo = new Gestion_Numeros(ObtenerSocket());
								} catch (ClassNotFoundException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								dialogo.setVisible(true);
								dispose();
							}

						} else {
							JOptionPane.showMessageDialog(btnModificar, mensaje);
						}

					}
				});
				btnModificar.setActionCommand("OK");
				buttonPane.add(btnModificar);
				getRootPane().setDefaultButton(btnModificar);
			}
			{
				btnCancelar = new JButton("Cancelar");
				btnCancelar.addActionListener(new ActionListener() {
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
				btnCancelar.setActionCommand("Cancel");
				buttonPane.add(btnCancelar);
			}
		}

		colocarDatos(id);
		traducir();
	}

	public void colocarDatos(int id) throws ClassNotFoundException {

		Numero numero = ObtenerNumeroporId(id, ObtenerSocket());

		txtTitulo.setText(numero.getTitulo());
		txtPrecio.setText(Double.toString(numero.getPrecio()));
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		txtFecha_publi.setText(formato.format(numero.getFecha_publicacion()));
		txtAreaSinopsis.setText(numero.getSinopsis());
		Image image = numero.getPortada().getImage();
		lblfoto.setIcon(numero.getPortada());
		imageicon = (ImageIcon) lblfoto.getIcon();

		String estado = numero.getEstado();
		for (int i = 0; i < estados.length; i++) {
			if (estado.equals(estados[i])) {
				cmbEstado.setSelectedIndex(i);
			}
		}

		ArrayList<Coleccion> colecciones = new ArrayList();
		colecciones = crearSocketPedirColecciones(ObtenerSocket());

		String[] array_colecciones = new String[colecciones.size()];

		for (int i = 0; i < colecciones.size(); i++) {
			array_colecciones[i] = colecciones.get(i).getTitulo();
		}

		for (int i = 0; i < colecciones.size(); i++) {
			if (colecciones.get(i).getId() == numero.getId_coleccion()) {

				for (int j = 0; j < array_colecciones.length; j++) {
					if (array_colecciones[j].equals(colecciones.get(i).getTitulo())) {
						cmbColecciones.setSelectedIndex(j);
					}
				}

			}
		}
	}

	public void traducir() {

		ResourceBundle rb = ResourceBundle.getBundle("traduccion.config");

		btnModificar.setText(rb.getString("btnModificar"));
		btnCancelar.setText(rb.getString("btnCancelar"));
		lblTitulo.setText(rb.getString("lblTitulo"));
		lblPrecio.setText(rb.getString("lblPrecio"));
		lbl_fecha_publicacion.setText(rb.getString("lbl_fecha_publicacion"));
		lblEstado.setText(rb.getString("lblEstado"));
		btnSeleccionarFoto.setText(rb.getString("btnSeleccionarFoto"));
		lblColeccion.setText(rb.getString("lblColeccion"));
		lblSinopsis.setText(rb.getString("lblSinopsis"));

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

	public boolean CrearSocketModificarNumero(Numero num, Socket socket) throws IOException {

		boolean modificado = false;
		byte[] bytes = PasarImagenaBytes(archivo);

		DataOutputStream salidaModifNum = null;
		ObjectOutputStream salidaObjModifNum = null;
		DataInputStream entrada_resultado = null;

		try {
			salidaModifNum = new DataOutputStream(socket.getOutputStream());
			salidaModifNum.writeUTF("9");
			salidaModifNum.flush();

			salidaObjModifNum = new ObjectOutputStream(socket.getOutputStream());
			salidaObjModifNum.writeObject(num);
			salidaObjModifNum.flush();

			salidaModifNum.writeInt(bytes.length);
			salidaModifNum.flush();

			for (int i = 0; i < bytes.length; i++) {
				salidaModifNum.writeByte(bytes[i]);
			}

			entrada_resultado = new DataInputStream(socket.getInputStream());
			modificado = entrada_resultado.readBoolean();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return modificado;
	}

	public byte[] PasarImagenaBytes(File f) throws IOException {

		byte[] bytes;
		BufferedImage bImage = ImageIO.read(f);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(bImage, "jpg", bos);

		bytes = bos.toByteArray();
		return bytes;
	}

	public Numero ObtenerNumeroporId(int id, Socket socket) throws ClassNotFoundException {

		Numero num = null;

		DataOutputStream salida = null;
		ObjectInputStream entradaObjeto = null;

		try {
			salida = new DataOutputStream(socket.getOutputStream());
			salida.writeUTF("8");
			salida.flush();

			salida.writeInt(id);
			salida.flush();

			entradaObjeto = new ObjectInputStream(socket.getInputStream());
			num = (Numero) entradaObjeto.readObject();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return num;
	}

}
