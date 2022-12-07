package Vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import modelo.Coleccion;
import modelo.Modelo_tabla_colecciones;

import javax.swing.JTextField;
import java.awt.Component;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.UIManager;
import java.awt.Font;
import javax.swing.JComboBox;

public class Gestion_colecciones extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtFiltro;
	private JTable tbl_colecciones;
	private JButton btnEliminar_coleccion;
	private JButton btnCrear_coleccion;
	private JLabel lblfoto;
	private ArrayList<Coleccion> colecciones;
	private String[] idiomas = { "ES", "EN" };
	private JButton btnModif_coleccion;
	private JButton btnMostrarTodo;
	private JButton btnFiltrar;
	private JButton btnVerPorColeccion;
	private JButton btnVolver;

	public static void main(String[] args) {
		try {
			Gestion_colecciones dialog = new Gestion_colecciones(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Gestion_colecciones(Socket sc) throws ClassNotFoundException, UnknownHostException, IOException {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				if (tbl_colecciones.getSelectedRow() == -1) {

					lblfoto.setIcon(new ImageIcon(
							Gestion_colecciones.class.getResource("/imagenes_placeholders/placeholder_claro.png")));
				}
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {

			}
		});
		setTitle("Colecciones de C\u00F3mics");
		setBounds(100, 100, 1036, 517);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBackground(Color.DARK_GRAY);
			FlowLayout flowLayout = (FlowLayout) panel.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			contentPanel.add(panel, BorderLayout.NORTH);
			{
				btnMostrarTodo = new JButton("Mostrar Todo");
				btnMostrarTodo.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

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
								colecciones = crearSocketPedirColecciones(new Socket(ip, 9999));
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

						tbl_colecciones.setModel(new Modelo_tabla_colecciones(colecciones));
					}
				});
				panel.add(btnMostrarTodo);
			}
			{
				Component horizontalStrut = Box.createHorizontalStrut(20);
				panel.add(horizontalStrut);
			}
			{
				Component horizontalStrut = Box.createHorizontalStrut(20);
				panel.add(horizontalStrut);
			}
			{
				txtFiltro = new JTextField();
				panel.add(txtFiltro);
				txtFiltro.setColumns(10);
			}
			{
				btnFiltrar = new JButton("Filtrar");
				btnFiltrar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						String filtro = txtFiltro.getText();
						ArrayList<Coleccion> colecciones_aux = new ArrayList<>();

						for (int i = 0; i < colecciones.size(); i++) {
							if (colecciones.get(i).getTitulo().startsWith(filtro)) {
								colecciones_aux.add(colecciones.get(i));
							}
						}

						tbl_colecciones.setModel(new Modelo_tabla_colecciones(colecciones_aux));
					}
				});
				panel.add(btnFiltrar);
			}
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {

				}
			});
			contentPanel.add(scrollPane, BorderLayout.WEST);
			{
				tbl_colecciones = new JTable();
				tbl_colecciones.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
				tbl_colecciones.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {

						int id = (int) tbl_colecciones.getModel().getValueAt(tbl_colecciones.getSelectedRow(), 1);
						System.out.println(id);

						for (int i = 0; i < colecciones.size(); i++) {
							if (colecciones.get(i).getId() == id) {
								if (colecciones.get(i).getFoto() != null) {
									Image image = colecciones.get(i).getFoto().getImage();
									Image reescalada = image.getScaledInstance(550, 400, java.awt.Image.SCALE_SMOOTH);
									ImageIcon imageIcon = new ImageIcon(reescalada);
									lblfoto.setIcon(imageIcon);
								}
								if (colecciones.get(i).getFoto() == null) {
									lblfoto.setIcon(new ImageIcon(Gestion_colecciones.class
											.getResource("/imagenes_placeholders/placeholder_claro.png")));
									return;
								}

							}
						}
					}
				});
				scrollPane.setViewportView(tbl_colecciones);

				ArrayList<Coleccion> listacolecciones = crearSocketPedirColecciones(sc);
				colecciones = listacolecciones;
				tbl_colecciones.setModel(new modelo.Modelo_tabla_colecciones(listacolecciones));

			}
		}
		{
			JPanel panel_imagenes = new JPanel();
			panel_imagenes.setBackground(Color.DARK_GRAY);
			contentPanel.add(panel_imagenes, BorderLayout.CENTER);
			panel_imagenes.setLayout(new BorderLayout(0, 0));
			{
				lblfoto = new JLabel("");
				lblfoto.setHorizontalAlignment(SwingConstants.CENTER);
				lblfoto.setIcon(new ImageIcon(
						Gestion_colecciones.class.getResource("/imagenes_placeholders/placeholder_claro.png")));
				lblfoto.setMinimumSize(new Dimension(500, 200));
				lblfoto.setMaximumSize(new Dimension(500, 200));
				lblfoto.setPreferredSize(new Dimension(500, 200));
				lblfoto.setSize(new Dimension(500, 200));
				panel_imagenes.add(lblfoto, BorderLayout.CENTER);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.DARK_GRAY);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnEliminar_coleccion = new JButton("Eliminar Colecci\u00F3n");
				btnEliminar_coleccion.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (tbl_colecciones.getSelectedRow() == -1) {

							JOptionPane.showMessageDialog(btnEliminar_coleccion,
									"Para eliminar una colecci�n, primero debes seleccionarla en la tabla.");
							return;
						}

						int respuesta = JOptionPane.showConfirmDialog(btnEliminar_coleccion,
								"Seguro que quieres eliminar esta colecci�n? \n Se eliminar�n todos los ejemplares que pertenecen a esta colecci�n.");

						if (respuesta == JOptionPane.YES_OPTION) {
							int id = (int) tbl_colecciones.getModel().getValueAt(tbl_colecciones.getSelectedRow(), 1);

							int eliminado = CrearSocketEliminarColeccion(id);

							if (eliminado == 1) {

								try {
									colecciones = crearSocketPedirColecciones(ObtenerSocket());
								} catch (ClassNotFoundException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

								tbl_colecciones.setModel(new Modelo_tabla_colecciones(colecciones));

							} else {
								return;
							}
						}
					};
				});
				{
					btnCrear_coleccion = new JButton("Nueva Colecci\u00F3n");
					btnCrear_coleccion.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							Crear_Coleccion dia = new Crear_Coleccion(sc);
							dia.setVisible(true);
							dispose();
						}
					});
					buttonPane.add(btnCrear_coleccion);
				}
				{
					btnModif_coleccion = new JButton("Modificar Colecci\u00F3n");
					btnModif_coleccion.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {

							if (tbl_colecciones.getSelectedRow() == -1) {

								JOptionPane.showMessageDialog(btnModif_coleccion,
										"Para modificar una colecci�n, primero debes seleccionarla.");
								return;
							}

							int id = (int) tbl_colecciones.getModel().getValueAt(tbl_colecciones.getSelectedRow(), 1);
							Coleccion colecc = colecciones.get(tbl_colecciones.getSelectedRow());

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

							Modificar_coleccion dia = new Modificar_coleccion(colecc);

							dia.setVisible(true);

							dispose();
						}
					});
					buttonPane.add(btnModif_coleccion);
				}
				buttonPane.add(btnEliminar_coleccion);
			}
			{
				Component horizontalStrut = Box.createHorizontalStrut(20);
				buttonPane.add(horizontalStrut);
			}
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
				{
					btnVerPorColeccion = new JButton("Ver C\u00F3mics de la Colecci\u00F3n");
					btnVerPorColeccion.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {

							if (tbl_colecciones.getSelectedRow() == -1) {
								JOptionPane.showMessageDialog(btnVerPorColeccion,
										"Para ver los ejemplares de una colecci�n primero debes seleccionarla en la tabla.");
								return;
							}

							int id = (int) tbl_colecciones.getModel().getValueAt(tbl_colecciones.getSelectedRow(), 1);
							Gestion_Numeros dia = null;
							try {
								dia = new Gestion_Numeros(id, ObtenerSocket());
							} catch (ClassNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							dia.setVisible(true);
							dispose();
						}
					});
					buttonPane.add(btnVerPorColeccion);
				}
				{
					Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
					buttonPane.add(rigidArea);
				}
				btnVolver.setActionCommand("Cancel");
				buttonPane.add(btnVolver);
			}
		}
		traducir();
	}

	public void traducir() {

		ResourceBundle rb = ResourceBundle.getBundle("traduccion.config");

		btnMostrarTodo.setText(rb.getString("btnMostrarTodo"));
		btnFiltrar.setText(rb.getString("btnFiltrar"));
		btnCrear_coleccion.setText(rb.getString("btnCrear_Coleccion"));
		btnModif_coleccion.setText(rb.getString("btnModif_coleccion"));
		btnEliminar_coleccion.setText(rb.getString("btnEliminar_coleccion"));
		btnVerPorColeccion.setText(rb.getString("btnVerPorColeccion"));
		btnVolver.setText(rb.getString("btnVolver"));

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

	public int CrearSocketEliminarColeccion(int id) {

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
			return -1;
		}

		Socket socket = null;

		try {
			socket = new Socket(ip, 9999);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(getContentPane(), "No se ha podido conectar con el servidor.");
			return -1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int resultado = 0;

		DataOutputStream out;
		ObjectOutputStream objectOut;
		DataInputStream in;

		try {

			out = new DataOutputStream(socket.getOutputStream());
			out.flush();
			out.writeUTF("10");
			out.flush();
			out.writeInt(id);
			out.flush();

			in = new DataInputStream(socket.getInputStream());
			resultado = in.readInt();
			socket.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return resultado;
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
