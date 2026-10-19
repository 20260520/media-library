package musicplaylist;

import java.util.ArrayList;
import java.util.Arrays;

//temporary regression tests for the bug fixes, run with: java musicplaylist.Test
public class Test {
    static Unit u(String title) {
        Unit x = new Unit();
        x.title = title;
        x.artist = "test";
        x.albumtitle = "test";
        x.releasedate = 20000101;
        return x;
    }

    static String listTitles(Linksandkit kit) {
        StringBuilder sb = new StringBuilder();
        for (Unit u : kit.outputmusiclist()) {
            if (sb.length() > 0) sb.append(' ');
            sb.append(u.title);
        }
        return sb.toString();
    }

    static void check(String actual, String expected, String name) {
        if (!expected.equals(actual)) {
            throw new AssertionError(name + ": expected [" + expected + "] got [" + actual + "]");
        }
        System.out.println("ok: " + name);
    }

    static void check(boolean ok, String name) {
        if (!ok) throw new AssertionError(name);
        System.out.println("ok: " + name);
    }

    public static void main(String[] args) {
        //1. sort regression: the old termination flag left 2 3 1 4 as 2 1 3 4
        Linksandkit kit = new Linksandkit();
        kit.inputmusiclist((byte) 1, (byte) 1, new Unit[]{u("2"), u("3"), u("1"), u("4")});
        check(listTitles(kit), "1 2 3 4", "sort 2 3 1 4");

        //2. duplicate + allowrepeated=1 used to loop forever, must add exactly one copy
        Linksandkit kit2 = new Linksandkit();
        kit2.addnewmusic((byte) 1, (byte) 1, u("a"));
        kit2.addnewmusic((byte) 1, (byte) 1, u("a"));
        check(listTitles(kit2), "a a", "duplicate allowrepeated");

        //3. duplicate + allowrepeated=0 advanced=1 replaces the old entry
        Linksandkit kit3 = new Linksandkit();
        kit3.addnewmusic((byte) 1, (byte) 0, u("a"));
        Unit b = u("a");
        b.artist = "new artist";
        kit3.addnewmusic((byte) 1, (byte) 0, b);
        check(kit3.outputmusiclist()[0].artist, "new artist", "duplicate replace");
        check(listTitles(kit3), "a", "no duplicate left");

        //4. binary search: found / not found / empty
        ArrayList<Unit> chain = new ArrayList<Unit>(Arrays.asList(kit.outputmusiclist()));
        check(kit.search4String((byte) 1, chain, "3") == 2, "search found");
        check(kit.search4String((byte) 1, chain, "9") == -1, "search not found");
        check(kit.search4String((byte) 1, new ArrayList<Unit>(), "3") == -1, "search empty list");

        //5. remove by index
        kit.removemusic(0);
        check(listTitles(kit), "2 3 4", "remove by index");

        //6. batch input merged into an existing sorted list stays sorted and deduped
        Linksandkit kit4 = new Linksandkit();
        kit4.addnewmusic((byte) 1, (byte) 0, u("b"));
        kit4.inputmusiclist((byte) 1, (byte) 0, new Unit[]{u("a"), u("c"), u("b")});
        check(listTitles(kit4), "a b c", "batch merge");

        System.out.println("ALL TESTS PASSED");
    }
}
