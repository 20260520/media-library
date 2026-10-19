package musicplaylist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
//whole Main.java is powered by deepseekv4pro with claude code
public class Main {
    public static void main(String[] args) {
        Linksandkit kit = new Linksandkit();
        //reuse the kit's Scanner: two Scanners on System.in would steal each other's buffered input
        Scanner in = kit.in;
        System.out.println("--------------------Welcome to musicplaylist--------------------");
        int choice = -1;
        while (choice != 0) {
            printmenu();
            choice = readint(in);
            switch (choice) {
                case 1:
                    kit.addnewmusic();
                    break;
                case 2:
                    removeone(kit, in);
                    break;
                case 3:
                    kit.removeallmusics();
                    System.out.println("the playlist has been cleared");
                    break;
                case 4:
                    printmusiclist(kit.outputmusiclist());
                    break;
                case 5:
                    searchone(kit, in);
                    break;
                case 6:
                    loaddemo(kit);
                    break;
                case 0:
                    System.out.println("goodbye");
                    break;
                default:
                    System.out.println("wrong choice, please input a number from 0 to 6");
            }
        }
        in.close();
    }

    static void printmenu() {
        System.out.println("----------------------------------------------");
        System.out.println("1. add a music");
        System.out.println("2. remove a music by index");
        System.out.println("3. remove all musics");
        System.out.println("4. show the playlist");
        System.out.println("5. search a music by title");
        System.out.println("6. load the demo playlist");
        System.out.println("0. exit");
        System.out.println("----------------------------------------------");
    }

    static int readint(Scanner in) {
        while (!in.hasNextInt()) {
            System.out.println("wrong input, please input a number");
            in.nextLine();
        }
        int number = in.nextInt();
        in.nextLine();//swallow the newline left by nextInt()
        return number;
    }

    static void removeone(Linksandkit kit, Scanner in) {
        System.out.println("please input the index of the music you want to remove");
        int index = readint(in);
        Unit[] list = kit.outputmusiclist();
        if (index < 0 || index >= list.length) {
            System.out.println("there is no music at index " + index);
            return;
        }
        kit.removemusic(index);
        System.out.println("the music at index " + index + " has been removed");
    }

    static void searchone(Linksandkit kit, Scanner in) {
        System.out.println("please input the title you want to search");
        String target = in.nextLine();
        ArrayList<Unit> list = new ArrayList<Unit>(Arrays.asList(kit.outputmusiclist()));
        int found = kit.search4String((byte) 1, list, target);//advanced=1 means the kit stays quiet, Main prints the result
        if (found == -1) {
            System.out.println("cannot find the music titled [" + target + "]");
        } else {
            System.out.println("found at index " + found);
            kit.get1music((byte) 0, found, list);
        }
    }

    static void printmusiclist(Unit[] list) {
        if (list.length == 0) {
            System.out.println("the playlist is empty");
            return;
        }
        System.out.println("--------------------playlist--------------------");
        for (int index = 0; index < list.length; index++) {
            Unit u = list[index];
            System.out.println("[" + index + "] " + u.title + " - " + u.artist
                    + " (" + u.albumtitle + ") " + u.releasedate);
        }
        System.out.println("-----------------------------------------------");
    }

    static void loaddemo(Linksandkit kit) {
        Unit[] demo = new Unit[4];
        demo[0] = makeunit("Shape of You", "Ed Sheeran", "Divide", "enUS", 20170106);
        demo[1] = makeunit("晴天", "周杰伦", "叶惠美", "zhCN", 20030731);
        demo[2] = makeunit("Lemon", "米津玄師", "BOOTLEG", "jaJP", 20180314);
        demo[3] = makeunit("Bohemian Rhapsody", "Queen", "A Night at the Opera", "enUS", 19751031);
        kit.inputmusiclist((byte) 1, (byte) 1, demo);
        System.out.println("demo playlist loaded");
    }

    static Unit makeunit(String title, String artist, String albumtitle, String language, int releasedate) {
        Unit u = new Unit();
        u.title = title;
        u.artist = artist;
        u.albumtitle = albumtitle;
        u.language = IVCharsToInt(language);
        u.releasedate = releasedate;
        return u;
    }

    //same encoding as Linksandkit.IVCharsToInt, which is private so Main cannot reuse it
    static int IVCharsToInt(String str) {
        if (str == null || str.isEmpty()) return 0;
        byte[] bytes = str.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        int len = Math.min(bytes.length, 4);
        int result = 0;
        for (int i = 0; i < len; i++) {
            result |= (bytes[i] & 0xFF) << ((3 - i) * 8);
        }
        return result;
    }
}
