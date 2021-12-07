/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name="Distance Test",group = "Test")
@Disabled
public class DistanceTest extends LinearOpMode{
    
    private DcMotor frontRight;
    private DcMotor frontLeft;
    private DcMotor backRight;
    private DcMotor backLeft;
    private DistanceSensor rangeSen;

    
    @Override
    public void runOpMode() {
        
        
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        rangeSen = hardwareMap.get(DistanceSensor.class, "rangeSen");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");

        waitForStart();

        while (opModeIsActive()) {

            double fRight;
            double bRight;
            double bLeft;
            double fLeft;

            double deg = 90.0;

            double magnitude = 0.6;

            double direction = deg * (Math.PI / 180);

            if (rangeSen.getDistance(DistanceUnit.INCH) > 12) {

                fRight = (Math.sin(direction - 1.0 / 4.0 * Math.PI) * magnitude);
                bLeft = (-Math.sin(direction - 1.0 / 4.0 * Math.PI) * magnitude);
                bRight = (Math.sin(direction + 1.0 / 4.0 * Math.PI) * magnitude);
                fLeft = (- Math.sin(direction + 1.0 / 4.0 * Math.PI) * magnitude);

                if (fRight > 1 || fRight < -1) {
                    fLeft = (fLeft / Math.abs(fRight));
                    fRight = (fRight / Math.abs(fRight));
                    bRight = (bRight / Math.abs(fRight));
                    bLeft = (bLeft / Math.abs(fRight));
                }

                if (fLeft > 1 || fLeft < -1) {
                    fLeft = (fLeft / Math.abs(fLeft));
                    fRight = (fRight / Math.abs(fLeft));
                    bLeft = (bLeft / Math.abs(fLeft));
                    bRight = (bRight / Math.abs(fLeft));
                }

            }
            else {
                fRight = 0;
                bLeft = 0;
                bRight = 0;
                fLeft = 0;
            }

            backLeft.setPower(bLeft);
            frontRight.setPower(fRight);
            backRight.setPower(bRight);
            frontLeft.setPower(fLeft);

            telemetry.addData("Distance:", rangeSen.getDistance(DistanceUnit.INCH));
            telemetry.update();
        }
        
        
        
    }
}
