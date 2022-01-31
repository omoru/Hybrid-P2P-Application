package gui;

import java.util.ArrayList;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import client_src.Client;
import client_src.OSobserver;
import msg_src.MsgErrorConexion;
import users_src.File;
import users_src.Usuario;


public class UsersTableModel extends AbstractTableModel implements OSobserver{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String[] columNames= {"ARCHIVO","USUARIO"};
	private ArrayList<TableFiles> table_info_users;
	private Client ctrlClient;
	
	public  UsersTableModel(Client ctrlClient) {
		this.table_info_users= new ArrayList<TableFiles>();
		this.ctrlClient=ctrlClient;
		ctrlClient.addObserver(this);
	}
	@Override
	public int getColumnCount() {
		return this.columNames.length;
	}

	@Override
	public int getRowCount() {
		return this.table_info_users.size();
	}
	
	public String getColumnName(int column) { 
		String s= new String();
		switch(column) {
			case 0: s=columNames[0];break;
			case 1: s=columNames[1];break;
		}
		return s;
	} 

	@Override
	public Object getValueAt(int row, int col) {
		Object ob = new Object();
		switch (col) {
		case 0: 
			ob =this.table_info_users.get(row).getFilename();
			break;

		case 1:
			ob =this.table_info_users.get(row).getId_user();
			break;
		}
		return ob;
	}
	
	

	
	private void update(ArrayList<Usuario> usuarios) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				table_info_users = transformTable(usuarios);
				fireTableStructureChanged();
			}
		});		
		
	}
	
	private ArrayList<TableFiles> transformTable(ArrayList<Usuario> usuarios) {
		ArrayList<TableFiles> new_table = new ArrayList<TableFiles>();
		
		for(Usuario u: usuarios) {
			if(!u.getIdUsuario().equals(ctrlClient.get_idUsuario())) {
				if(u.getFiles().size()==0)
					new_table.add(new TableFiles(null,u.getIdUsuario()));
				for(File file: u.getFiles()) {
					new_table.add(new TableFiles(file.getFilename(),u.getIdUsuario()));
				}
			}
			
		}
		
		return new_table;
		
	}

	@Override
	public void onChangeUsername(MsgErrorConexion msg) {
		
		
	}

	@Override
	public void onListaUsuariosRecibida(ArrayList<Usuario> usuarios) {
		update(usuarios);
		
	}
	
	@Override
	public void onFileDownloaded(String filename, long size) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onDownloading(String filename,long kbytes_downloaded) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onClientConnected(String name) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onFileAdded() {
		// TODO Auto-generated method stub
		
	}
	
	

}
