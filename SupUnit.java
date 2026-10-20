package archivelistV2;
//import java.util.ArrayList;
class SupUnit {
    SupUnit(){ 
    }
    SupUnit(String author, int language, String name, int releasedate, int sequencenumber){
        this.author = author;
        this.language = language;
        this.name = name;
        this.releasedate = releasedate;
        this.sequencenumber = sequencenumber;
    }
    protected SupUnit InitSupUnitandReturn(byte test, SupUnit generalunit, String author, int language, String name, int releasedate, int sequencenumber){
        generalunit.author = author;
        generalunit.language = language;
        generalunit.name = name;
        generalunit.releasedate = releasedate;
        generalunit.sequencenumber = sequencenumber;
        return generalunit;
    }//这里是屎山代码,为了在一个项目中熟悉父类和子类如何配合
    static protected SupUnit InitSupUnitandReturn(SupUnit generalunit, String author, int language, String name, int releasedate, int sequencenumber){
        generalunit.author = author;
        generalunit.language = language;
        generalunit.name = name;
        generalunit.releasedate = releasedate;
        generalunit.sequencenumber = sequencenumber;
        return generalunit;
    }//这里是屎山代码,为了在一个项目中熟悉父类和子类如何配合
    String author;
    int language;
    String name;
    int releasedate;
    int sequencenumber;                                              
}            
