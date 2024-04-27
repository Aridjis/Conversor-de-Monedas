package modelos;

public class RespuestaPersonalizada {
    private String base_code;
    private String target_code;
    private double conversion_rate;
    private double conversion_result;


    public RespuestaPersonalizada(String base_code, String target_code, double conversion_rate, double conversion_result) {
        this.base_code = base_code;
        this.target_code = target_code;
        this.conversion_rate = conversion_rate;
        this.conversion_result = conversion_result;

    }

    public String getBase_code() {
        return base_code;
    }

    public String getTarget_code() {
        return target_code;
    }

    public double getConversion_rate() {
        return conversion_rate;
    }

    public double getConversion_result() {
        return conversion_result;
    }

    @Override
    public String toString() {
        return "Moneda base: " + base_code + "\n" +
        "Moneda a convertir: " + target_code + "\n" +
                "Tipo de cambio: " + conversion_rate;
    }
}

