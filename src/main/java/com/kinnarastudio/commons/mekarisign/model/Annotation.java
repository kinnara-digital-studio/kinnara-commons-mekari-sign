package com.kinnarastudio.commons.mekarisign.model;

import org.json.JSONObject;

public class Annotation {
    private final AnnotationType annotationType;
    private final int page;
    private final long positionX;
    private final long positionY;
    private final long elementWidth;
    private final long elementHeight;
    private final long canvasWidth;
    private final long canvasHeight;

    public Annotation(AnnotationType annotationType, int page, long positionX, long positionY, long elementWidth, long elementHeight, long canvasWidth, long canvasHeight) {
        this.annotationType = annotationType;
        this.page = page;
        this.positionX = positionX;
        this.positionY = positionY;
        this.elementWidth = elementWidth;
        this.elementHeight = elementHeight;
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
    }

    public AnnotationType getAnnotationType() {
        return annotationType;
    }

    public int getPage() {
        return page;
    }

    public long getPositionX() {
        return positionX;
    }

    public long getPositionY() {
        return positionY;
    }

    public long getElementWidth() {
        return elementWidth;
    }

    public long getElementHeight() {
        return elementHeight;
    }

    public long getCanvasWidth() {
        return canvasWidth;
    }

    public long getCanvasHeight() {
        return canvasHeight;
    }

    public JSONObject toJson() {
        return new JSONObject();
    }
}
