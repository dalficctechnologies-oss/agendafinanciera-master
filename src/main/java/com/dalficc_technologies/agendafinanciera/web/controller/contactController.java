package com.dalficc_technologies.agendafinanciera.web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class contactController {

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/contact")
    public String showContactForm() {
        return "contact"; // nombre del template
    }
    @PostMapping("/contact/send")
    public String sendEmail(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("subject") String subject,
            @RequestParam("message") String message,
            RedirectAttributes redirectAttributes) {

        System.out.println("📩 Recibido POST de contacto...");

        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setFrom(email);
            mail.setTo("dalficctechnologies@gmail.com");
            mail.setSubject("Nuevo mensaje: " + subject);
            mail.setText(
                    "Nombre: " + name + "\n" +
                            "Email: " + email + "\n\n" +
                            "Mensaje:\n" + message
            );

            System.out.println("📨 Enviando mensaje...");
            mailSender.send(mail);
            System.out.println("✅ Correo enviado correctamente");

            redirectAttributes.addFlashAttribute("success", "¡Tu mensaje fue enviado correctamente!");

        } catch (Exception e) {
            System.out.println("❌ Error enviando correo: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "No se pudo enviar el mensaje: " + e.getMessage());
        }

        return "redirect:/contact";
    }

}

