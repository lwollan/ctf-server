package no.soprasteria.sikkerhet.owasp.ctf.games.structure;

public class FlagStructure {

    public String flagName;
    public String beskrivelse;
    public String flag;
    public String tips;
    public String poeng;

    public static FlagStructure newStructure(String flagName, String flag, Long poeng, String tips, String beskrivelse) {
        FlagStructure flagStructure = new FlagStructure();
        flagStructure.flagName = flagName;
        flagStructure.beskrivelse = beskrivelse;
        flagStructure.flag = flag;
        flagStructure.tips = tips;
        flagStructure.poeng = poeng.toString();

        return flagStructure;
    }
}
