package main.model;

import lombok.*;

@Getter
@Setter
@Builder
public class SequenceStatusDto {
    private String name;
    private String currentInterval;
    private int timeLeft;
    private String username;
}
