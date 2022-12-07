package Vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import modelo.Coleccion;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Dimension;
import javax.swing.ImageIcon;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Insets;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import java.awt.Component;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;

import java.awt.Color;
import javax.swing.JComboBox;

public class Crear_Coleccion extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel lblfoto;
	private String imagen;
	private Image newImage;
	private File archivo;
	private JTextField txtTitulo;
	private byte[] foto;
	private ImageIcon imageicon;
	private String[] idiomas = { "ES", "EN" };
	private JButton btnVolver;
	private JButton btnCrear_coleccion;
	private JButton btnSeleccionarFoto;
	private JLabel lblTitulo;

	public static void main(String[] args) {
		try {
			Crear_Coleccion dialog = new Crear_Coleccion(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Crear_Coleccion(Socket sc) {
		System.out.println("Puerto en crear coleccion" + sc.getLocalPort());
		setTitle("Crear Colecci\u00F3n");
		setBounds(100, 100, 593, 636);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setBackground(Color.DARK_GRAY);
			panel.setLayout(new BorderLayout(0, 0));
			{
				lblfoto = new JLabel("");
				lblfoto.setBackground(Color.DARK_GRAY);
				lblfoto.setPreferredSize(new Dimension(500, 350));
				lblfoto.setIcon(new ImageIcon(
						Crear_Coleccion.class.getResource("/imagenes_placeholders/placeholder_claro.png")));
				lblfoto.setSize(new Dimension(500, 350));
				lblfoto.setMinimumSize(new Dimension(500, 350));
				lblfoto.setMaximumSize(new Dimension(500, 350));
				panel.add(lblfoto);
			}
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.SOUTH);
			panel.setLayout(new BorderLayout(0, 0));
			{
				JPanel panel_1 = new JPanel();
				panel_1.setBackground(Color.DARK_GRAY);
				panel.add(panel_1, BorderLayout.NORTH);
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
					panel_1.add(btnSeleccionarFoto);
				}
			}
			{
				JPanel panel_1 = new JPanel();
				panel_1.setBackground(Color.DARK_GRAY);
				panel.add(panel_1, BorderLayout.CENTER);
				{
					lblTitulo = new JLabel("T\u00EDtulo : ");
					lblTitulo.setForeground(Color.WHITE);
					panel_1.add(lblTitulo);
				}
				{
					txtTitulo = new JTextField();
					txtTitulo.setMinimumSize(new Dimension(100, 20));
					panel_1.add(txtTitulo);
					txtTitulo.setColumns(10);
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.DARK_GRAY);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnCrear_coleccion = new JButton("Crear Colecci\u00F3n");
				btnCrear_coleccion.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						boolean validado = true;
						String titulo = txtTitulo.getText();
						String mensaje = "";

						if (titulo.length() == 0 || titulo.length() > 120) {
							validado = false;
							mensaje = mensaje + "No se ha escrito t�tulo, o es demasiado largo. \n";
						}

						if (validado) {
							Coleccion coleccion = new Coleccion(0, titulo, null);
							int id = 0;
							try {
								try {
									id = crearSocketCrearColeccion(coleccion);
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							} catch (ClassNotFoundException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}

							if (id != -1) {
								JOptionPane.showMessageDialog(btnCrear_coleccion, "Colecci�n creada con �xito");

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

							} else {
								JOptionPane.showMessageDialog(btnCrear_coleccion,
										"Ha ocurrido un error, no se ha podido guardar la colecci�n. \n ");
								return;
							}

						} else {
							JOptionPane.showMessageDialog(btnCrear_coleccion,
									"La colecci�n no ha podido ser creada. \n" + mensaje);
							return;
						}

					}
				});
				btnCrear_coleccion.setActionCommand("OK");
				buttonPane.add(btnCrear_coleccion);
				getRootPane().setDefaultButton(btnCrear_coleccion);
			}
			{
				btnVolver = new JButton("Volver");
				btnVolver.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
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
				});
				btnVolver.setActionCommand("Cancel");
				buttonPane.add(btnVolver);
			}
		}
		traducir();
	}

	public void traducir() {

		ResourceBundle rb = ResourceBundle.getBundle("traduccion.config");

		lblTitulo.setText(rb.getString("lblTitulo"));
		btnSeleccionarFoto.setText(rb.getString("btnSeleccionarFoto"));
		btnCrear_coleccion.setText(rb.getString("btnCrear_Coleccion"));
		btnVolver.setText(rb.getString("btnVolver"));

	}

	public int crearSocketCrearColeccion(Coleccion colecc) throws ClassNotFoundException, IOException {

		Socket socket = null;
		Properties prop = null;
		String ip;
		byte bytes[] = null;
		bytes = PasarImagenaBytes(archivo);

		try (InputStream os = new FileInputStream("./conexion_ip.properties")) {
			prop = new Properties();
			prop.load(os);
			ip = prop.getProperty("IP");
			os.close();

		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.print("No se encontr� el archivo properties");
			return -1;
		}

		try {
			socket = new Socket(ip, 9999);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(getContentPane(), "No se ha podido conectar con el servidor.");
			return -1;
			// TODO Auto-generated catch block

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int id = 0;

		DataOutputStream out;
		ObjectOutputStream objectOut;
		DataInputStream in;

		try {
			System.out.println("Puerto" + socket.getLocalPort());
			out = new DataOutputStream(socket.getOutputStream());
			out.flush();
			out.writeUTF("2");
			out.flush();
			System.out.println("Escribiendo un 2");

			objectOut = new ObjectOutputStream(socket.getOutputStream());
			objectOut.writeObject(colecc);
			objectOut.flush();

			out.writeInt(bytes.length);
			out.flush();

			for (int i = 0; i < bytes.length; i++) {
				out.writeByte(bytes[i]);
			}

			in = new DataInputStream(socket.getInputStream());
			id = in.readInt();

			socket.close();

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

}
