package com.nmt.groceryfinder.shared.schedulers;

import com.nmt.groceryfinder.common.enums.SubjectMailEnum;
import com.nmt.groceryfinder.utils.MailServiceUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 11/29/2024
 */
@Component
public class LogCleaner {
    private final MailServiceUtil mailServiceUtil;
    @Autowired
    public LogCleaner(MailServiceUtil mailServiceUtil) {
        this.mailServiceUtil = mailServiceUtil;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanLogs() throws MessagingException {
        System.out.println("Log cleaning started...");
        File logDir = new File("logs");
        File[] files = logDir.listFiles();
        int count = 0;
        if (files != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");
            Date currentDate = new Date();
            String targetDate = dateFormat.format(new Date(currentDate.getTime()-(24*60*60*1000)));
            for(File file: files) {
                String fileName = file.getName();
                if (fileName.equals("EcommerceLog.log")) {
                    continue;
                }

                if(fileName.startsWith("EcommerceLog-") && fileName.endsWith(".log")){
                    try {
                        String fileDateString = fileName.split("_")[0].substring("EcommerceLog-".length());
                        Date fileDate = dateFormat.parse(fileDateString);
                        if(fileDate.before(dateFormat.parse(targetDate))){
                            boolean  deleted = file.delete();
                            if (deleted) {
                                count++;
                                System.out.println("Deleted log file: " + file.getName());
                            }
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            // mail
            String mail = "minhthuan.2862001123@gmail.com";
            String subject = SubjectMailEnum.NOTIFICATION_CLEAN_LOG_FILE.getSubject();
            String htmlContent = this.mailServiceUtil.generateCleanEmailContent(mail, count);
            this.mailServiceUtil.sendHtmlEmail(mail, subject, htmlContent);
        }
        else {
            System.out.println("No files found in /logs directory");
        }
    }
}
