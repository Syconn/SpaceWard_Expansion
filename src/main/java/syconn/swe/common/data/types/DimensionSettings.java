package syconn.swe.common.data.types;

import com.google.gson.JsonObject;

public record DimensionSettings(double gravity, boolean breathable) {

    @Override
    public String toString() {
        return "Gravitational Force: " + gravity + " Breathable: " + breathable;
    }

    public static DimensionSettings fromGson(JsonObject o){
        return new DimensionSettings(o.get("gravity").getAsDouble(), o.get("breathable").getAsBoolean());
    }
}
