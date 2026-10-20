package archivelistV2;

public class SubBook extends SupUnit{
    SubBook(){super();}
    SubBook(short amountOfpages, String author, int language, String name, int releasedate, int sequencenumber){
        super(author, language, name, releasedate, sequencenumber);
        this.amountOfpages = amountOfpages;
    }
    static SubBook InitBookUnitandReturn(short amountOfpages, String author, int language, int lengthOftime, String name, int releasedate, int sequencenumber){
        SubBook bookunit = new SubBook(amountOfpages, author, language, name, releasedate, sequencenumber);
        return bookunit;
    }
    short amountOfpages;
}
