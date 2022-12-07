package Vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import modelo.Coleccion;
import modelo.Modelo_tabla_colecciones;
import modelo.Modelo_tabla_numeros;
import modelo.Numero;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JComboBox;

public class Gestion_Numeros extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JButton btnVolver;
	private JTextField txtFiltro;
	private JTable tblNumeros;
	private JLabel lblfoto;
	private ArrayList<Numero> listaNumeros;
	private JButton btnMostrarTodo;
	private JButton btnFiltrar;
	private JButton btnEliminarComic;
	private JButton btnModificarComic;
	private JButton btnNuevoComic;
	private String[] idiomas = { "ES", "EN" };

	public static void main(String[] args) {
		try {
			Gestion_Numeros dialog = new Gestion_Numeros(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Gestion_Numeros(Socket sc) throws ClassNotFoundException {
		getContentPane().setBackground(new Color(175, 238, 238));
		setTitle("Ver C\u00F3mics");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {

				if (tblNumeros.getSelectedRow() == -1) {
					lblfoto.setIcon(new ImageIcon(
							Gestion_Numeros.class.getResource("/imagenes_placeholders/placeholder_claro.png")));
				}
			}
		});
		setBounds(100, 100, 1219, 519);
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
						tblNumeros.setModel(new Modelo_tabla_numeros(listaNumeros));
					}
				});
				panel.add(btnMostrarTodo);
			}
			{
				Component horizontalStrut = Box.createHorizontalStrut(20);
				panel.add(horizontalStrut);
			}
			{
				Box horizontalBox = Box.createHorizontalBox();
				panel.add(horizontalBox);
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
						ArrayList<Numero> numeros_aux = new ArrayList<>();

						for (int i = 0; i < listaNumeros.size(); i++) {
							if (listaNumeros.get(i).getTitulo().toLowerCase().contains(filtro.toLowerCase())) {
								numeros_aux.add(listaNumeros.get(i));
							}
						}

						tblNumeros.setModel(new Modelo_tabla_numeros(numeros_aux));
					}
				});
				panel.add(btnFiltrar);
			}
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				tblNumeros = new JTable();
				tblNumeros.setFont(new Font("Comic Sans MS", Font.ITALIC, 13));
				tblNumeros.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
				tblNumeros.setBackground(Color.WHITE);
				listaNumeros = crearSocketPedirNumeros(sc);
				tblNumeros.setModel(new modelo.Modelo_tabla_numeros(listaNumeros));
				tblNumeros.addMouseListener(new MouseAdapter() {

					@Override
					public void mouseClicked(MouseEvent e) {
						int id = (int) tblNumeros.getModel().getValueAt(tblNumeros.getSelectedRow(), 4);
						System.out.println(id);

						for (int i = 0; i < listaNumeros.size(); i++) {
							if (listaNumeros.get(i).getId() == id) {
								Image image = listaNumeros.get(i).getPortada().getImage();
								Image reescalada = image.getScaledInstance(550, 400, java.awt.Image.SCALE_SMOOTH);
								ImageIcon imageIcon = new ImageIcon(reescalada);
								lblfoto.setIcon(imageIcon);
							}
						}

					}
				});
				scrollPane.setViewportView(tblNumeros);

			}
		}
		{
			try {
				ArrayList<Numero> lista_numeros = crearSocketPedirNumeros(ObtenerSocket());
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		{
			JPanel panel = new JPanel();
			panel.setBackground(Color.DARK_GRAY);
			contentPanel.add(panel, BorderLayout.EAST);
			panel.setLayout(new BorderLayout(0, 0));
			{
				lblfoto = new JLabel("");
				lblfoto.setAlignmentX(Component.CENTER_ALIGNMENT);
				lblfoto.setBackground(Color.DARK_GRAY);
				lblfoto.setMinimumSize(new Dimension(500, 800));
				lblfoto.setMaximumSize(new Dimension(500, 800));
				lblfoto.setPreferredSize(new Dimension(500, 800));
				lblfoto.setSize(new Dimension(500, 800));
				lblfoto.setIcon(new ImageIcon(
						Gestion_Numeros.class.getResource("/imagenes_placeholders/placeholder_claro.png")));
				panel.add(lblfoto);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.DARK_GRAY);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				{
					btnNuevoComic = new JButton("Nuevo C\u00F3mic");
					btnNuevoComic.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							Crear_Numero dia = null;
							try {
								dia = new Crear_Numero(ObtenerSocket());
							} catch (ClassNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							dia.setVisible(true);
							dispose();
						}
					});
					{

					}

					buttonPane.add(btnNuevoComic);
				}
				{
					btnModificarComic = new JButton("Modificar C\u00F3mic");
					btnModificarComic.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (tblNumeros.getSelectedRow() == -1) {
								JOptionPane.showMessageDialog(btnModificarComic,
										"Para Modificar un C�mic primero debes seleccionar uno en la tabla.");
								return;
							}

							int id = (int) tblNumeros.getModel().getValueAt(tblNumeros.getSelectedRow(), 4);

							Modificar_numero dia = null;
							try {
								dia = new Modificar_numero(id, ObtenerSocket());
							} catch (ClassNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							dia.setVisible(true);
							dispose();
						}
					});
					buttonPane.add(btnModificarComic);
				}
				{
					btnEliminarComic = new JButton("Eliminar C\u00F3mic");
					btnEliminarComic.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (tblNumeros.getSelectedRow() == -1) {

								JOptionPane.showMessageDialog(btnEliminarComic,
										"Para eliminar un c�mic, primero debes seleccionarlo en la tabla.");
								return;
							}

							int respuesta = JOptionPane.showConfirmDialog(btnEliminarComic,
									"Seguro que quieres eliminar este ejemplar?");
							int eliminado = -1;
							if (respuesta == JOptionPane.YES_OPTION) {
								int id = (int) tblNumeros.getModel().getValueAt(tblNumeros.getSelectedRow(), 4);

								eliminado = CrearSocketEliminarNumero(id);
								if (eliminado == 1) {
									JOptionPane.showMessageDialog(getContentPane(), "Numero Eliminado con �xito");

									ArrayList<Numero> numeros = null;
									try {
										numeros = crearSocketPedirNumeros(ObtenerSocket());
									} catch (ClassNotFoundException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}

									tblNumeros.setModel(new Modelo_tabla_numeros(numeros));

								}

							} else {
								return;
							}
						}
					});
					buttonPane.add(btnEliminarComic);

				}
				{
					Component horizontalStrut = Box.createHorizontalStrut(20);
					buttonPane.add(horizontalStrut);
				}
				{
					Component horizontalStrut = Box.createHorizontalStrut(20);
					buttonPane.add(horizontalStrut);
				}
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
				btnVolver.setActionCommand("Cancel");
				buttonPane.add(btnVolver);
			}
			traducir();
		}
	}

	public Gestion_Numeros(int id, Socket sc) throws ClassNotFoundException {
		setTitle("Ver C\u00F3mics");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {

				if (tblNumeros.getSelectedRow() == -1) {
					lblfoto.setIcon(new ImageIcon(
							Gestion_Numeros.class.getResource("/imagenes_placeholders/placeholder_claro.png")));
				}
			}
		});
		setBounds(100, 100, 1219, 519);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panel.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			panel.setBackground(Color.DARK_GRAY);
			contentPanel.add(panel, BorderLayout.NORTH);
			{
				btnMostrarTodo = new JButton("Mostrar Todo");
				btnMostrarTodo.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						tblNumeros.setModel(new Modelo_tabla_numeros(listaNumeros));
					}
				});
				panel.add(btnMostrarTodo);
			}
			{
				Component horizontalStrut = Box.createHorizontalStrut(20);
				panel.add(horizontalStrut);
			}
			{
				Box horizontalBox = Box.createHorizontalBox();
				panel.add(horizontalBox);
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
						ArrayList<Numero> numeros_aux = new ArrayList<>();

						for (int i = 0; i < listaNumeros.size(); i++) {
							if (listaNumeros.get(i).getTitulo().startsWith(filtro)
									&& listaNumeros.get(i).getId_coleccion() == id) {
								numeros_aux.add(listaNumeros.get(i));
							}
						}

						tblNumeros.setModel(new Modelo_tabla_numeros(numeros_aux));
					}
				});
				panel.add(btnFiltrar);
			}
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.WEST);
			{
				tblNumeros = new JTable();
				ArrayList<Numero> Numeros_aux = new ArrayList();
				listaNumeros = crearSocketPedirNumeros(sc);
				for (int i = 0; i < listaNumeros.size(); i++) {
					if (listaNumeros.get(i).getId_coleccion() == id) {
						Numeros_aux.add(listaNumeros.get(i));
					}
				}

				tblNumeros.setModel(new modelo.Modelo_tabla_numeros(Numeros_aux));
				tblNumeros.addMouseListener(new MouseAdapter() {

					@Override
					public void mouseClicked(MouseEvent e) {
						int id = (int) tblNumeros.getModel().getValueAt(tblNumeros.getSelectedRow(), 4);
						System.out.println(id);

						for (int i = 0; i < listaNumeros.size(); i++) {
							if (listaNumeros.get(i).getId() == id) {
								Image image = listaNumeros.get(i).getPortada().getImage();
								Image reescalada = image.getScaledInstance(550, 400, java.awt.Image.SCALE_SMOOTH);
								ImageIcon imageIcon = new ImageIcon(reescalada);
								lblfoto.setIcon(imageIcon);
							}
						}

					}
				});
				scrollPane.setViewportView(tblNumeros);

			}
		}
		{
			ArrayList<Numero> lista_numeros = crearSocketPedirNumeros(sc);

		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setBackground(Color.DARK_GRAY);
			panel.setLayout(new BorderLayout(0, 0));
			{
				lblfoto = new JLabel("");
				lblfoto.setIcon(new ImageIcon(
						Gestion_Numeros.class.getResource("/imagenes_placeholders/placeholder_claro.png")));
				panel.add(lblfoto);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.DARK_GRAY);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				{
					btnNuevoComic = new JButton("Nuevo C\u00F3mic");
					btnNuevoComic.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							Crear_Numero dia = null;
							try {
								dia = new Crear_Numero(ObtenerSocket());
							} catch (ClassNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							dia.setVisible(true);
							dispose();
						}
					});

					buttonPane.add(btnNuevoComic);
				}
				{
					btnModificarComic = new JButton("Modificar C\u00F3mic");
					btnModificarComic.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (tblNumeros.getSelectedRow() == -1) {
								JOptionPane.showMessageDialog(btnModificarComic,
										"Para Modificar un C�mic primero debes seleccionar uno en la tabla.");
								return;
							}

							int id = (int) tblNumeros.getModel().getValueAt(tblNumeros.getSelectedRow(), 4);

							Modificar_numero dia = null;
							try {
								dia = new Modificar_numero(id, ObtenerSocket());
							} catch (ClassNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							dia.setVisible(true);
							dispose();
						}
					});
					buttonPane.add(btnModificarComic);
				}
				{
					btnEliminarComic = new JButton("Eliminar C\u00F3mic");
					btnEliminarComic.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (tblNumeros.getSelectedRow() == -1) {

								JOptionPane.showMessageDialog(btnEliminarComic,
										"Para eliminar un c�mic, primero debes seleccionarlo en la tabla.");
								return;
							}

							int respuesta = JOptionPane.showConfirmDialog(btnEliminarComic,
									"Seguro que quieres eliminar este ejemplar?");

							if (respuesta == JOptionPane.YES_OPTION) {
								int id = (int) tblNumeros.getModel().getValueAt(tblNumeros.getSelectedRow(), 4);

								int eliminado = CrearSocketEliminarNumero(id);
								if (eliminado == 1) {
									ArrayList<Numero> listaNum_eliminado = null;
									try {
										listaNum_eliminado = crearSocketPedirNumeros(ObtenerSocket());
									} catch (ClassNotFoundException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}

									tblNumeros.setModel(new Modelo_tabla_numeros(listaNum_eliminado));
								}
							} else {
								return;
							}
						}
					});
					buttonPane.add(btnEliminarComic);
				}
				{
					Component horizontalStrut = Box.createHorizontalStrut(20);
					buttonPane.add(horizontalStrut);
				}
				{
					Component horizontalStrut = Box.createHorizontalStrut(20);
					buttonPane.add(horizontalStrut);
				}
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
				btnVolver.setActionCommand("Cancel");
				buttonPane.add(btnVolver);
			}
		}
		traducir();
	}

	public void traducir() {

		ResourceBundle rb = ResourceBundle.getBundle("traduccion.config");

		btnFiltrar.setText(rb.getString("btnFiltrar"));
		btnMostrarTodo.setText(rb.getString("btnMostrarTodo"));
		btnNuevoComic.setText(rb.getString("btnNuevoComic"));
		btnModificarComic.setText(rb.getString("btnModificarComic"));
		btnEliminarComic.setText(rb.getString("btnEliminarComic"));
		btnVolver.setText(rb.getString("btnVolver"));

	}

	public ArrayList<Numero> crearSocketPedirNumeros(Socket sc) throws ClassNotFoundException {

		DataOutputStream outputnumeros;
		ObjectInputStream objectInput;
		ArrayList<Numero> listanumeros = null;
		Socket socket = null;

		try {

			outputnumeros = new DataOutputStream(sc.getOutputStream());

			outputnumeros.writeUTF("6");
			outputnumeros.flush();

			objectInput = new ObjectInputStream(sc.getInputStream());
			listanumeros = (ArrayList<Numero>) objectInput.readObject();

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return listanumeros;

	}

	public int CrearSocketEliminarNumero(int id) {

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
			out.writeUTF("11");
			out.flush();
			out.writeInt(id);
			out.flush();

			in = new DataInputStream(socket.getInputStream());
			resultado = in.readInt();

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
