package ru.steamutility.tradehelper;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.layout.Region;
import ru.steamutility.tradehelper.controller.HeightAutoResizeable;
import ru.steamutility.tradehelper.controller.WidthAutoResizeable;

import java.lang.reflect.Field;

public class Annotations {
    public static void initAll() {
        initAutoResizeable(AnnotationType.WIDTH);
        initAutoResizeable(AnnotationType.HEIGHT);
        initStaticAutoResizeable(AnnotationType.WIDTH);
        initStaticAutoResizeable(AnnotationType.HEIGHT);
    }

    private enum AnnotationType {
        WIDTH,
        HEIGHT
    }

    private static void initStaticAutoResizeable(AnnotationType annotationType) {
        var defaultSceneManager = TradeHelperApp.getDefaultSceneManager();
        ReadOnlyDoubleProperty property = null;
        if (annotationType.equals(AnnotationType.WIDTH))
            property = defaultSceneManager.getStage().widthProperty();
        if (annotationType.equals(AnnotationType.HEIGHT))
            property = defaultSceneManager.getStage().heightProperty();
        property.addListener((observableValue, number, newSceneSize) -> {
            if (defaultSceneManager.isPageOpened()) {
                for (Field f : defaultSceneManager.getCurrentPageClass().getDeclaredFields()) {
                    if ((annotationType.equals(AnnotationType.HEIGHT) && f.isAnnotationPresent(HeightAutoResizeable.class))
                            || (annotationType.equals(AnnotationType.WIDTH) && f.isAnnotationPresent(WidthAutoResizeable.class))) {
                        Object instance = null;
                        try {
                            instance = f.get(null);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        resizeRegion(annotationType, (Double) newSceneSize, f, (Region) instance);
                    }
                }
            }
        });
    }

    private static void initAutoResizeable(AnnotationType annotationType) {
        var defaultSceneManager = TradeHelperApp.getDefaultSceneManager();
        ReadOnlyDoubleProperty property = null;
        if (annotationType.equals(AnnotationType.WIDTH))
            property = defaultSceneManager.getStage().widthProperty();
        if (annotationType.equals(AnnotationType.HEIGHT))
            property = defaultSceneManager.getStage().heightProperty();
        property.addListener((observableValue, number, newSceneSize) -> {
            if (!defaultSceneManager.isPageOpened()) {
                var controller = defaultSceneManager.getLoader().getController();
                for (Field f : controller.getClass().getDeclaredFields()) {
                    if ((annotationType.equals(AnnotationType.HEIGHT) && f.isAnnotationPresent(HeightAutoResizeable.class))
                            || (annotationType.equals(AnnotationType.WIDTH) && f.isAnnotationPresent(WidthAutoResizeable.class))) {
                        Object instance = null;
                        try {
                            instance = f.get(controller);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        resizeRegion(annotationType, (Double) newSceneSize, f, (Region) instance);
                    }
                }
            }
        });
    }

    private static void resizeRegion(AnnotationType annotationType, Double newSceneSize, Field f, Region instance) {
        Region r = instance;
        if (annotationType.equals(AnnotationType.WIDTH)) {
            if (f.isAnnotationPresent(WidthAutoResizeable.class)) {
                assert r != null;
                r.setPrefWidth(newSceneSize * f.getAnnotation(WidthAutoResizeable.class).width());
            }
        }
        if (annotationType.equals(AnnotationType.HEIGHT)) {
            if (f.isAnnotationPresent(HeightAutoResizeable.class)) {
                assert r != null;
                r.setPrefHeight(newSceneSize * f.getAnnotation(HeightAutoResizeable.class).height());
            }
        }
    }
}
