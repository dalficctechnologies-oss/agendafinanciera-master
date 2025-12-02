package com.dalficc_technologies.agendafinanciera.web.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@RestController
@RequestMapping("/api/contact")
public class contactController {

    private static final String API_KEY = "re_MtFDY3vN_KyNcXLPywDRcEMXwZrmYh6wx";
    private static final String RESEND_URL = "https://api.resend.com/emails";

    @PostMapping
    public ResponseEntity<String> sendContactEmail(@RequestParam String name,
                                                   @RequestParam String email,
                                                   @RequestParam String message) {

        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> body = new HashMap<>();
        body.put("from", "Dalficc Technologies <onboarding@resend.dev>");
        body.put("to", Collections.singletonList("dalficctechnologies@gmail.com"));
        body.put("subject", "Nuevo mensaje de contacto");
        body.put("html", "<strong>Nombre:</strong> " + name +
                "<br><strong>Email:</strong> " + email +
                "<br><strong>Mensaje:</strong><br>" + message);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(API_KEY);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                RESEND_URL,
                HttpMethod.POST,
                request,
                String.class
        );

        return ResponseEntity.ok(response.getBody());
    }
}
