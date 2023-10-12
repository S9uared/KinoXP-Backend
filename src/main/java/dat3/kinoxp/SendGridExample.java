package dat3.kinoxp;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.*;
import com.sendgrid.SendGrid;

import java.io.IOException;

public class SendGridExample {

    public static void main(String[] args) throws IOException {
        Email from = new Email("frejajep@hotmail.com");
        String subject = "Sending with Twilio SendGrid is Fun";
        Email to = new Email("frejajep2002@hotmail.com");
        Content content = new Content("text/plain", "easy to do anywhere, even with Java");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw ex;
        }

        SendGrid sendGrid = null;
        Response response = sendGrid.api(request);

        if (response.getStatusCode() == 202) {
            System.out.println("Email sent successfully!");
        } else {
            System.out.println("Failed to send the email. Status code: " + response.getStatusCode());
        }
    }
}