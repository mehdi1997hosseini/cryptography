package ir.smarttrustco.cryptography.messages.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageReceive {
    private Long code;
    private String title;
    private String message;
    private String sender;

}
