package common;

import java.lang.reflect.Method;

public class XChatBaseComponent {

    private String string;
    private Class iChatBaseComponentClass;

    public XChatBaseComponent(String string){
        this.string = string;
        try {
            iChatBaseComponentClass = ReflectionUtils.getNMSClass("IChatBaseComponent");
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public Object a(){
        try {
            Class chatSerializer = ReflectionUtils.getNMSClass("IChatBaseComponent$ChatSerializer");
            Method a = ReflectionUtils.getMethod(chatSerializer, "a", String.class);
            return a.invoke(null, "{\"text\":\"" + string + "\"}");
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Class getiChatBaseComponentClass() {
        return iChatBaseComponentClass;
    }
}
