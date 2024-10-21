package br.com.bellabiango.caju.autorizador.utils;

import java.util.Map;

public class MccCategory {
    private static final Map<String, String> MCC_TO_CATEGORY_MAP;

    static {

        MCC_TO_CATEGORY_MAP = Map.of(
                "5411", "FOOD",
                "5412", "FOOD",
                "5811", "MEAL",
                "5812", "MEAL");
    }

    public static String getCategoryByMcc(String mcc) {
        return MCC_TO_CATEGORY_MAP.get(mcc);
    }
}
