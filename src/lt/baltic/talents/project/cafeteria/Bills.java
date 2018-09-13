package lt.baltic.talents.project.cafeteria;

import java.time.LocalDateTime;

public interface Bills {

    public String issueAnInvoice(Integer tableNo);
    public void saveInvoice(Invoice invoice);
    public Invoice checkInvoice(Integer tableNo, LocalDateTime dateTime);
}
