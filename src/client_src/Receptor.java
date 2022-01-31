package client_src;


import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;




public class Receptor extends Thread {
	
	private String mode;
	private String ip;
	private int puerto;
	private ArrayList<OSobserver> observers;
	private String file_extension;// extension del archivo(.txt, .mp4 etc)
	private String filename;//nombre del archivo sin la extension
	
	public Receptor(ArrayList<OSobserver>  observers,String ip, int puerto,String mode, String file_name) {
		this.puerto=puerto;
		this.ip = ip;
		this.observers=observers;
		this.mode=mode;
		String [] parts = new String[2];
		parts =file_name.split("\\.");
		String extension = parts[1];
		this.filename=parts[0];
		this.file_extension= "."+ extension;
	}
	
	public void run() {
		try {

				//Creamos el canal de comunicación con ip_host por el puerto correspondiente
				System.out.println("El emisor esta esperando en el puerto "+ puerto);
				Socket socket = new Socket(ip,puerto);
				if(mode.equalsIgnoreCase("GUI"))descargaArchivoGUI(socket);
				else descargaArchivoBatch(socket);
				socket.close();
			
			} catch (Exception e) {
				System.out.println("Mal funcionamiento en Cliente");
				e.printStackTrace();

			}
	}
	
	
	
	private void descargaArchivoGUI(Socket socket) throws IOException{
		
		String file = JOptionPane.showInputDialog("Introduzca como quiere guardar el archivo(sin la extension de archivo):");
		while(file.length()==0) {
			file = JOptionPane.showInputDialog("El nombre no puede estar vacio");
		}
		this.filename = file;
		DataInputStream dis = new DataInputStream(socket.getInputStream());
		FileOutputStream fos = new FileOutputStream(filename + this.file_extension);
		int count;
	    byte[] bytes = new byte[8 * 1024];
	    long  totalbytes=0;
	    while((count = dis.read(bytes)) > 0) {
	    	fos.write(bytes, 0, count);
	    	totalbytes+=count;
	    	for(int i=0; i <observers.size();i++)observers.get(i).onDownloading(filename,totalbytes/1000);
		}
	    for(int i=0; i <observers.size();i++)observers.get(i).onDownloading(filename,totalbytes/1000);
	    for(int i=0; i <observers.size();i++)observers.get(i).onFileDownloaded(filename + this.file_extension,totalbytes/1000);
	    
		fos.close();
        dis.close();
			
		
	}
	private void descargaArchivoBatch(Socket socket) throws Exception {
			
			/*System.out.println("Introduce como quieres guardar el archivo(sin la extension de archivo):");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String filename = br.readLine();
			br.close();
			*/
			int complement =new Random().nextInt(1000); // generamos un numero aleatorio para que no sobreescriba al archivo original
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			FileOutputStream fos = new FileOutputStream(filename+ complement+ this.file_extension);
			System.out.println("RECIBIENDO");
			int count;
		    byte[] bytes = new byte[8 * 1024];
		    long  totalbytes=0;
		    long temp=0;
		    while((count = dis.read(bytes)) > 0) {
		    	fos.write(bytes, 0, count);
		    	totalbytes+=count;
		    	if(temp < totalbytes) {
		    		System.out.println("Se han descargado "+ totalbytes/1000+" Kbytes");
		    		temp = totalbytes * 4;
		    	}
		    		    	
			}
		    System.out.println("Se han descargado "+ totalbytes/1000+" KBytes");
			System.out.println("Archivo solicitado se ha descargado");
			fos.close();
	        dis.close();
	        
		
	}

}
