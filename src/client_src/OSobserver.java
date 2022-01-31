package client_src;

import java.util.ArrayList;


import msg_src.MsgErrorConexion;
import users_src.Usuario;

public interface OSobserver {
	
	public void onChangeUsername(MsgErrorConexion msg);
	public void onListaUsuariosRecibida(ArrayList<Usuario> usuarios);
	public void onFileDownloaded(String filename,long size);
	public void onDownloading(String filename,long kbytes_downloaded);
	public void onClientConnected(String name);
	public void onFileAdded();

}
