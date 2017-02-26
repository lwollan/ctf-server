package no.soprasteria.sikkerhet.owasp.ctf.games;

import no.soprasteria.sikkerhet.owasp.ctf.service.FlagService;

public class MrRobotGame {

    public static void setupGame(FlagService flagService) {
        String beskrivelse = "beskrivelse";
        flagService.addFlag("eps1.0_hellofriend.mov", "Sam Esmail", 1L, "Hvem skrev", beskrivelse);
        flagService.addFlag("eps1.1_ones-and-zer0es.mpeg", "Sam Esmail", 1L, "Hvem skrev", beskrivelse);
        flagService.addFlag("eps1.2_d3bug.mkv", "Sam Esmail", 1L, "Hvem skrev", beskrivelse);
        flagService.addFlag("eps1.3_da3m0ns.mp4", "Adam Penn", 1L, "Hvem skrev", beskrivelse);
        flagService.addFlag("eps1.4_3xpl0its.wmv", "David Iserson", 1L, "Hvem skrev", beskrivelse);
        flagService.addFlag("eps1.5_br4ve-trave1er.asf", "Kyle Bradstreet", 1L, "Hvem skrev", beskrivelse);
        flagService.addFlag("eps1.6_v1ew-s0urce.flv", "Kate Erickson", 1L, "Hvem skrev", beskrivelse);
        flagService.addFlag("eps1.7_wh1ter0se.m4v", "Randolph Leon", 1L, "Hvem skrev", beskrivelse);
        flagService.addFlag("eps1.8_m1rr0r1ng.qt", "Sam Esmail", 1L, "Hvem skrev", beskrivelse);
        flagService.addFlag("eps1.9_zer0-day.avi", "Sam Esmail", 1L, "Hvem skrev", beskrivelse);
    }
}
