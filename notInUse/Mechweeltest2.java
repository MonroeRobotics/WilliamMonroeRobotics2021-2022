package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp

public class Mechweeltest2 extends LinearOpMode{

    private DcMotor frontRight;
    private DcMotor frontLeft;
    private DcMotor backRight;
    private DcMotor backLeft;
    
    
    // todo: write your code here
    
    
    public void runOpMode(){
        
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();
        
        while (opModeIsActive()){
            if (this.gamepad1.dpad_up){
                backLeft.setPower(-1);
                frontRight.setPower(1);
                backRight.setPower(1);
                frontLeft.setPower(-1);
            }
            if (this.gamepad1.dpad_left){
                backLeft.setPower(-1);
                frontRight.setPower(1);
                backRight.setPower(-1);
                frontLeft.setPower(1);
            }
            if (this.gamepad1.dpad_down){
                backLeft.setPower(1);
                frontRight.setPower(-1);
                backRight.setPower(-1);
                frontLeft.setPower(1);
            }
            if (this.gamepad1.dpad_right){
                backLeft.setPower(1);
                frontRight.setPower(-1);
                backRight.setPower(1);
                frontLeft.setPower(-1);
            }
            else {
                backLeft.setPower(0);
                frontRight.setPower(0);
                backRight.setPower(0);
                frontLeft.setPower(0);
            }
    }
    
}
    // todo: write your code here
}