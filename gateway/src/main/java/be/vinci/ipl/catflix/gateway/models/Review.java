package be.vinci.ipl.catflix.gateway.models;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Review {
    private long id;
    private String pseudo;
    private String hash;
    private int rating; // between 0 and 10
    private String comment;
}
