package com.project.crop_prediction.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

@SuppressWarnings("HardCodedStringLiteral")
public class PredictionDeserializer implements JsonDeserializer<Prediction> {

    @Override
    public Prediction deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        JsonArray jsonConf = jsonObject.get(Prediction.CodingKeys.confidences.rawValue).getAsJsonArray();
        double[] conf = new double[jsonConf.size()];

        for (int i = 0; i < jsonConf.size(); i++)
            conf[i] = jsonConf.get(i).getAsDouble();

        return new Prediction(jsonObject.get(Prediction.CodingKeys.predicted_idx.rawValue).getAsInt(),
                conf,
                jsonObject.get(Prediction.CodingKeys.kind.rawValue).getAsString().equalsIgnoreCase("crop") ? Prediction.Kind.crop : Prediction.Kind.disease);
    }

}
