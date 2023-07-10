package miu.edu.onlineRetailSystem.contract;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardResponse {

    private int id;
    private String number;
    private LocalDateTime expirationDate;
    private String securityCode;

    // Constructors, getters, and setters

    // Parameterized constructor
    public CreditCardResponse(String number, LocalDateTime expirationDate, String securityCode) {
        this.number = number;
        this.expirationDate = expirationDate;
        this.securityCode = securityCode;
    }

    // Getters and setters
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }
}
