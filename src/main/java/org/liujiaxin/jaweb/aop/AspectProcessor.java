package org.liujiaxin.jaweb.aop;

import org.liujiaxin.jaweb.ioc.SimpleBeanFactory;

public interface AspectProcessor {

    public void process(SimpleBeanFactory ioc);
}
