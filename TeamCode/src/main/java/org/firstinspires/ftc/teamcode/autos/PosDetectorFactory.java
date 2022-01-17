package org.firstinspires.ftc.teamcode.autos;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.utils.PosDetector;

public class PosDetectorFactory {
    public static PosDetector blueLeft(Telemetry telemetry) {
        return new PosDetector(telemetry, new PosDetector.ImRegion[]{
                new PosDetector.ImRegion(0.9f, 1.0f, 0, 0.16f),
                new PosDetector.ImRegion(0.5f, 0.6f, 0, 0.16f),
        });
    }

    public static PosDetector blueRight(Telemetry telemetry) {
        return new PosDetector(telemetry, new PosDetector.ImRegion[]{
                new PosDetector.ImRegion(0.5f, 0.6f, 0, 0.16f),
                new PosDetector.ImRegion(0.9f, 1.0f, 0, 0.16f),
        });
    }

    public static PosDetector redLeft(Telemetry telemetry) {
        return new PosDetector(telemetry, new PosDetector.ImRegion[]{
                new PosDetector.ImRegion(0.025f, 0.125f, 0, 0.16f),
                new PosDetector.ImRegion(0.45f, 0.55f, 0, 0.16f),
        });
    }

    public static PosDetector redRight(Telemetry telemetry) {
        return new PosDetector(telemetry, new PosDetector.ImRegion[]{
                new PosDetector.ImRegion(0.34f, 0.5f, 0, 0.1f),
                new PosDetector.ImRegion(5 * 0.125f, 7 * 0.125f, 0, 0.1f),
        });
    }

}