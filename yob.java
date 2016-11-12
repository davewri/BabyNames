
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;
import java.util.*;

public class yob
{
    public void totalBirths(FileResource fr){
        int totalNames = 0;
        int totalGirlNames = 0;
        int totalBoyNames = 0;
        for (CSVRecord rec:fr.getCSVParser(false)){
            if(rec.get(1).equals("F")){
                totalGirlNames += 1;
            }
            else{
                totalBoyNames += 1;
            }
            totalNames = totalGirlNames + totalBoyNames;
        }
        System.out.println("total names =  "+ totalNames +", girl names = " + totalGirlNames + ", boy names = "+totalBoyNames);
    }
    
    public int getRank(int year, String name, String gender){
        int numberOfTheName = 0;
        int rankSoFar = 1;
        FileResource fr = new FileResource("us_babynames/us_babynames_by_year/yob"+year+".csv");
        for (CSVRecord rec:fr.getCSVParser(false)){
            if(rec.get(1).equals(gender) && rec.get(0).equals(name)){
                numberOfTheName = Integer.parseInt(rec.get(2));
            }
        }
        if(numberOfTheName != 0){
            for (CSVRecord rec:fr.getCSVParser(false)){
                if(Integer.parseInt(rec.get(2)) > numberOfTheName && rec.get(1).equals(gender)){
                    rankSoFar += 1;
                }
            }
        }
        else{
            rankSoFar = -1;
        }
        return rankSoFar;
    }
    
    public String getName(int year, int rank, String gender){
        FileResource fr = new FileResource("us_babynames/us_babynames_by_year/yob"+year+".csv");
        int totalNames = 0;
        int totalGirlNames = 0;
        int totalBoyNames = 0;
        String name = null;
        for (CSVRecord rec:fr.getCSVParser(false)){
            if(rec.get(1).equals("F")){
                totalGirlNames += 1;
            }
            else{
                totalBoyNames += 1;
            }
        }
        
        totalNames = totalGirlNames + totalBoyNames;
        
        for (CSVRecord rec:fr.getCSVParser(false)){
            if(gender.equals("F") && rank == (int)rec.getRecordNumber() ){
                name = rec.get(0);
            }
            else if(gender.equals("M")&& rank == (int)rec.getRecordNumber()-totalGirlNames){
                name = rec.get(0);
            }
            //else{
            //    name = "NO NAME";
            //}
        }
        return name;
        }
    
    public void whatIsNameInYear(String name, int year, int newYear, String gender){
        int rankOfThisName;
        String newName;
        rankOfThisName = getRank(year, name, gender);
        newName = getName(newYear, rankOfThisName, gender);
        System.out.println(name+" born in "+year+"  would be "+newName+" if she was born in "+newYear);
    }    
    
    public int yearOfHighestRank(String name, String gender){
        int year = 0;
        int rankOfThisYear=1;
        int highestRankSoFar = 0;
        int yearOfHighestRank = 0;
        DirectoryResource dr = new DirectoryResource();
        
        for(File f:dr.selectedFiles()){
            
            year = Integer.parseInt(f.getName().substring(3,7));
            rankOfThisYear = getRank(year,name,gender);
            
            if(yearOfHighestRank == 0){
            highestRankSoFar = rankOfThisYear;
            yearOfHighestRank = year;
            }
            else if(rankOfThisYear < highestRankSoFar){
                highestRankSoFar = rankOfThisYear;
                yearOfHighestRank = year;
            }
        }
        return yearOfHighestRank;
    }
    
    public double getAverageRank(String name, String gender){
        
        int year = 0;
        int rankOfThisYear=0;
        int numberOfFiles = 0;
        int totalRanks = 0;
        double averageRank = 0;
        DirectoryResource dr = new DirectoryResource();
       
        for(File f:dr.selectedFiles()){
            year = Integer.parseInt(f.getName().substring(3,7));
            rankOfThisYear = getRank(year,name,gender);
            numberOfFiles +=1;
            totalRanks += rankOfThisYear;
        }
        averageRank = (double)totalRanks/numberOfFiles;
        return averageRank;
    }
    public int getTotalBirthRankedHigher(int year, String name, String gender){
        int rank = 0 ;
        
        int totalBirth = 0;
        
        String Filename = "C:/Users/wrighdav/Documents/Java/Coursera/BabyNames/us_babynames/us_babynames_by_year/yob" + year + ".csv";       
        FileResource fr = new FileResource(Filename);
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if ( rec.get(1).equals(gender)) {
                 rank++;
                 
                 if (rec.get(0).equals(name)) return totalBirth;//}
                 totalBirth = totalBirth + Integer.parseInt(rec.get(2));
                }
                
            }
        return -1;
       }        
    
    
    public void testTotalBirths(){
        FileResource fr = new FileResource("us_babynames/us_babynames_test/example-small.csv");
        totalBirths(fr);
    }
    public void testGetRank(){
        System.out.println(getRank(2012, "Mason", "M"));
    }
    public void testGetName(){
        System.out.println(getName(2010, 1, "M"));
    }
    public void testWhatIsNameInYear(){
        whatIsNameInYear("Owen", 1974, 2014, "M");
    }
    public void testYearOfHighestRank(){
        System.out.println(yearOfHighestRank("Mich","M"));
    }
    public void testGetAverageRank(){
        System.out.println(getAverageRank("Susan","F"));
    }
    public void testGetTotalBirthRankedHigher(){
        System.out.println(getTotalBirthRankedHigher(1990, "Drew", "M"));
    }
}


