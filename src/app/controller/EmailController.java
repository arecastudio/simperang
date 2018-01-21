package app.controller;

import java.io.File;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
//import javax.swing.JOptionPane;

import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class EmailController {
	private String reciever;
	/*
	private String subj;
	private String con;
	private final String username;
	private final String password;
	private String from;
	private String host;
	private String put_auth;
	private String put_ttls;
	private String put_host;
	private String put_port;*/
    

	private Connection conn = null;	
	private ResultSet rs = null;
	private PreparedStatement pst = null;
	private DBHelper helper;
	
	private DateFormat dateFormat;
	private Date date;
	private String tgl;
    
    public EmailController (){
    	dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    	date = new Date();
    	tgl=dateFormat.format(date).toString();
    	
    	reciever="samudranta@gmail.com";

    }
    
    public int EmailVendor(String mail,String attach){
    	int ret=0;

		final String username = new LoginEmail().getUsername();
		final String password = new LoginEmail().getPassword();
		String con="";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		props.put("mail.smtp.timeout", "2000");
		props.put("mail.smtp.connectiontimeout", "2000");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(mail));

			message.setSubject("Rekap Permintaan Pengadaan Barang PT. PLN UIP Papua - "+tgl);
			//message.setText("Dear Mail Crawler,\n\n No spam to my email, please!");
			con+="";

			File file=new File(attach);
			Boolean exists=file.exists();
			if (attach!="" && exists==true){
				System.out.println("File exists on: "+attach);
				// Set the email msg text.
				MimeBodyPart messagePart = new MimeBodyPart();
				messagePart.setText("Berikut ini kami kirimkan lampiran Rekap Permintaan Pengadaan Barang PT. PLN UIP Papua");
				//messagePart.setContent(con,"text/html");

				// Set the email attachment file
				FileDataSource fileDataSource = new FileDataSource(attach);

				MimeBodyPart attachmentPart = new MimeBodyPart();
				attachmentPart.setDataHandler(new DataHandler(fileDataSource));
				attachmentPart.setFileName(fileDataSource.getName());

				// Create Multipart E-Mail.
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messagePart);
				multipart.addBodyPart(attachmentPart);

				message.setContent(multipart);
			}else{
				message.setContent(con, "text/html");
			}

			Task<Void>task=new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					Transport.send(message);
					return null;
				}
			};
			task.setOnSucceeded(event -> {
				System.out.println("Proses kirim email selesai.");
			});

			new Thread(task).run();

			//Transport.send(message);
			//System.out.println("Done");
			ret=1;
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		return ret;
	}
    
    public Boolean justSend(String nomor) {
    	Boolean b=Boolean.FALSE;
    	
    	final String username = new LoginEmail().getUsername();
		final String password = new LoginEmail().getPassword();
		String con="";
		String subj="SIMPERANG - Pembuatan Permintaan Barang "+tgl;

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
        
        props.put("mail.smtp.timeout", "2000");    
        props.put("mail.smtp.connectiontimeout", "2000");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(reciever));
			
			for (String str : getMailStack(nomor)) {
				System.out.println(str);
				message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(str));
			}
			System.out.println("prepare sending mail...");
			message.setSubject(subj);
			con+="Berikut ini adalah summary permintaan yang telah di-<i>approve</i> oleh SDM<br/>";
			con+=getContent(nomor);

			message.setContent(con, "text/html");
			System.out.println("Start sending mail...");
			Transport.send(message);

			//Transport.send(message);
			System.out.println("Done. Email sent.");
			b=Boolean.TRUE;

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}    	
    	return b;
    }
    
    private void Pesan(String title,String header,String content,AlertType type) {
    	Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.show();
    }
    
    private List<String> getMailStack(String nomor) {    	  
    	List<String> ls=new ArrayList<String>();
    	String sql="SELECT DISTINCT u.email FROM user AS u INNER JOIN divisi AS d ON d.id=u.id_divisi INNER JOIN (SELECT t1.id FROM divisi AS t1 INNER JOIN user AS t2 ON t2.id_divisi=t1.id INNER JOIN permintaan AS t3 ON t3.nik=t2.nik INNER JOIN dpb_kolektif_d AS t4 ON t4.nomor_permintaan=t3.nomor WHERE t4.nomor_dpb_kolektif=?) AS tmp ON tmp.id=d.id WHERE u.email<>?;";
    	try {
    		conn=helper.Konek();
    		//pst=conn.prepareStatement("SELECT u.email FROM user AS u WHERE u.email<>?;");
    		pst=conn.prepareStatement(sql);
    		pst.setString(2, reciever);
    		pst.setString(1, nomor);
    		rs=pst.executeQuery();
    		while(rs.next()) {
    			if(rs.getString(1)!="") ls.add(rs.getString(1));
    		}
    		pst.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return ls;
    }
    
    private String getContent(String nomor_dpb) {
    	String ret="";
    	String sql="SELECT DISTINCT k.nomor,k.ket,k.tgl,GROUP_CONCAT(d.divisi) AS dvs,GROUP_CONCAT(d.nomor_permintaan) AS nmr_srt FROM dpb_kolektif AS k LEFT OUTER JOIN dpb_kolektif_d AS d ON d.nomor_dpb_kolektif=k.nomor WHERE k.nomor=? LIMIT 1;";    	
    	try {
    		conn=helper.Konek();
    		pst=conn.prepareStatement(sql);
    		pst.setString(1, nomor_dpb);
    		rs=pst.executeQuery();
    		if(rs.next()) {    			
    			ret="<table border=\"1\" cellpadding=\"2\" cellspacing=\"1\">";    			
    			ret+="<tr><th>Informasi</th><th>Nilai</th></tr>";
    			ret+="<tr><td>No. Satuan Permintaan</td><td>"+rs.getString(1)+"</td></tr>";
    			ret+="<tr><td>Keterangan</td><td>"+rs.getString(2)+"</td></tr>";
    			ret+="<tr><td>Tanggal</td><td>"+rs.getString(3)+"</td></tr>";
    			ret+="<tr><td>Divisi</td><td>"+rs.getString(4)+"</td></tr>";
    			ret+="<tr><td>No. Surat Divisi</td><td>"+rs.getString(5)+"</td></tr>";    			
    			ret+=" </table>";    			
    			ret+="<br/><br/><hr/>Sent from System Informasi Manajemen Pengadaan Barang 1.0<br/>"+tgl;
    		}
    		pst.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return ret;
    }

    public int UbahStatusKirim(String nomor){
    	int ret=0;
    	String sql="UPDATE dpb_kolektif SET mail_send=1 WHERE nomor=?;";
		try {
			conn=helper.Konek();
			pst=conn.prepareStatement(sql);
			pst.setString(1,nomor);
			ret=pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return  ret;
	}

	public int UbahStatusKirimNota(String nomor){
		int ret=0;
		String sql="UPDATE nota SET mail_send=1 WHERE nomor=?;";
		try {
			conn=helper.Konek();
			pst=conn.prepareStatement(sql);
			pst.setString(1,nomor);
			ret=pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return  ret;
	}
}