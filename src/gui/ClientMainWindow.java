package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.WindowConstants;

import client_src.Client;


public class ClientMainWindow extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Client ctrlClient;
	ControlPanel control_panel;
	UsersTable users_table;
	DownloadsTable downloads_table;
		
	public ClientMainWindow(Client ctrlClient) {
		super(ctrlClient.get_idUsuario() +" client application");
		this.ctrlClient=ctrlClient;
		initGUI();
		//setExtendedState(MAXIMIZED_BOTH);
		
	}
	
	private void initGUI(){
		JPanel panelPrincipal = this.creaPanelPrincipal(); 
		this.addControlPanel(panelPrincipal);
		
		JPanel panelTablaUsuarios = new JPanel();
		JPanel panelTablaDownloads = new JPanel();
		this.createTable(panelTablaUsuarios);
		this.createTableDownloads(panelTablaDownloads);
		
		JSplitPane splitPaneV  = new JSplitPane( JSplitPane.VERTICAL_SPLIT );
		panelPrincipal.add(splitPaneV, BorderLayout.CENTER);
		splitPaneV.setLeftComponent(panelTablaUsuarios);
		splitPaneV.setRightComponent(panelTablaDownloads);
		
		this.setContentPane(panelPrincipal);
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	private JPanel creaPanelPrincipal() {
		JPanel panelPrincipal= new JPanel();
		panelPrincipal.setLayout(new BorderLayout());
		panelPrincipal.setBackground( Color.gray );
		return panelPrincipal;
	}
	

	private void addControlPanel(JPanel panelPrincipal) {
		this.control_panel= new ControlPanel(this.ctrlClient);
		panelPrincipal.add(this.control_panel,BorderLayout.PAGE_START);
	}

	

	private void createTable(JPanel panelTabla) {
		//panelTabla.setLayout(new BoxLayout (panelTabla,BoxLayout.Y_AXIS));
		this.users_table=new UsersTable(ctrlClient);
		panelTabla.add(this.users_table);
	}
	
	private void createTableDownloads(JPanel panelTablaDownloads) {
		
		//panelTablaDownloads.setLayout(new BoxLayout (panelTablaDownloads,BoxLayout.Y_AXIS));
		this.downloads_table=new DownloadsTable(ctrlClient);
		panelTablaDownloads.add(this.downloads_table);
				
	}

}
