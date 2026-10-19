package musicplaylist;
import java.util.ArrayList;
import java.util.Arrays;
//import java.util.HashMap;
import java.util.Scanner;
import java.util.Collections;
//可以加artlist laguage等的搜索功能,新建一条链,链里每一项存指针,把指针重新按照某个东西升序排列一遍
public class Linksandkit {
    private ArrayList<Unit> titlemain = new ArrayList<Unit>();
    Scanner in = new Scanner(System.in);
    Linksandkit(){
    }
    private int IVCharsToInt(String str) {
        if (str == null || str.isEmpty()) return 0;
        // 1. 转成 UTF-8/ASCII 字节数组
        byte[] bytes = str.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        // 2. 拼接前 4 个字节到 32 位 int 中（大端序）
        // Math.min 保证最多只取前 4 个字节，多余的直接忽略截断
        int len = Math.min(bytes.length, 4);
        int result = 0;
        for (int i = 0; i < len; i++) {
            result |= (bytes[i] & 0xFF) << ((3 - i) * 8);
        }
        return result;//The function powered by Google Gemini
    }
    private String intTo4Chars(int value) {
        byte[] bytes = new byte[4];
        // 按照大端序拆分 32 位 int 为 4 个字节
        for (int i = 0; i < 4; i++) {
            bytes[i] = (byte) ((value >>> ((3 - i) * 8)) & 0xFF);
        }
        // 转回 UTF-8 字符串
        return new String(bytes, java.nio.charset.StandardCharsets.UTF_8);
    }//The function powered by Google Gemini   
    void addnewmusic(byte advanced, byte allowrepeated, Unit tempunit){
        if(titlemain.size() != 0){
            for(int index = 0; index < titlemain.size(); index++){
                int i = tempunit.title.compareTo(titlemain.get(index).title);
                if(index == 0 && i < 0){
                    titlemain.add(0, tempunit);
                    break;
                }else if((index == titlemain.size()-1)&& i > 0){
                    titlemain.add(titlemain.size(), tempunit);
                    break;
                }else if(i > 0 && tempunit.title.compareTo(titlemain.get(index+1).title) < 0){
                    titlemain.add(index + 1, tempunit);
                    break;
                }else if(i == 0){
                    if(allowrepeated == 0){
                        if(advanced == 0){
                            System.out.println("--------------------Warning--------------------");
                            System.out.println("the music that you want add has already existed");
                            System.out.println("input 0 to cancel. input 1 to continue");
                            System.out.println("if input 1, the last change will be saved and cover\n previous all information of the Same Title music");
                            advanced = in.nextByte();
                            in.nextLine();
                        }
                        if(advanced == 1){
                            titlemain.remove(index);
                            titlemain.add(index, tempunit);
                            break;
                        }
                    }else if(allowrepeated == 1){
                        titlemain.add(index + 1, tempunit);
                        break;
                    }
                }
            }
        }else{    
            titlemain.add(tempunit);
        }
    }
    void addnewmusic(){
        Unit tempunit = new Unit();
        tempunit.sequencenumber = titlemain.size() + 1;
        System.out.println("please input title of music");
        tempunit.title = in.nextLine();
        System.out.println("please input name of artist");
        tempunit.artist = in.nextLine();
        System.out.println("please input title of albumn");
        tempunit.albumtitle = in.nextLine();
        System.out.println("please input language of music");
        System.out.println("such as zhCN, enUS etc.");
        tempunit.language = IVCharsToInt(in.nextLine());
        System.out.println("please input release date of music");
        System.out.println("such as 20000202, 20220222 etc.");
        tempunit.releasedate = in.nextInt();
        in.nextLine();
        byte advanced = 0, allowrepeated = 0;
        addnewmusic(advanced, allowrepeated, tempunit);
    }
    void removemusic(int index){
        titlemain.remove(index);
    }
    void removeallmusics(){
        titlemain.clear();
    }
    private void sequenceviaString(byte allowrepeated, ArrayList<Unit> inputchain){
    /*目前只支持title,还没想到办法怎么识别传入的是Unit中的哪个String并实现变量名软编码 */ 
        if(inputchain != null){
            byte successful = 0;
            while(successful == 0){
                successful = 1;
                for(int index = 0; index+1 < inputchain.size(); index++){
                    int difference = inputchain.get(index).title.compareTo(inputchain.get(index+1).title);
                    if(difference > 0){
                        //inputchain.add(index+1, inputchain.remove(index));//小巧思,性能低,时间复杂度O(n)
                        Collections.swap(inputchain, index, index + 1);//这一行是查询网络得知可以这么写
                        successful = 0;
                    }else if(difference == 0 && allowrepeated == 0){
                        inputchain.remove(index);
                        index--;
                    }
                }
            }
        }
    }
    void inputmusiclist(byte advanced, byte coveralltheprevious, Unit[] input){
        byte allowrepeated = 0;
        if(coveralltheprevious == 1){
            titlemain.clear();
            titlemain = new ArrayList<Unit>(Arrays.asList(input));//这一行是查询网络得知可以这么写
        }else if(coveralltheprevious == 0){
            for(int index = 0; index < input.length; index++){
                addnewmusic(advanced, allowrepeated, input[index]);
            }
        }
        sequenceviaString(allowrepeated, titlemain);
    }
    Unit[] outputmusiclist(){
        Unit[] output = titlemain.toArray(new Unit[0]);
        return output;
    }
    int search4String(byte advanced, ArrayList<Unit> inputchain, String target){ 
    //NO special supports for [allowrepeated], 暂时不支持除title以外的String搜索  
        int targetindex = -1, maxindex = inputchain.size()-1, minindex = 0;
        while(minindex <= maxindex){
            int q = (minindex + maxindex) / 2;//, r = (minindex + maxindex) % 2;
            if(target.compareTo(inputchain.get(q).title) == 0){
                targetindex = q;
                break;
            }else if(target.compareTo(inputchain.get(q).title) < 0){
                maxindex = q - 1;
            }else{
                minindex = q + 1;
            }
        }
        return targetindex;
    }
    Unit get1music(byte advanced, int index, ArrayList<Unit> inputchain){
        if(advanced == 0){
            System.out.println("albumtitle: "+inputchain.get(index).albumtitle+"\ntitle: "+inputchain.get(index).title+"\nartist: "+inputchain.get(index).artist);
            System.out.println("language: "+intTo4Chars(inputchain.get(index).language)+"\nreleasedate: "+inputchain.get(index).releasedate+"\nsequencenumber: "+inputchain.get(index).sequencenumber);
        }
        return inputchain.get(index);
    } 
}
