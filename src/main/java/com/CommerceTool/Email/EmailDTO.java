package com.CommerceTool.Email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO {

    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
    private URI  uri;
}
