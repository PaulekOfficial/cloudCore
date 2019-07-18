package com.paulek.core.common;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ReflectionUtils {

    //Inspired by https://github.com/rlf/bukkit-utils/blob/master/src/main/java/dk/lockfuglsang/minecraft/reflection/ReflectionUtil.java

    public static String getNMSVersion() {
        return nms().split("\\.")[3];
    }

    /**
     * Returns the real packagename for the net.minecraft.server.
     * @return the real packagename for the net.minecraft.server.
     * @since 1.9
     */
    public static String nms() {
        Object nmsServer = exec(Bukkit.getServer(), "getServer");
        return nmsServer != null ? nmsServer.getClass().getPackage().getName() : "net.minecraft.server";
    }

    public static <T> T exec(Object obj, String methodName, Class[] argTypes, Object... args) {
        if (obj == null) {
            return null;
        }
        Class<?> aClass = obj.getClass();
        try {
            Method method = getMethod(aClass, methodName, argTypes);
            boolean wasAccessible = method.isAccessible();
            method.setAccessible(true);
            try {
                return (T) method.invoke(obj, args);
            } finally {
                method.setAccessible(wasAccessible);
            }
        } catch (NoSuchMethodException | AbstractMethodError e) {
            e.printStackTrace();
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Method getFirstMethod(Class<?> aClass, String methodName){
        return Arrays.stream(aClass.getDeclaredMethods()).filter(m -> m.getName().equalsIgnoreCase(methodName)).findFirst().orElse(null);
    }

    public static Method getMethod(Class<?> aClass, String methodName, Class... argTypes) throws NoSuchMethodException {
        try {
            // Declared gives access to non-public
            return aClass.getDeclaredMethod(methodName, argTypes);
        } catch (NoSuchMethodException e) {
            return aClass.getMethod(methodName, argTypes);
        }
    }

    /**
     * Uses reflection to execute the named method on the supplied class giving the arguments.
     * Sinks all exceptions, but log an entry and returns <code>null</code>
     *
     * @param obj        The object on which to invoke the method
     * @param methodName The name of the method to invoke
     * @param args       The arguments to supply to the method
     * @return <code>null</code> or the return-object from the method.
     * @since 1.8
     */
    public static <T> T exec(Object obj, String methodName, Object... args) {
        if (obj == null) {
            return null;
        }
        Class[] argTypes = new Class[args.length];
        int ix = 0;
        for (Object arg : args) {
            argTypes[ix++] = arg != null ? arg.getClass() : null;
        }
        return exec(obj, methodName, argTypes, args);
    }

    public static void sendPackets(Player player, Object packet) throws Exception{
        Object nmsPlayer = getNMSPlayer(player);
        Object connection = nmsPlayer.getClass().getField("playerConnection").get(nmsPlayer);
        getMethod(connection.getClass(), "sendPacket", getNMSClass("Packet")).invoke(connection, packet);
    }

    public static Object getNMSPlayer(Player player) throws Exception{
        return player.getClass().getMethod("getHandle").invoke(player);
    }

    public static Class<?> getNMSClass(String className) throws ClassNotFoundException{
        return Class.forName("net.minecraft.server." + getNMSVersion() + "." + className);
    }

    public static Class<?> getBukkitClass(String className) throws ClassNotFoundException{
        return Class.forName("org.bukkit." + className);
    }

    public static Class<?> getCraftBukkitClass(String className) throws ClassNotFoundException{
        return Class.forName("org.bukkit.craftbukkit." + getNMSVersion() + "." + className);
    }

    public static <T> T getField(Object obj, String fieldName) {
        try {
            Field field = getFieldInternal(obj, fieldName);
            boolean wasAccessible = field.isAccessible();
            field.setAccessible(true);
            try {
                return (T) field.get(obj);
            } finally {
                field.setAccessible(wasAccessible);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> void setField(Object obj, String fieldName, T field) {
        try {
            Field declaredField = getFieldInternal(obj, fieldName);
            boolean wasAccessible = declaredField.isAccessible();
            declaredField.setAccessible(true);
            try {
                declaredField.set(obj, field);
            } finally {
                declaredField.setAccessible(wasAccessible);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static Field getFieldInternal(Object obj, String fieldName) throws NoSuchFieldException {
        return getFieldFromClass(obj.getClass(), fieldName);
    }

    private static Field getFieldFromClass(Class<?> aClass, String fieldName) throws NoSuchFieldException {
        if (aClass == null) {
            throw new NoSuchFieldException("Unable to locate field " + fieldName);
        }
        try {
            return aClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            // Ignored
        }
        try {
            return aClass.getField(fieldName);
        } catch (NoSuchFieldException e) {
            // Ignore
        }
        return getFieldFromClass(aClass.getSuperclass(), fieldName);
    }

    public static <T> T newInstance(String className, Class<?>[] argTypes, Object... args) {
        try {
            Class<?> aClass = Class.forName(className);
            Constructor<?> constructor = aClass.getDeclaredConstructor(argTypes);
            return (T) constructor.newInstance(args);
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Instantiates an object.
     * @param className The name of the class
     * @param args      An array of arguments
     * @param <T>       Return-type
     * @return the object, or <code>null</code>.
     * @since 1.9
     */
    public static <T> T newInstance(String className, Object... args) {
        Class[] argTypes = new Class[args.length];
        int ix = 0;
        for (Object arg : args) {
            argTypes[ix++] = arg != null ? arg.getClass() : null;
        }
        return newInstance(className, argTypes, args);
    }

}
