package Vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import modelo.Coleccion;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JComboBox;

public class Modificar_coleccion extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtTitulo;
	private JLabel lblfoto;
	private File archivo;
	private Image newImage;
	private String imagen;
	private ImageIcon imageicon;
	private String[] idiomas = { "ES", "EN" };
	private JLabel lblTitulo;
	private JButton btnSeleccionarFoto;
	private JButton btnModificar;
	private JButton btnCancelar;
	private JComboBox cmbIdiomas = new JComboBox();

	public static void main(String[] args) {
		try {
			Modificar_coleccion dialog = new Modificar_coleccion();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Modificar_coleccion() {
		setTitle("Modificar Colecci\u00F3n");
		setBounds(100, 100, 651, 555);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.SOUTH);
			panel.setLayout(new BorderLayout(0, 0));
			{
				JPanel panel_1 = new JPanel();
				panel_1.setBackground(Color.DARK_GRAY);
				panel.add(panel_1, BorderLayout.NORTH);
				{
					btnSeleccionarFoto = new JButton(" Seleccionar Imagen (.jpg)");
					btnSeleccionarFoto.addActionListener(new ActionListener() {
						private File archivo;
						private Image newImage;
						private String imagen;
						private ImageIcon imageicon;

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
					panel_1.add(btnSeleccionarFoto);
				}
			}
			{
				JPanel panel_1 = new JPanel();
				panel_1.setBackground(Color.DARK_GRAY);
				panel.add(panel_1, BorderLayout.SOUTH);
				{
					lblTitulo = new JLabel("Titulo: ");
					lblTitulo.setForeground(Color.WHITE);
					panel_1.add(lblTitulo);
				}
				{
					txtTitulo = new JTextField();
					panel_1.add(txtTitulo);
					txtTitulo.setColumns(10);
				}
			}
		}
		{
			JPanel panel = new JPanel();
			panel.setBackground(Color.DARK_GRAY);
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(new BorderLayout(0, 0));
			{
				lblfoto = new JLabel("");
				lblfoto.setBackground(Color.DARK_GRAY);
				lblfoto.setSize(new Dimension(400, 250));
				lblfoto.setMinimumSize(new Dimension(400, 250));
				lblfoto.setMaximumSize(new Dimension(400, 250));
				lblfoto.setPreferredSize(new Dimension(400, 250));
				lblfoto.setIcon(new ImageIcon(
						Modificar_coleccion.class.getResource("/imagenes_placeholders/placeholder_claro.png")));
				panel.add(lblfoto, BorderLayout.CENTER);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.DARK_GRAY);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnModificar = new JButton("Modificar");
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
						Properties prop = null;
						String ip;
						try (InputStream os = new FileInputStream("./conexion_ip.properties")) {
							prop = new Properties();
							prop.load(os);
							ip = prop.getProperty("IP");
							os.close();

						} catch (Exception e1) {
							e1.printStackTrace();
							System.out.print("No se encontr� el archivo properties");
							return;
						}

						try {
							Gestion_colecciones dia = new Gestion_colecciones(new Socket(ip, 9999));
							dia.setVisible(true);
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

						dispose();
					}
				});
				btnCancelar.setActionCommand("Cancel");
				buttonPane.add(btnCancelar);
			}
		}
	}

	public Modificar_coleccion(Coleccion colecc) {
		setTitle("Modificar Colecci\u00F3n");
		setBounds(100, 100, 651, 512);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.SOUTH);
			contentPanel.setBackground(Color.DARK_GRAY);
			panel.setLayout(new BorderLayout(0, 0));
			{
				JPanel panel_1 = new JPanel();
				panel_1.setBackground(Color.DARK_GRAY);
				panel.add(panel_1, BorderLayout.NORTH);
				{
					btnSeleccionarFoto = new JButton("Seleccionar Foto");
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
					panel_1.add(btnSeleccionarFoto);
				}
			}
			{
				JPanel panel_1 = new JPanel();
				panel_1.setBackground(Color.DARK_GRAY);
				panel.add(panel_1, BorderLayout.SOUTH);
				{
					lblTitulo = new JLabel("Titulo: ");
					lblTitulo.setForeground(Color.WHITE);
					panel_1.add(lblTitulo);
				}
				{
					txtTitulo = new JTextField();
					panel_1.add(txtTitulo);
					txtTitulo.setColumns(10);
				}
			}
		}
		{
			JPanel panel = new JPanel();
			panel.setBackground(Color.DARK_GRAY);
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(new BorderLayout(0, 0));
			{
				lblfoto = new JLabel("");
				lblfoto.setIcon(new ImageIcon(
						Modificar_coleccion.class.getResource("/imagenes_placeholders/Placeholder_grande.png")));
				panel.add(lblfoto);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.DARK_GRAY);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				cmbIdiomas = new JComboBox();
				cmbIdiomas.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent e) {

						if (cmbIdiomas.getSelectedItem().equals("ES")) {
							Locale.setDefault(new Locale("es", "ES"));
							traducir();
						}

						if (cmbIdiomas.getSelectedItem().equals("EN")) {
							Locale.setDefault(new Locale("en", "GB"));
							traducir();

						}

					}
				});
				cmbIdiomas.setModel(new DefaultComboBoxModel(idiomas));
				buttonPane.add(cmbIdiomas);
			}
			{
				btnModificar = new JButton("Modificar");
				btnModificar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						boolean validar = true;
						String mensaje = "";

						if (txtTitulo.getText().isEmpty() || txtTitulo.getText().length() > 120) {
							validar = false;
							mensaje = mensaje + "El T�tulo est� vac�o o es demasiado largo. \n";
						}

						if (archivo == null) {
							validar = false;
							mensaje = mensaje + "No se ha seleccionado una foto \n";
						}

						if (validar) {
							boolean modificado = false;

							Coleccion coleccion = new Coleccion(colecc.getId(), txtTitulo.getText(), imageicon);
							try {
								modificado = CrearSocketModificarColeccion(coleccion);
							} catch (IOException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
							if (modificado) {
								JOptionPane.showMessageDialog(getContentPane(), "Modificado con �xito");

								Gestion_colecciones dia = null;

								Properties prop = null;
								String ip;
								try (InputStream os = new FileInputStream("./conexion_ip.properties")) {
									prop = new Properties();
									prop.load(os);
									ip = prop.getProperty("IP");
									os.close();

								} catch (Exception e1) {
									e1.printStackTrace();
									System.out.print("No se encontr� el archivo properties");
									return;
								}

								try {
									dia = new Gestion_colecciones(new Socket(ip, 9999));
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

								dia.setVisible(true);
								dispose();
							}

						} else {
							JOptionPane.showMessageDialog(btnModificar, mensaje);
						}

					}
				});
				btnModificar.setActionCommand("OK");
				buttonPane.add(btnModificar);
				buttonPane.setBackground(Color.DARK_GRAY);
				getRootPane().setDefaultButton(btnModificar);
			}
			{
				btnCancelar = new JButton("Cancelar");
				btnCancelar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						Gestion_colecciones dia = null;
						try {
							Properties prop = null;
							String ip;
							try (InputStream os = new FileInputStream("./conexion_ip.properties")) {
								prop = new Properties();
								prop.load(os);
								ip = prop.getProperty("IP");
								os.close();

							} catch (Exception e1) {
								e1.printStackTrace();
								System.out.print("No se encontr� el archivo properties");
								return;

							}

							try {
								dia = new Gestion_colecciones(new Socket(ip, 9999));
							} catch (UnknownHostException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
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

		colocar_datos(colecc.getId());
		traducir();
	}

	public void colocar_datos(int id) {

		Coleccion coleccion = null;
		try {
			coleccion = crearSocketRecuperarColeccion(id);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		txtTitulo.setText(coleccion.getTitulo());
		if (coleccion.getFoto() == null) {
			return;
		}

		lblfoto.setIcon(coleccion.getFoto());

	}

	public void traducir() {

		ResourceBundle rb = ResourceBundle.getBundle("traduccion.config");

		lblTitulo.setText(rb.getString("lblTitulo"));
		btnSeleccionarFoto.setText(rb.getString("btnSeleccionarFoto"));
		btnModificar.setText(rb.getString("btnModificar"));
		btnCancelar.setText(rb.getString("btnCancelar"));

	}

	public Coleccion crearSocketRecuperarColeccion(int id) throws ClassNotFoundException {

		Properties prop = null;
		String ip;
		try (InputStream os = new FileInputStream("./conexion_ip.properties")) {
			prop = new Properties();
			prop.load(os);
			ip = prop.getProperty("IP");
			os.close();

		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.print("No se encontr� el archivo properties");
			return null;

		}

		Socket socket = null;
		Coleccion coleccion = null;

		try {
			socket = new Socket(ip, 9999);

			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			out.writeUTF("3");

			out.flush();

			out.writeInt(id);

			ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());

			coleccion = (Coleccion) objectInput.readObject();

			socket.close();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(getContentPane(), "No se ha podido conectar con el servidor.");
			return null;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return coleccion;

	}

	public boolean CrearSocketModificarColeccion(Coleccion colecc) throws IOException {

		Properties prop = null;
		String ip;
		try (InputStream os = new FileInputStream("./conexion_ip.properties")) {
			prop = new Properties();
			prop.load(os);
			ip = prop.getProperty("IP");
			os.close();

		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.print("No se encontr� el archivo properties");
			return false;

		}

		Socket socket = null;
		Coleccion coleccion = null;
		boolean modificado = false;
		byte[] bytes = PasarImagenaBytes(archivo);

		try {
			socket = new Socket(ip, 9999);

			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			out.writeUTF("4");
			out.flush();

			ObjectOutputStream salida_coleccion = new ObjectOutputStream(socket.getOutputStream());
			salida_coleccion.writeObject(colecc);

			salida_coleccion.flush();

			out.writeInt(bytes.length);
			out.flush();

			for (int i = 0; i < bytes.length; i++) {
				out.writeByte(bytes[i]);
			}

			DataInputStream entrada_bool = new DataInputStream(socket.getInputStream());
			modificado = entrada_bool.readBoolean();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(getContentPane(), "No se ha podido conectar con el servidor.");
			return false;
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

}
