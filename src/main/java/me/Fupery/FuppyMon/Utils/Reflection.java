package me.Fupery.FuppyMon.Utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Reflection {

    public static class Accessor<T> {

        private final Object target;

        public Accessor(Object target) {
            this.target = target;
        }

        public void invoke(String methodName, Object... params) {
            Class[] paramClasses = new Class[params.length];
            for (int i = 0; i < paramClasses.length; i++) {
                paramClasses[i] = params[i].getClass();
            }
            try {
                Method method = target.getClass().getDeclaredMethod(methodName, paramClasses);
                method.setAccessible(true);
                method.invoke(target, params);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        public T get(String fieldName) {
            T value;
            try {
                Field field = target.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                value = (T) field.get(target);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
                value = null;
            }
            return value;
        }

        public void set(String fieldName, T value) {
            try {
                Field field = target.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(target, value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
