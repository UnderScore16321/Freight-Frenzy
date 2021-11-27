package org.firstinspires.ftc.teamcode.autos;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.PosDetector;

public class PosDetectorFactory {
    public static PosDetector blueLeft(Telemetry telemetry) {
        return new PosDetector(telemetry, new PosDetector.ImRegion[]{
                new PosDetector.ImRegion(0.25f, 0.5f, 0, 0.33f),
                new PosDetector.ImRegion(0.75f, 1.0f, 0, 0.33f),
        });
    }
}
