package archivelistV2;

public class SubMusic extends SupUnit{
    SubMusic(){
        super();
    }
    SubMusic(String albumtitle, String author, int language, int lengthOftime, String name, int releasedate, int sequencenumber){
        super(author, language, name, releasedate, sequencenumber);
        this.albumtitle = albumtitle;
        this.lengthOftime = lengthOftime;
    }
    SubMusic InitMusicUnitandReturn(byte test, String albumtitle, String author, int language, int lengthOftime, String name, int releasedate, int sequencenumber){
        SubMusic musicunit = new SubMusic();
        musicunit.albumtitle = albumtitle;
        musicunit.lengthOftime = lengthOftime;
        return (SubMusic) super.InitSupUnitandReturn(test, musicunit, author, language, name, releasedate, sequencenumber);
        //这样写也可以 return (SubMusic) InitSupUnitandReturn(test, musicunit, author, language, name, releasedate, sequencenumber);
    }//这里是屎山代码,为了在一个项目中熟悉父类和子类如何配合
    static SubMusic InitMusicUnitandReturn(String albumtitle, String author, int language, int lengthOftime, String name, int releasedate, int sequencenumber){
        SubMusic musicunit = new SubMusic();
        musicunit.albumtitle = albumtitle;
        musicunit.lengthOftime = lengthOftime;
        return (SubMusic) InitSupUnitandReturn(musicunit, author, language, name, releasedate, sequencenumber);
        //这里是屎山代码,为了在一个项目中熟悉父类和子类如何配合
    }
    String albumtitle;
    int lengthOftime;
}
