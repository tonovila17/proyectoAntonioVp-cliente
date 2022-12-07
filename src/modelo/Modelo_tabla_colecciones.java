package modelo;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class Modelo_tabla_colecciones extends AbstractTableModel {

	ArrayList<Coleccion> Colecciones;

	String[] columnas = { "Titulo Colecci�n" };

	public Modelo_tabla_colecciones() {
		super();
	}

	public Modelo_tabla_colecciones(ArrayList<Coleccion> lista) {
		super();
		Colecciones = lista;
	}

	public String getColumnName(int col) {
		return columnas[col];
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return Colecciones.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnas.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex != -1 && Colecciones.size() > rowIndex) {
			Coleccion coleccion = Colecciones.get(rowIndex);

			switch (columnIndex) {
				case 0:
					return coleccion.getTitulo();
				case 1:
					return coleccion.getId();
				case 2:
					return coleccion.getFoto();
			}
		}
		return null;

	}

}
