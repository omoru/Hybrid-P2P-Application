package gui;

import java.util.ArrayList;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import client_src.Client;
import client_src.OSobserver;
import msg_src.MsgErrorConexion;
import users_src.Usuario;


public class DownloadsTableModel extends AbstractTableModel implements OSobserver {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String[] columNames= {"ARCHIVO","KB DESCARGADOS"};
	private ArrayList<Download> table_info_downloads;
	
	public  DownloadsTableModel(Client ctrlClient) {
		this.table_info_downloads= new ArrayList<Download>();
		ctrlClient.addObserver(this);
	}
	

	@Override
	public int getColumnCount() {
		return this.columNames.length;
	}

	@Override
	public int getRowCount() {
		return this.table_info_downloads.size();
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
			ob =this.table_info_downloads.get(row).getFilename();
			break;

		case 1:
			ob =this.table_info_downloads.get(row).getKbytes();
			break;
		}
		return ob;
	}
	
	private void update(String filename,long kbytes_downloaded) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				boolean enc=false;
				for(int i =0; i < table_info_downloads.size() && enc==false;i++) {
					if(table_info_downloads.get(i).getFilename().equalsIgnoreCase(filename)) {
						table_info_downloads.get(i).changeValue(kbytes_downloaded);
						enc=true;						
					}
				}
				if(!enc) {
					table_info_downloads.add(new Download(filename,kbytes_downloaded));
				}
				
				fireTableStructureChanged();
			}
		});		
		
	}

	@Override
	public void onChangeUsername(MsgErrorConexion msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onListaUsuariosRecibida(ArrayList<Usuario> usuarios) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onFileDownloaded(String filename, long size) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDownloading(String filename,long kbytes_downloaded) {
		update(filename, kbytes_downloaded);
		
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
