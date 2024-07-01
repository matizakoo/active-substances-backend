package pl.tanielazienki.tanielazienki.service;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.tanielazienki.tanielazienki.controller.ComplaintControllerImpl;
import pl.tanielazienki.tanielazienki.dto.ComplaintDTO;

import java.io.IOException;
import java.util.List;

@Service
public class EmailService {
    private final OkHttpClient okHttpClient;
    @Value("${email.auth}")
    private String emailAuth;
    @Autowired
    private ComplaintServiceImpl complaintServiceImpl;

    public EmailService() {
        this.okHttpClient = new OkHttpClient().newBuilder().build();
    }

    public void sendSimpleEmail(String to, String subject, String text) throws IOException {
        System.out.println(emailAuth);

        List<ComplaintDTO> list = complaintServiceImpl.complaintsExpiresInTwoDays();
        StringBuilder stringBuilder = new StringBuilder();

        for(ComplaintDTO e: list) {
            stringBuilder.append(e.getFactureId() + " ");
        }

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"from\":{\"email\":\"mailtrap@demomailtrap.com\",\"name\":\"Reklamacje tanielazienki\"},\"to\":[{\"email\":\"matzakoo@gmail.com\"}],\"subject\":\"Kończy się czas rozpatrywania reklamacji\",\"text\":\"Koniecznie rozpatrz reklamacje o numerze faktur: "+ stringBuilder +"\",\"category\":\"Integration Test\"}");
        Request request = new Request.Builder()
                .url("https://send.api.mailtrap.io/api/send")
                .method("POST", body)
                .addHeader("Authorization", "Bearer " + emailAuth)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response);
    }
}