package org.dlg.svtool.email;

import lombok.extern.slf4j.Slf4j;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * 测试 基于163邮箱
 * @author lingui
 */
@Slf4j
public class EmailUtil {
    /**
     * 发件人邮箱 (开通 POP3/SMTP/IMAP服务的邮箱)
     */
    private static final String EMAIL_ACCOUNT = "";

    /**
     * POP3/SMTP/IMAP客户端授权密码或者邮箱密码
     * 需要到邮箱控制台获取
     */
    private static final String EMAIL_PASSWORD = "";

    /**
     * 一对一发送邮件
     *
     * @param fromName           发件人姓名
     * @param receiveMailAccount 收件人邮箱
     * @param title              邮件标题
     * @param content            邮件内容
     * @return true    成功		false 失败
     */
    private static boolean sendEmail(String fromName, String receiveMailAccount, String title, String content) {
        // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();
        // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.transport.protocol", "smtp");
        // 发件人的邮箱的 SMTP 服务器地址, 163邮箱固定为：smtp.163.com
        props.setProperty("mail.smtp.host", "smtp.163.com");
        // 需要请求认证
        props.setProperty("mail.smtp.auth", "true");
        // 邮箱服务器端口号   163邮箱SMTP默认是25
        props.setProperty("mail.smtp.port", "25");
        try {
            // 2. 根据配置创建会话对象, 用于和邮件服务器交互
            Session session = Session.getInstance(props);
            // 设置为debug模式, 可以查看详细的发送 log
            session.setDebug(false);

            // 3. 创建一封邮件
            MimeMessage message = new MimeMessage(session);
            // 3.1 From: 发件人（昵称有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改昵称）
            message.setFrom(new InternetAddress(EMAIL_ACCOUNT, fromName, "UTF-8"));
            // 3.2 To: 收件人（可以增加多个收件人、抄送、密送）
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMailAccount, "", "UTF-8"));
            // 3.3 Subject: 邮件主题（标题有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改标题）
            message.setSubject(title, "UTF-8");
            // 3.4 Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）
            message.setContent(content, "text/html;charset=UTF-8");
            // 3.5 设置发件时间
            message.setSentDate(new Date());
            // 3.6. 保存设置
            message.saveChanges();

            // 4. 根据 Session 获取邮件传输对象
            Transport transport = session.getTransport();

            //5. 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
            transport.connect(EMAIL_ACCOUNT, EMAIL_PASSWORD);

            // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
            transport.sendMessage(message, message.getAllRecipients());

            // 7. 关闭连接
            transport.close();

            return true;
        } catch (Exception e) {
            log.error("发送邮件异常：", e);
            return false;
        }
    }
}