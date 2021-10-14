package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp

public class TestNew extends LinearOpMode{

    // todo: write your code here
    private DcMotor motorNew;
    
    public void runOpMode(){
        motorNew = hardwareMap.get(DcMotor.class, "motorNew");
        double runTime = 500;
        double runPower = 5;
        waitForStart();
        while (opModeIsActive()){
            motorNew.setPower(5);
        }
    }
}
