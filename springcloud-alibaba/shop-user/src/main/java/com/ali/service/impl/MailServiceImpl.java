package com.ali.service.impl;

import com.ali.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSenderImpl mailSender;

    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailSender.getUsername()); // 邮件发送者
        message.setTo(to);                 // 邮件接收者
        message.setSubject(subject);       // 主题
        message.setText(content);          // 内容

        mailSender.send(message);
    }

    public void sendAttachmentMail(String to, String subject, String content, File file) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(mailSender.getUsername());
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content);

            // 添加附件
            helper.addAttachment(file.getName(), file);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
