//package br.com.fiap.watchtower.service.mapper;
//
//
//import org.springframework.stereotype.Component;
//
//import br.com.fiap.watchtower.model.RiskPoint;
//import br.com.fiap.watchtower.model.SensorData;
//import org.json.JSONObject;
//
//@Component
//public class RiskPointMapper {
//
//    public RiskPoint toEntity(String jsonObject){
//        jsonObject =
//        JSONObject message = jsonObject.getJSONObject("message");
//        JSONObject sensorDataJson = message.getJSONObject("sensorData");
//        SensorData sensorData = new SensorData(
//                sensorDataJson.getDouble("temperatura"),
//                sensorDataJson.getDouble("umidade"),
//                sensorDataJson.getDouble("pressao"),
//                sensorDataJson.getDouble("vento"),
//                sensorDataJson.getDouble("chuva"),
//                sensorDataJson.getDouble("nivelAgua"),
//                sensorDataJson.getDouble("gases"),
//                sensorDataJson.getDouble("luminosidade")
//        );
//        return new RiskPoint(
//                message.getLong("id"),
//                message.getDouble("latitude"),
//                message.getDouble("longitude"),
//                message.getString("desasterType"),
//                message.getString("markerType"),
//                message.getString("timestamp"),
//                message.getString("aiAnalysis"),
//                message.getString("description"),
//                sensorData
//        );
//    }
//}
