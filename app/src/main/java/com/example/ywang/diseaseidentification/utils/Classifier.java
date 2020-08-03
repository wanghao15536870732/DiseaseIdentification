package com.example.ywang.diseaseidentification.utils;

import android.graphics.Bitmap;
import android.util.Log;

import org.pytorch.IValue;
import org.pytorch.Module;
import org.pytorch.Tensor;
import org.pytorch.torchvision.TensorImageUtils;

import java.util.Arrays;

public class Classifier {

    Module model;
    float[] mean = {0.485f, 0.456f, 0.406f};
    float[] std = {0.229f, 0.224f, 0.225f};

    public Classifier(String modelPath) {
        model = Module.load(modelPath);
    }

    public void setMeanAndStd(float[] mean, float[] std) {
        this.mean = mean;
        this.std = std;
    }

    public Tensor preprocess(Bitmap bitmap, int size) {
        bitmap = Bitmap.createScaledBitmap(bitmap, size, size, false);
        return TensorImageUtils.bitmapToFloat32Tensor(bitmap, this.mean, this.std);
    }

    public int argMax(float[] inputs) {
        int maxIndex = -1;
        float maxvalue = 0.0f;
        for (int i = 0; i < inputs.length; i++) {
            if (inputs[i] > maxvalue) {
                maxIndex = i;
                maxvalue = inputs[i];
            }
        }
        return maxIndex;
    }

    public String predict(Bitmap bitmap) {
        Tensor tensor = preprocess(bitmap, 325);
        IValue inputs = IValue.from(tensor);
        Tensor outputs = model.forward(inputs).toTensor();
        float[] scores = outputs.getDataAsFloatArray();
        Log.e("score", Arrays.toString(scores));

        StringBuilder predict_result = new StringBuilder();
        while (argMax(scores) != -1){
            int classIndex = argMax(scores);
            predict_result.append(Constants.DISEASE_CLASSES[classIndex]).append(";").append(scores[classIndex]).append(";");
            scores[classIndex] = 0.0f;
        }
        return predict_result.toString();
    }
}