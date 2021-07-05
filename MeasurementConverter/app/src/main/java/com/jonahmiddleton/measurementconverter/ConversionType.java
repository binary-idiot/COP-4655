package com.jonahmiddleton.measurementconverter;

import androidx.annotation.NonNull;

public class ConversionType {
    public final String from;
    public final String to;
    public final double ratio;

    public ConversionType(String From, String To, double Ratio){
        this.from = From;
        this.to = To;
        this.ratio = Ratio;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s to %s", this.from, this.to);
    }

    public double convert(double value){
        return value * this.ratio;
    }
}
