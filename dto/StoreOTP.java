package com.nineleaps.authentication.jwt.dto;

import com.nineleaps.authentication.jwt.dto.StoreOTP;

public class StoreOTP {

    private static int otp;

    public static int getOtp() {
        return otp;
    }

    public static void setOtp(int otp) {
        StoreOTP.otp = otp;
    }
}
