package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import client_src.Client;
import client_src.OSobserver;
import msg_src.MsgAñadirArchivo;
import msg_src.MsgCerrarConexion;
import msg_src.MsgConexion;

import msg_src.MsgErrorConexion;
import msg_src.MsgListaUsuarios;
import users_src.Usuario;

public class ControlPanel extends JPanel implements OSobserver{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Client ctrlClient;
	private JLabel id_user;
	private JButton getUserList;
	private JButton close;
	private JButton addFile;

	
	public ControlPanel(Client ctrlClient) {
		this.ctrlClient = ctrlClient;
		initGUI();
		ctrlClient.addObserver(this);
	}
	class GestorBoton implements ActionListener {
			
			public void actionPerformed(ActionEvent e) {
				JButton botonPulsado=(JButton)e.getSource();
				
				if(botonPulsado==getUserList) {
					ctrlClient.sendMensaje(new MsgListaUsuarios(ctrlClient.get_idUsuario(),ctrlClient.getIP(),ctrlClient.getIP_HOST()));
				}
				else if(botonPulsado==close) {
					int dialog=JOptionPane.YES_NO_OPTION;
					int dialogResult= JOptionPane.showConfirmDialog(getParent(),"¿Está seguro de que desea salir del programa?","EXIT",dialog);
					if(dialogResult==JOptionPane.YES_OPTION) {
						ctrlClient.sendMensaje(new MsgCerrarConexion(ctrlClient.getIP(), ctrlClient.getIP_HOST(), ctrlClient.get_idUsuario()));
						System.exit(0);
					
					}
				}
				
				else if(botonPulsado==addFile) {
					String filename="";
					String ruta_filename="";
					JFileChooser chooser = new JFileChooser();					
					int returnVal = chooser.showOpenDialog(getParent());
					if(returnVal == JFileChooser.APPROVE_OPTION) {
						filename =  chooser.getSelectedFile().getName();
						ruta_filename = chooser.getSelectedFile().getAbsolutePath();
						ctrlClient.sendMensaje(new MsgAñadirArchivo(ctrlClient.getIP(),ctrlClient.getIP_HOST(), filename, ruta_filename, ctrlClient.get_idUsuario()));

					}
					
				}
					
			}
		}
	
	private void initGUI() {
		JToolBar toolBar = new JToolBar();
		this.id_user=new JLabel("Menú:");
		id_user.setPreferredSize( new Dimension( 50, 10 ) );
		id_user.setMinimumSize( new Dimension( 50, 10 ) );
		this.getUserList = new JButton("Lista usuarios");
		this.setToolTipText("Muestra archivos disponibles y usuarios conectados");
		this.close = new JButton("Cerrar");
		this.addFile = new JButton("Añadir fichero");
		
		toolBar.add(this.id_user);
		toolBar.add(this.getUserList);
		toolBar.add(this.close);
		toolBar.add(this.addFile);
	
		GestorBoton gestor = new GestorBoton();
		
		getUserList.addActionListener(gestor);
		close.addActionListener(gestor);
		addFile.addActionListener(gestor);
		
		add(toolBar);
		
	}

	@Override
	public void onChangeUsername(MsgErrorConexion msg) {
	
		String name = JOptionPane.showInputDialog(this.getParent(),"Su id esta ya elegido, escoja otro");
		while(name.equalsIgnoreCase("")) {
			name = JOptionPane.showInputDialog(this.getParent(),"El id no puede ser vacio");
		}
		ctrlClient.setIdUsuario(name);
		ctrlClient.sendMensaje(new MsgConexion(ctrlClient.getIP(),msg.getIPOrigen(), name));
	}

	@Override
	public void onListaUsuariosRecibida(ArrayList<Usuario> usuarios) {

		
	}


	@Override
	public void onFileDownloaded(String filename,long size) {
		String msg = "Se ha descargado "+ filename + ".\n"+ "Size:"+ size +"kb";
		JOptionPane.showMessageDialog(this.getParent(),msg);		
	}

	@Override
	public void onDownloading(String filename,long kbytes_downloaded) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClientConnected(String name) {
		JOptionPane.showMessageDialog(this.getParent(), "Wellcome " + name);
		
	}

	@Override
	public void onFileAdded() {
		String msg = "Se ha añadido el archivo, ahora está visible para los demás usuarios.";
		JOptionPane.showMessageDialog(this.getParent(),msg);
		
	}

	

	
	
}
