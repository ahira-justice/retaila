package com.ahirajustice.retail.services.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;

@Getter
@Setter
public class Attachment {

    private byte[] content;
    private MediaType type;
    private String filename;
    private String disposition;
    private String contentId;

}
