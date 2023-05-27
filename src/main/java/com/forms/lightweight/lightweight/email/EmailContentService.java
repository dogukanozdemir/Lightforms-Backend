package com.forms.lightweight.lightweight.email;

import com.forms.lightweight.lightweight.util.FileUtil;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class EmailContentService {

    private static final String CONFIRM_MAIL_HTML = "html/confirmEmail.html";

    public static String getConfirmationMailContent(String userName, String token){
        String mailContent = FileUtil.getResourceFileAsString(CONFIRM_MAIL_HTML);
        String confirmLink = "https://lightforms.co/confirm?token=" + token;
        mailContent = mailContent.replace("{{userName}}",userName);
        mailContent = mailContent.replace("{{confirmLink}}",confirmLink);

        return mailContent;
    }
}
