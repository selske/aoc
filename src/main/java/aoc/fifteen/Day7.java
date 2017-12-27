package aoc.fifteen;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day7 {

    private static final String INPUT = // "123 -> x\n456 -> y\nx AND y -> d\nx OR y -> e\nx LSHIFT 2 -> f\ny RSHIFT 2 -> g\nNOT x -> h\nNOT y -> i";
            "af AND ah -> ai\n" +
            "NOT lk -> ll\n" +
            "hz RSHIFT 1 -> is\n" +
            "NOT go -> gp\n" +
            "du OR dt -> dv\n" +
            "x RSHIFT 5 -> aa\n" +
            "at OR az -> ba\n" +
            "eo LSHIFT 15 -> es\n" +
            "ci OR ct -> cu\n" +
            "b RSHIFT 5 -> f\n" +
            "fm OR fn -> fo\n" +
            "NOT ag -> ah\n" +
            "v OR w -> x\n" +
            "g AND i -> j\n" +
            "an LSHIFT 15 -> ar\n" +
            "1 AND cx -> cy\n" +
            "jq AND jw -> jy\n" +
            "iu RSHIFT 5 -> ix\n" +
            "gl AND gm -> go\n" +
            "NOT bw -> bx\n" +
            "jp RSHIFT 3 -> jr\n" +
            "hg AND hh -> hj\n" +
            "bv AND bx -> by\n" +
            "er OR es -> et\n" +
            "kl OR kr -> ks\n" +
            "et RSHIFT 1 -> fm\n" +
            "e AND f -> h\n" +
            "u LSHIFT 1 -> ao\n" +
            "he RSHIFT 1 -> hx\n" +
            "eg AND ei -> ej\n" +
            "bo AND bu -> bw\n" +
            "dz OR ef -> eg\n" +
            "dy RSHIFT 3 -> ea\n" +
            "gl OR gm -> gn\n" +
            "da LSHIFT 1 -> du\n" +
            "au OR av -> aw\n" +
            "gj OR gu -> gv\n" +
            "eu OR fa -> fb\n" +
            "lg OR lm -> ln\n" +
            "e OR f -> g\n" +
            "NOT dm -> dn\n" +
            "NOT l -> m\n" +
            "aq OR ar -> as\n" +
            "gj RSHIFT 5 -> gm\n" +
            "hm AND ho -> hp\n" +
            "ge LSHIFT 15 -> gi\n" +
            "jp RSHIFT 1 -> ki\n" +
            "hg OR hh -> hi\n" +
            "lc LSHIFT 1 -> lw\n" +
            "km OR kn -> ko\n" +
            "eq LSHIFT 1 -> fk\n" +
            "1 AND am -> an\n" +
            "gj RSHIFT 1 -> hc\n" +
            "aj AND al -> am\n" +
            "gj AND gu -> gw\n" +
            "ko AND kq -> kr\n" +
            "ha OR gz -> hb\n" +
            "bn OR by -> bz\n" +
            "iv OR jb -> jc\n" +
            "NOT ac -> ad\n" +
            "bo OR bu -> bv\n" +
            "d AND j -> l\n" +
            "bk LSHIFT 1 -> ce\n" +
            "de OR dk -> dl\n" +
            "dd RSHIFT 1 -> dw\n" +
            "hz AND ik -> im\n" +
            "NOT jd -> je\n" +
            "fo RSHIFT 2 -> fp\n" +
            "hb LSHIFT 1 -> hv\n" +
            "lf RSHIFT 2 -> lg\n" +
            "gj RSHIFT 3 -> gl\n" +
            "ki OR kj -> kk\n" +
            "NOT ak -> al\n" +
            "ld OR le -> lf\n" +
            "ci RSHIFT 3 -> ck\n" +
            "1 AND cc -> cd\n" +
            "NOT kx -> ky\n" +
            "fp OR fv -> fw\n" +
            "ev AND ew -> ey\n" +
            "dt LSHIFT 15 -> dx\n" +
            "NOT ax -> ay\n" +
            "bp AND bq -> bs\n" +
            "NOT ii -> ij\n" +
            "ci AND ct -> cv\n" +
            "iq OR ip -> ir\n" +
            "x RSHIFT 2 -> y\n" +
            "fq OR fr -> fs\n" +
            "bn RSHIFT 5 -> bq\n" +
            "0 -> c\n" +
            "14146 -> b\n" +
            "d OR j -> k\n" +
            "z OR aa -> ab\n" +
            "gf OR ge -> gg\n" +
            "df OR dg -> dh\n" +
            "NOT hj -> hk\n" +
            "NOT di -> dj\n" +
            "fj LSHIFT 15 -> fn\n" +
            "lf RSHIFT 1 -> ly\n" +
            "b AND n -> p\n" +
            "jq OR jw -> jx\n" +
            "gn AND gp -> gq\n" +
            "x RSHIFT 1 -> aq\n" +
            "ex AND ez -> fa\n" +
            "NOT fc -> fd\n" +
            "bj OR bi -> bk\n" +
            "as RSHIFT 5 -> av\n" +
            "hu LSHIFT 15 -> hy\n" +
            "NOT gs -> gt\n" +
            "fs AND fu -> fv\n" +
            "dh AND dj -> dk\n" +
            "bz AND cb -> cc\n" +
            "dy RSHIFT 1 -> er\n" +
            "hc OR hd -> he\n" +
            "fo OR fz -> ga\n" +
            "t OR s -> u\n" +
            "b RSHIFT 2 -> d\n" +
            "NOT jy -> jz\n" +
            "hz RSHIFT 2 -> ia\n" +
            "kk AND kv -> kx\n" +
            "ga AND gc -> gd\n" +
            "fl LSHIFT 1 -> gf\n" +
            "bn AND by -> ca\n" +
            "NOT hr -> hs\n" +
            "NOT bs -> bt\n" +
            "lf RSHIFT 3 -> lh\n" +
            "au AND av -> ax\n" +
            "1 AND gd -> ge\n" +
            "jr OR js -> jt\n" +
            "fw AND fy -> fz\n" +
            "NOT iz -> ja\n" +
            "c LSHIFT 1 -> t\n" +
            "dy RSHIFT 5 -> eb\n" +
            "bp OR bq -> br\n" +
            "NOT h -> i\n" +
            "1 AND ds -> dt\n" +
            "ab AND ad -> ae\n" +
            "ap LSHIFT 1 -> bj\n" +
            "br AND bt -> bu\n" +
            "NOT ca -> cb\n" +
            "NOT el -> em\n" +
            "s LSHIFT 15 -> w\n" +
            "gk OR gq -> gr\n" +
            "ff AND fh -> fi\n" +
            "kf LSHIFT 15 -> kj\n" +
            "fp AND fv -> fx\n" +
            "lh OR li -> lj\n" +
            "bn RSHIFT 3 -> bp\n" +
            "jp OR ka -> kb\n" +
            "lw OR lv -> lx\n" +
            "iy AND ja -> jb\n" +
            "dy OR ej -> ek\n" +
            "1 AND bh -> bi\n" +
            "NOT kt -> ku\n" +
            "ao OR an -> ap\n" +
            "ia AND ig -> ii\n" +
            "NOT ey -> ez\n" +
            "bn RSHIFT 1 -> cg\n" +
            "fk OR fj -> fl\n" +
            "ce OR cd -> cf\n" +
            "eu AND fa -> fc\n" +
            "kg OR kf -> kh\n" +
            "jr AND js -> ju\n" +
            "iu RSHIFT 3 -> iw\n" +
            "df AND dg -> di\n" +
            "dl AND dn -> do\n" +
            "la LSHIFT 15 -> le\n" +
            "fo RSHIFT 1 -> gh\n" +
            "NOT gw -> gx\n" +
            "NOT gb -> gc\n" +
            "ir LSHIFT 1 -> jl\n" +
            "x AND ai -> ak\n" +
            "he RSHIFT 5 -> hh\n" +
            "1 AND lu -> lv\n" +
            "NOT ft -> fu\n" +
            "gh OR gi -> gj\n" +
            "lf RSHIFT 5 -> li\n" +
            "x RSHIFT 3 -> z\n" +
            "b RSHIFT 3 -> e\n" +
            "he RSHIFT 2 -> hf\n" +
            "NOT fx -> fy\n" +
            "jt AND jv -> jw\n" +
            "hx OR hy -> hz\n" +
            "jp AND ka -> kc\n" +
            "fb AND fd -> fe\n" +
            "hz OR ik -> il\n" +
            "ci RSHIFT 1 -> db\n" +
            "fo AND fz -> gb\n" +
            "fq AND fr -> ft\n" +
            "gj RSHIFT 2 -> gk\n" +
            "cg OR ch -> ci\n" +
            "cd LSHIFT 15 -> ch\n" +
            "jm LSHIFT 1 -> kg\n" +
            "ih AND ij -> ik\n" +
            "fo RSHIFT 3 -> fq\n" +
            "fo RSHIFT 5 -> fr\n" +
            "1 AND fi -> fj\n" +
            "1 AND kz -> la\n" +
            "iu AND jf -> jh\n" +
            "cq AND cs -> ct\n" +
            "dv LSHIFT 1 -> ep\n" +
            "hf OR hl -> hm\n" +
            "km AND kn -> kp\n" +
            "de AND dk -> dm\n" +
            "dd RSHIFT 5 -> dg\n" +
            "NOT lo -> lp\n" +
            "NOT ju -> jv\n" +
            "NOT fg -> fh\n" +
            "cm AND co -> cp\n" +
            "ea AND eb -> ed\n" +
            "dd RSHIFT 3 -> df\n" +
            "gr AND gt -> gu\n" +
            "ep OR eo -> eq\n" +
            "cj AND cp -> cr\n" +
            "lf OR lq -> lr\n" +
            "gg LSHIFT 1 -> ha\n" +
            "et RSHIFT 2 -> eu\n" +
            "NOT jh -> ji\n" +
            "ek AND em -> en\n" +
            "jk LSHIFT 15 -> jo\n" +
            "ia OR ig -> ih\n" +
            "gv AND gx -> gy\n" +
            "et AND fe -> fg\n" +
            "lh AND li -> lk\n" +
            "1 AND io -> ip\n" +
            "kb AND kd -> ke\n" +
            "kk RSHIFT 5 -> kn\n" +
            "id AND if -> ig\n" +
            "NOT ls -> lt\n" +
            "dw OR dx -> dy\n" +
            "dd AND do -> dq\n" +
            "lf AND lq -> ls\n" +
            "NOT kc -> kd\n" +
            "dy AND ej -> el\n" +
            "1 AND ke -> kf\n" +
            "et OR fe -> ff\n" +
            "hz RSHIFT 5 -> ic\n" +
            "dd OR do -> dp\n" +
            "cj OR cp -> cq\n" +
            "NOT dq -> dr\n" +
            "kk RSHIFT 1 -> ld\n" +
            "jg AND ji -> jj\n" +
            "he OR hp -> hq\n" +
            "hi AND hk -> hl\n" +
            "dp AND dr -> ds\n" +
            "dz AND ef -> eh\n" +
            "hz RSHIFT 3 -> ib\n" +
            "db OR dc -> dd\n" +
            "hw LSHIFT 1 -> iq\n" +
            "he AND hp -> hr\n" +
            "NOT cr -> cs\n" +
            "lg AND lm -> lo\n" +
            "hv OR hu -> hw\n" +
            "il AND in -> io\n" +
            "NOT eh -> ei\n" +
            "gz LSHIFT 15 -> hd\n" +
            "gk AND gq -> gs\n" +
            "1 AND en -> eo\n" +
            "NOT kp -> kq\n" +
            "et RSHIFT 5 -> ew\n" +
            "lj AND ll -> lm\n" +
            "he RSHIFT 3 -> hg\n" +
            "et RSHIFT 3 -> ev\n" +
            "as AND bd -> bf\n" +
            "cu AND cw -> cx\n" +
            "jx AND jz -> ka\n" +
            "b OR n -> o\n" +
            "be AND bg -> bh\n" +
            "1 AND ht -> hu\n" +
            "1 AND gy -> gz\n" +
            "NOT hn -> ho\n" +
            "ck OR cl -> cm\n" +
            "ec AND ee -> ef\n" +
            "lv LSHIFT 15 -> lz\n" +
            "ks AND ku -> kv\n" +
            "NOT ie -> if\n" +
            "hf AND hl -> hn\n" +
            "1 AND r -> s\n" +
            "ib AND ic -> ie\n" +
            "hq AND hs -> ht\n" +
            "y AND ae -> ag\n" +
            "NOT ed -> ee\n" +
            "bi LSHIFT 15 -> bm\n" +
            "dy RSHIFT 2 -> dz\n" +
            "ci RSHIFT 2 -> cj\n" +
            "NOT bf -> bg\n" +
            "NOT im -> in\n" +
            "ev OR ew -> ex\n" +
            "ib OR ic -> id\n" +
            "bn RSHIFT 2 -> bo\n" +
            "dd RSHIFT 2 -> de\n" +
            "bl OR bm -> bn\n" +
            "as RSHIFT 1 -> bl\n" +
            "ea OR eb -> ec\n" +
            "ln AND lp -> lq\n" +
            "kk RSHIFT 3 -> km\n" +
            "is OR it -> iu\n" +
            "iu RSHIFT 2 -> iv\n" +
            "as OR bd -> be\n" +
            "ip LSHIFT 15 -> it\n" +
            "iw OR ix -> iy\n" +
            "kk RSHIFT 2 -> kl\n" +
            "NOT bb -> bc\n" +
            "ci RSHIFT 5 -> cl\n" +
            "ly OR lz -> ma\n" +
            "z AND aa -> ac\n" +
            "iu RSHIFT 1 -> jn\n" +
            "cy LSHIFT 15 -> dc\n" +
            "cf LSHIFT 1 -> cz\n" +
            "as RSHIFT 3 -> au\n" +
            "cz OR cy -> da\n" +
            "kw AND ky -> kz\n" +
            "lx -> a\n" +
            "iw AND ix -> iz\n" +
            "lr AND lt -> lu\n" +
            "jp RSHIFT 5 -> js\n" +
            "aw AND ay -> az\n" +
            "jc AND je -> jf\n" +
            "lb OR la -> lc\n" +
            "NOT cn -> co\n" +
            "kh LSHIFT 1 -> lb\n" +
            "1 AND jj -> jk\n" +
            "y OR ae -> af\n" +
            "ck AND cl -> cn\n" +
            "kk OR kv -> kw\n" +
            "NOT cv -> cw\n" +
            "kl AND kr -> kt\n" +
            "iu OR jf -> jg\n" +
            "at AND az -> bb\n" +
            "jp RSHIFT 2 -> jq\n" +
            "iv AND jb -> jd\n" +
            "jn OR jo -> jp\n" +
            "x OR ai -> aj\n" +
            "ba AND bc -> bd\n" +
            "jl OR jk -> jm\n" +
            "b RSHIFT 1 -> v\n" +
            "o AND q -> r\n" +
            "NOT p -> q\n" +
            "k AND m -> n\n" +
            "as RSHIFT 2 -> at";

    public static void main(final String[] args) {
        Pattern setValPattern = Pattern.compile("(\\d*) -> ([a-z]*)");
        Pattern setPattern = Pattern.compile("([a-z]*) -> ([a-z]*)");
        Pattern valueAndPattern = Pattern.compile("(\\d*) AND ([a-z]*) -> ([a-z]*)");
        Pattern andPattern = Pattern.compile("([a-z]*) AND ([a-z]*) -> ([a-z]*)");
        Pattern orPattern = Pattern.compile("([a-z]*) OR ([a-z]*) -> ([a-z]*)");
        Pattern leftShiftPattern = Pattern.compile("([a-z]*) LSHIFT (\\d*) -> ([a-z]*)");
        Pattern rightShiftPattern = Pattern.compile("([a-z]*) RSHIFT (\\d*) -> ([a-z]*)");
        Pattern notPattern = Pattern.compile("NOT ([a-z]*) -> ([a-z]*)");

        boolean part2 = false;
        Map<String, Integer> wires = new HashMap<>();
        if(part2) {
            wires.put("b", 956);
        }
        while (wires.get("a") == null) {
            new BufferedReader(new StringReader(INPUT)).lines() //
                    .forEach(line -> {
                        if (part2 && line.endsWith("-> b")) {
                            return;
                        }
                        Matcher matcher = setValPattern.matcher(line);
                        if (matcher.matches()) {
                            int a = Integer.valueOf(matcher.group(1));

                            wires.put(matcher.group(2), a);
                            return;
                        }
                        matcher = setPattern.matcher(line);
                        if (matcher.matches()) {
                            Integer a = wires.get(matcher.group(1));

                            if (a != null) {
                                wires.put(matcher.group(2), a);
                            }
                            return;
                        }
                        matcher = andPattern.matcher(line);
                        if (matcher.matches()) {
                            Integer a = wires.get(matcher.group(1));
                            Integer b = wires.get(matcher.group(2));

                            if (a != null && b != null) {
                                wires.put(matcher.group(3), a & b);
                            }
                            return;
                        }
                        matcher = valueAndPattern.matcher(line);
                        if (matcher.matches()) {
                            int a = Integer.valueOf(matcher.group(1));
                            Integer b = wires.get(matcher.group(2));

                            if (b != null) {
                                wires.put(matcher.group(3), a & b);
                            }
                            return;
                        }
                        matcher = orPattern.matcher(line);
                        if (matcher.matches()) {
                            Integer a = wires.get(matcher.group(1));
                            Integer b = wires.get(matcher.group(2));

                            if (a != null && b != null) {
                                wires.put(matcher.group(3), a | b);
                            }
                            return;
                        }
                        matcher = leftShiftPattern.matcher(line);
                        if (matcher.matches()) {
                            Integer a = wires.get(matcher.group(1));
                            int b = Integer.valueOf(matcher.group(2));

                            if (a != null) {
                                wires.put(matcher.group(3), a << b);
                            }
                            return;
                        }
                        matcher = rightShiftPattern.matcher(line);
                        if (matcher.matches()) {
                            Integer a = wires.get(matcher.group(1));
                            int b = Integer.valueOf(matcher.group(2));

                            if (a != null) {
                                wires.put(matcher.group(3), a >> b);
                            }
                            return;
                        }
                        matcher = notPattern.matcher(line);
                        if (matcher.matches()) {
                            Integer a = wires.get(matcher.group(1));

                            if (a != null) {
                                wires.put(matcher.group(2), (int) (char) ~(char) (int) a);
                            }
                            return;
                        }
                        throw new IllegalArgumentException(line);
                    });
        }
        System.out.println(wires.keySet());
        System.out.println(wires.get("a"));
    }

}
