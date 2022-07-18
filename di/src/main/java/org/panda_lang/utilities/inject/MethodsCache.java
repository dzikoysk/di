package org.panda_lang.utilities.inject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import panda.std.Pair;

final class MethodsCache {

    private final Map<Pair<Class<?>, Class<? extends Annotation>>, List<Method>> cachedAnnotatedMethods = new HashMap<>();

    public List<Method> getAnnotatedMethods(Class<?> clazz, Class<? extends Annotation> annotation) {
        return this.cachedAnnotatedMethods.computeIfAbsent(Pair.of(clazz, annotation), (key) ->
                getAllMethods(new ArrayList<>(), clazz)
                        .stream()
                        .filter(method -> method.isAnnotationPresent(annotation))
                        .collect(Collectors.toList()));
    }

    private static List<Method> getAllMethods(List<Method> methods, Class<?> type) {
        methods.addAll(Arrays.asList(type.getDeclaredMethods()));
        if (type.getSuperclass() != null) {
            getAllMethods(methods, type.getSuperclass());
        }
        return methods;
    }

}
