package modelo;

import java.io.Serializable;

import javax.swing.ImageIcon;

public class Coleccion implements Serializable {

	private int id;
	private String titulo;
	private ImageIcon foto;

	public Coleccion(int id, String titulo, ImageIcon foto) {
		this.id = id;
		this.titulo = titulo;
		this.foto = foto;
	}

	public Coleccion(int id, String titulo) {
		this.id = id;
		this.titulo = titulo;
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

	public ImageIcon getFoto() {
		return foto;
	}

	public void setFoto(ImageIcon foto) {
		this.foto = foto;
	}

	public Coleccion() {
	}

}
