package org.firstinspires.ftc.teamcode.utils;

import android.graphics.Bitmap;
import android.graphics.Color;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ColorChangePosDetector implements PosDetector {

    private final ImRegion[] searchRegions;
    private float[][] baseStates;
    private List<Bitmap> baseImages;
    private final Telemetry telemetry;

    public ColorChangePosDetector(Telemetry telemetry, ImRegion[] searchRegions) {
        this.telemetry = telemetry;
        this.searchRegions = searchRegions;
    }

    @Override
    public void useBaseStates(List<Bitmap> baseStateImages) {
        baseImages = baseStateImages;
        float[][] newBaseStates = new float[searchRegions.length][3];
        for (int r = 0; r < searchRegions.length; r++) {
            List<Float> hs = new ArrayList<>();
            int s = 0;
            int l = 0;
            for (Bitmap baseState : baseStateImages) {
                float[] hsl = averageHSLInRegion(baseState, searchRegions[r]);
                hs.add(hsl[0]);
                s += hsl[1];
                l += hsl[2];
            }
            newBaseStates[r][0] = averageOfHs(hs);
            newBaseStates[r][1] = s / (float)baseStateImages.size();
            newBaseStates[r][2] = l / (float)baseStateImages.size();
        }
        baseStates = newBaseStates;
    }

    private static final float H_MULT = 0.5f;
    private static final float S_MULT = 2.0f;
    private static final float L_MULT = 2.0f;
    private static final float MIN_DIFF = 50;

    @Override
    public int regionOfImage(Bitmap image) {
        if (baseStates == null) throw new IllegalArgumentException();

        float[][] hsls = new float[searchRegions.length][3];
        for (int r = 0; r < searchRegions.length; r++) {
            float[] hsl = averageHSLInRegion(image, searchRegions[r]);
            telemetry.addData("base " + r, PosDetector.hslToStr(baseStates[r]));
            telemetry.addData("obs " + r, PosDetector.hslToStr(hsl));
            hsls[r][0] = hsl[0];
            hsls[r][1] = hsl[1];
            hsls[r][2] = hsl[2];
        }
        float[] differencesFromBaseStates = new float[searchRegions.length];
        for (int r = 0; r < searchRegions.length; r++) {
            float hDiff = diffBetweenHs(baseStates[r][0], hsls[r][0]);
            float sDiff = Math.abs(baseStates[r][1] - hsls[r][1]);
            float lDiff = Math.abs(baseStates[r][2] - hsls[r][2]);
            differencesFromBaseStates[r] = hDiff * H_MULT + sDiff * S_MULT + lDiff * L_MULT;
        }

        int diffLoc = -1;
        for (int d = 0; d < differencesFromBaseStates.length; d++) {
            telemetry.addData("diff " + d, differencesFromBaseStates[d]);
            if (diffLoc == -1 && differencesFromBaseStates[d] > MIN_DIFF) {
                diffLoc = d;
            } else if (diffLoc != -1 && differencesFromBaseStates[d] > differencesFromBaseStates[diffLoc]) {
                diffLoc = d;
            }
        }
        return diffLoc;
    }

    @Override
    public float allImageDiff(Bitmap current) {
        float[] baseHSL = averageHSLInRegion(baseImages.get(0), ImRegion.FULL_IMAGE);
        float[] currentHSL = averageHSLInRegion(current, ImRegion.FULL_IMAGE);

        float hDiff = diffBetweenHs(baseHSL[0], currentHSL[0]);
        float sDiff = Math.abs(baseHSL[1] - currentHSL[1]);
        float lDiff = Math.abs(baseHSL[2] - currentHSL[2]);
        return hDiff * H_MULT + sDiff * S_MULT + lDiff * L_MULT;
    }

    private float averageOfHs(List<Float> hs) {
        float min = Collections.min(hs);
        float max = Collections.max(hs);
        if (min <= 90 && max >= 90) {
            float addition = 360 - max;
            float total = 0;
            for (Float h : hs) {
                total += (h + addition) % 360;
            }
            return total / hs.size() - addition;
        } else {
            float total = 0;
            for (Float h : hs) {
                total += h;
            }
            return total / hs.size();
        }
    }

    private static float diffBetweenHs(float h1, float h2) {
        if (h1 <= 90 && h2 >= 270) {
            return h1 + (360 - h2);
        } else if (h2 <= 90 && h1 >= 270) {
            return h2 + (360 - h1);
        } else {
            return Math.abs(h1 - h2);
        }
    }

    private static float[] averageHSLInRegion(Bitmap image, ImRegion region) {
        int xStart = (int) (image.getWidth() * region.x1);
        int xEnd = (int) (image.getWidth() * region.x2);
        int yStart = (int) (image.getHeight() * region.y1);
        int yEnd = (int) (image.getHeight() * region.y2);

        int r = 0, g = 0, b = 0;
        int count = 0;

        for (int i = xStart; i < xEnd; i++) {
            for (int j = yStart; j < yEnd; j++) {
                count++;
                int pixel = image.getPixel(i, j);
                r += Color.red(pixel);
                g += Color.green(pixel);
                b += Color.blue(pixel);
            }
        }

        int rT = r / count;
        int gT = g / count;
        int bT = b / count;
        return PosDetector.rgbToHSL(rT, gT, bT);
    }

}
