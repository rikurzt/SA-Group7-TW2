package com.example.sa_g7_tw2_spring.utils;

import java.lang.reflect.*;

public class Reflect {
    public static <T> T get(Object o, String field){
        return (T) get(o.getClass(), o, field);
    }
    public static <T> T  get(Class clazz, Object o,String field) {
        Field f = null;
        boolean accessible = true, success = true;
        try {
            f = clazz.getDeclaredField(field);
            accessible = f.canAccess(o);
            f.setAccessible(true);
            return (T) f.get(o);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            success = false;
            return null;
        } finally {
            if(success && !accessible) {
                f.setAccessible(false);
            }
        }
    }

    public static boolean set(Object o,String field,Object o2){
        return set(o.getClass(),o,field,o2);
    }

    public static boolean set(Class clazz, Object o,String field, Object o2){
        boolean accessible = true, isFinal = false;
        Field f = null;
        try {
            f = clazz.getDeclaredField(field);
            accessible = f.canAccess(o);
            f.setAccessible(true);

            if(isFinal = Modifier.isFinal(f.getModifiers()))
                Reflect.set(f,"modifiers",f.getModifiers() & ~Modifier.FINAL);

            f.set(o,o2);

            return true;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }finally {
            f.setAccessible(accessible);
            if(isFinal) Reflect.set(f,"modifiers",f.getModifiers() | Modifier.FINAL);
        }
    }

    public static <T> T  invoke(Object o, String name, Object... args){
        return invoke(o.getClass(), o, name, args);
    }

    public static <T> T  invoke(Class clazz, Object o, String name, Object... args){
        var len = args.length;
        if(len%2 == 1) return (T) new Exception("arguments length error");
        else{
            Class[] classes = new Class[len/2];
            Object[] objects = new Object[len/2];
            for (int i = 0; i < len; i+=2) {
                classes[i/2] = (Class) args[i];
                objects[i/2] = args[i+1];
            }
            boolean accessible = true;
            Method method = null;
            try {
                method = clazz.getDeclaredMethod(name, classes);
                accessible = method.canAccess(o);
                method.setAccessible(true);
                return (T) method.invoke(o,objects);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                return (T) e;
            }finally {
                if(!accessible){
                    method.setAccessible(false);
                }
            }
        }
    }
}

