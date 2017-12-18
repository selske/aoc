package aoc.seventeen;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day7 {

    private static class Node {
        private final String name;
        private int weight;
        private Node parent;
        private List<Node> children = new ArrayList<>();

        public Node(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setParent(final Node parent) {
            this.parent = parent;
        }

        public Node getParent() {
            return parent;
        }

        public void setWeight(final int weight) {
            this.weight = weight;
        }

        public int getWeight() {
            return weight;
        }

        public int getTotalWeight() {
            return (int) (weight + children.stream().map(Node::getTotalWeight).collect(Collectors.summarizingInt(i -> i)).getSum());
        }

        public void setChildren(final List<Node> children) {
            this.children = children;
        }

        public List<Node> getChildren() {
            return children;
        }

        @Override
        public String toString() {
            return name + " : " + getTotalWeight();
        }
    }

    // private static final String INPUT = "pbga (66)\n" +
    // "xhth (57)\n" +
    // "ebii (61)\n" +
    // "havc (66)\n" +
    // "ktlj (57)\n" +
    // "fwft (72) -> ktlj, cntj, xhth\n" +
    // "qoyq (66)\n" +
    // "padx (45) -> pbga, havc, qoyq\n" +
    // "tknk (41) -> ugml, padx, fwft\n" +
    // "jptl (61)\n" +
    // "ugml (68) -> gyxo, ebii, jptl\n" +
    // "gyxo (61)\n" +
    // "cntj (57)";

    private static final String INPUT = "rmhcw (26)\n" + //
            "dmmriu (61)\n" + //
            "zjyaaoc (89) -> khekb, lywwhf\n" + //
            "kjgaib (385)\n" + //
            "nvvdii (28)\n" + //
            "zsgegr (15)\n" + //
            "azrafw (72)\n" + //
            "amzxtvd (92)\n" + //
            "zlkpdaw (77)\n" + //
            "pgfqtb (552) -> uynvl, ogcra, detyx\n" + //
            "uoztb (75)\n" + //
            "dktlvzc (97) -> jrryajw, uwctdam\n" + //
            "jtsahle (37)\n" + //
            "tuphfrq (87)\n" + //
            "pimll (344) -> ajhnj, mtmpaji\n" + //
            "otnkggc (84)\n" + //
            "njlmm (6)\n" + //
            "cktiwle (20)\n" + //
            "cdthf (17)\n" + //
            "rmmgmm (17)\n" + //
            "vthnptc (132) -> ivaasv, bpehcx\n" + //
            "dujrdhp (51)\n" + //
            "dtubefc (50)\n" + //
            "fdkyuhv (74) -> gxpqjp, tvnoctm\n" + //
            "qplfqti (1201) -> moyxzbt, icdth, mynrc, sbrhj, ocynqs\n" + //
            "rtckm (24)\n" + //
            "vszkz (1030) -> sitfw, mlhli, atyrf, nrbvw\n" + //
            "fxyfmtw (913) -> vptoff, etzcek, erieyzj\n" + //
            "pjqxvcx (24)\n" + //
            "foyrjn (903) -> vhvdkt, pvmhjh, pimll\n" + //
            "brofu (12)\n" + //
            "wrucr (110) -> kcxol, vflryv, dgvxrd\n" + //
            "lmcvdg (96)\n" + //
            "tykfhh (18)\n" + //
            "lgmsbu (32)\n" + //
            "qwqbgko (1661) -> ltdmtot, czgwxvf, dktlvzc\n" + //
            "ocfqtr (54)\n" + //
            "kvfpfwn (84)\n" + //
            "lpuzdj (51)\n" + //
            "aotgdc (67)\n" + //
            "dciwsw (15)\n" + //
            "lwyej (84)\n" + //
            "bwxgva (28)\n" + //
            "oraaeb (9)\n" + //
            "zdgdoe (66)\n" + //
            "boolrdh (96)\n" + //
            "ryixkn (40) -> kvfpfwn, jrzeg\n" + //
            "rgyulf (99)\n" + //
            "oztfyt (77) -> gijzmpj, moukxw\n" + //
            "ujhhibr (82)\n" + //
            "zwzki (57)\n" + //
            "pqmyjtl (166) -> vipoi, iylzwio\n" + //
            "szifo (77) -> pkmgtcu, rgyulf\n" + //
            "bcawzmn (2980) -> kdnex, dualr, zacca, kodtvsj, lkvju, twumak, vlhsdy\n" + //
            "agifmzc (5)\n" + //
            "fyazgqg (76)\n" + //
            "riqok (295)\n" + //
            "chhjhsu (231) -> ejootzk, gpzdwzc\n" + //
            "hmlkdv (157)\n" + //
            "esvdf (128) -> wmrxba, dfhbypd, egcjh\n" + //
            "skusk (73)\n" + //
            "mcbkdjm (48) -> uojvtmd, kthqsm, klkqzgk, yauwsp, zjgifte\n" + //
            "mptfgnr (67)\n" + //
            "toejq (265)\n" + //
            "scgycex (64)\n" + //
            "ortnis (89)\n" + //
            "kmmejxq (19)\n" + //
            "impwda (30)\n" + //
            "cijljt (85)\n" + //
            "fhbphvw (38)\n" + //
            "kwqnczk (73)\n" + //
            "xyfqbw (136)\n" + //
            "criyk (23)\n" + //
            "gzywgu (69)\n" + //
            "ghgsrqm (122) -> jajds, mkhupy\n" + //
            "gqdyvi (40) -> nepucl, dgczfau, kxudlu, bfuogmf, udoins, qplfqti\n" + //
            "mxnuvaf (86)\n" + //
            "plsxwh (79)\n" + //
            "hrckiut (93)\n" + //
            "uneul (84)\n" + //
            "qjgvzt (44)\n" + //
            "lfhkna (30)\n" + //
            "xchhko (59)\n" + //
            "xryvbkx (9) -> gsgsoo, uixpy, tsjropj, wunvxyy, fxmegac, xabac, twfzsig\n" + //
            "oeecpoh (136) -> rmnxqwr, iqpcyq, xtnbcdl\n" + //
            "ggtdtk (51) -> dnflw, cwagf, cyflr\n" + //
            "jubznb (74)\n" + //
            "yubyr (61)\n" + //
            "xnpbag (122) -> pgpszp, oykdl, nuytwqt\n" + //
            "wmmgpoi (42)\n" + //
            "elulm (17)\n" + //
            "dvgaf (77)\n" + //
            "pdftmlf (90)\n" + //
            "cbtbxyj (67)\n" + //
            "tsttu (50)\n" + //
            "biupfx (63)\n" + //
            "wwodj (68)\n" + //
            "ttsik (98) -> lodegh, udjcgxd, oluejv\n" + //
            "dmfzrwz (78)\n" + //
            "cohmgk (38)\n" + //
            "ujpxjvo (23)\n" + //
            "voqoq (248) -> gxlnyfk, bslrn, yigwdrq\n" + //
            "lxcuf (76) -> bkhouo, cijljt, hdyfay\n" + //
            "jzytjy (14) -> wsgicvu, xlcvwa, dfrdsx\n" + //
            "jktzsu (1800) -> rlmvaz, rbqziy\n" + //
            "budlmc (77)\n" + //
            "lvzbscf (24)\n" + //
            "vaytig (81)\n" + //
            "xifnep (44)\n" + //
            "mzwuqf (23)\n" + //
            "buurids (67)\n" + //
            "yjqyzdx (32)\n" + //
            "xwqwjpe (50)\n" + //
            "mgitd (18)\n" + //
            "ocoebv (130) -> xbrnkt, pyamx\n" + //
            "txejs (229)\n" + //
            "hnafo (312) -> qajub, hijor, cpgqlov, glvea, ioyqxi, yqobec\n" + //
            "olywf (56)\n" + //
            "hfysw (27)\n" + //
            "dxuzg (95)\n" + //
            "qnfptsg (12)\n" + //
            "teudtu (60)\n" + //
            "miheo (87) -> tgdxj, lgbdoew\n" + //
            "mweofft (112) -> xjwsq, nfuhle, ejchirl\n" + //
            "ffwqnu (19)\n" + //
            "klkqzgk (230) -> rzvmuhc, gfdqw\n" + //
            "jqfnlo (62)\n" + //
            "yddig (106) -> zwzki, onasr, acpvgye\n" + //
            "gijzmpj (27)\n" + //
            "glvpo (76)\n" + //
            "eiqkt (99)\n" + //
            "ivaasv (89)\n" + //
            "mkbkf (50)\n" + //
            "coozmv (56)\n" + //
            "sxtosdg (80)\n" + //
            "pbfyx (72)\n" + //
            "xdvfq (221) -> wyepbsc, shvdgjj\n" + //
            "ykvbw (1128) -> klplfg, tgtuj, qmuwov\n" + //
            "tusmyhc (116) -> mxfhzxz, agajilj\n" + //
            "cumah (1069) -> pqmyjtl, wczux, rfjilj\n" + //
            "tdtgvd (89) -> zykce, cznez, cxdfcfo\n" + //
            "lubxj (58)\n" + //
            "atyrf (171) -> nvvdii, qvsyjp\n" + //
            "dixfg (51)\n" + //
            "bsaqdlw (115) -> bwxgva, ijpkw, qhzijcy, isixh\n" + //
            "vxmcz (114) -> ltpdyh, tlgrujs\n" + //
            "eezhvk (65) -> kakya, ublhtvx, uetik, lxvsklc\n" + //
            "zoswcn (81)\n" + //
            "uyrmu (63)\n" + //
            "ngnyte (46)\n" + //
            "jgihy (79)\n" + //
            "flutk (50)\n" + //
            "gcepy (49)\n" + //
            "zobxdhc (51)\n" + //
            "ttigpkh (66)\n" + //
            "pjockdt (48) -> gjadc, wctijwc, mejujn\n" + //
            "esfkj (80)\n" + //
            "exiatbf (9)\n" + //
            "thxdl (85)\n" + //
            "vmyeuh (39) -> ygtusut, vxywld, mzwuqf, esyoils\n" + //
            "axftz (227) -> olufpv, hpbcytx\n" + //
            "fodble (30)\n" + //
            "geqhfh (98) -> ffwqnu, lwlhv\n" + //
            "twryo (17)\n" + //
            "almlu (65) -> vurym, ryhdf, ymmix\n" + //
            "fbqnyl (40) -> mukgev, aytmotj, vxmcz, zegmhba, cjoktd\n" + //
            "hzdjy (61)\n" + //
            "smatb (212)\n" + //
            "ajzejub (118) -> fkfhz, ccjay\n" + //
            "rednz (44)\n" + //
            "nhqcyo (59)\n" + //
            "xjqjmx (3053) -> okurj, rdzusni, zmtmgdb\n" + //
            "cgxxf (10)\n" + //
            "cznez (67)\n" + //
            "eugdc (117) -> wqwwip, byffz, virxd, scgycex\n" + //
            "ambyx (20)\n" + //
            "sqlwsz (78)\n" + //
            "esgkmgn (100) -> thxdl, fhlom\n" + //
            "qyree (60)\n" + //
            "nuhuny (91)\n" + //
            "djoubm (15) -> lubxj, lhaxjf\n" + //
            "yrgajm (24)\n" + //
            "vrfrgqq (95)\n" + //
            "sullc (84) -> urmwxm, dcqtq\n" + //
            "yiwrl (13)\n" + //
            "qktqn (241) -> evprik, eegxx\n" + //
            "uqxdea (99) -> ndqve, cvgwkop\n" + //
            "raxhxb (144) -> dpqqukb, uvyqu, bxtyq\n" + //
            "jepwvl (47)\n" + //
            "uqtqvs (99)\n" + //
            "ieekq (77)\n" + //
            "ffulcj (73)\n" + //
            "wygaoit (119) -> uijhb, opcrls\n" + //
            "intsv (84)\n" + //
            "ymmix (43)\n" + //
            "oemmjed (56)\n" + //
            "wnpzam (216) -> ldeelvr, dfaui\n" + //
            "mwjtujw (16)\n" + //
            "tvxvkfa (6) -> bhwoeq, jqfnlo, frejy, ylzefq\n" + //
            "utdyk (163) -> exjlwux, agkvm\n" + //
            "zhcka (89)\n" + //
            "qylhq (196) -> qqpefh, fxxlahy, yxqaoje\n" + //
            "acckfcm (171) -> thxkub, jmogygm\n" + //
            "rgjrr (14)\n" + //
            "prjpe (11)\n" + //
            "cxdfcfo (67)\n" + //
            "gpgeg (58) -> hxwcaqc, ieiyqb, etnfk\n" + //
            "jzsvhf (51)\n" + //
            "vhdea (82)\n" + //
            "oeyvbro (84)\n" + //
            "jwyixhi (15)\n" + //
            "jpposiw (665) -> bjgyr, djoubm, oztfyt, tsfmg, vmyeuh\n" + //
            "udcru (24)\n" + //
            "mvihmy (241) -> jpxcpds, qjpmeb, amwgqjw\n" + //
            "kutwy (51)\n" + //
            "thizds (41)\n" + //
            "aavcuhq (22)\n" + //
            "izyzvs (139) -> wmmgpoi, bmucb\n" + //
            "lksjvrl (84)\n" + //
            "qztwco (53)\n" + //
            "dcmwnt (222) -> zdjtgl, udcru\n" + //
            "kvrvt (10)\n" + //
            "frejy (62)\n" + //
            "bpehcx (89)\n" + //
            "uwctdam (48)\n" + //
            "afhbfu (87) -> mlbwwv, uujpswh\n" + //
            "hqhnhs (85)\n" + //
            "nlonk (77)\n" + //
            "wufwm (253)\n" + //
            "bdowv (54)\n" + //
            "aytmotj (92) -> ocleuky, mktrq, vheuyos, apvdjw\n" + //
            "vxfsl (89)\n" + //
            "gjfjuk (65)\n" + //
            "dgoxkjd (166) -> zgmryzq, gusfsh\n" + //
            "atvqfv (84)\n" + //
            "lsfboo (75)\n" + //
            "daxpn (45)\n" + //
            "irqwcab (8461) -> hxsausc, qtmdrsq, xfqfzsh\n" + //
            "uixpy (92) -> cpgiuqo, biupfx, rlqjnc\n" + //
            "qhbrq (166) -> oemmjed, ifixi\n" + //
            "ewgqv (211) -> cwydepp, bdowv\n" + //
            "tckqlsw (54)\n" + //
            "bxtyq (35)\n" + //
            "jtnvh (43)\n" + //
            "aywmn (97) -> oqfkzw, epgmufe\n" + //
            "gyvcry (80)\n" + //
            "upkdq (16)\n" + //
            "gjadc (67)\n" + //
            "fxmegac (177) -> nqvpxt, qwgal\n" + //
            "tklmb (40)\n" + //
            "saskhnf (44)\n" + //
            "pkdky (51) -> iezqyyh, rtefbvp, pquivn\n" + //
            "yqobec (47) -> axvuwo, cunshl, bfwwmam, riqok, wohqjaq, ufthnyr\n" + //
            "twmxk (25)\n" + //
            "jmjybfb (125) -> wbjbp, phaab\n" + //
            "mukks (5)\n" + //
            "xrdny (45)\n" + //
            "toqcntj (137) -> yckzzdv, qbmsizh\n" + //
            "vpcnw (36)\n" + //
            "jnsfz (260) -> obfhgbl, igwsote, krfbes\n" + //
            "ikqwjn (4725) -> pgfqtb, nzhby, azdkxw, nynvxxt\n" + //
            "lgnltgy (88)\n" + //
            "vnseppy (89)\n" + //
            "vldkam (203) -> ejgmgu, uyrmu\n" + //
            "flayoln (27)\n" + //
            "rmdxq (90)\n" + //
            "zfwxd (318)\n" + //
            "wnndu (45)\n" + //
            "detyx (76) -> uujwdtb, cvdatwa\n" + //
            "ilgssfa (164)\n" + //
            "jcvlaqi (326) -> fyrumv, zacqgsf\n" + //
            "vcdoz (32)\n" + //
            "msntfpz (15)\n" + //
            "hwlwfs (6471) -> ueszsu, mroag, trvnbpq\n" + //
            "vxdpzw (46)\n" + //
            "laehzou (84)\n" + //
            "aafaeyd (34)\n" + //
            "yigwdrq (9)\n" + //
            "yuutd (47)\n" + //
            "gbeooc (133) -> budhvyf, kpnwcay\n" + //
            "pquivn (45)\n" + //
            "twfsmo (39)\n" + //
            "fdjrm (39)\n" + //
            "mvjzwg (233) -> cktiwle, duwsfyh\n" + //
            "fyjqt (212) -> kmihua, qromdd\n" + //
            "vhechuz (14)\n" + //
            "zmtmgdb (1292) -> taadcy, fyjqt, akzavdu\n" + //
            "huongnb (223) -> hwyjtzo, olryvzf\n" + //
            "jdwezf (12)\n" + //
            "zqfvypo (1810) -> juxyuv, ednozov\n" + //
            "fahfz (1009) -> psgqug, lxcuf, mvihmy\n" + //
            "uetik (47)\n" + //
            "quvar (40)\n" + //
            "aqlnsb (17) -> dhkdkp, rhmgk\n" + //
            "wkheu (88)\n" + //
            "jeysfy (74)\n" + //
            "bzoeb (75)\n" + //
            "pvmhjh (266) -> wuwksnc, hwgrnki\n" + //
            "rhvrmfv (57)\n" + //
            "xknwy (63)\n" + //
            "tjepyd (167) -> hpoyfr, zgyuoh, cpzhmuv\n" + //
            "oxngag (39)\n" + //
            "pvoch (54)\n" + //
            "hqpikjx (241) -> gcvsbqg, gxmyaep, zyvvmko\n" + //
            "wyfod (269) -> znlwikg, nvnqv\n" + //
            "emdcjcc (71)\n" + //
            "zhunqhj (78)\n" + //
            "ioyqxi (829) -> xumalc, jyuww, rytowol, uodkl\n" + //
            "plmgm (124) -> civtm, twfsmo\n" + //
            "pqmiyd (52)\n" + //
            "wquesh (77)\n" + //
            "jwani (92)\n" + //
            "bslrn (9)\n" + //
            "fyrumv (38)\n" + //
            "zzjjmdw (59)\n" + //
            "zacqgsf (38)\n" + //
            "uduyfo (1710) -> amhaz, cumah, zqfvypo, dribos\n" + //
            "vhvdkt (12) -> utwhpg, gsivsr, hpxkzm, dtfsxdh\n" + //
            "wchboy (227) -> pqkudqa, blofhut\n" + //
            "oacpzvm (37)\n" + //
            "rdzusni (7) -> ewgqv, bfmemro, mweofft, mmbhu, qylhq, jeqojht, gpgeg\n" + //
            "uudmxem (38)\n" + //
            "emcmkkw (45)\n" + //
            "kdnex (137) -> zmmov, rdktr, tdtgvd\n" + //
            "uksfrmb (85) -> emcmkkw, ijlmsfb, pdrnuox, wnndu\n" + //
            "umpivl (159) -> mgitd, iqanf\n" + //
            "mikmp (50)\n" + //
            "klukw (71) -> pggnlt, skusk, xqewue\n" + //
            "jvamqz (87) -> mblszml, scxgu\n" + //
            "zgmryzq (21)\n" + //
            "wymhyko (17)\n" + //
            "nifolyc (56)\n" + //
            "zeiubr (138) -> ncyhsb, srubp\n" + //
            "uzvsyt (6253) -> fsetxvb, mlufac, crpom, xarbaxi\n" + //
            "smqdbd (280) -> kfuvm, xcjfiq\n" + //
            "tttent (91)\n" + //
            "nomlds (26) -> intsv, laehzou\n" + //
            "sfegieu (92)\n" + //
            "bfnnpkl (68)\n" + //
            "wvfty (23)\n" + //
            "oykdl (79)\n" + //
            "xmqfua (18)\n" + //
            "uvyqu (35)\n" + //
            "cvhuwne (273)\n" + //
            "rtmloqi (17)\n" + //
            "xoexmt (23)\n" + //
            "olegqeb (27)\n" + //
            "dylatux (1508) -> zbkxzh, rpuefig\n" + //
            "qtmdrsq (23) -> atvqfv, rjojhya, xzvbd\n" + //
            "lhaxjf (58)\n" + //
            "lfyyq (1044) -> oeecpoh, gmfsqw, fshbxj\n" + //
            "tqlnvvp (59)\n" + //
            "jhiejzb (74)\n" + //
            "pkmgtcu (99)\n" + //
            "iygaq (166) -> msntfpz, falzunq, jwyixhi, xyfhe\n" + //
            "mtmjdk (87)\n" + //
            "rdktr (256) -> xaxmer, srgev\n" + //
            "cwlzph (98) -> lwqrj, somurr\n" + //
            "hdvyqml (64)\n" + //
            "oluejv (87)\n" + //
            "qtuxuwe (26)\n" + //
            "ltdmtot (143) -> xenad, lxmob\n" + //
            "hwgrnki (49)\n" + //
            "rbxgy (77)\n" + //
            "nmonk (27)\n" + //
            "kodtvsj (137) -> zeiubr, klukw, ddayyiv\n" + //
            "wbjbp (75)\n" + //
            "gsilyv (26)\n" + //
            "cwadvj (95)\n" + //
            "qhzijcy (28)\n" + //
            "tqslgy (176) -> tnluhpd, irbpync\n" + //
            "zjgifte (194) -> sefsszz, agrkalx\n" + //
            "nazpcr (1111) -> feoqwhl, mwvluyo, fkwkr, cqusdqo, iszhf\n" + //
            "sxzfd (161) -> offde, bikwar\n" + //
            "nfmsd (134) -> tqlnvvp, tghuhpn\n" + //
            "yauwsp (344) -> twryo, rmmgmm\n" + //
            "eywyawe (11)\n" + //
            "bikwar (43)\n" + //
            "cnyrs (74)\n" + //
            "zvynzb (51)\n" + //
            "fxxlahy (41)\n" + //
            "oqfkzw (90)\n" + //
            "giuua (124) -> vdongnn, vgkkp\n" + //
            "lctnj (54)\n" + //
            "rlmvaz (69)\n" + //
            "deqakr (225) -> ryxia, uycpqa\n" + //
            "gxhnsq (71)\n" + //
            "jmskfql (18)\n" + //
            "vnlet (54)\n" + //
            "lwubj (100) -> ttsxvot, oacpzvm\n" + //
            "wpanqd (35)\n" + //
            "jmogygm (39)\n" + //
            "pmsdrrh (46)\n" + //
            "fgrhkdd (28)\n" + //
            "rlqjnc (63)\n" + //
            "nxszwh (48)\n" + //
            "qyldt (154) -> jdwezf, lfqglc\n" + //
            "edtzpv (28) -> hocfnxj, ugdcq, rluqka\n" + //
            "fhptqw (31)\n" + //
            "okurj (1350) -> reeaojr, vlsons, qyldt, ocoebv, oqwgc\n" + //
            "utcwj (249)\n" + //
            "xbekvhl (16)\n" + //
            "ecvpo (238) -> hexev, yaxzkrb\n" + //
            "jwymbhh (5)\n" + //
            "abmcv (54)\n" + //
            "smcmduw (19)\n" + //
            "bhond (63)\n" + //
            "wmxih (50)\n" + //
            "exqni (56)\n" + //
            "fhdcvd (54)\n" + //
            "gqjvwpx (10)\n" + //
            "oawmfyx (77)\n" + //
            "moukxw (27)\n" + //
            "amwgqjw (30)\n" + //
            "xutehnq (361) -> wnpzam, dsstz, tvxvkfa\n" + //
            "pyamx (24)\n" + //
            "dvehwh (11)\n" + //
            "kgprdd (96) -> qtbws, iafal, yjqyzdx\n" + //
            "sgwhkc (231) -> ckzpuzs, xbkccy\n" + //
            "plawaw (14)\n" + //
            "hzzbmus (48)\n" + //
            "wlubh (125) -> sdpmpe, vcdoz\n" + //
            "fkwkr (11) -> oxudyzv, xcrfey, tckqlsw\n" + //
            "fjkhu (273) -> rvvuep, mvgcb\n" + //
            "tsjropj (203) -> gzfuad, wrinrd\n" + //
            "pggnlt (73)\n" + //
            "lswbgy (117) -> dzvqmjz, bfnnpkl\n" + //
            "hjvegbe (227) -> uqqjmps, xifnep, rednz\n" + //
            "uynvl (30) -> bxiiba, rhvrmfv, hwzfzf, avnxvad\n" + //
            "hnsyjq (49) -> uqtqvs, ecaqtyw\n" + //
            "uzjxnwf (95)\n" + //
            "ampem (74)\n" + //
            "utwhpg (88)\n" + //
            "lcbmlo (1650) -> kvrvt, gqjvwpx\n" + //
            "fzycgs (38)\n" + //
            "tnpjqk (44)\n" + //
            "ajhnj (10)\n" + //
            "hvaybmr (80)\n" + //
            "xfhxb (34)\n" + //
            "kcnabhn (259) -> soocm, cefpt\n" + //
            "bvcygkz (88)\n" + //
            "ejgmgu (63)\n" + //
            "ajkaxbk (50) -> zoswcn, heilv\n" + //
            "djwoy (213) -> conuwmt, iijuiln\n" + //
            "cwsycss (51)\n" + //
            "bawsdp (67)\n" + //
            "krfbes (6)\n" + //
            "budhvyf (98)\n" + //
            "hpyfzew (29)\n" + //
            "hexamha (30)\n" + //
            "wqwwip (64)\n" + //
            "qnzej (51)\n" + //
            "ipauj (47) -> wkheu, bvcygkz\n" + //
            "ekdzx (91)\n" + //
            "ybbplm (6)\n" + //
            "uizst (8)\n" + //
            "nuynvy (57)\n" + //
            "ickzym (14)\n" + //
            "agkvm (43)\n" + //
            "jrryajw (48)\n" + //
            "qxrydcs (119) -> lvcpzy, wmzmuk, xqjqedz, uhrkq\n" + //
            "rtqef (94)\n" + //
            "yxqaoje (41)\n" + //
            "dokkjf (132) -> efhdibc, afajf\n" + //
            "evprik (17)\n" + //
            "caufhb (82)\n" + //
            "fhlom (85)\n" + //
            "secmp (99)\n" + //
            "aodxwem (74)\n" + //
            "dhkdkp (92)\n" + //
            "hdyfay (85)\n" + //
            "ikcofec (11)\n" + //
            "cyflr (89)\n" + //
            "cyahch (941) -> rwiet, vulgw, qxrydcs, gique, ynirob\n" + //
            "txdej (66)\n" + //
            "yaxzkrb (7)\n" + //
            "rtefbvp (45)\n" + //
            "wljtjp (6)\n" + //
            "sdxbol (2587) -> qwsjrs, dnqva, ddssmn\n" + //
            "yckzzdv (37)\n" + //
            "ncyhsb (76)\n" + //
            "sxoxuhy (275)\n" + //
            "whiaid (74)\n" + //
            "hwzfzf (57)\n" + //
            "olauul (75)\n" + //
            "heilv (81)\n" + //
            "deujsoj (202) -> dciwsw, hvjusf, oyiem\n" + //
            "wyepbsc (40)\n" + //
            "wksjc (19)\n" + //
            "ejchirl (69)\n" + //
            "tlgrujs (71)\n" + //
            "ljpndb (195) -> nmonk, flayoln\n" + //
            "vatjopi (17)\n" + //
            "nyhlmwv (38) -> cjjge, gyvcry, hvaybmr, sxtosdg\n" + //
            "ndqve (29)\n" + //
            "zyvvmko (12)\n" + //
            "dxfln (52) -> pihhj, xelex, csypcco\n" + //
            "agajilj (87)\n" + //
            "iqanf (18)\n" + //
            "qjwjgap (305) -> bhond, xknwy\n" + //
            "yqhvof (46) -> hfysw, mwsdpe, xugclel, drrlizg\n" + //
            "pzzerxc (96) -> cqhwrn, lxbsb\n" + //
            "ttsxvot (37)\n" + //
            "jahdhw (64)\n" + //
            "zdjtgl (24)\n" + //
            "fmrbyn (230) -> bpqusy, jkcpz\n" + //
            "btoafg (276) -> almjyt, ajzejub, ydlek, yopixg, rmtjyq, lwubj\n" + //
            "cpgqlov (1061) -> xlqya, dtvxbn, nmwst, jfpmnr\n" + //
            "ypatkqb (40) -> qqyqj, mxnuvaf\n" + //
            "hijor (179) -> jzwcj, mjsib, mvjzwg, wcfib, tdfupn, dkxszcn\n" + //
            "shvdgjj (40)\n" + //
            "cjdmv (75) -> vzqaftq, fyazgqg\n" + //
            "gqdbu (77)\n" + //
            "gcjjvj (359)\n" + //
            "gzgqh (10)\n" + //
            "xelex (84) -> ekdzx, nuhuny, blscadu\n" + //
            "xgudb (26966) -> hnafo, fouvfkn, pjfty, qkvogv, hwlwfs, lazhif\n" + //
            "mtdnxdv (51)\n" + //
            "hxnxfk (9973) -> xwdxtws, htwlkzk, voafbv\n" + //
            "ryxia (38)\n" + //
            "hlidsi (91)\n" + //
            "gftoj (79)\n" + //
            "yupwby (12)\n" + //
            "zcyzaxo (28) -> dkshm, achnat, aperzqo\n" + //
            "tsaqdup (19)\n" + //
            "yegoug (154)\n" + //
            "mlhli (19) -> nnjokq, iqgap, uclrhqy, zcuix\n" + //
            "fprvu (77) -> dtubefc, tsttu\n" + //
            "etnfk (87)\n" + //
            "klplfg (131) -> plsxwh, gftoj\n" + //
            "vtuzdmb (106) -> quvar, tklmb\n" + //
            "mblszml (71)\n" + //
            "tbltuom (96)\n" + //
            "jfpmnr (165) -> ybbplm, njlmm, wljtjp, mekaep\n" + //
            "skxmk (14)\n" + //
            "xtnbcdl (54)\n" + //
            "iqgap (52)\n" + //
            "bajivws (84) -> rmhcw, uautcpo\n" + //
            "avnxvad (57)\n" + //
            "npnkt (60)\n" + //
            "bqkjt (184) -> yrgajm, lvzbscf\n" + //
            "gndfat (9) -> sldqhxe, bzdbxi, rmczumv, jeysfy\n" + //
            "bfuogmf (1244) -> otivo, ytjnkx, uyklfxt, cpdtit\n" + //
            "mxfhzxz (87)\n" + //
            "zlcappl (201) -> xsefm, xbekvhl, upkdq\n" + //
            "olxlgo (87)\n" + //
            "duron (41)\n" + //
            "pssbgm (52) -> emybof, hqhnhs\n" + //
            "kthqsm (186) -> tbltuom, tjcnb\n" + //
            "jknws (47) -> xemhgy, tazjcwi\n" + //
            "sairne (45)\n" + //
            "bfznf (2048) -> jsnlpme, wygaoit, elpequ, fprvu\n" + //
            "pccpzvp (54)\n" + //
            "oieobzw (234) -> frlpmld, tmyne, agifmzc\n" + //
            "iylzwio (56)\n" + //
            "zykce (67)\n" + //
            "ejyyd (99)\n" + //
            "rphyyst (4749) -> btoafg, fbqnyl, ntdeo, jpposiw\n" + //
            "feoqwhl (85) -> wqjbjg, qjgvzt\n" + //
            "rhmgk (92)\n" + //
            "fjxjp (76)\n" + //
            "hdhjbvn (84)\n" + //
            "kjvnox (14) -> cnyrs, whiaid, nmcrmt, jubznb\n" + //
            "uhbty (67) -> apangy, sktjkgs\n" + //
            "rgiro (266) -> htlvlk, cklybnh\n" + //
            "jzwcj (137) -> wwodj, yarqa\n" + //
            "vjvbn (79)\n" + //
            "allxyqf (40)\n" + //
            "rjojhya (84)\n" + //
            "pqalwfs (234) -> wsfdatj, gzywgu\n" + //
            "zulymxl (66)\n" + //
            "kgnxc (142) -> frayh, vaytig, xnvqjpw\n" + //
            "vzqaftq (76)\n" + //
            "wkatvkd (99) -> fmdfzpk, ndextdo\n" + //
            "ywtmxkk (43)\n" + //
            "nuautun (78)\n" + //
            "mkhupy (81)\n" + //
            "pyjlxpa (22)\n" + //
            "hyiye (16)\n" + //
            "bhwoeq (62)\n" + //
            "hlddv (150) -> zlqtv, ickzym, plawaw\n" + //
            "qednvhh (96) -> tttent, znaap\n" + //
            "ecfls (59)\n" + //
            "xakcd (94)\n" + //
            "eddwubo (53)\n" + //
            "zlkhsa (129) -> djwhzz, jnsfz, qhbrq, wfysif, qednvhh\n" + //
            "acfxsgz (97)\n" + //
            "tjhtlqt (81)\n" + //
            "jkcpz (64)\n" + //
            "xabac (127) -> bwpyz, wquesh\n" + //
            "isixh (28)\n" + //
            "vipoi (56)\n" + //
            "ckzpuzs (23)\n" + //
            "wjlprje (71)\n" + //
            "kmgdk (63)\n" + //
            "bpqusy (64)\n" + //
            "ubcenyy (93)\n" + //
            "mdhzx (735) -> sullc, ypymf, crfaeqg, nfmsd, ecvpo\n" + //
            "omicyg (9)\n" + //
            "wjwnd (146) -> ujpxjvo, xoexmt\n" + //
            "kpnwcay (98)\n" + //
            "aoxfg (83)\n" + //
            "qldtb (61) -> hihdfp, lsfboo\n" + //
            "ezyzl (61)\n" + //
            "eegxx (17)\n" + //
            "lgbdoew (51)\n" + //
            "rbqziy (69)\n" + //
            "dgczfau (1196) -> kpzfnz, deowe, dgoxkjd, rsisz, ryixkn\n" + //
            "xugclel (27)\n" + //
            "wctijwc (67)\n" + //
            "sphft (53)\n" + //
            "nrbvw (131) -> hzzbmus, yeqdida\n" + //
            "fojja (97) -> jzsvhf, wuypkq, dujrdhp\n" + //
            "mynrc (69) -> rnvhups, vxdpzw, pmsdrrh\n" + //
            "oikboi (66)\n" + //
            "dwddkt (189)\n" + //
            "bbmrv (101) -> rmhroyf, oomcfz\n" + //
            "zbkxzh (81)\n" + //
            "rnvhups (46)\n" + //
            "pjfty (3310) -> bqygu, xryvbkx, nazpcr, cjntq\n" + //
            "isrdlt (51)\n" + //
            "rgjigx (28)\n" + //
            "pkmojw (77)\n" + //
            "vurym (43)\n" + //
            "civtm (39)\n" + //
            "iqpcyq (54)\n" + //
            "cefpt (7)\n" + //
            "scglak (20) -> lrrtkfx, qztwco, sphft, qolhl\n" + //
            "qkhciro (325) -> tzvshk, ywzaw\n" + //
            "qyjllh (20)\n" + //
            "pdsuhsj (90)\n" + //
            "xyfhe (15)\n" + //
            "esquqjv (330) -> jcvlaqi, zfljxqi, fgsiued, wyaatnb\n" + //
            "czfbso (28)\n" + //
            "ydlek (24) -> bzoeb, gckpcy\n" + //
            "wzdcg (61)\n" + //
            "uwbonvb (31)\n" + //
            "udjcgxd (87)\n" + //
            "bdrcuw (39) -> jahdhw, hdvyqml, idmaspp\n" + //
            "vvqnfr (148)\n" + //
            "mcarhq (62) -> ygkjkzl, pvswgen\n" + //
            "cqdcsas (40)\n" + //
            "egcjh (47)\n" + //
            "lzeeb (517) -> fckqt, plmgm, qljngh\n" + //
            "yhfcugm (81)\n" + //
            "tmyne (5)\n" + //
            "mwsdpe (27)\n" + //
            "mlbwwv (35)\n" + //
            "sldqhxe (74)\n" + //
            "accmwfa (81) -> ezxcaqx, jmskfql\n" + //
            "vjlxr (46) -> dxhqs, exqni, coozmv, ooevokb\n" + //
            "cpgiuqo (63)\n" + //
            "hkzfl (23)\n" + //
            "zmmov (290)\n" + //
            "cwydepp (54)\n" + //
            "xujppza (41)\n" + //
            "gzfuad (39)\n" + //
            "htwlkzk (846) -> uzrqz, xwagm, urbknyn\n" + //
            "xbkccy (23)\n" + //
            "crpom (713) -> uqhati, zlkpdaw, owbglst\n" + //
            "esyoils (23)\n" + //
            "bhfbkik (48)\n" + //
            "qmuwov (289)\n" + //
            "tclwlv (12)\n" + //
            "wunvxyy (161) -> kqkdd, jhihznq\n" + //
            "fmpkn (59)\n" + //
            "pvswgen (37)\n" + //
            "bvcrfun (78)\n" + //
            "nhfqb (61) -> mwxuez, kgnxc, yegxpjz, ddfvxuh, wnplhq, kjgaib, bzlgvwt\n" + //
            "lwgeanp (29) -> teudtu, qyree, cvfyti, npnkt\n" + //
            "gusfsh (21)\n" + //
            "iqlxjrp (79)\n" + //
            "bkckl (215) -> daxpn, xrdny\n" + //
            "gfdqw (74)\n" + //
            "etrstos (24) -> mtmjdk, tuphfrq, olxlgo, yhvvk\n" + //
            "pqdnn (87)\n" + //
            "hexev (7)\n" + //
            "tdfupn (141) -> ttigpkh, zdgdoe\n" + //
            "emybof (85)\n" + //
            "zfljxqi (370) -> jypod, hyiye\n" + //
            "mtpmeyy (1731) -> vgqdk, kvlbx, zulymxl, txdej\n" + //
            "lsecoh (74)\n" + //
            "tgtuj (53) -> ecfls, zzjjmdw, lmpfqe, xchhko\n" + //
            "pihhj (205) -> tsywh, fzycgs, cohmgk, sqdnri\n" + //
            "ijlmsfb (45)\n" + //
            "dnflw (89)\n" + //
            "hocfnxj (94)\n" + //
            "dgyrl (154)\n" + //
            "evayy (36)\n" + //
            "cwzzlp (133) -> rzjbs, qfaiy\n" + //
            "bzdbxi (74)\n" + //
            "wvkrjz (1399) -> dmcvsjm, aqlnsb, fynfku\n" + //
            "gxpqjp (45)\n" + //
            "jacxtkj (165) -> flqvd, lgmsbu\n" + //
            "bfwwmam (51) -> izqkafc, bunddd, wzicni, hzdjy\n" + //
            "wgfxt (195) -> gzpbfr, nhfhwsb\n" + //
            "chriadz (13)\n" + //
            "ksxbwm (11)\n" + //
            "ylzefq (62)\n" + //
            "gbgzoy (81)\n" + //
            "znlwikg (81)\n" + //
            "faucj (41)\n" + //
            "kpzfnz (48) -> firzu, esfkj\n" + //
            "ezxcaqx (18)\n" + //
            "fcaaq (75) -> mptfgnr, nanka, dazaikq, lkyrbu\n" + //
            "erhgm (211) -> tykfhh, xmqfua\n" + //
            "ackdjv (68) -> ftiupez, jgihy\n" + //
            "qkvogv (14) -> yrhrrr, lvxvpth, iyhelv, zwhvins, qwqbgko\n" + //
            "qpiwrz (81) -> knczsj, fjkhu, btfwq, hiibilo, eugdc, ieetr\n" + //
            "bvrrf (77)\n" + //
            "xkzrpp (207) -> wksjc, lwedn\n" + //
            "nputbbu (93) -> gjfjuk, exwnwk\n" + //
            "yoyfiwh (66)\n" + //
            "jgeknun (97)\n" + //
            "nmpozu (30)\n" + //
            "apoom (183) -> vhlfxfp, lgnltgy\n" + //
            "vxywld (23)\n" + //
            "kxudlu (1291) -> jcpfdu, dwddkt, qxvtbfs, wlubh, miheo\n" + //
            "yevvy (34)\n" + //
            "iqaret (13)\n" + //
            "msnvc (57)\n" + //
            "lrrtkfx (53)\n" + //
            "firzu (80)\n" + //
            "dualr (20) -> qdsbwl, vldkam, gbeooc\n" + //
            "qjpmeb (30)\n" + //
            "rsisz (46) -> vtlju, uwflq, pccpzvp\n" + //
            "ykqcfv (14)\n" + //
            "blscadu (91)\n" + //
            "gglbk (75) -> jndkhd, aodxwem\n" + //
            "ctofl (11)\n" + //
            "rnnqgvs (254) -> wzgnpbd, tclwlv, yupwby\n" + //
            "rdgbqio (94)\n" + //
            "efhdibc (40)\n" + //
            "lvxvpth (1490) -> snlalj, fojja, nitloc\n" + //
            "xlkgp (24)\n" + //
            "fchnaw (205) -> fodble, noiubo\n" + //
            "fabvi (74)\n" + //
            "abmxib (21)\n" + //
            "wsfdatj (69)\n" + //
            "jiaiwto (936) -> ezftzv, uwcumu, dylatux, fhezp, lcbmlo\n" + //
            "bhjuuh (16)\n" + //
            "bwhjf (79)\n" + //
            "ublhtvx (47)\n" + //
            "lfopzn (67)\n" + //
            "ovtzpu (24)\n" + //
            "qtbws (32)\n" + //
            "kakya (47)\n" + //
            "kcxol (83)\n" + //
            "xsmuszr (64)\n" + //
            "abeyout (324) -> vatjopi, krrpjjl\n" + //
            "jndkhd (74)\n" + //
            "pguona (99)\n" + //
            "wmzmuk (61)\n" + //
            "znntc (63) -> prcff, olegqeb\n" + //
            "hrrnlw (89)\n" + //
            "vhlfxfp (88)\n" + //
            "uqmwqye (180) -> hrrnlw, gwlrhs\n" + //
            "ufbyegt (11)\n" + //
            "etzcek (97) -> nuynvy, msnvc\n" + //
            "ztkiwjh (46) -> qwike, sairne\n" + //
            "hpoyfr (41)\n" + //
            "harlf (526) -> ilgssfa, fdkyuhv, npoep\n" + //
            "jypod (16)\n" + //
            "juxyuv (42)\n" + //
            "itcgi (11)\n" + //
            "uwldrgf (64) -> ahbhxn, wchboy, acckfcm, ijpaqb, eepvqlh, pjockdt, raxhxb\n" + //
            "vxbubjs (21)\n" + //
            "hfzhso (8)\n" + //
            "grglx (143) -> gsilyv, rrdpahj\n" + //
            "pgpszp (79)\n" + //
            "kekbi (71) -> gnoisq, bfmwqqf\n" + //
            "agrkalx (92)\n" + //
            "jcpfdu (87) -> jzyjby, spfqkfm\n" + //
            "fbyfuuo (138) -> eefthpt, giyfh\n" + //
            "qqpefh (41)\n" + //
            "wznaj (41) -> fmhuoa, rdgbqio\n" + //
            "lwlhv (19)\n" + //
            "wffua (79)\n" + //
            "xsefm (16)\n" + //
            "khekb (78)\n" + //
            "wnplhq (77) -> nlonk, dvgaf, rbxgy, bvrrf\n" + //
            "czhrelb (71)\n" + //
            "pdrnuox (45)\n" + //
            "phaab (75)\n" + //
            "frayh (81)\n" + //
            "qajub (1139) -> ctulmjv, rvrln, zlizsa\n" + //
            "tqhbqhb (44)\n" + //
            "ewrnfma (192)\n" + //
            "gwlrhs (89)\n" + //
            "vlsons (132) -> wrvyeig, wvfty\n" + //
            "hxwcaqc (87)\n" + //
            "fcavj (92)\n" + //
            "vptoff (195) -> hfzhso, uizst\n" + //
            "ndextdo (89)\n" + //
            "krrpjjl (17)\n" + //
            "qavslj (23)\n" + //
            "qwsjrs (1423) -> vjlxr, esgkmgn, dcmwnt\n" + //
            "yhksuh (48)\n" + //
            "awpysm (77) -> szifo, voqoq, qktqn, abnvpr, xfadyhg, sxoxuhy, jmjybfb\n" + //
            "qwgpbfg (46)\n" + //
            "ebkdhne (54)\n" + //
            "jzyjby (51)\n" + //
            "lgvms (60) -> atrgeje, czhrelb, emdcjcc\n" + //
            "ejootzk (21)\n" + //
            "ejbhi (64)\n" + //
            "drjgbw (83)\n" + //
            "acvpax (92)\n" + //
            "djwhzz (22) -> fbdpoc, kqfgpp, ejbhi, fkouq\n" + //
            "vkzpqun (181)\n" + //
            "ryhdf (43)\n" + //
            "mmneogw (208) -> mtdnxdv, dixfg\n" + //
            "ddssmn (1486) -> ysqoonr, ipnzaia, ljpndb\n" + //
            "iijuiln (9)\n" + //
            "fhezp (1004) -> otffrtx, iteoql, pssbgm\n" + //
            "dhtyyg (343)\n" + //
            "rvvuep (50)\n" + //
            "wvtnd (115) -> lctnj, yxaki, abmcv\n" + //
            "nhfhwsb (41)\n" + //
            "lfqglc (12)\n" + //
            "cunshl (203) -> docbp, bdlmbpt\n" + //
            "deeinjt (59)\n" + //
            "lkyrbu (67)\n" + //
            "zbqwhi (135) -> vurgzbe, npblyg\n" + //
            "bgkys (56)\n" + //
            "epkixfl (9)\n" + //
            "vriwfj (36) -> mkbkf, wmxih\n" + //
            "dfhbypd (47)\n" + //
            "tnluhpd (25)\n" + //
            "zgyuoh (41)\n" + //
            "abnvpr (173) -> hlpyj, zvynzb\n" + //
            "mkdimt (84)\n" + //
            "kvlbx (66)\n" + //
            "ddayyiv (290)\n" + //
            "iivbohi (48)\n" + //
            "spfqkfm (51)\n" + //
            "mkyhyh (45)\n" + //
            "qivrp (95) -> oikboi, yoyfiwh\n" + //
            "jhihznq (60)\n" + //
            "lnjbmj (203) -> wlifgm, uuraq\n" + //
            "axvuwo (295)\n" + //
            "gqyhvji (23)\n" + //
            "qljngh (110) -> ngnyte, qwgpbfg\n" + //
            "opmnbbk (1191) -> yegoug, yqhvof, dgyrl, isuzre\n" + //
            "dtfsxdh (88)\n" + //
            "jeqojht (301) -> ikdgjcm, aimvgyf\n" + //
            "bplrv (59)\n" + //
            "yqflxg (34)\n" + //
            "thxkub (39)\n" + //
            "ednozov (42)\n" + //
            "qhqqbl (89)\n" + //
            "dfaui (19)\n" + //
            "iyemub (34)\n" + //
            "mhptn (310)\n" + //
            "wqjbjg (44)\n" + //
            "lwedn (19)\n" + //
            "qxvtbfs (189)\n" + //
            "cklybnh (26)\n" + //
            "dmcvsjm (155) -> qavslj, gqyhvji\n" + //
            "uefjnb (99)\n" + //
            "ygtusut (23)\n" + //
            "crfaeqg (252)\n" + //
            "nepucl (1320) -> jvamqz, txejs, mmqsptl, wznaj\n" + //
            "rmtjyq (68) -> srfoda, eddwubo\n" + //
            "somurr (67)\n" + //
            "wrvyeig (23)\n" + //
            "tvnoctm (45)\n" + //
            "blofhut (11)\n" + //
            "bqjch (167) -> kutwy, qnzej\n" + //
            "xenad (25)\n" + //
            "zlqtv (14)\n" + //
            "lodegh (87)\n" + //
            "ntdeo (360) -> ewrnfma, kgprdd, giuua, wjwnd, hlddv\n" + //
            "sitfw (134) -> fhptqw, pjjmx, uwbonvb\n" + //
            "ieetr (45) -> ujhhibr, togwcf, bwxrzd, wbzmod\n" + //
            "scxgu (71)\n" + //
            "xwagm (81) -> qnfptsg, npipcju\n" + //
            "fmdfzpk (89)\n" + //
            "ynirob (221) -> wjlprje, gxhnsq\n" + //
            "gmfsqw (30) -> aotgdc, cbtbxyj, lfopzn, smiki\n" + //
            "vlhsdy (449) -> souqsh, pkdky, vtuzdmb\n" + //
            "btfwq (343) -> cnndv, zsgegr\n" + //
            "vheuyos (41)\n" + //
            "bevzsm (41)\n" + //
            "xfadyhg (117) -> mtxtg, bwhjf\n" + //
            "gjtwc (96) -> tjepyd, rnnqgvs, pzzerxc, tusmyhc, jzytjy\n" + //
            "koordg (1686) -> pbrfxep, qldtb, toqcntj\n" + //
            "ftiupez (79)\n" + //
            "gxlnyfk (9)\n" + //
            "uwflq (54)\n" + //
            "jborbs (90)\n" + //
            "bwxrzd (82)\n" + //
            "razhq (135) -> detrtr, wpqtlt, pyjlxpa, gvhnj\n" + //
            "nvulzcj (93) -> mvrhyd, jbesus\n" + //
            "wlifgm (22)\n" + //
            "dfrdsx (92)\n" + //
            "apangy (91)\n" + //
            "qybpmcs (1503) -> mcarhq, bajivws, vriwfj, geqhfh, xyfqbw, ztkiwjh\n" + //
            "smllqwu (22)\n" + //
            "ruspne (64)\n" + //
            "bqygu (602) -> zjuaeyb, jacxtkj, qxhuu, qckcj, ydsqnu, nvulzcj\n" + //
            "obplhib (35)\n" + //
            "obfhgbl (6)\n" + //
            "uujwdtb (91)\n" + //
            "odvzjj (49)\n" + //
            "znbzdqc (90)\n" + //
            "ghkvjcx (75)\n" + //
            "mansket (10)\n" + //
            "bjqbgn (92)\n" + //
            "qwike (45)\n" + //
            "otffrtx (98) -> sgkjjhe, jsuvvc\n" + //
            "nsehz (82)\n" + //
            "rajgq (57) -> vrflxtt, hnsyjq, bgfqi, rnncmf, lnjbmj, bevree, sxzfd\n" + //
            "qypftq (11)\n" + //
            "kstjj (1875) -> vvqnfr, gcuqah, cixxs\n" + //
            "fmhuoa (94)\n" + //
            "azyuh (78)\n" + //
            "yhvvk (87)\n" + //
            "giyfh (86)\n" + //
            "cnndv (15)\n" + //
            "mtxtg (79)\n" + //
            "yopixg (144) -> xzbuxg, wbbiy\n" + //
            "nnjokq (52)\n" + //
            "rmczumv (74)\n" + //
            "zkeocg (45) -> nifolyc, bgkys, eieslp, olywf\n" + //
            "bwpyz (77)\n" + //
            "hpxkzm (88)\n" + //
            "kmwsdo (44)\n" + //
            "chwnp (41) -> deeinjt, fmpkn, bplrv, nhqcyo\n" + //
            "esnoa (10087) -> dxfln, lzeeb, xutehnq\n" + //
            "bgilhnw (94)\n" + //
            "detrtr (22)\n" + //
            "cixxs (80) -> yevvy, aafaeyd\n" + //
            "svkkmgi (94) -> lubjlxb, uzjxnwf\n" + //
            "tpxwzh (38)\n" + //
            "tkekg (7272) -> gjtwc, fxyfmtw, tuaafw, jxllcpv\n" + //
            "verta (117) -> yqflxg, iyemub, bfcpfc, xfhxb\n" + //
            "cuxbqo (67)\n" + //
            "xrporzt (46) -> vizxyti, vrfrgqq, cwadvj\n" + //
            "cpzhmuv (41)\n" + //
            "ggwcjup (189) -> aavcuhq, acznm, swukjd, smllqwu\n" + //
            "qftda (78)\n" + //
            "vlxrpve (131) -> dcxprn, lzfbfx\n" + //
            "dtvxbn (31) -> iqlxjrp, dgpyy\n" + //
            "irbpync (25)\n" + //
            "vgqdk (66)\n" + //
            "lxvsklc (47)\n" + //
            "ypdcg (372)\n" + //
            "eepvqlh (99) -> olauul, ghkvjcx\n" + //
            "dgvxrd (83)\n" + //
            "virxd (64)\n" + //
            "bhvxkx (78)\n" + //
            "wsgicvu (92)\n" + //
            "duwsfyh (20)\n" + //
            "bkhouo (85)\n" + //
            "jeluq (95) -> simjssf, otnkggc, oeyvbro, dptrckz\n" + //
            "lwqrj (67)\n" + //
            "dkxszcn (27) -> zmmnvt, osxza, vhdea\n" + //
            "ydsqnu (172) -> eeqszay, kmmejxq, rlgjt\n" + //
            "gpzdwzc (21)\n" + //
            "bngep (48)\n" + //
            "zlhwwj (35)\n" + //
            "gsivsr (88)\n" + //
            "uzrqz (61) -> ctofl, ikcofec, eywyawe, qdykimk\n" + //
            "dzvqmjz (68)\n" + //
            "dlncdim (79)\n" + //
            "nuytwqt (79)\n" + //
            "gxmyaep (12)\n" + //
            "nfuhle (69)\n" + //
            "bjgyr (89) -> abmxib, vxbubjs\n" + //
            "ksznx (27)\n" + //
            "jpxcpds (30)\n" + //
            "prcff (27)\n" + //
            "offde (43)\n" + //
            "dpqqukb (35)\n" + //
            "rwiet (335) -> rgjrr, vhechuz\n" + //
            "fwhbf (84)\n" + //
            "amhaz (39) -> pvnsq, vlxrpve, fchnaw, toejq, dnxvlw, iqjhw, uksfrmb\n" + //
            "sktjkgs (91)\n" + //
            "uiphn (36)\n" + //
            "gruvxkx (778) -> dhtyyg, fcaaq, qkhciro\n" + //
            "wzicni (61)\n" + //
            "emwgz (1072) -> erqmft, bprqu, vjeqj\n" + //
            "xarbaxi (760) -> jwani, wrigvm\n" + //
            "epgmufe (90)\n" + //
            "kqkdd (60)\n" + //
            "gzpbfr (41)\n" + //
            "fkfhz (28)\n" + //
            "gckpcy (75)\n" + //
            "mhscf (99)\n" + //
            "ypymf (204) -> rtckm, xlkgp\n" + //
            "qlphalp (44)\n" + //
            "jkqctlo (90)\n" + //
            "tgdxj (51)\n" + //
            "sdpmpe (32)\n" + //
            "uqqjmps (44)\n" + //
            "bfcpfc (34)\n" + //
            "swukjd (22)\n" + //
            "wflrs (9)\n" + //
            "ldeelvr (19)\n" + //
            "npoep (164)\n" + //
            "cxokpea (90)\n" + //
            "bunddd (61)\n" + //
            "mlufac (96) -> ajkaxbk, ypatkqb, smatb, dokkjf\n" + //
            "snlalj (96) -> budlmc, lfhkif\n" + //
            "kqfgpp (64)\n" + //
            "smiki (67)\n" + //
            "soocm (7)\n" + //
            "lywwhf (78)\n" + //
            "jbesus (68)\n" + //
            "osxza (82)\n" + //
            "exjlwux (43)\n" + //
            "ueszsu (885) -> scglak, cwlzph, bqkjt\n" + //
            "lzfbfx (67)\n" + //
            "cknaaem (8090) -> sgwhkc, yddig, aywmn, ggwcjup, ioabb, wkatvkd, huongnb\n" + //
            "zibnqys (67)\n" + //
            "qfaiy (99)\n" + //
            "xwdxtws (45) -> etrstos, pqalwfs, ypdcg\n" + //
            "eefthpt (86)\n" + //
            "iyhelv (1388) -> ghgsrqm, svkkmgi, arwpamw\n" + //
            "xqjqedz (61)\n" + //
            "uuraq (22)\n" + //
            "qzrbg (8098) -> rajgq, btahmaz, emwgz\n" + //
            "jyuww (55) -> bngep, nxszwh, mjkvgm, iivbohi\n" + //
            "syjeatu (54)\n" + //
            "akzavdu (130) -> ubcenyy, hrckiut\n" + //
            "powzs (217) -> rgjigx, fgrhkdd\n" + //
            "yeqdida (48)\n" + //
            "gnoisq (76)\n" + //
            "ywzaw (9)\n" + //
            "campr (52)\n" + //
            "tsfmg (79) -> zrpnsh, qtuxuwe\n" + //
            "srubp (76)\n" + //
            "igrjbzz (81) -> mkdimt, nagjilt\n" + //
            "nzhby (61) -> wufwm, verta, eezhvk, axftz, lswbgy\n" + //
            "ctulmjv (110) -> lgiwd, vkdxy\n" + //
            "rfjilj (278)\n" + //
            "bprqu (116) -> wzdcg, ezyzl\n" + //
            "frlpmld (5)\n" + //
            "flqvd (32)\n" + //
            "hpbcytx (13)\n" + //
            "lkvju (464) -> zfxyqi, vkzpqun, jawskc\n" + //
            "dsstz (254)\n" + //
            "bczlzx (63)\n" + //
            "epxqmky (87)\n" + //
            "atrgeje (71)\n" + //
            "lxbsb (97)\n" + //
            "iszhf (17) -> qftda, bvcrfun\n" + //
            "qdsbwl (33) -> lsecoh, ampem, jhiejzb, fabvi\n" + //
            "ahbhxn (227) -> ufbyegt, ksxbwm\n" + //
            "vflryv (83)\n" + //
            "dcqtq (84)\n" + //
            "mynoky (11)\n" + //
            "wtpzrwa (36)\n" + //
            "pwxyk (25) -> cwzzlp, xrporzt, omhqjws\n" + //
            "gwllcp (5)\n" + //
            "xqewue (73)\n" + //
            "mjsib (203) -> wpanqd, zlhwwj\n" + //
            "ygkjkzl (37)\n" + //
            "rzjbs (99)\n" + //
            "drrlizg (27)\n" + //
            "ocynqs (207)\n" + //
            "tuezm (67)\n" + //
            "qdykimk (11)\n" + //
            "fbdpoc (64)\n" + //
            "moyxzbt (27) -> alwkx, znbzdqc\n" + //
            "uautcpo (26)\n" + //
            "trvnbpq (20) -> kuswuil, razhq, ipauj, kekbi, izyzvs, gglbk, nputbbu\n" + //
            "qedztsd (28)\n" + //
            "mphqxmj (41)\n" + //
            "wmrxba (47)\n" + //
            "oiksv (84)\n" + //
            "ltpdyh (71)\n" + //
            "olufpv (13)\n" + //
            "yxaki (54)\n" + //
            "djsczmj (9)\n" + //
            "mvrhyd (68)\n" + //
            "pjjmx (31)\n" + //
            "conuwmt (9)\n" + //
            "bzlgvwt (352) -> prjpe, itcgi, qypftq\n" + //
            "yrhrrr (70) -> edtzpv, obtsuh, mmneogw, kjvnox, fbyfuuo, vthnptc, mhptn\n" + //
            "jxllcpv (1510) -> oraaeb, exiatbf, epkixfl, djsczmj\n" + //
            "izqkafc (61)\n" + //
            "jsuvvc (62)\n" + //
            "zegmhba (216) -> mansket, cgxxf, bwkhwca, gzgqh\n" + //
            "ufthnyr (52) -> gbgzoy, yhfcugm, tjhtlqt\n" + //
            "reeaojr (96) -> mphqxmj, duron\n" + //
            "gcuqah (122) -> vmmtxcw, chriadz\n" + //
            "rlgjt (19)\n" + //
            "bfmwqqf (76)\n" + //
            "knczsj (315) -> hpyfzew, epywpbm\n" + //
            "twfzsig (173) -> fhdcvd, pvoch\n" + //
            "nkflspq (60)\n" + //
            "ddfvxuh (117) -> cuxbqo, buurids, tuezm, zibnqys\n" + //
            "hwyjtzo (27)\n" + //
            "iukroo (51)\n" + //
            "rytowol (157) -> nmpozu, impwda, hexamha\n" + //
            "ooevokb (56)\n" + //
            "npipcju (12)\n" + //
            "gbwdd (83) -> esquqjv, lfyyq, jktzsu, mcbkdjm, vszkz\n" + //
            "udoins (88) -> fmrbyn, abeyout, uqmwqye, bologkp, nyhlmwv, smqdbd\n" + //
            "hxsausc (205) -> anlhcj, obplhib\n" + //
            "hlpyj (51)\n" + //
            "gique (27) -> uneul, lwyej, fwhbf, yhnfc\n" + //
            "nynvxxt (411) -> xybyxrz, bkckl, gndfat\n" + //
            "zfxyqi (141) -> qyjllh, ambyx\n" + //
            "cvfyti (60)\n" + //
            "vpkja (9336) -> djwoy, bbmrv, bdrcuw\n" + //
            "mroag (1535) -> criyk, hkzfl\n" + //
            "oxudyzv (54)\n" + //
            "pqkudqa (11)\n" + //
            "qwgal (52)\n" + //
            "jjdefjc (193) -> allxyqf, cqdcsas\n" + //
            "xoolwto (197) -> evayy, uiphn\n" + //
            "qckcj (229)\n" + //
            "vrflxtt (57) -> dxuzg, naupker\n" + //
            "nitloc (250)\n" + //
            "yegxpjz (357) -> skxmk, ykqcfv\n" + //
            "docbp (46)\n" + //
            "mekaep (6)\n" + //
            "psgqug (175) -> zhunqhj, azyuh\n" + //
            "vjeqj (50) -> rtqef, tofxrp\n" + //
            "qxhuu (143) -> ywtmxkk, jtnvh\n" + //
            "wczux (41) -> zmgehs, wffua, vjvbn\n" + //
            "tzvshk (9)\n" + //
            "xzbuxg (15)\n" + //
            "dribos (1213) -> cjdmv, lpozpnx, jknws\n" + //
            "cwagf (89)\n" + //
            "arwpamw (134) -> uoztb, evsryno\n" + //
            "bfmemro (319)\n" + //
            "vkdxy (58)\n" + //
            "wbzmod (82)\n" + //
            "fkouq (64)\n" + //
            "uwcumu (989) -> bsaqdlw, dfikzif, qivrp\n" + //
            "btahmaz (493) -> wyfod, qjwjgap, jeluq\n" + //
            "apvdjw (41)\n" + //
            "vulgw (66) -> uefjnb, eiqkt, secmp\n" + //
            "cxiqycp (98)\n" + //
            "aimvgyf (9)\n" + //
            "icdth (153) -> ksznx, rttef\n" + //
            "lxmob (25)\n" + //
            "ybcmydv (54) -> mdhzx, mtpmeyy, foyrjn, ykvbw, xqour\n" + //
            "eeqszay (19)\n" + //
            "lvcpzy (61)\n" + //
            "alwkx (90)\n" + //
            "ysqoonr (249)\n" + //
            "hiibilo (273) -> flutk, xwqwjpe\n" + //
            "kmihua (52)\n" + //
            "cjjge (80)\n" + //
            "ohtrv (176) -> wflrs, omicyg\n" + //
            "idmaspp (64)\n" + //
            "acznm (22)\n" + //
            "eilena (47)\n" + //
            "nvnqv (81)\n" + //
            "npblyg (57)\n" + //
            "dptrckz (84)\n" + //
            "scpzva (79)\n" + //
            "ugdcq (94)\n" + //
            "ikdgjcm (9)\n" + //
            "fucsb (24047) -> uzvsyt, bcawzmn, cknaaem, ybcmydv, rphyyst, ikqwjn, vpkja\n" + //
            "alseh (5)\n" + //
            "seeusvs (69)\n" + //
            "epywpbm (29)\n" + //
            "erieyzj (113) -> odvzjj, gcepy\n" + //
            "ieiyqb (87)\n" + //
            "mejujn (67)\n" + //
            "czgwxvf (121) -> wtpzrwa, vpcnw\n" + //
            "almjyt (46) -> ruspne, xsmuszr\n" + //
            "rluqka (94)\n" + //
            "pvnsq (109) -> sqlwsz, bhvxkx\n" + //
            "otivo (84) -> nsehz, caufhb\n" + //
            "cvdatwa (91)\n" + //
            "rnncmf (209) -> smcmduw, tsaqdup\n" + //
            "tuaafw (643) -> deqakr, zcyzaxo, xdvfq\n" + //
            "bologkp (142) -> syjeatu, ylmgpwu, ebkdhne, ocfqtr\n" + //
            "xcrfey (54)\n" + //
            "sefsszz (92)\n" + //
            "xcjfiq (39)\n" + //
            "gvhnj (22)\n" + //
            "eieslp (56)\n" + //
            "rrdpahj (26)\n" + //
            "sbrhj (159) -> ovtzpu, pjqxvcx\n" + //
            "dnxvlw (265)\n" + //
            "mmbhu (269) -> cqysfwi, twmxk\n" + //
            "arcnlfo (54)\n" + //
            "ezftzv (56) -> esvdf, dpyag, zkeocg, lwgeanp, bqjch, xoolwto\n" + //
            "igwsote (6)\n" + //
            "nzayo (5)\n" + //
            "anlhcj (35)\n" + //
            "zacca (272) -> xkzrpp, sijmk, zjyaaoc\n" + //
            "xqour (84) -> jjdefjc, cvhuwne, powzs, fibrh, chhjhsu, kcnabhn, lgvms\n" + //
            "iezqyyh (45)\n" + //
            "fynfku (33) -> jjlen, oiksv\n" + //
            "gsgsoo (259) -> dvehwh, mynoky\n" + //
            "ytnamqm (28)\n" + //
            "befwl (99)\n" + //
            "rttef (27)\n" + //
            "hgpeqh (13)\n" + //
            "owbglst (77)\n" + //
            "urbknyn (45) -> yrudtf, lfhkna\n" + //
            "znaap (91)\n" + //
            "fgsiued (402)\n" + //
            "aperzqo (91)\n" + //
            "nmwst (189)\n" + //
            "cvgwkop (29)\n" + //
            "sgkjjhe (62)\n" + //
            "uojvtmd (10) -> fcavj, ajqhy, bjqbgn, amzxtvd\n" + //
            "qrfmb (97) -> nzayo, mukks, alseh, agvigpm\n" + //
            "cqysfwi (25)\n" + //
            "dcxprn (67)\n" + //
            "uyklfxt (174) -> jtsahle, geqes\n" + //
            "ipnzaia (75) -> pqdnn, epxqmky\n" + //
            "jrzeg (84)\n" + //
            "xlcvwa (92)\n" + //
            "rzvmuhc (74)\n" + //
            "twumak (656) -> znntc, accmwfa, qrfmb\n" + //
            "menqop (97)\n" + //
            "sqdnri (38)\n" + //
            "zcuix (52)\n" + //
            "viydtj (10) -> kstjj, qybpmcs, qpiwrz, koordg\n" + //
            "mukgev (174) -> bevzsm, faucj\n" + //
            "yhnfc (84)\n" + //
            "mktrq (41)\n" + //
            "jsnlpme (121) -> fgbmgk, ytnamqm\n" + //
            "dnqva (79) -> apoom, hjvegbe, ttsik, gcjjvj, wrucr, xnpbag\n" + //
            "lfkpbw (91)\n" + //
            "wcfib (185) -> saskhnf, tqhbqhb\n" + //
            "dpyag (259) -> jwymbhh, gwllcp\n" + //
            "isuzre (154)\n" + //
            "urmwxm (84)\n" + //
            "olryvzf (27)\n" + //
            "lpozpnx (93) -> bawsdp, eyfui\n" + //
            "bmucb (42)\n" + //
            "taadcy (49) -> qhqqbl, zhcka, vxfsl\n" + //
            "opcrls (29)\n" + //
            "rvrln (74) -> fjxjp, glvpo\n" + //
            "xemhgy (90)\n" + //
            "bxiiba (57)\n" + //
            "falzunq (15)\n" + //
            "tazjcwi (90)\n" + //
            "sijmk (89) -> dmfzrwz, nuautun\n" + //
            "fsetxvb (203) -> erhgm, tnuvco, deujsoj\n" + //
            "nmcrmt (74)\n" + //
            "xfqfzsh (113) -> vnlet, baomcxj, arcnlfo\n" + //
            "ykpsek (84) -> xgudb, fucsb, rsalq, xjllex, splbrdu\n" + //
            "mfqcnw (64) -> rgiro, zfwxd, ggtdtk\n" + //
            "ioabb (89) -> xakcd, bgilhnw\n" + //
            "dazaikq (67)\n" + //
            "zwhvins (1493) -> igrjbzz, zbqwhi, zlcappl\n" + //
            "lubjlxb (95)\n" + //
            "lazhif (8160) -> pwxyk, harlf, mfqcnw\n" + //
            "fouvfkn (5208) -> fahfz, wvkrjz, awpysm\n" + //
            "xjllex (64931) -> xjqjmx, mkvdfih, gbwdd\n" + //
            "iteoql (46) -> zkyawx, kmwsdo, qlphalp, tnpjqk\n" + //
            "tjcnb (96)\n" + //
            "uqhati (77)\n" + //
            "splbrdu (58) -> gqdyvi, qzrbg, esnoa, hxnxfk, ialiosa, tkekg, oskyl\n" + //
            "ylmgpwu (54)\n" + //
            "ifixi (56)\n" + //
            "srfoda (53)\n" + //
            "lqhydgt (45)\n" + //
            "mhfbh (25) -> oieobzw, uhbty, utdyk, utcwj, njryk, mswtfmy\n" + //
            "uijhb (29)\n" + //
            "erqmft (72) -> drjgbw, aoxfg\n" + //
            "iafal (32)\n" + //
            "zmgehs (79)\n" + //
            "simjssf (84)\n" + //
            "uclrhqy (52)\n" + //
            "dkshm (91)\n" + //
            "vtlju (54)\n" + //
            "ialiosa (8035) -> opmnbbk, gruvxkx, uwldrgf\n" + //
            "byffz (64)\n" + //
            "wrinrd (39)\n" + //
            "qbmsizh (37)\n" + //
            "exwnwk (65)\n" + //
            "vizxyti (95)\n" + //
            "achnat (91)\n" + //
            "fibrh (195) -> fdjrm, oxngag\n" + //
            "uujpswh (35)\n" + //
            "wzgnpbd (12)\n" + //
            "angwk (97)\n" + //
            "rbqbboq (13)\n" + //
            "bevree (89) -> dlncdim, scpzva\n" + //
            "obtsuh (106) -> xjtvw, iukroo, lpuzdj, zobxdhc\n" + //
            "klhlxb (50)\n" + //
            "uodkl (67) -> rmdxq, pdsuhsj\n" + //
            "evsryno (75)\n" + //
            "bwkhwca (10)\n" + //
            "mmqsptl (45) -> acvpax, sfegieu\n" + //
            "tsywh (38)\n" + //
            "fgbmgk (28)\n" + //
            "lgiwd (58)\n" + //
            "noiubo (30)\n" + //
            "vgkkp (34)\n" + //
            "zlizsa (82) -> azrafw, pbfyx\n" + //
            "gcvsbqg (12)\n" + //
            "wbyisd (73) -> yubyr, dmmriu\n" + //
            "cpdtit (94) -> gqdbu, oawmfyx\n" + //
            "ajkhou (28)\n" + //
            "yarqa (68)\n" + //
            "ijpkw (28)\n" + //
            "qqyqj (86)\n" + //
            "xjtvw (51)\n" + //
            "tofxrp (94)\n" + //
            "srgev (17)\n" + //
            "mswtfmy (147) -> cwsycss, isrdlt\n" + //
            "cqhwrn (97)\n" + //
            "oqwgc (178)\n" + //
            "hihdfp (75)\n" + //
            "jjlen (84)\n" + //
            "mzogi (77)\n" + //
            "xjwsq (69)\n" + //
            "bgfqi (53) -> acfxsgz, angwk\n" + //
            "mjkvgm (48)\n" + //
            "fckqt (6) -> wrmebwn, cxiqycp\n" + //
            "zmmnvt (82)\n" + //
            "sliim (28)\n" + //
            "qromdd (52)\n" + //
            "cjntq (591) -> wvtnd, chwnp, hqpikjx, ojdec, wgfxt\n" + //
            "nanka (67)\n" + //
            "azdkxw (741) -> umpivl, wbyisd, grglx\n" + //
            "dfikzif (195) -> mwjtujw, bhjuuh\n" + //
            "deowe (208)\n" + //
            "xumalc (59) -> jepwvl, yuutd, eilena, fypzx\n" + //
            "nqvpxt (52)\n" + //
            "fshbxj (130) -> hdhjbvn, lksjvrl\n" + //
            "zrpnsh (26)\n" + //
            "wfysif (140) -> vktggdu, seeusvs\n" + //
            "ccjay (28)\n" + //
            "geqes (37)\n" + //
            "wuwksnc (49)\n" + //
            "rsalq (47820) -> sdxbol, irqwcab, jiaiwto, viydtj, uduyfo\n" + //
            "gotnp (99)\n" + //
            "wyaatnb (6) -> ejyyd, gotnp, mhscf, xvfbt\n" + //
            "wpqtlt (22)\n" + //
            "nagjilt (84)\n" + //
            "mkvdfih (1505) -> nhfqb, cyahch, bfznf\n" + //
            "oskyl (8899) -> mhfbh, bzmlka, zlkhsa\n" + //
            "xybyxrz (281) -> ytnkx, brofu\n" + //
            "qvsyjp (28)\n" + //
            "xvfbt (99)\n" + //
            "mtmpaji (10)\n" + //
            "togwcf (82)\n" + //
            "dxhqs (56)\n" + //
            "vurgzbe (57)\n" + //
            "wbbiy (15)\n" + //
            "avueexg (63)\n" + //
            "acpvgye (57)\n" + //
            "vktggdu (69)\n" + //
            "dgpyy (79)\n" + //
            "oomcfz (65)\n" + //
            "fypzx (47)\n" + //
            "naupker (95)\n" + //
            "voafbv (533) -> hmlkdv, afhbfu, gspvc, uqxdea\n" + //
            "jfpqxeb (52)\n" + //
            "njryk (18) -> mzogi, ieekq, pkmojw\n" + //
            "ocleuky (41)\n" + //
            "cjoktd (222) -> elulm, wymhyko\n" + //
            "agvigpm (5)\n" + //
            "lfhkif (77)\n" + //
            "rmhroyf (65)\n" + //
            "ogcra (78) -> cxokpea, pdftmlf\n" + //
            "elpequ (87) -> lqhydgt, mkyhyh\n" + //
            "uycpqa (38)\n" + //
            "mwvluyo (173)\n" + //
            "xlqya (89) -> mikmp, klhlxb\n" + //
            "lmpfqe (59)\n" + //
            "rpuefig (81)\n" + //
            "xnvqjpw (81)\n" + //
            "ecaqtyw (99)\n" + //
            "cqusdqo (121) -> hgpeqh, iqaret, yiwrl, rbqbboq\n" + //
            "mwxuez (203) -> hlidsi, lfkpbw\n" + //
            "wrigvm (92)\n" + //
            "ammsc (112) -> fhbphvw, uudmxem, tpxwzh\n" + //
            "qolhl (53)\n" + //
            "ytjnkx (50) -> pguona, befwl\n" + //
            "mvgcb (50)\n" + //
            "kuswuil (127) -> yhksuh, bhfbkik\n" + //
            "bdlmbpt (46)\n" + //
            "zjuaeyb (195) -> cdthf, rtmloqi\n" + //
            "omhqjws (139) -> boolrdh, lmcvdg\n" + //
            "bzmlka (937) -> nomlds, almlu, ohtrv\n" + //
            "tghuhpn (59)\n" + //
            "souqsh (8) -> ortnis, vnseppy\n" + //
            "jawskc (69) -> czfbso, qedztsd, ajkhou, sliim\n" + //
            "hvjusf (15)\n" + //
            "xaxmer (17)\n" + //
            "ajqhy (92)\n" + //
            "vmmtxcw (13)\n" + //
            "oyiem (15)\n" + //
            "csypcco (163) -> jgeknun, menqop\n" + //
            "eyfui (67)\n" + //
            "gspvc (75) -> xujppza, thizds\n" + //
            "uhrkq (61)\n" + //
            "xbrnkt (24)\n" + //
            "ojdec (157) -> nkflspq, kyshwy\n" + //
            "xzvbd (84)\n" + //
            "zkyawx (44)\n" + //
            "htlvlk (26)\n" + //
            "ytnkx (12)\n" + //
            "rmnxqwr (54)\n" + //
            "onasr (57)\n" + //
            "afajf (40)\n" + //
            "kfuvm (39)\n" + //
            "vdongnn (34)\n" + //
            "wrmebwn (98)\n" + //
            "pbrfxep (22) -> avueexg, bczlzx, kmgdk\n" + //
            "jajds (81)\n" + //
            "glvea (913) -> ackdjv, ammsc, iygaq, tqslgy\n" + //
            "ijpaqb (93) -> campr, pqmiyd, jfpqxeb\n" + //
            "wuypkq (51)\n" + //
            "wohqjaq (295)\n" + //
            "iqjhw (119) -> kwqnczk, ffulcj\n" + //
            "yrudtf (30)\n" + //
            "tnuvco (67) -> jkqctlo, jborbs\n" + //
            "kyshwy (60)\n" + //
            "baomcxj (54)";

    public static void main(final String[] args) {
        Pattern pattern = Pattern.compile("([a-z]*?) \\(([0-9]*)\\)( -> .*)?");
        BufferedReader reader = new BufferedReader(new StringReader(INPUT));
        Map<String, Node> nodes = new HashMap<>();
        reader.lines() //
                .forEach(line -> {
                    Matcher matcher = pattern.matcher(line);
                    matcher.matches();
                    String nodename = matcher.group(1);

                    Node node = nodes.getOrDefault(nodename, new Node(nodename));
                    nodes.put(nodename, node);

                    int weight = Integer.valueOf(matcher.group(2));
                    node.setWeight(weight);
                    String group3 = matcher.group(3);
                    if (group3 != null) {
                        List<Node> children = new ArrayList<>();
                        Arrays.stream(group3.substring(4).split(", ")) //
                                .forEach(child -> {
                                    Node existingChild = nodes.get(child);
                                    if (existingChild == null) {
                                        existingChild = new Node(child);
                                        nodes.put(child, existingChild);
                                    }
                                    existingChild.setParent(node);
                                    children.add(existingChild);
                                });
                        node.setChildren(children);
                    }
                    nodes.put(nodename, node);
                });

        List<String> allChildren = nodes.values().stream() //
                .map(Node::getChildren) //
                .filter(Objects::nonNull) //
                .flatMap(Collection::stream) //
                .map(Node::getName) //
                .collect(Collectors.toList());

        String root = nodes.keySet().stream() //
                .filter(((Predicate<String>) allChildren::contains).negate()) //
                .findAny().get();

        List<Node> nodesToCheck = nodes.values().stream() //
                .filter(node -> node.getChildren().isEmpty()) //
                .collect(Collectors.toList());

        int weightDiff = 0;
        while (weightDiff == 0) {
            for (Node child : nodesToCheck) {
                weightDiff = getWeightDiff(child);
                if (weightDiff > 0) {
                    break;
                }
            }
            nodesToCheck = nodesToCheck.stream() //
                    .map(Node::getParent) //
                    .collect(Collectors.toList());
        }
        System.out.println(weightDiff);
    }

    private static int getWeightDiff(final Node node) {
        List<Integer> distinctWeights = node.children.stream().map(Node::getTotalWeight).distinct().collect(Collectors.toList());
        if (distinctWeights.size() > 1) {
            int max = Math.abs(distinctWeights.get(0) - distinctWeights.get(1));
            int min = Math.min(distinctWeights.get(0), distinctWeights.get(1));

            Node heavyNode = node.children.stream().filter(n -> n.getTotalWeight() == max).findFirst().get();
            return heavyNode.getWeight() - (max - min);
        }
        return 0;
    }

}
