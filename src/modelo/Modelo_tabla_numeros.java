package modelo;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class Modelo_tabla_numeros extends AbstractTableModel {

	ArrayList<Numero> Numeros;

	String[] columnas = { "T�tulo C�mic", "Fecha Publicaci�n", "Estado", "Precio" };

	public Modelo_tabla_numeros() {
		super();
	}

	public Modelo_tabla_numeros(ArrayList<Numero> lista) {
		super();
		Numeros = lista;
	}

	public String getColumnName(int col) {
		return columnas[col];
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return Numeros.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnas.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex != -1 && Numeros.size() > rowIndex) {
			Numero numero = Numeros.get(rowIndex);

			switch (columnIndex) {
				case 0:
					return numero.getTitulo();
				case 1:
					return numero.getFecha_publicacion();
				case 2:
					return numero.getEstado();
				case 3:
					return numero.getPrecio();
				case 4:
					return numero.getId();
			}
		}
		return null;

	}
}
