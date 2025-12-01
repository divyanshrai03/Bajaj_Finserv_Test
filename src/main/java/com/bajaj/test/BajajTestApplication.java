package com.bajaj.test;

import com.bajaj.test.dto.RegistrationRequest;
import com.bajaj.test.dto.RegistrationResponse;
import com.bajaj.test.dto.SubmissionRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class BajajTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(BajajTestApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) {
        return args -> {
            try {
                System.out.println("--- STARTING APPLICATION FLOW (Modern Java) ---");

                String generateUrl = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";
                
                // TODO: ENSURE THESE ARE CORRECT
                RegistrationRequest regRequest = new RegistrationRequest(
                    "Divyansh_Rai", 
                    "22BLC1106",       
                    "divyansh.rai2022@vitstudent.ac.in"
                );

                System.out.println("Step 1: Requesting Webhook...");
                HttpHeaders regHeaders = new HttpHeaders();
                regHeaders.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<RegistrationRequest> regEntity = new HttpEntity<>(regRequest, regHeaders);

                ResponseEntity<RegistrationResponse> regResponse = restTemplate.exchange(
                    generateUrl, 
                    HttpMethod.POST, 
                    regEntity, 
                    RegistrationResponse.class
                );

                if (regResponse.getBody() == null) {
                    throw new RuntimeException("API returned null response");
                }

                String webhookUrl = regResponse.getBody().getWebhookUrl();
                String accessToken = regResponse.getBody().getAccessToken();

                System.out.println(" > Got Webhook: " + webhookUrl);
                System.out.println(" > Got Token: " + accessToken);

                // Java Text Blocks (Clean SQL)
                String rawSql = """
                    SELECT 
                        e.EMP_ID, 
                        e.FIRST_NAME, 
                        e.LAST_NAME, 
                        d.DEPARTMENT_NAME, 
                        (
                            SELECT COUNT(*) 
                            FROM EMPLOYEE e2 
                            WHERE e2.DEPARTMENT = e.DEPARTMENT 
                            AND e2.DOB > e.DOB
                        ) AS YOUNGER_EMPLOYEES_COUNT
                    FROM 
                        EMPLOYEE e
                    JOIN 
                        DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID
                    ORDER BY 
                        e.EMP_ID DESC
                    """;
                
                String cleanSql = rawSql.replace("\n", " ").replaceAll("\\s+", " ").trim();

                System.out.println("Step 2: Submitting SQL Solution...");
                
                SubmissionRequest subRequest = new SubmissionRequest(cleanSql);
                HttpHeaders subHeaders = new HttpHeaders();
                subHeaders.setContentType(MediaType.APPLICATION_JSON);
                subHeaders.set("Authorization", "Bearer " + accessToken); 

                HttpEntity<SubmissionRequest> subEntity = new HttpEntity<>(subRequest, subHeaders);

                ResponseEntity<String> finalResponse = restTemplate.exchange(
                    webhookUrl, 
                    HttpMethod.POST, 
                    subEntity, 
                    String.class
                );

                System.out.println("--- PROCESS COMPLETED ---");
                System.out.println("Status Code: " + finalResponse.getStatusCode());
                System.out.println("Response Body: " + finalResponse.getBody());

            } catch (Exception e) {
                System.err.println("ERROR OCCURRED: " + e.getMessage());
                e.printStackTrace();
            }
        };
    }
}