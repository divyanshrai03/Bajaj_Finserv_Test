package com.bajaj.test.dto;

public class RegistrationResponse {
    private String webhookUrl;
    private String accessToken;

    // Getters and Setters (Fixes "cannot find symbol" error)
    public String getWebhookUrl() { return webhookUrl; }
    public void setWebhookUrl(String webhookUrl) { this.webhookUrl = webhookUrl; }
    
    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
}