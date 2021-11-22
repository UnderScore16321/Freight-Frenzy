package org.firstinspires.ftc.teamcode;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.ArrayList;
import java.util.List;

@TeleOp
public class CameraTest extends LinearOpMode {
    HardwareMap hm;

    private static final float PURPLE_TOL = 20;

    @Override
    public void runOpMode() throws InterruptedException {
        hm = new HardwareMap(this, true);
        PosDetector posDetector = new PosDetector(telemetry, new PosDetector.ImRegion[] {
                new PosDetector.ImRegion(2.0f / 5.0f, 3.0f / 5.0f, 2.0f / 3.0f, 1.0f),
                new PosDetector.ImRegion(4.0f / 5.0f, 1.0f, 2.0f / 3.0f, 1.0f),
        });

        waitForStart();

        List<Bitmap> bases = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            bases.add(hm.forceGetFrame());
        }
        posDetector.useBaseStates(bases);

        telemetry.clear();

        while (opModeIsActive()) {
//            Thread.sleep(100);
            Bitmap frame = hm.forceGetFrame();
            if (frame != null) {
                int loc = posDetector.regionOfImage(frame);
                telemetry.addData("loc", loc);
                telemetry.update();
            }
//                boolean isPurpleLeft = blockLeft(hslInImageSector(frame, ));
//                telemetry.addData("block left", isPurpleLeft);
//                boolean isPurpleRight = blockRight(hslInImageSector(frame, 4.0 / 5.0, 1.0, 2.0 / 3.0, 1.0));
//                telemetry.addData("block right", isPurpleRight);
//                if (!isPurpleLeft && !isPurpleRight) {
//                    telemetry.addData("position", "right");
//                } else if (!isPurpleLeft && isPurpleRight) {
//                    telemetry.addData("position", "middle");
//                } else if (isPurpleLeft && !isPurpleRight) {
//                    telemetry.addData("position", "left");
//                } else if (isPurpleLeft && isPurpleRight) {
//                    telemetry.addData("position", "?");
//                }
//                telemetry.update();
//            }
        }

        hm.closeCamera();
    }


}
