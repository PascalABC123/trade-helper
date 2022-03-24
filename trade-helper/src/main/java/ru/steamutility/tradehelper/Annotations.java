package ru.steamutility.tradehelper;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.layout.Region;
import ru.steamutility.tradehelper.controller.HeightAutoResizeable;
import ru.steamutility.tradehelper.controller.WidthAutoResizeable;

import java.lang.reflect.Field;

public class Annotations {
    public static void initAll(SceneManager sm) {
        initAutoResizeable(AnnotationType.WIDTH, sm);
        initAutoResizeable(AnnotationType.HEIGHT, sm);
        initStaticAutoResizeable(AnnotationType.WIDTH, sm);
        initStaticAutoResizeable(AnnotationType.HEIGHT, sm);
    }

    private enum AnnotationType {
        WIDTH,
        HEIGHT
    }

    private static void initStaticAutoResizeable(AnnotationType annotationType, final SceneManager sm) {
        assert sm != null;

        // set size property matching annotation type
        ReadOnlyDoubleProperty property = null;
        if (annotationType.equals(AnnotationType.WIDTH))
            property = sm.getStage().widthProperty();
        if (annotationType.equals(AnnotationType.HEIGHT))
            property = sm.getStage().heightProperty();

        property.addListener((observableValue, number, newSceneSize) -> {
            if (sm.isPageOpened()) {
                for (final Field f : sm.getCurrentPageClass().getDeclaredFields()) {

                    // if annotation type equals var annotationType
                    if ((annotationType.equals(AnnotationType.HEIGHT) && f.isAnnotationPresent(HeightAutoResizeable.class))
                            || (annotationType.equals(AnnotationType.WIDTH) && f.isAnnotationPresent(WidthAutoResizeable.class))) {

                        // region instance with annotation
                        Object instance = null;

                        try {
                            instance = f.get(null);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }

                        assert instance != null;
                        resizeRegion(annotationType, (Double) newSceneSize, f, (Region) instance);
                    }
                }
            }
        });
    }

    private static void initAutoResizeable(AnnotationType annotationType, final SceneManager sm) {
        assert sm != null;

        // set size property matching annotation type
        ReadOnlyDoubleProperty property = null;
        if (annotationType.equals(AnnotationType.WIDTH))
            property = sm.getStage().widthProperty();
        if (annotationType.equals(AnnotationType.HEIGHT))
            property = sm.getStage().heightProperty();

        property.addListener((observableValue, number, newSceneSize) -> {
            if (!sm.isPageOpened()) {

                Object controller = sm.getLoader().getController();
                for (Field f : controller.getClass().getDeclaredFields()) {

                    // if annotation type equals var annotationType
                    if ((annotationType.equals(AnnotationType.HEIGHT) && f.isAnnotationPresent(HeightAutoResizeable.class))
                            || (annotationType.equals(AnnotationType.WIDTH) && f.isAnnotationPresent(WidthAutoResizeable.class))) {

                        // region instance with annotation
                        Object instance = null;

                        try {
                            instance = f.get(controller);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }

                        assert instance != null;
                        resizeRegion(annotationType, (Double) newSceneSize, f, (Region) instance);
                    }
                }
            }
        });
    }

    private static void resizeRegion(AnnotationType annotationType, Double newSceneSize, Field f, Region instance) {
        if (annotationType.equals(AnnotationType.WIDTH)) {
            if (f.isAnnotationPresent(WidthAutoResizeable.class)) {
                assert instance != null;
                instance.setPrefWidth(newSceneSize * f.getAnnotation(WidthAutoResizeable.class).width());
            }
        }
        if (annotationType.equals(AnnotationType.HEIGHT)) {
            if (f.isAnnotationPresent(HeightAutoResizeable.class)) {
                assert instance != null;
                instance.setPrefHeight(newSceneSize * f.getAnnotation(HeightAutoResizeable.class).height());
            }
        }
    }
}
