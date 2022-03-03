package org.firstinspires.ftc.teamcode.utils;

import android.graphics.Bitmap;

import java.util.List;

public interface PosDetector {
    void useBaseStates(List<Bitmap> baseStateImages);

    int regionOfImage(Bitmap image);

    float allImageDiff(Bitmap current);

    public static class ImRegion {
        public static final ImRegion FULL_IMAGE = new ImRegion(0f, 1.0f, 0f, 1.0f);

        float x1, y1, x2, y2;

        public ImRegion(float x1, float x2, float y1, float y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
    }
}
