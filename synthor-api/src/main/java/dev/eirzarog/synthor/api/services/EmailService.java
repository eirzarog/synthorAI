package dev.eirzarog.synthor.api.services;

import dev.eirzarog.synthor.api.models.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
/*
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.mail.from:noreply@yourapp.com}")
    private String fromEmail;

    @Value("${app.frontend.url:http://localhost:3000}")
    private String frontendUrl;

    @Async
    public void sendVerificationEmail(User user) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(user.getEmail());
            helper.setSubject("Verify Your Email Address");

            String verificationUrl = frontendUrl + "/verify-email?token=" + user.getEmailVerificationToken();

            String htmlContent = buildVerificationEmailTemplate(user.getFirstName(), verificationUrl);
            helper.setText(htmlContent, true);

            mailSender.send(message);
            logger.info("Verification email sent to: {}", user.getEmail());

        } catch (MessagingException e) {
            logger.error("Failed to send verification email to: {}", user.getEmail(), e);
            throw new RuntimeException("Failed to send verification email", e);
        }
    }

    @Async
    public void sendPasswordResetEmail(User user, String resetToken) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(user.getEmail());
            helper.setSubject("Reset Your Password");

            String resetUrl = frontendUrl + "/reset-password?token=" + resetToken;

            String htmlContent = buildPasswordResetEmailTemplate(user.getFirstName(), resetUrl);
            helper.setText(htmlContent, true);

            mailSender.send(message);
            logger.info("Password reset email sent to: {}", user.getEmail());

        } catch (MessagingException e) {
            logger.error("Failed to send password reset email to: {}", user.getEmail(), e);
            throw new RuntimeException("Failed to send password reset email", e);
        }
    }

    @Async
    public void sendWelcomeEmail(User user) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(user.getEmail());
            helper.setSubject("Welcome to Our Platform!");

            String htmlContent = buildWelcomeEmailTemplate(user.getFirstName());
            helper.setText(htmlContent, true);

            mailSender.send(message);
            logger.info("Welcome email sent to: {}", user.getEmail());

        } catch (MessagingException e) {
            logger.error("Failed to send welcome email to: {}", user.getEmail(), e);
        }
    }

    // Simple email method (fallback)
    @Async
    public void sendSimpleEmail(String to, String subject, String content) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content);

            mailSender.send(message);
            logger.info("Simple email sent to: {}", to);

        } catch (Exception e) {
            logger.error("Failed to send simple email to: {}", to, e);
        }
    }

    private String buildVerificationEmailTemplate(String firstName, String verificationUrl) {
        return String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    .container { max-width: 600px; margin: 0 auto; font-family: Arial, sans-serif; }
                    .header { background-color: #007bff; color: white; padding: 20px; text-align: center; }
                    .content { padding: 20px; line-height: 1.6; }
                    .button { 
                        display: inline-block; 
                        background-color: #007bff; 
                        color: white; 
                        padding: 12px 24px; 
                        text-decoration: none; 
                        border-radius: 5px; 
                        margin: 20px 0;
                    }
                    .footer { background-color: #f8f9fa; padding: 20px; text-align: center; color: #6c757d; font-size: 14px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>Email Verification</h1>
                    </div>
                    <div class="content">
                        <h2>Hi %s,</h2>
                        <p>Thank you for registering with us! Please click the button below to verify your email address:</p>
                        <p style="text-align: center;">
                            <a href="%s" class="button">Verify Email</a>
                        </p>
                        <p>If the button doesn't work, you can also copy and paste this link into your browser:</p>
                        <p style="word-break: break-all;">%s</p>
                        <p>This link will expire in 24 hours.</p>
                        <p>If you didn't create an account with us, please ignore this email.</p>
                    </div>
                    <div class="footer">
                        <p>© 2025 Your Company Name. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
            """, firstName, verificationUrl, verificationUrl);
    }

    private String buildPasswordResetEmailTemplate(String firstName, String resetUrl) {
        return String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    .container { max-width: 600px; margin: 0 auto; font-family: Arial, sans-serif; }
                    .header { background-color: #dc3545; color: white; padding: 20px; text-align: center; }
                    .content { padding: 20px; line-height: 1.6; }
                    .button { 
                        display: inline-block; 
                        background-color: #dc3545; 
                        color: white; 
                        padding: 12px 24px; 
                        text-decoration: none; 
                        border-radius: 5px; 
                        margin: 20px 0;
                    }
                    .footer { background-color: #f8f9fa; padding: 20px; text-align: center; color: #6c757d; font-size: 14px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>Password Reset</h1>
                    </div>
                    <div class="content">
                        <h2>Hi %s,</h2>
                        <p>We received a request to reset your password. Click the button below to create a new password:</p>
                        <p style="text-align: center;">
                            <a href="%s" class="button">Reset Password</a>
                        </p>
                        <p>If the button doesn't work, you can also copy and paste this link into your browser:</p>
                        <p style="word-break: break-all;">%s</p>
                        <p><strong>This link will expire in 1 hour.</strong></p>
                        <p>If you didn't request a password reset, please ignore this email or contact support if you're concerned.</p>
                    </div>
                    <div class="footer">
                        <p>© 2025 Your Company Name. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
            """, firstName, resetUrl, resetUrl);
    }

    private String buildWelcomeEmailTemplate(String firstName) {
        return String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    .container { max-width: 600px; margin: 0 auto; font-family: Arial, sans-serif; }
                    .header { background-color: #28a745; color: white; padding: 20px; text-align: center; }
                    .content { padding: 20px; line-height: 1.6; }
                    .footer { background-color: #f8f9fa; padding: 20px; text-align: center; color: #6c757d; font-size: 14px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>Welcome!</h1>
                    </div>
                    <div class="content">
                        <h2>Hi %s,</h2>
                        <p>Welcome to our platform! Your email has been successfully verified.</p>
                        <p>You can now access all features of your account. Here's what you can do:</p>
                        <ul>
                            <li>Complete your profile</li>
                            <li>Explore our features</li>
                            <li>Connect with other users</li>
                        </ul>
                        <p>If you have any questions, feel free to contact our support team.</p>
                        <p>Thanks for joining us!</p>
                    </div>
                    <div class="footer">
                        <p>© 2025 Your Company Name. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
            """, firstName);
    }*/
}
