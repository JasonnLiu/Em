package org.liujiaxin.jaweb.aop;

import org.liujiaxin.jaweb.ioc.SimpleBeanFactory;

public class AopHelper {

    private AspectProcessor aspectProcessor;

    private SimpleBeanFactory ioc;

    public AspectProcessor getAspectProcessor() {
        return aspectProcessor;
    }

    public void setAspectProcessor(AspectProcessor aspectProcessor) {
        this.aspectProcessor = aspectProcessor;
    }

    public SimpleBeanFactory getIoc() {
        return ioc;
    }

    public void setIoc(SimpleBeanFactory ioc) {
        this.ioc = ioc;
    }

    public void init(SimpleBeanFactory ioc) {
        aspectProcessor.process(ioc);
    }

}
