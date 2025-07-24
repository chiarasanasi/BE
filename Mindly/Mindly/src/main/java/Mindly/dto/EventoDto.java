package Mindly.dto;

import lombok.Data;

@Data
public class EventoDto {
    private String id;
    private String title;
    private String start;
    private String end;
    private String description;

    public EventoDto(String id, String title, String start, String end,  String description) {
        this.id = id;
        this.title = title;
        this.start = start;
        this.end = end;
        this.description = description;
    }
}

