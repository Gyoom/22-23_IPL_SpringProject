package be.vinci.ipl.chattycar.gateway.models;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class NoIdReview {
    private String pseudo;
    private String hash;
    private int rating; // between 0 and 10
    private String comment;
}
