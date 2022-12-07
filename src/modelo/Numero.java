package modelo;

import java.io.Serializable;
import java.util.Date;

import javax.swing.ImageIcon;

public class Numero implements Serializable {

	private int id;
	private String titulo;
	private String sinopsis;
	private double precio;
	private String estado;
	private Date fecha_publicacion;
	private int id_coleccion;
	private ImageIcon portada;

	public Numero(int id, String titulo, String sinopsis, double precio, String estado,
			Date fecha_publicacion, int id_coleccion, ImageIcon portada) {
		this.id = id;
		this.titulo = titulo;
		this.sinopsis = sinopsis;
		this.precio = precio;
		this.estado = estado;
		this.fecha_publicacion = fecha_publicacion;
		this.id_coleccion = id_coleccion;
		this.portada = portada;
	}

	public Numero() {
		// TODO Auto-generated constructor stub
	}

	public int getId_coleccion() {
		return id_coleccion;
	}

	public void setId_coleccion(int id_coleccion) {
		this.id_coleccion = id_coleccion;
	}

	public ImageIcon getPortada() {
		return portada;
	}

	public void setPortada(ImageIcon portada) {
		this.portada = portada;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getSinopsis() {
		return sinopsis;
	}

	public void setSinopsis(String sinopsis) {
		this.sinopsis = sinopsis;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFecha_publicacion() {
		return fecha_publicacion;
	}

	public void setFecha_publicacion(Date fecha_publicacion) {
		this.fecha_publicacion = fecha_publicacion;
	}

	public int getColeccion() {
		return id_coleccion;
	}

	public void setColeccion(int coleccion) {
		this.id_coleccion = id_coleccion;
	}

}
