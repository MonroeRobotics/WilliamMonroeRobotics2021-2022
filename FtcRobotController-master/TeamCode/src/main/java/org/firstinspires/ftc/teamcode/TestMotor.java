package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class TestMotor extends LinearOpMode {

    public void runOpMode(){

        DcMotor frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        waitForStart();
        while (opModeIsActive()){
            frontRight.setPower(1);
        }
    }


}
