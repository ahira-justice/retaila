package com.ahirajustice.retail.services.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class AppEmail {

    private String templateId;
    private String message;
    private Map<String, Object> context = new HashMap<>();
    private List<String> cc = new ArrayList<>();
    private List<String> bcc = new ArrayList<>();
    private List<String> to = new ArrayList<>();
    private List<String> from = new ArrayList<>();
    private List<Attachment> attachments = new ArrayList<>();

    public void addAttachment(Attachment attachment) {
        attachments.add(attachment);
    }

    public void setContextVariable(String key, Object value) {
        context.put(key, value);
    }

    public void addCC(String... emailAddresses) {
        cc.addAll(Arrays.asList(emailAddresses));
    }

    public void addBCC(String... emailAddresses) {
        bcc.addAll(Arrays.asList(emailAddresses));
    }

    public void addTo(String... emailAddresses) {
        to.addAll(Arrays.asList(emailAddresses));
    }

    public void addFrom(String... emailAddresses) {
        from.addAll(Arrays.asList(emailAddresses));
    }

}
