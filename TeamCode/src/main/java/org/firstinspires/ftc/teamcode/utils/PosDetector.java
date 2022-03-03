package org.firstinspires.ftc.teamcode.utils;

import android.graphics.Bitmap;

import java.util.List;

public interface PosDetector {
    void useBaseStates(List<Bitmap> baseStateImages);

    int regionOfImage(Bitmap image);

    float allImageDiff(Bitmap current);

    class ImRegion {
        public static final ImRegion FULL_IMAGE = new ImRegion(0f, 1.0f, 0f, 1.0f);

        float x1, y1, x2, y2;

        public ImRegion(float x1, float x2, float y1, float y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
    }

    static String hslToStr(float[] hsl) {
        return hsl[0] + ", " + hsl[1] + ", " + hsl[2];
    }

    static float[] rgbToHSL(int rIn, int gIn, int bIn) {
        float r = (float) (rIn / 255.0);
        float g = (float) (gIn / 255.0);
        float b = (float) (bIn / 255.0);

        //	Minimum and Maximum RGB values are used in the HSL calculations
        float min = Math.min(r, Math.min(g, b));
        float max = Math.max(r, Math.max(g, b));

        //  Calculate the Hue
        float h = 0;

        if (max == min) {
            h = 0;
        } else if (max == r) {
            h = ((60 * (g - b) / (max - min)) + 360) % 360;
        } else if (max == g) {
            h = (60 * (b - r) / (max - min)) + 120;
        } else if (max == b) {
            h = (60 * (r - g) / (max - min)) + 240;
        }

        //  Calculate the Luminance
        float l = (max + min) / 2;

        //  Calculate the Saturation
        float s = 0;

        if (max == min) {
            s = 0;
        } else if (l <= .5f) {
            s = (max - min) / (max + min);
        } else {
            s = (max - min) / (2 - max - min);
        }

        return new float[]{h, s * 100, l * 100};
    }

}
