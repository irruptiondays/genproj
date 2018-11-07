package org.irruptiondays.genealogy.ui;

import org.irruptiondays.genealogy.controller.PersonController;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextUtils implements ApplicationContextAware {
    private static ApplicationContext ctx;

    private static final String USER_SERVICE = "userServiceBean";

    @Override
    public void setApplicationContext(ApplicationContext appContext)
            throws BeansException {
        ctx = appContext;

    }

    public static ApplicationContext getApplicationContext() {
        return ctx;
    }

    public static UIContent getUserService() {
        return ctx.getBean(UIContent.class);
    }

    public static PersonController getPersonController() {
        return ctx.getBean(PersonController.class);
    }
}