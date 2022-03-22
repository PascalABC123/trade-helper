package ru.steamutility.tradehelper.annotations;

import javafx.scene.layout.Region;
import ru.steamutility.tradehelper.TradeHelperApp;

import java.lang.reflect.Field;

public class Annotations {
    public static void initAll() {
        initAutoResizeable();
        initStaticAutoResizeable();
    }

    private static void initStaticAutoResizeable() {
        var defaultSceneManager = TradeHelperApp.getDefaultSceneManager();
        defaultSceneManager.getStage().widthProperty().
                addListener((observableValue, number, newSceneWidth) -> {
                    if(defaultSceneManager.isPageOpened()) {
                        for (Field f : defaultSceneManager.getCurrentPageClass().getDeclaredFields()) {
                            if (f.isAnnotationPresent(AutoResizeable.class)) {
                                try {
                                    Object instance = f.get(null);
                                    Region r = (Region) instance;
                                    r.setPrefWidth((Double) newSceneWidth * f.getAnnotation(AutoResizeable.class).width());
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });
    }

    private static void initAutoResizeable() {
        var defaultSceneManager = TradeHelperApp.getDefaultSceneManager();
        defaultSceneManager.getStage().widthProperty().
                addListener((observableValue, number, newSceneWidth) -> {
                    Object controller = defaultSceneManager.getLoader().getController().getClass();
                    for (Field f : controller.getClass().getDeclaredFields()) {
                        if (f.isAnnotationPresent(AutoResizeable.class)) {
                            try {
                                Object instance = f.get(controller);
                                Region r = (Region) instance;
                                r.setPrefWidth((Double) newSceneWidth * f.getAnnotation(AutoResizeable.class).width());
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }
}
