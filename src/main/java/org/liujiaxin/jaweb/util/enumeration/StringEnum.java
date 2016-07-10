package org.liujiaxin.jaweb.util.enumeration;


public interface StringEnum {

    String getStringValue();

    boolean isNull();

    class API {
        public static boolean isNull(StringEnum value) {
            return value == null || value.isNull();
        }
    }
}
