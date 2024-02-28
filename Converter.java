import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.io.IOException;


/* 
P|förnamn|efternamn                     person
T|mobilnummer|fastnätsnummer            telephone
A|gata|stad|postnummer                  address
F|namn|födelseår                        family

P kan följas av T, A och F
F kan följas av T och A
 */

 
public class Converter
{
    public static String getXML(List<String> input)
    {
        
      
        StringBuilder output = new StringBuilder("<people>\n"); //initializes the output, starts with "<people>", can be built upon with new text
        int personCounter = 0;
        boolean familyActive = false;
        
        for (int i = 0; i < input.size(); i++) // for each line of input
        {
            String[] lineSplit = (input.get((i))).split("\\|"); // using  "\\"" encapsulates the "|"
            String spaces = "";
            switch (lineSplit[0]) 
            {
                case "P":
                    familyActive = false; // removes extra spacing
                    if(personCounter > 0)
                    {
                        output.append("  </person>\n"); // end of person section
                    }
                    personCounter++;

                    
                    output.append("  <person>\n");   // start of person section
                    
                   
                        // first name
                    output.append("    <firstname>");
                    if(lineSplit.length > 1)
                    {
                        output.append(lineSplit[1]); // this is the first name from input
                    }
                    output.append("</firstname>\n");
                    
                    
                    
                    // last name
                    output.append("    <lastname>");
                    if(lineSplit.length > 2)
                    {
                        output.append(lineSplit[2]); // this is the last name from input
                    }
                    output.append("</lastname>\n");
                    
                    break; // this end the case, otherwise it goes to next case. Which is bad
            //___________________________________________________________________________________________________________

                case "T":
                    spaces = "";
                    if(familyActive) // if extra spaces are needed for the format to be correct while writing family members
                    {
                        spaces = "    ";
                    }
                    output.append(spaces + "    <phone>\n"); // start of phone section

                    output.append(spaces + "      <mobile>");
                    if(lineSplit.length > 1)
                    {
                        output.append(lineSplit[1]); // this is the mobile number
                    }
                    output.append("</mobile>\n");

                    output.append(spaces + "      <landline>");
                    if(lineSplit.length > 2)
                    {
                        output.append(lineSplit[2]); // this is the landline number
                    }
                    output.append("</landline>\n");

                    output.append(spaces + "    </phone>\n"); // end of phone section

                    break; // this end the case, otherwise it goes to next case. Which is bad
            //___________________________________________________________________________________________________________

                case "A":
                    spaces = "";
                    if(familyActive)// if extra spaces are needed for the format to be correct while writing family members
                    {
                        spaces = "    ";
                    }

                    output.append(spaces + "    <address>\n"); // start of address section

                    output.append(spaces + "      <street>");
                    if(lineSplit.length > 1)
                    {
                        output.append(lineSplit[1]); // this is the street name
                    }
                    output.append("</street>\n");

                    output.append(spaces + "      <city>");
                    if(lineSplit.length > 2)
                    {
                        output.append(lineSplit[2]); // this is the city name
                    }
                    output.append("</city>\n");

                    output.append(spaces + "      <zip>");
                    if(lineSplit.length > 3)
                    {
                        output.append(lineSplit[3]); // this is the zip code
                    }
                    output.append("</zip>\n");

                    output.append(spaces + "    </address>\n"); // end of adress section

                    break; // this end the case, otherwise it goes to next case. Which is bad
            //___________________________________________________________________________________________________________

                case "F":
                    if(familyActive)
                    {
                        output.append("    </family>\n");
                    }
                    familyActive = true; // adds extra spacing

                    output.append("    <family>\n"); // start of family section

                    output.append("      <name>");
                    if(lineSplit.length > 1)
                    {
                        output.append(lineSplit[1]); // this is the name
                    }
                    output.append("</street>\n");

                    output.append("      <born>");
                    if(lineSplit.length > 2)
                    {
                        output.append(lineSplit[2]); // this is the birthyear
                    }
                    output.append("</born>\n");

                    break; // this end the case, otherwise it goes to next case. Which is bad
            //___________________________________________________________________________________________________________     
            }
        }
        output.append("  </person>\n"); // end of person section
        output.append("</people>"); // end of people section
        
        return output.toString();
    }


    public static void main(String args[])
    {
        String filePath = "input.txt";

        try 
        {// Read line by line and save into list
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            
            String xmlString = getXML(lines);// call function
            System.out.println(xmlString); // print result
        }
        catch (IOException error) // in case something goes wrong
        {
            error.printStackTrace(); //print info
        }
    }

    
}