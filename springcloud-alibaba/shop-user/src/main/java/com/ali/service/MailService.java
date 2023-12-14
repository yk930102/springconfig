package com.ali.service;

import java.io.File;

public interface MailService {
    /**
     * 发送简单的文本邮件
     */
    void sendSimpleMail(String to, String subject, String content);

    /**
     * 发送带有附件的邮件
     */
    void sendAttachmentMail(String to, String subject, String content, File file);
}
