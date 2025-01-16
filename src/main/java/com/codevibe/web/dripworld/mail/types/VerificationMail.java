package com.codevibe.web.dripworld.mail.types;


import com.codevibe.web.dripworld.mail.Mailable;
import com.codevibe.web.dripworld.util.Env;
import io.rocketbase.mail.EmailTemplateBuilder;
import io.rocketbase.mail.model.HtmlTextEmail;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;

public class VerificationMail extends Mailable {

    private static final String APP_URL = Env.get("server.url");
    private final String VERIFY_URL;
    private final String to;
    private final String name;
    private final String verificationCode;

    public VerificationMail(String to, String name, String verificationCode) {
        this.to = to;
        this.name = name;
        this.verificationCode = verificationCode;
        this.VERIFY_URL = APP_URL + "/verify?token=" + verificationCode;
    }


    @Override
    public void build(Message message) throws MessagingException {
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject("Drip WRLD : Email Verification");

        // generate html/text content
        HtmlTextEmail content = EmailTemplateBuilder.builder()
                .header()
//                .logo("https://www.rocketbase.io/img/logo-dark.png").logoHeight(41)
                .and()
                .text("Welcome, " + name + " !").h1().center().and()
                .text("Click the button below to verify your email address.").center().and()
                .text("If you didn't make this request, ignore this email.").center().and()
                .button("Verify Now", VERIFY_URL).blue().and()
                .text("").center().and()
                .html("<a href=\"" + VERIFY_URL + "\">" + VERIFY_URL + "</a>").and()
                .text("Thank you,\n" +
                        "Drip WRLD Team").and()
                .copyright("Drip WRLD").url(APP_URL).suffix(". All rights reserved.")
                .build();

        message.setContent(content.getHtml(), "text/html");

    }
}
