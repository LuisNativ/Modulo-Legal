package com.abaco.negocio.util;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UEmail {

	public void mail_alist(String cuerpo, String asunto, String emailDestino, Properties prop, String format) {
		try {
			Sendmail(cuerpo, asunto, emailDestino, prop, format);
		} catch (MessagingException ex) {
			Logger.getLogger(UEmail.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void mail_alist(String cuerpo, String asunto, String emailDestino, String format) {
		try {
			Sendmail(cuerpo, asunto, emailDestino, format);
		} catch (MessagingException ex) {
			Logger.getLogger(UEmail.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void Sendmail(String body, String subject, String emailTo, Properties prop, String format)
			throws MessagingException //// this is used to send the emails
	{

		Message message = new MimeMessage(getSession(prop));
		List<String> listaMail = new ArrayList<String>(Arrays.asList(emailTo.split(",")));

		for (String mail : listaMail) {
			message.addRecipient(RecipientType.TO, new InternetAddress(mail));
		}

		message.addFrom(new InternetAddress[] { new InternetAddress(prop.getProperty("mail.salida")) });
		message.setSubject(subject);

		if (format != null && !format.isEmpty()) {
			message.setContent(body, format);
		} else {
			message.setContent(body, UConstante.IntranetGeneral.FORMATO_TEXTO_PLANO);
		}

		Transport.send(message);
	}

	public void Sendmail(String body, String subject, String emailTo, String format) throws MessagingException {
		Properties prop = new Properties();
		String username = "";
		String password = "";
		String emailFrom = "";

		try {
			prop.load(getClass().getClassLoader()
					.getResourceAsStream("com/abaco/presentacion/properties/configuracion.properties"));

			username = prop.getProperty("mail.user");
			password = prop.getProperty("mail.password");
			emailFrom = prop.getProperty("mail.salida");

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		Message message = new MimeMessage(getSession(prop, username, password));
		List<String> listaMail = new ArrayList<String>(Arrays.asList(emailTo.split(",")));

		for (String mail : listaMail) {
			message.addRecipient(RecipientType.TO, new InternetAddress(mail));
		}

		message.addFrom(new InternetAddress[] { new InternetAddress(emailFrom) });

		message.setSubject(subject);

		if (format != null && !format.isEmpty()) {
			message.setContent(body, format);
		} else {
			message.setContent(body, UConstante.IntranetGeneral.FORMATO_TEXTO_PLANO);
		}

		try {
			Transport.send(message);
			UManejadorLog.log("Se envio el correo");
		} catch (Exception ex) {
			UManejadorLog.log("Error: "+ ex.getMessage());
		}
	}

	public void Sendmail(String body, String subject, String emailTo, String format, boolean adjunto,
			String rutaArchivoAdjunto, String nombreArchivoAdjunto) throws MessagingException, IOException {

		Properties prop = new Properties();

		try {
			prop.load(getClass().getClassLoader()
					.getResourceAsStream("com/abaco/presentacion/properties/configuracion.properties"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		Message message = new MimeMessage(getSession(prop));
		List<String> listaMail = new ArrayList<String>(Arrays.asList(emailTo.split(",")));

		for (String mail : listaMail) {
			message.addRecipient(RecipientType.TO, new InternetAddress(mail));
		}

		message.addFrom(new InternetAddress[] { new InternetAddress(prop.getProperty("mail.salida")) });

		message.setSubject(subject);

		if (adjunto) {
			BodyPart texto = new MimeBodyPart();
			texto.setText(body);

			BodyPart adjuntoArchivo = new MimeBodyPart();
			adjuntoArchivo.setDataHandler(new DataHandler(new FileDataSource(rutaArchivoAdjunto)));
			adjuntoArchivo.setFileName(nombreArchivoAdjunto);

			MimeMultipart multiParte = new MimeMultipart();
			multiParte.addBodyPart(texto);
			multiParte.addBodyPart(adjuntoArchivo);
			/*
			 * try {
			 * 
			 * if (archivosAdjuntoAdi != null && archivosAdjuntoAdi.size() > 0)
			 * { for (EArchivo archivo : archivosAdjuntoAdi) { final
			 * ByteArrayInputStream bin = new ByteArrayInputStream(
			 * archivo.getData()); final ByteArrayDataSource dataSource = new
			 * ByteArrayDataSource( bin, archivo.getMime()); final MimeBodyPart
			 * messageBodyPart = new MimeBodyPart();
			 * messageBodyPart.setDataHandler(new DataHandler( dataSource));
			 * 
			 * multiParte.addBodyPart(messageBodyPart); } } } catch (Exception
			 * e) { throw new IOException("Error closing stream: " +
			 * e.getMessage()); }
			 */
			if (format != null && !format.isEmpty()) {
				message.setContent(multiParte, format);
			} else {
				message.setContent(multiParte, UConstante.IntranetGeneral.FORMATO_TEXTO_PLANO);
			}
		} else {

			if (format != null && !format.isEmpty()) {
				message.setContent(body, format);
			} else {
				message.setContent(body, UConstante.IntranetGeneral.FORMATO_TEXTO_PLANO);
			}
		}

		Transport.send(message);
	}

	private Session getSession(Properties prop) {

		Authenticator authenticator = new Authenticator(prop);

		Properties properties = new Properties();
		properties.setProperty("mail.smtp.submitter", authenticator.getPasswordAuthentication().getUserName());
		properties.setProperty("mail.smtp.host", prop.getProperty("mail.smtp.host"));
		properties.setProperty("mail.smtp.port", prop.getProperty("mail.smtp.port"));

		properties.setProperty("mail.smtp.auth", prop.getProperty("mail.smtp.auth"));
		properties.setProperty("mail.smtp.starttls.enable", prop.getProperty("mail.smtp.starttls.enable"));
		properties.setProperty("mail.smtp.socketFactory.port", prop.getProperty("mail.smtp.socketFactory.port"));
		properties.setProperty("mail.smtp.socketFactory.fallback",
				prop.getProperty("mail.smtp.socketFactory.fallback"));
		properties.setProperty("mail.debug", prop.getProperty("mail.debug"));
		return Session.getInstance(properties, authenticator);
	}

	private Session getSession(Properties prop, String username, String password) {

		Authenticator2 authenticator = new Authenticator2(username, password);
		Properties properties = new Properties();
		properties.setProperty("mail.smtp.submitter", authenticator.getPasswordAuthentication().getUserName());
		properties.setProperty("mail.smtp.host", prop.getProperty("mail.smtp.host"));
		properties.setProperty("mail.smtp.port", prop.getProperty("mail.smtp.port"));

		properties.setProperty("mail.smtp.auth", prop.getProperty("mail.smtp.auth"));
		properties.setProperty("mail.smtp.starttls.enable", prop.getProperty("mail.smtp.starttls.enable"));
		properties.setProperty("mail.smtp.socketFactory.port", prop.getProperty("mail.smtp.socketFactory.port"));
		properties.setProperty("mail.smtp.socketFactory.fallback",
				prop.getProperty("mail.smtp.socketFactory.fallback"));
		properties.setProperty("mail.debug", prop.getProperty("mail.debug"));
		return Session.getInstance(properties, authenticator);
	}

	private class Authenticator extends javax.mail.Authenticator {
		private PasswordAuthentication authentication;

		public Authenticator(Properties prop) {
			String username = prop.getProperty("mail.user");
			String password = prop.getProperty("mail.password");
			authentication = new PasswordAuthentication(username, password);
		}

		protected PasswordAuthentication getPasswordAuthentication() {
			return authentication;
		}
	}

	private class Authenticator2 extends javax.mail.Authenticator {
		private PasswordAuthentication authentication;

		public Authenticator2(String username, String password) {
			authentication = new PasswordAuthentication(username, password);
		}

		protected PasswordAuthentication getPasswordAuthentication() {
			return authentication;
		}
	}
}


