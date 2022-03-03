package org.firstinspires.ftc.teamcode.autos;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.utils.BlackPosDetector;
import org.firstinspires.ftc.teamcode.utils.ColorChangePosDetector;
import org.firstinspires.ftc.teamcode.utils.PosDetector;

import java.util.Arrays;

public class PosDetectorFactory {
    public static BlackPosDetector blueLeft(Telemetry telemetry) {
        return new BlackPosDetector(telemetry, Arrays.asList(
                new ColorChangePosDetector.ImRegion(0.9f, 1.0f, 0, 0.16f),
                new ColorChangePosDetector.ImRegion(0.5f, 0.6f, 0, 0.16f)
        ));
    }

    public static BlackPosDetector blueRight(Telemetry telemetry) {
        return new BlackPosDetector(telemetry, Arrays.asList(
                new ColorChangePosDetector.ImRegion(0.5f, 0.6f, 0, 0.16f),
                new ColorChangePosDetector.ImRegion(0.9f, 1.0f, 0, 0.16f)
        ));
    }

    public static BlackPosDetector redLeft(Telemetry telemetry) {
        return new BlackPosDetector(telemetry, Arrays.asList(
                new ColorChangePosDetector.ImRegion(0.525f, 0.575f, 0, 0.1f),
                new ColorChangePosDetector.ImRegion(0.9f, 0.95f, 0, 0.1f)
        ));
    }

    public static BlackPosDetector redRight(Telemetry telemetry) {
        return new BlackPosDetector(telemetry, Arrays.asList(
                new ColorChangePosDetector.ImRegion(0.45f, 0.5f, 0, 0.1f),
                new ColorChangePosDetector.ImRegion(0.8f, 0.85f, 0, 0.1f)
        ));
    }

}