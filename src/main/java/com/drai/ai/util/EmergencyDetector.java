package com.drai.ai.util;

import java.util.Locale;
import java.util.Set;

public class EmergencyDetector {

    private static final Set<String> KEYS = Set.of(
            "chest pain","unconscious","severe bleeding",
            "shortness of breath","seizure","severe headache",
            "breathing difficulty","loss of consciousness"
    );

    public static boolean isEmergency(String msg) {
        if (msg == null) return false;
        String text = msg.toLowerCase(Locale.ROOT);
        return KEYS.stream().anyMatch(text::contains);
    }
}
