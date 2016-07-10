package org.liujiaxin.jaweb.ioc;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.liujiaxin.jaweb.util.ReflectUtil;

public class DefaultBeanFactory extends AbstractBeanFactory {

    private final String configPath = "src/main/java/config.xml";

    @Override
    public void init() {
        File f = new File(configPath);
        SAXReader reader = new SAXReader();
        parseXml(reader, f);

    }

    @SuppressWarnings("unchecked")
    private void parseXml(SAXReader reader, File f) {
        try {
            Document doc = reader.read(f);
            Element root = doc.getRootElement();
            List<Element> elements = root.elements();
            for (Element e : elements) {
                if (e.getName().equals("bean")) {
                    String id = e.attributeValue("id");
                    String className = e.attributeValue("class");
                    Object obj = null;
                    if (null != id && !id.equals("") && null != className && !className.equals("")) {
                        obj = ReflectUtil.newInstance(className);
                        registerBean(id, className, obj);
                    }
                    Map<String, Object> propMap = new HashMap<String, Object>();
                    List<Element> propEle = e.elements();
                    for (Element prop : propEle) {
                        String propName = prop.attributeValue("name");
                        Object propVal;
                        if (null != prop.attributeValue("value")) {
                            propVal = prop.attributeValue("value");
                        } else {
                            String ref = prop.attributeValue("ref");
                            propVal = getBeanByName(ref);
                        }
                        propMap.put(propName, propVal);
                    }
                    initWired(obj, propMap);
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }


}
