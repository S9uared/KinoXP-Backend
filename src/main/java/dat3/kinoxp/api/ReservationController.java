package dat3.kinoxp.api;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import dat3.kinoxp.dto.ReservationRequest;
import dat3.kinoxp.dto.ReservationResponse;
import dat3.kinoxp.entity.Reservation;
import dat3.kinoxp.service.ReservationService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.*;
import com.sendgrid.SendGrid;

import java.io.IOException;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService){
        this.reservationService = reservationService;
    }

    //Security -> USER
    @GetMapping("/{phoneNumber}")
    public List<ReservationResponse> getReservationsByPhoneNumber(@PathVariable String phoneNumber){
        return reservationService.getReservationsByPhoneNumber(phoneNumber);
    }

    //Security -> USER
    @GetMapping("/showing/{showingId}")
    public List<ReservationResponse> getReservationsForShowing(@PathVariable int showingId) {
        return reservationService.viewReservationsForShowing(showingId);
    }

    //Security -> USER
    @GetMapping("/reservation/{reservationId}")
    public ReservationResponse getReservationById(@PathVariable  int reservationId) throws IOException {
        return reservationService.viewReservationById(reservationId);
    }

    //Security -> Anonymous
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReservationResponse createReservation(@RequestBody ReservationRequest body) throws IOException {
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
        } catch (
                IOException ex) {
            throw ex;
        }

        SendGrid sendGrid = null;
        Response response = sendGrid.api(request);

        if (response.getStatusCode() == 202) {
            System.out.println("Email sent successfully!");
        } else {
            System.out.println("Failed to send the email. Status code: " + response.getStatusCode());
        }
        return reservationService.createReservation(body);
    }

    //Security -> USER
    @DeleteMapping("/{reservationId}")
    public void deleteReservation(@PathVariable int reservationId) {
        reservationService.deleteReservation(reservationId);
    }
}