package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@TeleOp
// Wheel Movement
public class MechWheelTesdt extends LinearOpMode{
    private DcMotor frontRight;
    private DcMotor frontLeft;
    private DcMotor backRight;
    private DcMotor backLeft;
    // todo: write your code here
    
    
    public void runOpMode(){
        double leftstickX;
        double leftstickY;
        double rightstickX;
        double rightstickY;
        double direction;
        double magnitude;
        double fRightBLeft;
        double fLeftBRight;
        double turn;
        
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();
        
        while (opModeIsActive()){
                
            leftstickX = this.gamepad1.left_stick_x;
            leftstickY = -this.gamepad1.left_stick_y;
                        
            turn = this.gamepad1.right_stick_x;
                        
            direction = Math.atan2(leftstickY, leftstickX);
            magnitude = Math.sqrt(Math.pow(leftstickX, 2) + Math.pow(leftstickY, 2));
                        
            fRightBLeft = (Math.sin(direction - 1.0/4.0 * Math.PI) * magnitude + turn);
            fLeftBRight = (Math.sin(direction + 1.0/4.0 * Math.PI) * magnitude + turn);
                        
                if (fRightBLeft > 1 || fRightBLeft < -1){
                            fRightBLeft = (fRightBLeft / Math.abs(fRightBLeft));
                            fLeftBRight = (fLeftBRight / Math.abs(fRightBLeft));
                }
        
                        if (fLeftBRight > 1 || fLeftBRight < -1){
                                fLeftBRight = (fLeftBRight / Math.abs(fLeftBRight));
                                fRightBLeft = (fRightBLeft / Math.abs(fLeftBRight));
                        }

            backLeft.setPower(-fRightBLeft);
            frontRight.setPower(fRightBLeft);
            backRight.setPower(fLeftBRight);
            frontLeft.setPower(-fLeftBRight);
            
            telemetry.addData("Status", "Initialized");
            telemetry.addData("leftX", leftstickX);
            telemetry.addData("leftY", leftstickY);
            telemetry.addData("Direction", direction);
            telemetry.addData("magnitude", magnitude);
            telemetry.addData("fRightBleft", fRightBLeft);
            telemetry.addData("fRightBleft", fLeftBRight);
            telemetry.update();
            
        }
    }
    
}