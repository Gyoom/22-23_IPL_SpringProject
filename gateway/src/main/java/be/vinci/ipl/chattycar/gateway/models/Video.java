package be.vinci.ipl.chattycar.gateway.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Video {
    private String hash;
    private String name;
    private String author;
    private int creationYear;
    private int duration; // in seconds
    private String url;
}
