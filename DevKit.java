package archivelistV2;
import java.util.ArrayList;
import java.util.Arrays;
//import java.util.HashMap;
import java.util.Scanner;
import java.util.Collections;
//可以加artlist laguage等的搜索功能,新建一条链,链里每一项存指针,把指针重新按照某个东西升序排列一遍
public class DevKit {
    private ArrayList<SupUnit> NameBasedChain = new ArrayList<SupUnit>();
    Scanner in = new Scanner(System.in);
    DevKit(){
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
    void Add1ArchiveUnit(byte advanced, byte allowduplicate, SupUnit tempunit){
        if(NameBasedChain.size() != 0){
            for(int index = 0; index < NameBasedChain.size(); index++){
                int i = tempunit.name.compareTo(NameBasedChain.get(index).name);
                if(index == 0 && i < 0){
                    NameBasedChain.add(0, tempunit);
                    break;
                }else if((index == NameBasedChain.size()-1)&& i > 0){
                    NameBasedChain.add(NameBasedChain.size(), tempunit);
                    break;
                }else if(i > 0 && tempunit.name.compareTo(NameBasedChain.get(index+1).name) < 0){
                    NameBasedChain.add(index + 1, tempunit);
                    break;
                }else if(i == 0){
                    if(allowduplicate == 0){
                        if(advanced == 0){
                            System.out.println("--------------------Warning--------------------");
                            System.out.println("the music that you want add has already existed");
                            System.out.println("input 0 to cancel. input 1 to continue");
                            System.out.println("if input 1, the last change will be saved and cover\n previous all information of the Same Name music");
                            advanced = in.nextByte();
                            in.nextLine();
                        }
                        if(advanced == 1){
                            NameBasedChain.remove(index);
                            NameBasedChain.add(index, tempunit);
                            break;
                        }
                    }else if(allowduplicate == 1){
                        NameBasedChain.add(index + 1, tempunit);
                        break;
                    }
                }
            }
        }else{    
            NameBasedChain.add(tempunit);
        }
    }
    void Add1ArchiveUnit(){
        System.out.println("which type of Archive Unit you want to create ?");
        System.out.println("1. a Book Unit");
        System.out.println("2. a Movie Unit");
        System.out.println("3. a Music Unit");
        System.out.println("please input 1, 2 or 3 to select options");
        byte type = in.nextByte();
        in.nextLine();
        SupUnit tempunit = null;
        if (type == 1) {
            SubBook tempunit1 = new SubBook();
            System.out.println("please input amount of pages");
            tempunit1.amountOfpages = in.nextShort();
            in.nextLine();
            tempunit = tempunit1;
        } else if (type == 2) {
            SubMovie tempunit2 = new SubMovie();
            System.out.println("please input length of time");
            System.out.println("such as 235959 etc.");
            tempunit2.lengthOftime = in.nextInt();
            in.nextLine();
            tempunit = tempunit2;
        } else if (type == 3) {
            SubMusic tempunit3 = new SubMusic();
            System.out.println("please input name of album");
            tempunit3.albumtitle = in.nextLine();
            System.out.println("please input length of time");
            System.out.println("such as 235959 etc.");
            tempunit3.lengthOftime = in.nextInt();
            in.nextLine();
            tempunit = tempunit3;
        } else {
            System.out.println("ERROR");
        }
        tempunit.sequencenumber = NameBasedChain.size() + 1;
        System.out.println("please input name of music");
        tempunit.name = in.nextLine();
        System.out.println("please input name of artist");
        tempunit.author = in.nextLine();
        System.out.println("please input language of music");
        System.out.println("such as zhCN, enUS etc.");
        tempunit.language = IVCharsToInt(in.nextLine());
        System.out.println("please input release date of music");
        System.out.println("such as 20000202, 20220222 etc.");
        tempunit.releasedate = in.nextInt();
        in.nextLine();
        byte advanced = 0, allowduplicate = 0;
        Add1ArchiveUnit(advanced, allowduplicate, tempunit);
    }
    void Remove1ArchiveUnit(int index){
        NameBasedChain.remove(index);
    }
    void RemoveALLArchiveUnits(){
        NameBasedChain.clear();
    }
    private void sequenceviaString(byte allowduplicate, ArrayList<SupUnit> inputchain){
    /*目前只支持name,还没想到办法怎么识别传入的是Unit中的哪个String并实现变量名软编码 */ 
        if(inputchain != null){
            byte successful = 0;
            while(successful == 0){
                successful = 1;
                for(int index = 0; index+1 < inputchain.size(); index++){
                    int difference = inputchain.get(index).name.compareTo(inputchain.get(index+1).name);
                    if(difference > 0){
                        //inputchain.add(index+1, inputchain.remove(index));//小巧思,性能低,时间复杂度O(n)
                        Collections.swap(inputchain, index, index + 1);//这一行是查询网络得知可以这么写
                        successful = 0;
                    }else if(difference == 0 && allowduplicate == 0){
                        inputchain.remove(index);
                        index--;
                    }
                }
            }
        }
    }
    void inputchain(byte advanced, byte coveralltheprevious, SupUnit[] input){
        byte allowduplicate = 0;
        if(coveralltheprevious == 1){
            NameBasedChain.clear();
            NameBasedChain = new ArrayList<SupUnit>(Arrays.asList(input));//这一行是查询网络得知可以这么写
        }else if(coveralltheprevious == 0){
            for(int index = 0; index < input.length; index++){
                Add1ArchiveUnit(advanced, allowduplicate, input[index]);
            }
        }
        sequenceviaString(allowduplicate, NameBasedChain);
    }
    SupUnit[] outputmusiclist(){
        SupUnit[] output = NameBasedChain.toArray(new SupUnit[0]);
        return output;
    }
    int search4String(byte advanced , ArrayList<SupUnit> inputchain, String target){ 
    //NO special supports for [allowduplicate], 暂时不支持除name以外的String搜索  
        int targetindex = -1, maxindex = inputchain.size()-1, minindex = 0;
        while(minindex <= maxindex){
            int q = (minindex + maxindex) / 2;//, r = (minindex + maxindex) % 2;
            if(target.compareTo(inputchain.get(q).name) == 0){
                targetindex = q;
                break;
            }else if(target.compareTo(inputchain.get(q).name) < 0){
                maxindex = q - 1;
            }else{
                minindex = q + 1;
            }
        }
        return targetindex;
    }
    SupUnit Get1ArchiveUnit(int index, ArrayList<SupUnit> inputchain){
        return Get1ArchiveUnit((byte)1, index, (byte)0, inputchain);
    }
    SupUnit Get1ArchiveUnit(byte advanced, int index, byte type, ArrayList<SupUnit> inputchain){
        if(advanced == 0){
            if (type == 1) {
                System.out.println("amount of pages: "+((SubBook)inputchain.get(index)).amountOfpages);
            } else if (type == 2) {
                System.out.println("length of time: "+((SubMovie)inputchain.get(index)).lengthOftime);
            } else if (type == 3) {
                System.out.println("name of album: "+((SubMusic)inputchain.get(index)).albumtitle);
                System.out.println("length of time: "+((SubMusic)inputchain.get(index)).lengthOftime);
            } else {
                System.out.println("ERROR");
            }
            System.out.println("name: "+inputchain.get(index).name+"\nartist: "+inputchain.get(index).author);
            System.out.println("language: "+intTo4Chars(inputchain.get(index).language));
            System.out.println("\nreleasedate: "+inputchain.get(index).releasedate+"\nsequencenumber: "+inputchain.get(index).sequencenumber);
        }
        return inputchain.get(index);
    } 
}
