/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

/**
 *
 * @author emmanuel
 */
public class EmailSender {

    private static final String de_usuario = "registro.4ciencias";
    private static final String de_correo = "registro.4ciencias@gmail.com";
    private static final String de_nombre = "4ciencias";
    private static final String de_pass = "slowcode"; // GMail password
    private static final String server = "smtp.gmail.com";
    private static final int port = 587;

    /**
     *
     * @param to
     * @param id
     */
    public static void mandaValidacion(String to, int id) {
        String subject = "¡Verifica tu cuenta en 4ciencias!";
        String body = "Para verificar tu cuenta, entra al siguiente link:\n"
                + "\t localhost:8080/4ciencias/valida_cuenta.xhtml?id=" + id;

        Email email = EmailBuilder.startingBlank()
                .from(de_nombre, de_correo)
                .to(to)
                .withSubject(subject)
                .withPlainText(body)
                .buildEmail();
        MailerBuilder.withSMTPServer(server, 587, de_usuario, de_pass)
                .buildMailer().sendMail(email);
    }
}
