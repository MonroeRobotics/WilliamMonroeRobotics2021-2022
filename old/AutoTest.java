package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous

public class AutoTest extends LinearOpMode
{

    // todo: write your code here
    private DcMotor motorL;
    private DcMotor motorR;
    private DcMotor motorA;
    private DcMotor motorS;
    private Servo servoL;
    private Servo servoF;

    public void runOpMode() {
        motorL = hardwareMap.get(DcMotor.class, "motorL");
        motorR = hardwareMap.get(DcMotor.class, "motorR");
        motorA = hardwareMap.get(DcMotor.class, "motorA");
        motorS = hardwareMap.get(DcMotor.class, "motorS");
        servoL = hardwareMap.get(Servo.class, "servoL");
        servoF = hardwareMap.get(Servo.class, "servoF");
        
        
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();
        
        motorL.setPower(-1);
        motorR.setPower(1);
        sleep(2000);
        motorL.setPower(0);
        motorR.setPower(0);
    }
}
