package aoc.eighteen;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Day2 {

    private static final String INPUT = "oiwcdpbseqgxryfmlpktnupvza\r\n" + "oiwddpbsuqhxryfmlgkznujvza\r\n" + "ziwcdpbsechxrvfmlgktnujvza\r\n" + "oiwcgpbseqhxryfmmgktnhjvza\r\n"
            + "owwcdpbseqhxryfmlgktnqjvze\r\n" + "oiscdkbseqhxrffmlgktnujvza\r\n" + "oiwcdibseqoxrnfmlgktnujvza\r\n" + "oiwcdpbsejhxryfmlektnujiza\r\n" + "oewcdpbsephxryfmlgkwnujvza\r\n"
            + "riwcdpbseqhxryfmlgktnujzaa\r\n" + "omwcdpbseqwxryfmlgktnujvqa\r\n" + "oiwcdqqneqhxryfmlgktnujvza\r\n" + "oawcdvaseqhxryfmlgktnujvza\r\n" + "oiwcdabseqhxcyfmlkktnujvza\r\n"
            + "oiwcdpbseqhxryfmlrktrufvza\r\n" + "oiwcdpbseqhxdyfmlgqtnujkza\r\n" + "oiwcdpbseqhxrmfolgktnujvzy\r\n" + "oiwcdpeseqhxnyfmlgktnejvza\r\n" + "oiwcdpbseqhxrynmlaktlujvza\r\n"
            + "oiwcdpbseqixryfmlektncjvza\r\n" + "liwtdpbseqhxryfylgktnujvza\r\n" + "ouwcdpbszqhxryfmlgktnajvza\r\n" + "oiwzdpbseqhxryfmngktnujvga\r\n" + "wiwcfpbseqhxryfmlgktnuhvza\r\n"
            + "oiwcdpbselhfryfmlrktnujvza\r\n" + "oywcdpbveqhxryfmlgktnujdza\r\n" + "oiwcdpbsiqhxryfmqiktnujvza\r\n" + "obwcdhbseqhxryfmlgktnujvqa\r\n" + "oitcdpbseqhfryfmlyktnujvza\r\n"
            + "oiwcdpbseqhxryfmlnutnujqza\r\n" + "oiwcdpbseqhxnyfmlhktnujtza\r\n" + "oiwcdpbseqhbryfmlgkunuwvza\r\n" + "oiwcopbseqhiryfmlgktnkjvza\r\n" + "oiwcdpsseqhxryfklrktnujvza\r\n"
            + "oiwcdpsrsqhxryfmlgktnujvza\r\n" + "oiwcdpbsyqrxryfmlgktnujvzc\r\n" + "ojwcepbseqhxryfmlgktnujvfa\r\n" + "oiwcdpbseqhxrlfmlgvtnujvzr\r\n" + "oiycdpbsethsryfmlgktnujvza\r\n"
            + "eiwcdpbseqwxryfmlgktnujcza\r\n" + "oiocdpbseqhxryfxlgktaujvza\r\n" + "qiwydpbseqhpryfmlgktnujvza\r\n" + "ziwcdpbseqhxryfmlgktsuuvza\r\n" + "oiwcdpbseqheryfmygxtnujvza\r\n"
            + "oiwidpbseqhxryfulgktnujvzm\r\n" + "oiscdpdseqhxryfmlgktnujvya\r\n" + "oiwmypbseqhxryfmlgktnuxvza\r\n" + "oizcwbbseqhxryfmlgktnujvza\r\n" + "oiwcdpbseqpxryfmlgxfnujvza\r\n"
            + "oiwpdpbscqhxryxmlgktnujvza\r\n" + "oiwcdpbseqhxrifrlgkxnujvza\r\n" + "oiwcdpbsrqhxrifmlgktnzjvza\r\n" + "tiwcdpbseqhxryfmegkvjujvza\r\n" + "oiwcddbseqhxryfingktnujvza\r\n"
            + "oiwcdpbsiqhiryfmlgktnucvza\r\n" + "oiwcipbseqhxrkfmlgktnuvvza\r\n" + "kiwcdpbseqhxryfmlbkonujvza\r\n" + "qiwcdhbsedhxryfmlgktnujvza\r\n" + "siwcdpbseqhxryfmsgktnujvua\r\n"
            + "oqwcdpbseqhxryfmlyktndjvza\r\n" + "oiwcqnbseehxryfmlgktnujvza\r\n" + "oiwcdybseqhxryfmlgbtnujvia\r\n" + "oiwcdpbsejhxryfdlgktngjvza\r\n" + "oawcdpbseqhxrbfmlkktnujvza\r\n"
            + "oilcdpbseqhhrjfmlgktnujvza\r\n" + "oibcdpbseqhxryfmngktnucvza\r\n" + "niwcdpbseqhxryfmlgkuaujvza\r\n" + "oiwcdpbseqhxryfmqgmtnujvha\r\n" + "oiwcdpbseqhcryfxlgktnzjvza\r\n"
            + "oiwcdpaseqhxryfmqgktnujvzl\r\n" + "oiwcdpbseqhxjyfmlgkonujvzx\r\n" + "oiwmdzbseqlxryfmlgktnujvza\r\n" + "oiwhdpbsexhxryfmlgktnujvzw\r\n" + "oiwctpbseqhxryfmlgktnummza\r\n"
            + "oiwcdpbseqoxrydmlgktnujvoa\r\n" + "oiwkdpvseqhxeyfmlgktnujvza\r\n" + "oixcdpbsemhxryfmlgctnujvza\r\n" + "oimcdpbseqhxryfmlgktnujvlr\r\n" + "oiwcdpbseehxrywmlgktnejvza\r\n"
            + "oiwcdpbseqoxhyfmlgktnujyza\r\n" + "oiwcdpbsethxryfmlgktnrjvxa\r\n" + "oiwcdpbxeqhxryfmlgktnrjvzb\r\n" + "ogeadpbseqhxryfmlgktnujvza\r\n" + "eiwcdpbseqhxryfmlgktnvuvza\r\n"
            + "oiwcdpbseqhxryfmlgktnujaxv\r\n" + "siwcdpbsuqhxryfmlgktnuavza\r\n" + "oixcdpbseqhxryfmlgatnujvzy\r\n" + "oiwcdpbzeghmryfmlgktnujvza\r\n" + "oiwcdpbieqhxryfmlgktyujvzr\r\n"
            + "oiwcdpbseqhxeyfhlgktngjvza\r\n" + "oiwcdpbseqhjoyrmlgktnujvza\r\n" + "iiwcdpbseqhxryfmwhktnujvza\r\n" + "oixcdpbsiqhxryfmagktnujvza\r\n" + "oiwcdpfljqhxryfmlgktnujvza\r\n"
            + "oivcdpbseqhxrqfmlgktnujvca\r\n" + "ovwcdpbseqhxzyfmlgkenujvza\r\n" + "oiwxdpgseqhxryfmlgktnhjvza\r\n" + "oibcdpbbeohxryfmlgktnujvza\r\n" + "oiwcrpbseqhxrygmlgwtnujvza\r\n"
            + "jiwcdpbseqkxryfmlgntnujvza\r\n" + "oiwcdbbseqhxrywmlgktnujvra\r\n" + "oiwcdpbteqyxoyfmlgktnujvza\r\n" + "oiwcdjbsvqvxryfmlgktnujvza\r\n" + "obwcdukseqhxryfmlgktnujvza\r\n"
            + "oifcdpdseqhxryfmlgktnujsza\r\n" + "oiwcdpbseqhxryfalgktnujyda\r\n" + "oiwcwpbseqhxrnfmkgktnujvza\r\n" + "oswcdpbsethcryfmlgktnujvza\r\n" + "oiwcdpbieqhxryfmlgktnuoiza\r\n"
            + "oiwcdibsehhxryfmzgktnujvza\r\n" + "oisjdpbseqhxryfmfgktnujvza\r\n" + "oiwcjpbseqkxqyfmlgktnujvza\r\n" + "obwcdpbshqhgryfmlgktnujvza\r\n" + "oiwcspbseqhxryxmlgktnujvzl\r\n"
            + "oiwcdvbswqhxryfmlgklnujvza\r\n" + "oiwcdhuseqhxryfmlgdtnujvza\r\n" + "oiwcdpbkeqdxryfmlgktnujvzv\r\n" + "oiwcdpzseqhxcyfmlgksnujvza\r\n" + "oiwcdpbseqhxryfmbkkvnujvza\r\n"
            + "qiwcdpbseqhxrnfmlgktnujvha\r\n" + "okwcdpbseqhxryfmdgktgujvza\r\n" + "oiwcdpbkeqhxryfmldktnujvzu\r\n" + "oiwctpxseqhxgyfmlgktnujvza\r\n" + "oiwcdpbseqhxrykmlgktnujita\r\n"
            + "oiwcdpbseqhxryfmldktyujnza\r\n" + "oiwcdpbszqhxrjfmlgktnuqvza\r\n" + "oiwcdpbeeqhxrykmlgktnujrza\r\n" + "oiwcvpbseqhxryhmlgwtnujvza\r\n" + "oiwcdpbpeehxryfmlgktnujvzz\r\n"
            + "oiwcdbbsxqhxyyfmlgktnujvza\r\n" + "oiwkdpbseqhxryfplgktnujeza\r\n" + "opwcdpbseqhxdyfmlgctnujvza\r\n" + "oowcdpbseqhnryfmlgktnujvga\r\n" + "oawzdibseqhxryfmlgktnujvza\r\n"
            + "oiwcdpbfeqzxrjfmlgktnujvza\r\n" + "oiwcdpbseqaxryfmlgkonulvza\r\n" + "oiacdpbseqvxryfmlgktvujvza\r\n" + "oiwudpbseqhxryfwlgktnujvka\r\n" + "oiwcdpbjeqhxryfymgktnujvza\r\n"
            + "oiwcdpbweqhxrynmlgktnujaza\r\n" + "oiwcdpbseqdxryfclgvtnujvza\r\n" + "oiwcdppseqhxryfmlgmtzujvza\r\n" + "oiwcdpbseqhxrhfelektnujvza\r\n" + "kiwcdpbsnqhxryfmegktnujvza\r\n"
            + "oiwcdpbseqpxryfmlgzwnujvza\r\n" + "eiwcdpbseqnxryfplgktnujvza\r\n" + "oiwcdbbseqhxryfmlmktnujvha\r\n" + "oiwcdpbseqhxryfmlgktjhjvka\r\n" + "oiwcdpbseqhxnyfylgktnujvzs\r\n"
            + "oiwcdpbbxqhxryfmzgktnujvza\r\n" + "opwcdpbseqhfryfmlgktnujzza\r\n" + "oiwcdpbsjqpxryfmtgktnujvza\r\n" + "oiwcdpbseqhyqbfmlgktnujvza\r\n" + "oxwcdpbseqhxrffmlgktiujvza\r\n"
            + "oiwcdpbseqhxgyfmlgytnujvzq\r\n" + "oiwidpbseqhxryfmlgxtnujvzd\r\n" + "oiwcdpbshqhxryzmlpktnujvza\r\n" + "oifcdpbseqbxryfmlgktdujvza\r\n" + "biwcdzbseqhxtyfmlgktnujvza\r\n"
            + "oiwcdpbswqhxryfmlgutnujvca\r\n" + "xiwcdpbseqhxryxmlnktnujvza\r\n" + "oiwcdpzseqhxryfrlgktdujvza\r\n" + "oiwudpbseqhxryfmlgkqnurvza\r\n" + "oiwqdpbseihiryfmlgktnujvza\r\n"
            + "iiwjdpbseqhxryamlgktnujvza\r\n" + "oiwcdplseqhqryfmmgktnujvza\r\n" + "oiwcdppseqhxrmfmlgklnujvza\r\n" + "oiwcdobseqhxryfmmgkttujvza\r\n" + "odwcdpbseqhxryfmlgktnujvyk\r\n"
            + "oiwcdpkseqhxrhfmlgktntjvza\r\n" + "oiocdpbseqhxryfmlgjknujvza\r\n" + "oiicdpbieqhxryfmlgksnujvza\r\n" + "oiwcdpbseqhxryemlgktnujdla\r\n" + "oiwcdpbseqdxryfmlgktsujvzt\r\n"
            + "oiwcdcksnqhxryfmlgktnujvza\r\n" + "oowcdpbseqhxryfmlgsfnujvza\r\n" + "oawcdpbseqhxryfmljktnuevza\r\n" + "oiwcdpbsaqhxrffmzgktnujvza\r\n" + "oiwcipbseqhcryfmlgktnujvga\r\n"
            + "oiwcdpbsewhxrbfmlgktnuuvza\r\n" + "oiwcdpbsewhxryfmlgkunujvzc\r\n" + "oiwcdpbseqhxryfmlgktiujkga\r\n" + "jiwcdpbseqhxrlfmlgktnurvza\r\n" + "tiwcdpbseqoxryfmliktnujvza\r\n"
            + "oiwcdpbsenhxryfmlgkskujvza\r\n" + "oiwcdpbseqhxvyfmlhktoujvza\r\n" + "oiwcdpbseqhxeyfmlgmtnunvza\r\n" + "oiwcdpbseqhxdyfmloktnujvzu\r\n" + "oiwcdpbseqgxryfmlgkynejvza\r\n"
            + "oudcdpbseqhxryfmlgktmujvza\r\n" + "oiwcdpbseqhxryfmvgktnucvzc\r\n" + "oiwcdpbseqhxrysalgwtnujvza\r\n" + "odwodpbseqhgryfmlgktnujvza\r\n" + "oiwcdpbseqheryzmlgktnujgza\r\n"
            + "oiwcdpbseqhxryfalgwtnujvba\r\n" + "oiwcdpbseqhxryfmlgtdnuhvza\r\n" + "oiocdpbseqhxrefulgktnujvza\r\n" + "kiwcdpbseqhxrywolgktnujvza\r\n" + "niwcdpbseksxryfmlgktnujvza\r\n"
            + "oiwcdybseqexryfalgktnujvza\r\n" + "oiwcdpbbeqhxryamlgktnujpza\r\n" + "oiecdqbseqhxryfmlgktnujcza\r\n" + "oiwcdpbsqqhxlyfmlpktnujvza\r\n" + "oiwcdpbsaqheryfmlgktnujlza\r\n"
            + "oiecdpbseqhxryfmlgkknujvzz\r\n" + "oiwcapbsdqhxryfmlgktvujvza\r\n" + "ojwcdxbseqhxryfmlgktrujvza\r\n" + "oiwhdpbseqvxrzfmlgktnujvza\r\n" + "oiwcdppseqhtryfmlgktnujvzs\r\n"
            + "oikcdpbsfqhxryfmdgktnujvza\r\n" + "owwczpbseqhxryfilgktnujvza\r\n" + "oifwdpbseqhxryfmlgktnujfza\r\n" + "oowcdpbseqhxrprmlgktnujvza\r\n" + "oiwcapbseqhxryfmjgktnujvze\r\n"
            + "oiwcdpaseqhdrybmlgktnujvza\r\n" + "tiwcdpbseqhxryfmlgkvjujvza\r\n" + "xiwcdpbseqhoryfmlgktnujvqa\r\n" + "eiwrdpbsyqhxryfmlgktnujvza\r\n" + "oiwcdpbseqhxranmlgktnujvzt\r\n"
            + "oiwcdpbseqhxryfqlgktnudaza\r\n" + "oiwcdpbsvqhxrywmlgktnsjvza\r\n" + "oewcdpbseqhxryfmlgkunujvma\r\n" + "oiwcdpbseqhjrywmlgktnujvzb\r\n" + "omwcdpbseqhxryfmlgktwujsza\r\n"
            + "oiwcdpbyxqhxryfmlgrtnujvza\r\n" + "oiwidpbseqhxryfhlgktnunvza\r\n" + "oqwcdpbweqhxrybmlgktnujvza\r\n" + "oiwcdqbseqhxryfrlgktnujfza\r\n" + "oiacdpbseqhdryfmlgktaujvza\r\n"
            + "oiwcdpbstqhxmyfmlgktyujvza\r\n" + "oiwcdpbseqhxeyfclgktjujvza\r\n" + "wiwcdpeseqhxryfmlgktnujvzx\r\n" + "viwcdpbseqhxryfmlgvtvujvza\r\n" + "oircdpbseqhxcyfmlgktnujvma\r\n"
            + "miwcdpbseqtwryfmlgktnujvza\r\n" + "oiwcppbseqhxcyfmlgxtnujvza\r\n" + "giwcrpbseqhxryfmlgktnudvza\r\n" + "onwcvpbseqhxryfmlgktnujqza\r\n" + "oiwcipbseqhxryfmlgitnuqvza\r\n"
            + "oiwcdpbseqhxryjmlgkonufvza\r\n" + "oiwnwpbseqhxtyfmlgktnujvza\r\n" + "oiwcypbseqhxryfmlgetnujvzv\r\n" + "oiwcdpbseqhxryqmljktnkjvza\r\n" + "olwcdpbseqhxryfmlgkenujvba\r\n"
            + "biwcdpbseqwxrywmlgktnujvza\r\n" + "oiwcdpbsevhmryjmlgktnujvza\r\n" + "oiwcdpbseqhxryfmlzktnkjvzv\r\n" + "oiwudpbseqhxrefmlgktnujvia\r\n" + "oiicdpbseqhxryfdloktnujvza\r\n"
            + "oihcjpbsxqhxryfmlgktnujvza";

    private static int part1() {
        AtomicInteger twoCount = new AtomicInteger();
        AtomicInteger threeCount = new AtomicInteger();

        new BufferedReader(new StringReader(INPUT)).lines() //
                .forEach(line -> {
                    Map<Character, Integer> counts = new HashMap<>();
                    line.chars().forEach(c -> counts.merge((char) c, 1, (o, n) -> o + n));
                    counts.entrySet().stream().filter(e -> e.getValue() == 2 || e.getValue() == 3) //
                            .collect(Collectors.toList());
                    if (counts.containsValue(2)) {
                        twoCount.incrementAndGet();
                    }
                    if (counts.containsValue(3)) {
                        threeCount.incrementAndGet();
                    }
                });

        return twoCount.get() * threeCount.get();
    }

    private static String part2() {
        List<List<Character>> lines = new BufferedReader(new StringReader(INPUT)).lines() //
                .map(line -> line.chars().mapToObj(i -> Character.valueOf((char) i)).collect(Collectors.toList())) //
                .collect(Collectors.toList());

        for (int i = 0; i < lines.size(); i++) {
            List<Character> chars = lines.get(i);
            for (int j = 0; j < lines.size(); j++) {
                int mismatchIndex = -1;
                List<Character> other = lines.get(j);
                for (int k = 0; k < chars.size(); k++) {
                    if (chars.get(k) != other.get(k)) {
                        if (mismatchIndex != -1) {
                            mismatchIndex = -1;
                            break;
                        }
                        mismatchIndex = k;
                    }
                }
                if (mismatchIndex != -1) {
                    chars.remove(mismatchIndex);
                    return chars.stream().collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
                }

            }

        }

        throw new IllegalStateException();
    }

    public static void main(final String[] args) {
        System.out.println(Day2.part1());
        System.out.println(Day2.part2());
    }
}
