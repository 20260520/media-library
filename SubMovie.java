package archivelistV2;

public class SubMovie extends SupUnit{
    SubMovie(){super();}
    SubMovie(String author, int language, int lengthOftime, String name, int releasedate, int sequencenumber){
        super(author, language, name, releasedate, sequencenumber);
        this.lengthOftime = lengthOftime;
    }
    static SubMovie InitMovieUnitandReturn(String author, int language, int lengthOftime, String name, int releasedate, int sequencenumber){
        SubMovie movieunit = new SubMovie(author, language, lengthOftime, name, releasedate, sequencenumber);
        return movieunit;
    }
    int lengthOftime;
}
