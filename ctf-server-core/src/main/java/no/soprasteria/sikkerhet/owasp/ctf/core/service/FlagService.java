package no.soprasteria.sikkerhet.owasp.ctf.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static no.soprasteria.sikkerhet.owasp.ctf.core.service.FlagService.Keys.tips;

public class FlagService {

    private static Logger logger = LoggerFactory.getLogger(FlagService.class);

    private Map<String, Map<String, String>> flags = new HashMap<>();

    public enum Keys {
        flagId, flagName, beskrivelse, flag, tips, poeng
    }

    public String addFlag(String name, String svar, Long poeng, String tips, String beskrivelse) {
        String id = UUID.randomUUID().toString();
        Map<String, String> nyttFlagg = nyttFlagg(id, name, svar, poeng.toString(), tips, beskrivelse);
        flags.put(id, nyttFlagg);

        logger.info("Flag: {} {}", id, nyttFlagg);
        return id;
    }

    public boolean isFlag(String flagId) {
        return flags.containsKey(flagId);
    }

    public void delFlag(String flagId) {
        flags.remove(flagId);
    }

    public boolean isCorrect(String flagId, String flag) {
        if (flags.containsKey(flagId)) {
            Map<String, String> map = this.flags.get(flagId);
            String svar = map.get(Keys.flag.toString());
            return svar.equals(flag);
        } else {
            return false;
        }
    }

    public Long getPoints(String flagId) {
        if (flags.containsKey(flagId)) {
            Map<String, String> map = this.flags.get(flagId);
            String poeng = map.get(Keys.poeng.toString());
            return Long.parseLong(poeng);
        } else {
            return 0l;
        }
    }

    public String getTip(String flagId) {
        if (flags.containsKey(flagId)) {
            Map<String, String> map = this.flags.get(flagId);
            return  map.get(tips.toString());
        } else {
            return null;
        }
    }

    public List<Map<String, String>> listFlag() {
        return new ArrayList<>(flags.values());
    }


    static Map<String, String> nyttFlagg(String id, String name, String svar, String poeng, String tips, String beskrivelse) {
        Map<String, String> flag = new HashMap<>();
        flag.put(Keys.flagId.toString(), id);
        flag.put(Keys.flagName.toString(), name);
        flag.put(Keys.tips.toString(), tips);
        flag.put(Keys.flag.toString(), svar);
        flag.put(Keys.poeng.toString(), poeng);
        flag.put(Keys.beskrivelse.toString(), beskrivelse);
        return flag;
    }

    public void clear() {
        flags.clear();
    }

}
